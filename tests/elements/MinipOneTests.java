package elements;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.BasicRayTracer;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import java.util.Base64;

/**
 * Test class to check the Antialiasing and depth of field picture improvements.
 */
public class MinipOneTests {
    /**
     * An image that we created, with multiple geometries and lights, reflections and refractions.
     */
    @Test
    public void AATest() {
        Camera camera = new Camera(new Point3D(-200, -200, 1000), new Vector(1, 1, -5), new Vector(5, 5, 2)) //
                .setViewPlaneSize(200, 200).setDistance(1000).setAntiAliasing(true).setRays(5);
        Scene scene = new Scene("Test scene");
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.setBackground(new Color(java.awt.Color.ORANGE).reduce(1.125));
        scene.geometries.setAABB(false).add(
                new Sphere(25, new Point3D(0, 0, 0))
                        .setMaterial(new Material().setKd(0.7).setKs(0.4).setKr(0.3).setShininess(60)).setEmission(new Color(10, 100, 200)),

                new Sphere(20, new Point3D(0, 60, 0))
                        .setMaterial(new Material().setKd(0.7).setKs(0.4).setKr(0.3).setShininess(60)).setEmission(new Color(java.awt.Color.BLUE)),

                new Sphere(20, new Point3D(60, 0, 0))
                        .setMaterial(new Material().setKd(0.7).setKs(0.4).setKr(0.3).setShininess(60)).setEmission(new Color(java.awt.Color.GREEN)),

                new Sphere(20, new Point3D(-60, 0, 0))
                        .setMaterial(new Material().setKd(0.7).setKs(0.4).setKr(0.3).setShininess(60)).setEmission(new Color(java.awt.Color.YELLOW)),

                new Sphere(20, new Point3D(0, -60, 0))
                        .setMaterial(new Material().setKd(0.7).setKs(0.4).setKr(0.3).setShininess(60)).setEmission(new Color(java.awt.Color.RED)),

                new Cylinder(new Ray(new Point3D(0, 0, 0), new Vector(0, 1, 0)), 10, 60)
                        .setMaterial(new Material().setKd(0.3).setKs(0.5).setKt(0.5).setShininess(60)).setEmission(new Color(255, 69, 0)),

                new Cylinder(new Ray(new Point3D(0, 0, 0), new Vector(1, 0, 0)), 10, 60)
                        .setMaterial(new Material().setKd(0.3).setKs(0.5).setKt(0.5).setShininess(60)).setEmission(new Color(255, 69, 0)),

                new Cylinder(new Ray(new Point3D(0, 0, 0), new Vector(0, -1, 0)), 10, 60)
                        .setMaterial(new Material().setKd(0.3).setKs(0.5).setKt(0.5).setShininess(60)).setEmission(new Color(255, 69, 0)),

                new Cylinder(new Ray(new Point3D(0, 0, 0), new Vector(-1, 0, 0)), 10, 60)
                        .setMaterial(new Material().setKd(0.3).setKs(0.5).setKt(0.5).setShininess(60)).setEmission(new Color(255, 69, 0)),

                new Pyramid(new Point3D(75, 75, 0), new Point3D(30, 60, 0), new Point3D(30, 30, 0), new Point3D(60, 30, 0), new Point3D(45, 45, 20))
                        .setMaterial(new Material().setKd(0.3).setKs(0.5).setKr(0.2).setShininess(90)).setEmission(new Color(110, 3, 177)),

                new Pyramid(new Point3D(75, -75, 0), new Point3D(30, -60, 0), new Point3D(30, -30, 0), new Point3D(60, -30, 0), new Point3D(45, -45, 20))
                        .setMaterial(new Material().setKd(0.3).setKs(0.5).setKr(0.2).setShininess(90)).setEmission(new Color(110, 3, 177)),

                new Pyramid(new Point3D(-75, -75, 0), new Point3D(-30, -60, 0), new Point3D(-30, -30, 0), new Point3D(-60, -30, 0), new Point3D(-45, -45, 20))
                        .setMaterial(new Material().setKd(0.3).setKs(0.5).setKr(0.2).setShininess(90)).setEmission(new Color(110, 3, 177)),

                new Pyramid(new Point3D(-75, 75, 0), new Point3D(-30, 60, 0), new Point3D(-30, 30, 0), new Point3D(-60, 30, 0), new Point3D(-45, 45, 20))
                        .setMaterial(new Material().setKd(0.3).setKs(0.5).setKr(0.2).setShininess(90)).setEmission(new Color(110, 3, 177)),

//                new Pyramid(new Point3D(10, 10, 30), new Point3D(10, -10, 30), new Point3D(-10, -10, 30), new Point3D(-10, 10, 30), new Point3D(0, 0, 34))
//                        .setMaterial(new Material().setKd(0.3).setKs(0.5).setShininess(90)).setEmission(new Color(110,3,177)),

                new Polygon(new Point3D(-120, 0, -60), new Point3D(0, -120, -60), new Point3D(120, -80, -60), new Point3D(120, 80, -60), new Point3D(-40, 120, -60))
                        .setMaterial(new Material().setKd(0.3).setKs(0.001)).setEmission(new Color(java.awt.Color.PINK).reduce(1.5))


        );
        scene.lights.add(new SpotLight(new Color(200, 270, 200), new Point3D(50, 25, 100), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));


//        scene.lights.add(new PointLight(new Color(500, 100, 100), new Point3D(45, 45, 10)) //
//                .setKl(4E-10).setKq(3E-10));

        ImageWriter imageWriter = new ImageWriter("AATest", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setMultithreading(1).setDebugPrint()
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();
    }

    /**
     * An image with a few spheres and cylinders, to show the depth of field.
     */
    @Test
    public void DoFTest() {
        Camera camera = new Camera(new Point3D(-100, 0, 50), new Vector(1, 0, 0), new Vector(0, 0, 1)) //
                .setViewPlaneSize(200, 200)
                .setDistance(100)
                .setFocalDistance(40).setApertureRadius(1).setDepthOfField(true).setRays(7);


        Material sphMat = new Material().setShininess(80).setKd(0.9).setKs(0.3);
        Material cylMat = new Material().setKd(0.7).setKs(0.3);
        Material plaMat = new Material().setKd(0.7).setKs(0.3);

        Color color = new Color(0, 0, 0);

        Scene scene = new Scene("Test scene");

        scene.geometries.add(
                new Sphere(5, new Point3D(30, -20, 0)).setEmission(color).setMaterial(sphMat),
                new Sphere(5, new Point3D(30, -50, 0)).setEmission(color).setMaterial(sphMat),
                new Cylinder(new Ray(new Point3D(30, -45, 0), new Vector(0, 1, 0)), 1, 20)
                        .setEmission(color).setMaterial(cylMat),
                new Sphere(4, new Point3D(40, -63, -26)).setEmission(color).setMaterial(sphMat),
                new Cylinder(new Ray(new Point3D(40, -63, -26), new Vector(-10, 13, 26)), 1,30.75)
                        .setEmission(color).setMaterial(cylMat),
                //new Sphere(30, new Point3D(-300, -80, -10)).setEmission(new Color(java.awt.Color.WHITE)).setMaterial(sphMat),

                new Sphere(5, new Point3D(110, 60, 0)).setEmission(color).setMaterial(sphMat),
                new Sphere(5, new Point3D(110, 30, 0)).setEmission(color).setMaterial(sphMat),
                new Cylinder(new Ray(new Point3D(110, 35, 0), new Vector(0, 1, 0)), 1, 20)
                        .setEmission(color).setMaterial(cylMat),
                new Sphere(4, new Point3D(120, 70, -26)).setEmission(color).setMaterial(sphMat),
                new Cylinder(new Ray(new Point3D(120, 70, -26), new Vector(-10, -10, 26)), 1,30.75)
                        .setEmission(color).setMaterial(cylMat),

                new Plane(new Point3D(0, 0, -30), new Vector(0, 0, -1))
                        .setEmission(new Color(java.awt.Color.DARK_GRAY)).setMaterial(plaMat)

        );

        scene.lights.add(new SpotLight(new Color(java.awt.Color.WHITE), new Point3D(-55, -50, 100),
                new Vector(0, 0, -1)).setKl(0.000000001).setKq(0.0000005));

        ImageWriter imageWriter = new ImageWriter("DoFTest", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setMultithreading(3).setDebugPrint()
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();
    }
}