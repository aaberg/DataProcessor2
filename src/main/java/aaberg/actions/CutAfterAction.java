package aaberg.actions;

import aaberg.ApplicationState;
import aaberg.data.DataFile;

/**
 * Created by lars on 25.05.14.
 */
public class CutAfterAction implements CutAction {
    @Override
    public void perform(int rowNumber) {
        DataFile df = ApplicationState.instance.getDataFile();

        int remCnt = df.getRowCnt() - rowNumber;

        for (int idx = 0; idx < remCnt; idx++) {
            df.getData().remove(df.getData().size() - 1);
        }

        ApplicationState.instance.onDataFileUpdated();
    }
}
