package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * Plane class represents a plane in 3D space.
 */
public class Plane extends Geometry {
    /**
     * The reference point of the plane.
     */
    Point3D p0;

    /**
     * the normal to the plane.
     */
    Vector normal;

    /**
     * plane constructor with point and vector
     *
     * @param p0     point on the plane
     * @param normal normal to the plane
     */
    public Plane(Point3D p0, Vector normal) {
        this.p0 = p0;
        this.normal = normal;
    }

    /**
     * plane constructor with 3 points
     *
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

    /**
     * @return The reference point of the plane.
     */
    public Point3D getP0() {
        return p0;
    }


    /**
     * @return The normal of the plane.
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * returns the normal to the plane in the point given
     *
     * @param point the point
     * @return the normal from point
     */
    public Vector getNormal(Point3D point) {
        return normal;
    }


    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        double nv = normal.dotProduct(ray.getDir());
        if (Util.isZero(nv)) return null;

        double t = Util.alignZero(normal.dotProduct(p0.subtract(ray.getP0())) / normal.dotProduct(ray.getDir()));
        if (t <= 0 || alignZero(t - maxDistance) >= 0) return null;

        ArrayList<GeoPoint> lst = new ArrayList<>();
        lst.add(new GeoPoint(this, ray.getPoint(t)));
        return lst;
    }

    @Override
    public String toString() {
        return "q0=" + p0 +
                ", normal=" + normal;
    }
}
