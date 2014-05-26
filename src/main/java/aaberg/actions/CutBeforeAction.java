package aaberg.actions;

import aaberg.ApplicationState;
import aaberg.data.DataFile;

/**
 * Created by lars on 25.05.14.
 */
public class CutBeforeAction implements CutAction {
    @Override
    public void perform(int rowNumber) {
        DataFile df = ApplicationState.instance.getDataFile();

        for (int idx = 0; idx < rowNumber - 1; idx++) {
            df.getData().remove(0);
        }

        ApplicationState.instance.onDataFileUpdated();
    }
}
