package elements;

import primitives.Color;

/**
 * Ambient light source represents an non-directional, fixed-intensity and fixed-color light source.
 */
public class AmbientLight extends Light {
    /**
     * Constructs a new instance of ambient light and sets it as BLACK.
     */
    public AmbientLight() {
        super(Color.BLACK);
    }


    /**
     * Constructs a new instance of ambient light with intensity according to the parameters.<br>
     * The final intensity is iA * kA.
     * @param iA Intensity of the ambient light.
     * @param kA The attenuation coefficient of the ambient light.
     */
    public AmbientLight(Color iA, double kA) {
        super(iA.scale(kA));
    }
}
