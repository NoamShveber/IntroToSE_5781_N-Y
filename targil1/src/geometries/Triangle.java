package geometries;

import primitives.Point3D;

public class Triangle extends Polygon {
    public Triangle(Point3D p0, Point3D p1, Point3D p2) {
        super(p0, p1, p2);
    }

    @Override
    public String toString() {
        return super.toString() + " (Triangle)";
    }
}