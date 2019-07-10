package com.au.coverplugin.util;

import java.io.*;

/**
 * @author:artificialunintelligent
 * @Date:2019-07-10
 * @Time:10:38
 */
public class FileReader {

    public static String readToString(String fileName) throws IOException {
        String encoding = "GBK";
        File file = new File(fileName);
        long filelength = file.length();
        byte[] filecontent = new byte[(int) filelength];
        FileInputStream in = new FileInputStream(file);
        in.read(filecontent);
        in.close();
        return new String(filecontent, encoding);
    }
}
