package at.makubi.services;

import at.makubi.entities.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
public interface ZoneService {

    Collection<Zone> getZones();

    void saveZone(Zone zone);

    void saveCategories(Collection<Category> categories);

    void saveTranslations(Collection<Translation> translations);

    void saveEntries(Collection<Entry> entries);

    void saveIdentifier(Identifier identifier);
}
