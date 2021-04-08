package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util.*;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Tube class
 *
 * @author Yishaya Zobel
 */
class TubeTest {

    @Test
    void testGetNormal() {
        Vector v = new Vector(0,0,1);
        Ray r = new Ray(Point3D.ZERO, v);
        Tube tube = new Tube(r, 1);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the getNormal() function on Tube works properly for points on positive direction.
        Point3D p1 = new Point3D(0,1,0.5);
        assertTrue(tube.getNormal(p1).equals(new Vector(0,1,0)), "Tube.getNormal() gives wrong normal.");

        // TC02: Test that the getNormal() function on Tube works properly for points on negative direction.
        Point3D p2 = new Point3D(0,1,-0.5);
        assertTrue(tube.getNormal(p2).equals(new Vector(0,1,0)), "Tube.getNormal() gives wrong normal.");


        // =============== Boundary Values Tests ==================
        // TC11: Test that the getNormal() function on Tube works properly for points on middle circle
        Point3D p3 = new Point3D(0,1,0);
        assertTrue(tube.getNormal(p2).equals(new Vector(0,1,0)), "Tube.getNormal() gives wrong normal.");

    }
}