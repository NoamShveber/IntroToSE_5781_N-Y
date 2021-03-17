package geometries;

import primitives.Ray;

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
}
