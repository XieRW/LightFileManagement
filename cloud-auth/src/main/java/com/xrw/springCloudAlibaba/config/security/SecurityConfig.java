package com.xrw.springCloudAlibaba.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author xearin
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private LoginAuthSuccessHandler loginAuthSuccessHandler;
    private LoginAuthFailHandler loginAuthFailHandler;
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private LoginOutSuccessHandler loginOutSuccessHandler;

    public SecurityConfig(
            LoginAuthFailHandler loginAuthFailHandler,
            LoginAuthSuccessHandler loginAuthSuccessHandler) {
        this.loginAuthFailHandler = loginAuthFailHandler;
        this.loginAuthSuccessHandler = loginAuthSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //crsf资源跨域访问限制取消
        http.csrf().disable();
        //X-Frame-Options请求头限制取消
        http.headers().frameOptions().disable();
        http
                .httpBasic()
                .and()
                .authorizeRequests().antMatchers("/permit/login").permitAll()
                .anyRequest()
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/permit/logout")
                .logoutSuccessHandler(loginOutSuccessHandler)
                .and()
                .formLogin().loginProcessingUrl("/permit/login")
                .successHandler(loginAuthSuccessHandler)
                .failureHandler(loginAuthFailHandler);
//        http.authorizeRequests()
//                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
//                .antMatchers("/rsa/publicKey").permitAll()
//                .anyRequest().authenticated();
        http.authenticationProvider(customAuthenticationProvider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}