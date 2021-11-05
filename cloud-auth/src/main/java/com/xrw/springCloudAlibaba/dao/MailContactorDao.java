package com.xrw.springCloudAlibaba.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xrw.springCloudAlibaba.entity.MailContactorEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 联系人表
 * 
 * @author xearin
 * @email sunlightcs@gmail.com
 * @date 2019-05-25 13:33:40
 */
@Mapper
public interface MailContactorDao extends BaseMapper<MailContactorEntity> {
	IPage<MailContactorEntity> selectContactir(IPage<MailContactorEntity> pagination, @Param("groupIds") Long[] groupIds, @Param("search") String search, @Param("platformId") Long platformId, @Param("plural") String[] plural);

	List<MailContactorEntity> selectContactir(@Param("groupIds") Long[] groupIds, @Param("search") String search, @Param("platformId") Long platformId);

	IPage<MailContactorEntity> selectCommonContact(IPage<MailContactorEntity> pagination, @Param("search") String search, @Param("platformId") Long platformId);

	int insertContactor(@Param("contactor") MailContactorEntity mailContactorEntity);

	int insertContactorBath(List<MailContactorEntity> contactors);

	MailContactorEntity selectByRpPoliceResponseId(@Param("responseId") Long responseId);

	List<MailContactorEntity> selectContactirByGroupId(@Param("groupId") Long groupId);

	MailContactorEntity selectContactorAndGroupIdsById(@Param("contactorId") Long contactorId);

//	@Select("select sp.* from mail_contactor mc left join sys_platform sp on mc.platform_id=sp.id where mc.open_id = #{openId} and mc.is_deleted = 0 ")
//	List<SysPlatformEntity> selectPlatformByOpenId(@Param("openId")String openId);

	MailContactorEntity selectOneByOpenId(@Param("openId") String openId, @Param("platformId") Long platformId);

    MailContactorEntity selectOneById(@Param("id") Long id, @Param("platformId") Long platformId);

	List<MailContactorEntity> selectContactorAndGroupIdsByOpenId(@Param("openId") String openId);

//	@Select("select name,position,work_unit,id,mobile1,mobile2 from mail_contactor WHERE id IN(SELECT receiver_user FROM `message_infor` where create_user=#{userId}  GROUP BY receiver_user)")
//	@Select("select a.phone,b.name,b.position,b.work_unit,b.mobile1,b.mobile2 from(SELECT phone FROM `message_infor` where create_user=#{userId} GROUP BY phone)a LEFT JOIN mail_contactor b ON a.phone=b.mobile1 AND b.is_deleted=0")

	List<MailContactorEntity> selectContactorByMessageReceiverId(Page<MailContactorEntity> page, String search,
                                                                 Long userId);

	@Select("<script>"
    		+"select * from mail_contactor where id in(select contacts_id from mail_contactor_group where group_id in"
            + "<foreach item='id' index='index' collection='ids'      open='(' separator=',' close=')'>"
            + "#{id}"
            + "</foreach>"
            +")"
            + "</script>")
	List<MailContactorEntity> getListInGroup(@Param("ids") List<String> ids);


    @Select("<script>"
    		+"select * from mail_contactor where id in(select mail_contactor_id from message_contactor_group <if test='ids!=null and ids.size()>0'> where message_group_id in"
            + "<foreach item='id' index='index' collection='ids'      open='(' separator=',' close=')'>"
            + "#{id}"
            + "</foreach>"
			+ "</if>"
            +")"
            + "</script>")
	List<MailContactorEntity> getListInMessageGroup(@Param("ids") List<String> ids);

    @Select("select * from mail_contactor where id in(select contactor_id from rp_unit_contactor where unit_id=#{id}) and is_deleted=0 ")
	List<MailContactorEntity> getListInRpUnit(@Param("id") long id);
    @Select("select * from mail_contactor where id in(select contactor_id from resoure_protect_contactor where protect_id=#{id} and type=#{type})")
	List<MailContactorEntity> listByProtectIdAndType(@Param("id") Long id, @Param("type") int type);

	List<MailContactorEntity> selectContactorListNoPage(@Param("groupIds") List<Long> groupIds,
                                                        @Param("platformId") Long platformId);

    List<MailContactorEntity> contactorInfo(@Param("contactIds") List<String> contactIds);

    MailContactorEntity getDutyContactor(@Param("id") Long id);

	MailContactorEntity getBySourceIdAndType(@Param("sourceId") String sourceId, @Param("sourceType") String sourceType);

	MailContactorEntity getByMobile(@Param("mobile") String mobile);

	List<MailContactorEntity> getListByMobile(@Param("mobile") String mobile);
}
