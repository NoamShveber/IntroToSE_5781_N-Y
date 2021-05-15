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

    private Color calcLocalEffects(GeoPoint point, Ray ray) {
        Vector v = ray.getDir(); Vector n = point.geometry.getNormal(point.point);
        double nv = alignZero(n.dotProduct(v)); if (nv == 0) return Color.BLACK;
        Material material = point.geometry.getMaterial();
        Color color = Color.BLACK;
        for (LightSource lightSource: scene.lights) {
            Vector l = lightSource.getL(point.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color intensity = lightSource.getIntensity(point.point);
                color = color.add(calcDiffusive(material.kD, l, n, intensity),
                        calcSpecular(material.kS, l, n, v, material.nShininess, intensity));
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
