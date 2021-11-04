package com.xrw.springCloudAlibaba.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringVerifyUtil {

    public static final String PHONE_PATTERN = "(^\\s{0}$)|(^(0\\d{2,3}\\-|0\\d{2,3})?[1-9]\\d{6,7}(\\-\\d{1,4})?$)|(^1(3|4|5|7|8|9)\\d{9}$)";
    public static final String MOBILE_PATTERN = "(^\\s{0}$)|(^1(3|4|5|7|8|9)\\d{9}$)";
    public static final String FAX_PATTERN = "^(^\\s{0}$)|(0\\d{2,3}\\-|0\\d{2,3})?[1-9]\\d{6,7}(\\-\\d{1,4})?$";
    public static final String NO_SPEC_CHAR_PATTERN = "[^`~!@#$%^&*=|{}:\\[\\].<>《》/?~！@#￥……&*——|{}【】；：。？]+";
    public static final String POST_CODE_PATTERN = "^[0-9]{6}$";
    public static final String NUMBER_PATTERN = "^\\d+$";
    public static final String EMAIL_PATTERN = "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";

    /**
     * 根据正则表达式校验字符串
     * @param str           待校验字符串
     * @param pattern       正则表达式
     * @return              true：匹配  false：不匹配
     */
    
    public static Boolean verifyString(String str, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    
    public static Boolean isMobile(String str){
        return verifyString(str, MOBILE_PATTERN);
    }

    
    public static Boolean isPhone(String str){
        return verifyString(str, PHONE_PATTERN);
    }

    
    public static Boolean isFax(String str){
        return verifyString(str, FAX_PATTERN);
    }

    
    public static Boolean isEmail(String str){
        return verifyString(str, EMAIL_PATTERN);
    }

    
    public static Boolean isPostCode(String str){
        return verifyString(str, POST_CODE_PATTERN);
    }

    
    public static Boolean isNumber(String str){
        return verifyString(str, NUMBER_PATTERN);
    }

    
    public static Boolean hasNoSpecialChar(String str){
        return verifyString(str, NO_SPEC_CHAR_PATTERN);
    }

    public static Boolean isNotEmpty(String source){
        return !isEmpty(source);
    }

    
    public static Boolean isEmpty(String source){
        return source == null || "".equals(source.trim());
    }
}
