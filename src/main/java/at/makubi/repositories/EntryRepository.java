package at.makubi.repositories;

import at.makubi.entities.Entry;
import org.springframework.data.repository.CrudRepository;

public interface EntryRepository extends CrudRepository<Entry, Long> {

}
