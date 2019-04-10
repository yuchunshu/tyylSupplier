/**
 *
 */
package cn.com.chaochuang.doc.countersign.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.doc.countersign.domain.DocCountersign;
import cn.com.chaochuang.doc.event.bean.FileShowBean;

/**
 * @author hzr 2016年5月16日
 *
 */
public interface CountersignService extends CrudRestService<DocCountersign, Long> {

    Long saveDocCountersgin(String userIds, String type, FileShowBean bean);

    List<DocCountersign> selectDocCountersignByFileId(String fileId);
    
    void deleteDocCountersignByFileId(String fileId);

}
