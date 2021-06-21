package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point3D class
 *
 * @author Yishaya Zobel and Noam Shveber
 */
class Point3DTest {
    /**
     * Tests the add function in Point3D class.
     */
    @Test
    void testAdd() {
        Point3D p1 = new Point3D(1, 2, 3);


        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that point plus vector gives right answer
        assertEquals(p1.add(new Vector(-1, -2, -3)), Point3D.ZERO, "Point3D.add() gave wrong answer");
    }

    /**
     * Tests the subtract function in Point3D class.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that point subtract another point gives right answer
        Point3D p1 = new Point3D(1,2,3);
        Point3D p2 = new Point3D(4,5,6);
        assertEquals((p1.subtract(p2)), new Vector(-3, -3, -3), "subtract() gives wrong answer.");
    }

    /**
     * Tests the distanceSquared function in Point3D class.
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the squared distance between two points is calculated correctly.
        Point3D p = new Point3D(1,1,1);
        assertEquals(3, p.distanceSquared(Point3D.ZERO), 0.00001, "distanceSquared() gives wrong distance.");
    }

    /**
     * Tests the distance function in Point3D class.
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the distance between two points is calculated correctly.
        Point3D p = new Point3D(1,1,1);
        assertEquals(Math.sqrt(3), p.distance(Point3D.ZERO), 0.00001, "distance() gives wrong distance.");
    }
}