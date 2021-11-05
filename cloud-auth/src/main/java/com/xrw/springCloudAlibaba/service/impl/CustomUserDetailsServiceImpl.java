package com.xrw.springCloudAlibaba.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.xrw.springCloudAlibaba.config.config.LoginException;
import com.xrw.springCloudAlibaba.entity.MailContactorEntity;
import com.xrw.springCloudAlibaba.entity.SysUserEntity;
import com.xrw.springCloudAlibaba.service.MailContactorService;
import com.xrw.springCloudAlibaba.service.SysUserService;
import com.xrw.springCloudAlibaba.service.SysUserTokenService;
import com.xrw.springCloudAlibaba.utils.AESUtil;
import com.xrw.springCloudAlibaba.utils.RedisUtils;
import com.xrw.springCloudAlibaba.vo.CustomUserDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SysUserTokenService sysUserTokenService;
    @Autowired
    private MailContactorService mailContactorService;


    /* public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         System.out.println("调用loadUserByUsername方法");
         SysUserEntity user = sysUserService.getUserByName(username);
         if (user == null){
             return null;
         }
         Set<String> perms = sysMenuService.getPermsByVcName(username);
        // UserDetails build = User.withUsername(username).password(passwordEncoder.encode("gly")).authorities(String.valueOf(perms)).build();
         UserDetails build = User.withUsername(username).password(user.getPassword()).authorities(String.valueOf(perms)).build();
         return build;
     }*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public CustomUserDetails loadUserByUsernamePassword(String username, String password, String type) throws LoginException {
        try {
            password = AESUtil.desEncrypt(password, "_aes_secret_key_", "_aes_secret_iv__").trim();
            username = AESUtil.desEncrypt(username, "_aes_secret_key_", "_aes_secret_iv__").trim();
        } catch (Exception e) {
            log.warn("AESUtil解密失败>>>{}", e.getMessage());
        }
        //用户信息
        SysUserEntity user = sysUserService.queryByUserName(username);
        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(sha256(user.getSalt(), password))) {
//        if (user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
            if (user != null) {
                //todo 判断有重复，可以优化
                if (user.getStatus() == 0) {
                    throw new LoginException("您的账号因存在安全风险已被锁定,请联系管理员");
                }
                //用来计算用户登录错误次数
                if (redisUtils.get("user_login_" + user.getId()) == null) {
                    redisUtils.set("user_login_" + user.getId(), "1", 600);
                    throw new LoginException("账号密码不正确！");
                } else {
                    Integer append = redisUtils.append("user_login_" + user.getId(), "1");
                    if (append >= 5) {
                        user.setStatus(0);
                        sysUserService.updateById(user);
                        redisUtils.delete("user_login_" + user.getId());
                        throw new LoginException("由于您登录失败的次数过多,系统已锁定了此用户,请联系管理员！");
                    }
                }
            }
            throw new LoginException("账号密码不正确！请联系管理员！！！");
        }
        //账号锁定
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new LoginException("您的账号因存在安全风险已被锁定,请联系管理员");
        }
        //登陆成功后把用户信息存到缓存里
        String token = sysUserTokenService.createToken(user.getId(), type);
        // 补充自定义用户对象信息
        CustomUserDetails userDetails = new CustomUserDetails(user, username, password);
        userDetails.setAccountNonExpired(true);
        userDetails.setEnabled(true);
        userDetails.setToken(token);
//        userDetails.permitUrls.addAll(sysModuleService.modulesToUrls(sysModuleService.getAllByOperator(ctiOperator)));
        return userDetails;
    }

    public static String sha256(String salt, String pwdPlain) {
        return Hashing.sha256().newHasher().putString(salt + pwdPlain, Charsets.UTF_8).hash().toString();
    }

    /***
     * 添加微信的授权认证（同pc登录有点区别）
     * hdq
     * @param username
     * @param password
     * @return
     * @throws LoginException
     */
    public CustomUserDetails loadUserByUsernameOpenId(String username, String password) throws LoginException {
        MailContactorEntity mailContactor = mailContactorService.getOne(
                new QueryWrapper<MailContactorEntity>()
                        .eq("open_id", password)
                        .eq("is_deleted", 0));
        return setContactorToken(mailContactor, username, password, "WX");
    }

    private CustomUserDetails setContactorToken(MailContactorEntity mailContactor, String username, String password, String type) throws LoginException {
        if (mailContactor == null) {
            throw new LoginException("用户不存在！！！");
        }
        //登陆成功后把用户信息存到缓存里
        String token = sysUserTokenService.createWxToken(mailContactor.getId(), "CONTACTOR" + type, -1);

        // 补充自定义用户对象信息
        CustomUserDetails userDetails = new CustomUserDetails(mailContactor, username, password);
        userDetails.setAccountNonExpired(true);
        userDetails.setEnabled(true);
        userDetails.setToken(token);
        return userDetails;
    }


}
