package com.au.coverplugin.domain.xmlevent;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.List;

/**
 * @Author: ArtificialUnintelligent
 * @Description: 用于解析Jacoco.xml，被忽略的字段放在父类
 * @Date: 3:30 PM 2019/1/23
 */
@XStreamAlias("report")
public class JacocoReportEvent extends BaseEvent {

    @XStreamImplicit(itemFieldName = "counter")
    private List<JacocoCounter> counters;

    public List<JacocoCounter> getCounters() {
        return counters;
    }

    public void setCounters(List<JacocoCounter> counters) {
        this.counters = counters;
    }


}
