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

    private static final double DELTA = 0.1;

    /**
     * A function to check if a light source is shaded.
     * @param light The light source that we want to check if
     * @param l The l vector.
     * @param n The n vector.
     * @param geoPoint The geometric intersection point with the geometry.
     * @return <b>true</b> if the light source is not shaded by other geometries, otherwise <b>false</b>.
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : - DELTA);
        Point3D point = geoPoint.point.add(delta);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries
                .findGeoIntersections(lightRay, light.getDistance(geoPoint.point));
        return intersections == null || intersections.isEmpty();
    }


    /**
     * A function to calculate the local effects of the lights (diffusive and specular).
     * @param point The geometric point on the geometry.
     * @param ray The ray that we trace.
     * @return The sum of the local effects color in that point.
     */
    private Color calcLocalEffects(GeoPoint point, Ray ray) {
        Vector v = ray.getDir(); Vector n = point.geometry.getNormal(point.point);
        double nv = alignZero(n.dotProduct(v)); if (nv == 0) return Color.BLACK;
        Material material = point.geometry.getMaterial();
        Color color = Color.BLACK;
        for (LightSource lightSource: scene.lights) {
            Vector l = lightSource.getL(point.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                if (unshaded(lightSource, l, n, point)) {
                    Color intensity = lightSource.getIntensity(point.point);
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
     * @param point The geometric point on the geometry.
     * @param ray The ray that we trace.
     * @return The color of the point.
     */
    private Color calcColor(GeoPoint point, Ray ray) {
        return scene.ambientLight.getIntensity().add(point.geometry.getEmission())
                .add(calcLocalEffects(point, ray));
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) return scene.background;
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint, ray);
    }
}
