package at.aau.itec.emmt.jpeg.impl;

import at.aau.itec.emmt.jpeg.spec.RunLevelI;

public class RunLevel implements RunLevelI {

    int run, level;

    /**
     * Creates a new RUN-LEVEL object.
     * @param run the run of zeros, which must not be negative.
     * @param level the level following the run
     */
    public RunLevel(int run, int level) {
        if (run >= 0) {
            this.run = run;
            this.level = level;
        }
        else
            throw new IllegalArgumentException("The RUN must not be negative.");
    }

    public int getRun() {
        return run;
    }

    public int getLevel() {
        return level;
    }
}
