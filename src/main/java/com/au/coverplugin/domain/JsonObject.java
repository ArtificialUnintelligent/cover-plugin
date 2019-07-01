package com.au.coverplugin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;

/**
 * @Author: ArtificialUnintelligent
 * @Description:
 * @Date: 10:03 AM 2019/1/22
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public interface JsonObject extends Serializable{

}
