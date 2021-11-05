package com.xrw.springCloudAlibaba.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xearin
 * @Date 2019-11-15 10:00
 **/
public class JsonUtil {
	
	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	
	/**
	 * 对象转换成Json字符串
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj){
		 ObjectMapper mapper = new ObjectMapper(); 
		 try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("toJson throw exception",e);
		}
		 return null;
	} 
	
	/**
	 * json转对象
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T>T toObject(String jsonStr, Class<T> clazz){
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		T t = null;
		try {
			t = mapper.readValue(jsonStr, clazz);
		} catch (JsonParseException e) {
			logger.error("toObject throw JsonParseException",e);
		} catch (JsonMappingException e) {
			logger.error("toObject throw JsonMappingException",e);
		} catch (IOException e) {
			logger.error("toObject throw IOException",e);
		}
		return t;
	}

	public static <T> List<T> toList(String jsonStr, Class<T> clazz){
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
		List rt = null;
		try {
			rt = mapper.readValue(jsonStr, javaType);
		} catch (IOException e) {
			logger.error("toList throw IOException",e);
		}
		return rt;
	}

}
