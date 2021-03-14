package primitives;

import java.util.Objects;

public class Vector {
    Point3D head;

    public Vector(Point3D head) {
        this.head = head;
    }

    public Vector(double x, double y, double z) {
        this.head = new Point3D(
                new Coordinate(x),
                new Coordinate(y),
                new Coordinate(z));
    }

    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        this.head = new Point3D(x, y, z);
    }

    public Vector add(Vector vec) {
        return new Vector(head.x.coord + vec.head.x.coord,
                         head.y.coord + vec.head.y.coord,
                         head.z.coord + vec.head.z.coord);

    }

    public Vector subtract(Vector vec) {
        return new Vector(head.x.coord - vec.head.x.coord,
                    head.y.coord - vec.head.y.coord,
                    head.z.coord - vec.head.z.coord);
    }

    public Vector scale(double num) {
        return new Vector(head.x.coord * num,
                        head.y.coord * num,
                        head.z.coord * num);
    }

    public Vector crossProduct(Vector vec) {

    }

    public double dotProduct(Vector vec) {

    }

    public double lengthSquared() {

    }

    public double length() {

    }

    public Vector normalize() {

    }

    public Vector normalized() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Objects.equals(head, vector.head);
    }
}
