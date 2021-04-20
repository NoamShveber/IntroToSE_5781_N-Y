package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 *
 * @author Yishaya Zobel
 */
class VectorTest {

    @Test
    void testAdd() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(0, 3, -2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that add is proper
        assertTrue((v1.add(v2)).equals(new Vector(1,5,1)), "add() gave wrong result");

        // =============== Boundary Values Tests ==================
        // TC11: Test v1 plus Zero Vector = v1
        assertTrue(v1.add(Vector.ZERO).equals(v1), "add() gave wrong result");


    }

    @Test
    void testSubtract() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(0, 3, -2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that add is proper
        assertTrue((v1.subtract(v2)).equals(new Vector(1,-1,5)), "subtract() gave wrong result");

        // =============== Boundary Values Tests ==================
        // TC11: Test v1 minus Zero Vector = v1
        assertTrue(v1.subtract(Vector.ZERO).equals(v1), "subtract() gave wrong result");
    }

    @Test
    void testScale() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the scaled vector has a scaled length
        assertEquals(3 * v1.length(), v1.scale(3).length(), 0.00001, "scale() gave wrong result");

        // =============== Boundary Values Tests ==================
        // TC11: Test that scaling a vector by 0 throws an exception
        assertThrows(
                IllegalArgumentException.class,() -> v1.scale(0),
                "scale() by 0 gave wrong result");

    }

    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals( v1.length() * v2.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue( isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue( isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(
                IllegalArgumentException.class, () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");
        // try {
        //     v1.crossProduct(v2);
        //     fail("crossProduct() for parallel vectors does not throw an exception");
        // } catch (Exception e) {}
    }

    @Test
    void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that dot product return value is proper
        assertEquals(-28, v1.dotProduct(v2), 0.00001, "ERROR: dotProduct() wrong value");

        // TC02: test that orthogonal vectors return 0 on dot product
        assertTrue(isZero(v1.dotProduct(v3)), "ERROR: dotProduct() for orthogonal vectors is not zero");
    }

    @Test
    void testLengthSquared() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that lengthSquared value is proper
        assertEquals (14, v1.lengthSquared(), 0.00001, "ERROR: lengthSquared() gave wrong value");

    }

    @Test
    void testLength() {
        Vector v1 = new Vector(0, 3, 4);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that length value is proper
        assertEquals (5, v1.length(), 0.00001, "ERROR: length() gave wrong value");
    }

    @Test
    void testNormalize() {
        // test vector normalization vs vector length and cross-product
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that normalize doesn't create a new vector
        assertTrue(vCopy == vCopyNormalize, "ERROR: normalize() function creates a new vector");

        // TC02: Test that normalize makes the vector length 1
        assertEquals(1, vCopyNormalize.length(), 0.0001, "ERROR: normalize() result is not a unit vector");


    }

    @Test
    void testNormalized() {
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalized();

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that normalized creates a new vector
        assertTrue(v != u, "ERROR: normalized() function does not create a new vector");

        // TC02: Test that normalized creates a new vector with length 1
        assertEquals(1, u.length(), 0.0001, "ERROR: normalized() result is not a unit vector");

    }
}