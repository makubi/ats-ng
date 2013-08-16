package at.makubi.pages;

import at.makubi.module.importer.ImportModule;
import at.makubi.services.ImportModuleService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Set;

public class Import
{
    private static final Logger LOG = LoggerFactory.getLogger(Import.class);

    @Inject
    @Property
    private ImportModuleService importModuleService;

    @Property
    private UploadedFile uploadedFile;

    @Property
    private String currentModule;

    @Property
    private String selectedModule;

    public Set<String> getAvailableModules() {
        return importModuleService.getModuleNames();
    }

    public String getSimpleName() {
        return currentModule.substring(currentModule.lastIndexOf(".") + 1, currentModule.length());
    }

    Object onSuccessFromUploadFileForm() {
        try {
            LOG.info("Initializing LinguaBaseImportModule...");

            final File file = File.createTempFile("ats.",".tmp");

            uploadedFile.write(file);

            final ImportModule importModule = importModuleService.getModuleByName(selectedModule);

            importModuleService.importFromFile(importModule, file);
        }
        catch (Exception e) {
            LOG.warn("Could not initialize LinguaBaseImportModule", e);
        }

        return this;
    }
}
