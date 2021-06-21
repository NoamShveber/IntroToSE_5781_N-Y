package elements;

import primitives.*;

/**
 * Spot light class models a point light with a direction
 * the final intensity is (intensity * max(0, dir * l)) /(kC + kL * d + kQ * d * d).
 */
public class SpotLight extends PointLight {
    /**
     * The direction of the light source.
     */
    Vector direction;

    /**
     * Creates a directional spot light given its position, intensity and direction
     * @param intensity Light's intensity
     * @param position Light's position
     * @param direction Light's direction vector
     */
    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity(p).scale(Math.max(0, direction.dotProduct(getL(p))));
    }
}
