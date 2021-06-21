package primitives;

/**
 * Material class is a PDS representing a geometry's attenuation and shininess constants
 */
public class Material {
    // The coefficients:

    /**
     * The attenuation coefficient of the diffusive light.
     */
    public double kD;

    /**
     * The coefficient of the specular light.
     */
    public double kS;

    /**
     * The coefficient of the transparency of the geometry.
     */
    public double kT;

    /**
     * The coefficient of the reflection of the geometry.
     */
    public double kR;

    /**
     * The level of shininess of the geometry.
     */
    public int nShininess;

    /**
     * Constructs a new instance of material where all fields are 0.
     */
    public Material() {
        kR = kT = kD = kS = nShininess = 0;
    }

    /**
     * @param kD The number to set as kD.
     * @return The current instance (Builder pattern).
     */
    public Material setKd(double kD) {
        this.kD = kD;
        return this;
    }

    /**
     * @param kS The number to set as kS.
     * @return The current instance (Builder pattern).
     */
    public Material setKs(double kS) {
        this.kS = kS;
        return this;
    }

    /**
     * @param kT The number to set as kT.
     * @return The current instance (Builder pattern).
     */
    public Material setKt(double kT) {
        this.kT = kT;
        return this;
    }

    /**
     * @param kR The number to set as kR.
     * @return The current instance (Builder pattern).
     */
    public Material setKr(double kR) {
        this.kR = kR;
        return this;
    }

    /**
     * @param nShininess The number to set as the shininess level.
     * @return The current instance (Builder pattern).
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
