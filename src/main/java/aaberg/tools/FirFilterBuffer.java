package aaberg.tools;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lars on 26.05.14.
 */
public class FirFilterBuffer {

    private final double[] buf;
    private int pointer;
    private int length;

    private final List<Double> valList;

    public FirFilterBuffer(int size) {
        this.buf = new double[size];
        this.pointer = 0;
        this.length = 0;

        valList = new AbstractList<Double>() {
            @Override
            public Double get(int i) {
                if (i > length) {
                    throw new IndexOutOfBoundsException();
                }
                return buf[(length - i - 1) % buf.length];
            }

            @Override
            public int size() {
                return length;
            }
        };
    }

    public void insert(double val) {
        buf[pointer++] = val;

        if (pointer >= buf.length) {
            pointer = 0;
        }

        if (++length > buf.length) {
            length = buf.length;
        }
    }

    public double minVal() {
        if (length == 0) return 0d;

        double min = Double.MAX_VALUE;
        for (double val : valList) {
            if (val < min) {
                min = val;
            }
        }
//        for (int idx = pointer - 1, counter = 0; counter < length; idx--, counter++) {
//            double val = buf[idx];
//            if (val < min) {
//                min = val;
//            }
//        }

        return min;
    }

    public double maxVal() {
        if (length == 0) return 0d;

        double max = Double.MIN_VALUE;
        for (double val : valList) {
            if (val > max) {
                max = val;
            }
        }

        return max;
    }

    public double meanVal() {
        if (length == 0) return 0d;
        double sum = 0;
        for (double val : valList) {
            sum += val;
        }

        return sum / length;
    }

    public double medianVal() {
        if (length == 0) return 0;

        final double[] sortedBuffer = Arrays.copyOfRange(buf, 0, length);
        Arrays.sort(sortedBuffer);

        int idx = new Double( Math.floor(length / 2d)).intValue();
        return sortedBuffer[idx];
    }
}
