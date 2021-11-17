package com.xrw.springCloudAlibaba.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xrw.springCloudAlibaba.entity.UserFriendApplicationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * @author xearin
 * @email 
 * @date 2021-11-12 14:00:58
 */
@Mapper
public interface UserFriendApplicationDao extends BaseMapper<UserFriendApplicationEntity> {

    @Select("<script> "+
            " select ufa.*,u.name as applyFromName " +
            " from user_friend_application ufa " +
            " left join user u on ufa.apply_from_id = u.id " +
            " where ufa.is_deleted=0 " +
            " and ufa.apply_to_id = #{applyToId} and ufa.apply_status = #{status} order by ufa.id " +

            "<if test='offset!=null and size!=null'> " +
            " limit #{offset},#{size} "+
            "</if>"+

            "</script>")
    ArrayList<UserFriendApplicationEntity> getSelectPage(String status,Long applyToId,Integer offset, Integer size);

    @Select("<script> "+
            " select count(ufa.id) " +
            " from user_friend_application ufa " +
            " left join user u on ufa.apply_from_id = u.id " +
            " where ufa.is_deleted=0 " +
            " and ufa.apply_to_id = #{applyToId} and ufa.apply_status = #{status} order by ufa.id " +

            "<if test='offset!=null and size!=null'> " +
            " limit #{offset},#{size} "+
            "</if>"+

            "</script>")
    Long getSelectPageCount(String status,Long applyToId,Integer offset, Integer size);
}
