package com.xrw.springCloudAlibaba.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xrw.springCloudAlibaba.entity.FileEntity;
import com.xrw.springCloudAlibaba.service.FileShareFriendServiceImpl;
import com.xrw.springCloudAlibaba.utils.login.LoginUserHolder;
import com.xrw.springCloudAlibaba.vo.ResponseJSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: LightFileManagement
 * @description: 文件共享控制器层
 * @author: xearin 1429382875@qq.com
 * @create: 2021-11-29 14:44
 **/
@Slf4j
@RestController
@RequestMapping("/file/share")
public class FileShareFriendController {
    @Autowired
    private FileShareFriendServiceImpl fileShareFriendService;

    /**
     * @param fileId:     文件id
     * @param friendId:   共享文件的目标用户
     * @param permission: 共享权限
     * @Description: 新增或者修改文件共享权限
     * @return: com.xrw.springCloudAlibaba.vo.ResponseJSON
     * @Author: xearin 1429382875@qq.com
     * @Date: 2021/11/29
     */
    @RequestMapping("/save")
    public ResponseJSON save(@RequestParam("fileId") Long fileId,
                             @RequestParam("friendId") Long friendId,
                             @RequestParam(value = "permission", defaultValue = "1") String permission) {
        fileShareFriendService.save(fileId, friendId, permission, LoginUserHolder.getUserId());
        return new ResponseJSON();
    }

    /**
     * @param page:   页码，从1开始
     * @param size:   每页数量
     * @param select: 模糊查询关键字，按文件名称查询
     * @Description: 分页查询共享文件列表
     * @return: com.xrw.springCloudAlibaba.vo.ResponseJSON
     * @Author: xearin 1429382875@qq.com
     * @Date: 2021/11/29
     */
    @RequestMapping("page")
    public ResponseJSON page(@RequestParam(value = "page", required = false) Long page,
                             @RequestParam(value = "size", required = false) Long size,
                             @RequestParam(value = "select", required = false, defaultValue = "") String select) {
        Page<FileEntity> selectPage = fileShareFriendService.getSelectPage(page, size, select, LoginUserHolder.getUserId());
        return new ResponseJSON(selectPage);
    }

    /**
     * @param fileId:
     * @Description: delete 删除文件
     * @return: com.xrw.springCloudAlibaba.vo.ResponseJSON
     * @Author: xearin 1429382875@qq.com
     * @Date: 2021/11/29
     */
    @RequestMapping("delete")
    public ResponseJSON delete(@RequestParam("fileId") Long fileId) {
        fileShareFriendService.delete(fileId, LoginUserHolder.getUserId());
        return new ResponseJSON();
    }

    /**
     * @param fileId: 文件id
     * @Description: deleteShare 删除共享关系（共享给他人或者收到的共享文件）
     * @return: com.xrw.springCloudAlibaba.vo.ResponseJSON
     * @Author: xearin 1429382875@qq.com
     * @Date: 2021/11/29
     */
    @RequestMapping("deleteShare")
    public ResponseJSON deleteShare(@RequestParam("fileId") Long fileId) {
        fileShareFriendService.deleteShare(fileId, LoginUserHolder.getUserId());
        return new ResponseJSON();
    }
}
