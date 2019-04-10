/*
 * FileName:    OaDocArchives.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.template.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.LongIdEntity;

/**
 * @author huangwq
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "template_id")) })
@Table(name = "oa_doc_template")
public class DocTemplate extends LongIdEntity {

    private String templateName;
    private String templateFilePath;
    private String remark;
    private Long   deptId;

    /**
     * @return the templateName
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * @param templateName
     *            the templateName to set
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * @return the templateFilePath
     */
    public String getTemplateFilePath() {
        return templateFilePath;
    }

    /**
     * @param templateFilePath
     *            the templateFilePath to set
     */
    public void setTemplateFilePath(String templateFilePath) {
        this.templateFilePath = templateFilePath;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     *            the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the deptId
     */
    public Long getDeptId() {
        return deptId;
    }

    /**
     * @param deptId
     *            the deptId to set
     */
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

}
