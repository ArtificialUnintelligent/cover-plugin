package com.au.coverplugin.reader;

import com.au.coverplugin.util.FileReader;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Test;

import java.io.IOException;

/**
 * @author:artificialunintelligent
 * @Date:2019-07-10
 * @Time:10:30
 */
public class XmlReader {

    @Test
    public void read(){
        try {
            String xml = FileReader.readToString("src/test/target/site/jacoco/jacoco.xml");

            //将xml转为json
            JSONObject xmlJSONObj = XML.toJSONObject(xml);
            //设置缩进
            String jsonPrettyPrintString = xmlJSONObj.toString(4);
            //输出格式化后的json
            System.out.println(jsonPrettyPrintString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
