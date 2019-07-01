package com.au.coverplugin.util;

import com.au.coverplugin.domain.Coverage;
import com.au.coverplugin.domain.Git;
import com.au.coverplugin.domain.Report;
import com.au.coverplugin.domain.ReportDOC;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: ArtificialUnintelligent
 * @Description:
 * @Date: 11:31 AM 2019/1/28
 */
public class UrlUtils {

    private static final String VALUES = "=";

    private static final String CONNECTOR = "&";

    private static void buildUrlParams(Object object) throws IllegalAccessException {
        if (object == null)
            return;
        Class clazz = object.getClass();

        StringBuilder builder = new StringBuilder();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            if (builder.length() > 0){
                builder.append(CONNECTOR);
            }
            field.setAccessible(true);
            builder.append(field.getName()).append(VALUES).append(field.get(object));
        }
        System.out.println(builder.toString());
    }

    public static void main(String[] args) throws JsonProcessingException, IllegalAccessException {
        Git.Head head = new Git.Head("aea42c047133c9b1c49c97137c20df409bb343bb",
            "observer",
            "823655851@qq.com",
            "observer",
            "823655851@qq.com",
            "@test 使用coverplugin插件",
            new Date());
        List<Git.Remote> remotes = new ArrayList<>();
        //todo : git address and group
        Git.Remote remote = new Git.Remote("origin", "", "");
        remotes.add(remote);
        //todo : pro dir and branch
        Git git = new Git(new File(""), head, "", remotes);
//        ReportFactory reportFactory = new JacocoReportFactory("cupid-arrow", git, "src/test/files");
//        Report report = reportFactory.buildReport();
//        Assert.assertNotNull(report);

        Coverage coverage = new Coverage();
        coverage.setBranchCovered(100);
        coverage.setBranchMissed(10);
        coverage.setClassCovered(10);
        coverage.setClassMissed(20);
        coverage.setComplexityCovered(200);
        coverage.setComplexityMissed(500);
        coverage.setLineCovered(200);
        coverage.setLineMissed(500);
        Report r = new Report();
        r.setFile(new File("src/test/files/target/coverage-xmlevent.zip"));
        r.setFileName("coverage-xmlevent.zip");
        r.setGit(git);
        r.setCoverage(coverage);

        ReportDOC reportDOC = new ReportDOC();
        try {
            reportDOC.buildReportDOC(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(reportDOC));
        buildUrlParams(reportDOC);
    }
}
