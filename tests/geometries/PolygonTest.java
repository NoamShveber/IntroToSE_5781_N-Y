package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Polygon class
 *
 * @author Yishaya Zobel
 */
class PolygonTest {

    @Test
    void testGetNormal() {
        Point3D p0 = new Point3D(0, 1, 0);
        Point3D p1 = new Point3D(1, 0, 0);
        Point3D p2 = new Point3D(0, 0, 1);
        Polygon po = new Polygon(p0, p1, p2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the getNormal() function on Polygon works properly.
        assertEquals(1, Math.abs(po.getNormal(p1).dotProduct(new Vector(1,1,1).normalize())), 0.00001,
                "Polygon.getNormal() gives wrong normal.");

    }

    @Test
    void findIntsersections() {
        Polygon polygon = new Polygon(new Point3D(-0.5,-0.5,0), new Point3D(0,1,0), new Point3D(1,0,0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects inside polygon.
        Ray ray = new Ray(new Point3D(0.25,0.25,1),new Vector(0.25,0,-1));
        List<Point3D> expRes = List.of(new Point3D(0.5,0.25,0));
        List<Point3D> res = polygon.findIntsersections(ray);
        assertEquals(res.size(), 1, "Ray intersects inside polygon EP doesn't work.");
        assertEquals(res, expRes, "Ray intersects inside polygon EP doesn't work.");


        // TC02: Ray outside polygon against vertex.
        ray = new Ray(new Point3D(0.25,0.25,1),new Vector(1.5,-0.5,-1));
        assertNull(polygon.findIntsersections(ray), "Ray outside polygon against vertex EP doesn't work.");

        // TC03: Ray outside polygon against edge.
        ray = new Ray(new Point3D(0.25,0.25,1),new Vector(0.75,0.75,-1));
        assertNull(polygon.findIntsersections(ray), "Ray outside polygon against edge EP doesn't work.");

        // =============== Boundary Values Tests ==================
        // TC11: Ray intersects on vertex of polygon.
        ray = new Ray(new Point3D(0.25,0.25,1),new Vector(-0.25,0.75,-1));
        assertNull(polygon.findIntsersections(ray), "Ray intersects on vertex of polygon BVA doesn't work.");

        // TC12: Ray intersects on edge of polygon.
        ray = new Ray(new Point3D(0.25,0.25,1),new Vector(0.25,0.25,-1));
        assertNull(polygon.findIntsersections(ray), "Ray intersects on edge of polygon BVA doesn't work.");

        // TC13: Ray intersects on edge's continuation of polygon.
        ray = new Ray(new Point3D(0.25,0.25,1),new Vector(-1.25,-2.25,-1));
        assertNull(polygon.findIntsersections(ray), "Ray intersects on edge's continuation of polygon BVA doesn't work.");
    }
}