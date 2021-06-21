package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;
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

    /**
     * @return The axis ray of the tube.
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * @return The radius of the tube.
     */
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
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        Vector dir = ray.getDir();
        Vector v = axisRay.getDir();
        double dirV = dir.dotProduct(v);

        if (ray.getP0().equals(axisRay.getP0())) { // In case the ray starts on the p0.
            if (isZero(dirV))
                return List.of(new GeoPoint(this, ray.getPoint(radius)));

            if (dir.equals(v.scale(dir.dotProduct(v))))
                return null;

            return List.of(new GeoPoint(this, ray.getPoint(
                    Math.sqrt(radius * radius / dir.subtract(v.scale(dir.dotProduct(v)))
                    .lengthSquared()))));
        }

        Vector deltaP = ray.getP0().subtract(axisRay.getP0());
        double dpV = deltaP.dotProduct(v);

        double a = 1 - dirV * dirV;
        double b = 2 * (dir.dotProduct(deltaP) - dirV * dpV);
        double c = deltaP.lengthSquared() - dpV * dpV - radius * radius;

        if (isZero(a)) {
            if (isZero(b)) { // If a constant equation.
                return null;
            }
            return List.of(new GeoPoint(this, ray.getPoint(-c / b))); // if it's linear, there's a solution.
        }

        double discriminant = alignZero(b * b - 4 * a * c);

        if (discriminant < 0) // No real solutions.
            return null;

        List<GeoPoint> lst = new ArrayList<>();

        double t1 = alignZero(-(b + Math.sqrt(discriminant)) / (2 * a)); // Positive solution.
        double t2 = alignZero(-(b - Math.sqrt(discriminant)) / (2 * a)); // Negative solution.

        if (t1 > 0 && alignZero(t1 - maxDistance) < 0)
            lst.add(new GeoPoint(this, ray.getPoint(t1)));


        if (t2 > 0 && t1 != t2 && alignZero(t2 - maxDistance) < 0) // If not the same solution.
            lst.add(new GeoPoint(this, ray.getPoint(t2)));

        return lst;
    }

    @Override
    public String toString() {
        return "axisRay=" + axisRay +
                ", radius=" + radius;
    }
}
