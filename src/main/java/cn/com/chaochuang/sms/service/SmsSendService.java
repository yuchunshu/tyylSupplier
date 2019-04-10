/*
 * FileName:    SmsSendService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年12月05日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.sms.service;

import cn.com.chaochuang.common.bean.ReturnBean;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.sms.bean.SmSendInfo;
import cn.com.chaochuang.sms.bean.SmsEditInfo;
import cn.com.chaochuang.sms.bean.SmsPhraseInfo;
import cn.com.chaochuang.sms.domain.SmsPhraseType;

import java.util.List;

/**
 * @author Shicx
 *
 * 短信保存与查询
 */
public interface SmsSendService {

    /**
     * 调用接口保存发送的短信
     * @param editInfo
     * @return
     */
    ReturnBean saveSendSmsByWebservice(SmsEditInfo editInfo);

    /**
     * 调用接口保存发送的短信
     * @param sendInfoList
     * @return
     */
    ReturnBean saveSendSmsByWebservice(List<SmSendInfo> sendInfoList);

    /**
     * 保存发送的短信(只用于本地调试)
     * @param editInfo
     * @return
     */
    ReturnBean saveSmsLocal(SmsEditInfo editInfo);

    /**
     * 保存常用短语
     * @param phraseInfo
     * @param user 创建短语的用户，新建时不能为空
     * @return
     */
    ReturnBean savePhrase(SmsPhraseInfo phraseInfo, SysUser user);

    /**
     * 删除短语
     * @param phraseId
     * @return
     */
    ReturnBean deletePhrase(Long phraseId);

    /**
     * 保存短语类型
     * @param phraseTypeInfo
     * @param user 创建短语的用户，新建时不能为空
     * @return
     */
    ReturnBean savePhraseType(SmsPhraseType phraseTypeInfo, SysUser user);

    /**
     * 删除短语类型
     * @param typeId
     * @return
     */
    ReturnBean deletePhraseType(Long typeId);
}
