package at.makubi.services;

import at.makubi.module.exporter.ExportModule;
import at.makubi.module.importer.ImportModule;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

@Transactional
public interface ExportModuleService {

    Collection<ExportModule> getExportModules();

    Set<String> getModuleNames();

    ExportModule getModuleByName(String name);
}
