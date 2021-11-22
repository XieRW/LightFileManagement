package com.xrw.springCloudAlibaba.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xrw.springCloudAlibaba.dao.FileDao;
import com.xrw.springCloudAlibaba.entity.FileEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description: 文件服务层
 * @Author: xearin
 * @Date: 2021-11-12
 */
@Slf4j
@Service("UserFriendServiceImpl")
public class FileServiceImpl extends ServiceImpl<FileDao, FileEntity> {

}