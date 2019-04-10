package cn.com.chaochuang.doc.archive.service;

import javax.servlet.http.HttpServletRequest;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.doc.archive.bean.DocArchivesBasicEditBean;
import cn.com.chaochuang.doc.archive.domain.DocArchivesBasic;

/**
 * @author shiql 2017.11.22
 *
 */
public interface DocArchivesBasicService extends CrudRestService<DocArchivesBasic, String> {

    /** 保存 */
    public DocArchivesBasic saveDocArchives(DocArchivesBasicEditBean bean);

    /** 删除 */
    public boolean deleteDocArchivesBasic(String basicId, HttpServletRequest request);

    /** 检查案卷号 */
    public boolean checkBasicCode(String basicCode, String basicId);


}
