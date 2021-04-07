package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Tube class represents a tube that continues infinitely in 3D space
 */
public class Tube implements Geometry {
    /**
     * ray around which the cylinder is built
     */
    Ray axisRay;

    /**
     * radius of the tube
     */
    double radius;

    /**
     * Tube constructor using a ray and the radius
     * @param axisRay the axis ray of the tube
     * @param radius the radius of the tube
     */
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

    /**
     * returns the normal to the tube at a point given
     * @param point the point on the tube
     * @return the noraml to the tube at point
     */
    public Vector getNormal(Point3D point) {
        double t = axisRay.getDir().dotProduct(point.subtract(axisRay.getP0()));
        Point3D o = axisRay.getP0().add(axisRay.getDir().scale(t));
        return (point.subtract(o)).normalize();
    }

    @Override
    public String toString() {
        return "axisRay=" + axisRay +
                ", radius=" + radius;
    }
}