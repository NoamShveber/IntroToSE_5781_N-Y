package geometries;

/**
 * An interface to take care of creating Bounding Boxes
 */
public interface Boundable {

    /**
     * @return The bounding box of the object
     */
    AxisAlignedBoundingBox getBoundingBox();
}
