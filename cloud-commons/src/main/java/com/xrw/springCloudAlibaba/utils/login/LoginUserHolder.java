package com.xrw.springCloudAlibaba.utils.login;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xrw.springCloudAlibaba.dto.SysUser;
import com.xrw.springCloudAlibaba.exception.ApiError;
import com.xrw.springCloudAlibaba.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取登录用户信息
 *
 * @author leon
 * @date 2021/04/08
 */
@Slf4j
public class LoginUserHolder {

    /**
     * 这里存当前请求内要查看的数据的平台id
     * 可以是本平台，也可以是其他平台，具体权限要自行校检
     */
    public final static ThreadLocal<Long> platformField = new ThreadLocal<>();

    public static SysUser getCurrentUser() {
        return getCurrentUser(true);
    }

    public static SysUser getCurrentUser(boolean thowEx) {
        //从Header中获取用户信息
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            if (thowEx) {
                throw new ApiException(ApiError.USER_NOT_FIND);
            }
            return null;
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String userStr = request.getHeader("user");
        if (StringUtils.isEmpty(userStr)) {
            if (thowEx) {
                throw new ApiException(ApiError.USER_NOT_FIND);
            }
            return null;
        }
        userStr = new String(userStr.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        return JSONObject.parseObject(userStr, SysUser.class);
    }

    public static Long getUserId() {
        return getCurrentUser().getId();
    }

    public static String getToken() {
        HttpServletRequest request = getRequest();
        return request == null ? null : request.getHeader("token");
    }

    public static String getUserStr() {
        HttpServletRequest request = getRequest();
        return request == null ? null : request.getHeader("user");
    }


    private static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            return null;
        }

        return servletRequestAttributes.getRequest();
    }


    public static List<String> getAuthority() {
        HttpServletRequest request = getRequest();
        String authority = request.getHeader("Authority");
        if (authority == null) {
            return new ArrayList<>();
        }
        return JSONArray.parseArray(authority).toJavaList(String.class);
    }
}
