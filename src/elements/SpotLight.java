package elements;

import primitives.*;

/**
 * Spot light class models a point light with a direction
 */
public class SpotLight extends PointLight {
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
