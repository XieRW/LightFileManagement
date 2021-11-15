package com.xrw.springCloudAlibaba.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xrw.springCloudAlibaba.dao.MailContactorDao;
import com.xrw.springCloudAlibaba.entity.MailContactorEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("mailContactorService")
public class MailContactorServiceImpl extends ServiceImpl<MailContactorDao, MailContactorEntity>   {

    public int saveContactorBach(List<MailContactorEntity> contactors) {
        return this.baseMapper.insertContactorBath(contactors);
    }

    public MailContactorEntity selectByRpPoliceResponseId(Long responseId) {
        return this.baseMapper.selectByRpPoliceResponseId(responseId);
    }

    public List<MailContactorEntity> selectContactirByGroupId(Long groupId) {
        return this.baseMapper.selectContactirByGroupId(groupId);
    }

    public MailContactorEntity selectContactorAndGroupIdsById(Long contactorId) {
        return baseMapper.selectContactorAndGroupIdsById(contactorId);
    }

    public List<MailContactorEntity> selectAllByGroupId(Long[] groupIds, String search) {
        return null;
    }



    public MailContactorEntity selectOneByOpenId(String openId, Long platformId) {
        return baseMapper.selectOneByOpenId(openId, platformId);
    }

    public MailContactorEntity selectOneById(Long id, Long platformId) {
        return null;
    }

    public List<MailContactorEntity> selectContactorAndGroupIdsByOpenId(String openId) {
        return baseMapper.selectContactorAndGroupIdsByOpenId(openId);
    }

    public List<MailContactorEntity> getListInGroup(List<String> ids) {
        // TODO Auto-generated method stub
        List<MailContactorEntity> ls = baseMapper.getListInGroup(ids);
        return ls;
    }

    public List<MailContactorEntity> getListInMessageGroup(List<String> ids) {
        List<MailContactorEntity> ls = baseMapper.getListInMessageGroup(ids);
        return ls;
    }

    public List<MailContactorEntity> getListInRpUnit(long id) {
        List<MailContactorEntity> ls = baseMapper.getListInRpUnit(id);
        return ls;
    }

    public List<MailContactorEntity> listByProtectIdAndType(Long id, int type) {
        List<MailContactorEntity> ls = baseMapper.listByProtectIdAndType(id, type);
        return ls;
    }

    public List<MailContactorEntity> listNoPage(Map<String, Object> params) {
        return null;
    }


    public List<MailContactorEntity> contactorInfo(List<String> contactIds) {
        return baseMapper.contactorInfo(contactIds);
    }

    public MailContactorEntity getDutyContactor(Long id) {
        return baseMapper.getDutyContactor(id);
    }

    public MailContactorEntity getBySourceIdAndType(String sourceId, String sourceType) {
        return baseMapper.getBySourceIdAndType(sourceId, sourceType);
    }

    public MailContactorEntity getByMobile(String mobile) {
        return baseMapper.getByMobile(mobile);
    }

    public List<MailContactorEntity> getListByMobile(String mobile) {
        return baseMapper.getListByMobile(mobile);
    }
}