package at.makubi.services;

import at.makubi.Task;
import at.makubi.entities.Entry;
import at.makubi.module.exporter.AndroidExportModule;
import at.makubi.module.exporter.ExportModule;
import at.makubi.repositories.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

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
    public void createEntries(final Iterable<Entry> entries) {
        for (Entry entry : entries) {
            createEntry(entry);
        }
    }

    @Override
    public Iterable<Entry> getAllEntriesWithText(String text) {
        return entryRepository.findByTexts_TextIgnoreCaseContaining(text);
    }

    @Override
    public boolean exists(String identifier) {
        return entryRepository.numRows(identifier) > 0;
    }

    @Override
    public Entry getEntryById(long id) {
        return entryRepository.findOne(id);
    }
}
