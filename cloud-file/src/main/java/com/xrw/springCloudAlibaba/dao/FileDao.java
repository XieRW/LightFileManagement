package com.xrw.springCloudAlibaba.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xrw.springCloudAlibaba.entity.FileEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * @author xearin
 * @email
 * @date 2021-11-12 14:00:58
 */
@Mapper
public interface FileDao extends BaseMapper<FileEntity> {

    @Select("<script> " +
            " select f.* " +
            " from file f " +
            " where f.is_deleted=0 and f.user_id = #{userId} " +

            "<if test='select!=null '> " +
            " and (f.filename like '%${select}%') " +
            "</if> " +

            "<if test='fileGroupId!=0 '> " +
            " and f.file_group_id = #{fileGroupId} " +
            "</if> " +

            " order by f.update_time desc " +

            "<if test='offset!=null and size!=null'> " +
            " limit #{offset},#{size} " +
            "</if>" +

            "</script>")
    ArrayList<FileEntity> getSelectPage(@Param("offset") Long offset,
                                        @Param("size") Long size,
                                        @Param("select") String select,
                                        @Param("fileGroupId") Long fileGroupId,
                                        @Param("userId") Long userId);

    @Select("<script> " +
            " select count(f.id) " +
            " from file f " +
            " where f.is_deleted=0 and f.user_id = #{userId} " +

            "<if test='select!=null '> " +
            " and (f.filename like '%${select}%') " +
            "</if> " +

            "<if test='fileGroupId!=0 '> " +
            " and f.file_group_id = #{fileGroupId} " +
            "</if> " +

            " order by f.update_time desc " +

            "<if test='offset!=null and size!=null'> " +
            " limit #{offset},#{size} " +
            "</if>" +

            "</script>")
    Long getSelectPageCount(@Param("offset") Long offset,
                            @Param("size") Long size,
                            @Param("select") String select,
                            @Param("fileGroupId") Long fileGroupId,
                            @Param("userId") Long userId);
}
