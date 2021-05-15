package elements;

import primitives.Color;

/**
 * An abstract class to represent a template for lights.
 */
abstract class Light {
    protected Color intensity;

    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    public Color getIntensity() {
        return intensity;
    }
}
