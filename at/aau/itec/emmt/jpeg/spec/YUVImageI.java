package at.aau.itec.emmt.jpeg.spec;

/**
 * A YUV image contains luminance (Y_COMP), chrominance blue (CB_COMP) and chrominance
 * red (CR_COMP) components. That is why a YUV image is also referred to as YCbCr
 * image in digital image processing.
 * @author Roland Tusch
 * @version 1.0
 */

public interface YUVImageI {

    /** constant for luminance **/
    public static final int Y_COMP = 0;
    /** constant for chrominance blue **/
    public static final int CB_COMP = 1;
    /** constant for chrominance red **/
    public static final int CR_COMP = 2;

    /**
     * Delivers the component of the specified component type of this
     * YUV image.
     * @param compType the type of component to return. Must be one of
     *        Y_COMP, CB_COMP or CR_COMP.
     * @return the component of specified type
     * @exception IllegalArgumentException if the given type is not valid
     */
    ComponentI getComponent(int compType);

    /**
     * Retrieves the currently used sampling ratio for this YUV (or YCbCr) image.
     * You can compare it with SubSamplerI.YUV_444, in order to figure out,
     * whether the image has been subsampled, or not.
     * @return the sampling factor of this component. Must be one of SubSamplerI.YUV_444,
     *         SubSamplerI.YUV_422 or SubSamplerI.YUV_420.
     */
    int getSamplingRatio();
}
