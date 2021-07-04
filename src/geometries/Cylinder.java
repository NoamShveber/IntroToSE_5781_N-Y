package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Cylinder class represents a cylinder (A finite tube) in 3D space
 */
public class Cylinder extends Tube implements Boundable {
    /**
     * The height of the cylinder
     */
    private double height;

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

    /**
     * @param height The number to set as the height of the cylinder.
     * @return The current instance (Builder pattern).
     */
    public Cylinder setHeight(double height) {
        this.height = height;
        return this;
    }

    /**
     * @return The height of the cylinder.
     */
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

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        List<GeoPoint> res = new ArrayList<>();

        List<GeoPoint> lst = super.findGeoIntersections(ray, maxDistance);
        if (lst != null)
            for (GeoPoint point : lst) {
                double distance = alignZero(point.point.subtract(axisRay.getP0()).dotProduct(axisRay.getDir()));
                if (distance > 0 && distance <= height)
                    res.add(new GeoPoint(this, point.point));
            }



        // Checking intersection on edges.
        Point3D p0 = axisRay.getP0();
        Vector v = axisRay.getDir();
        lst = new Plane(p0, v).findGeoIntersections(ray, maxDistance);
        if (lst != null)
            for (GeoPoint point: lst)
                if (point.point.distance(p0) < radius)
                    res.add(new GeoPoint(this, point.point));

        Point3D topCenter = p0.add(v.scale(height));
        lst = new Plane(topCenter, v).findGeoIntersections(ray, maxDistance);
        if (lst != null)
            for (GeoPoint point: lst)
                if (point.point.distance(topCenter) < radius)
                    res.add(new GeoPoint(this, point.point));



        return res;
    }

    @Override
    public AxisAlignedBoundingBox getBoundingBox() {
        double minX, minY, minZ, maxX, maxY, maxZ;

        Point3D o1 = axisRay.getP0();//middle of first end
        Point3D o2 = o1.add(axisRay.getDir().scale(height));//middle of second end


        //middle point of side circles plus a radius offset is a good approximation for the bounding box
        if (o1.getCx() > o2.getCx()) {
            maxX = o1.getCx() + radius;
            minX = o2.getCx() - radius;
        }

        else {
            maxX = o2.getCx() + radius;
            minX = o1.getCx() - radius;
        }

        if (o1.getCy() > o2.getCy()) {
            maxY = o1.getCy() + radius;
            minY = o2.getCy() - radius;
        }

        else {
            maxY = o2.getCy() + radius;
            minY = o1.getCy() - radius;
        }

        if (o1.getCz() > o2.getCz()) {
            maxZ = o1.getCz() + radius;
            minZ = o2.getCz() - radius;
        }

        else {
            maxZ = o2.getCz() + radius;
            minZ = o1.getCz() - radius;
        }

        AxisAlignedBoundingBox res = new AxisAlignedBoundingBox(minX,minY,minZ,maxX,maxY,maxZ);
        res.addToContains(this);

        return res;
    }
}
