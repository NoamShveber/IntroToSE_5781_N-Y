package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to represent a pyramid, with 4 triangles and a square (polygon).
 */
public class Pyramid extends Geometry {
    /**
     * A triangle side of the pyramid.
     */
    Triangle t1, t2, t3, t4;

    /**
     * The square base of the pyramid.
     */
    Polygon p;

    /**
     * Constructs an instance of pyramid with given 4 base points on the same plane<br>
     * and the tip point, in that order.
     * @param b1 First point of plane.
     * @param b2 Second point of plane.
     * @param b3 Third point of plane.
     * @param b4 Fourth point of plane.
     * @param tip The tip point of the pyramid.
     */
    public Pyramid(Point3D b1, Point3D b2, Point3D b3, Point3D b4, Point3D tip) {
        t1 = new Triangle(b1, b2, tip);
        t2 = new Triangle(b2, b3, tip);
        t3 = new Triangle(b3, b4, tip);
        t4 = new Triangle(b4, b1, tip);
        p = new Polygon(b1, b2, b3, b4);
    }

    @Override
    public Geometry setEmission(Color emission) {
        t1.setEmission(emission);
        t2.setEmission(emission);
        t3.setEmission(emission);
        t4.setEmission(emission);
        p.setEmission(emission);
        return super.setEmission(emission);
    }

    @Override
    public Geometry setMaterial(Material material) {
        t1.setMaterial(material);
        t2.setMaterial(material);
        t3.setMaterial(material);
        t4.setMaterial(material);
        p.setMaterial(material);
        return super.setMaterial(material);
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        List<GeoPoint> lst = new ArrayList<>();

        List<GeoPoint> lst1 = t1.findGeoIntersections(ray, maxDistance);
        if (lst1 != null) lst.addAll(lst1);
        List<GeoPoint> lst2 = t2.findGeoIntersections(ray, maxDistance);
        if (lst2 != null) lst.addAll(lst2);
        List<GeoPoint> lst3 = t3.findGeoIntersections(ray, maxDistance);
        if (lst3 != null) lst.addAll(lst3);
        List<GeoPoint> lst4 = t4.findGeoIntersections(ray, maxDistance);
        if (lst4 != null) lst.addAll(lst4);
        List<GeoPoint> lst5 = p.findGeoIntersections(ray, maxDistance);
        if (lst5 != null) lst.addAll(lst5);

        if (lst.size() == 0) return null;

        return lst;
    }

    @Override
    public Vector getNormal(Point3D point) {
        if (t1.isPointOnPolygon(point)) return t1.getNormal(point);
        if (t2.isPointOnPolygon(point)) return t2.getNormal(point);
        if (t3.isPointOnPolygon(point)) return t3.getNormal(point);
        if (t4.isPointOnPolygon(point)) return t4.getNormal(point);
        if (p.isPointOnPolygon(point)) return p.getNormal(point);
        return null;
    }
}
