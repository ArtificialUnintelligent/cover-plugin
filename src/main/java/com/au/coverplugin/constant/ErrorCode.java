package com.au.coverplugin.constant;

/**
 * @Author: ArtificialUnintelligent
 * @Description:
 * @Date: 2:24 PM 2019/1/22
 */
public enum  ErrorCode {

    COVERCENTER_API_ERROR("Covercenter API internal error"),
    CONTENT_TYPE_MISSING("Response doesn't contain Content-Type header"),
    REPORT_SUBMIT_FAIL("Report submission to Covercenter API failed with HTTP status"),
    SKIP_PLUGIN("Skip property set, skipping cover-plugin execution"),
    UPLOAD_ERROR("upload report failed"),
    NON_EXISTENT(" file is non-existent"),
    GET_FILE_ERROR("get report file error")
    ;

    private String message;

    ErrorCode(java.lang.String message) {
        this.message = message;
    }

    public java.lang.String getMessage() {
        return message;
    }

    public void setMessage(java.lang.String message) {
        this.message = message;
    }
}
