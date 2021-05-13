package geometries;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class
 *
 * @author Yishaya Zobel
 */
class PlaneTest {
    @Test
    void testGetNormal() {

        Point3D p0 = new Point3D(0,1,0);
        Point3D p1 = new Point3D(1,0, 0);
        Point3D p2 = new Point3D(0,0,1);
        Plane pl = new Plane(p0, p1, p2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the getNormal() function on plane works properly.
        assertEquals(1, Math.abs(pl.getNormal(p1).dotProduct(new Vector(1,1,1).normalize())), 0.00001, "Plane.getNormal() gives wrong normal.");
    }

    @Test
    void findIntsersections() {
        Plane plane = new Plane(new Point3D(-0.5,-0.5,0), new Point3D(1,0,0), new Point3D(0,1,0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the plane.
        Ray ray = new Ray(new Point3D(1,1,1),new Vector(-1,0,-1));
        List<Point3D> expRes = List.of(new Point3D(0,1,0));
        List<Point3D> res = plane.findIntersections(ray);
        assertEquals(res.size(), 1, "Ray intersects the plane EP doesn't work.");
        assertEquals(res, expRes, "Ray intersects the plane EP doesn't work.");

        // TC02: Ray does not intersects the plane.
        ray = new Ray(new Point3D(1,1,1),new Vector(1,1,1));
        assertNull(plane.findIntersections(ray), "Ray does not intersects the plane EP doesn't work.");


        // =============== Boundary Values Tests ==================
        // TC11: Ray is parallel and included in the plane.
        ray = new Ray(new Point3D(0,1,0),new Vector(1,0,0));
        assertNull(plane.findIntersections(ray), "Ray is parallel and included in the plane BVA doesn't work.");

        // TC12: Ray is parallel and not included in the plane.
        ray = new Ray(new Point3D(0,1,1),new Vector(1,0,0));
        assertNull(plane.findIntersections(ray), "Ray is parallel and not included in the plane BVA doesn't work.");

        // TC13: Ray is orthogonal to the plane and before the plane.
        ray = new Ray(new Point3D(0,1,1),new Vector(0,0,-1));
        expRes = List.of(new Point3D(0,1,0));
        res = plane.findIntersections(ray);
        assertEquals(res.size(), 1, "Ray is orthogonal to the plane and before the plane BVA doesn't work.");
        assertEquals(res, expRes, "Ray is orthogonal to the plane and before the plane BVA doesn't work.");

        // TC14: Ray is orthogonal to the plane and on the plane.
        ray = new Ray(new Point3D(0,2,0),new Vector(0,0,-1));
        assertNull(plane.findIntersections(ray), "Ray is orthogonal to the plane and in the plane BVA doesn't work.");

        // TC15: Ray is orthogonal to the plane and after the plane.
        ray = new Ray(new Point3D(0,2,-1),new Vector(0,0,-1));
        assertNull(plane.findIntersections(ray), "Ray is orthogonal to the plane and after the plane BVA doesn't work.");

        // TC16: Ray begins in the same point which appears as the plane's reference point.
        ray = new Ray(plane.getP0(), new Vector(1,1,1));
        assertNull(plane.findIntersections(ray), "Ray begins in the same point which appears as the plane's reference point BVA doesn't work.");
    }
}