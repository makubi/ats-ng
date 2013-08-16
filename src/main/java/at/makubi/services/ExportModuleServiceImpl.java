package at.makubi.services;

import at.makubi.module.exporter.ExportModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ExportModuleServiceImpl implements ExportModuleService {

    private final Collection<ExportModule> exportModules;
    private final Map<String, ExportModule> stringExportModuleMap = new HashMap<String, ExportModule>();

    private final EntryService entryService;
    private final TaskService taskService;

    @Autowired
    public ExportModuleServiceImpl(Collection<ExportModule> exportModules, EntryService entryService, TaskService taskService) {
        this.exportModules = exportModules;
        this.entryService = entryService;
        this.taskService = taskService;

        for(ExportModule exportModule : exportModules) {
            stringExportModuleMap.put(exportModule.getClass().getCanonicalName(), exportModule);
        }
    }

    @Override
    public Collection<ExportModule> getExportModules() {
        return exportModules;
    }

    @Override
    public Set<String> getModuleNames() {
        return stringExportModuleMap.keySet();
    }

    @Override
    public ExportModule getModuleByName(String name) {
        return stringExportModuleMap.get(name);
    }

    @Override
    public File exportForFile(final ExportModule exportModule, final File file) {
        return exportModule.export(file, entryService.getAllEntries());
    }
}
