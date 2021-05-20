package geometries;

import primitives.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * basic interface for intersectable geometries.
 */
public interface Intersectable {
    /**
     * @param ray The ray to check intersection points with.
     * @return List of intersection points between the ray and the intersectable geometries.
     */

    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
    }


    /**
     * @param ray The ray to check intersection points with.
     * @return List of geometric intersection points between the ray and the intersectable geometries.
     */
    default List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * @param ray         The ray to check intersection points with.
     * @param maxDistance The maximum distance to check intersection with.
     * @return List of geometric intersection points between the ray and the intersectable geometries
     * that are not further than the maximum distance.
     */
    List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);



    /**
     * A PDS to present a point with its geometry.
     */

    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.getClass() == geoPoint.geometry.getClass() && Objects.equals(point, geoPoint.point);
        }

        /**
         * A constructor for the GeoPoint class
         * @param geometry The geometry of the GeoPoint.
         * @param point The point of the GeoPoint.
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }
    }
}
