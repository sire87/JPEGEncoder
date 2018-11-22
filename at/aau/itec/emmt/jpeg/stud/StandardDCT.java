package at.aau.itec.emmt.jpeg.stud;

import at.aau.itec.emmt.jpeg.impl.DCTBlock;
import at.aau.itec.emmt.jpeg.spec.BlockI;
import at.aau.itec.emmt.jpeg.spec.DCTBlockI;
import at.aau.itec.emmt.jpeg.spec.DCTI;

public class StandardDCT implements DCTI {

    @Override
    public DCTBlockI forward(BlockI b) {

        int[][] blockPixels = b.getData();
        double[][] dctCoeffData = new double[8][8];

        for (int u = 0; u < dctCoeffData.length; u++) {
            for (int v = 0; v < dctCoeffData[0].length; v++) {

                double sum = 0;

                for (int i = 0; i < blockPixels.length; i++) {
                    for (int j = 0; j < blockPixels[0].length; j++) {

                        double tmp = (blockPixels[i][j] - 128) *
                                Math.cos(((2 * i + 1) * u * Math.PI) / 16) *
                                Math.cos(((2 * j + 1) * v * Math.PI) / 16);

                        sum += tmp;
                    }
                }

                dctCoeffData[u][v] = (getConstant(u) * getConstant(v) / 4) * sum;
            }
        }

        return new DCTBlock(dctCoeffData);
    }

    private double getConstant(int i) {

        if (i == 0) {
            return 1 / Math.sqrt(2);
        }

        return 1;
    }

}
