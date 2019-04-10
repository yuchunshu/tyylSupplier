/*
 * FileName:    SmsSendService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年12月05日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.sms.service;

import cn.com.chaochuang.common.bean.ReturnBean;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.repository.SysUserRepository;
import cn.com.chaochuang.sms.bean.ArrayOfSmSendInfo;
import cn.com.chaochuang.sms.bean.SmSendInfo;
import cn.com.chaochuang.sms.bean.SmsEditInfo;
import cn.com.chaochuang.sms.bean.SmsPhraseInfo;
import cn.com.chaochuang.sms.domain.SmsPhrase;
import cn.com.chaochuang.sms.domain.SmsPhraseType;
import cn.com.chaochuang.sms.domain.SmsSent;
import cn.com.chaochuang.sms.domain.SmsSending;
import cn.com.chaochuang.sms.repository.SmsPhraseRepository;
import cn.com.chaochuang.sms.repository.SmsPhraseTypeRepository;
import cn.com.chaochuang.sms.repository.SmsSentRepository;
import cn.com.chaochuang.sms.repository.SmsSendingRepository;
import cn.com.chaochuang.sms.webservice.ISmsExServicePortType;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author Shicx
 * <p>
 * 短信保存与查询
 */
@Service
@Transactional
public class SmsSendServiceImpl implements SmsSendService {

    /**
     * 系统类型，短信发送时需要的参数
     */
    @Value("${sys.type.flag}")
    private String sysType;

    @Autowired
    private ISmsExServicePortType iSmsExServicePortType;
    @Autowired
    private SmsSendingRepository smsSendingRepository;
    @Autowired
    private SmsSentRepository smsSentRepository;
    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private SmsPhraseRepository smsPhraseRepository;
    @Autowired
    private SmsPhraseTypeRepository smsPhraseTypeRepository;

    /**
     * 获取发送短信的信息，用于调用webservice接口
     * @param editInfo
     * @return
     */
    private List<SmSendInfo> getSmsSendDataList(SmsEditInfo editInfo) {
        String[] dataArr = editInfo.getReceiveManData().split(",");
        List<SmSendInfo> smSendInfoList = Lists.newArrayList();
        for (int i = 0; i < dataArr.length; i++) {
            String[] dataItemArr = dataArr[i].split(":");
            //号码和接收人名称不能为空
            if(dataItemArr.length>=2&&StringUtils.isNotEmpty(dataItemArr[0])&&StringUtils.isNotEmpty(dataItemArr[1])){
                SmSendInfo sendInfo = new SmSendInfo();
                sendInfo.setContent(editInfo.getContent());
                sendInfo.setReceivePhone(dataItemArr[0]);
                sendInfo.setReceiveMan(dataItemArr[1]);

                //短信发送时需要的参数
                sendInfo.setSysType(this.sysType);
                sendInfo.setServiceId(this.sysType);

                //todo 这里的发送人id和部门id要与旧的用户表一致。待数据迁移后再修改。现在只是测试使用
                //使用旧系统用户表中的 user_info_id 字段
                sendInfo.setSendManId(editInfo.getCreator().getId());
                if (editInfo.getCreator().getDepartment() != null) {
                    sendInfo.setSendDepId(editInfo.getCreator().getDepartment().getId());
                }

                if (editInfo.getTimingSendFlag() != null) {
                    sendInfo.setTimingSendFlag(editInfo.getTimingSendFlag());
                } else {
                    sendInfo.setTimingSendFlag(SmsSending.IMM_SEND);
                }
                sendInfo.setTimingSendTime(editInfo.getTimingSendTime());
                sendInfo.setReqDeliveryReport(editInfo.getReqDeliveryReport());

                //接收人id,如果是系统用户则需要保存
                if(dataItemArr.length==3&&StringUtils.isNotEmpty(dataItemArr[2])){
                    SysUser user = sysUserRepository.findOne(Long.valueOf(dataItemArr[2]));
                    if(user!=null&&StringUtils.isNotEmpty(user.getMobile())){
                        sendInfo.setReceiveManId(user.getId());
                    }
                }

                smSendInfoList.add(sendInfo);
            }
        }

        return smSendInfoList;
    }

    /**
     * 调用接口保存发送的短信
     *
     * @param editInfo
     * @return
     */
    @Override
    public ReturnBean saveSendSmsByWebservice(SmsEditInfo editInfo) {
        //短信无暂存功能，保存到数据库后会有其他程序读取
        if (editInfo.getCreator() == null) {
            return new ReturnBean("当前用户不存在", null, false);
        }
        if (StringUtils.isEmpty(editInfo.getReceiveManData())) {
            return new ReturnBean("接收人信息为空", null, false);
        }
        ArrayOfSmSendInfo smsArray = new ArrayOfSmSendInfo();
        List<SmSendInfo> smSendInfoList = this.getSmsSendDataList(editInfo);
        smsArray.setSmSendInfo(smSendInfoList);
        //调用接口进行保存及发送
        iSmsExServicePortType.smsEx(smsArray);
        return new ReturnBean("发送成功", null, true);
    }

