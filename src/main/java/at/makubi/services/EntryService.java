package at.makubi.services;

import at.makubi.entities.Entry;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
public interface EntryService {
    Iterable<Entry> getAllEntries();

    void createEntry(Entry entry);
}
