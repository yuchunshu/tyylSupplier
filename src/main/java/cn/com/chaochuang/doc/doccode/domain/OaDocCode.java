package cn.com.chaochuang.doc.doccode.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.doc.event.reference.DocCate;
import cn.com.chaochuang.doc.event.reference.DocCateConverter;
import cn.com.chaochuang.doc.template.domain.DocTemplate;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfBusinessTypeConverter;

/**
 * 公文字号
 *
 * @author yuandl 2016-12-04
 *
 */
@Entity
@Table(name = "oa_doc_code")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "code_id")) })
public class OaDocCode extends LongIdEntity {

    /**  */
    private static final long serialVersionUID = -4722111051287584748L;

    /** 字号名称 */
    private String            codeName;

    /** 字号分类（收文、发文、便函等） */
    @Convert(converter = WfBusinessTypeConverter.class)
    private WfBusinessType    codeType;

    /** 文种 */
    @Convert(converter = DocCateConverter.class)
    private DocCate           docCate;

    /** 备注 */
    private String            remark;

    /** 排序 */
    private Integer           codeSort;

    /** 模板信息 */
    @OneToOne
    @JoinColumn(name = "templateId",updatable = false, insertable = false)
    private DocTemplate       docTemplate;
    private Long 			  templateId;
    
    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public WfBusinessType getCodeType() {
        return codeType;
    }

    public void setCodeType(WfBusinessType codeType) {
        this.codeType = codeType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCodeSort() {
        return codeSort;
    }

    public void setCodeSort(Integer codeSort) {
        this.codeSort = codeSort;
    }

	public DocCate getDocCate() {
		return docCate;
	}

	public void setDocCate(DocCate docCate) {
		this.docCate = docCate;
	}

	public DocTemplate getDocTemplate() {
		return docTemplate;
	}

	public void setDocTemplate(DocTemplate docTemplate) {
		this.docTemplate = docTemplate;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

}
