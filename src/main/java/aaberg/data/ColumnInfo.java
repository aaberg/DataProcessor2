package aaberg.data;

/**
 * Created by lars on 25.05.14.
 */
public class ColumnInfo {

    public final int columnNumber;
    private double minValue;
    private double maxValue;

    public ColumnInfo(int columnNumber, double minValue, double maxValue) {
        this.columnNumber = columnNumber;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }


    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    @Override
    public String toString() {
        return "Column " + columnNumber;
    }
}
