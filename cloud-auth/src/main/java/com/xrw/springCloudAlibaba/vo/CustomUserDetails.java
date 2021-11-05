package com.xrw.springCloudAlibaba.vo;

import com.xrw.springCloudAlibaba.entity.MailContactorEntity;
import com.xrw.springCloudAlibaba.entity.SysUserEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 保存用户的实际关键信息
 *
 * @author unknown
 */
@Data
public class CustomUserDetails implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    SysUserEntity sysUserEntity;

    String username;
    String password;
    Set<GrantedAuthority> authorities = new HashSet<>();
    boolean accountNonExpired;
    boolean enabled;
    Set<String> permitUrls = new HashSet<>();
    String clientIp;
    String token;

    //添加微信的支持
    MailContactorEntity mailContactorEntity;

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }


    public CustomUserDetails() {

    }

    public CustomUserDetails(SysUserEntity sysUserEntity, String username, String password) {
        this.sysUserEntity = sysUserEntity;
        this.username = username;
        this.password = password;
    }

    public CustomUserDetails(MailContactorEntity mailContactorEntity, String username, String password) {
        this.mailContactorEntity = mailContactorEntity;
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public SysUserEntity getSysUserEntity() {
        return sysUserEntity;
    }

    public MailContactorEntity getMailContactorEntity() {
        return mailContactorEntity;
    }

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
