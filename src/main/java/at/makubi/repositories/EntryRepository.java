package at.makubi.repositories;

import at.makubi.entities.Entry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EntryRepository extends CrudRepository<Entry, Long> {

    Iterable<Entry> findByTexts_TextIgnoreCaseContaining(String text);
}
