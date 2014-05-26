package aaberg.actions;

import aaberg.ApplicationState;
import aaberg.TestForm;
import aaberg.data.DataFile;

import javax.swing.*;

/**
 * Created by lars on 25.05.14.
 */
public class OpenFileAction implements Action {

//    private static JFileChooser fileDialog = null;

    @Override
    public void perform() {

        JFileChooser fileChooser = TestForm.getFileChooser();

        int res = fileChooser.showOpenDialog(TestForm.mainFrame);
        if (res == JFileChooser.CANCEL_OPTION) {
            return;
        }

        ApplicationState.instance.setDataFile(new DataFile(fileChooser.getSelectedFile()));
    }
}
