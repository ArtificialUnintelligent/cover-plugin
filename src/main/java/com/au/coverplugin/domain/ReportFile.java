package com.au.coverplugin.domain;

import java.io.File;

/**
 * @author:artificialunintelligent
 * @Date:2019-07-09
 * @Time:16:29
 */
public class ReportFile {

    private String fileName;

    private File file;

    public ReportFile(String fileName, File file) {
        this.fileName = fileName;
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
