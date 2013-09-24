package at.makubi.ats.tapestry.pages;

import at.makubi.ats.entities.*;
import at.makubi.ats.services.EntryService;
import at.makubi.ats.services.LanguageService;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class AddEntry
{
    private static final Logger LOG = LoggerFactory.getLogger(AddEntry.class);

    @Inject
    @Property
    private EntryService entryService;

    @Inject
    @Property
    private LanguageService languageService;

    @Property
    private String identifier;

    @Property
    private String language;

    @Property
    private String text;

    @Property
    private String metaInfo;
    @Property
    private int maxLength;

    public static <T> List<T> copyIterator(Iterator<T> iter) {
        List<T> copy = new ArrayList<T>();
        while (iter.hasNext())
            copy.add(iter.next());
        return copy;
    }

    void onValidateFromIdentifier(String identifier) throws ValidationException {
        if(entryService.exists(identifier)) {
            throw new ValidationException("An entry with that identifier exists");
        }
    }

    // TODO liste wird nicht immer vollstaendig angezeigt
    public Iterable<String> getAvailableLanguages() {
        return languageService.getAvailableLanguageNames();
    }

    Object onSuccessFromEntryForm() {
        Identifier identifier = new Identifier();
        identifier.setText(this.identifier);

        Collection<Translation> translations = new ArrayList<Translation>();

        Translation translation = new Translation();
        translation.setLanguage(languageService.getLanguageByName(language));
        translation.setText(text);
        translations.add(translation);

        Entry entry = new Entry();
        entry.setIdentifier(identifier);
        entry.setTexts(translations);
        entry.setMetaInformation(metaInfo);
        entry.setMaxLength(maxLength);

        entryService.createEntry(entry);

        return this;
    }

}
