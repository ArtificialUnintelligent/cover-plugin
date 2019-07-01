package com.au.coverplugin.util;

import com.au.coverplugin.constant.ErrorCode;

import java.io.*;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author: ArtificialUnintelligent
 * @Description:
 * @Date: 10:38 AM 2019/1/18
 */
public class ZipUtils {

    public static File compress(String source, String target) throws IOException {
        File src = new File(source);
        if (!src.exists()) {
            throw new RuntimeException(source + ErrorCode.NON_EXISTENT.getMessage());
        }
        File zipFile = new File(target);
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zos = new ZipOutputStream(fos);
        String baseDir = "";
        compressByType(src, zos, baseDir);
        zos.close();
        return zipFile;
    }

    /**
     * 按照原路径的类型就行压缩。文件路径直接把文件压缩，
     * @param src
     * @param zos
     * @param baseDir
     */
    private static void compressByType(File src, ZipOutputStream zos, String baseDir) {
        if (!src.exists())
            return;
        if (src.isFile()) {
            compressFile(src, zos, baseDir);
        } else if (src.isDirectory()) {
            compressDir(src, zos, baseDir);
        }
    }

    private static void compressFile(File file, ZipOutputStream zos, String baseDir) {
        if (!file.exists())
            return;
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            ZipEntry entry = new ZipEntry(baseDir + file.getName());
            zos.putNextEntry(entry);
            int count;
            byte[] buf = new byte[1024];
            while ((count = bis.read(buf)) != -1) {
                zos.write(buf, 0, count);
            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void compressDir(File dir, ZipOutputStream zos, String baseDir) {
        if (!dir.exists())
            return;
        File[] files = dir.listFiles();
        if (Objects.isNull(files)){
            return;
        }
        if(Objects.equals(files.length, 0)){
            try {
                zos.putNextEntry(new ZipEntry(baseDir + dir.getName() + File.separator));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (File file : files) {
            compressByType(file, zos, baseDir + dir.getName() + File.separator);
        }
    }
}
