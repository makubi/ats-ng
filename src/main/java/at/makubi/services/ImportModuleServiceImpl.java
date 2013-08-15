package at.makubi.services;

import at.makubi.module.importer.ImportModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ImportModuleServiceImpl implements ImportModuleService {

    private final Collection<ImportModule> importModules;
    private final Map<String, ImportModule> stringImportModuleMap = new HashMap<String, ImportModule>();

    @Autowired
    public ImportModuleServiceImpl(Collection<ImportModule> importModules) {
        this.importModules = importModules;

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

}
