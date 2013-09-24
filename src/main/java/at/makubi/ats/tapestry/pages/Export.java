package at.makubi.ats.tapestry.pages;

import at.makubi.ats.module.exporter.ExportModule;
import at.makubi.ats.services.DownloadFile;
import at.makubi.ats.services.ExportModuleService;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class Export
{

    private static final Logger LOG = LoggerFactory.getLogger(Export.class);

    @Inject
    @Property
    private ExportModuleService exportModuleService;

    @Property
    private String currentModule;

    @Property
    private String selectedModule;

    @InjectComponent
    private Zone zone;

    @Property
    private UploadedFile uploadedFile;

    @Property
    private DownloadFile currentFile;

    @Property
    private String fileName;

    @Property
    private String downloadFile;

    @Persist(PersistenceConstants.SESSION)
    @Property
    private Collection<DownloadFile> downloadFiles;

    @Persist(PersistenceConstants.SESSION)
    private int id;

    Object onSuccessFromUploadFileForm() {
        try {
            LOG.info("Initializing AndroidExportModule...");

            if(downloadFiles == null) downloadFiles = new ArrayList<DownloadFile>();

            final ExportModule exportModule = exportModuleService.getModuleByName(selectedModule);

            final DownloadFile downloadFile = new DownloadFile(id++, uploadedFile.getFileName());

            exportModuleService.exportFile(exportModule, uploadedFile, downloadFile);

            downloadFiles.add(downloadFile);
        }
        catch (Exception e) {
            LOG.warn("Could not initialize AndroidExportModule", e);
        }

        return zone;
    }

    public DownloadFile getDownloadFileForId(int id) {
        for(DownloadFile downloadFile : downloadFiles) {
            if(downloadFile.getId() == id) {
                return downloadFile;
            }
        }

        throw new RuntimeException("Unable to find file for id " + id);
    }

    Object onActionFromDownloadFileLink(int id) {
        final DownloadFile selectedDownloadFile = getDownloadFileForId(id);

        return new StreamResponse() {
            @Override
            public String getContentType() {
                return "text/xml";
            }

            @Override
            public InputStream getStream() throws IOException {
                return new FileInputStream(selectedDownloadFile.getFile());
            }

            @Override
            public void prepareResponse(Response response) {
                response.setHeader("Content-Disposition", "attachment; filename=" + selectedDownloadFile.getFileName());
            }
        };
    }

    public Set<String> getAvailableModules() {
        return exportModuleService.getModuleNames();
    }

    public String getSimpleName() {
        return currentModule.substring(currentModule.lastIndexOf(".") + 1, currentModule.length());
    }

}
