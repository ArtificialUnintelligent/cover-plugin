package com.au.coverplugin.domain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

/**
 * @Author: ArtificialUnintelligent
 * @Description: 思考-->以下两点是否有更好的做法？
 *               1. 在使用gitlab-runner运行插件时，job会重命名分支，导致jgit无法获取当前分支名称
 *               因此目前根据位数判断区分分支名称和HEAD ID
 *               2. gitlab-runner下，jgit获取到的项目地址类似http://gitlab-ci-token:s3cr3tt0k3n@192.168.1.23/namespace/project.git
 *               因此插件根据@符号做了划分，以排除token
 * @Date: 2:14 PM 2019/1/26
 */
public class GitRepository {

    private final File sourceDirectory;

    public GitRepository(final File sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    public Git load() throws IOException {
        try (Repository repository = new RepositoryBuilder().findGitDir(this.sourceDirectory).build()) {
            Git.Head head = getHead(repository);
            String branch = getBranch(repository);
            List<Git.Remote> remotes = getRemotes(repository);
            return new Git(repository.getWorkTree(), head, branch, remotes);
        }
    }

    private Git.Head getHead(final Repository repository) throws IOException {
        ObjectId revision = repository.resolve(Constants.HEAD);
        RevCommit commit = new RevWalk(repository).parseCommit(revision);
        Git.Head head = new Git.Head(
            revision.getName(),
            commit.getAuthorIdent().getName(),
            commit.getAuthorIdent().getEmailAddress(),
            commit.getCommitterIdent().getName(),
            commit.getCommitterIdent().getEmailAddress(),
            commit.getFullMessage(),
            commit.getAuthorIdent().getWhen()
        );
        return head;
    }

    /**
     * jgit的不足-->分支被重命名，返回HEAD ID
     * 分支被重命名的情况下，通过HEAD ID进行关联，从远程获取分支名
     * @param repository
     * @return
     * @throws IOException
     */
    private String getBranch(final Repository repository) throws IOException {
        String branch = repository.getBranch();
        if (branch.length() != 40){
            return branch;
        }
        Map<String, Ref> refMap = repository.getAllRefs();
        final String[] b = {""};
        refMap.keySet().forEach(k -> {
            Ref v = refMap.get(k);
            if (v.getObjectId().toString().contains(branch) && !Objects.equals(v.getName(), "HEAD")){
                b[0] = v.getName().substring("refs/remotes/origin/".length());
            }
        });
        return b[0];
    }

    /**
     * 处理gitlab-runner拼接的token
     * @param repository
     * @return
     */
    private List<Git.Remote> getRemotes(final Repository repository) {
        Config config = repository.getConfig();
        List<Git.Remote> remotes = new ArrayList<>();
        for (String remote : config.getSubsections("remote")) {
            String url = config.getString("remote", remote, "url");
            if (url.contains("@")){
                url = "https://" + (url.split("@")[1]).replace(":","/");

            }
            String group = url.split("/")[3];
            remotes.add(new Git.Remote(remote, url, group));
        }
        return remotes;
    }
}
