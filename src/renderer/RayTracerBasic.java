package renderer;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.*;

/**
 * A basic implementation of a ray tracer class.
 */

public class RayTracerBasic extends RayTracerBase {

    public RayTracerBasic(Scene scene) {
        super(scene);
    }


    /**
     * Maximum level of depth in recursion.
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * Minimum scaling of k in recursion.
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * The initial value of K.
     */
    private static final double INITIAL_K = 1.0;



    /**
     * A function to check if a light source is shaded.
     * @param light The light source that we want to check if
     * @param l The l vector.
     * @param n The n vector.
     * @param geoPoint The geometric intersection point with the geometry.
     * @return <b>true</b> if the light source is not shaded by other geometries, otherwise <b>false</b>.
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {
        Ray lightRay = new Ray(geoPoint.point, l.scale(-1), n);
        List<GeoPoint> intersections = scene.geometries
                .findGeoIntersections(lightRay, light.getDistance(geoPoint.point));
        return intersections == null || intersections.isEmpty() || geoPoint.geometry.getMaterial().kT != 0;
    }

    /**
     * A function to check the ktr (check the transparency of the blocking lights).
     * @param light The light source that we want to check if
     * @param l The l vector.
     * @param n The n vector.
     * @param geoPoint The geometric intersection point with the geometry.
     * @return <b>true</b> if the light source is not shaded by other geometries, otherwise <b>false</b>.
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        double lightDistance = light.getDistance(geoPoint.point);
        var intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return 1.0;
        double ktr = 1.0;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) {
                ktr *= gp.geometry.getMaterial().kT;
                if (ktr < MIN_CALC_COLOR_K) return 0.0;
            }
        }
        return ktr;
    }



    /**
     * A function to calculate the local effects of the lights (diffusive and specular).
     * @param geoPoint The geometric point on the geometry.
     * @param ray The ray that we trace.
     * @return The sum of the local effects color in that point.
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, double k) {
        Vector v = ray.getDir(); Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        double nv = alignZero(n.dotProduct(v)); if (nv == 0) return Color.BLACK;
        Material material = geoPoint.geometry.getMaterial();
        Color color = Color.BLACK;
        for (LightSource lightSource: scene.lights) {
            Vector l = lightSource.getL(geoPoint.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                double ktr = transparency(lightSource, l, n, geoPoint);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color intensity = lightSource.getIntensity(geoPoint.point).scale(ktr);
                    color = color.add(calcDiffusive(material.kD, l, n, intensity),
                            calcSpecular(material.kS, l, n, v, material.nShininess, intensity));
                }
                // Just like the calculations in the presentation.
            }
        }
        return color;
    }

    private Color calcDiffusive(double kD, Vector l, Vector n, Color intensity) {
        return intensity.scale(kD * Math.abs(l.dotProduct(n)));
    }

    private Color calcSpecular(double kS, Vector l, Vector n, Vector v, int nShininess, Color intensity) {
        Vector r = l.subtract(n.scale(2 * (l.dotProduct(n))));
        return intensity.scale(kS * Math.pow(Math.max(v.scale(-1).dotProduct(r), 0), nShininess));
    }

    /**
     * A function to check the color of a point with a given ray.
     * @param geoPoint The geometric point on the geometry.
     * @param ray The ray that we trace.
     * @return The color of the point.
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * A recursive function to calculate the global (reflection and refraction effects).
     * @param geoPoint The point where to calculate its color.
     * @param ray The ray to that point.
     * @param level The current depth level.
     * @param k The current K.
     * @return The sum color of the global effects in that point.
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, double k) {
        Color color = geoPoint.geometry.getEmission();
        color = color.add(calcLocalEffects(geoPoint, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }


    /**
     * A recursive function to calculate the global (reflection and refraction) effects
     * @param geoPoint The point where to calculate its color.
     * @param ray The ray to that point.
     * @param level The current depth level.
     * @param k The current K.
     * @return The color of the global effects in that point.
     */
    private Color calcGlobalEffects(GeoPoint geoPoint, Ray ray, int level, double k) {
        Color color = Color.BLACK;
        Material material = geoPoint.geometry.getMaterial();
        double kr = material.kR, kkr = k * kr;
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(n, geoPoint.point, ray.getDir());
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
        }
        double kt = material.kT, kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(n, geoPoint.point, ray.getDir());
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null)
                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
        }
        return color;
    }


    /**
     * Constructs a reflected ray in a point.
     * @param n The normal vector.
     * @param point The intersection point with the geometry.
     * @param v The vector to the point.
     * @return The reflected ray.
     */
    private Ray constructReflectedRay(Vector n, Point3D point, Vector v) {
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n)));
        return new Ray(point, r, n);

    }

    /**
     * Constructs a refracted ray in a point.
     * @param n The normal vector.
     * @param point The intersection point with the geometry.
     * @param v The vector to the point.
     * @return The refracted ray.
     */
    private Ray constructRefractedRay(Vector n, Point3D point, Vector v) {
        return new Ray(point, v, n);
    }

    /**
     * Find the closest intersection point with a ray.
     * @param ray The ray to checks intersections with.
     * @return The closest intersection point with the ray.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) return null;
        return ray.findClosestGeoPoint(intersections);
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.ambientLight.getIntensity() : calcColor(closestPoint, ray);
    }
}
