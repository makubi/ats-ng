package at.makubi.ats.repositories;

import at.makubi.ats.entities.Entry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EntryRepository extends CrudRepository<Entry, Long> {

    Iterable<Entry> findByTexts_TextIgnoreCaseContaining(String text);

    @Query("SELECT COUNT(*) FROM Entry e WHERE e.identifier.text = ?1")
    long numRows(String identifier);

    Iterable<Entry> findByIdentifier_TextIgnoreCaseContaining(String text);
}
