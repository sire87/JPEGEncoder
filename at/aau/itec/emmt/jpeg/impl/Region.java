package at.aau.itec.emmt.jpeg.impl;

import at.aau.itec.emmt.jpeg.spec.BlockI;
import at.aau.itec.emmt.jpeg.spec.RegionI;
import at.aau.itec.emmt.jpeg.spec.YUVImageI;

/**
 * Pattern implementation of interface RegionI.
 * @author Roland Tusch
 * @version 1.0
 */

public class Region implements RegionI {

    BlockI[] blocks;
    int compType;

    /**
     * Constructs a new region holding the given blocks for the specified
     * component type.
     * @param blocks the blocks covered by this region
     * @param compType the component type, from which this region is part of.
     *        Must be one of YUVImageI.Y_COMP, YUVImageI.CB_COMP or YUVImageI.CR_COMP
     */
    public Region(BlockI[] blocks, int compType) {
        if (compType >= YUVImageI.Y_COMP && compType <= YUVImageI.CR_COMP) {
            this.blocks = blocks;
            this.compType = compType;
        }
        else
            throw new IllegalArgumentException("Invalid component type.");
    }

    public int getType() {
        return compType;
    }

    public BlockI[] getBlocks() {
        return blocks;
    }
}