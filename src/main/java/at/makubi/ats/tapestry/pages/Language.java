package at.makubi.ats.tapestry.pages;

import at.makubi.ats.services.LanguageService;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Language
{
    private static final Logger LOG = LoggerFactory.getLogger(Language.class);

    @Inject
    @Property
    private LanguageService languageService;

    @Property
    private String code;

    @Property
    private String name;

    Object onSuccessFromAddLanguageForm() {
        at.makubi.ats.entities.Language language = new at.makubi.ats.entities.Language();
        language.setCode(code);
        language.setName(name);

        languageService.createLanguage(language);

        return this;
    }

    void onValidateFromCode(String code) throws ValidationException {
        if(languageService.exists(code)) {
            throw new ValidationException("An language with that code exists");
        }
    }
//
//    @Property
//    private String selectedModule;
//
//    public Set<String> getAvailableModules() {
//        return importModuleService.getModuleNames();
//    }
//
//    public String getSimpleName() {
//        return currentModule.substring(currentModule.lastIndexOf(".") + 1, currentModule.length());
//    }
//
//    Object onSuccessFromUploadFileForm() {
//        try {
//            LOG.info("Initializing LinguaBaseImportModule...");
//
//            final File file = File.createTempFile("ats.",".tmp");
//
//            uploadedFile.write(file);
//
//            final ImportModule importModule = importModuleService.getModuleByName(selectedModule);
//
//            importModuleService.importFromFile(importModule, file);
//        }
//        catch (Exception e) {
//            LOG.warn("Could not initialize LinguaBaseImportModule", e);
//        }
//
//        return this;
//    }
}
