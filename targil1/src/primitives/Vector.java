package primitives;

import geometries.Sphere;

import java.util.Objects;

public class Vector {
    public static final Vector ZERO = new Vector(new Point3D(0,0,0));
    Point3D head;

    public Vector(Point3D head) {
        this.head = new Point3D(
                new Coordinate(head.x.coord),
                new Coordinate(head.y.coord),
                new Coordinate(head.z.coord));
    }

    public Vector(double x, double y, double z) {
        Point3D tmp = new Point3D(x, y, z);
        if (tmp.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Can't create zero vector!");
        this.head = tmp;
    }

    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        Point3D tmp = new Point3D(x, y, z);
        if (tmp.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Can't create zero vector!");
        this.head = tmp;
    }

    public Point3D getHead() {
        return head;
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
        return new Vector(head.y.coord * vec.head.z.coord - head.z.coord * vec.head.y.coord,
                        head.z.coord * vec.head.x.coord - head.x.coord * vec.head.z.coord,
                        head.x.coord * vec.head.y.coord - head.y.coord * vec.head.x.coord);


    }

    public double dotProduct(Vector vec) {
        return head.x.coord * vec.head.x.coord +
                head.y.coord * vec.head.y.coord +
                head.z.coord * vec.head.z.coord;
    }

    public double lengthSquared() {
        return head.x.coord * head.x.coord +
                head.y.coord * head.y.coord +
                head.z.coord * head.z.coord;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector normalize() {
        double len = length();
        head.x.coord /= len;
        head.y.coord /= len;
        head.z.coord /= len;
        return this;
    }

    public Vector normalized() {
        return new Vector(head).normalize();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Vector)) return false;
        Vector other = (Vector) obj;
        return this.head.equals(other.head);
    }

    @Override
    public String toString() {
        return "head=(" + head.toString() +
                ')';
    }
}
