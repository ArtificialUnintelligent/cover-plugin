package com.au.coverplugin.httpclient;

import com.alibaba.fastjson.JSON;
import com.au.coverplugin.domain.CoverCenterResponse;
import com.au.coverplugin.domain.Report;
import com.au.coverplugin.domain.ReportDOC;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.au.coverplugin.constant.ErrorCode;
import com.au.coverplugin.exception.ProcessingException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.StringUtils;

/**
 * @Author: ArtificialUnintelligent
 * @Description:
 * @Date: 4:30 PM 2019/1/21
 */
public class CoverClient {
    private static final ContentType MIME_TYPE = ContentType.create("application/octet-stream", "utf-8");
    private static final String UPLOAD_PATH = "/uploadFile";
    private static final String SUBMIT_PATH = "/uploadInfo";

    private final String covercenterUrl;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final Report report;

    public CoverClient(String covercenterUrl, Report report) {
        this(covercenterUrl, new HttpClientFactory().create(), new ObjectMapper(), report);
    }

    public CoverClient(String covercenterUrl, HttpClient httpClient,
        ObjectMapper objectMapper, Report report) {
        this.covercenterUrl = covercenterUrl;
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.report = report;
    }

    public CoverCenterResponse submit() throws Exception {
        ReportDOC reportDOC = new ReportDOC();
        reportDOC.buildReportDOC(report);
        StringEntity entity = new StringEntity(objectMapper.writeValueAsString(reportDOC), ContentType.APPLICATION_JSON);
        HttpPost post = new HttpPost(covercenterUrl + SUBMIT_PATH);
        post.setEntity(entity);
        HttpResponse response = httpClient.execute(post);
        return parseRsponse(response);
    }

    public CoverCenterResponse upload(final String id) throws Exception {
        List<String> packageList = report.getPackageList();
        String s = JSON.toJSONString(packageList);
        HttpEntity entity = MultipartEntityBuilder.create()
            .addBinaryBody("report", report.getFile(), MIME_TYPE, report.getFileName())
            .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
            .addTextBody("id", id)
            .addTextBody("packageListJson",s)
            .build();
        HttpPost post = new HttpPost(covercenterUrl + UPLOAD_PATH);
        post.setEntity(entity);
        HttpResponse response = httpClient.execute(post);
        return parseRsponse(response);
    }

    private CoverCenterResponse parseRsponse(final HttpResponse response)
        throws ProcessingException, IOException {
        HttpEntity entity = response.getEntity();
        ContentType contentType = ContentType.getOrDefault(entity);
        if (contentType.getCharset() == null) {
            throw new ProcessingException(getResponseErrorMessage(response, ErrorCode.CONTENT_TYPE_MISSING.getMessage()));
        }
        InputStreamReader reader = null;
        try {
            if (response.getStatusLine().getStatusCode() >= HttpStatus.SC_INTERNAL_SERVER_ERROR) {
                throw new IOException(ErrorCode.COVERCENTER_API_ERROR.getMessage());
            }
            reader = new InputStreamReader(entity.getContent(), contentType.getCharset());
            CoverCenterResponse cr = objectMapper.readValue(reader, CoverCenterResponse.class);
            if (!cr.getSuccess()) {
                throw new ProcessingException(getResponseErrorMessage(response, cr.getMsg()));
            }
            return cr;
        }catch (JsonProcessingException e){
            throw new ProcessingException(getResponseErrorMessage(response, e.getMessage()), e);
        } catch (IOException e) {
            throw new IOException(getResponseErrorMessage(response, e.getMessage()), e);
        }finally {
            IOUtil.close(reader);
        }
    }

    private String getResponseErrorMessage(final HttpResponse response, final String message) {
        int status = response.getStatusLine().getStatusCode();
        String reason = response.getStatusLine().getReasonPhrase();
        StringBuilder builder = new StringBuilder();
        builder.append(ErrorCode.REPORT_SUBMIT_FAIL.getMessage()).append(status).append(":");
        if (StringUtils.isNotBlank(reason)) {
            builder.append(reason);
        }
        if (StringUtils.isNotBlank(message)) {
            builder.append(message);
        }
        return builder.toString();
    }
}
