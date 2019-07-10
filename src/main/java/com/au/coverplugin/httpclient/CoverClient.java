package com.au.coverplugin.httpclient;

import com.alibaba.fastjson.JSON;
import com.au.coverplugin.domain.CoverCenterResponse;
import com.au.coverplugin.domain.Report;
import com.au.coverplugin.domain.ReportDOC;
import com.au.coverplugin.domain.ReportFile;
import com.au.coverplugin.report.ReportFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.au.coverplugin.constant.ErrorCode;
import com.au.coverplugin.exception.ProcessingException;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

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
//    private static final String UPLOAD_PATH = "/uploadFile";
//    private static final String SUBMIT_PATH = "/uploadInfo";

    private final String url;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final ReportFactory factory;

    /**
     * 报告提交工具
     * @param url 上传地址 - 可以是上传报告、提交报告概述、提交报告解析结果的接口地址
     * @param factory
     */
    public CoverClient(String url, ReportFactory factory) {
        this(url, new HttpClientFactory().create(), new ObjectMapper(), factory);
    }

    public CoverClient(String url, HttpClient httpClient,
        ObjectMapper objectMapper, ReportFactory factory) {
        this.url = url;
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.factory = factory;
    }

    /**
     * 提交报告概述
     * @return
     * @throws Exception
     */
    public CoverCenterResponse submitSummary() throws Exception {
        Report report = factory.buildHtmlReport();
        if (Objects.isNull(report)){
            throw new ProcessingException(ErrorCode.GET_FILE_ERROR.getMessage());
        }
        ReportDOC reportDOC = new ReportDOC();
        reportDOC.buildReportDOC(report);
        StringEntity entity = new StringEntity(objectMapper.writeValueAsString(reportDOC), ContentType.APPLICATION_JSON);
        HttpPost post = new HttpPost(url);
        post.setEntity(entity);
        HttpResponse response = httpClient.execute(post);
        return parseRsponse(response);
    }

    /**
     * 提交报告详细内容-json格式
     * @return
     * @throws Exception
     */
    public CoverCenterResponse submitDetail() throws Exception {
        String reportDetail = "";
        StringEntity entity = new StringEntity(reportDetail, ContentType.APPLICATION_JSON);
        HttpPost post = new HttpPost(url);
        post.setEntity(entity);
        HttpResponse response = httpClient.execute(post);
        return parseRsponse(response);
    }

    /**
     * 上传报告压缩文件
     * @return
     * @throws Exception
     */
    public CoverCenterResponse upload() throws Exception {
        ReportFile file = factory.buildReportFile();
        HttpEntity entity = MultipartEntityBuilder.create()
            .addBinaryBody("report", file.getFile(), MIME_TYPE, file.getFileName())
            .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
            .build();
        HttpPost post = new HttpPost(url);
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
