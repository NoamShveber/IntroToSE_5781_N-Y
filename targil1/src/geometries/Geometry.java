package geometries;

import primitives.*;

/**
 * basic interface for Geometries
 */
public interface Geometry {
    Vector getNormal(Point3D point);
}
