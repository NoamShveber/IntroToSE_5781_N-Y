package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Cylinder class
 *
 * @author Yishaya Zobel
 */
class CylinderTest {
    /**
     * The ray that is used the test.
     */
    Ray r = new Ray(Point3D.ZERO, new Vector(0,0,1));

    /**
     * The cylinder that is used in the tests.
     */
    Cylinder cylinder = new Cylinder(r, 1, 1);


    /**
     * Tests the getNormal function in cylinder.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the getNormal() function on Cylinder works properly.
        Point3D p1 = new Point3D(0,1,0.5);
        assertEquals(new Vector(0, 1, 0), cylinder.getNormal(p1), "Cylinder.getNormal() gives wrong normal.");

        // TC02: Test that the getNormal() function on Tube works properly for points on side 1
        Point3D p2 = new Point3D(0,0.5,0);
        assertEquals(new Vector(0, 0, -1), cylinder.getNormal(p2), "Cylinder.getNormal() gives wrong normal.");

        // TC03: Test that the getNormal() function on Tube works properly for points on side 2
        Point3D p3 = new Point3D(0,0.5,1);
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(p3), "Cylinder.getNormal() gives wrong normal.");

        // =============== Boundary Values Tests ==================
        // TC11: Test that the getNormal() function on Tube works properly for points on side 1 rim
        Point3D p4 = new Point3D(0,1,0);
        assertEquals(new Vector(0, 0, -1), cylinder.getNormal(p2), "Cylinder.getNormal() gives wrong normal.");

        // TC12: Test that the getNormal() function on Tube works properly for points on side 2 rim
        Point3D p5 = new Point3D(0,1,1);
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(p3), "Cylinder.getNormal() gives wrong normal.");
    }

    /**
     * Tests the findGeoIntersections function in cylinder.
     */
    @Test
    void findGeoIntersections() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Two intersection points.
        List<GeoPoint> lst = List.of(new GeoPoint(cylinder, new Point3D(0, 1,0.25)),
                                    new GeoPoint(cylinder, new Point3D(0, -1,0.5)));
        Ray ray = new Ray(new Point3D(0,3,0), new Vector(0, -2,0.25));

        assertEquals(lst, cylinder.findGeoIntersections(ray), "Two intersection points.");

        // TC02: One intersection point.
        lst = List.of(new GeoPoint(cylinder, new Point3D(1, 0,0.4)));
        ray = new Ray(new Point3D(1,2,0), new Vector(0, -2,0.4));

        assertEquals(lst, cylinder.findGeoIntersections(ray), "One intersection points.");

        // =============== Boundary Values Tests ==================
        //TC11: Intersection on the edge.
        lst = List.of(new GeoPoint(cylinder, new Point3D(1, 0,1)));
        ray = new Ray(new Point3D(1,2,0), new Vector(0, -2,1));

        assertEquals(lst, cylinder.findGeoIntersections(ray), "Intersection on the edge.");

        // TC12: Ray from inside to up. (1 intersection points)
        lst = List.of(new GeoPoint(cylinder, new Point3D(0, 0.4,1)));
        ray = new Ray(new Point3D(0, 0.4, 0), new Vector(0, 0, 1));
        assertEquals(lst, cylinder.findGeoIntersections(ray),  "Ray from inside to up. (1 intersection points");

        // TC13: Ray from outside intersects a point and circle. (2 intersection points)
        lst = List.of(new GeoPoint(cylinder, new Point3D(0, 1,0.5)),
                new GeoPoint(cylinder, new Point3D(0, 0,1)));
        ray = new Ray(new Point3D(0,2,0), new Vector(0, -2,1));

        assertEquals(lst, cylinder.findGeoIntersections(ray), "Ray from outside intersects a point and circle. (2 intersection points)");

        // TC13: Ray intersects circle (edge) twice.
        lst = List.of(new GeoPoint(cylinder, new Point3D(0, 1,1)),
                new GeoPoint(cylinder, new Point3D(0, -1,1)));
        ray = new Ray(new Point3D(0,2,1), new Vector(0, -2,0));

        assertEquals(lst, cylinder.findGeoIntersections(ray), "Ray from outside intersects a point and circle. (2 intersection points)");

        // TC13: Ray starts below cylinder. (2 intersection points).
        lst = List.of(new GeoPoint(cylinder, new Point3D(0,0.4,0)),
                new GeoPoint(cylinder, new Point3D(0, 0.4,1)));
        ray = new Ray(new Point3D(0,0.4,-2), new Vector(0, 0,1));

        assertEquals(lst, cylinder.findGeoIntersections(ray), "Ray starts below cylinder. (2 intersection points).");
    }
}