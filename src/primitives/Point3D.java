package primitives;

public class Point3D {
    public static final Point3D ZERO = new Point3D(
            new Coordinate(0),
            new Coordinate(0),
            new Coordinate(0));
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

    public Coordinate getX() {
        return x;
    }

    public Coordinate getY() {
        return y;
    }

    public Coordinate getZ() {
        return z;
    }

    public double getCx() {
        return x.coord;
    }

    public double getCy() {
        return y.coord;
    }

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

    public Vector subtract(Point3D point) {
        return new Vector(new Point3D(
                new Coordinate(x.coord - point.x.coord),
                new Coordinate(y.coord - point.y.coord),
                new Coordinate(z.coord - point.z.coord)));
    }

    public double distanceSquared(Point3D point) {
        return (point.x.coord - x.coord) * (point.x.coord - x.coord) +
                (point.y.coord - y.coord) * (point.y.coord - y.coord) +
                (point.z.coord - z.coord) * (point.z.coord - z.coord);

    }

    public double distance(Point3D point) {
        return Math.sqrt(distanceSquared(point));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point3D)) return false;
        Point3D other = (Point3D) obj;
        return this.x.equals(other.x) && this.y.equals(other.y) && this.z.equals(other.z);
    }


    @Override
    public String toString() {
        return "x=" + x +
                ", y=" + y +
                ", z=" + z;
    }
}
