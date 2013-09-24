package at.makubi.ats.services;

import at.makubi.ats.entities.Entry;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface EntryService {
    Iterable<Entry> getAllEntries();

    void createEntry(Entry entry);

    void createEntries(Iterable<Entry> entries);

    Iterable<Entry> getAllEntriesWithText(String text);

    boolean exists(String identifier);

    Entry getEntryById(long id);
}
