package com.au.coverplugin.parser;

import com.au.coverplugin.domain.Coverage;
import com.au.coverplugin.domain.xmlevent.JacocoReportEvent;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @Author: ArtificialUnintelligent
 * @Description:
 * @Date: 4:15 PM 2019/1/23
 */
public class JacocoParser extends BaseParser {

    public JacocoParser(File coverageFile) {
        super(coverageFile);
    }

    @Override
    public Coverage parse() {
        File coverageFile = getCoverageFile();
        XStream xs = new XStream();
        XStream.setupDefaultSecurity(xs);
        xs.allowTypes(new Class[]{JacocoReportEvent.class});
        xs.autodetectAnnotations(true);
        xs.processAnnotations(JacocoReportEvent.class);
        JacocoReportEvent jacocoReportEvent = (JacocoReportEvent) xs.fromXML(coverageFile);
        return Coverage.buildJacocoCoverage(jacocoReportEvent.getCounters().stream());
    }

    @Override
    public Coverage parseHtml(String source) {
        File file =  getCoverageFile();
        File[] files = file.listFiles();
        int instruction_covered = 0;
        int instruction_missed= 0;
        int branch_covered= 0;
        int branch_missed= 0;
        int line_covered= 0;
        int line_missed= 0;
        int complexity_covered= 0;
        int complexity_missed= 0;
        int method_covered= 0;
        int method_missed= 0;
        int class_covered= 0;
        int class_missed= 0;
        for (File file1 : files) {
            if(file1.isDirectory() && (!file1.getName().contains("jacoco-resources"))){
                String html =source+ "/"+file1.getName()+"/index.html";
                File input = new File(html);
                Document doc = null;
                try {
                    doc = Jsoup.parse(input, "UTF-8" );
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Element body = doc.body();
                Elements tr = body.getElementsByTag("tr");
                ListIterator<Element> elementListIterator = tr.listIterator();
                while (elementListIterator.hasNext()){
                    Element next = elementListIterator.next();
                    String text = next.text();
                    text = text.replace(",", "");
                    if(text.contains("Total")){
                        String[] s = text.split(" ");
                        List<String> list = Arrays.asList(s);
                        instruction_covered += Integer.valueOf(list.get(3))-Integer.valueOf(list.get(1));
                        instruction_missed +=Integer.valueOf(list.get(1));
                        branch_covered += Integer.valueOf(list.get(7))-Integer.valueOf(list.get(5));
                        branch_missed+=Integer.valueOf(list.get(5));
                        line_covered += Integer.valueOf(list.get(12))-Integer.valueOf(list.get(11));
                        line_missed += Integer.valueOf(list.get(11));
                        complexity_covered += Integer.valueOf(list.get(10))-Integer.valueOf(list.get(9));
                        complexity_missed += Integer.valueOf(list.get(9));
                        method_covered+= Integer.valueOf(list.get(14))-Integer.valueOf(list.get(13));
                        method_missed += Integer.valueOf(list.get(13));
                        class_covered += Integer.valueOf(list.get(14))-Integer.valueOf(list.get(13));
                        class_missed += Integer.valueOf(list.get(15));
                    }
                }

            }

        }

        Coverage coverage = new Coverage();
        coverage.setInstructionCovered(instruction_covered);
        coverage.setInstructionMissed(instruction_missed);
        coverage.setBranchCovered(branch_covered);
        coverage.setBranchMissed(branch_missed);
        coverage.setLineCovered(line_covered);
        coverage.setLineMissed(line_missed);
        coverage.setComplexityCovered(complexity_covered);
        coverage.setComplexityMissed(complexity_missed);
        coverage.setMethodCovered(method_covered);
        coverage.setMethodMissed(method_missed);
        coverage.setClassCovered(class_covered);
        coverage.setClassMissed(class_missed);
        return coverage;
    }
}
