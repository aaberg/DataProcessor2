package aaberg.actions;

import aaberg.ApplicationState;
import aaberg.data.ColumnInfo;
import aaberg.data.DataFile;

/**
 * Created by lars on 25.05.14.
 */
public class PerformOffsetAction implements OffsetAction {

    @Override
    public void perform(ColumnInfo columnInfo, double offsetValue) {
        DataFile data = ApplicationState.instance.getDataFile();

        for (double[] valArr : data.getData()) {
            int idx = columnInfo.columnNumber - 1;
            double val = valArr[idx];
            valArr[idx] = val + offsetValue;
        }

        ApplicationState.instance.onDataFileUpdated();

    }
}
