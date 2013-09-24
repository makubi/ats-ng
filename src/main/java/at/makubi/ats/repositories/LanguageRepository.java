package at.makubi.ats.repositories;

import at.makubi.ats.entities.Language;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LanguageRepository extends CrudRepository<Language, Long> {
    Language findByName(String name);

    @Query("SELECT COUNT(*) FROM Language e WHERE e.code = ?1")
    long numRows(String code);
}
