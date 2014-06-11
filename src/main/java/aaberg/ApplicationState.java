package aaberg;

import aaberg.callbacks.Callback;
import aaberg.data.DataFile;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by lars on 24.05.14.
 */
public class ApplicationState {

    public final static ApplicationState instance;
    public static final DecimalFormat decFormat = new DecimalFormat("#,##0.000", new DecimalFormatSymbols(new Locale("no")));

//    private List<Callback> dataFileSetCallbacks = new ArrayList<>();
    private final List<Callback> datafileUpdatedCallbacks = new ArrayList<>();

    static {
        instance = new ApplicationState();
    }


    private DataFile dataFile;
    public DataFile getDataFile() {
        return dataFile;
    }

    public void setDataFile(DataFile dataFile) {
        this.dataFile = dataFile;

        this.onDataFileUpdated();
    }

    public void registerDataFileUdatedCallback(Callback callback){
        this.datafileUpdatedCallbacks.add(callback);
    }

    public void removeDataFileUpdatedCallback(Callback callback) {
        this.datafileUpdatedCallbacks.remove(callback);
    }

    public void onDataFileUpdated() {
        this.dataFile.updateColumnInfos();
        this.dataFile.getTableModel().onUpdate();
        for (Callback callback : this.datafileUpdatedCallbacks) {
            callback.callback();
        }

    }
}
