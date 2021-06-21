package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Directional light class represents a light source with a direction.
 */
public class DirectionalLight extends Light implements LightSource {

    /**
     * A vector that represents the direction of the light.
     */
    private Vector direction;

    /**
     * Builds a directional light object given its position and direction.
     * @param intensity Light's intensity(color)
     * @param direction Light's position
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point3D p) {
        return intensity;
    }

    @Override
    public Vector getL(Point3D p) {
        return direction.normalize();
    }

    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }
}
