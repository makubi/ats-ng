package at.makubi.ats.services;

import at.makubi.ats.entities.Language;
import at.makubi.ats.repositories.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageServiceImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public Iterable<String> getAvailableLanguageNames() {
        Collection<String> availableLanguages = new ArrayList<String>();

        for(Language language : languageRepository.findAll()) {
            availableLanguages.add(language.getName());
        }

        return availableLanguages;
    }

    @Override
    public void createLanguage(Language language) {
        languageRepository.save(language);
    }

    @Override
    public Language getLanguageByName(String name) {
        return languageRepository.findByName(name);
    }

    @Override
    public boolean exists(String code) {
        return languageRepository.numRows(code) > 0;
    }

    @Override
    public Iterable<Language> getAvailableLanguages() {
        return languageRepository.findAll();
    }
}
