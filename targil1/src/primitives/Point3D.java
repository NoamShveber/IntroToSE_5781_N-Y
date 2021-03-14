package primitives;

import java.util.Objects;

public class Point3D {
    public static final Point3D ZERO = new Point3D(
            new Coordinate(0),
            new Coordinate(0),
            new Coordinate(0));
    final Coordinate x, y, z;


    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D add(Vector vec) {
        return new Point3D(new Coordinate(x.coord + vec.head.x.coord),
                            new Coordinate(y.coord + vec.head.y.coord),
                            new Coordinate(z.coord + vec.head.z.coord));
    }

    public Vector subtract(Point3D point) {
        return new Vector(new Point3D(
                new Coordinate(x.coord + point.x.coord),
                new Coordinate(y.coord + point.y.coord),
                new Coordinate(z.coord + point.z.coord)));
    }

    public double distanceSquared(Point3D point) {
        return Math.pow(point.x.coord - point.x.coord, 2) +
                Math.pow(point.y.coord - point.y.coord, 2) +
                Math.pow(point.z.coord - point.z.coord, 2);

    }

    public double distance(Point3D point) {
        return Math.sqrt(distanceSquared(point));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return Objects.equals(x, point3D.x) && Objects.equals(y, point3D.y) && Objects.equals(z, point3D.z);
    }

}
