package cn.com.chaochuang.crm.project.bean;

import java.util.Date;

import javax.persistence.Convert;


import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.crm.project.reference.ProjectCategory;
import cn.com.chaochuang.crm.project.reference.ProjectCategoryConverter;
import cn.com.chaochuang.crm.project.reference.ProjectStatus;
import cn.com.chaochuang.crm.project.reference.ProjectStatusConverter;

public class ProjectShowBean {


    private Long     		id;
    
    /** 项目名称	 */
    private String   			projectName;

    /** 项目类别	 */
    @Convert(converter = ProjectCategoryConverter.class)
    private ProjectCategory   	projectCategory;

    /** 项目具体内容	 */
    private String   			content;

    /** 项目状态	 */
    @Convert(converter = ProjectStatusConverter.class)
    private ProjectStatus   	status;	

//    /** 项目进度	 */
//    private String   			progress;	

    /** 合同签订时间 */
    private Date   				contractTime;

    /** 项目金额	 */
    private String   			amount;	

    /** 项目回款	 */
    private String   			payment;

    /** 项目欠款	 */
    private String   			arrears;

    /** 业务主办	 */
    private String   			businessHosting;	

    /** 归属子公司	 */
    private String   			subsidiary;

    /** 项目负责人 */
    private String   			manager;
    
    /** 备注 */
    private String   			remark;
    
    /** 录入日期 */
    private Date   	 			createTime;
    private String   			createTimeShow;
    
    /** 修改日期 */
    private Date   	 			updateTime;
    private String   			updateTimeShow;
    
    /** 创建人员*/
    private String   			creatorName;
    
    /** 更新人员*/
    private String   			updateName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public ProjectCategory getProjectCategory() {
		return projectCategory;
	}

	public void setProjectCategory(ProjectCategory projectCategory) {
		this.projectCategory = projectCategory;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ProjectStatus getStatus() {
		return status;
	}

	public void setStatus(ProjectStatus status) {
		this.status = status;
	}

	public Date getContractTime() {
		return contractTime;
	}

	public void setContractTime(Date contractTime) {
		this.contractTime = contractTime;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getArrears() {
		return arrears;
	}

	public void setArrears(String arrears) {
		this.arrears = arrears;
	}

	public String getBusinessHosting() {
		return businessHosting;
	}

	public void setBusinessHosting(String businessHosting) {
		this.businessHosting = businessHosting;
	}

	public String getSubsidiary() {
		return subsidiary;
	}

	public void setSubsidiary(String subsidiary) {
		this.subsidiary = subsidiary;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
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
        if (this.createTime != null) {
            this.createTimeShow = Tools.DATE_FORMAT.format(this.createTime);
        }
	}

	public String getCreateTimeShow() {
		return createTimeShow;
	}

	public void setCreateTimeShow(String createTimeShow) {
		this.createTimeShow = createTimeShow;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
        if (this.updateTime != null) {
            this.updateTimeShow = Tools.DATE_FORMAT.format(this.updateTime);
        }
	}

	public String getUpdateTimeShow() {
		return updateTimeShow;
	}

	public void setUpdateTimeShow(String updateTimeShow) {
		this.updateTimeShow = updateTimeShow;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
    
}
