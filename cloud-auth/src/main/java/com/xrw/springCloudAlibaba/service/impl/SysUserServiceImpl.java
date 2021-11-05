package com.xrw.springCloudAlibaba.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xrw.springCloudAlibaba.dao.SysUserDao;
import com.xrw.springCloudAlibaba.entity.SysUserEntity;
import com.xrw.springCloudAlibaba.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service("ctiOperatorService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${security.oauth2.client.grant-type:password}")
    private String grantType;

    /**
     * 登录
     * @param name 用户名
     * @return
     */
    @Override
    public SysUserEntity getUserByName(String name) {
        QueryWrapper<SysUserEntity> condition = new QueryWrapper<>();
        condition.eq("vc_name",name);
        List<SysUserEntity> tUserEntities = baseMapper.selectList(condition);
        return tUserEntities.get(0);
    }

    @Override
    public SysUserEntity queryByUserName(String username) {
        return baseMapper.queryByUserName(username);
    }

    @Override
    public SysUserEntity listBy(String username, String name, String mobile, String sourceUserId, String sourceType, Long platForm) {
        return baseMapper.listBy(username, name, mobile, sourceUserId, sourceType, platForm);
    }

    @Override
    public Boolean listByUsernamOrMobile(String username, String mobile, Long userId) {
        List<SysUserEntity> sysUserEntityList =  baseMapper.listByUsernameOrMobile(mobile, username);
        if (sysUserEntityList == null || sysUserEntityList.size() == 0) {
            return false;
        }
        sysUserEntityList = sysUserEntityList.stream().filter(t -> !t.getId().equals(userId)).collect(Collectors.toList());
        if (sysUserEntityList.size() > 0) {
            return true;
        }
        return false;
    }

}