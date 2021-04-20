package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * basic interface for Geometries
 */
public interface Geometry extends Intersectable {
    Vector getNormal(Point3D point);
}
