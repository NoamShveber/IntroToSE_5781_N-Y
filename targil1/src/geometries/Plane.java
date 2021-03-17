package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Plane class represents a plane in 3D space
 */
public class Plane implements Geometry {
    /**
     * a point on the plane
     */
    Point3D q0;

    /**
     * the normal to the plane
     */
    Vector normal;

    /**
     * plane constructor with point and vector
     * @param q0 point on the plane
     * @param normal normal to the plane
     */
    public Plane(Point3D q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal;
    }

    /**
     * plane constructor with 3 points
     * @param q0 point 1 on the plane
     * @param q1 point 2 on the plane
     * @param q2 point 3 on the plane
     */
    public Plane(Point3D q0, Point3D q1, Point3D q2) {
        this.normal = null;
        this.q0 = q0;
    }

    public Point3D getQ0() {
        return q0;
    }

    public Vector getNormal() {
        return normal;
    }

    /**
     * returns the normal to the plane in the point given
     * @param point the point
     * @return the normal from point
     */
    public Vector getNormal(Point3D point) {
        return null;
    }

    @Override
    public String toString() {
        return "q0=" + q0 +
                ", normal=" + normal;
    }
}
