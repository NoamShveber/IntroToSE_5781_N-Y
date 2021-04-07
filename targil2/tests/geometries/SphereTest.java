package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

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
}