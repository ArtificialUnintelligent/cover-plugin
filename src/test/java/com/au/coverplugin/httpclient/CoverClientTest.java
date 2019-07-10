package com.au.coverplugin.httpclient;

import com.au.coverplugin.domain.CoverCenterResponse;
import com.au.coverplugin.domain.Coverage;
import com.au.coverplugin.domain.Git;
import com.au.coverplugin.domain.Report;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.au.coverplugin.exception.ProcessingException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicStatusLine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @Author: ArtificialUnintelligent
 * @Description:
 * @Date: 11:25 AM 2019/1/22
 */
@RunWith(MockitoJUnitRunner.class)
public class CoverClientTest {

    @Mock
    private HttpResponse httpResponseMock;

    @Mock
    private HttpClient httpClientMock;

    @Mock
    private HttpEntity httpEntityMock;


    @Before
    public void init(){
    }

    @Test
    public void testConstructors(){
//        assertNotNull(new CoverClient(""));
//        assertNotNull(new CoverClient("", httpClientMock, new ObjectMapper()));
    }

    @Test
    public void testSubmit() throws IOException, ProcessingException {
        StatusLine statusLine = new BasicStatusLine(HttpVersion.HTTP_1_1, 200, "success");
        when(httpResponseMock.getStatusLine()).thenReturn(statusLine);
        when(httpClientMock.execute(any(HttpUriRequest.class))).thenReturn(httpResponseMock);
        when(httpResponseMock.getEntity()).thenReturn(httpEntityMock);
        when(httpEntityMock.getContent()).thenReturn(covercenterResponse(new CoverCenterResponse("success", "200", "dataTest", "traceIdTest", true)));
//        CoverClient coverClient = new CoverClient("");
    }

    private InputStream covercenterResponse(final CoverCenterResponse response)
        throws JsonProcessingException {
        String content = new ObjectMapper().writeValueAsString(response);
        return new ByteArrayInputStream(content.getBytes());
    }

    @Test
    public void submit() throws Exception {
        Git.Head head = new Git.Head("aea42c047133c9b1c49c97137c20df409bb343bb",
            "observer",
            "823655851@qq.com",
            "observer",
            "823655851@qq.com",
            "@test 使用coverplugin插件",
            new Date());
        List<Git.Remote> remotes = new ArrayList<>();
        //todo: pro url and group
        Git.Remote remote = new Git.Remote("origin", "", "");
        remotes.add(remote);
        //todo: pro address and branch
        Git git = new Git(new File(""), head, "", remotes);
//        ReportFactory reportFactory = new JacocoReportFactory("cupid-arrow", git, "src/test/files");
//        Report report = reportFactory.buildReport();
//        Assert.assertNotNull(report);

        Coverage coverage = new Coverage();
        coverage.setBranchCovered(100);
        coverage.setBranchMissed(10);
        coverage.setClassCovered(10);
        coverage.setClassMissed(20);
        coverage.setComplexityCovered(200);
        coverage.setComplexityMissed(500);
        coverage.setLineCovered(200);
        coverage.setLineMissed(500);
        Report r = new Report();
//        r.setFile(new File("src/test/files/target/coverage-xmlevent.zip"));
//        r.setFileName("coverage-xmlevent.zip");
        r.setGit(git);
        r.setCoverage(coverage);
//        System.setProperty("http.proxySet", "true");
//        System.setProperty("http.proxyHost", "172.18.10.209");172.18.10.209
//        System.setProperty("http.proxyPort", "8888");
        //TODO: cover center address
//        CoverClient coverClient = new CoverClient("http://localhost:10099/coverReports", r);
//        String id = coverClient.submit().getData();
//        CoverCenterResponse response = coverClient.upload(id);
//        System.out.println(response);
    }
}
