package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * basic interface for Geometries
 */
public abstract class Geometry implements Intersectable {
    protected Color emission = new Color(0,0,0);
    protected Material material = new Material();

    /**
     * @return The color of the geometry
     */
    public Color getEmission() {
        return emission;
    }


    /**
     * @param emission Sets the color of the geometry.
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public Material getMaterial() {
        return material;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public abstract Vector getNormal(Point3D point);
}
