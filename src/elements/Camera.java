package elements;

import geometries.Intersectable.GeoPoint;
import geometries.*;
import primitives.*;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Random;

import static primitives.Util.alignZero;

/**
 * Camera class represents the camera through which we see the scene.
 */
public class Camera {

    /**
     * The point of view of the camera.
     */
    private Point3D p0;


    /**
     * The directions of the camera:
     * vUp - The "up" direction in the camera.
     * vRight - The "right" direction in the camera.
     * vTo - The "to" direction in the camera, where the scene is.
     */
    private Vector vUp, vTo, vRight;


    /**
     * The width and height of the view plane, and
     * the distance between the p0 and the view plane (in the direction of vTo).
     */
    private double width, height, distance;

    /**
     * (For depth of field)
     * The width and height of the aperture, and
     * the distance between the view plane and the focal plane (in the direction of vTo).
     */
    private double apertureWidth, apertureHeight, focalDistance;


    public Point3D getP0() {
        return p0;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvRight() {
        return vRight;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        if (!(vUp.dotProduct(vTo) == 0))
            throw new IllegalArgumentException("vTo and vUp have to be orthogonal!!!");
        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight = vTo.crossProduct(vUp).normalize();
    }

    public Camera setP0(Point3D p0) {
        this.p0 = p0;
        return this;
    }

    public Camera setViewPlaneSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public Camera setDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public Camera setApertureWidth(double apertureWidth) {
        this.apertureWidth = apertureWidth;
        return this;
    }

    public Camera setApertureHeight(double apertureHeight) {
        this.apertureHeight = apertureHeight;
        return this;
    }

    public Camera setFocalDistance(double focalDistance) {
        this.focalDistance = focalDistance;
        return this;
    }

    /**
     * Creates a ray that goes through a given pixel
     * @param nX number of pixels on X axis in the view plane
     * @param nY number of pixels on Y axis in the view plane
     * @param i Y coordinate of the pixel
     * @param j X coordinate of the pixel
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

    public double randomDouble(double min, double max) {
        double num = min + (max - min) * new Random().nextDouble();
        return num != 0 ? num : randomDouble(min, max);
    }

    /**
     * Creates a list of rays that goes through a given pixel (as a grid of sub-pixels)
     * in random direction to make an antialiasing effect.
     * @param nX number of pixels on X axis in the view plane.
     * @param nY number of pixels on Y axis in the view plane.
     * @param i Y coordinate of the pixel.
     * @param j X coordinate of the pixel.
     * @param rays The amount of rays in each column and row.
     * @return A list of rays around that pixel.
     */
    public List<Ray> constructRaysThroughPixelAA(int nX, int nY, int i, int j, int rays) {
        List<Ray> lst = new ArrayList<>();

        // Choosing the biggest scalar to scale the vectors.
        double rY = height / (2 * nY * rays * 0.05 * distance),
                rX = width / (2 * nX * rays * 0.05 * distance);
        Random random = new Random();

        //Constructing (rays * rays) rays in random directions.
        for (int k = 0; k < rays; k++) {
            for (int l = 0; l < rays; l++) {
                //Constructing a ray to the middle of the current subpixel.
                Ray ray = constructRayThroughPixel(nX * rays, nY * rays, rays * i + k, rays * j + l);

                // Creating a random direction vector.
                Vector rnd = vUp.scale(randomDouble(-rY, rY))
                        .add(vRight.scale(randomDouble(-rX, rX)));

                // Adding the random vector to the ray.
                lst.add(new Ray(ray.getP0(), ray.getDir().add(rnd)));

            }
        }

        return lst;
    }

    /**
     * Creates a list of rays that goes through a focal point (from a grid of points)
     * to make a depth of field effect.
     * @param nX number of pixels on X axis in the view plane.
     * @param nY number of pixels on Y axis in the view plane.
     * @param i Y coordinate of the pixel.
     * @param j X coordinate of the pixel.
     * @param rays The amount of rays in each column and row.
     * @return A list of rays that go throughout the focal point.
     * @throws MissingResourceException If missing one of the resources of the depth of field.
     */
    public List<Ray> constructRaysThroughPixelDoF(int nX, int nY, int i, int j, int rays) {
        if (focalDistance == 0 || apertureWidth == 0 || apertureHeight == 0)
            throw new MissingResourceException("Missing focal distance, aperture width or aperture height!",
                    "double", "");
        Point3D PcV = p0.add(vTo.scale(distance)); //Pc view plane
        Point3D PcF = p0.add(vTo.scale(distance + focalDistance)); //Pc focal plane
        Plane viewPlane = new Plane(PcV, vTo); // Creates the view plane.
        Plane focalPlane = new Plane(PcF, vTo); // Creates the focal plane.

        Ray ray = constructRayThroughPixel(nX, nY, i, j); // Constructs a ray to the middle of the current pixel

        GeoPoint viewIntersection = viewPlane.findGeoIntersections(ray).get(0); // Only one intersection point in plane.
        GeoPoint focalPoint = focalPlane.findGeoIntersections(ray).get(0);

        List<Ray> lst = new ArrayList<>();

        double rX = apertureWidth / (2 * rays), // The width of a half sub-pixel in the aperture.
                rY = apertureHeight / (2 * rays); // The height of a half sub-pixel in the aperture.

        // Constructing (rays * rays) rays, while moving the point, but all the rays
        // will go throughout the focal point.
        for (int k = -rays / 2; k < Math.ceil(rays / 2d); k++) {
            for (int l = -rays / 2; l < Math.ceil(rays / 2d); l++) {
                Point3D point = viewIntersection.point;
                if (k != 0)
                    point = point.add(vRight.scale(k * rX));
                if (l != 0)
                    point = point.add(vUp.scale(l * rY));

                lst.add(new Ray(point, focalPoint.point.subtract(point)));
            }
        }

        return lst;
    }
}
