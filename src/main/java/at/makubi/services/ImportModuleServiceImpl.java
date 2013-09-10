package at.makubi.services;

import at.makubi.Task;
import at.makubi.module.importer.ImportModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ImportModuleServiceImpl implements ImportModuleService {

    private final Collection<ImportModule> importModules;
    private final Map<String, ImportModule> stringImportModuleMap = new HashMap<String, ImportModule>();

    private final EntryService entryService;
    private final TaskService taskService;
    private final LanguageService languageService;

    @Autowired
    public ImportModuleServiceImpl(Collection<ImportModule> importModules, EntryService entryService, TaskService taskService, LanguageService languageService) {
        this.importModules = importModules;
        this.entryService = entryService;
        this.taskService = taskService;
        this.languageService = languageService;

        for(ImportModule importModule : importModules) {
            stringImportModuleMap.put(importModule.getClass().getCanonicalName(), importModule);
        }
    }

    @Override
    public Collection<ImportModule> getImportModules() {
        return importModules;
    }

    @Override
    public Set<String> getModuleNames() {
        return stringImportModuleMap.keySet();
    }

    @Override
    public ImportModule getModuleByName(String name) {
        return stringImportModuleMap.get(name);
    }

    @Override
    public void importFromFile(final ImportModule importModule, final File file) {
        taskService.addTask(new Task("Importing " + file.getName() + " with module " + importModule.getClass().getSimpleName()) {
            @Override
            public void execute() {
                entryService.createEntries(importModule.getEntriesForImport(file, languageService.getAvailableLanguages()));
            }
        });
    }

}
