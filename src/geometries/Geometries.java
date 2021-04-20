package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.*;

public class Geometries implements Intersectable {
    private List<Intersectable> geometries;

    public Geometries() {
        this.geometries = new ArrayList<Intersectable>(); // I used array list because of the constant access time.
    }

    public Geometries(Intersectable... geometries) {
        this.geometries = new ArrayList<Intersectable>(Arrays.asList(geometries));
    }

    public void add(Intersectable... geometries) {
        this.geometries.addAll(Arrays.asList(geometries));
    }

    public List<Point3D> findIntsersections(Ray ray) {
        ArrayList<Point3D> lst = new ArrayList<Point3D>();
        for (Intersectable geometry: geometries) {
            var tmp = geometry.findIntsersections(ray);
            if (tmp != null)
                lst.addAll(tmp);
        }

        if (lst.size() == 0) return null;

        return lst;
    }
}
