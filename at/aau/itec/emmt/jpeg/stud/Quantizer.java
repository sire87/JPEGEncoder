package at.aau.itec.emmt.jpeg.stud;

import at.aau.itec.emmt.jpeg.impl.Block;
import at.aau.itec.emmt.jpeg.spec.BlockI;
import at.aau.itec.emmt.jpeg.spec.DCTBlockI;
import at.aau.itec.emmt.jpeg.spec.QuantizationI;
import at.aau.itec.emmt.jpeg.spec.YUVImageI;

public class Quantizer implements QuantizationI {

    protected int qualityFactor;

    public Quantizer() {
        this(DEFAULT_QUALITY_FACTOR);
    }

    public Quantizer(int qualityFactor) {
        this.qualityFactor = qualityFactor;
    }

    @Override
    public int[] getQuantumLuminance() {
        return convertNormedToScaledMatrix(QUANTUM_LUMINANCE);
    }

    @Override
    public int[] getQuantumChrominance() {
        return convertNormedToScaledMatrix(QUANTUM_CHROMINANCE);
    }

    private int[] convertNormedToScaledMatrix(int[] normedMatrix) {
        int[] res = new int[normedMatrix.length];

        for (int idx = 0; idx < res.length; ++idx)
            res[idx] = getScaledQuantValue(normedMatrix[idx]);
        return res;
    }

    @Override
    public BlockI quantizeBlock(DCTBlockI dctBlock, int compType) {
        // prepare
        int[] usedQuantMatrix =
                compType == YUVImageI.Y_COMP
                        ? getQuantumLuminance()
                        : getQuantumChrominance();

        double[][] origBlockData = dctBlock.getData();
        int[][] resBlockData = new int[origBlockData.length][origBlockData[0].length];

        // quantize
        for (int i = 0; i < origBlockData.length; ++i)
            for (int j = 0; j < origBlockData[0].length; ++j)
                resBlockData[i][j] =
                        (int)Math.round(getQuantDctCoefficient(origBlockData[i][j], usedQuantMatrix[i * origBlockData[0].length + j]));

        return new Block(resBlockData);
    }

    @Override
    public void setQualityFactor(int qualityFactor) {
        this.qualityFactor = qualityFactor;
    }

    /**
     * Returns the QualityScaleFactor S.
     *
     * @return
     */
    private int getQualityScalingFactor() {
        int qFact = qualityFactor;
        if (qFact < 50)
            return 5000 / qFact;
        else
            return 200 - 2 * qFact;
    }

    /**
     * Returns the scaled quantization value sqi.
     *
     * @param normedVal qi
     * @return
     */
    private int getScaledQuantValue(int normedVal) {
        return Math.min(255, Math.max(1,
                (normedVal * getQualityScalingFactor() + 50) / 100
        ));
    }

    /**
     * Returns the quantized DCT coefficient ^Fi.
     *
     * @param coefficient      Fi
     * @param scaledQuantValue sqi
     * @return
     */
    private double getQuantDctCoefficient(double coefficient, int scaledQuantValue) {
        return coefficient / scaledQuantValue;
    }
}
