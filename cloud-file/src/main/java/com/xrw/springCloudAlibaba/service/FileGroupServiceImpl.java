package com.xrw.springCloudAlibaba.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xrw.springCloudAlibaba.dao.FileGroupDao;
import com.xrw.springCloudAlibaba.entity.FileGroupEntity;
import com.xrw.springCloudAlibaba.exception.ApiError;
import com.xrw.springCloudAlibaba.exception.ApiException;
import com.xrw.springCloudAlibaba.utils.tree.TreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 文件分组服务层
 * @Author: xearin
 * @Date: 2021-11-12
 */
@Slf4j
@Service("FileGroupServiceImpl")
public class FileGroupServiceImpl extends ServiceImpl<FileGroupDao, FileGroupEntity> {

    public List<FileGroupEntity> pageSelect(Long userId) {
        List<FileGroupEntity> entities = baseMapper.selectList(new QueryWrapper<FileGroupEntity>().eq("user_id", userId));
        List<FileGroupEntity> tree;
        try {
            tree = TreeUtil.getTree(entities, "id", "pId", "children");
        } catch (Exception e) {
            throw new ApiException(ApiError.DATA_FORMAT_TREE_ERROR);
        }
        return tree;
    }
}