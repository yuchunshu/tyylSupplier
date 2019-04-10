package cn.com.chaochuang.doc.sms.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.repository.SysUserRepository;
import cn.com.chaochuang.doc.sms.bean.SmsBaseInfo;
import cn.com.chaochuang.sms.bean.ArrayOfSmSendInfo;
import cn.com.chaochuang.sms.bean.SmSendInfo;
import cn.com.chaochuang.sms.webservice.ISmsExServicePortType;



/** 
 * @ClassName: SmsServiceImpl 
 * @Description: 短信发送处理接口实现
 * @author: chunshu
 * @date: 2017年9月21日 上午10:11:20  
 */
@Service
@Transactional
public class SmsServiceImpl implements SmsService {

	@Autowired
    private ISmsExServicePortType  smsService;
	
    @Autowired
    private SysUserRepository  	   sysUserRepository;
    
    @Override
    public void sendUserIdListMessage(String userIds, SmsBaseInfo smsBaseInfo) {
        if (userIds != null && !"".equals(userIds)) {
            String[] userId = userIds.split(",");
            if (null != userId && userId.length > 0) {
            	ArrayOfSmSendInfo smsArray = new ArrayOfSmSendInfo();
                List<SmSendInfo> smSendInfoList = smsArray.getSmSendInfo();
                SysUser sender = sysUserRepository.findOne(smsBaseInfo.getSendManId());
                for (int i = 0; i < userId.length; i++) {
                    String suserId = userId[i].split("\\|")[0];
                    SmSendInfo smSendInfo = this.createSmSendInfo(smsBaseInfo,sysUserRepository.findOne(new Long(suserId)), sender);
                    smSendInfoList.add(smSendInfo);
                }
                smsArray.setSmSendInfo(smSendInfoList);
                this.smsService.smsEx(smsArray);
            }
        }
    }

    private SmSendInfo createSmSendInfo(SmsBaseInfo smsBaseInfo, SysUser receiver, SysUser sendDer) {
        SmSendInfo smSendInfo = new SmSendInfo();
        smSendInfo.setContent(smsBaseInfo.getMessage());
//        smSendInfo.setReceivePhone(receiver.getMobile());
        smSendInfo.setReceivePhone("15277096697");//测试使用
        smSendInfo.setSendManId(sendDer.getId());
        smSendInfo.setSendDepId(sendDer.getDepartment().getId());
        smSendInfo.setReceiveManId(receiver.getId());
        smSendInfo.setReceiveMan(receiver.getUserName());
        smSendInfo.setSysType("01");//系统类型：01（没有具体文档说明，暂时写死）
        smSendInfo.setServiceId("01");//EC/SI应用的ID：01 （没有具体文档说明，暂时写死）
        smSendInfo.setTimingSendFlag(smsBaseInfo.getTimingSendFlag() == null ? SmsBaseInfo.IMM_SEND : smsBaseInfo.getTimingSendFlag());
        smSendInfo.setTimingSendTime(smsBaseInfo.getTimingSendTime());
        smSendInfo.setReqDeliveryReport(smsBaseInfo.getReqDeliveryReport() == null ? SmsBaseInfo.REPORT_NOT_NEED : smsBaseInfo.getReqDeliveryReport());
        return smSendInfo;
    }

}