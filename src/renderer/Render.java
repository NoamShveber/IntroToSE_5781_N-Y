package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;

import java.util.List;
import java.util.MissingResourceException;

/**
 * Render class takes a ray tracer and turns it into an image
 */
public class Render {
    /**
     * If true, then the renderer will also add antialiasing to the calculations, and vice versa.
     */
    public static final boolean ANTI_ALIASING = false;

    /**
     * If true, then the renderer will also add depth of field to the calculations, and vice versa.
     */
    public static final boolean DEPTH_OF_FIELD = true;

    /**
     * The amount of rays that will be shot in each row and column,
     * in all picture improvements (so the final count of rays in each
     * improvement is RAYS * RAYS).
     */
    public static final int RAYS = 7;


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

        // Counts the amount of picture improvements.
        int numOfImp = (ANTI_ALIASING ? 1 : 0) + (DEPTH_OF_FIELD ? 1 : 0);
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                // If there are no improvements to the picture
                if (numOfImp == 0) {
                    imageWriter.writePixel(i, j, rayTracer.traceRay(camera.constructRayThroughPixel(imageWriter.getNx(),
                            imageWriter.getNy(), i, j)));
                }

                else {
                    Color color = Color.BLACK;

                    if (ANTI_ALIASING) { // If anti-aliasing is enabled - a boolean constant.
                        // constructRaysThroughPixelAA is a function to create
                        // a list of rays to calculate
                        // the average color (RAYS is a constant, how many rays in each
                        // column and row) of that pixel according to the AA algorithm.
                        List<Ray> rays = camera.constructRaysThroughPixelAA(imageWriter.getNx(),
                                imageWriter.getNy(), i, j, RAYS);

                        for (Ray ray : rays) { // A loop to sum all colors
                            color = color.add(rayTracer.traceRay(ray));
                        }
                    }

                    if (DEPTH_OF_FIELD) {
                        // If depth of field is enabled - a boolean constant.
                        // constructRaysThroughPixelDoF is a function to create
                        // a list of rays to calculate
                        // the average color (RAYS is a constant, how many rays in each
                        // column and row) of that pixel according to the DoF algorithm.
                        List<Ray> rays = camera.constructRaysThroughPixelDoF(imageWriter.getNx(),
                                imageWriter.getNy(), i, j, RAYS);

                        for (Ray ray : rays) { // A loop to sum all colors
                            color = color.add(rayTracer.traceRay(ray));
                        }
                    }

                    // Writing the average color to the pixel.
                    imageWriter.writePixel(i, j, color.reduce(RAYS * RAYS * numOfImp));
                }
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
