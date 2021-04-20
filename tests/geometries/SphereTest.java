package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Sphere class
 *
 * @author Yishaya Zobel & Noam Schwber
 */
class SphereTest {

    @Test
    void testGetNormal() {
        Point3D o = Point3D.ZERO;
        Sphere sp = new Sphere(o, 1);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the getNormal() function on Sphere works properly.
        Point3D p1 = new Point3D(0,0,1);
        // Point3D p2 = new Point3D(0,0,1);
        // Point3D p3 = new Point3D(0,0,2);

        assertTrue(sp.getNormal(p1).equals(new Vector(0,0,1)), "Sphere.getNormal() gives wrong normal.");
        // assertTrue(sp.getNormal(p).equals());
        // assertTrue(sp.getNormal(p).equals());
    }

    @Test
    void findIntsersections() {
        Sphere sphere = new Sphere(new Point3D(-3,0,0), 1);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the sphere twice.
        Ray ray = new Ray(new Point3D(3,0,0),new Vector(-1,0,0));
        List<Point3D> expRes = List.of(new Point3D(-4,0,0), new Point3D(-2,0,0));
        List<Point3D> res = sphere.findIntsersections(ray);
        assertEquals(res.size(), 2, "Ray intersects sphere twice EP doesn't work.");
        assertEquals(res, expRes, "Ray intersects sphere twice EP doesn't work.");

        // TC02: Ray does not intersect the sphere.
        ray = new Ray(new Point3D(3,0,0),new Vector(-1,0,1));
        assertNull(sphere.findIntsersections(ray), "Ray that doesn't intersect sphere EP doesn't work.");

        // TC03: Ray intersects the sphere from inside the sphere.
        ray = new Ray(new Point3D(-3,0,0),new Vector(-1,0,0));
        assertEquals(sphere.findIntsersections(ray).size(), 1, "Ray from inside sphere EP doesn't work.");

        // TC04: Ray goes to the opposite direction of the sphere (then 0 intersection points).
        ray = new Ray(new Point3D(3,0,0),new Vector(1,0,0));
        assertNull(sphere.findIntsersections(ray), "Ray in opposite dir of sphere EP doesn't work.");

        // =============== Boundary Values Tests ==================
        // TC05: Ray tangent to the sphere.
        ray = new Ray(new Point3D(0,0,1),new Vector(-1,0,0));
        assertNull(sphere.findIntsersections(ray), "Ray tangent to the sphere BVA doesn't work.");

        // TC06: Ray p0's is on the sphere.
        ray = new Ray(new Point3D(-4,0,0),new Vector(1,0,0));
        expRes = List.of(new Point3D(-2,0,0));
        assertEquals(sphere.findIntsersections(ray).size(), 1, "Ray p0's is on the sphere BVA doesn't work.");
        assertEquals(sphere.findIntsersections(ray), expRes, "Ray p0's is on the sphere BVA doesn't work.");


        // TC07: Ray is perpendicular on the u-vector of the sphere.
        ray = new Ray(new Point3D(-2,0,0),new Vector(0,0,1));
        assertNull(sphere.findIntsersections(ray), "Ray is perpendicular on the u-vector of the sphere BVA doesn't work.");
    }
}