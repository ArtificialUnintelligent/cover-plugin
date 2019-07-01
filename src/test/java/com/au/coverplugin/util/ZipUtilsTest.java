package com.au.coverplugin.util;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @Author: ArtificialUnintelligent
 * @Description:
 * @Date: 11:19 AM 2019/1/18
 */
public class ZipUtilsTest {

    @Test
    public void compressTest(){
        File file = null;
        try {
            //TODO:source dir
            file = ZipUtils.compress("", "./");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(file);
    }
}
