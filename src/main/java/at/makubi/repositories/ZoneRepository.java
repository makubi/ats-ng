package at.makubi.repositories;

import at.makubi.entities.Category;
import at.makubi.entities.Zone;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ZoneRepository extends CrudRepository<Zone, Long> {

    Collection<Zone> findByName(String name);

    Category findByCategories_Name(String name);

}
