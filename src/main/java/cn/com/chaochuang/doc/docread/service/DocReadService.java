/*
 * FileName:    OaReceiveFileService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.docread.service;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.doc.docread.bean.DocReadEditBean;
import cn.com.chaochuang.doc.docread.bean.ReceivesReadEditBean;
import cn.com.chaochuang.doc.docread.domain.DocRead;

/**
 * @author HeYunTao
 *
 */

public interface DocReadService extends CrudRestService<DocRead, Long> {

    Long saveDocread(DocReadEditBean bean);

    Long saveDocreadReceives(ReceivesReadEditBean bean);

    // 签收
    void readDocRead(Long id);

    // 获取接收人名字
    String getReadManNames(Long docreadId);

}