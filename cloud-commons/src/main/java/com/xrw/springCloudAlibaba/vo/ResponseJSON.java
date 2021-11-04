package com.xrw.springCloudAlibaba.vo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xrw.springCloudAlibaba.config.fastjson.FieldNameFilter;
import com.xrw.springCloudAlibaba.config.fastjson.MyPropertyFilter;
import com.xrw.springCloudAlibaba.exception.ApiError;
import com.xrw.springCloudAlibaba.utils.Util;
import com.xrw.springCloudAlibaba.utils.page.PageWrapper;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author unknown
 */
public class ResponseJSON extends JSONObject implements Serializable {

    private List<SerializeFilter> filters = new ArrayList<>();
    {
        filters.add(new FieldNameFilter());
        filters.add(ignoreSystemFieldFilter());
    }
    private List<SerializerFeature> features = new ArrayList<>();
    {
        features.add(SerializerFeature.DisableCircularReferenceDetect);
        features.add(SerializerFeature.WriteMapNullValue);
        features.add(SerializerFeature.WriteNullBooleanAsFalse);
        features.add(SerializerFeature.WriteNullNumberAsZero);
        features.add(SerializerFeature.WriteNullStringAsEmpty);
        features.add(SerializerFeature.WriteNullListAsEmpty);
    }

    public ResponseJSON() {
        super.put("errorcode", 0);
        super.put("code", 0);
        super.put("msg", "SUCCESS");
    }

    public ResponseJSON(Integer errorCode, String msg) {
        super.put("errorcode", errorCode);
        super.put("code", errorCode);
        super.put("msg", msg);
    }

    public ResponseJSON(ApiError apiError) {
        super.put("errorcode", apiError.errorCode);
        super.put("code", apiError.errorCode);

        super.put("msg", apiError.msg);
    }

    public ResponseJSON(ApiError apiError, Object data) {
        super.put("errorcode", apiError.errorCode);
        super.put("code", apiError.errorCode);
        super.put("msg", apiError.msg);
        super.put("data", data);
    }

    public ResponseJSON(Object data) {
        super.put("errorcode", 0);
        super.put("code", 0);
        super.put("msg", "SUCCESS");
        if (data == null) {
            super.put("data", "");
            return ;
        }
        super.put("data", preHandleData(data));
    }

    public ResponseJSON(Object data, SerializeFilter...filters) {

        this.filters.addAll(Arrays.asList(filters));

        super.put("errorcode", 0);
        super.put("code", 0);
        super.put("msg", "SUCCESS");
        if (data == null) {
            super.put("data", "");
            return ;
        }
        super.put("data", preHandleData(data));
    }

    public ResponseJSON(Object data, boolean ingoreDefaultFilters, SerializeFilter...filters) {
        if (ingoreDefaultFilters){
            this.filters =Arrays.asList(filters);
        }else{

            this.filters.addAll(Arrays.asList(filters));
        }

        super.put("errorcode", 0);
        super.put("code", 0);
        super.put("msg", "SUCCESS");
        if (data == null) {
            super.put("data", "");
            return ;
        }
        super.put("data", preHandleData(data));
    }

    public ResponseJSON(Object data, boolean ingoreDefaultFilters, PropertyFilter propertyFilter) {
        if(propertyFilter != null){
            this.filters.add(propertyFilter);
        }
        super.put("errorcode", 0);
        super.put("code", 0);
        super.put("msg", "SUCCESS");
        if (data == null) {
            super.put("data", "");
            return ;
        }
        super.put("data", preHandleData(data));
    }

    public static PropertyFilter propertyFilter(Class clazz, String...properties) {
        for (int i = 0; i < properties.length; i++) {
            String property = properties[i];
            properties[i] = Util.propertyNameTransfer(property);
        }
        return new MyPropertyFilter(clazz, properties);
    }

    public static PropertyFilter ignoreSystemFieldFilter() {
        // 需要忽略的其他系统属性
        List<String> field = Arrays.asList("createTime", "updateTime");
        return (object, name, value) -> !name.startsWith("sys") && !field.contains(name);
    }

    /**
     * 忽略不需要的属性
     * @param properties: 需要忽略的属性
     * @return this
     * @author jtb
     * @date 2020/9/24 20:47
     */
    public ResponseJSON ignoreFieldFilter(String... properties) {
        PropertyFilter propertyFilter = (object, name, value) -> !Arrays.asList(properties).contains(name);
        filters.add(propertyFilter);
        return this;
    }

    private Object preHandleData(Object data) {
        if (data instanceof Page) {
            return new PageWrapper<>((Page) data);
        } else {
            return data;
        }
    }

    public SerializeFilter[] getFilters() {
        return filters.toArray(new SerializeFilter[filters.size()]);
    }

    public SerializerFeature[] getFeatures() {
        return features.toArray(new SerializerFeature[features.size()]);
    }
}