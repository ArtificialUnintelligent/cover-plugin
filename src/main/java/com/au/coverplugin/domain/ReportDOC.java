package com.au.coverplugin.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.au.coverplugin.domain.Git.Remote;
import java.util.List;

/**
 * @Author: ArtificialUnintelligent
 * @Description:
 * @Date: 4:12 PM 2019/1/26
 */
public class ReportDOC implements JsonObject{

    @JsonProperty
    private String projectName;

    @JsonProperty
    private String projectPath;

    @JsonProperty
    private String projectBranch;

    @JsonProperty
    private String projectGroupName;

    @JsonProperty
    private String committer;

    @JsonProperty
    private String commitMessage;

    @JsonProperty
    private String commitTime;

    @JsonProperty
    private Coverage coverage;

    public void buildReportDOC(Report report) throws Exception{
        projectName = report.getProjectName();
        coverage = report.getCoverage();
        buildGitInfo(report.getGit());
    }

    private void buildGitInfo(Git git) throws Exception{
        projectBranch = git.getBranch();
        committer = git.getHead().getCommitterName();
        commitMessage = git.getHead().getMessage();
        commitTime = String.valueOf(git.getHead().getCommitTime().getTime());
        List<Remote> remotes = git.getRemotes();
        if (remotes.isEmpty()){
            return;
        }
        Remote remote = remotes.get(0);
        projectPath = remote.getUrl();
        projectGroupName = remote.getGroup();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getProjectBranch() {
        return projectBranch;
    }

    public void setProjectBranch(String projectBranch) {
        this.projectBranch = projectBranch;
    }

    public String getCommitter() {
        return committer;
    }

    public void setCommitter(String committer) {
        this.committer = committer;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }

    public Coverage getCoverage() {
        return coverage;
    }

    public void setCoverage(Coverage coverage) {
        this.coverage = coverage;
    }

    public String getProjectGroupName() {
        return projectGroupName;
    }

    public void setProjectGroupName(String projectGroupName) {
        this.projectGroupName = projectGroupName;
    }
}
