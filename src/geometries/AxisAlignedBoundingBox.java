package geometries;

import primitives.Point3D;
import primitives.Ray;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AxisAlignedBoundingBox implements Intersectable, Boundable{
    /**
     * The minimum values of the box on the axis
     */
    private double minX, minY, minZ;


    /**
     * The maximum values of the box on the axis
     */
    private double maxX, maxY, maxZ;


    /**
     * The middle of the box on the axis
     */
    private double midX, midY, midZ;


    /**
     * A list of the contained boundable objects
     */
    List<Boundable> contains;


    /**
     * Create an AABB given furthest axis values
     * @param minX minimum x value
     * @param minY minimum y value
     * @param minZ minimum z value
     * @param maxX maximum x value
     * @param maxY maximum y value
     * @param maxZ maximum z value
     */
    public AxisAlignedBoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;

        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;

        this.midX = (minX + maxX)/2;
        this.midY = (minY + maxY)/2;
        this.midZ = (minZ + maxZ)/2;

        contains = new ArrayList<>();
    }

//    /**
//     * Create an AABB given 2 Points
//     * @param min minimum point
//     * @param max maximum point
//     */
//    public AxisAlignedBoundingBox(ArrayList<Point3D> points,int bob) {
//        this.min = min;
//        this.max = max;
//        this.mid = new Point3D(
//                (this.max.getCx()+ this.min.getCx())/2,
//                (this.max.getCy()+ this.min.getCy())/2,
//                (this.max.getCz()+ this.min.getCz())/2);
//
//        contains = new ArrayList<Boundable>();
//    }

    /**
     * Create an AABB given a list of Boxes to bound
     * @param boxes the list of boxes to bound
     */
    public AxisAlignedBoundingBox(ArrayList<AxisAlignedBoundingBox> boxes) {
        this.maxX = boxes.get(0).getMinX();
        this.maxY = boxes.get(0).getMinY();
        this.maxZ = boxes.get(0).getMinZ();
        this.minX = boxes.get(0).getMaxX();
        this.minY = boxes.get(0).getMaxY();
        this.minZ = boxes.get(0).getMaxZ();

        for(int i=1; i<boxes.size(); i++)
        {
            if(boxes.get(i).getMinX() > maxX) { this.maxX = boxes.get(i).getMinX();}
            if(boxes.get(i).getMinY() > maxY) { this.maxY = boxes.get(i).getMinY();}
            if(boxes.get(i).getMinZ() > maxZ) { this.maxZ = boxes.get(i).getMinZ();}
            if(boxes.get(i).getMaxX() < minX) { this.minX = boxes.get(i).getMaxX();}
            if(boxes.get(i).getMaxY() < minY) { this.minY = boxes.get(i).getMaxY();}
            if(boxes.get(i).getMaxZ() < minZ) { this.minZ = boxes.get(i).getMaxZ();}

        }

        this.midX = (minX + maxX)/2;
        this.midY = (minY + maxY)/2;
        this.midZ = (minZ + maxZ)/2;

        contains = new ArrayList<>();
        contains.addAll(boxes);
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMinZ() {
        return minZ;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public double getMaxZ() {
        return maxZ;
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return null;
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return null;
    }

    @Override
    public AxisAlignedBoundingBox getBoundingBox() {
        return this;//a bounding box bounds itself
    }
}
