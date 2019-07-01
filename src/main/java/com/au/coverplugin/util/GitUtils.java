package com.au.coverplugin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Author: ArtificialUnintelligent
 * @Description: 通过调用git指令获取分支名称
 * @Date: 5:41 PM 2019/1/18
 */
public class GitUtils {

    private static final String INSTRUCTION_BRANCH = "git symbolic-ref --short HEAD";

    public static String getBranch() throws IOException {
        Process process = Runtime.getRuntime().exec(INSTRUCTION_BRANCH);
        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null){
            result.append(line);
        }
        inputStream.close();
        inputStreamReader.close();
        bufferedReader.close();
        return result.toString();
    }

}
