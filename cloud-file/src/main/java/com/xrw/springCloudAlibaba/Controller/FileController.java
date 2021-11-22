package com.xrw.springCloudAlibaba.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xrw.springCloudAlibaba.service.FileGroupServiceImpl;
import com.xrw.springCloudAlibaba.service.FileServiceImpl;
import com.xrw.springCloudAlibaba.utils.login.LoginUserHolder;
import com.xrw.springCloudAlibaba.vo.ResponseJSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: LightFileManagement
 * @description: 文件控制器层
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2021-11-22 10:30
 **/
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileServiceImpl fileService;
    @Autowired
    private FileGroupServiceImpl fileGroupService;

    @RequestMapping("/upload")
    public ResponseJSON upload(@RequestPart("files") MultipartFile[] files,
                               @RequestParam("type") String type,
                               @RequestParam("fileGroupId")Long fileGroupId){

        return new ResponseJSON();
    }

    @RequestMapping("delete")
    public ResponseJSON delete(@RequestParam("id")Long id){
        return new ResponseJSON();
    }

    @RequestMapping("download")
    public void download(@RequestParam("id")Long id,
                         HttpServletResponse response){

    }

    @RequestMapping("page")
    public void page(@RequestParam(value = "page", required = false) Integer page,
                     @RequestParam(value = "size", required = false) Integer size,
                     @RequestParam(value = "select", required = false, defaultValue = "") String select,
                     @RequestParam("fileGroupId")Long fileGroupId){

    }
}
