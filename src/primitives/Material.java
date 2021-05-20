package primitives;

/**
 * Material class is a PDS representing a geometry's attenuation and shininess constants
 */
public class Material {
    public double kD, kS;
    public int nShininess;

    public Material() {
        kD = kS = nShininess = 0;
    }

    /**
     * Set the kD attenuation value of the material
     * @param kD
     * @return this
     */
    public Material setKd(double kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Set the kS attenuation value of the material
     * @param kS
     * @return this
     */
    public Material setKs(double kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Set the shininess value of the material
     * @param nShininess
     * @return this
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }


}
