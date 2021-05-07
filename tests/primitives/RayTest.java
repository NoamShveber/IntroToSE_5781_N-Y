package primitives;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

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
}