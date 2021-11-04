package com.xrw.springCloudAlibaba.config.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.DoubleSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.util.TypeUtils;
import org.springframework.context.annotation.Configuration;

import java.sql.Date;
import java.sql.Timestamp;

@Configuration
public class MyFastJsonConfig extends FastJsonConfig {

    public MyFastJsonConfig() {
        SerializeConfig config = SerializeConfig.getGlobalInstance();
        config.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
        config.put(Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
        config.put(Double.class, new DoubleSerializer("#.################"));
        this.setSerializeConfig(config);

        TypeUtils.compatibleWithJavaBean =true;
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.WriteMapNullValue.getMask();
    }


}
