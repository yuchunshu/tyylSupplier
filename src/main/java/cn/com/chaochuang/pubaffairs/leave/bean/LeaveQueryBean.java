package cn.com.chaochuang.pubaffairs.leave.bean;

import java.util.Date;

import cn.com.chaochuang.pubaffairs.leave.reference.LeaveType;

/**
 * @author dengl 2018.08.08
 *
 */

public class LeaveQueryBean {
	
	private LeaveType leaveType;

    private Long      applyId;
    private String    applyName;

    private Long      auditId;

    private Long      deptId;
    private String    deptName;

    /** 申请日期开始 */
    private Date      applyTimeBegin;

    /** 申请日期结束 */
    private Date      applyTimeEnd;

    /** 未审批 */
    private String    notAudit;

	public LeaveType getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}

	public Long getApplyId() {
		return applyId;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	public Long getAuditId() {
		return auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Date getApplyTimeBegin() {
		return applyTimeBegin;
	}

	public void setApplyTimeBegin(Date applyTimeBegin) {
		this.applyTimeBegin = applyTimeBegin;
	}

	public Date getApplyTimeEnd() {
		return applyTimeEnd;
	}

	public void setApplyTimeEnd(Date applyTimeEnd) {
		this.applyTimeEnd = applyTimeEnd;
	}

	public String getNotAudit() {
		return notAudit;
	}

	public void setNotAudit(String notAudit) {
		this.notAudit = notAudit;
	}
}
