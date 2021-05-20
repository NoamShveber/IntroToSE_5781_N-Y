package primitives;

/**
 * Material class is a PDS representing a geometry's attenuation and shininess constants
 */
public class Material {
    public double kD, kS, kT, kR;
    public int nShininess;

    public Material() {
        kD = kS = nShininess = 0;
    }

    /**
     * Set the kD attenuation value of the material
     * @param kD The value to set.
     * @return this
     */
    public Material setKd(double kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Set the kS attenuation value of the material
     * @param kS The value to set.
     * @return this
     */
    public Material setKs(double kS) {
        this.kS = kS;
        return this;
    }

    public Material setKt(double kT) {
        this.kT = kT;
        return this;
    }

    public Material setKr(double kR) {
        this.kR = kR;
        return this;
    }

    /**
     * Set the shininess value of the material
     * @param nShininess The value to set.
     * @return this
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }


}
