package primitives;

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
     * @param p0 ray starting point
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
}
