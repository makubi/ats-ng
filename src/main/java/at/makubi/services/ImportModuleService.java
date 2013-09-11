package at.makubi.services;

import at.makubi.module.importer.ImportModule;
import at.makubi.pages.Import;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Transactional
public interface ImportModuleService {
    Collection<ImportModule> getImportModules();

    Set<String> getModuleNames();

    ImportModule getModuleByName(String name);

    void importFromFile(ImportModule importModule, UploadedFile file);
}
