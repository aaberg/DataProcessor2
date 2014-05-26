package aaberg.components;

import aaberg.actions.ExitAction;
import aaberg.actions.OpenFileAction;
import aaberg.actions.SaveFileAction;
import aaberg.actions.adapters.ActionListenerAdapter;

import javax.swing.*;

/**
 * Created by lars on 25.05.14.
 */
public class TheMenu extends JMenuBar {

    public TheMenu() {
        JMenu fileMenu = new JMenu("File");

        // File menu items
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        JMenuItem openFileMenuItem = new JMenuItem("Open...");
        JMenuItem saveFileMenuItem = new JMenuItem("Save as...");

        exitMenuItem.addActionListener(new ActionListenerAdapter(new ExitAction()));
        openFileMenuItem.addActionListener(new ActionListenerAdapter(new OpenFileAction()));
        saveFileMenuItem.addActionListener(new ActionListenerAdapter(new SaveFileAction()));

        fileMenu.add(openFileMenuItem);
        fileMenu.add(saveFileMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);


        this.add(fileMenu);
    }


}
