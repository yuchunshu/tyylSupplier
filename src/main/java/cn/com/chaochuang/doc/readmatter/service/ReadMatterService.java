/*
 * FileName:    OaReceiveFileService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.readmatter.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.doc.readmatter.bean.ReadMatterOppBean;
import cn.com.chaochuang.doc.readmatter.domain.ReadMatter;

/**
 * @author huangwq
 *
 */

public interface ReadMatterService extends CrudRestService<ReadMatter, String> {

    List<ReadMatter> findByFileId(String fileId);

    void deleteReadMatterByFileId(String fileId);

    String saveReadMatter(ReadMatterOppBean bean);

    /** 检查是否有阅知 */
    long selectReadMatterCount(String fileId);

}