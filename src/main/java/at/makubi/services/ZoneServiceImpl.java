package at.makubi.services;

import at.makubi.entities.*;
import at.makubi.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;
    private final CategoryRepository categoryRepository;
    private final EntryRepository entryRepository;
    private final TranslationRepository translationRepository;
    private final IdentifierRepository identifierRepository;

    @Autowired
    public ZoneServiceImpl(ZoneRepository zoneRepository, CategoryRepository categoryRepository, EntryRepository entryRepository, TranslationRepository translationRepository, IdentifierRepository identifierRepository) throws IOException {
        this.zoneRepository = zoneRepository;
        this.categoryRepository = categoryRepository;
        this.entryRepository = entryRepository;
        this.translationRepository = translationRepository;
        this.identifierRepository = identifierRepository;
    }

    private int count = 0;

    @Override
    public Collection<Zone> getZones() {
        final Collection<Zone> zones = new ArrayList<Zone>();

        Identifier identifier = new Identifier();
        identifier.setNumber(100001);
        identifier.setSubNumber(1);

        Collection<Translation> translations = new ArrayList<Translation>();

        Translation translation = new Translation();
        translation.setCountryCode("de");
        translation.setText("Hallo");
        translations.add(translation);

        Collection<Entry> entries = new ArrayList<Entry>();
        Entry entry = new Entry();
        entry.setIdentifier(identifier);
        entry.setTexts(translations);
        entry.setMetaInformation("meta inf");
        entry.setMaxLength(10);

        entries.add(entry);

        Collection<Category> categories = new ArrayList<Category>();
        Category category = new Category();
        category.setName("Main");
        category.setStartNumber(100000);
        category.setEndNumber(110000);
        category.setEntries(entries);
        categories.add(category);

        Zone zone = new Zone();
        zone.setName("zone " + count++);
        zone.setStartNumber(100000L);
        zone.setEndNumber(200000L);
        zone.setCategories(categories);

        saveIdentifier(identifier);
        saveTranslations(translations);
        saveEntries(entries);
        saveCategories(categories);
        saveZone(zone);

        for(Zone zone1 : zoneRepository.findAll()) {
            zones.add(zone1);
        }

        return zones;
    }

    @Override
    public void saveZone(Zone zone) {
        zoneRepository.save(zone);
    }

    @Override
    public void saveCategories(Collection<Category> categories) {
        categoryRepository.save(categories);
    }

    @Override
    public void saveTranslations(Collection<Translation> translations) {
        translationRepository.save(translations);
    }

    @Override
    public void saveEntries(Collection<Entry> entries) {
        entryRepository.save(entries);
    }

    @Override
    public void saveIdentifier(Identifier identifier) {
        identifierRepository.save(identifier);
    }
}
