package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;



/**
 * Ray class represents a ray in 3D space.
 * a ray is an infinite line that starts at a point and goes in the direction of a vector
 */
public class Ray {
    /**
     * A constant for moving a vector, for ensuring a ray doesn't
     * intersect the point’s geometry itself again and again.
     */
    public static final double DELTA = 0.1;

    /**
     * starting point
     */
    Point3D p0;
    /**
     * direction vector
     */
    Vector dir;

    /**
     * Ray constructor with a point and a vector
     *
     * @param p0  ray starting point
     * @param dir ray direction vector
     */
    public Ray(Point3D p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     * Ray constructor to create a vector with a delta difference,
     * to ensure they don’t intersect the point’s geometry itself
     * again and again.
     * @param head The head of the ray (before adding delta).
     * @param direction The direction of the ray.
     * @param normal The normal vector to the geometry.
     */
    public Ray(Point3D head, Vector direction, Vector normal) {
        Vector delta = normal.scale(normal.dotProduct(direction) > 0 ? DELTA : - DELTA);
        this.p0 = head.add(delta);
        this.dir = direction;
    }



    /**
     * @return The starting point of the ray.
     */
    public Point3D getP0() {
        return p0;
    }

    /**
     * @return The direction vector.
     */
    public Vector getDir() {
        return dir;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Ray other)) return false;
        return this.dir.equals(other.dir) && this.p0.equals(other.p0);
    }

    @Override
    public String toString() {
        return "p0=" + p0 +
                ", dir=" + dir;
    }

    /**
     * Calculates the point where starts at p0 and scaled by t.
     * @param t The scalar to scale the direction with.
     * @return The calculated point.
     */
    public Point3D getPoint(double t) {
        return p0.add(dir.scale(t));
    }

    /**
     * @param lst The list of all the points.
     * @return The closest point to p0 in the list.
     */
    public Point3D findClosestPoint(List<Point3D> lst) {
        if (lst == null || lst.size() == 0) return null;

        Point3D closest = lst.get(0);
        double closestDistance = p0.distanceSquared(closest); // To make the calculations more efficient.
        double tmpDist;
        for (Point3D point : lst) {
            tmpDist = p0.distanceSquared(point); // To make the calculations more efficient.
            if (tmpDist < closestDistance) {
                closest = point;
                closestDistance = tmpDist;
            }
        }

        return closest;
    }

    /**
     * @param lst The list of all the geo points.
     * @return The closest point to p0 in the list.
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> lst) {
        if (lst == null || lst.size() == 0) return null;

        GeoPoint closest = lst.get(0);
        double closestDistance = p0.distanceSquared(closest.point); // To make the calculations more efficient.
        double tmpDist;
        for (GeoPoint point : lst) {
            tmpDist = p0.distanceSquared(point.point); // To make the calculations more efficient.
            if (tmpDist < closestDistance) {
                closest = point;
                closestDistance = tmpDist;
            }
        }

        return closest;
    }
}
