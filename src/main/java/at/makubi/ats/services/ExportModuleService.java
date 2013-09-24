package at.makubi.ats.services;

import at.makubi.ats.module.exporter.ExportModule;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Collection;
import java.util.Set;

@Transactional
public interface ExportModuleService {

    Collection<ExportModule> getExportModules();

    Set<String> getModuleNames();

    ExportModule getModuleByName(String name);

    File exportForFile(ExportModule exportModule, File file);

    void exportFile(ExportModule exportModule, UploadedFile uploadedFile, DownloadFile downloadFile);
}
