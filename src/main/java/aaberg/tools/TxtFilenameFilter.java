package aaberg.tools;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by lars on 25.05.14.
 */
public class TxtFilenameFilter implements FilenameFilter {
    @Override
    public boolean accept(File file, String name) {
        return name.endsWith(".txt");
    }
}
