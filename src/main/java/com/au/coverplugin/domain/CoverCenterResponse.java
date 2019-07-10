package com.au.coverplugin.domain;

import com.au.coverplugin.constant.ErrorCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author: ArtificialUnintelligent
 * @Description: 覆盖率中心的返回结果
 * @Date: 10:02 AM 2019/1/22
 */
public class CoverCenterResponse implements JsonObject{

    private final String msg;
    private final String code;
    private final String data;
    private final Boolean success;
    private final String traceId;

    @JsonCreator
    public CoverCenterResponse(
        @JsonProperty("msg") final String msg,
        @JsonProperty("code") final String code,
        @JsonProperty("data") final String data,
        @JsonProperty("traceId") final String traceId,
        @JsonProperty("success") final Boolean success) {
        this.msg = msg;
        this.code = code;
        this.data = data;
        this.success = success;
        this.traceId = traceId;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public String getData() {
        return data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getTraceId() {
        return traceId;
    }
}
