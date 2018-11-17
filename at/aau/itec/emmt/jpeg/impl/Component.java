package at.aau.itec.emmt.jpeg.impl;

import at.aau.itec.emmt.jpeg.spec.ComponentI;
import at.aau.itec.emmt.jpeg.spec.YUVImageI;

import java.awt.Dimension;


/**
 * Pattern implementation of interface ComponentI.
 * @author Roland Tusch
 * @version 1.0
 */

public class Component implements ComponentI {

    int[][] compData;
    int compType;

    /**
     * Constructs a component with the given component data and component type.
     * @param compData the pixel data of the component. Must not be null.
     * @param compType the component type. Must be one of YUVImageI.Y_COMP, YUVImageI.CB_COMP
     * or YUVImageI.CR_COMP.
     * @exception IllegalArgumentException if one of the arguments fails its
     *            requirements
     */
    public Component(int[][] compData, int compType) {
        if (compData != null) {
            if (compType >= YUVImageI.Y_COMP && compType <= YUVImageI.CR_COMP) {
                this.compData = compData;
                this.compType = compType;
            }
            else
                throw new IllegalArgumentException("Invalid component type.");
        }
        else
            throw new IllegalArgumentException("Component data must not be null.");
    }

    public Dimension getSize() {
        return new Dimension(compData[0].length,compData.length);
    }

    public int[][] getData() {
        return compData;
    }

    public int getType() {
        return compType;
    }
}
