package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

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
}