package com.xrw.springCloudAlibaba.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xrw.springCloudAlibaba.dao.MailContactorDao;
import com.xrw.springCloudAlibaba.entity.MailContactorEntity;
import com.xrw.springCloudAlibaba.service.MailContactorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("mailContactorService")
public class MailContactorServiceImpl extends ServiceImpl<MailContactorDao, MailContactorEntity> implements MailContactorService {

    @Override
    public int saveContactorBach(List<MailContactorEntity> contactors) {
        return this.baseMapper.insertContactorBath(contactors);
    }

    @Override
    public MailContactorEntity selectByRpPoliceResponseId(Long responseId) {
        return this.baseMapper.selectByRpPoliceResponseId(responseId);
    }

    @Override
    public List<MailContactorEntity> selectContactirByGroupId(Long groupId) {
        return this.baseMapper.selectContactirByGroupId(groupId);
    }

    @Override
    public MailContactorEntity selectContactorAndGroupIdsById(Long contactorId) {
        return baseMapper.selectContactorAndGroupIdsById(contactorId);
    }

    @Override
    public List<MailContactorEntity> selectAllByGroupId(Long[] groupIds, String search) {
        return null;
    }


    @Override
    public MailContactorEntity selectOneByOpenId(String openId, Long platformId) {
        return baseMapper.selectOneByOpenId(openId, platformId);
    }

    @Override
    public MailContactorEntity selectOneById(Long id, Long platformId) {
        return null;
    }

    @Override
    public List<MailContactorEntity> selectContactorAndGroupIdsByOpenId(String openId) {
        return baseMapper.selectContactorAndGroupIdsByOpenId(openId);
    }

    @Override
    public List<MailContactorEntity> getListInGroup(List<String> ids) {
        // TODO Auto-generated method stub
        List<MailContactorEntity> ls = baseMapper.getListInGroup(ids);
        return ls;
    }

    @Override
    public List<MailContactorEntity> getListInMessageGroup(List<String> ids) {
        List<MailContactorEntity> ls = baseMapper.getListInMessageGroup(ids);
        return ls;
    }

    @Override
    public List<MailContactorEntity> getListInRpUnit(long id) {
        List<MailContactorEntity> ls = baseMapper.getListInRpUnit(id);
        return ls;
    }

    @Override
    public List<MailContactorEntity> listByProtectIdAndType(Long id, int type) {
        List<MailContactorEntity> ls = baseMapper.listByProtectIdAndType(id, type);
        return ls;
    }

    @Override
    public List<MailContactorEntity> listNoPage(Map<String, Object> params) {
        return null;
    }


    @Override
    public List<MailContactorEntity> contactorInfo(List<String> contactIds) {
        return baseMapper.contactorInfo(contactIds);
    }

    @Override
    public MailContactorEntity getDutyContactor(Long id) {
        return baseMapper.getDutyContactor(id);
    }

    @Override
    public MailContactorEntity getBySourceIdAndType(String sourceId, String sourceType) {
        return baseMapper.getBySourceIdAndType(sourceId, sourceType);
    }

    @Override
    public MailContactorEntity getByMobile(String mobile) {
        return baseMapper.getByMobile(mobile);
    }

    @Override
    public List<MailContactorEntity> getListByMobile(String mobile) {
        return baseMapper.getListByMobile(mobile);
    }
}