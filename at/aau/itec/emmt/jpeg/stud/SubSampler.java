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
                    // TODO: think this through...
                    // should mean value be used instead of left value?
                    cbDataNew[i][j] = cbData[i][j * 2];
                    crDataNew[i][j] = crData[i][j * 2];
                }
            }

            cb = new Component(cbDataNew, YUVImageI.CB_COMP);
            cr = new Component(crDataNew, YUVImageI.CR_COMP);
            return new YUVImage(y, cb, cr, SubSamplerI.YUV_422);

        } else if (samplingRatio == SubSamplerI.YUV_420) {
            // TODO: what value should be used? mean vs. upper left value
            return yuvImg;
        }

        // either YUV_444 or incompatible sampling ratio -> return yuvImg w/o subsampling
        else {
            return yuvImg;
        }

    }

}
