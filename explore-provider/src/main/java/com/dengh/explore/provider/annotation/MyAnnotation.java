package com.dengh.explore.provider.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author dengH
 * @title: MyAnnotation
 * @description: TODO
 * @date 2019/9/18 10:32
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    String getValue();
}
