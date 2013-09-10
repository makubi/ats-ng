package at.makubi.services;

import at.makubi.entities.Language;

import java.util.Collection;

public interface LanguageService {
    Iterable<String> getAvailableLanguageNames();

    void createLanguage(Language language);

    Language getLanguageByName(String name);

    boolean exists(String code);

    Iterable<Language> getAvailableLanguages();
}
