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
    Point3D p0;

    /**
     * the normal to the plane
     */
    Vector normal;

    /**
     * plane constructor with point and vector
     * @param p0 point on the plane
     * @param normal normal to the plane
     */
    public Plane(Point3D p0, Vector normal) {
        this.p0 = p0;
        this.normal = normal;
    }

    /**
     * plane constructor with 3 points
     * @param p0 point 1 on the plane
     * @param p1 point 2 on the plane
     * @param p2 point 3 on the plane
     */
    public Plane(Point3D p0, Point3D p1, Point3D p2) {
        Vector v1 = p1.subtract(p0);
        Vector v2 = p2.subtract(p0);
        this.normal = (v1.crossProduct(v2)).normalize();
        this.p0 = p0;
    }

    public Point3D getP0() {
        return p0;
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
        return normal;
    }

    @Override
    public String toString() {
        return "q0=" + p0 +
                ", normal=" + normal;
    }
}
