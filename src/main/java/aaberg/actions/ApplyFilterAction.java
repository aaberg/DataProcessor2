package aaberg.actions;

import aaberg.ApplicationState;
import aaberg.data.ColumnInfo;
import aaberg.tools.FilterMethod;
import aaberg.tools.FilterMethodFactory;

/**
 * Created by lars on 26.05.14.
 */
public class ApplyFilterAction {

    public void perform(int filterOrder, FilterMethodFactory filterMethodFactory, ColumnInfo columnInfo) {
        FilterMethod method = filterMethodFactory.newFilterWrapper(filterOrder);

        int colIdx = columnInfo.columnNumber - 1;

        for (double[] row : ApplicationState.instance.getDataFile().getData()) {
            double val = row[colIdx];

            method.insert(val);

            row[colIdx] = method.getValue();
        }


        ApplicationState.instance.onDataFileUpdated();
    }
}
