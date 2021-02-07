package com.xrw.springCloudAlibaba.config.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.*;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.Duration;

/**
 * @program: LightFileManagement
 * @description: 缓存配置类
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2020-12-14 22:11
 **/
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);
    @Autowired
    private RedisConnectionFactory factory;
    /**
     * 默认两小时
     */
    private static final long DURATION_SECOND_7200 = 7200L;
    private static final long DURATION_SECOND_300 = 300L;

    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @SuppressWarnings("rawtypes")
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder(RedisAutoCacheValue.AUTO_KEY_PREFIX);
                if(target instanceof Proxy) {
                	//如果是代理类
                	Class[] i = target.getClass().getInterfaces();
                	if(i != null  && i.length > 0) {
                		//取第一个即可
                		sb.append(i[0].getName());
                	}else {
                		sb.append(target.getClass().getName());
                	}
                } else if(target instanceof org.springframework.cglib.proxy.Factory){
                    //如果是cglib代理，需要手动去除 $$ 后面的
                    String className = target.getClass().getName();
                    sb.append(className, 0, className.indexOf("$$"));
                } else {
                	sb.append(target.getClass().getName());
                }
                sb.append(".");
                sb.append(method.getName());
                sb.append("_");
                for (Object obj : params) {
                    if (obj != null) {
                        Class cls = obj.getClass();
                        if (cls.isArray()) {
                            //对于基础数据处理
                            logger.info("keyGenerator : {}", cls.getComponentType());
                            if (cls.isAssignableFrom(long.class)) {
                                long[] ay = (long[]) obj;
                                for (long o : ay) {
                                    sb.append(o).append("");
                                }
                            } else if (cls.isAssignableFrom(int.class)) {
                                int[] ay = (int[]) obj;
                                for (int o : ay) {
                                    sb.append(o).append("");
                                }
                            } else if (cls.isAssignableFrom(float.class)) {
                                float[] ay = (float[]) obj;
                                for (float o : ay) {
                                    sb.append(o).append("");
                                }
                            } else if (cls.isAssignableFrom(double.class)) {
                                double[] ay = (double[]) obj;
                                for (double o : ay) {
                                    sb.append(o).append("");
                                }
                            } else if (cls.isAssignableFrom(String.class)) {
                                String[] ay = (String[]) obj;
                                for (String o : ay) {
                                    sb.append(o).append("");
                                }
                            } else {
                                sb.append(obj.toString());
                            }
                            //TODO 对其他类型数组处理
                        } else {
                            sb.append(obj.toString());
                        }
                    } else {
                        sb.append("null");
                    }
                    sb.append("_");
                    //sb.append(obj == null ? "null" : obj.toString());
                }
                sb.delete(sb.length()-1, sb.length());
                return sb.toString();
            }

        };

    }

    /**
     * 默认的缓存管理，存放时效较长的缓存
     * @param redisTemplate
     * @return
     */
    @SuppressWarnings({"rawtypes", "Duplicates"})
    @Primary
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheConfiguration config = RedisCacheConfiguration
                .defaultCacheConfig()
                //过期时间
                .entryTtl(Duration.ofSeconds(DURATION_SECOND_7200))
                //不缓存null值
                //.disableCachingNullValues()
                //明确manager中的序列化与template一样，防止莫名其妙的问题
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(this.keySerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(this.valueSerializer()));

        RedisCacheManager rcm = RedisCacheManager.builder(redisTemplate.getConnectionFactory()).cacheDefaults(config).transactionAware().build();
        return rcm;
    }

    /**
     * 存放时效较短的缓存（5分钟）
     * @param redisTemplate
     * @return
     */
    @SuppressWarnings({"rawtypes", "Duplicates"})
    @Bean
    public CacheManager cacheManagerIn5Minutes(RedisTemplate redisTemplate) {
        RedisCacheConfiguration config = RedisCacheConfiguration
                .defaultCacheConfig()
                //过期时间
                .entryTtl(Duration.ofSeconds(DURATION_SECOND_300))
                //不缓存null值
                //.disableCachingNullValues()
                //明确manager中的序列化与template一样，防止莫名其妙的问题
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(this.keySerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(this.valueSerializer()));

        RedisCacheManager rcm = RedisCacheManager.builder(redisTemplate.getConnectionFactory()).cacheDefaults(config).transactionAware().build();
        return rcm;
    }

    /*@SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
    	//factory = connectionFactory(3,"172.20.11.134",6379,"123456",2000,100,1,1000,2000);
        StringRedisTemplate template = new StringRedisTemplate(factory);

        template.setKeySerializer(keySerializer());
        template.setHashKeySerializer(keySerializer());
        template.setValueSerializer(valueSerializer());
        template.setHashValueSerializer(valueSerializer());
        template.afterPropertiesSet();
        return template;
    }
*/
    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }
    private RedisSerializer<Object> valueSerializer() {
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        //略过不匹配的属性
//        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        return jackson2JsonRedisSerializer;

        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        //略过不匹配的属性
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(om);
        return genericJackson2JsonRedisSerializer;
    }

// ==========注解：以上是用来适配Cacheable缓存注解的配置，自定义缓存类型和时长=============================================

// ==========手动：以下是用来适配原来的Redis的配置，用于手动添加Redis缓存，现在把Redis做成新版的缓存配置==================

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    public ValueOperations<String, String> valueOperations(RedisTemplate<String, String> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }
}