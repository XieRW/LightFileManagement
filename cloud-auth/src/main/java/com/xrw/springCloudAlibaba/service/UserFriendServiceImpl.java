package com.xrw.springCloudAlibaba.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xrw.springCloudAlibaba.dao.UserFriendDao;
import com.xrw.springCloudAlibaba.entity.UserFriendApplicationEntity;
import com.xrw.springCloudAlibaba.entity.UserFriendEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @Description: 好友表服务层
 * @Author: xearin
 * @Date: 2021-11-12
 */
@Slf4j
@Service("UserFriendServiceImpl")
public class UserFriendServiceImpl extends ServiceImpl<UserFriendDao, UserFriendEntity> {

    public Page<UserFriendEntity> pageSelect(Long applyToId,
                                             String select,
                                             @RequestParam(value = "page", required = false) Integer page,
                                             @RequestParam(value = "size", required = false) Integer size) {
        int offset = 0;
        if (page != null && size != null) {
            offset = (page - 1) * size;
        }
        ArrayList<UserFriendEntity> applicationEntities = baseMapper.getSelectPage(applyToId,select, offset, size);
        Long pageCount = baseMapper.getSelectPageCount(applyToId,select, offset, size);
        Page<UserFriendEntity> entityPage = new Page<>(Optional.ofNullable(page).orElse(0),
                Optional.ofNullable(size).orElse(0),
                Optional.ofNullable(pageCount).orElse(0L));
        entityPage.setRecords(applicationEntities);
        return entityPage;
    }
}