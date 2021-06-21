package elements;

import primitives.Color;

/**
 * An abstract class to represent a template for lights.
 */
abstract class Light {
    /**
     * A color to represent the intensity of the light.
     */
    protected Color intensity;

    /**
     * Constructs a new instance of light with a given intensity.
     * @param intensity The light's intensity
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * @return The light's intensity
     */
    public Color getIntensity() {
        return intensity;
    }
}
