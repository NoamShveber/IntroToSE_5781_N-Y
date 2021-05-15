package primitives;

public class Material {
    public double kD, kS;
    public int nShininess;

    public Material() {
        kD = kS = nShininess = 0;
    }

    public Material setKd(double kD) {
        this.kD = kD;
        return this;
    }

    public Material setKs(double kS) {
        this.kS = kS;
        return this;
    }

    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }


}
