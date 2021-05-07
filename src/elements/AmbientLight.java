package elements;

import primitives.Color;

public class AmbientLight {
    Color intensity;

    public AmbientLight(Color iA, double kA) {
        this.intensity = iA.scale(kA);
    }

    public Color getIntensity() {
        return intensity;
    }
}
