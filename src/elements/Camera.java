package elements;

import geometries.*;
import primitives.*;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Random;

/**
 * Camera class represents the camera through which we see the scene.
 */
public class Camera {
    /**
     * The point of view of the camera.
     */
    private Point3D p0;

    //The directions of the camera:
    /**
     * vUp - The "up" direction in the camera.
     */
    private Vector vUp;

    /**
     * vTo - The "to" direction in the camera, where the scene is.
     */
    private Vector vTo;

    /**
     * vRight - The "right" direction in the camera.
     */
    private Vector vRight;

    // The attributes of the view plane:
    /**
     * The width of the view plane.
     */
    private double width;

    /**
     * The height of the view plane.
     */
    private double height;

    /**
     * The distance between the p0 and the view plane (in the direction of vTo).
     */
    private double distance;

    /**
     * If true, then the renderer will also add antialiasing to the calculations, and vice versa.
     */
    private boolean antiAliasing = false;

    /**
     * If true, then the renderer will also add depth of field to the calculations, and vice versa.
     */
    private boolean depthOfField = false;

    /**
     * The amount of rays that will be shot in each row and column,<br>
     * in all picture improvements (so the final count of rays in each<br>
     * improvement is RAYS * RAYS).
     */
    private int rays = 1;


    /**
     * (For depth of field)
     * The width and height of the aperture, and
     * the distance between the view plane and the focal plane (in the direction of vTo).
     */
    private double apertureRadius, focalDistance;


