package com.au.coverplugin.constant;

/**
 * @Author: ArtificialUnintelligent
 * @Description:
 * @Date: 3:14 PM 2019/1/24
 */
public enum CoverageEnum {
    INSTRUCTION("INSTRUCTION"),
    BRANCH("BRANCH"),
    LINE("LINE"),
    COMPLEXITY("COMPLEXITY"),
    METHOD("METHOD"),
    CLASS("CLASS")
    ;

    private String type;

    CoverageEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
