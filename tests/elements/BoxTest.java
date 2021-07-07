package elements;

import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.BasicRayTracer;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

/**
 * A test class to check the AxisAlignedBoundingBox class.
 */
public class BoxTest {
    /**
     * The camera to produce the rays for the current scene.
     */
    private final Camera camera = new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, 1, 0)) //
            .setDistance(1000).setViewPlaneSize(200, 200).setAntiAliasing(false).setRays(5);

    /**
     * The current scene.
     */
    private Scene scene = new Scene("Test scene");

    /**
     * The color of all geometries in the scene.
     */
    private static final Color color = new Color(200, 0, 0);
    /**
     * The material for all geometries in the scene.
     */
    private static final Material mat = new Material().setKd(0.5).setKs(0.5).setShininess(60);



    /**
     * Produce a scene with a 3D model and render it into a png image
     */
    @Test
    public void box1() {
//        scene.geometries.add( //
//                new Sphere(80, Point3D.ZERO).setEmission(color).setMaterial(mat));

        scene.geometries.add( //
                new Triangle(new Point3D(80, 80, 0),
                        new Point3D(-80, -80, 0),
                        new Point3D(80, -80, 0))
                        .setEmission(color).setMaterial(mat),
                new Triangle(new Point3D(80,80,0),
                        new Point3D(-80, -80, 0),
                        new Point3D(-80, 80, 0))
                        .setEmission(new Color(0,200,0)).setMaterial(mat),
                new Sphere(20, Point3D.ZERO)
                        .setEmission(new Color(java.awt.Color.BLUE)).setMaterial(mat)
                );
        scene.lights.add(new PointLight(new Color(500, 500, 500), new Point3D(100, 0, -100)) //
                .setKq(0.000001));

        ImageWriter imageWriter = new ImageWriter("box", 800, 800);
        Render render = new Render() //
                .setCamera(camera) //
                .setImageWriter(imageWriter) //
                .setRayTracer(new BasicRayTracer(scene)) //
                .setMultithreading(1).setDebugPrint();
        render.renderImage();
        render.printGrid(50, new Color(java.awt.Color.YELLOW));
        render.writeToImage();
    }


    /**
     * Produce a scene with a 3D model and render it into a png image
     */
    @Test
    public void box2() {
//        scene.geometries.add( //
//                new Sphere(80, Point3D.ZERO).setEmission(color).setMaterial(mat));
        scene = new Scene("Test");
        scene.geometries.add( //
                new Sphere(30, new Point3D(70, 70, 0)),
                new Sphere(30, new Point3D(-70, 70, 0)),
                new Sphere(30, new Point3D(-70, -70, 0)),
                new Sphere(30, new Point3D(70, -70, 0))
                        .setEmission(color).setMaterial(mat));
        scene.lights.add(new PointLight(new Color(500, 500, 500), new Point3D(100, 0, -100)) //
                .setKq(0.000001));

        ImageWriter imageWriter = new ImageWriter("box2", 800, 800);
        Render render = new Render() //
                .setCamera(camera) //
                .setImageWriter(imageWriter) //
                .setRayTracer(new BasicRayTracer(scene)) //
                .setMultithreading(1).setDebugPrint();
        render.renderImage();
        render.printGrid(50, new Color(java.awt.Color.YELLOW));
        render.writeToImage();
    }
}
