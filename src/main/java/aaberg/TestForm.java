package aaberg;

import aaberg.actions.*;
import aaberg.actions.adapters.ActionListenerAdapter;
import aaberg.callbacks.Callback;
import aaberg.components.TheMenu;
import aaberg.data.ColumnInfo;
import aaberg.data.DataFile;
import com.alee.laf.WebLookAndFeel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by lars on 24.05.14.
 */
public class TestForm {
    private JPanel panel1;
    private JButton openFileButton;
    private JButton saveFileAsButton;
    private JLabel fileNameLabel;
    private JTable table1;
    private JTextPane statusTextPane;
    private JTabbedPane tabbedPane1;
    private JComboBox comboBoxOffset;
    private JTextField offsetTextField;
    private JButton offsetApplyButton;
    private JLabel offsetValueError;
    private JTextField cutBeforTextField;
    private JButton cutBeforeApplyButton;
    private JTextField cutAfterTextField;
    private JButton cutAfterApplyButton;

    //    private JFileChooser theFileChooser;
    private static JFileChooser fileChooser;

    public static Frame mainFrame;

    public TestForm(Frame frame) {
        fileChooser = new JFileChooser();
//        saveFileDialog = new FileDialog(frame, "Save file as...", FileDialog.SAVE);
        initStuff();
    }

    public static void main(String[] args) {

        WebLookAndFeel.install();

        final JFrame frame = new JFrame("TestForm");
        final TestForm form = new TestForm(frame);
        frame.setJMenuBar(new TheMenu());
        frame.setContentPane(form.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        mainFrame = frame;

        frame.setVisible(true);
    }

    public void initStuff() {

        fileChooser.setFileFilter(new FileNameExtensionFilter(".txt files", "txt"));

        this.openFileButton.addActionListener(new ActionListenerAdapter(new OpenFileAction()));

        this.offsetApplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                offsetValueError.setText("");
                double offsetValue;
                try {
                    offsetValue = Double.parseDouble(offsetTextField.getText());
                } catch (NumberFormatException nfe) {
                    offsetValueError.setText("Error converting to number");
                    return;
                }

                OffsetAction action = new PerformOffsetAction();
                action.perform((ColumnInfo)comboBoxOffset.getSelectedItem(), offsetValue);
            }
        });
        this.offsetTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                offsetValueError.setText("");
            }
        });

        this.saveFileAsButton.addActionListener(new ActionListenerAdapter(new SaveFileAction()));

        ApplicationState.instance.registerDataFileUdatedCallback(new Callback() {
            @Override
            public void callback() {
                fileOpened();
            }
        });

        cutBeforeApplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CutAction action = new CutBeforeAction();
                action.perform(Integer.parseInt( cutBeforTextField.getText()));
            }
        });

        cutAfterApplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CutAction action = new CutAfterAction();
                action.perform(Integer.parseInt( cutAfterTextField.getText()));
            }
        });


    }

    private void fileOpened(){

        DataFile dataFile = ApplicationState.instance.getDataFile();

        fileNameLabel.setText(dataFile.getFile().getAbsolutePath());
        saveFileAsButton.setEnabled(true);

        table1.setModel(dataFile.getTableModel());

        // build status string
        StringBuilder sb = new StringBuilder();
        sb.append("File loaded succesfully\n");
        sb.append("Number of rows: ").append(ApplicationState.instance.getDataFile().getRowCnt()).append("\n");
        for (ColumnInfo ci : dataFile.getColumnsInfos()) {
            sb.append("Column ").append(ci.columnNumber).append(":");
            sb.append("\tMin value: ").append(ci.getMinValue()).append("\n");
            sb.append("\tMax value: ").append(ci.getMaxValue()).append("\n");
        }

        statusTextPane.setText(sb.toString());

        prepareConversions();
    }

    private void prepareConversions() {
        this.comboBoxOffset.removeAllItems();
        for (ColumnInfo ci : ApplicationState.instance.getDataFile().getColumnsInfos()) {
            this.comboBoxOffset.addItem(ci);
        }
    }

    public static JFileChooser getFileChooser() {
        return fileChooser;
    }
}
