package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Cylinder class
 *
 * @author Yishaya Zobel
 */
class CylinderTest {


    @Test
    void testGetNormal() {
        Vector v = new Vector(0,0,1);
        Ray r = new Ray(Point3D.ZERO, v);
        Cylinder cylinder = new Cylinder(r, 1, 2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the getNormal() function on Tube works properly for points on the cylinder.
        Point3D p1 = new Point3D(0,1,1);
        assertTrue(cylinder.getNormal(p1).equals(new Vector(0,1,0)), "Tube.getNormal() gives wrong normal.");

        // =============== Boundary Values Tests ==================
        // TC11: Test that the getNormal() function on Tube works properly for points on side 1
        Point3D p2 = new Point3D(0,1,0);
        assertTrue(cylinder.getNormal(p2).equals(new Vector(0,0,-1)), "Tube.getNormal() gives wrong normal.");

        // TC12: Test that the getNormal() function on Tube works properly for points on side 1
        Point3D p3 = new Point3D(0,1,2);
        assertTrue(cylinder.getNormal(p3).equals(new Vector(0,0,1)), "Tube.getNormal() gives wrong normal.");

    }
}