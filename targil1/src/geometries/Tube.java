package geometries;

import primitives.*;

public class Tube implements Geometry {
    Ray axisRay;
    double radius;

    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    public Ray getAxisRay() {
        return axisRay;
    }

    public double getRadius() {
        return radius;
    }

    public Vector getNormal(Point3D point) {
        return null;
    }

    @Override
    public String toString() {
        return "axisRay=" + axisRay +
                ", radius=" + radius;
    }
}
