/*
 * FileName:    processService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年4月27日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.template.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.doc.template.bean.DocTemplateEditBean;
import cn.com.chaochuang.doc.template.domain.DocTemplate;

/**
 * @author huangwq
 *
 */
public interface DocTemplateService extends CrudRestService<DocTemplate, Long> {

    public Long saveDocTemplate(DocTemplateEditBean bean);

    public boolean delDocTemplate(Long id);

    public List<DocTemplate> findByDeptId(Long depId);

    boolean deleteAttach(Long attachId);

}
