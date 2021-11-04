package com.xrw.springCloudAlibaba.config.fastjson;

import com.alibaba.fastjson.serializer.NameFilter;
import com.xrw.springCloudAlibaba.utils.Util;

public class FieldNameFilter implements NameFilter {
    @Override
    public String process(Object object, String name, Object value) {
        return Util.propertyNameTransfer(name);
    }
}