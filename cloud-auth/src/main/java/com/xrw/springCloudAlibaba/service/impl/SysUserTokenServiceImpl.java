

package com.xrw.springCloudAlibaba.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xrw.springCloudAlibaba.dao.SysUserTokenDao;
import com.xrw.springCloudAlibaba.entity.MailContactorEntity;
import com.xrw.springCloudAlibaba.entity.SysUserEntity;
import com.xrw.springCloudAlibaba.entity.SysUserTokenEntity;
import com.xrw.springCloudAlibaba.service.MailContactorService;
import com.xrw.springCloudAlibaba.service.SysUserService;
import com.xrw.springCloudAlibaba.service.SysUserTokenService;
import com.xrw.springCloudAlibaba.utils.RedisUtils;
import com.xrw.springCloudAlibaba.utils.TimeUtil;
import com.xrw.springCloudAlibaba.utils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author xearin
 */
@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements SysUserTokenService {
    @Autowired
    private RedisUtils redisUtils;
    private final String tokenmark = "TOKEN:";
    @Autowired
    private MailContactorService mailContactorService;
    @Autowired
    private SysUserService sysUserService;
    /**
     * 过期时间，12小时后过期
     */
    private final static int EXPIRE = 3600 * 24 * 7;
    @Override
    public String createWxToken(long contactorId, String tokenType, int time) {

        //生成一个token
        String token = tokenType + "_" + TokenGenerator.generateValue();
        //当前时间
        Date now = new Date();
        //判断是否生成过token
        String key = redisUtils.getKey(tokenmark + tokenType + "_*:wechat" + contactorId);

        MailContactorEntity byId = mailContactorService.getById(contactorId);
        byId.setToken(token);

        // 把用户信息也放到redis
        String concatotStr = JSON.toJSONString(byId);
        if (key != null) {
            redisUtils.delete(key);
        }

        redisUtils.set(tokenmark + token + ":wechat" + contactorId, contactorId, time == -1 ? EXPIRE : 3600 * 24 * 365);
        redisUtils.set(tokenType + "_user_" + contactorId, concatotStr, time == -1 ? EXPIRE : 3600 * 24 * 365);

        return token;
    }
    @Override
    public String createToken(long userId, String tokenType) {
        //生成一个token
        String token = tokenType + "_" + TokenGenerator.generateValue();
        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
        //判断是否生成过token
        String key = redisUtils.getKey(tokenmark + tokenType + "_*:" + userId);

        if (key != null) {
            redisUtils.delete(key);
        }
        redisUtils.set(tokenmark + token + ":" + userId, userId + "#" + TimeUtil.dateToString2(new Date(), "yyyy-MM-dd HH:mm:ss"), EXPIRE);

        //判断是否生成过token
        String userkey = redisUtils.getKey(tokenType + "_user_" + userId);
        if (userkey != null) {
            redisUtils.delete(userkey);
        }
        SysUserEntity byId = sysUserService.getById(userId);
        byId.setToken(token);
        // 把用户信息也放到redis
        String userStr = JSON.toJSONString(byId);
        redisUtils.set(tokenType + "_user_" + userId, userStr, EXPIRE);

        return token;
    }
}