    /**
     * Constructs an instance of Camera with point and to and up vectors.
     *
     * @param p0  The point of view of the camera.
     * @param vTo The "to" direction of the camera, where the scene is.
     * @param vUp The "up" direction of the camera.
     */
    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        if (!(vUp.dotProduct(vTo) == 0))
            throw new IllegalArgumentException("vTo and vUp have to be orthogonal!!!");
        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight = vTo.crossProduct(vUp).normalize();
    }


    /**
     * @return p0 of the camera.
     */
    public Point3D getP0() {
        return p0;
    }

    /**
     * @return vUp of the camera.
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     * @return vTo of the camera.
     */
    public Vector getvTo() {
        return vTo;
    }

    /**
     * @return vRight of the camera.
     */
    public Vector getvRight() {
        return vRight;
    }

    /**
     * @return The width of the view plane of the camera.
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return The height of the view plane of the camera.
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return the distance between p0 and the view plane.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * @return The radius of the aperture.
     */
    public double getApertureRadius() {
        return apertureRadius;
    }

    /**
     * @return The distance between the view plane and the focal plane.
     */
    public double getFocalDistance() {
        return focalDistance;
    }

    /**
     * @return the square root of the amount of rays used in the improvements.
     */
    public int getRays() {
        return rays;
    }


    /**
     * @param p0 The point to set as p0.
     * @return The current instance (Builder pattern).
     */
    public Camera setP0(Point3D p0) {
        this.p0 = p0;
        return this;
    }

    /**
     * @param width  The number to set as the view plane's width.
     * @param height The number to set as the view plane's height.
     * @return The current instance (Builder pattern).
     */
    public Camera setViewPlaneSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * @param distance The number to set as the distance between the p0 and the view plane.
     * @return The current instance (Builder pattern).
     */
    public Camera setDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * @param apertureRadius The number to set as the radius of the aperture.
     * @return The current instance (Builder pattern).
     */
    public Camera setApertureRadius(double apertureRadius) {
        this.apertureRadius = apertureRadius;
        return this;
    }

    /**
     * @param focalDistance The number to set as the distance between<br>
     *                      the view plane and the focal distance (in the direction<br>
     *                      of vTo).
     * @return The current instance (Builder pattern).
     */
    public Camera setFocalDistance(double focalDistance) {
        this.focalDistance = focalDistance;
        return this;
    }

    /**
     * @param rays The number to set as the square root amount of rays.
     * @return The current instance (Builder pattern).
     */
    public Camera setRays(int rays) {
        this.rays = rays;
        return this;
    }

    /**
     * @param antiAliasing The boolean value to set if anti-aliasing is on.
     * @return The current instance (Builder pattern).
     */
    public Camera setAntiAliasing(boolean antiAliasing) {
        this.antiAliasing = antiAliasing;
        return this;
    }

    /**
     * @param depthOfField The boolean value to set if depth-of-field is on.
     * @return The current instance (Builder pattern).
     */
    public Camera setDepthOfField(boolean depthOfField) {
        this.depthOfField = depthOfField;
        return this;
    }

    /**
     * Creates a ray that goes through a given pixel
     *
     * @param nX number of pixels on X axis in the view plane
     * @param nY number of pixels on Y axis in the view plane
     * @param i  Y coordinate of the pixel
     * @param j  X coordinate of the pixel
     * @return The ray from the camera to the pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int i, int j) { // As written in the presentation.
        Point3D imgCenter = p0.add(vTo.scale(distance));
        double rY = height / nY, rX = width / nX;
        double iY = -(j - (nY - 1d) / 2) * rY, jX = (i - (nX - 1d) / 2) * rX;
        Point3D ijP = imgCenter;
        if (jX != 0) ijP = ijP.add(vRight.scale(jX));
        if (iY != 0) ijP = ijP.add(vUp.scale(iY));
        Vector ijV = ijP.subtract(p0);
        return new Ray(p0, ijV);
    }

    /**
     * Creates a list of rays that goes through a given pixel (as a grid of sub-pixels)<br>
     * in random direction to make an antialiasing effect.
     *
     * @param nX number of pixels on X axis in the view plane.
     * @param nY number of pixels on Y axis in the view plane.
     * @param i  Y coordinate of the pixel.
     * @param j  X coordinate of the pixel.
     * @return A list of rays around that pixel.
     */
    public List<Ray> constructRaysThroughPixelAA(int nX, int nY, int i, int j) {
        List<Ray> lst = new ArrayList<>();

        // Choosing the biggest scalar to scale the vectors.
        double rY = height / (2 * nY * rays * distance),
                rX = width / (2 * nX * rays * distance);
        Random random = new Random();

        //Constructing (rays * rays) rays in random directions.
        for (int k = 0; k < rays; k++) {
            for (int l = 0; l < rays; l++) {
                //Constructing a ray to the middle of the current subpixel.
                Ray ray = constructRayThroughPixel(nX * rays, nY * rays, rays * i + k, rays * j + l);

                // Creating a random direction vector.
                Vector rnd = vUp.scale(Util.random(-rY, rY))
                        .add(vRight.scale(Util.random(-rX, rX)));

                // Adding the random vector to the ray.
                lst.add(new Ray(ray.getP0(), ray.getDir().add(rnd)));
            }
        }

        return lst;
    }

    /**
     * Creates a list of rays that goes through a focal point (from a grid of points)
     * to make a depth of field effect.
     *
     * @param nX number of pixels on X axis in the view plane.
     * @param nY number of pixels on Y axis in the view plane.
     * @param i  Y coordinate of the pixel.
     * @param j  X coordinate of the pixel.
     * @return A list of rays that go throughout the focal point.
     * @throws MissingResourceException If missing one of the resources of the depth of field.
     */
    public List<Ray> constructRaysThroughPixelDoF(int nX, int nY, int i, int j) {
        if (focalDistance == 0 || apertureRadius == 0)
            throw new MissingResourceException("Missing focal distance, aperture width or aperture height!",
                    "double", "");
        Point3D PcV = p0.add(vTo.scale(distance)); //Pc view plane
        Point3D PcF = p0.add(vTo.scale(distance + focalDistance)); //Pc focal plane
        Plane viewPlane = new Plane(PcV, vTo); // Creates the view plane.
        Plane focalPlane = new Plane(PcF, vTo); // Creates the focal plane.

        Ray ray = constructRayThroughPixel(nX, nY, i, j); // Constructs a ray to the middle of the current pixel

        Point3D viewIntersection = viewPlane.findIntersections(ray).get(0); // Only one intersection point in plane.
        Point3D focalPoint = focalPlane.findIntersections(ray).get(0);

        List<Ray> lst = new ArrayList<>();

        double alpha = 2 * Math.PI / (rays * rays);
        for (int k = 0; k < rays * rays; k++) {
            Point3D point = viewIntersection;

            if ((k != 0) && (k != Math.PI))
                point = point.add(vRight.scale(apertureRadius * Math.sin(k * alpha)));
            if ((k != Math.PI / 2) && (k != 3 * Math.PI / 2))
                point = point.add(vUp.scale(apertureRadius * Math.cos(k * alpha)));

            lst.add(new Ray(point, focalPoint.subtract(point)));
        }

        return lst;
    }

    /**
     * Creates a list of rays to calculate the color of a given pixel
     *
     * @param nX number of pixels on X axis in the view plane
     * @param nY number of pixels on Y axis in the view plane
     * @param i  Y coordinate of the pixel
     * @param j  X coordinate of the pixel
     * @return The rays for the calculations.
     */
    public List<Ray> constructRaysThroughPixel(int nX, int nY, int i, int j) {
        if (!depthOfField && !antiAliasing)
            return List.of(constructRayThroughPixel(nX, nY, i, j));

        List<Ray> rays = new ArrayList<>();
        if (depthOfField)
            rays.addAll(constructRaysThroughPixelDoF(nX, nY, i, j));

        if (antiAliasing)
            rays.addAll(constructRaysThroughPixelAA(nX, nY, i, j));

        return rays;

    }
}
