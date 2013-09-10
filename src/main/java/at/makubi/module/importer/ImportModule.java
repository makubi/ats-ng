package at.makubi.module.importer;

import at.makubi.entities.Entry;
import at.makubi.entities.Language;

import java.io.File;

public interface ImportModule {

    Iterable<Entry> getEntriesForImport(File file, Iterable<Language> availableLanguages);
}
