package com.acrobotics.v1.File;

import android.os.Environment;
import java.io.File;

/**
 * <p><strong>InternalFile System</strong></p>
 * <p>
 * This class wraps standard Java File objects to make handling paths on the
 * Control Hub easier. It automatically handles creating missing directories
 * and provides safe methods for renaming or moving files.
 * </p>
 *
 * @author Criddle
 * @version 2.0 (Refined for robustness)
 */
public class InternalFile {

    private String filePath;
    private String fileName;
    protected File fileObject;
    private boolean existsCache = false;

    /**
     * Creates a file reference.
     * Use this constructor to point to a specific file in the Standard FTC Dir.
     *
     * @param fileName The name of the file (e.g., "RobotConfig.xml")
     */
    public InternalFile(String fileName) {
        // Default to the standard FIRST storage directory to avoid path mess
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        this.filePath = root;
        this.fileName = fileName;

        init();
    }

    /**
     * Creates a file reference with a custom path.
     *
     * @param path The custom directory path.
     * @param fileName The name of the file.
     */
    public InternalFile(String path, String fileName) {
        this.filePath = path;
        this.fileName = fileName;
        init();
    }

    private void init() {
        this.fileObject = new File(filePath, fileName);
        ensureDirectoryExists();
        this.existsCache = fileObject.exists();
    }

    /**
     * Checks if the parent directory exists; if not, creates it.
     * This prevents "FileNotFoundException" during write operations.
     */
    public void ensureDirectoryExists() {
        File parent = fileObject.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }

    /**
     * Renames the current file.
     * @param newName The new name (including extension).
     * @return The updated InternalFile instance.
     */
    public InternalFile renameFile(String newName) {
        if (exists()) {
            File newFile = new File(this.filePath, newName);
            boolean success = fileObject.renameTo(newFile);
            if (success) {
                this.fileName = newName;
                this.fileObject = newFile;
            }
        }
        return this;
    }

    /**
     * @return True if the physical file exists on the disk.
     */
    public boolean exists() {
        // Update cache
        existsCache = fileObject.exists();
        return existsCache;
    }

    /**
     * @return The raw Java File object.
     */
    public File getFile() {
        return this.fileObject;
    }

    public String getName() {
        return this.fileName;
    }

    public String getPath() {
        return this.filePath;
    }
}