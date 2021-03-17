package geometries;

import primitives.Point3D;
import primitives.Vector;

import java.util.Objects;

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
     * @param point the point on the sphere that we want the normal to
     * @return the normal
     */
    public Vector getNormal(Point3D point) {
        return null;
    }

    @Override
    public String toString() {
        return "center=" + center +
                ", radius=" + radius;
    }
}
