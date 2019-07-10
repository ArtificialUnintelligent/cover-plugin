package com.au.coverplugin;

import com.au.coverplugin.constant.ErrorCode;
import com.au.coverplugin.domain.CoverCenterResponse;
import com.au.coverplugin.domain.Git;
import com.au.coverplugin.domain.GitRepository;
import com.au.coverplugin.httpclient.CoverClient;
import com.au.coverplugin.report.JacocoReportFactory;
import com.au.coverplugin.report.ReportFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author: ArtificialUnintelligent
 * @Description: 单测覆盖率上传插件入口
 * @Date: 6:12 PM 2019/1/17
 */
@Mojo(name = "cover-report", defaultPhase = LifecyclePhase.NONE, requiresDirectInvocation = true)
public class CoverReportMojo extends AbstractMojo{

    @Parameter(property = "target", defaultValue = "${project.build.directory}")
    protected String target;

    @Parameter(defaultValue = "${project.basedir}")
    protected File basedir;

    @Parameter(property = "skip", defaultValue = "false")
    protected Boolean skip;

    @Parameter(defaultValue = "${project.name}")
    protected String project;

    /**
     * 整体信息上传地址（包括项目的基本信息以及项目整体覆盖率情况）
     */
    @Parameter(property = "uploadSummaryUrl")
    protected String uploadSummaryUrl;

    /**
     * 报告压缩文件上传地址
     */
    @Parameter(property = "uploadReportUrl")
    protected String uploadReportUrl;

    /**
     * 报告详情上传地址
     */
    @Parameter(property = "uploadDetailUrl")
    protected String uploadDetailUrl;

    /**
     * 报告类型（默认jacoco，当前版本只支持jacoco）
     */
    @Parameter(property = "type", defaultValue = "jacoco")
    protected String type;

    /**
     * 报告路径-需要和报告插件指定生成报告的路径一致 例如 jacoco：jacoco-site对应/site/jacoco
     */
    @Parameter(property = "reportDir")
    protected String reportDir;

    public  static  int count = 0;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        //这里在配置多模块项目的时候可能会有只提交子模块报告的可能，不过还是建议把插件放在父模块
//        count++;
//        if(count > 1){
//            return;
//        }
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
            submit(factory);
        } catch (IOException e) {
            getLog().error("压缩报告时发生错误:", e);
        } catch (Exception e){
            getLog().error(ErrorCode.UPLOAD_ERROR.getMessage());
            getLog().error(e);
        }
    }

    private void submit(ReportFactory factory) throws Exception {
        if (StringUtils.isNotBlank(uploadSummaryUrl)){
            CoverClient coverClient = new CoverClient(uploadSummaryUrl, factory);
            CoverCenterResponse response = coverClient.submitSummary();
            getLog().info(response.getData());
        }
        if (StringUtils.isNotBlank(uploadReportUrl)){
            CoverClient coverClient = new CoverClient(uploadReportUrl, factory);
            CoverCenterResponse response = coverClient.upload();
            getLog().info(response.getData());
        }
        if (StringUtils.isNotBlank(uploadDetailUrl)){
            CoverClient coverClient = new CoverClient(uploadDetailUrl, factory);
            CoverCenterResponse response = coverClient.submitDetail();
            getLog().info(response.getData());
        }
    }

    private ReportFactory selectFactory(Git git) throws IOException {
        if ("jacoco".equals(type)){
            return new JacocoReportFactory(project, git, target, reportDir);
        }
        return null;
    }

    private Git getGitInfo() throws IOException {
        return new GitRepository(basedir).load();
    }
}
