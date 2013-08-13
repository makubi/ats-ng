package at.makubi.module.exporter;

import at.makubi.entities.Entry;

import java.util.Collection;

public interface ExportModule {

    public void export(Iterable<Entry> entryCollection);
}
