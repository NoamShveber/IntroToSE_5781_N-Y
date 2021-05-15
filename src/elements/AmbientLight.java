package elements;

import primitives.Color;

/**
 * Ambient light source represents an non-directional, fixed-intensity and fixed-color light source.
 */
public class AmbientLight extends Light {
    public AmbientLight() {
        super(Color.BLACK);
    }

    public AmbientLight(Color iA, double kA) {
        super(iA.scale(kA));
    }
}
