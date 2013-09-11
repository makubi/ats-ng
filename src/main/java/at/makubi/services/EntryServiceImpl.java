package at.makubi.services;

import at.makubi.entities.Entry;
import at.makubi.entities.Language;
import at.makubi.repositories.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        final Collection<Entry> textsContaining = makeCollection(entryRepository.findByTexts_TextIgnoreCaseContaining(text));
        final Collection<Entry> identifierContaining = makeCollection(entryRepository.findByIdentifier_TextIgnoreCaseContaining(text));
        textsContaining.addAll(identifierContaining);

        return textsContaining;
    }

    public static <E> Collection<E> makeCollection(Iterable<E> iter) {
        Collection<E> list = new ArrayList<E>();
        for (E item : iter) {
            list.add(item);
        }
        return list;
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
