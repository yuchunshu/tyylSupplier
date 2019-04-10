package cn.com.chaochuang.doc.log.service;


import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.doc.log.bean.DocModifyLogEditBean;
import cn.com.chaochuang.doc.log.domain.DocModifyLog;

public interface DocModifyLogService extends CrudRestService<DocModifyLog, String> {

    /**
     * 保存公文编辑记录
     *
     * @param instId
     */
    String saveDocModifyLog(DocModifyLogEditBean bean);
}
