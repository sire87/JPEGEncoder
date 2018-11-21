package at.aau.itec.emmt.jpeg.stud;

import at.aau.itec.emmt.jpeg.impl.Component;
import at.aau.itec.emmt.jpeg.impl.YUVImage;
import at.aau.itec.emmt.jpeg.spec.ColorSpaceConverterI;
import at.aau.itec.emmt.jpeg.spec.SubSamplerI;
import at.aau.itec.emmt.jpeg.spec.YUVImageI;

import java.awt.*;
import java.awt.image.PixelGrabber;

public class ColorSpaceConverter implements ColorSpaceConverterI {

    @Override
    public YUVImageI convertRGBToYUV(Image rgbImg) {

        int width = rgbImg.getWidth(null);
        int height = rgbImg.getHeight(null);
        int[] rgbPixels = new int[width * height];
        PixelGrabber pg = new PixelGrabber(rgbImg, 0, 0, width, height, rgbPixels, 0, width);

        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            throw new RuntimeException("Could not grab 'em by their pixels... :D");
        }

        // transform pixel array to 2-dimensional pixel array
        // needed for YCbCr component data
        int[][] rgbPixels2D = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                rgbPixels2D[i][j] = rgbPixels[i * height + j];
            }
        }

        // 2-dimensional pixel arrays for YCbCr components data
        int[][] compDataY = new int[height][width];
        int[][] compDataCb = new int[height][width];
        int[][] compDataCr = new int[height][width];

        // convert rgb to YCbCr and store values in respective component data arrays
        for (int i = 0; i < rgbPixels2D.length; i++) {
            for (int j = 0; j < rgbPixels2D[0].length; j++) {
                int red = 0xff & (rgbPixels2D[i][j] >> 16);
                int green = 0xff & (rgbPixels2D[i][j] >> 8);
                int blue = 0xff & (rgbPixels2D[i][j]);
                double y = 0.299 * red + 0.587 * green + 0.114 * blue;
                double cb = 128 - 0.1687 * red - 0.3313 * green + 0.5 * blue;
                double cr = 128 + 0.5 * red - 0.4187 * green - 0.0813 * blue;
                compDataY[i][j] = (int) y;
                compDataCb[i][j] = (int) cb;
                compDataCr[i][j] = (int) cr;
            }
        }

        // create YCbCr components
        Component y = new Component(compDataY, YUVImageI.Y_COMP);
        Component cb = new Component(compDataCb, YUVImageI.CB_COMP);
        Component cr = new Component(compDataCr, YUVImageI.CR_COMP);
        int samplingRatio = SubSamplerI.YUV_444;

        // create and return YUVImage
        return new YUVImage(y, cb, cr, samplingRatio);
    }

}
