package com.xrw.springCloudAlibaba.config.fastjson;

import com.alibaba.fastjson.serializer.NameFilter;
import com.xrw.springCloudAlibaba.utils.Util;

import java.io.Serializable;

/**
 * @author xearin
 */
public class FieldNameToUpperCaseFilter implements NameFilter, Serializable {

    @Override
    public String process(Object object, String name, Object value) {
        return Util.firstLetterUpperCase(name);
    }
}