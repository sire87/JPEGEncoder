package at.aau.itec.emmt.jpeg.stud;

import at.aau.itec.emmt.jpeg.impl.AbstractHuffmanCoder;
import at.aau.itec.emmt.jpeg.impl.RunLevel;
import at.aau.itec.emmt.jpeg.spec.BlockI;
import at.aau.itec.emmt.jpeg.spec.EntropyCoderI;
import at.aau.itec.emmt.jpeg.spec.RunLevelI;

import java.util.LinkedList;
import java.util.List;

public class HuffmanCoder extends AbstractHuffmanCoder {

    @Override
    public RunLevelI[] runLengthEncode(BlockI quantBlock) {
        int[] inputBlock = flatten2dArray(quantBlock.getData());
        List<RunLevelI> resRuns = new LinkedList<RunLevelI>();
        int nextVal, run = 0;

        for (int i = 1; i < ZIGZAG_ORDER.length; ++i) {
            int idx = ZIGZAG_ORDER[i];
            nextVal = inputBlock[idx];
            if (nextVal == 0)
                ++run;
            else {
                resRuns.add(new RunLevel(run, nextVal));
                run = 0;
            }
        }

        if (run != 0)
            resRuns.add(new RunLevel(0, 0));

        return (RunLevelI[]) resRuns.toArray(new RunLevelI[0]);
    }

    private int[] flatten2dArray(int[][] arr) {
        List<Integer> res = new LinkedList<Integer>();
        for (int[] subarr : arr) {
            for (int val : subarr) {
                res.add(val);
            }
        }
        return convertListToArr(res);
    }

    private int[] convertListToArr(List<Integer> lst) {
        return lst.stream().mapToInt(i -> i).toArray();
    }
}
