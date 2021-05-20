package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Point light class models a non-directional light source
 */
public class PointLight extends Light implements LightSource {
    protected Point3D position;
    protected double kC, kL, kQ;

    /**
     * Builds a point light object given its position and intensity.
     * @param intensity Light's intensity
     * @param position Light's position
     */
    public PointLight(Color intensity, Point3D position) {
        super(intensity);
        this.position = position;
        this.kC = 1;
        this.kL = 0;
        this.kQ = 0;
    }

    //region Setters
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }
    //endregion
    @Override
    public Color getIntensity(Point3D p) {
        double d = p.distance(position);
        return intensity.reduce(kC + kL * d + kQ * d * d);
    }

    @Override
    public Vector getL(Point3D p) {
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point3D point) {
        return position.distance(point);
    }
}