    /**
     * 调用接口保存发送的短信
     *
     * @param sendInfoList
     * @return
     */
    @Override
    public ReturnBean saveSendSmsByWebservice(List<SmSendInfo> sendInfoList) {
        if(sendInfoList==null){
            return new ReturnBean("接收信息为空", null, false);
        }
        ArrayOfSmSendInfo smsArray = new ArrayOfSmSendInfo();
        smsArray.setSmSendInfo(sendInfoList);
        //调用接口进行保存及发送
        iSmsExServicePortType.smsEx(smsArray);
        return new ReturnBean("发送成功", null, true);
    }

    /**
     * 保存发送的短信(只用于本地调试)
     *
     * @param editInfo
     * @return
     */
    @Override
    public ReturnBean saveSmsLocal(SmsEditInfo editInfo) {
        //短信无暂存功能，保存到数据库后会有其他程序读取
        if (editInfo.getCreator() == null) {
            return new ReturnBean("当前用户不存在", null, false);
        }
        if (StringUtils.isEmpty(editInfo.getReceiveManData())) {
            return new ReturnBean("接收人信息为空", null, false);
        }
        List<SmSendInfo> smSendInfoList = this.getSmsSendDataList(editInfo);
        //在本地保存
        for(SmSendInfo sendInfo : smSendInfoList){
            SmsSent smsSent = BeanCopyBuilder.buildObject(sendInfo,SmsSent.class);
            smsSent.setSendTime(new Date());
            smsSentRepository.save(smsSent);
        }
        return new ReturnBean("发送成功", null, true);
    }

    /**
     * 保存常用短语
     * @param phraseInfo
     * @param user 创建短语的用户，新建时不能为空
     * @return
     */
    @Override
    public ReturnBean savePhrase(SmsPhraseInfo phraseInfo,SysUser user) {
        if(StringUtils.isEmpty(phraseInfo.getPhraseName())||StringUtils.isEmpty(phraseInfo.getPhraseTypeName())){
            return new ReturnBean("短语内容为空", null, false);
        }
        //先检查短语类型数据
        SmsPhraseType phraseType = null;
        if(phraseInfo.getSmsPhraseTypeId()!=null){
            phraseType = smsPhraseTypeRepository.findOne(phraseInfo.getSmsPhraseTypeId());
        }
        if(phraseType==null){
            //新建短语类型
            if (user == null) {
                return new ReturnBean("当前用户不存在", null, false);
            }
            phraseType = new SmsPhraseType();
            phraseType.setUserId(user.getId());
            phraseType.setUserName(user.getUserName());
        }
        phraseType.setPhraseTypeName(phraseInfo.getPhraseTypeName());
        smsPhraseTypeRepository.saveAndFlush(phraseType);

        //短语内容
        SmsPhrase smsPhrase = null;
        if(phraseInfo.getSmsPhraseId()!=null){
            smsPhrase = smsPhraseRepository.findOne(phraseInfo.getSmsPhraseId());
        }
        if(smsPhrase==null){
            //新增
            if (user == null) {
                return new ReturnBean("当前用户不存在", null, false);
            }
            smsPhrase = new SmsPhrase();
            smsPhrase.setUserId(user.getId());
            smsPhrase.setUserName(user.getUserName());
        }
        smsPhrase.setPhraseName(phraseInfo.getPhraseName());
        smsPhrase.setSmsPhraseTypeId(phraseType.getSmsPhraseTypeId());
        smsPhrase.setSortFlag(phraseInfo.getSortFlag());
        smsPhraseRepository.save(smsPhrase);
        return new ReturnBean("保存成功", null, true);
    }

    @Override
    public ReturnBean savePhraseType(SmsPhraseType phraseTypeInfo, SysUser user) {
        SmsPhraseType phraseType = null;
        if(phraseTypeInfo.getSmsPhraseTypeId()!=null){
            phraseType =smsPhraseTypeRepository.findOne(phraseTypeInfo.getSmsPhraseTypeId());
        }
        if(phraseType==null){
            //新增
            phraseType = new SmsPhraseType();
            if (user == null) {
                return new ReturnBean("当前用户不存在", null, false);
            }
            phraseType.setUserId(user.getId());
            phraseType.setUserName(user.getUserName());
        }
        phraseType.setPhraseTypeName(phraseTypeInfo.getPhraseTypeName());
        smsPhraseTypeRepository.save(phraseType);
        return new ReturnBean("保存成功", null, true);
    }

    /**
     * 删除短语
     * @param phraseId
     * @return
     */
    @Override
    public ReturnBean deletePhrase(Long phraseId) {
        if(phraseId == null){
            return new ReturnBean("id为空", null, false);
        }
        smsPhraseRepository.delete(phraseId);
        return new ReturnBean("删除成功", null, true);
    }

    /**
     * 删除短语类型
     * @param typeId
     * @return
     */
    @Override
    public ReturnBean deletePhraseType(Long typeId) {
        if(typeId == null){
            return new ReturnBean("id为空", null, false);
        }
        smsPhraseTypeRepository.delete(typeId);
        return new ReturnBean("删除成功", null, true);
    }
}

