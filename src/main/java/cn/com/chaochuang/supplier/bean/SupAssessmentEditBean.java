package cn.com.chaochuang.supplier.bean;

import java.util.Date;


import cn.com.chaochuang.supplier.reference.Evaluation;

public class SupAssessmentEditBean {

    // Fields
    private Long     		id;
    
    /** 考核项目 */
    private String   		assessEvent;
    
    /** 考核时间 */
    private Date   	 		assessDate;
    
    /** 项目发生金额 */
    private String   		eventAmount;
    
    /** 外在感受 */
    private Evaluation      feel;
    
    /** 技术实力 */
    private Evaluation      technology;
    
    /** 做工质量 */
    private Evaluation      quality;
    
    /** 服务情况 */
    private Evaluation      service;
    
    /** 评论意见 */
    private String   		remark;
    
    /** 登记日期 */
    private Date   	 		createTime;

    /** 所属单位ID */
    private Long     		unitId;
    
    /** 附件IDs 通过','分割 */
    private String      	attach;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssessEvent() {
		return assessEvent;
	}

	public void setAssessEvent(String assessEvent) {
		this.assessEvent = assessEvent;
	}

	public Date getAssessDate() {
		return assessDate;
	}

	public void setAssessDate(Date assessDate) {
		this.assessDate = assessDate;
	}

	public String getEventAmount() {
		return eventAmount;
	}

	public void setEventAmount(String eventAmount) {
		this.eventAmount = eventAmount;
	}

	public Evaluation getFeel() {
		return feel;
	}

	public void setFeel(Evaluation feel) {
		this.feel = feel;
	}

	public Evaluation getTechnology() {
		return technology;
	}

	public void setTechnology(Evaluation technology) {
		this.technology = technology;
	}

	public Evaluation getQuality() {
		return quality;
	}

	public void setQuality(Evaluation quality) {
		this.quality = quality;
	}

	public Evaluation getService() {
		return service;
	}

	public void setService(Evaluation service) {
		this.service = service;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

}
