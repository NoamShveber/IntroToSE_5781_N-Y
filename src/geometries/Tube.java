package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * Tube class represents a tube that continues infinitely in 3D space
 */
public class Tube extends Geometry {
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
     * @return the normal to the tube at point
     */
    public Vector getNormal(Point3D point) {

        Point3D o;

        //if point is on the middle circle
        if(isZero(point.subtract(axisRay.getP0()).dotProduct(axisRay.getDir()))) {
            o = axisRay.getP0();
        }

        else {
            double t = axisRay.getDir().dotProduct(point.subtract(axisRay.getP0()));
            o = axisRay.getP0().add(axisRay.getDir().scale(t));
        }
        return (point.subtract(o)).normalize();
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        var intersections = findIntersections(ray);
        if (intersections == null) return null;

        List<GeoPoint> lst = new ArrayList<>();
        for (Point3D point: findIntersections(ray)) {
            lst.add(new GeoPoint(this, point));
        }

        return lst;
    }

    @Override
    public String toString() {
        return "axisRay=" + axisRay +
                ", radius=" + radius;
    }
}
