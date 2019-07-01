package com.au.coverplugin.domain.xmlevent;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * @Author: ArtificialUnintelligent
 * @Description: xstream的坑，需要将xml里的所有字段定义出来，可使用XStreamOmitField忽略不需要的字段
 *               优化：将忽略的字段全部放入父类，支持忽略xml里不存在的字段，不会抛出异常
 *               注：如果xml出现java保留字，则使用XStreamAlias对其进行重命名
 * @Date: 4:09 PM 2019/1/23
 */
public abstract class BaseEvent {

    @XStreamOmitField
    private String name;

    @XStreamOmitField
    private String sessioninfo;

    @XStreamOmitField
    @XStreamAlias("package")
    private String p;

    public String getSessioninfo() {
        return sessioninfo;
    }

    public void setSessioninfo(String sessioninfo) {
        this.sessioninfo = sessioninfo;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
