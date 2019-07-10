package com.au.coverplugin.report;

import com.au.coverplugin.domain.Coverage;
import com.au.coverplugin.domain.Git;
import com.au.coverplugin.domain.Report;
import com.au.coverplugin.domain.ReportFile;
import com.au.coverplugin.util.ZipUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ArtificialUnintelligent
 * @Description: 生产report压缩文件
 * @Date: 2:50 PM 2019/1/18
 */
public abstract class ReportFactory {

    private static final String TARGET_TYPE;

    private static final String REPORT_NAME;

    private String project;

    private Git git;

    String sourcePath;

    String reportPath;

    private Report report = new Report();

    public ReportFactory(String project, Git git, String sourcePath, String reportPath) {
        this.project = project;
        this.git = git;
        this.sourcePath = sourcePath;
        this.reportPath = reportPath;
    }

    public ReportFile buildReportFile() throws IOException{
        File file = ZipUtils.compress(buildSource(), buildTarget());
        return new ReportFile(buildReportFileName(), file);
    }

    public Report buildReport() throws IOException {
        report.setProjectName(project);
        report.setGit(git);
        report.setCoverage(parsingCoverageReport());
        return report;
    }

    public Report buildHtmlReport() throws IOException {
        report.setProjectName(project);
        report.setGit(git);
        report.setCoverage(parsingHtmlCoverageReport());
        File file2 = new File(buildSource());
        File[] files = file2.listFiles();
        List<String> list = new ArrayList<>();
        assert files != null;
        for (File file1 : files) {
            if(file1.isDirectory() && (!file1.getName().contains("jacoco-resources"))){
                list.add(file1.getName());
            }
        }
        report.setPackageList(list);
        return report;
    }

    private String buildTarget(){
        return sourcePath + reportPath + buildReportFileName();
    }

    private String buildReportFileName(){
        return buildReportName() + TARGET_TYPE;
    }

    private String buildReportName(){
        return REPORT_NAME;
    }

    abstract String buildSource();

    abstract Coverage parsingCoverageReport();

    abstract Coverage parsingHtmlCoverageReport();

    static {
        TARGET_TYPE = ".zip";
        REPORT_NAME = "coverage-report";
    }
}
