package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.*;

/**
 * Geometries class implements a list of geometries
 */
public class Geometries implements Intersectable {
    /**
     * A list of geometries. (According to the composite design pattern)
     */
    private List<Intersectable> geometries;

    /**
     * Constructs a new instance with empty list of geometries.
     */
    public Geometries() {
        this.geometries = new ArrayList<>(); // I used array list because of the constant access time.
    }

    /**
     * Constructs a new instance with a collections of geometries.
     * @param geometries The geometries to insert to the new instance.
     */
    public Geometries(Intersectable... geometries) {
        this.geometries = new ArrayList<>(Arrays.asList(geometries));
    }

    /**
     * Add new geometries into the list
     *
     * @param geometries the new geometries to add
     */
    public void add(Intersectable... geometries) {
        this.geometries.addAll(Arrays.asList(geometries));
    }


    /**
     * Finds all the intersection points with geometries in our list
     *
     * @param ray The ray to check intersection points with.
     * @return List of the geometric intersection points
     */
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        ArrayList<GeoPoint> lst = new ArrayList<>();
        for (Intersectable geometry : geometries) {
            var points = geometry.findGeoIntersections(ray, maxDistance);
            if (points != null) {
                lst.addAll(points);
            }
        }

        if (lst.size() == 0) return null;

        return lst;
    }
}
