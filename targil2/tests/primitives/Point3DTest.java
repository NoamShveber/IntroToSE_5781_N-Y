package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point3D class
 *
 * @author Yishaya Zobel
 */
class Point3DTest {
    @Test
    void testAdd() {
        Point3D p1 = new Point3D(1, 2, 3);


        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that point plus vector gives right answer
        assertEquals(p1.add(new Vector(-1, -2, -3)), Point3D.ZERO, "Point3D.add() gave wrong answer");
    }

    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that point subtract another point gives right answer
        Point3D p1 = new Point3D(1,2,3);
        Point3D p2 = new Point3D(4,5,6);
        assertEquals((p1.subtract(p2)).equals(new Vector(-3, -3, -3)), true, "subtract() gives wrong answer.");
    }

    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the squared distance between two points is calculated correctly.
        Point3D p = new Point3D(1,1,1);
        assertEquals(3, p.distanceSquared(Point3D.ZERO), 0.00001, "distanceSquared() gives wrong distance.");
    }

    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the distance between two points is calculated correctly.
        Point3D p = new Point3D(1,1,1);
        assertEquals(Math.sqrt(3), p.distance(Point3D.ZERO), 0.00001, "distance() gives wrong distance.");
    }
}