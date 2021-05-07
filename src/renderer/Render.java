package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.MissingResourceException;

public class Render {
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

    public void renderImage() {
        if (imageWriter == null)
            throw new MissingResourceException("Missing image writer object!", "ImageWriter", "");
        //if (scene == null)
        //    throw new MissingResourceException("Missing scene object!", "Scene", "");
        if (camera == null)
            throw new MissingResourceException("Missing camera object!", "Camera", "");
        if (rayTracer == null)
            throw new MissingResourceException("Missing tracer object!", "RayTracerBase", "");
        Ray ray;
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                imageWriter.writePixel(i, j, rayTracer.traceRay(camera.constructRayThroughPixel(imageWriter.getNx(),
                        imageWriter.getNy(), j, i)));
            }
        }
    }

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

    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException("Missing image writer object!", "ImageWriter", "");

        imageWriter.writeToImage();
    }
}
