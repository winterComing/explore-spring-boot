package com.dengh.explore.web.resource;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Controller;

/**
 * @author dengH
 * @title: TestController
 * @description: TODO
 * @date 2019/7/5 14:40
 */
public class TestController implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        System.out.println(JSONObject.toJSONString(annotationMetadata));
        return new String[0];
    }
}
