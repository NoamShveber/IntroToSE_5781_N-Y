package primitives;


import geometries.*;
import geometries.Intersectable.GeoPoint;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A class to test the Ray class.
 */
class RayTest {
    /**
     * Tests the findClosestPoint function in Ray class.
     */
    @Test
    void findClosestPoint() {
        Ray ray = new Ray(Point3D.ZERO, new Vector(1,0,0));
        Point3D a = new Point3D(8, 0, 0),
                b = new Point3D(2, 0, 0),
                c = new Point3D(5, 0, 0);

        // ============ Equivalence Partitions Tests ==============
        // TC01: The middle point is the closest
        List<Point3D> lst = List.of(a, b, c);

        assertEquals(b, ray.findClosestPoint(lst), "The middle point is the closest test failed");

        // =============== Boundary Values Tests ==================
        // TC11: Empty set
        lst = List.of();

        assertNull(ray.findClosestPoint(lst), "Empty set test failed");

        // TC12: First point is closest
        lst = List.of(b, c, a);

        assertEquals(b, ray.findClosestPoint(lst), "First point is the closest test failed");

        // TC13: Last point is the closest
        lst = List.of(c, a, b);

        assertEquals(b, ray.findClosestPoint(lst), "Last point is the closest test failed");



    }

    /**
     * Tests the findClosestGeoPoint function in Ray class.
     */
    @Test
    void findClosestGeoPoint() {
        Ray ray = new Ray(Point3D.ZERO, new Vector(1,0,0));
        Point3D a = new Point3D(8, 0, 0),
                b = new Point3D(2, 0, 0),
                c = new Point3D(5, 0, 0);

        Sphere sp = new Sphere(2, Point3D.ZERO);
        Triangle tr = new Triangle(a, Point3D.ZERO, new Point3D(0,8,0));


        GeoPoint gA = new GeoPoint(sp, a),
                gB = new GeoPoint(tr, b),
                gC = new GeoPoint(sp, c);

        // ============ Equivalence Partitions Tests ==============
        // TC01: The middle point is the closest
        List<GeoPoint> lst = List.of(gA, gB, gC);

        assertEquals(gB, ray.findClosestGeoPoint(lst), "The middle point is the closest test failed");

        // TC02: Creating a new geometry, not using the existing one.
        lst = List.of(gA, gB, gC);
        Triangle tryTr = new Triangle(a, Point3D.ZERO, new Point3D(0,8,0));

        assertEquals(new GeoPoint(tryTr, b), ray.findClosestGeoPoint(lst), "The middle point is the closest test failed");

        // =============== Boundary Values Tests ==================
        // TC11: Empty set
        lst = List.of();

        assertNull(ray.findClosestGeoPoint(lst), "Empty set test failed");

        // TC12: First point is closest
        lst = List.of(gB, gC, gA);

        assertEquals(gB, ray.findClosestGeoPoint(lst), "First point is the closest test failed");

        // TC13: Last point is the closest
        lst = List.of(gC, gA, gB);

        assertEquals(gB, ray.findClosestGeoPoint(lst), "Last point is the closest test failed");


    }
}