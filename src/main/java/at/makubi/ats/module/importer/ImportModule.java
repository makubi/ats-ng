package at.makubi.ats.module.importer;

import at.makubi.ats.entities.Entry;
import at.makubi.ats.entities.Language;

import java.io.File;

public interface ImportModule {

    Iterable<Entry> getEntriesForImport(File file, Iterable<Language> availableLanguages);
}
