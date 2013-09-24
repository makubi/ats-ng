package at.makubi.ats.services;

import java.io.File;

public class DownloadFile {
    private final int id;
    private final String fileName;
    private File file;

    private final Object lock = new Object();

    public DownloadFile(int id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFile(File file) {
        synchronized (lock) {
            this.file = file;
        }
    }

    public File getFile() {
        synchronized (lock) {
            return file;
        }
    }

    public boolean isReady() {
        synchronized (lock)  {
            return file != null;
        }
    }

    public int getId() {
        return id;
    }
}