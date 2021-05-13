package geometries;
import primitives.*;
import java.util.List;

/**
 * basic interface for intersectable geometries.
 */
public interface Intersectable {
    /**
     * @param ray The ray to check intersection points with.
     * @return List of intersection points between the ray and the intersectable geometries.
     */
    List<Point3D> findIntersections(Ray ray);
}
