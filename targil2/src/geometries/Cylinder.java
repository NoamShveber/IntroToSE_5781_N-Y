package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import static java.lang.System.out;
import static primitives.Util.isZero;

/**
 * Cylinder class represents a cylinder in 3D space
 */
public class Cylinder extends Tube {
    /**
     * height of the cylinder
     */
    double height;

    /**
     * Constructor based on Ray, radius and height.
     * @param axisRay ray around which the cylinder is built
     * @param radius cylinder radius
     * @param height cylinder height
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "height=" + height +
                ", " + super.toString();
    }

    /**
     * returns the normal to the cylinder at a point given including both ends
     * @param point the point on the cylinder
     * @return the normal to the cylinder at point
     */
    @Override
    public Vector getNormal(Point3D point) {
        Point3D o1 = axisRay.getP0();//middle of first end
        Point3D o2 = o1.add(axisRay.getDir().scale(height));//middle of second end

        //if point is on the first end of the cylinder
        if(isZero(point.subtract(o1).dotProduct(axisRay.getDir()))) {
            return axisRay.getDir().scale(-1).normalize();//return the opposite direction of the tube
        }

        //if point is on the second end of the cylinder
        else if(isZero(point.subtract(o2).dotProduct(axisRay.getDir()))){
            return axisRay.getDir().scale(1).normalize();//return the direction of the tube
        }

        return super.getNormal(point);
    }
}
