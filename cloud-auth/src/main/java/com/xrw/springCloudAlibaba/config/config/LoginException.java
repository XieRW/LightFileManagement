package com.xrw.springCloudAlibaba.config.config;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * @author xearin
 */
public class LoginException extends InternalAuthenticationServiceException {

    public LoginException(String msg, Throwable t) {
        super(msg, t);
    }

    public LoginException(String msg) {
        super(msg);
    }
}
