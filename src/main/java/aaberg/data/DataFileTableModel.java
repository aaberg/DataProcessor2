package aaberg.data;

import aaberg.ApplicationState;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lars on 25.05.14.
 */
public class DataFileTableModel implements TableModel {

    private final DataFile dataFile;

    public DataFileTableModel(DataFile dataFile) {
        this.dataFile = dataFile;
    }

    @Override
    public int getRowCount() {
        return dataFile.getRowCnt();
    }

    @Override
    public int getColumnCount() {
        return dataFile.getColCnt() + 1;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex == 0) {
            return "Row number";
        }
        return dataFile.getColumnsInfos().get(columnIndex - 1).toString();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return rowIndex + 1;
        }
        ColumnInfo columnInfo = ApplicationState.instance.getDataFile().getColumnsInfos().get(columnIndex - 1);
        double doubleVal = dataFile.getData().get(rowIndex)[columnIndex -1];
        return columnInfo.getDecimalFormat().format( doubleVal );
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        dataFile.getData().get(rowIndex)[columnIndex] = ((Number)aValue).doubleValue();
    }

    private List<TableModelListener> tableModelListeners = new ArrayList<>();

    @Override
    public void addTableModelListener(TableModelListener l) {
        this.tableModelListeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        this.tableModelListeners.remove(l);
    }

    public void onUpdate() {
        for (TableModelListener listener : this.tableModelListeners) {
            listener.tableChanged(new TableModelEvent(this));
        }
    }
}
