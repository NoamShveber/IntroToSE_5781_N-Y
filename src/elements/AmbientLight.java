package elements;

import primitives.Color;

/**
 * Ambient light source represents an non-directional, fixed-intensity and fixed-color light source.
 */
public class AmbientLight {
    Color intensity;

    public AmbientLight(Color iA, double kA) {
        this.intensity = iA.scale(kA);
    }

    public Color getIntensity() {
        return intensity;
    }
}
