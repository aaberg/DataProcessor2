package aaberg.actions;

import aaberg.ApplicationState;
import aaberg.TestForm;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by lars on 25.05.14.
 */
public class SaveFileAction implements Action {

    @Override
    public void perform() {

        JFileChooser fileChooser = TestForm.getFileChooser();

        if (fileChooser.showSaveDialog(TestForm.mainFrame) == JFileChooser.CANCEL_OPTION) {
            return;
        }
        File file = fileChooser.getSelectedFile();

        try (FileWriter writer = new FileWriter(file)) {
            java.util.List<double[]> d = ApplicationState.instance.getDataFile().getData();
            for (double[] row : d) {
                for (int colIdx = 0; colIdx < row.length; colIdx++) {
                    if (colIdx != 0) {
                        writer.write('\t');
                    }
                    writer.write(ApplicationState.decFormat.format(row[colIdx]));
                }
                writer.write("\r\n");
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
