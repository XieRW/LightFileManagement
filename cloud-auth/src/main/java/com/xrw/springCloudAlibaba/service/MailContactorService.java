package com.xrw.springCloudAlibaba.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xrw.springCloudAlibaba.entity.MailContactorEntity;

import java.util.List;
import java.util.Map;

/**
 * 联系人表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-25 13:33:40
 */
public interface MailContactorService extends IService<MailContactorEntity> {


    int saveContactorBach(List<MailContactorEntity> contactors);

    MailContactorEntity selectByRpPoliceResponseId(Long responseId);

    List<MailContactorEntity> selectContactirByGroupId(Long groupId);

    MailContactorEntity selectContactorAndGroupIdsById(Long contactorId);


    List<MailContactorEntity> selectAllByGroupId(Long[] groupIds, String search) ;


//    List<SysPlatformEntity> selectPlatformByOpenId(String openId);

    MailContactorEntity selectOneByOpenId(String openId, Long platformId);

    MailContactorEntity selectOneById(Long id, Long platformId);

    List<MailContactorEntity> selectContactorAndGroupIdsByOpenId(String openId);

//    PageUtils selectContactorByMessageReceiverId(Map<String, Object> params);

	List<MailContactorEntity> getListInGroup(List<String> ids);

	List<MailContactorEntity> getListInMessageGroup(List<String> ids);

	List<MailContactorEntity> getListInRpUnit(long id);

	List<MailContactorEntity> listByProtectIdAndType(Long id, int i);

    List<MailContactorEntity> listNoPage(Map<String, Object> params);


    List<MailContactorEntity> contactorInfo(List<String> contactIds);

    MailContactorEntity getDutyContactor(Long id);

    MailContactorEntity getBySourceIdAndType(String sourceId, String sourceType);

    MailContactorEntity getByMobile(String mobile);

    List<MailContactorEntity> getListByMobile(String mobile);
}

