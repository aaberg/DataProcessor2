package aaberg.actions;

import aaberg.data.ColumnInfo;

/**
 * Created by lars on 25.05.14.
 */
public interface OffsetAction {

    void perform(ColumnInfo columnInfo, double offsetValue);
}
