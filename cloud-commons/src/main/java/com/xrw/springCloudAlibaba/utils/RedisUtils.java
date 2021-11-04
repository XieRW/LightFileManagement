package com.xrw.springCloudAlibaba.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @program: LightFileManagement
 * @description: Redis工具类
 * ‘@ConditionalOnMissingBean’注解表示当IOC容器中没有指定名称或类型的Bean时候，就注册它。
 * 这样做还有一个好处，就是如果子系统需要的话可以定义自己的日志处理方法，来覆盖我们功能模块中定义的方法。
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2020-12-14 22:11
 **/
@Component
@Order(1)
@ConditionalOnMissingBean(name = "RedisUtils")
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ValueOperations<String, String> valueOperations;
    @Autowired
    private HashOperations<String, String, Object> hashOperations;
    @Autowired
    private ListOperations<String, Object> listOperations;
    @Autowired
    private SetOperations<String, Object> setOperations;
    @Autowired
    private ZSetOperations<String, Object> zSetOperations;
    /**  默认过期时长，单位：秒 */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**  一周过期时长，单位：秒 */
    public final static long WEEK_EXPIRE = 60 * 60 * 24*7;
    /**  不设置过期时长 */
    public final static long NOT_EXPIRE = -1;

    public Integer append(String key , String value ){
        return valueOperations.append(key, value);
    }

    public void set(String key, Object value, long expire){
        valueOperations.set(key, toJson(value));
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public void set(String key, Object value){
        set(key, value, DEFAULT_EXPIRE);
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = valueOperations.get(key);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public String get(String key, long expire) {
        String value = valueOperations.get(key);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public String getKey(String pattern){

        Set<String> keys = redisTemplate.keys(pattern);
        if(keys==null||keys.size()==0){
            return null;
        }
        return keys.iterator().next();
    }

    public boolean delete(String key) {
        Boolean d = redisTemplate.delete(key);
        return d;
    }

    /**
     * Object转成JSON数据
     */
    public String toJson(Object object){
        if(object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String){
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }

    /**
     * JSON数据，转成Object
     */
    public  <T> T fromJson(String json, Class<T> clazz){
        return JSON.parseObject(json,clazz);
    }
}
