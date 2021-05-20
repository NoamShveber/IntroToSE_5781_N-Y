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
     * Sets the color of the geometry.
     * @param emission new emission color
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Get the geometry's material
     * @return geometry's material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Set the geometry's material
     * @param material new material
     * @return this
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public abstract Vector getNormal(Point3D point);
}
