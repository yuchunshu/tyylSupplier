/*
 * FileName:    EmIncepterService.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.mail.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.oa.mail.domain.EmIncepter;

/**
 * @author HM
 *
 */
public interface EmIncepterService extends CrudRestService<EmIncepter, String> {

    /**
     * 按邮件ID和收件人ID查询
     *
     * @param emailId
     * @param incepterId
     * @return
     */
    public EmIncepter findByEmailIdAndIncepterId(String emailId, Long incepterId);

    /**
     * 按邮件ID查询所有收件人
     *
     * @param emailId
     * @return
     */
    public List<EmIncepter> findByEmailId(String emailId);

    /** 根据同步时间获取收件箱数据 */
    List<EmIncepter> selectMailInbox(String lastOutputTime);

}
