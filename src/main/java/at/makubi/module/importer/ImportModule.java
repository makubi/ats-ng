package at.makubi.module.importer;

import at.makubi.entities.Entry;

import java.io.File;

public interface ImportModule {

    Iterable<Entry> getEntriesForImport(File file);
}
