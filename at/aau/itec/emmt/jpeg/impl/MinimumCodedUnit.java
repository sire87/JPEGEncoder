package at.aau.itec.emmt.jpeg.impl;

import at.aau.itec.emmt.jpeg.spec.MinimumCodedUnitI;
import at.aau.itec.emmt.jpeg.spec.RegionI;
import at.aau.itec.emmt.jpeg.spec.YUVImageI;

/**
 * Pattern implementation of interface MinimumCodedUnitI.
 * @author Roland Tusch
 * @version 1.0
 */

public class MinimumCodedUnit implements MinimumCodedUnitI {

    RegionI[] regions;

    /**
     * Creates a new minimum coded unit holding the given array of regions.
     * @param regions an array containing one region for luminance (Y_COMP),
     *        one for chrominance blue (CB_COMP), and one for chrominance red (CR_COMP),
     *        excactly in this ordering.
     */
    public MinimumCodedUnit(RegionI[] regions) {
        if (regions != null && regions.length == 3) {
            if (regions[0].getType() == YUVImageI.Y_COMP &&
                regions[1].getType() == YUVImageI.CB_COMP &&
                regions[2].getType() == YUVImageI.CR_COMP)
                this.regions = regions;
            else
                throw new IllegalArgumentException("Wrong order of component regions.");
        }
        else
            throw new IllegalArgumentException("Argument 'regions' must not be null and of length 3.");
    }

    public RegionI[] getRegions() {
        return regions;
    }

    public int getNumberOfBlocks() {
        int numBlocks = 0;
        for (int i=0;i < regions.length;i++) {
            numBlocks += regions[i].getBlocks().length;
        }
        return numBlocks;
    }
}