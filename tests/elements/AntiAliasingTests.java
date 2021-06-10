package elements;

import elements.Camera;
import elements.PointLight;
import elements.SpotLight;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.Scene;

public class AntiAliasingTests {
    /**
     * An image that we created, with 10 geometries and lights, reflections and refractions.
     */
    @Test
    public void createAnImage() {
        Camera camera = new Camera(new Point3D(-200, -200, 1000), new Vector(1, 1, -5), new Vector(5, 5, 2)) //
                .setViewPlaneSize(200, 200).setDistance(1000);
        Scene scene = new Scene("Test scene");
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.setBackground(new Color(java.awt.Color.ORANGE).reduce(1.125));
        scene.geometries.add(
                new Sphere(25, new Point3D(0,0,0))
                        .setMaterial(new Material().setKd(0.7).setKs(0.4).setKr(0.3).setShininess(60)).setEmission(new Color(10,100,200)),

                new Sphere(20, new Point3D(0,60,0))
                        .setMaterial(new Material().setKd(0.7).setKs(0.4).setKr(0.3).setShininess(60)).setEmission(new Color(java.awt.Color.BLUE)),

                new Sphere(20, new Point3D(60,0,0))
                        .setMaterial(new Material().setKd(0.7).setKs(0.4).setKr(0.3).setShininess(60)).setEmission(new Color(java.awt.Color.GREEN)),

                new Sphere(20, new Point3D(-60,0,0))
                        .setMaterial(new Material().setKd(0.7).setKs(0.4).setKr(0.3).setShininess(60)).setEmission(new Color(java.awt.Color.YELLOW)),

                new Sphere(20, new Point3D(0,-60,0))
                        .setMaterial(new Material().setKd(0.7).setKs(0.4).setKr(0.3).setShininess(60)).setEmission(new Color(java.awt.Color.RED)),

                new Cylinder(new Ray(new Point3D(0,0,0),new Vector(0,1,0)),10,60)
                        .setMaterial(new Material().setKd(0.3).setKs(0.5).setKt(0.5).setShininess(60)).setEmission(new Color(255,69,0)),

                new Cylinder(new Ray(new Point3D(0,0,0),new Vector(1,0,0)),10,60)
                        .setMaterial(new Material().setKd(0.3).setKs(0.5).setKt(0.5).setShininess(60)).setEmission(new Color(255,69,0)),

                new Cylinder(new Ray(new Point3D(0,0,0),new Vector(0,-1,0)),10,60)
                        .setMaterial(new Material().setKd(0.3).setKs(0.5).setKt(0.5).setShininess(60)).setEmission(new Color(255,69,0)),

                new Cylinder(new Ray(new Point3D(0,0,0),new Vector(-1,0,0)),10,60)
                        .setMaterial(new Material().setKd(0.3).setKs(0.5).setKt(0.5).setShininess(60)).setEmission(new Color(255,69,0)),

                new Pyramid(new Point3D(75, 75, 0), new Point3D(30, 60, 0), new Point3D(30, 30, 0), new Point3D(60, 30, 0), new Point3D(45, 45, 20))
                        .setMaterial(new Material().setKd(0.3).setKs(0.5).setKr(0.2).setShininess(90)).setEmission(new Color(110,3,177)),

                new Pyramid(new Point3D(75, -75, 0), new Point3D(30, -60, 0), new Point3D(30, -30, 0), new Point3D(60, -30, 0), new Point3D(45, -45, 20))
                        .setMaterial(new Material().setKd(0.3).setKs(0.5).setKr(0.2).setShininess(90)).setEmission(new Color(110,3,177)),

                new Pyramid(new Point3D(-75, -75, 0), new Point3D(-30, -60, 0), new Point3D(-30, -30, 0), new Point3D(-60, -30, 0), new Point3D(-45, -45, 20))
                        .setMaterial(new Material().setKd(0.3).setKs(0.5).setKr(0.2).setShininess(90)).setEmission(new Color(110,3,177)),

                new Pyramid(new Point3D(-75, 75, 0), new Point3D(-30, 60, 0), new Point3D(-30, 30, 0), new Point3D(-60, 30, 0), new Point3D(-45, 45, 20))
                        .setMaterial(new Material().setKd(0.3).setKs(0.5).setKr(0.2).setShininess(90)).setEmission(new Color(110,3,177)),

//                new Pyramid(new Point3D(10, 10, 30), new Point3D(10, -10, 30), new Point3D(-10, -10, 30), new Point3D(-10, 10, 30), new Point3D(0, 0, 34))
//                        .setMaterial(new Material().setKd(0.3).setKs(0.5).setShininess(90)).setEmission(new Color(110,3,177)),

                new Polygon(new Point3D(-120,0,-60), new Point3D(0,-120,-60), new Point3D(120,-80,-60), new Point3D(120,80,-60),  new Point3D(-40,120,-60))
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
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();
    }
}