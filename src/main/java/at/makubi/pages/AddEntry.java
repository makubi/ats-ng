package at.makubi.pages;

import at.makubi.entities.Entry;
import at.makubi.entities.Identifier;
import at.makubi.entities.Translation;
import at.makubi.module.exporter.AndroidExportModule;
import at.makubi.module.exporter.ExportModule;
import at.makubi.module.importer.ImportModule;
import at.makubi.module.importer.lingua.LinguaBaseImportModule;
import at.makubi.services.EntryService;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.h2.jdbc.JdbcSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;

import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public class AddEntry
{
    private static final Logger LOG = LoggerFactory.getLogger(AddEntry.class);

    @Inject
    @Property
    private EntryService entryService;

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

    void onValidateFromIdentifier(String identifier) throws ValidationException {
        if(entryService.exists(identifier)) {
            throw new ValidationException("An entry with that identifier exists");
        }
    }

    Object onSuccessFromEntryForm() {
        Identifier identifier = new Identifier();
        identifier.setText(this.identifier);

        Collection<Translation> translations = new ArrayList<Translation>();

        Translation translation = new Translation();
        translation.setCountryCode(language);
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
