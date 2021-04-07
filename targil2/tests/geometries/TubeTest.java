package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util.*;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    @Test
    void testGetNormal() {
        Vector v = new Vector(0,0,1);
        Ray r = new Ray(Point3D.ZERO, v);
        Tube tube = new Tube(r, 1);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the getNormal() function on Tube works properly.
        Point3D p = new Point3D(0,1,0.5);
        assertTrue(tube.getNormal(p).equals(new Vector(0,1,0)), "Tube.getNormal() gives wrong normal.");
    }
}