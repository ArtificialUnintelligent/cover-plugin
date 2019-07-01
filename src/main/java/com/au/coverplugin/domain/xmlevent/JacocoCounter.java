package com.au.coverplugin.domain.xmlevent;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @Author: ArtificialUnintelligent
 * @Description:
 * @Date: 3:49 PM 2019/1/23
 */
@XStreamAlias("counter")
public class JacocoCounter {

    @XStreamAsAttribute
    private String type;

    @XStreamAsAttribute
    private String missed;

    @XStreamAsAttribute
    private String covered;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMissed() {
        return missed;
    }

    public void setMissed(String missed) {
        this.missed = missed;
    }

    public String getCovered() {
        return covered;
    }

    public void setCovered(String covered) {
        this.covered = covered;
    }
}
