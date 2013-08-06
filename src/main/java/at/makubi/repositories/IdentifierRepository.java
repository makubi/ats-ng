package at.makubi.repositories;

import at.makubi.entities.Identifier;
import org.springframework.data.repository.CrudRepository;

public interface IdentifierRepository extends CrudRepository<Identifier, Long> {

}
