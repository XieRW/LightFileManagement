package com.xrw.springCloudAlibaba.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xrw.springCloudAlibaba.entity.FileEntity;
import com.xrw.springCloudAlibaba.entity.FileGroupEntity;
import com.xrw.springCloudAlibaba.exception.ApiError;
import com.xrw.springCloudAlibaba.exception.ApiException;
import com.xrw.springCloudAlibaba.service.FileGroupServiceImpl;
import com.xrw.springCloudAlibaba.service.FileServiceImpl;
import com.xrw.springCloudAlibaba.vo.ResponseJSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;

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

    @RequestMapping("add")
    public ResponseJSON add(@RequestParam(value = "pId", defaultValue = "0") Long pId,
                            @RequestParam(value = "name") String name,
                            @RequestParam(value = "detail") String detail) {
        FileGroupEntity one = fileGroupService.getOne(new QueryWrapper<FileGroupEntity>().eq("p_id", pId)
                .eq("name", name));
        if (one!=null){
            throw new ApiException(ApiError.DATA_EXISTS);
        }
        FileGroupEntity fileGroupEntity = new FileGroupEntity().setPId(pId)
                .setName(name)
                .setDetail(detail);
        if (fileGroupService.save(fileGroupEntity)){
            return new ResponseJSON();
        }else {
            throw new ApiException(ApiError.DATA_SAVE_ERROR);
        }
    }

    @RequestMapping("modify")
    public ResponseJSON modify(@RequestParam(value = "id") Long id,
                               @RequestParam(value = "pId") Long pId,
                               @RequestParam(value = "name") String name,
                               @RequestParam(value = "detail") String detail) {
        FileGroupEntity fileGroupEntity = new FileGroupEntity().setPId(pId)
                .setName(name)
                .setDetail(detail);
        if (fileGroupService.saveOrUpdate(fileGroupEntity)){
            return new ResponseJSON();
        }else {
            throw new ApiException(ApiError.DATA_SAVE_ERROR);
        }
    }

    @RequestMapping("delete")
    public ResponseJSON delete(@RequestParam(value = "id") Long id) {
        int countFile = fileService.count(new QueryWrapper<FileEntity>().eq("file_group_id", id));
        if (countFile!=0){
            throw new ApiException(ApiError.DATA_GROUP_NOT_EMPTY);
        }
        int countFileGroup = fileGroupService.count(new QueryWrapper<FileGroupEntity>().eq("p_id", id));
        if (countFileGroup!=0){
            throw new ApiException(ApiError.DATA_GROUP_NOT_EMPTY);
        }
        if (fileGroupService.removeById(id)){
            return new ResponseJSON();
        }else {
            throw new ApiException(ApiError.DATA_DELETE_ERROR);
        }
    }

    @RequestMapping("page")
    public ResponseJSON page(@RequestParam(value = "page", required = false) Integer page,
                             @RequestParam(value = "size", required = false) Integer size,
                             @RequestParam(value = "select", required = false, defaultValue = "") String select,
                             @RequestParam("pId")Long pId) {
        return new ResponseJSON();
    }
}
