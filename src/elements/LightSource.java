package elements;

import primitives.*;

/**
 * An interface for light sources (with distance).
 */
public interface LightSource {

    /**
     * @param p The point where we want to check the intensity.
     * @return The intensity of the light source in the given point.
     */
    Color getIntensity(Point3D p);


    /**
     * @param p The point where we want to construct a vector to.
     * @return A vector from the given point to the light source.
     */
    Vector getL(Point3D p);
}
