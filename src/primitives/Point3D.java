package primitives;

/**
 * A class to represent a point in 3D space.
 */
public class Point3D {
    /**
     * A constant static ZERO point (0, 0, 0).
     */
    public static final Point3D ZERO = new Point3D(
            new Coordinate(0),
            new Coordinate(0),
            new Coordinate(0));

    /**
     * A coordinate of the point.
     */
    final Coordinate x, y, z;

    /**
     * Point3D constructor based on 3 double numbers.
     *
     * @param x double value of X coordinate of the point.
     * @param y double value of Y coordinate of the point.
     * @param z double value of Z coordinate of the point.
     */
    public Point3D(double x, double y, double z) {
        this.x = new Coordinate(x);
        this.y = new Coordinate(y);
        this.z = new Coordinate(z);
    }


    /**
     * @return The X coordinate.
     */
    public Coordinate getX() {
        return x;
    }

    /**
     * @return The Y coordinate.
     */
    public Coordinate getY() {
        return y;
    }

    /**
     * @return The Z coordinate.
     */
    public Coordinate getZ() {
        return z;
    }

    /**
     * @return The value of the X coordinate.
     */
    public double getCx() {
        return x.coord;
    }

    /**
     * @return The value of the Y coordinate.
     */
    public double getCy() {
        return y.coord;
    }

    /**
     * @return The value of the Z coordinate.
     */
    public double getCz() {
        return z.coord;
    }

    /**
     * Point3D constructor based on 3 coordinates.
     *
     * @param x X coordinate of the point.
     * @param y Y coordinate of the point.
     * @param z Z coordinate of the point.
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    /**
     * Adds a vector to the point and returns it.
     * @param vec The vector to add to the point.
     * @return Returns the point plus the given vector.
     */
    public Point3D add(Vector vec) {
        return new Point3D(new Coordinate(x.coord + vec.head.x.coord),
                new Coordinate(y.coord + vec.head.y.coord),
                new Coordinate(z.coord + vec.head.z.coord));
    }

    /**
     * Creates a vector as a result of subtracting the instance and a given point.
     * @param point The point to subtract from the instance's point.
     * @return A vector from the point to the instance point. (A - B = B -> A)
     */
    public Vector subtract(Point3D point) {
        return new Vector(new Point3D(
                new Coordinate(x.coord - point.x.coord),
                new Coordinate(y.coord - point.y.coord),
                new Coordinate(z.coord - point.z.coord)));
    }


    /**
     * Calculates the squared distance between the instance's point and a given point.
     * @param point The point to calculate the distance from.
     * @return The squared distance between the points.
     */
    public double distanceSquared(Point3D point) {
        return (point.x.coord - x.coord) * (point.x.coord - x.coord) +
                (point.y.coord - y.coord) * (point.y.coord - y.coord) +
                (point.z.coord - z.coord) * (point.z.coord - z.coord);

    }

    /**
     * Calculates the distance between the instance's point and a given point.
     * @param point The point to calculate the distance from.
     * @return The distance between the points.
     */
    public double distance(Point3D point) {
        return Math.sqrt(distanceSquared(point));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point3D other)) return false;
        return this.x.equals(other.x) && this.y.equals(other.y) && this.z.equals(other.z);
    }


    @Override
    public String toString() {
        return "x=" + x +
                ", y=" + y +
                ", z=" + z;
    }
}
