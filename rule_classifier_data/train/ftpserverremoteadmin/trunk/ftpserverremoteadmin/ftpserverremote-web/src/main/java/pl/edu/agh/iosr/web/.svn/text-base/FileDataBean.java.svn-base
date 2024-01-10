
package pl.edu.agh.iosr.web;

import java.util.List;
import pl.edu.agh.iosr.ftpserverremote.data.FileData;
import pl.edu.agh.iosr.ftpserverremote.data.FileData.FileType;
import pl.edu.agh.iosr.ftpserverremote.serverdata.ServerDataProvider;

/**
 *
 * @author Tomasz Jadczyk
 */
public class FileDataBean {

    private List<FileData> uploadedFiles;
    private List<FileData> downloadedFiles;
    private List<FileData> removedFiles;
    private List<FileData> removedDirs;
    private List<FileData> createdDirs;
    
    public FileDataBean() {
        reload();
    }

    public List<FileData> getDownloadedFiles() {
        return downloadedFiles;
    }

    public void setDownloadedFiles(List<FileData> downloadedFiles) {
        this.downloadedFiles = downloadedFiles;
    }

    public List<FileData> getRemovedFiles() {
        return removedFiles;
    }

    public void setRemovedFiles(List<FileData> removedFiles) {
        this.removedFiles = removedFiles;
    }

    public List<FileData> getUploadedFiles() {
        return uploadedFiles;
    }

    public void setUploadedFiles(List<FileData> uploadedFiles) {
        this.uploadedFiles = uploadedFiles;
    }

    public List<FileData> getCreatedDirs() {
        return createdDirs;
    }

    public void setCreatedDirs(List<FileData> createdDirs) {
        this.createdDirs = createdDirs;
    }

    public List<FileData> getRemovedDirs() {
        return removedDirs;
    }

    public void setRemovedDirs(List<FileData> removedDirs) {
        this.removedDirs = removedDirs;
    }
    
    
    
    
    public void reload() {
        uploadedFiles = ServerDataProvider.getFileList(FileType.UPLOADED);
        downloadedFiles = ServerDataProvider.getFileList(FileType.DOWNLOADED);
        removedFiles = ServerDataProvider.getFileList(FileType.REMOVED);
        createdDirs = ServerDataProvider.getFileList(FileType.DIRCREATED);
        removedDirs = ServerDataProvider.getFileList(FileType.DIRREMOVED);
    }
    
    public void clearDirs() {
        //TODO:
    }
    
    public void clearFiles() {
        //TODO:
    }
}
