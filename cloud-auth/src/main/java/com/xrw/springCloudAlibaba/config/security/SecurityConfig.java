package com.xrw.springCloudAlibaba.config.security;



import com.alibaba.nacos.common.util.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//import com.xrw.springCloudAlibaba.config.security.sso.eerduosi.SsoEErDuoSiAuthenticationProvider;
//import com.xrw.springCloudAlibaba.config.security.sso.eerduosi.SsoEErDuoSiLoginFilter;

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
        http.csrf().disable();
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