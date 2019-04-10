/*
 * FileName:    CongressDocs.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年4月15日 (WTL) 1.0 Create
 */

package cn.com.chaochuang.oa.docs.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.oa.docs.bean.DocsEditBean;
import cn.com.chaochuang.oa.docs.domain.OaDocs;

/**
 * @author WTL
 *
 */
public interface DocsService extends CrudRestService<OaDocs, Long> {

    public Long saveCongressDocs(DocsEditBean bean);

    public void deleteCongressDocs(Long[] ids, HttpServletRequest request);

    public void saveCongressDocs(String keyword, Long[] docsIds, Long folderId);

    public List<OaDocs> findByFolderId(Long folderId);

    public void exchange(Long[] ids, Long folderId);
}
