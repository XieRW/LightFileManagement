package com.xrw.springCloudAlibaba.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xrw.springCloudAlibaba.entity.FileEntity;
import com.xrw.springCloudAlibaba.entity.FileShareFriendEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * @author xearin
 * @email
 * @date 2021-11-29 14:00:58
 */
@Mapper
public interface FileShareFriendDao extends BaseMapper<FileShareFriendEntity> {

    @Select("<script> " +
            " select f.*,u.name as userName,fsf.file_share_permission as fileSharePermission " +
            " from file_share_friend fsf " +
            " left join file f on fsf.file_id = f.id " +
            " left join user u on f.user_id = u.id " +
            " where f.is_deleted=0 and fsf.is_deleted=0 and fsf.share_to = #{userId} " +

            "<if test='select!=null '> " +
            " and (f.filename like '%${select}%') " +
            "</if> " +

            " order by fsf.update_time desc " +

            "<if test='offset!=null and size!=null'> " +
            " limit #{offset},#{size} " +
            "</if>" +

            "</script>")
    ArrayList<FileEntity> getSelectPage(@Param("offset") Long offset,
                                        @Param("size") Long size,
                                        @Param("select") String select,
                                        @Param("userId") Long userId);

    @Select("<script> " +
            " select count(f.id) " +
            " from file_share_friend fsf " +
            " left join file f on fsf.file_id = f.id " +
            " left join user u on f.user_id = u.id " +
            " where f.is_deleted=0 and fsf.is_deleted=0 and fsf.share_to = #{userId} " +

            "<if test='select!=null '> " +
            " and (f.filename like '%${select}%') " +
            "</if> " +

            " order by fsf.update_time desc " +

            "<if test='offset!=null and size!=null'> " +
            " limit #{offset},#{size} " +
            "</if>" +

            "</script>")
    Long getSelectPageCount(@Param("offset") Long offset,
                            @Param("size") Long size,
                            @Param("select") String select,
                            @Param("userId") Long userId);
}
