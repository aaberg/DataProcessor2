package aaberg.data;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lars on 25.05.14.
 */
public class DataFile {

    private final File file;

    private final List<double[]> data = new ArrayList<>();

    private List<ColumnInfo> columnsInfos;

    private DataFileTableModel tableModel;

    public DataFile(File file) {
        this.file = file;
        this.columnsInfos = new ArrayList<>();

        this.load();
    }

    public File getFile() {
        return file;
    }

    public List<double[]> getData() {
        return data;
    }

    public int getColCnt(){
        if (this.data == null) return 0;
        if (this.data.size() == 0) return 0;
        return this.data.get(0).length;
    }

    public int getRowCnt() {
        if (this.data == null) return 0;
        return this.data.size();
    }

    public DataFileTableModel getTableModel() {
        if (tableModel == null) {
            tableModel = new DataFileTableModel(this);
        }
        return tableModel;
    }

    private void load() {

//        DecimalFormat decFormat = new DecimalFormat("0.###", new DecimalFormatSymbols(new Locale("no")));

        this.setColumnsInfos(null);

        try (PushbackInputStream in = new PushbackInputStream( new FileInputStream(this.getFile()), 3)) {

            byte[] buf = new byte[3];
            in.read(buf);

            if (buf[0] == (byte)0xEF &&
                buf[1] == (byte)0xBB &&
                buf[2] == (byte)0xBF) {
                // UTF-8 BOM. Ignore these bytes.
            } else {
                in.unread(buf, 0, 3);
            }

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in), 3)) {

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains("Inf")) continue;
                    String[] vals = line.split("\t");
                    double[] arr = new double[vals.length];
                    initColInfoIfNull(arr.length);

                    for (int idx = 0; idx < vals.length; idx++) {

                        ColumnInfo colInfo = columnsInfos.get(idx);

                        String strVal = vals[idx].trim().replace(".", ",");
                        int decimals = strVal.length() - strVal.indexOf(",");
                        if (decimals < strVal.length() && decimals > colInfo.getNumberOfDecimals()) {
                            colInfo.setNumberOfDecimals(decimals);
                        }

                        arr[idx] = colInfo.getDecimalFormat().parse(strVal).doubleValue();
                    }
                    this.data.add(arr);
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public List<ColumnInfo> getColumnsInfos() {
        return columnsInfos;
    }

    public void setColumnsInfos(List<ColumnInfo> columnsInfos) {
        this.columnsInfos = columnsInfos;
    }

    private void initColInfoIfNull(int colCnt) {

        // update column infos
        if (this.getColumnsInfos() == null){
            this.setColumnsInfos(new ArrayList<ColumnInfo>());
            for (int idx = 0; idx < colCnt; idx++) {
                this.getColumnsInfos().add(new ColumnInfo(idx + 1, Double.MAX_VALUE, Double.MIN_VALUE));
            }
        }
    }

    public void updateColumnInfos() {
        if (this.getData() == null || this.getData().size() == 0) return;
        int colCnt = this.getData().get(0).length;

        initColInfoIfNull(colCnt);

        for (ColumnInfo ci : getColumnsInfos()){
            ci.setMaxValue(Double.MIN_VALUE);
            ci.setMinValue(Double.MAX_VALUE);
        }

        for (int rowIdx = 0; rowIdx < this.getData().size(); rowIdx++) {
            double[] row = this.getData().get(rowIdx);
            for (int colIdx = 0; colIdx < row.length; colIdx++) {
                ColumnInfo ci = this.getColumnsInfos().get(colIdx);
                if (row[colIdx] < ci.getMinValue()) {
                    ci.setMinValue(row[colIdx]);
                }
                if (row[colIdx] > ci.getMaxValue()) {
                    ci.setMaxValue(row[colIdx]);
                }
            }
        }
    }
}
