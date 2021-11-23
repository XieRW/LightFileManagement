package com.xrw.springCloudAlibaba.Controller;

import com.xrw.springCloudAlibaba.entity.FileEntity;
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
import java.util.List;

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

    /**
     * @param files:       文件
     * @param type:        文件类型
     * @param remake:      备注
     * @param fileGroupId: 文件分组
     * @Description: 批量上传文件
     * @return: com.xrw.springCloudAlibaba.vo.ResponseJSON
     * @Author: xearin 1429382875@qq.com
     * @Date: 2021/11/23
     */
    @RequestMapping("/upload")
    public ResponseJSON upload(@RequestPart("files") MultipartFile[] files,
                               @RequestParam("type") String type,
                               @RequestParam("remake") String remake,
                               @RequestParam("fileGroupId") Long fileGroupId) {
        List<FileEntity> entities = fileService.upload(files, type, remake, fileGroupId, LoginUserHolder.getUserId());
        return new ResponseJSON(entities);
    }

    /**
     * @param id: 文件表id
     * @Description: 文件删除
     * @return: com.xrw.springCloudAlibaba.vo.ResponseJSON
     * @Author: xearin 1429382875@qq.com
     * @Date: 2021/11/23
     */
    @RequestMapping("delete")
    public ResponseJSON delete(@RequestParam("id") Long id) {
        fileService.delete(id, LoginUserHolder.getUserId());
        return new ResponseJSON();
    }

    /**
     * @param id:              文件id
     * @param isForceDownload: 是否强制下载
     * @param response:        返回体
     * @Description: 文件下载
     * @return: void
     * @Author: xearin 1429382875@qq.com
     * @Date: 2021/11/23
     */
    @RequestMapping("download")
    public void download(@RequestParam("id") Long id,
                         @RequestParam(value = "isForceDownload", defaultValue = "true") boolean isForceDownload,
                         HttpServletResponse response) {
        fileService.download(id, LoginUserHolder.getUserId(), isForceDownload, response);
    }

    /**
     * @param page:        页码，从1开始
     * @param size:        每页数量
     * @param select:      模糊查询关键字，按文件名称查询
     * @param fileGroupId: 文件分组id，默认为0，即查全部分组下的文件
     * @Description: 分页查询文件列表
     * @return: com.xrw.springCloudAlibaba.vo.ResponseJSON
     * @Author: xearin 1429382875@qq.com
     * @Date: 2021/11/23
     */
    @RequestMapping("page")
    public ResponseJSON page(@RequestParam(value = "page", required = false) Long page,
                             @RequestParam(value = "size", required = false) Long size,
                             @RequestParam(value = "select", required = false, defaultValue = "") String select,
                             @RequestParam(value = "fileGroupId", defaultValue = "0") Long fileGroupId) {
        return new ResponseJSON(fileService.getSelectPage(page, size, select, fileGroupId, LoginUserHolder.getUserId()));
    }
}
