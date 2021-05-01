package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.*;

/**
 * Camera class represents the camera through which we see the scene
 */
public class Camera {
    private Point3D location;
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;

    public Point3D getLocation() {
        return location;
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

    public Camera(Point3D location, Vector vUp, Vector vTo) {
        if (!(vUp.dotProduct(vTo) == 0))
            throw new IllegalArgumentException("vTo and vUp have to be orthogonal!!!");
        this.location = location;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight = vTo.crossProduct(vUp).normalize();
    }

    public Camera setViewPlaneSize(double width, double height){
        return this;
    }

    public Camera setDistance(double distance){
        return this;
    }

}
