package primitives;

/**
 * Vector class represents a vector in 3D space.
 * the vector starts at zero and ends at the point head
 */
public class Vector {
    public static final Vector ZERO = new Vector(new Point3D(0,0,0));
    /**
     * the head of the vector
     */
    Point3D head;

    /**
     * Vector constructor by giving the head point
     * @param head the head point
     */
    public Vector(Point3D head) {
        this.head = new Point3D(
                new Coordinate(head.x.coord),
                new Coordinate(head.y.coord),
                new Coordinate(head.z.coord));
    }

    /**
     * Vector constructor by giving the values of the coordinates of the head point
     * @param x value of the x coordinate of head point
     * @param y value of the y coordinate of head point
     * @param z value of the z coordinate of head point
     */
    public Vector(double x, double y, double z) {
        Point3D tmp = new Point3D(x, y, z);
        if (x == 0 && y == 0 && z == 0)
            throw new IllegalArgumentException("Can't create zero vector!");
        this.head = tmp;
    }

    /**
     * Vector constructor by giving the coordinates of the head point
     * @param x the x coordinate of head point
     * @param y the y coordinate of head point
     * @param z the z coordinate of head point
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        Point3D tmp = new Point3D(x, y, z);
        if (tmp.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Can't create zero vector!");
        this.head = tmp;
    }

    public Point3D getHead() {
        return head;
    }

    /**
     * add operation between vectors
     * @param vec other vector
     * @return the sum between the vectors
     */
    public Vector add(Vector vec) {
        return new Vector(head.x.coord + vec.head.x.coord,
                         head.y.coord + vec.head.y.coord,
                         head.z.coord + vec.head.z.coord);

    }

    /**
     * subtract operation between vectors
     * @param vec other vector
     * @return the difference between the vectors
     */
    public Vector subtract(Vector vec) {
        return new Vector(head.x.coord - vec.head.x.coord,
                    head.y.coord - vec.head.y.coord,
                    head.z.coord - vec.head.z.coord);
    }

    /**
     * scale a vector by a factor of num
     * @param num the amount to scale by
     * @return the scaled vector
     */
    public Vector scale(double num) {
//        double a;
//        if (isZero(num) || (isZero(head.x.coord) && isZero(head.y.coord) && isZero(head.z.coord)))
//            a = 0;
        return new Vector(head.x.coord * num,
                        head.y.coord * num,
                        head.z.coord * num);
    }

    /**
     * cross product between two vectors.
     * returns the vector that is orthogonal to both of them
     * @param vec the other vector
     * @return the normal vector
     */
    public Vector crossProduct(Vector vec) {
        return new Vector(head.y.coord * vec.head.z.coord - head.z.coord * vec.head.y.coord,
                        head.z.coord * vec.head.x.coord - head.x.coord * vec.head.z.coord,
                        head.x.coord * vec.head.y.coord - head.y.coord * vec.head.x.coord);


    }

    /**
     * dot product between 2 vectors
     * @param vec the other vector
     * @return dot product
     */
    public double dotProduct(Vector vec) {
        return head.x.coord * vec.head.x.coord +
                head.y.coord * vec.head.y.coord +
                head.z.coord * vec.head.z.coord;
    }

    /**
     * @return the squared length of the vector
     */
    public double lengthSquared() {
        return head.x.coord * head.x.coord +
                head.y.coord * head.y.coord +
                head.z.coord * head.z.coord;
    }

    /**
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * normalize the vector
     * @return the normalised vector
     */
    public Vector normalize() {
        double len = length();
        head.x.coord /= len;
        head.y.coord /= len;
        head.z.coord /= len;
        return this;
    }

    /** create a new normalised vector
     * @return a new normalised vector
     */
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
