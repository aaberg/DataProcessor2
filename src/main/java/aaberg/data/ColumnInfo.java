package aaberg.data;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by lars on 25.05.14.
 */
public class ColumnInfo {

    public final int columnNumber;
    private double minValue;
    private double maxValue;

    private int numberOfDecimals;
    private DecimalFormat decimalFormat;

    public ColumnInfo(int columnNumber, double minValue, double maxValue) {
        this.columnNumber = columnNumber;
        this.minValue = minValue;
        this.maxValue = maxValue;

        decimalFormat = new DecimalFormat("0");
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

    public int getNumberOfDecimals() {
        return numberOfDecimals;
    }

    public void setNumberOfDecimals(int numberOfDecimals) {
        this.numberOfDecimals = numberOfDecimals;

        StringBuilder formatStr = new StringBuilder("0.");

        for (int i = 0; i < numberOfDecimals; i++) {
            formatStr.append("0");
        }

        decimalFormat = new DecimalFormat(formatStr.toString(), new DecimalFormatSymbols(new Locale("no")));
    }

    public DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }

    @Override
    public String toString() {
        return "Column " + columnNumber;
    }
}
