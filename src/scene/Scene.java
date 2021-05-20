package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;


/**
 * Scene class represents a scene with a background, lights and geometries.
 */
public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;
    public List<LightSource> lights;

    public Scene(String name) {
        this.name = name;
        geometries = new Geometries();
        background = new Color(Color.BLACK);
        ambientLight = new AmbientLight(new Color(Color.BLACK), 0);
        lights = new LinkedList<>();
    }

    /**
     * Set the scene's background color
     * @param background New color for the background
     * @return this
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Set the scene's ambientLight
     * @param ambientLight New ambientLight
     * @return this
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Set the scene's geometry list
     * @param geometries New list of geometries
     * @return this
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Set the scene's light source list
     * @param lights New list of light sources
     * @return this
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
