package at.makubi.services;

import at.makubi.module.exporter.ExportModule;
import at.makubi.module.importer.ImportModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ExportModuleServiceImpl implements ExportModuleService {


    private final Collection<ExportModule> exportModules;
    private final Map<String, ExportModule> stringExportModuleMap = new HashMap<String, ExportModule>();

    @Autowired
    public ExportModuleServiceImpl(Collection<ExportModule> exportModules) {
        this.exportModules = exportModules;

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
}
