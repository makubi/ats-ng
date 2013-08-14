package at.makubi.pages;

import at.makubi.entities.Entry;
import at.makubi.module.exporter.AndroidExportModule;
import at.makubi.module.exporter.ExportModule;
import at.makubi.module.importer.ImportModule;
import at.makubi.module.importer.lingua.LinguaBaseImportModule;
import at.makubi.services.EntryService;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public class Export
{

    private static final Logger LOG = LoggerFactory.getLogger(Export.class);

    @Inject
    @Property
    private EntryService entryService;

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

            File file = File.createTempFile("ats.",".tmp");

            uploadedFile.write(file);

            ExportModule androidExportModule = new AndroidExportModule();

            downloadFiles.add(new DownloadFile(id++, uploadedFile.getFileName(), androidExportModule.export(file, entryService.getAllEntries())));
        }
        catch (Exception e) {
            LOG.warn("Could not initialize AndroidExportModule", e);
        }

        return zone;
    }

    private DownloadFile getDownloadFileForId(int id) {
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

    public class DownloadFile {
        private final int id;
        private final String fileName;
        private final File file;

        public DownloadFile(int id, String fileName, File file) {
            this.id = id;
            this.fileName = fileName;
            this.file = file;
        }

        public String getFileName() {
            return fileName;
        }

        public File getFile() {
            return file;
        }

        public int getId() {
            return id;
        }
    }

}
