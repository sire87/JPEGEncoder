package at.aau.itec.emmt.jpeg.stud;

import at.aau.itec.emmt.jpeg.spec.BlockI;
import at.aau.itec.emmt.jpeg.spec.DCTBlockI;
import at.aau.itec.emmt.jpeg.spec.QuantizationI;

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
        return new int[0];
    }

    @Override
    public int[] getQuantumChrominance() {
        return new int[0];
    }

    @Override
    public BlockI quantizeBlock(DCTBlockI dctBlock, int compType) {
        return null;
    }

    @Override
    public void setQualityFactor(int qualityFactor) {
        this.qualityFactor = qualityFactor;
    }

}
