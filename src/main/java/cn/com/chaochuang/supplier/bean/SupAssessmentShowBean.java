package cn.com.chaochuang.supplier.bean;

import java.util.Date;


import org.dozer.Mapping;

import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.supplier.reference.Evaluation;

public class SupAssessmentShowBean {


    private Long     		id;
    
    @Mapping("unit.unitName")
    private String   		unitName;

    /** 考核项目 */
    private String   		assessEvent;
    
    /** 考核时间 */
    private Date   	 		assessDate;
    private String   		assessDateShow;
    
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
    private String   		createTimeShow;
    
    /** 所属单位ID */
    private Long     		unitId;

	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
        if (this.createTime != null) {
            this.createTimeShow = Tools.DATE_FORMAT.format(this.createTime);
        }
	}

	public Date getAssessDate() {
		return assessDate;
	}

	public void setAssessDate(Date assessDate) {
		this.assessDate = assessDate;
        if (this.assessDate != null) {
            this.assessDateShow = Tools.DATE_FORMAT.format(this.assessDate);
        }
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getAssessEvent() {
		return assessEvent;
	}

	public void setAssessEvent(String assessEvent) {
		this.assessEvent = assessEvent;
	}

	public String getAssessDateShow() {
		return assessDateShow;
	}

	public void setAssessDateShow(String assessDateShow) {
		this.assessDateShow = assessDateShow;
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

	public String getCreateTimeShow() {
		return createTimeShow;
	}

	public void setCreateTimeShow(String createTimeShow) {
		this.createTimeShow = createTimeShow;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

}
