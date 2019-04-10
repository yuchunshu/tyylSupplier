package cn.com.chaochuang.pubaffairs.car.bean;

import java.util.Date;

import cn.com.chaochuang.common.user.domain.SysUser;

/**
 * @author dengl 2018.08.08
 *
 */
public class OfficialCarCommonBean {
	
	/** 注入当前登录用户（移动端无直接取得，只能传参写入）*/
	private SysUser         	currentUser;
	
	/** 当前办理环节 */
	private String 				curNodeId;
	private String 				curNodeInstId;

	/** 办理意见 */
	private String 				opinion;

	/** 意见日期 */
	private Date 				opinionDate;

	/** 办理结果（是否通过） */
	private String 				auditPass;

	/** 流程启动判断标记 */
	private boolean 			startFlag;

	public String getCurNodeId() {
		return curNodeId;
	}

	public void setCurNodeId(String curNodeId) {
		this.curNodeId = curNodeId;
	}

	public String getCurNodeInstId() {
		return curNodeInstId;
	}

	public void setCurNodeInstId(String curNodeInstId) {
		this.curNodeInstId = curNodeInstId;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public Date getOpinionDate() {
		return opinionDate;
	}

	public void setOpinionDate(Date opinionDate) {
		this.opinionDate = opinionDate;
	}

	public String getAuditPass() {
		return auditPass;
	}

	public void setAuditPass(String auditPass) {
		this.auditPass = auditPass;
	}

	public boolean isStartFlag() {
		return startFlag;
	}

	public void setStartFlag(boolean startFlag) {
		this.startFlag = startFlag;
	}

	public SysUser getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(SysUser currentUser) {
		this.currentUser = currentUser;
	}
}
