/*
 * FileName:    EmAttachService.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月26日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.mail.service;

import java.util.List;
import java.util.Map;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.oa.mail.domain.EmAttach;

/**
 * @author HM
 *
 */
public interface EmAttachService extends CrudRestService<EmAttach, String> {

    Map<String, List<EmAttach>> getAttachMap(String emailId);

    boolean deleteAttach(String attachId);

    void linkAttachAndEmail(String attachId, String emailId);

    List<EmAttach> findByEmailId(String emailId);

    /**
     * 转发邮件复制附件
     *
     * @param emailId
     * @return
     */
    Map<String, List<EmAttach>> copyAttachMap(String emailId);
}
