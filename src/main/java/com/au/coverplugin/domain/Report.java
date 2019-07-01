package com.au.coverplugin.domain;

import java.io.File;
import java.util.List;

/**
 * @Author: ArtificialUnintelligent
 * @Description:
 * @Date: 3:22 PM 2019/1/18
 */
public class Report {

    private String projectName;

    private Git git;

    private File file;

    private String fileName;

    private Coverage coverage;

    private List<String> packageList;


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Coverage getCoverage() {
        return coverage;
    }

    public void setCoverage(Coverage coverage) {
        this.coverage = coverage;
    }

    public Git getGit() {
        return git;
    }

    public void setGit(Git git) {
        this.git = git;
    }

    public List<String> getPackageList() {
        return packageList;
    }

    public void setPackageList(List<String> packageList) {
        this.packageList = packageList;
    }
}
