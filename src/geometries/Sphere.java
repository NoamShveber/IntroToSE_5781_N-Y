package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.*;

/**
 * Sphere class represents a sphere in 3D space
 */
public class Sphere extends Geometry {
    /**
     * central point of the sphere
     */
    Point3D center;

    /**
     * the sphere's radius
     */
    double radius;

    /**
     * constructor with center and radius params
     *
     * @param radius radius length
     * @param center center point of the sphere
     */
    public Sphere(double radius, Point3D center) {
        this.center = center;
        this.radius = radius;
    }


    /**
     * @return The center of the sphere.
     */
    public Point3D getCenter() {
        return center;
    }

    /**
     * @return The radius of the sphere.
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return (point.subtract(center)).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        var u = center.subtract(ray.getP0());
        var tm = u.dotProduct(ray.getDir()); // Not the other way around to not break LoD
        var d = Math.sqrt(u.lengthSquared() - tm * tm);
        if (d >= radius) return null; // No intersection points

        List<GeoPoint> lst = new ArrayList<>();
        var th = Math.sqrt(radius * radius - d * d);

        double t1 = tm + th, t2 = tm - th;
        if (Util.alignZero(t1) > 0 && Util.alignZero(t1 - maxDistance) <= 0)
            lst.add(new GeoPoint(this, ray.getPoint(t1)));
        if (Util.alignZero(t2) > 0 && Util.alignZero(t2 - maxDistance) <= 0)
            lst.add(new GeoPoint(this, ray.getPoint(t2)));

        if (lst.size() == 0) return null;

        return lst;
    }

    @Override
    public String toString() {
        return "center=" + center +
                ", radius=" + radius;
    }
}
