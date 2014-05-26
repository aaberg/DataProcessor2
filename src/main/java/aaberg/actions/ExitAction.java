package aaberg.actions;

/**
 * Created by lars on 25.05.14.
 */
public class ExitAction implements Action {
    @Override
    public void perform() {
        System.exit(0);
    }
}
