package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.*;

/**
 * Sphere class represents a sphere in 3D space
 */
public class Sphere implements Geometry {
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
     * @param center center point of the sphere
     * @param radius radius length
     */
    public Sphere(Point3D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point3D getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }


    /**
     * returns the normal to the sphere in point
     *
     * @param point the point on the sphere that we want the normal to
     * @return the normal
     */
    public Vector getNormal(Point3D point) {
        return (point.subtract(center)).normalize();
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        var u = center.subtract(ray.getP0());
        var tm = u.dotProduct(ray.getDir()); // Not the other way around to not break LoD
        var d = Math.sqrt(u.lengthSquared() - tm * tm);
        if (d >= radius) return null; // No intersection points

        List<Point3D> lst = new ArrayList<Point3D>();
        var th = Math.sqrt(radius * radius - d * d);

        double t1 = tm + th, t2 = tm - th;
        if (Util.alignZero(t1) > 0) lst.add(ray.getPoint(t1));
        if (Util.alignZero(t2) > 0) lst.add(ray.getPoint(t2));

        if (lst.size() == 0) return null;

        return lst;

    }

    @Override
    public String toString() {
        return "center=" + center +
                ", radius=" + radius;
    }
}
