package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.List;
import java.util.MissingResourceException;

/**
 * Render class takes a ray tracer and turns it into an image
 */
public class Render {
    public static final boolean ANTI_ALIASING = true;
    public static final int RAYS = 8;


    ImageWriter imageWriter;
    //Scene scene;
    Camera camera;
    RayTracerBase rayTracer;

    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /*public Render setScene(Scene scene) {
        this.scene = scene;
        return this;
    }*/

    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    public Render setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Renders the image pixel by pixel into the imageWriter
     */
    public void renderImage() {
        if (imageWriter == null)
            throw new MissingResourceException("Missing image writer object!", "ImageWriter", "");
        //if (scene == null)
        //    throw new MissingResourceException("Missing scene object!", "Scene", "");
        if (camera == null)
            throw new MissingResourceException("Missing camera object!", "Camera", "");
        if (rayTracer == null)
            throw new MissingResourceException("Missing tracer object!", "RayTracerBase", "");

        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if (ANTI_ALIASING) {
                    Color color = Color.BLACK;
                    List<Ray> rays = camera.constructRaysThroughPixel(imageWriter.getNx(),
                            imageWriter.getNy(), i, j, RAYS);
                    for (Ray ray: rays) {
                        color = color.add(rayTracer.traceRay(ray));
                    }

                    imageWriter.writePixel(i, j, color.reduce(rays.size()));
                }
                else
                    imageWriter.writePixel(i, j, rayTracer.traceRay(camera.constructRayThroughPixel(imageWriter.getNx(),
                            imageWriter.getNy(), i, j)));
            }
        }
    }

    /**
     * Create a grid in the image
     *
     * @param interval How many pixels do you want to have in a square
     * @param color    What color the grid should be
     */
    public void printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException("Missing image writer object!", "ImageWriter", "");

        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if (i % interval == 0 || j % interval == 0)
                    imageWriter.writePixel(i, j, color);
            }
        }
    }

    /**
     * Change the actual image file according to the imageWriter object
     */
    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException("Missing image writer object!", "ImageWriter", "");

        imageWriter.writeToImage();
    }
}
