package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public abstract class RayTracerBase {
    protected Scene scene;

    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Find the intersection and the scene’s geometries
     * If there is no intersection, return the background color
     * Find the closest intersection point
     * Find the color of the intersection point (Ambient light)
     *
     * @param ray The ray to trace.
     * @return The color of the intersection point.
     */
    public abstract Color traceRay(Ray ray);
}
