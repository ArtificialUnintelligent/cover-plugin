package com.au.coverplugin.parser;

import com.au.coverplugin.domain.Coverage;

import java.io.File;

/**
 * @Author: ArtificialUnintelligent
 * @Description:
 * @Date: 3:59 PM 2019/1/23
 */
public abstract class BaseParser {

    private final File coverageFile;

    public BaseParser(File coverageFile) {
        this.coverageFile = coverageFile;
    }

    public abstract Coverage parse();

    public abstract Coverage parseHtml(String source);

    public File getCoverageFile() {
        return coverageFile;
    }
}
