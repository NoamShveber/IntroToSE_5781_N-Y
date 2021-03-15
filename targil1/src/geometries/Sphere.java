package geometries;

import primitives.Point3D;
import primitives.Vector;

import java.util.Objects;

public class Sphere implements Geometry {
    Point3D center;
    double radius;

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

    public Vector getNormal(Point3D point) {
        return null;
    }

    @Override
    public String toString() {
        return "center=" + center +
                ", radius=" + radius;
    }
}