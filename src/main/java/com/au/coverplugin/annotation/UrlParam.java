package com.au.coverplugin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: ArtificialUnintelligent
 * @Description:
 * @Date: 2:04 PM 2019/1/28
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UrlParam {



}
