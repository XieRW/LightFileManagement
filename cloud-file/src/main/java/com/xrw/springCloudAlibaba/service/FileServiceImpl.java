package com.xrw.springCloudAlibaba.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xrw.springCloudAlibaba.dao.FileDao;
import com.xrw.springCloudAlibaba.entity.FileEntity;
import com.xrw.springCloudAlibaba.enums.SysDictEnum;
import com.xrw.springCloudAlibaba.enums.SysDictItemEnum;
import com.xrw.springCloudAlibaba.exception.ApiError;
import com.xrw.springCloudAlibaba.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 文件服务层
 * @Author: xearin
 * @Date: 2021-11-12
 */
@Slf4j
@Service("FileServiceImpl")
public class FileServiceImpl extends ServiceImpl<FileDao, FileEntity> {

    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${file.upload-path}")
    private String fileUploadPath;
    @Resource(name = "MyThreadPoolExecutor1")
    private ThreadPoolExecutor threadPoolExecutor;


    public List<FileEntity> upload(MultipartFile[] files, String type, String remake, Long fileGroupId, Long userId) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        String month = df.format(new Date());
        String fileTypeMiddlePath = SysDictItemEnum.getByKey(SysDictEnum.file_type.getKey(), type).getValue();
        fileTypeMiddlePath += month + "/";
        List<FileEntity> entities = new ArrayList<>();
        for (MultipartFile file : files) {
            df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String originalFileName = file.getOriginalFilename();
            if (originalFileName.indexOf("\\") >= 0) {
                originalFileName = originalFileName.substring(originalFileName.lastIndexOf("\\") + 1);
            }
            String fileName;
            String extension = "";
            if (originalFileName.lastIndexOf(".") >= 0) {
                fileName = df.format(new Date()) + originalFileName.substring(originalFileName.lastIndexOf("."));
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            } else {
                fileName = df.format(new Date()) + originalFileName;
            }
            String filePath = fileUploadPath + fileTypeMiddlePath + fileName;
            File serverFile = new File(filePath);
            if (!serverFile.getParentFile().exists()) {
                if (!serverFile.getParentFile().mkdirs()) {
                    throw new ApiException(ApiError.FILE_MKDIR_ERROR);
                }
            }
            try {
                file.transferTo(serverFile);
            } catch (IOException e) {
                throw new ApiException(ApiError.FILE_TRANSFER_ERROR);
            }
            FileEntity fileEntity = new FileEntity().setCreateUserId(userId)
                    .setUpdateUserId(userId)
                    .setUserId(userId)
                    .setType(type)
                    .setSize(file.getSize())
                    .setFilename(originalFileName)
                    .setUrl(contextPath + fileTypeMiddlePath + fileName)
                    .setPath(fileTypeMiddlePath + fileName)
                    .setStorename(fileName)
                    .setExtension(extension)
                    .setRemark(remake)
                    .setDirectory(SysDictItemEnum.getByKey(SysDictEnum.file_type.getKey(), type).getValue())
                    .setFileGroupId(fileGroupId);
            baseMapper.insert(fileEntity);
            entities.add(fileEntity);
        }
        return entities;
    }

    public boolean delete(Long id, Long userId) {
        FileEntity fileEntity = baseMapper.selectById(id);
        if (fileEntity == null) {
            throw new ApiException(ApiError.DATA_NOT_EXISTS);
        }
        //todo 添加文件共享功能之后，此时的权限判断条件需要做相应的更改
        if (!fileEntity.getUserId().equals(userId)) {
            throw new ApiException(ApiError.INTERFACE_UNPREMITTED);
        }
        String filePath = fileUploadPath + fileEntity.getPath();
        File file = new File(filePath);
        Future<Boolean> submit = threadPoolExecutor.submit(() -> delFile(file));
        try {
            submit.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApiException(ApiError.FILE_DELETE_ERROR);
        }
        if (baseMapper.deleteById(id) > 0) {
            return true;
        } else {
            throw new ApiException(ApiError.FILE_DELETE_ERROR);
        }
    }

    /**
     * @param file: 文件或者文件夹
     * @Description: 删除一个文件或者逐级删除文件夹
     * @return: boolean
     * @Author: xearin 1429382875@qq.com
     * @Date: 2021/11/23
     */
    public boolean delFile(File file) {
        if (!file.isFile()) {
            File[] files = file.listFiles();
            assert files != null;
            for (File value : files) {
                if (!delFile(value)) {
                    throw new ApiException(ApiError.FILE_DELETE_ERROR);
                }
            }
        }
        if (!file.delete()) {
            throw new ApiException(ApiError.FILE_DELETE_ERROR);
        }
        return true;
    }

    public void download(Long id, Long userId, boolean isForceDownload, HttpServletResponse response) {
        FileEntity fileEntity = baseMapper.selectById(id);
        if (fileEntity == null) {
            throw new ApiException(ApiError.DATA_NOT_EXISTS);
        }
        //todo 添加文件共享功能之后，此时的权限判断条件需要做相应的更改
        if (!fileEntity.getUserId().equals(userId)) {
            throw new ApiException(ApiError.INTERFACE_UNPREMITTED);
        }
        File file = new File(fileUploadPath + fileEntity.getPath());
        if (!file.exists()) {
            throw new ApiException(ApiError.FILE_GET_ERROR);
        }
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        byte[] buffer = new byte[1024];
        try {
            if (isForceDownload) {
                // 设置强制下载
                response.setContentType("application/force-download");
            }
            //设置文件名
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileEntity.getFilename(), "UTF-8"));
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (IOException e) {
            throw new ApiException(ApiError.FILE_GET_ERROR);
        } finally {
            //关闭输入流
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
            }
            //关闭输入流
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
            }
        }
    }

    public Page<FileEntity> getSelectPage(Long page, Long size, String select, Long fileGroupId, Long userId) {
        Long offset = 0L;
        if (page != null && size != null) {
            offset = (page - 1) * size;
        }
        ArrayList<FileEntity> applicationEntities = baseMapper.getSelectPage(offset, size, select, fileGroupId, userId);
        Long pageCount = baseMapper.getSelectPageCount(offset, size, select, fileGroupId, userId);
        Page<FileEntity> entityPage = new Page<>(Optional.ofNullable(page).orElse(0L),
                Optional.ofNullable(size).orElse(0L),
                Optional.ofNullable(pageCount).orElse(0L));
        entityPage.setRecords(applicationEntities);
        return entityPage;
    }
}