package at.makubi.ats.services;

import at.makubi.ats.module.importer.ImportModule;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

@Transactional
public interface ImportModuleService {
    Collection<ImportModule> getImportModules();

    Set<String> getModuleNames();

    ImportModule getModuleByName(String name);

    void importFromFile(ImportModule importModule, UploadedFile file);
}
