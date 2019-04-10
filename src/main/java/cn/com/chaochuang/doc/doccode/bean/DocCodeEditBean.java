package cn.com.chaochuang.doc.doccode.bean;

import cn.com.chaochuang.doc.event.reference.DocCate;
import cn.com.chaochuang.workflow.reference.WfBusinessType;

public class DocCodeEditBean {
	private Long        id;

	/** 字号名称 */
	private String      codeName;

	/** 字号类型 */
	private WfBusinessType codeType;

	/** 备注 */
	private String      remark;

	/** 排序 */
	private Integer     codeSort;

	/** 文种 */
	private DocCate     docCate;

	/** 模板id */
	private String      templateId;
    
	/** 模板名称 */
	private String      templateName;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

}
