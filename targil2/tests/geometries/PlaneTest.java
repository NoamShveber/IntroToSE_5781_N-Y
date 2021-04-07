package geometries;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    @Test
    void testGetNormal() {

        Point3D p0 = new Point3D(0,1,0);
        Point3D p1 = new Point3D(1,0, 0);
        Point3D p2 = new Point3D(0,0,1);
        Plane pl = new Plane(p0, p1, p2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the getNormal() function on plane works properly.
        assertEquals(1, Math.abs(pl.getNormal(p1).dotProduct(new Vector(1,1,1).normalize())), 0.00001, "Plane.getNormal() gives wrong normal.");
    }
}