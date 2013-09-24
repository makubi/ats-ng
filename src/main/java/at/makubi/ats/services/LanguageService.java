package at.makubi.ats.services;

import at.makubi.ats.entities.Language;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LanguageService {
    Iterable<String> getAvailableLanguageNames();

    void createLanguage(Language language);

    Language getLanguageByName(String name);

    boolean exists(String code);

    Iterable<Language> getAvailableLanguages();
}
