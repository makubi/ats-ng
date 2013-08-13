package at.makubi.services;

import at.makubi.entities.Entry;
import at.makubi.repositories.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntryServiceImpl implements EntryService {

    private final EntryRepository entryRepository;

    @Autowired
    public EntryServiceImpl(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    @Override
    public Iterable<Entry> getAllEntries() {
        return entryRepository.findAll();
    }

    @Override
    public void createEntry(Entry entry) {
        entryRepository.save(entry);
    }

    @Override
    public Iterable<Entry> getAllEntriesWithText(String text) {
        return entryRepository.findByTexts_TextIgnoreCaseContaining(text);
    }
}
