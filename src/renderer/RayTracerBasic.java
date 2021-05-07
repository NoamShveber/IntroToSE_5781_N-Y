package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracerBase {

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    private Color calcColor(Point3D point) {
        return scene.ambientLight.getIntensity();
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point3D> intersections = scene.geometries.findIntsersections(ray);
        if (intersections == null) return scene.background;
        Point3D closestPoint = ray.findClosestPoint(intersections);
        return calcColor(closestPoint);
    }
}
