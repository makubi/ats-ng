package at.makubi.ats.module.exporter;

import at.makubi.ats.entities.Entry;

import java.io.File;

public interface ExportModule {

    public File export(File file, Iterable<Entry> entryCollection);
}
