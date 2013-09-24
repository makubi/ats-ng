package at.makubi.ats.repositories;

import at.makubi.ats.entities.Identifier;
import org.springframework.data.repository.CrudRepository;

public interface IdentifierRepository extends CrudRepository<Identifier, Long> {

}
