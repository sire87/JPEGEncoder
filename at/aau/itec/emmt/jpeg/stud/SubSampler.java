package at.aau.itec.emmt.jpeg.stud;

import at.aau.itec.emmt.jpeg.impl.Component;
import at.aau.itec.emmt.jpeg.impl.YUVImage;
import at.aau.itec.emmt.jpeg.spec.SubSamplerI;
import at.aau.itec.emmt.jpeg.spec.YUVImageI;

public class SubSampler implements SubSamplerI {

    @Override
    public YUVImageI downSample(YUVImageI yuvImg, int samplingRatio) {

        // reduce chroma by factor of 2 in horizontal direction
        if (samplingRatio == SubSamplerI.YUV_422) {

            // get components
            Component y = (Component) yuvImg.getComponent(YUVImageI.Y_COMP);
            Component cb = (Component) yuvImg.getComponent(YUVImageI.CB_COMP);
            Component cr = (Component) yuvImg.getComponent(YUVImageI.CR_COMP);

            // get component data
            // attention: int[height][width]
            int[][] cbData = cb.getData();
            int[][] crData = cr.getData();

            // downsample cb and cr data i.e. only half the resolution for width
            int[][] cbDataNew = new int[cbData.length][cbData[0].length / 2];
            int[][] crDataNew = new int[crData.length][crData[0].length / 2];

            for (int i = 0; i < cbDataNew.length; i++) {
                for (int j = 0; j < cbDataNew[0].length; j++) {
                    // mean value from 2 neighboring old values is used for new value
                    cbDataNew[i][j] = (cbData[i][j * 2] + cbData[i][(j * 2) + 1]) / 2;
                    crDataNew[i][j] = (crData[i][j * 2] + crData[i][(j * 2) + 1]) / 2;
                }
            }

            cb = new Component(cbDataNew, YUVImageI.CB_COMP);
            cr = new Component(crDataNew, YUVImageI.CR_COMP);
            return new YUVImage(y, cb, cr, SubSamplerI.YUV_422);

        }

        // reduce chroma by factor of 2 in horizontal and vertical direction
        else if (samplingRatio == SubSamplerI.YUV_420) {

            // get components
            Component y = (Component) yuvImg.getComponent(YUVImageI.Y_COMP);
            Component cb = (Component) yuvImg.getComponent(YUVImageI.CB_COMP);
            Component cr = (Component) yuvImg.getComponent(YUVImageI.CR_COMP);

            // get component data
            // attention: int[height][width]
            int[][] cbData = cb.getData();
            int[][] crData = cr.getData();

            // downsample cb and cr data i.e. only half the resolution for width and height
            int[][] cbDataNew = new int[cbData.length / 2][cbData[0].length / 2];
            int[][] crDataNew = new int[crData.length / 2][crData[0].length / 2];

            for (int i = 0; i < cbDataNew.length; i++) {
                for (int j = 0; j < cbDataNew[0].length; j++) {
                    // mean value from 4 neighboring old values is used for new value
                    int cbRow0Sum = cbData[i * 2][j * 2] + cbData[i * 2][(j * 2) + 1];
                    int cbRow1Sum = cbData[i * 2 + 1][j * 2] + cbData[i * 2 + 1][(j * 2) + 1];
                    cbDataNew[i][j] = (cbRow0Sum + cbRow1Sum) / 4;
                    int crRow0Sum = crData[i * 2][j * 2] + crData[i * 2][(j * 2) + 1];
                    int crRow1Sum = crData[i * 2 + 1][j * 2] + crData[i * 2 + 1][(j * 2) + 1];
                    crDataNew[i][j] = (crRow0Sum + crRow1Sum) / 4;
                }
            }

            cb = new Component(cbDataNew, YUVImageI.CB_COMP);
            cr = new Component(crDataNew, YUVImageI.CR_COMP);
            return new YUVImage(y, cb, cr, SubSamplerI.YUV_420);
        }

        // either YUV_444 or incompatible sampling ratio -> return yuvImg w/o subsampling or throw exception
        if (samplingRatio == SubSamplerI.YUV_444) {
            return yuvImg;
        } else throw new RuntimeException("incompatible sampling ratio");

    }

}
