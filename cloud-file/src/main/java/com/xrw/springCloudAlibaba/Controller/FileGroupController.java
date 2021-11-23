package com.xrw.springCloudAlibaba.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xrw.springCloudAlibaba.entity.FileEntity;
import com.xrw.springCloudAlibaba.entity.FileGroupEntity;
import com.xrw.springCloudAlibaba.exception.ApiError;
import com.xrw.springCloudAlibaba.exception.ApiException;
import com.xrw.springCloudAlibaba.service.FileGroupServiceImpl;
import com.xrw.springCloudAlibaba.service.FileServiceImpl;
import com.xrw.springCloudAlibaba.utils.login.LoginUserHolder;
import com.xrw.springCloudAlibaba.vo.ResponseJSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: LightFileManagement
 * @description: 文件分组控制器层
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2021-11-22 10:30
 **/
@Slf4j
@RestController
@RequestMapping("/file/group")
public class FileGroupController {
    @Autowired
    private FileGroupServiceImpl fileGroupService;
    @Autowired
    private FileServiceImpl fileService;

    /**
     * @param pId:    父节点id
     * @param name:   分组名称
     * @param detail: 分组详情
     * @Description: 添加一个文件分组
     * @return: com.xrw.springCloudAlibaba.vo.ResponseJSON
     * @Author: xearin 1429382875@qq.com
     * @Date: 2021/11/22
     */
    @RequestMapping("add")
    public ResponseJSON add(@RequestParam(value = "pId", defaultValue = "0") Long pId,
                            @RequestParam(value = "name") String name,
                            @RequestParam(value = "detail") String detail) {
        FileGroupEntity one = fileGroupService.getOne(new QueryWrapper<FileGroupEntity>().eq("p_id", pId)
                .eq("name", name)
                .eq("user_id", LoginUserHolder.getUserId()));
        if (one != null) {
            throw new ApiException(ApiError.DATA_EXISTS);
        }
        FileGroupEntity fileGroupEntity = new FileGroupEntity().setpId(pId)
                .setName(name)
                .setDetail(detail)
                .setUserId(LoginUserHolder.getUserId())
                .setCreateUserId(LoginUserHolder.getUserId())
                .setUpdateUserId(LoginUserHolder.getUserId());
        if (fileGroupService.save(fileGroupEntity)) {
            return new ResponseJSON(fileGroupEntity);
        } else {
            throw new ApiException(ApiError.DATA_SAVE_ERROR);
        }
    }

    /**
     * @param id:     文件分组id
     * @param pId:    文件分组的父id
     * @param name:   文件名称
     * @param detail: 文件详情
     * @Description: 修改一个文件分组
     * @return: com.xrw.springCloudAlibaba.vo.ResponseJSON
     * @Author: xearin 1429382875@qq.com
     * @Date: 2021/11/22
     */
    @RequestMapping("modify")
    public ResponseJSON modify(@RequestParam(value = "id") Long id,
                               @RequestParam(value = "pId") Long pId,
                               @RequestParam(value = "name") String name,
                               @RequestParam(value = "detail") String detail) {
        FileGroupEntity fileGroupEntity = fileGroupService.getById(id);
        if (fileGroupEntity == null) {
            throw new ApiException(ApiError.DATA_NOT_EXISTS);
        }
        fileGroupEntity.setpId(pId)
                .setName(name)
                .setDetail(detail)
                .setUpdateUserId(LoginUserHolder.getUserId());
        if (fileGroupService.saveOrUpdate(fileGroupEntity)) {
            return new ResponseJSON(fileGroupEntity);
        } else {
            throw new ApiException(ApiError.DATA_SAVE_ERROR);
        }
    }

    /**
     * @param id: 分组id
     * @Description: 删除一个文件分组
     * @return: com.xrw.springCloudAlibaba.vo.ResponseJSON
     * @Author: xearin 1429382875@qq.com
     * @Date: 2021/11/22
     */
    @RequestMapping("delete")
    public ResponseJSON delete(@RequestParam(value = "id") Long id) {
        int countFile = fileService.count(new QueryWrapper<FileEntity>().eq("file_group_id", id));
        if (countFile != 0) {
            throw new ApiException(ApiError.DATA_GROUP_NOT_EMPTY);
        }
        int countFileGroup = fileGroupService.count(new QueryWrapper<FileGroupEntity>().eq("p_id", id));
        if (countFileGroup != 0) {
            throw new ApiException(ApiError.DATA_GROUP_NOT_EMPTY);
        }
        if (fileGroupService.removeById(id)) {
            return new ResponseJSON();
        } else {
            throw new ApiException(ApiError.DATA_DELETE_ERROR);
        }
    }

    /**
     * @Description: 以树的形式返回所有文件分组
     * @return: com.xrw.springCloudAlibaba.vo.ResponseJSON
     * @Author: xearin 1429382875@qq.com
     * @Date: 2021/11/22
     */
    @RequestMapping("page")
    public ResponseJSON page() {
        List<FileGroupEntity> fileGroupEntities = fileGroupService.pageSelect(LoginUserHolder.getUserId());
        return new ResponseJSON(fileGroupEntities);
    }
}
