package com.xrw.springCloudAlibaba.config.fastjson;

import com.alibaba.fastjson.serializer.PropertyFilter;

import java.util.HashSet;
import java.util.Set;

public class MyPropertyFilter implements PropertyFilter {

    Class clazz;
    Set<String> includes = new HashSet<>();

    public MyPropertyFilter(Class clazz, String...properties) {
        this.clazz = clazz;
        for (String item : properties) {
            if (item != null) {
                this.includes.add(item);
            }
        }
    }

    @Override
    public boolean apply(Object object, String name, Object value) {
        if (object == null) {
            return true;
        }

        if (clazz != null && !clazz.isInstance(object)) {
            return true;
        }

        if (includes.size() == 0 || includes.contains(name)) {
            return true;
        }

        for (String include:
             includes) {
            include = include.substring(0, 1).toUpperCase() + include.substring(1);
            if (name.matches("(i|vc|dt|ti|txt|dl|b|d|sysVc|sysDt|sysI)" + include)) {
                return true;
            }
        }

        return false;
    }
}

