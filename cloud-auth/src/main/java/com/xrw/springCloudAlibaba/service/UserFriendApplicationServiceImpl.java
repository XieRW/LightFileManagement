package com.xrw.springCloudAlibaba.service;

import cn.hutool.core.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xrw.springCloudAlibaba.dao.UserFriendApplicationDao;
import com.xrw.springCloudAlibaba.entity.UserFriendApplicationEntity;
import com.xrw.springCloudAlibaba.entity.UserFriendEntity;
import com.xrw.springCloudAlibaba.enums.SysDictEnum;
import com.xrw.springCloudAlibaba.enums.SysDictItemEnum;
import com.xrw.springCloudAlibaba.exception.ApiError;
import com.xrw.springCloudAlibaba.exception.ApiException;
import com.xrw.springCloudAlibaba.utils.login.LoginUserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

/**
 * @Description: 好友表服务层
 * @Author: xearin
 * @Date: 2021-11-12
 */
@Slf4j
@Service("UserFriendApplicationServiceImpl")
public class UserFriendApplicationServiceImpl extends ServiceImpl<UserFriendApplicationDao, UserFriendApplicationEntity> {
    @Autowired
    private UserFriendServiceImpl userFriendService;

    public UserFriendApplicationEntity addById(Long applyToId) {
        //先判断是否存在重复记录
        UserFriendApplicationEntity selectOne = baseMapper.selectOne(new QueryWrapper<UserFriendApplicationEntity>()
                .eq("apply_from_id", LoginUserHolder.getUserId())
                .eq("apply_to_id", applyToId));
        if (selectOne != null) {
            throw new ApiException(ApiError.DATA_EXISTS);
        }
        UserFriendApplicationEntity selectOne2 = baseMapper.selectOne(new QueryWrapper<UserFriendApplicationEntity>()
                .eq("apply_from_id", applyToId)
                .eq("apply_to_id", LoginUserHolder.getUserId()));
        if (selectOne2 != null) {
            throw new ApiException(ApiError.DATA_EXISTS);
        }
        //如果不存在重复记录则插入
        UserFriendApplicationEntity applicationEntity = new UserFriendApplicationEntity()
                .setApplyFromId(LoginUserHolder.getUserId())
                .setApplyToId(applyToId)
                .setApplyTime(new Date())
                .setApplyStatus(SysDictItemEnum.apply_status_0.getKey());
        baseMapper.insert(applicationEntity);
        return applicationEntity;
    }

    public UserFriendApplicationEntity dispose(Long id, String dispose) {
        UserFriendApplicationEntity userFriendApplicationEntity = baseMapper.selectById(id);
        if (userFriendApplicationEntity == null) {
            throw new ApiException(ApiError.DATA_NOT_EXISTS);
        }
        SysDictItemEnum dict = SysDictItemEnum.getByKey(SysDictEnum.apply_status.getKey(),dispose);
        if (dict == null) {
            throw new ApiException(ApiError.DICT_NOT_EXISTS);
        }
        userFriendApplicationEntity
                .setDisposeTime(new Date())
                .setApplyStatus(dict.getKey());
        baseMapper.updateById(userFriendApplicationEntity);
        if (dict.equals(SysDictItemEnum.apply_status_1)) {
            //先判断是否存在重复记录
            UserFriendEntity selectOne = userFriendService.getOne(new QueryWrapper<UserFriendEntity>()
                    .eq("apply_from_id", userFriendApplicationEntity.getApplyFromId())
                    .eq("apply_to_id", userFriendApplicationEntity.getApplyToId()));
            if (selectOne != null) {
                //存在则直接返回
                return userFriendApplicationEntity;
            }
            //不存在则创建好友记录
            UserFriendEntity userFriendEntity = new UserFriendEntity()
                    .setApplyFromId(userFriendApplicationEntity.getApplyFromId())
                    .setApplyToId(userFriendApplicationEntity.getApplyToId())
                    .setApplyTime(userFriendApplicationEntity.getApplyTime())
                    .setAgreeTime(new Date());

            userFriendService.save(userFriendEntity);
        }
        return userFriendApplicationEntity;
    }

    public Page<UserFriendApplicationEntity> page(Long applyToId,
                                                  @RequestParam(value = "page", required = false) Integer page,
                                                  @RequestParam(value = "size", required = false) Integer size) {
        int offset = 0;
        if (page != null && size != null) {
            offset = (page - 1) * size;
        }
        String status = SysDictItemEnum.apply_status_0.getKey();
        ArrayList<UserFriendApplicationEntity> applicationEntities = baseMapper.getSelectPage(status, applyToId, offset, size);
        Long pageCount = baseMapper.getSelectPageCount(status, applyToId, offset, size);
        Page<UserFriendApplicationEntity> entityPage = new Page<>(Optional.ofNullable(page).orElse(0),
                Optional.ofNullable(size).orElse(0),
                Optional.ofNullable(pageCount).orElse(0L));
        entityPage.setRecords(applicationEntities);
        return entityPage;
    }
}