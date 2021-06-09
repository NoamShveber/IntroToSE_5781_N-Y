package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GeometriesTest {

    @Test
    void findIntsersections() {
        Ray ray = new Ray(new Point3D(1,0,0), new Vector(-1,0,0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Some of the geometries intersect.
        Geometries geometries = new Geometries(new Sphere(1, new Point3D(0,0.5, 0.5)) , //2 intersection points
                                                new Polygon(new Point3D(-0.5, -0.5, 0), new Point3D(0, 1, 0), new Point3D(1, 0, 0)),
                                                new Plane(new Point3D(-0.5, -0.5, 2), new Point3D(0, 1, 2), new Point3D(1, 0, 2)));

        assertEquals(geometries.findIntersections(ray).size(), 2, "Some of the geometries intersect doesn't work.");

        // =============== Boundary Values Tests ==================
        // TC11: Empty geometries set.
        geometries = new Geometries();
        assertNull(geometries.findIntersections(ray), "Empty geometries set doesn't work.");

        // TC12: One geometry in the set intersects.
        geometries = new Geometries(new Sphere(1, new Point3D(0,0.5, 0.5)),
                new Polygon(new Point3D(-0.5, -0.5, -4), new Point3D(0, 1, -4), new Point3D(1, 0, -4)),
                new Plane(new Point3D(-0.5, -0.5, 4), new Point3D(0, 1, 4), new Point3D(1, 0, 4)));
        assertEquals(geometries.findIntersections(ray).size(), 2, "One geometry in the set intersects doesn't work.");

        // TC13: None of the geometries intersects.
        geometries = new Geometries(new Sphere(1, new Point3D(0,0.5, 4)),
                new Polygon(new Point3D(-0.5, -0.5, -2), new Point3D(0, 1, -2), new Point3D(1, 0, -2)),
                new Plane(new Point3D(-0.5, -0.5, 2), new Point3D(0, 1, 2), new Point3D(1, 0, 2)));
        assertNull(geometries.findIntersections(ray), "None of the geometries intersects doesn't work.");

        // TC14: All geometry in the set intersects.
        geometries = new Geometries(new Sphere(1, new Point3D(0,0.5, 0.5)), //2 intersection points
                new Polygon(new Point3D(0.5, 0.5, -1), new Point3D(-1, -1, -1), new Point3D(-0.5, 0.5, 1)),
                new Plane(new Point3D(0.5, 0.5, -1), new Point3D(-1, -1, -1), new Point3D(-0.5, 0.5, 1)));
        assertEquals(geometries.findIntersections(ray).size(), 4, "All geometry in the set intersects doesn't work.");



    }
}