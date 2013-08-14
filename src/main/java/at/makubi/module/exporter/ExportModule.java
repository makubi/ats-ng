package at.makubi.module.exporter;

import at.makubi.entities.Entry;

import java.io.File;
import java.util.Collection;

public interface ExportModule {

    public File export(Iterable<Entry> entryCollection);
}
