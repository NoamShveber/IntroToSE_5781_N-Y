package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * Triangle class represents 2D triangle in 3D space
 */
public class Triangle extends Polygon {
    /**
     * Triangle constructor with 3 points
     * @param p0 point 1
     * @param p1 point 2
     * @param p2 point 3
     */
    public Triangle(Point3D p0, Point3D p1, Point3D p2) {
        super(p0, p1, p2);
    }

    @Override
    public String toString() {
        return super.toString() + " (Triangle)";
    }
}
