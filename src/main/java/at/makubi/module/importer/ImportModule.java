package at.makubi.module.importer;

import at.makubi.entities.Entry;

public interface ImportModule {

    Iterable<Entry> getEntriesForImport();
}
