package cn.com.chaochuang.doc.sms.service;


import cn.com.chaochuang.doc.sms.bean.SmsBaseInfo;


/** 
 * @ClassName: SmsService 
 * @Description: 短信发送处理
 * @author: chunshu
 * @date: 2017年9月21日 上午10:11:03  
 */
public interface SmsService {

    
    /** 
     * @Title: sendUserIdListMessage 
     * @Description: 短信发送处理
     * @param userIds
     * @param smsBaseInfo
     * @return: void
     */
    void sendUserIdListMessage(String userIds, SmsBaseInfo smsBaseInfo);

}
