/*
 * FileName:    EmMainService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.mail.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.oa.mail.bean.MailEditBean;
import cn.com.chaochuang.oa.mail.domain.EmMain;

/**
 * @author HM
 *
 */
public interface EmMainService extends CrudRestService<EmMain, String> {

    /**
     * 保存
     *
     * @param bean
     * @return emailId
     */
    public String saveMail(MailEditBean bean);

    /**
     * 邮件
     *
     * @param emailId
     * @return boolean
     */
    public boolean getBack(String emailId);

    /**
     * 转发邮件
     *
     * @param emailId
     * @return mail
     */
    public EmMain forwardingMail(String emailId);

    /**
     * 阅读邮件
     *
     * @param emailId
     */
    public void readMail(String emailId, Long userId);

    /**
     * 回复邮件
     *
     * @param emailId
     * @return
     */
    public EmMain mailReplay(String emailId);

    /**
     * 逻辑删除
     *
     * @param emailId
     * @return
     */
    public boolean delMail(String emailIds);

    /** 根据同步时间获取发件箱数据 */
    List<EmMain> selectMailOutbox(String lastOutputTime);
}
