package at.makubi.repositories;

import at.makubi.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findByName(String name);
}
