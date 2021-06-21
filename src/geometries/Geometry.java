package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * basic interface for Geometries
 */
public abstract class Geometry implements Intersectable {
    /**
     * The emission color (color of the geometry).
     */
    protected Color emission = new Color(0,0,0);

    /**
     * The material of the geometry.
     */
    protected Material material = new Material();

    /**
     * @return The emission color of the geometry.
     */
    public Color getEmission() {
        return emission;
    }


    /**
     * @param emission The color to set as the emission color.
     * @return The current instance (Builder pattern).
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * @return The geometry's material.
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * @param material The material to set as the material of the geometry.
     * @return The current instance (Builder pattern).
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }


    /**
     * @param point The point where to calculate the normal.
     * @return The normal to the geometry in that point.
     */
    public abstract Vector getNormal(Point3D point);
}
