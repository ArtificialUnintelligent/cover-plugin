package com.au.coverplugin.parser;

import com.au.coverplugin.domain.Coverage;

import java.io.File;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Author: ArtificialUnintelligent
 * @Description:
 * @Date: 4:48 PM 2019/1/23
 */
public class JacocoParserTest {

    @Test
    public void parseTest(){
        File file = new File("src/test/files/jacoco.xml");
        BaseParser parser = new JacocoParser(file);
        Coverage coverage = parser.parse();
        Assert.assertNotNull(coverage);
    }

}
