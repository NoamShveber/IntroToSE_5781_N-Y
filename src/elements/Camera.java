package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static primitives.Util.alignZero;

/**
 * Camera class represents the camera through which we see the scene.
 */
public class Camera {
    private Point3D p0;
    private Vector vUp, vTo, vRight;
    private double width, height, distance;

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
     * in random directions.
     * @param nX number of pixels on X axis in the view plane
     * @param nY number of pixels on Y axis in the view plane
     * @param i Y coordinate of the pixel
     * @param j X coordinate of the pixel
     * @param rays The amount of rays in each column and row.
     * @return A list of rays around that pixel.
     */
    public List<Ray> constructRaysThroughPixel(int nX, int nY, int i, int j, int rays) {
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
}
