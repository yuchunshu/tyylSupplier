package cn.com.chaochuang.supplier.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.supplier.reference.Evaluation;
import cn.com.chaochuang.supplier.reference.EvaluationConverter;

@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "assess_id")) })
public class SupAssessment extends LongIdEntity {

    private static final long serialVersionUID = -8749450542121773678L;

    /** 考核项目 */
    private String   		assessEvent;
    
    /** 考核时间 */
    private Date   	 		assessDate;
    
    /** 项目发生金额 */
    private String   		eventAmount;
    
    /** 外在感受 */
    @Convert(converter = EvaluationConverter.class)
    private Evaluation      feel;
    
    /** 技术实力 */
    @Convert(converter = EvaluationConverter.class)
    private Evaluation      technology;
    
    /** 做工质量 */
    @Convert(converter = EvaluationConverter.class)
    private Evaluation      quality;
    
    /** 服务情况 */
    @Convert(converter = EvaluationConverter.class)
    private Evaluation      service;
    
    /** 评论意见 */
    private String   		remark;
    
    /** 登记日期 */
    private Date   	 		createTime;
    
    /** 所属单位ID */
    private Long     		unitId;
    
    /** 所属单位 */
    @ManyToOne
    @JoinColumn(name = "unitId", insertable = false, updatable = false)
    private SupUnit  		unit;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public SupUnit getUnit() {
		return unit;
	}

	public void setUnit(SupUnit unit) {
		this.unit = unit;
	}

    
}
