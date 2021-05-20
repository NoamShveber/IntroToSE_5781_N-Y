package elements;

import primitives.Color;

/**
 * An abstract class to represent a template for lights.
 */
abstract class Light {
    protected Color intensity;

    /**
     * Light constructor
     * @param intensity the light source's intensity
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Get the light's intensity
     * @return light's intensity
     */
    public Color getIntensity() {
        return intensity;
    }
}
