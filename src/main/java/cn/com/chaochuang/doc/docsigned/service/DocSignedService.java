/*
 * FileName:    OaNoticeService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.doc.docsigned.service;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.doc.docsigned.domain.DocSigned;
import cn.com.chaochuang.doc.event.domain.OaDocFile;

/**
 * @author huangwq
 *
 */
public interface DocSignedService extends CrudRestService<DocSigned, Long> {

    DocSigned saveDocSigned(OaDocFile obj);

}
