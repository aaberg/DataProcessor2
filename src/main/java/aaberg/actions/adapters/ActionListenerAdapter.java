package aaberg.actions.adapters;

import aaberg.actions.Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lars on 25.05.14.
 */
public class ActionListenerAdapter implements ActionListener {

    private final Action action;

    public ActionListenerAdapter(Action action) {
        this.action = action;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        action.perform();
    }
}
