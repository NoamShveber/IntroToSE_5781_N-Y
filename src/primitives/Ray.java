package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * Ray class represents a ray in 3D space.
 * a ray is an infinite line that starts at a point and goes in the direction of a vector
 */
public class Ray {
    /**
     * starting point
     */
    Point3D p0;
    /**
     * direction vector
     */
    Vector dir;

    /**
     * ray constructor with a point and a vector
     *
     * @param p0  ray starting point
     * @param dir ray direction vector
     */
    public Ray(Point3D p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    public Point3D getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Ray)) return false;
        Ray other = (Ray) obj;
        return this.dir.equals(other.dir) && this.p0.equals(other.p0);
    }

    @Override
    public String toString() {
        return "p0=" + p0 +
                ", dir=" + dir;
    }

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
