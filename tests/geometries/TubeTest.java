package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Tube class
 *
 * @author Yishaya Zobel & Noam Shveber
 */
class TubeTest {
    Vector v = new Vector(0,0,1);
    Ray r = new Ray(Point3D.ZERO, v);
    Tube tube = new Tube(r, 1);

    @Test
    void testGetNormal() {


        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the getNormal() function on Tube works properly for points on positive direction.
        Point3D p1 = new Point3D(0,1,0.5);
        assertEquals(new Vector(0, 1, 0), tube.getNormal(p1), "Tube.getNormal() gives wrong normal.");

        // TC02: Test that the getNormal() function on Tube works properly for points on negative direction.
        Point3D p2 = new Point3D(0,1,-0.5);
        assertEquals(new Vector(0, 1, 0), tube.getNormal(p2), "Tube.getNormal() gives wrong normal.");


        // =============== Boundary Values Tests ==================
        // TC11: Test that the getNormal() function on Tube works properly for points on middle circle
        Point3D p3 = new Point3D(0,1,0);
        assertEquals(new Vector(0, 1, 0), tube.getNormal(p2), "Tube.getNormal() gives wrong normal.");

    }

    @Test
    void findGeoIntersections() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: A single intersection point from outside
        List<GeoPoint> lst = List.of(new GeoPoint(tube, new Point3D(1,0,1)));
        Ray ray = new Ray(new Point3D(1, 2, 0), new Vector(0, -2, 1));
        assertEquals(lst, tube.findGeoIntersections(ray),  "A single intersection point from outside");

        // TC02: No intersection points.
        ray = new Ray(new Point3D(1, 2, 0), new Vector(2, -2, 1));
        assertNull(tube.findGeoIntersections(ray), "No intersection points.");

        // TC03: Two intersection points
        lst = List.of(new GeoPoint(tube, new Point3D(0,1,0.5)), new GeoPoint(tube, new Point3D(0,-1,1.5)));
        ray = new Ray(new Point3D(0, 2, 0), new Vector(0, -2, 1));
        assertEquals(lst, tube.findGeoIntersections(ray),  "Two intersection points.");



        // =============== Boundary Values Tests ==================
        // TC11: Ray throughout the mid point.
        lst = List.of(new GeoPoint(tube, new Point3D(0,1,0)), new GeoPoint(tube, new Point3D(0,-1,0)));
        ray = new Ray(new Point3D(0, 2, 0), new Vector(0, -2, 0));
        assertEquals(lst, tube.findGeoIntersections(ray),  "Ray throughout the mid point.");

        // TC12: Ray from inside to up. (0 intersection points)
        ray = new Ray(new Point3D(0, 0.4, 0), new Vector(0, 0, 1));
        assertNull(tube.findGeoIntersections(ray),  "Ray from inside to up. (0 intersection points");
    }
}