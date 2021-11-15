package com.xrw.springCloudAlibaba.config.security;

import com.xrw.springCloudAlibaba.config.config.LoginException;
import com.xrw.springCloudAlibaba.service.CustomUserDetailsServiceImpl;
import com.xrw.springCloudAlibaba.service.SysCaptchaServiceImpl;
import com.xrw.springCloudAlibaba.service.SysUserServiceImpl;
import com.xrw.springCloudAlibaba.vo.CustomUserDetails;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆
 * 做验证
 *
 * @author xearin
 */
@Log4j2
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsServiceImpl userDetailsService;

    @Autowired
    private SysUserServiceImpl sysUserService;

    @Autowired
    private SysCaptchaServiceImpl sysCaptchaService;

    // 单点登录的uuid(用于跳过验证码)，勿删，以及下面对验证码的判断也不要删除
    @Value("${default.uuid:null}")
    private String singleUUid;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();


        //验证码处理  如果接口直接调用
        String captcha = request.getParameter("captcha");
        String uuid = request.getParameter("uuid");
        String codeNum = request.getParameter("codeNum");

        if(codeNum != null && !"".equals(codeNum.trim()) && !singleUUid.equals(uuid)){
            if(StringUtils.isBlank(captcha.trim())){
                throw new LoginException("验证码不能为空");
            }else {
                boolean validate = sysCaptchaService.validate(uuid, captcha);
                if(!validate){
                    throw new LoginException("验证码不正确！");
                }
            }
        }

        //获得登录的用户名密码
        String username;
        String password;
        username = authentication.getPrincipal().toString();
        password = authentication.getCredentials().toString();

        String type = StringUtils.isBlank(request.getParameter("type")) ? "PC" : request.getParameter("type");

        log.info("后端接收到的用户名：{} 密码：{}", username, password);
        if (StringUtils.isAnyBlank(username, password)) {
            throw new LoginException("用户或密码不能为空");
        } else {
            // 调度台，对账号和密码获取到的+号变空格做处理
            if (username.contains(" ")) {
                username = username.replaceAll(" ", "+");
            }
            if (password.contains(" ")) {
                password = password.replaceAll(" ", "+");
            }
        }
        log.info("解密前的的用户名：{} 密码：{}", username, password);
        //根据用户名密码，交由UserDetailsService从数据库查出用户，并得到UserDetails
        CustomUserDetails userDetails = null;
        //添加微信处理 微信  不用AES对账户进行加密，微信只有openId 没有账号 所以前端 传 username 为wx 和password 为openID
        if (username.equals("WX")) {
            //微信的处理
            userDetails = userDetailsService.loadUserByUsernameOpenId(username, password);
        } else {
            //原pc的操作
            userDetails = userDetailsService.loadUserByUsernamePassword(username, password, type);
        }
        if (userDetails == null) {
            throw new LoginException("账号用户密码不正确！！！！");
        }

        //根据userDetail生成AuthenticationToken
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == UsernamePasswordAuthenticationToken.class;
    }

}
