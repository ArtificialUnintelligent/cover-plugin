package com.au.coverplugin;

import com.au.coverplugin.domain.CoverCenterResponse;
import com.au.coverplugin.domain.Git;
import com.au.coverplugin.domain.GitRepository;
import com.au.coverplugin.domain.Report;
import com.au.coverplugin.report.JacocoReportFactory;
import com.au.coverplugin.constant.ErrorCode;
import com.au.coverplugin.httpclient.CoverClient;
import com.au.coverplugin.report.ReportFactory;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @Author: ArtificialUnintelligent
 * @Description:
 * @Date: 6:12 PM 2019/1/17
 */
@Mojo(name = "cover-report", defaultPhase = LifecyclePhase.PACKAGE, requiresDirectInvocation = true)
public class CoverReportMojo extends AbstractMojo{

    @Parameter(property = "target", defaultValue = "${project.build.directory}")
    protected String target;

    @Parameter(defaultValue = "${project.basedir}")
    protected File basedir;

    @Parameter(property = "skip", defaultValue = "false")
    protected Boolean skip;

    @Parameter(defaultValue = "${project.name}")
    protected String project;

    //TODO:指定上传地址
    @Parameter(property = "covercenterUrl", defaultValue = "")
    protected String covercenterUrl;

    @Parameter(property = "report", defaultValue = "jacoco")
    protected String report;
    public  static  int count = 0;
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        count++;
        if(count > 1){
            return;
        }
        getLog().info("param info : "
            + "target path->" + target
            + "|skip->" + skip
            + "|project->" + project
            + "|basedir->" + basedir);
        if (Objects.nonNull(skip) && skip){
            getLog().info(ErrorCode.SKIP_PLUGIN.getMessage());
            return;
        }
        try {
            Git git = getGitInfo();
            ReportFactory factory = selectFactory(git);
            if (Objects.isNull(factory)){
                getLog().error("no match report type");
                return;
            }
            Report report = factory.buildHtmlReport();
            CoverClient coverClient = new CoverClient(covercenterUrl, report);
            String id = coverClient.submit().getData();
            CoverCenterResponse response = coverClient.upload(id);
            getLog().info(response.getData());
        } catch (IOException e) {
            getLog().error("压缩报告时发生错误:", e);
        } catch (Exception e){
            getLog().error(ErrorCode.UPLOAD_ERROR.getMessage());
            getLog().error(e);
        }
    }

    private ReportFactory selectFactory(Git git) throws IOException {
        if ("jacoco".equals(report)){
            return new JacocoReportFactory(project, git, target);
        }
        return null;
    }

    private Git getGitInfo() throws IOException {
        return new GitRepository(basedir).load();
    }
}
