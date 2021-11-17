package com.xrw.springCloudAlibaba.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xrw.springCloudAlibaba.entity.UserFriendApplicationEntity;
import com.xrw.springCloudAlibaba.entity.UserFriendEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xearin
 * @email
 * @date 2021-11-12 14:00:58
 */
@Mapper
public interface UserFriendDao extends BaseMapper<UserFriendEntity> {

    @Select("<script> " +
            " select uf.*,u.name as applyFromName " +
            " from user u " +
            " left join user_friend uf on uf.apply_from_id = u.id or uf.apply_to_id = u.id " +
            " where uf.is_deleted=0 and u.id != #{applyToId} " +
            " and (uf.apply_to_id = #{applyToId} or uf.apply_from_id = #{applyToId}) " +

            "<if test='select!=null '> " +
            " and (u.username like '%${select}%' or u.name like '%${select}%' or u.mobile like '%${select}%' or  u.email like '%${select}%')" +
            "</if> " +

            " order by u.name " +

            "<if test='offset!=null and size!=null'> " +
            " limit #{offset},#{size} " +
            "</if>" +

            "</script>")
    ArrayList<UserFriendEntity> getSelectPage(@Param("applyToId") Long applyToId,
                                              @Param("select") String select,
                                              @Param("offset") Integer offset,
                                              @Param("size") Integer size);

    @Select("<script> " +
            " select count(uf.id) " +
            " from user u " +
            " left join user_friend uf on uf.apply_from_id = u.id or uf.apply_to_id = u.id " +
            " where uf.is_deleted=0 and u.id != #{applyToId} " +
            " and (uf.apply_to_id = #{applyToId} or uf.apply_from_id = #{applyToId}) " +

            "<if test='select!=null '> " +
            " and (u.username like '%${select}%' or u.name like '%${select}%' or u.mobile like '%${select}%' or  u.email like '%${select}%')" +
            "</if> " +

            " order by u.name " +

            "<if test='offset!=null and size!=null'> " +
            " limit #{offset},#{size} " +
            "</if>" +

            "</script>")
    Long getSelectPageCount(@Param("applyToId") Long applyToId,
                            @Param("select") String select,
                            @Param("offset") Integer offset,
                            @Param("size") Integer size);
}
