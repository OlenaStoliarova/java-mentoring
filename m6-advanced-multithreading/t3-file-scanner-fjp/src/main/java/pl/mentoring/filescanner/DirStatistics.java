package pl.mentoring.filescanner;

import org.apache.commons.io.FileUtils;

public class DirStatistics {
    private int fileCount;
    private int dirCount;
    private long size;

    public DirStatistics() {
        fileCount = 0;
        dirCount = 0;
        size = 0;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void addToFileCount(int fileCount) {
        this.fileCount += fileCount;
    }

    public int getDirCount() {
        return dirCount;
    }

    public void addToDirCount(int dirCount) {
        this.dirCount += dirCount;
    }

    public long getSize() {
        return size;
    }

    public void addToSize(long size) {
        this.size += size;
    }

    @Override
    public String toString() {
        return "File count: " + fileCount + " files \n"
            + "Folder count: " + dirCount + " folders \n"
            + "Size: " + FileUtils.byteCountToDisplaySize(size)
            + " (" + size + " bytes)";
    }
}
