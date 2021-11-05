package com.xrw.springCloudAlibaba.config.security;

import com.alibaba.fastjson.JSONObject;
import com.xrw.springCloudAlibaba.entity.MailContactorEntity;
import com.xrw.springCloudAlibaba.entity.SysRoleEntity;
import com.xrw.springCloudAlibaba.entity.SysUserEntity;
import com.xrw.springCloudAlibaba.service.SysRoleService;
import com.xrw.springCloudAlibaba.vo.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * 认证成功处理
 *
 * @author xearin
 * @date 2021/11/05 16:58
 */
@Component
@Slf4j
public class LoginAuthSuccessHandler extends AbstractResponseHandler implements AuthenticationSuccessHandler {
    private SysRoleService sysRoleService;

    public LoginAuthSuccessHandler(SysRoleService sysRoleService){
        this.sysRoleService = sysRoleService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        //buildResponse(request, response);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setContentType("application/json; charset=utf-8");
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            JSONObject jsonObject = new JSONObject();
            //添加对于微信的支持
            SysUserEntity sysUserEntity = ((CustomUserDetails) authentication.getPrincipal()).getSysUserEntity();
            if (sysUserEntity != null) {
                String deptName = "";
                jsonObject.put("username", sysUserEntity.getUsername());
                jsonObject.put("name", sysUserEntity.getName());
                jsonObject.fluentPut("deptName", deptName);
                jsonObject.fluentPut("role", Optional.ofNullable(sysUserEntity.getRoleId()).orElse(0L));
                if (!Optional.ofNullable(sysUserEntity.getRoleId()).isPresent()){
                    jsonObject.fluentPut("roleName", "");
                }else {
                    SysRoleEntity role = sysRoleService.getById(sysUserEntity.getRoleId());
                    if (role==null){
                        jsonObject.fluentPut("roleName", "");
                    }else {
                        jsonObject.fluentPut("roleName", role.getName());
                    }
                }
                jsonObject.fluentPut("userId", sysUserEntity.getId());
                jsonObject.fluentPut("user", sysUserEntity);
                Long isAdmin = sysUserEntity.getRoleId();
                if (isAdmin == null || isAdmin != 0L) {
                    jsonObject.put("isAdmin", 0);
                } else {
                    jsonObject.put("isAdmin", isAdmin);
                }
                //登陆成功后把用户信息存到缓存里
                jsonObject.put("token", ((CustomUserDetails) principal).getToken());
                jsonObject.put("code", 0);
                jsonObject.put("msg", "success");
                String responseStr = jsonObject.toJSONString();
                // 设置登录session永久不过期
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(-1);
                PrintWriter out = response.getWriter();
                out.write(new String(responseStr.getBytes(), StandardCharsets.UTF_8));
                out.flush();
            } else {
                //微信处理逻辑
                MailContactorEntity mailContactorEntity = ((CustomUserDetails) authentication.getPrincipal()).getMailContactorEntity();
                String deptName = "";
                jsonObject.put("username", mailContactorEntity.getName());
                jsonObject.fluentPut("deptName", deptName);
                jsonObject.fluentPut("userId", mailContactorEntity.getId());
                //登陆成功后把用户信息存到缓存里
                jsonObject.put("token", ((CustomUserDetails) principal).getToken());
                jsonObject.put("code", 0);
                jsonObject.put("msg", "success");
                // 设置登录session永久不过期
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(-1);
                PrintWriter out = response.getWriter();
                out.write(new String(jsonObject.toJSONString().getBytes(), StandardCharsets.UTF_8));
                out.flush();
            }

        } else {
            PrintWriter out = response.getWriter();
            out.write(new String("登录成功".getBytes(), StandardCharsets.UTF_8));
            out.flush();
        }
//        logger.info("登录成功：url={},method={},ip={},userId={}", request.getRequestURI(),request.getMethod(),request.getRemoteAddr(),((CustomUserDetails) authentication.getPrincipal()).getSysUserEntity().getUserId());
//        setLoginLog("登录成功",request,authentication);
    }

    public void setLoginLog(String operation, HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            SysUserEntity sysUserEntity = ((CustomUserDetails) authentication.getPrincipal()).getSysUserEntity();
            /*SysLog sysLog = new SysLog();
            sysLog.setiOperatorId(currentOperator.getiId());
            sysLog.setVcUserName(currentOperator.getVcName());
            sysLog.setDtLoginTime(currentOperator.getDtLastLogin());
            String ip = request.getRemoteAddr();
            String host = request.getRemoteHost();
            sysLog.setVcIp(ip);
            sysLog.setVcHostName(host);
            //获取方法执行前时间
            sysLog.setDtOperateTime(new Timestamp(System.currentTimeMillis()));
            sysLog.setTxtContent(operation+"成功");
            sysLog.setVcOperation(operation);
            sysLogService.save(sysLog);*/
        }
    }
}
