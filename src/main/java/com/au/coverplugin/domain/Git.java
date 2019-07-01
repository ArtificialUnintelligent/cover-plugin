package com.au.coverplugin.domain;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: ArtificialUnintelligent
 * @Description:
 * @Date: 2:12 PM 2019/1/26
 */
public class Git{

    private final File baseDir;

    private final Head head;

    private final String branch;

    private final List<Remote> remotes;

    public Git(final File baseDir, final Head head, final String branch, final List<Remote> remotes) {
        this.baseDir = baseDir;
        this.head = head;
        this.branch = branch;
        this.remotes = remotes;
    }

    public File getBaseDir() {
        return baseDir;
    }

    public Head getHead() {
        return head;
    }

    public String getBranch() {
        return branch;
    }

    public List<Remote> getRemotes() {
        return remotes;
    }

    public static class Head implements Serializable {
        private final String id;

        private final String authorName;

        private final String authorEmail;

        private final String committerName;

        private final String committerEmail;

        private final String message;

        private final Date commitTime;

        public Head(String id, String authorName, String authorEmail, String committerName,
            String committerEmail, String message, Date commitTime) {
            this.id = id;
            this.authorName = authorName;
            this.authorEmail = authorEmail;
            this.committerName = committerName;
            this.committerEmail = committerEmail;
            this.message = message;
            this.commitTime = commitTime;
        }

        public String getId() {
            return id;
        }

        public String getAuthorName() {
            return authorName;
        }

        public String getAuthorEmail() {
            return authorEmail;
        }

        public String getCommitterName() {
            return committerName;
        }

        public String getCommitterEmail() {
            return committerEmail;
        }

        public String getMessage() {
            return message;
        }

        public Date getCommitTime() {
            return commitTime;
        }

        @Override
        public String toString() {
            return "Head{" +
                "id='" + id + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorEmail='" + authorEmail + '\'' +
                ", committerName='" + committerName + '\'' +
                ", committerEmail='" + committerEmail + '\'' +
                ", message='" + message + '\'' +
                ", commitTime=" + commitTime +
                '}';
        }
    }

    public static class Remote implements Serializable {
        private final String name;

        private final String url;

        private final String group;

        public Remote(final String name, final String url, final String group) {
            this.name = name;
            this.url = url;
            this.group = group;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public String getGroup() {
            return group;
        }

        @Override
        public String toString() {
            return "Remote{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", group='" + group + '\'' +
                '}';
        }
    }

    @Override
    public String toString() {
        return "Git{" +
            "baseDir=" + baseDir +
            ", head=" + head +
            ", branch='" + branch + '\'' +
            ", remotes=" + remotes +
            '}';
    }
}
