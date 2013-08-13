package at.makubi.pages;

import at.makubi.entities.Entry;
import at.makubi.entities.Identifier;
import at.makubi.entities.Translation;
import at.makubi.module.importer.ImportModule;
import at.makubi.module.importer.lingua.LinguaBaseImportModule;
import at.makubi.services.EntryService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class AddEntry
{
    private static final Logger LOG = LoggerFactory.getLogger(AddEntry.class);

    @Inject
    @Property
    private EntryService entryService;

    @Property
    private int number;
    @Property
    private int subNumber;

    @Property
    private String language;
    @Property
    private String text;

    @Property
    private String metaInfo;
    @Property
    private int maxLength;

    @Property
    private UploadedFile uploadedFile;

    Object onSuccessFromEntryForm() {
        Identifier identifier = new Identifier();
        identifier.setNumber(number);
        identifier.setSubNumber(subNumber);

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

    public void onSuccessFromUploadFileForm() {
        try {
            LOG.info("Initializing LinguaBaseImportModule...");

            File file = new File(System.getProperty("java.io.tmpdir") + File.separator + uploadedFile.getFileName());
            uploadedFile.write(file);

            ImportModule importModule = new LinguaBaseImportModule(file);

            for(Entry entry : importModule.getEntriesForImport()) {
                entryService.createEntry(entry);
            }
        }
        catch (Exception e) {
            LOG.warn("Could not initialize LinguaBaseImportModule", e);
        }

    }
}