/*
 * FileName:    RemoteDocSwapContentService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.service;

import java.util.List;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapContent;

/**
 * @author yuandl
 *
 */
public interface RemoteDocSwapContentService extends CrudRestService<RemoteDocSwapContent, Long> {

    /**
     * 根据封首查询封体
     */
    public RemoteDocSwapContent getByEnvelopeId(Long envelopeId);

    /**
     * 根据封首UUID查询封体
     */
    public List<RemoteDocSwapContent> findByEnvelopeUuid(String envelopeUuid);
    
    /** 
     * 复制公文交换附件到系统附件表
     */
    public List<SysAttach> copyDocSwapAttachToSysAttach(Long envelopeId);
}
