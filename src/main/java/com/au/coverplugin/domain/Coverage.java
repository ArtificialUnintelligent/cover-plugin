package com.au.coverplugin.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.au.coverplugin.constant.CoverageEnum;
import com.au.coverplugin.domain.xmlevent.JacocoCounter;
import java.util.stream.Stream;

/**
 * @Author: ArtificialUnintelligent
 * @Description:
 * @Date: 2:57 PM 2019/1/24
 */
public class Coverage implements JsonObject{

    @JsonProperty
    private Integer instructionCovered;

    @JsonProperty
    private Integer instructionMissed;

    @JsonProperty
    private Integer branchCovered;

    @JsonProperty
    private Integer branchMissed;

    @JsonProperty
    private Integer lineCovered;

    @JsonProperty
    private Integer lineMissed;

    @JsonProperty
    private Integer complexityCovered;

    @JsonProperty
    private Integer complexityMissed;

    @JsonProperty
    private Integer methodCovered;

    @JsonProperty
    private Integer methodMissed;

    @JsonProperty
    private Integer classCovered;

    @JsonProperty
    private Integer classMissed;

    public static Coverage buildJacocoCoverage(Stream<JacocoCounter> l){
        Coverage coverage = new Coverage();
        l.forEach(v -> {
            CoverageEnum e = CoverageEnum.valueOf(v.getType());
            switch (e) {
                case INSTRUCTION:
                    coverage.instructionCovered = Integer.valueOf(v.getCovered());
                    coverage.instructionMissed = Integer.valueOf(v.getMissed());
                    break;
                case BRANCH:
                    coverage.branchCovered = Integer.valueOf(v.getCovered());
                    coverage.branchMissed = Integer.valueOf(v.getMissed());
                    break;
                case LINE:
                    coverage.lineCovered = Integer.valueOf(v.getCovered());
                    coverage.lineMissed = Integer.valueOf(v.getMissed());
                    break;
                case COMPLEXITY:
                    coverage.complexityCovered = Integer.valueOf(v.getCovered());
                    coverage.complexityMissed = Integer.valueOf(v.getMissed());
                    break;
                case METHOD:
                    coverage.methodCovered = Integer.valueOf(v.getCovered());
                    coverage.methodMissed = Integer.valueOf(v.getMissed());
                    break;
                case CLASS:
                    coverage.classCovered = Integer.valueOf(v.getCovered());
                    coverage.classMissed = Integer.valueOf(v.getMissed());
                    break;
            }
        });
        return coverage;
    }

    public Integer getInstructionCovered() {
        return instructionCovered;
    }

    public void setInstructionCovered(Integer instructionCovered) {
        this.instructionCovered = instructionCovered;
    }

    public Integer getInstructionMissed() {
        return instructionMissed;
    }

    public void setInstructionMissed(Integer instructionMissed) {
        this.instructionMissed = instructionMissed;
    }

    public Integer getBranchCovered() {
        return branchCovered;
    }

    public void setBranchCovered(Integer branchCovered) {
        this.branchCovered = branchCovered;
    }

    public Integer getBranchMissed() {
        return branchMissed;
    }

    public void setBranchMissed(Integer branchMissed) {
        this.branchMissed = branchMissed;
    }

    public Integer getLineCovered() {
        return lineCovered;
    }

    public void setLineCovered(Integer lineCovered) {
        this.lineCovered = lineCovered;
    }

    public Integer getLineMissed() {
        return lineMissed;
    }

    public void setLineMissed(Integer lineMissed) {
        this.lineMissed = lineMissed;
    }

    public Integer getComplexityCovered() {
        return complexityCovered;
    }

    public void setComplexityCovered(Integer complexityCovered) {
        this.complexityCovered = complexityCovered;
    }

    public Integer getComplexityMissed() {
        return complexityMissed;
    }

    public void setComplexityMissed(Integer complexityMissed) {
        this.complexityMissed = complexityMissed;
    }

    public Integer getMethodCovered() {
        return methodCovered;
    }

    public void setMethodCovered(Integer methodCovered) {
        this.methodCovered = methodCovered;
    }

    public Integer getMethodMissed() {
        return methodMissed;
    }

    public void setMethodMissed(Integer methodMissed) {
        this.methodMissed = methodMissed;
    }

    public Integer getClassCovered() {
        return classCovered;
    }

    public void setClassCovered(Integer classCovered) {
        this.classCovered = classCovered;
    }

    public Integer getClassMissed() {
        return classMissed;
    }

    public void setClassMissed(Integer classMissed) {
        this.classMissed = classMissed;
    }
}
