/*
 * FileName:    MessageInfoBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年5月10日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.message.app.bean;

/**
 * @author HM
 *
 */
public class MessageInfoBean {

    private Long    id;

    /** 待办路径 */
    private String  pendingFilePath;

    /** 待办数量 */
    private Integer pendingFileSum;
    
    /** 内部请示路径 */
    private String  inteRequestFilePath;

    /** 内部请示数量 */
    private Integer inteRequestFileSum;

    /** 待阅路径 */
    private String  toReadMatterPath;

    /** 待阅数量 */
    private Integer toReadMatterSum;

    /** 邮件路径 */
    private String  mailPath;

    /** 邮件数量 */
    private Integer mailSum;

    /** 会议路径 */
    private String  meetingPath;

    /** 会议数量 */
    private Integer meetingSum;

    /** 日程路径 */
    private String  schedulePath;

    /** 日程数量 */
    private Integer scheduleSum;

    /** 即时消息数量 */
    private Integer imMessageSum;

    /** 其余业务模块消息(信访、监督等) */
    private Integer otherSum;

    /** 未读消息 */
    private Integer notReadSum;
    


    /** 车辆申请路径 */
    private String  carPath;

    /** 车辆申请数量 */
    private Integer carSum;
    
    /** 物品请购路径 */
    private String  repairPath;

    /** 物品请购数量 */
    private Integer repairSum;
    
    /** 休假申请路径 */
    private String  leavePath;

    /** 休假申请数量 */
    private Integer leaveSum;
    
    /** 出差申请路径 */
    private String  mealPath;

    /** 出差申请数量 */
    private Integer mealSum;

    public String getCarPath() {
		return carPath;
	}

	public void setCarPath(String carPath) {
		this.carPath = carPath;
	}

	public Integer getCarSum() {
		return carSum;
	}

	public void setCarSum(Integer carSum) {
		this.carSum = carSum;
	}

	public String getRepairPath() {
		return repairPath;
	}

	public void setRepairPath(String repairPath) {
		this.repairPath = repairPath;
	}

	public Integer getRepairSum() {
		return repairSum;
	}

	public void setRepairSum(Integer repairSum) {
		this.repairSum = repairSum;
	}

	public String getLeavePath() {
		return leavePath;
	}

	public void setLeavePath(String leavePath) {
		this.leavePath = leavePath;
	}

	public Integer getLeaveSum() {
		return leaveSum;
	}

	public void setLeaveSum(Integer leaveSum) {
		this.leaveSum = leaveSum;
	}

	public String getMealPath() {
		return mealPath;
	}

	public void setMealPath(String mealPath) {
		this.mealPath = mealPath;
	}

	public Integer getMealSum() {
		return mealSum;
	}

	public void setMealSum(Integer mealSum) {
		this.mealSum = mealSum;
	}

	/**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the pendingFilePath
     */
    public String getPendingFilePath() {
        return pendingFilePath;
    }

    /**
     * @param pendingFilePath
     *            the pendingFilePath to set
     */
    public void setPendingFilePath(String pendingFilePath) {
        this.pendingFilePath = pendingFilePath;
    }

    /**
     * @return the pendingFileSum
     */
    public Integer getPendingFileSum() {
        return pendingFileSum;
    }

    /**
     * @param pendingFileSum
     *            the pendingFileSum to set
     */
    public void setPendingFileSum(Integer pendingFileSum) {
        this.pendingFileSum = pendingFileSum;
    }

    /**
     * @return the toReadMatterPath
     */
    public String getToReadMatterPath() {
        return toReadMatterPath;
    }

    /**
     * @param toReadMatterPath
     *            the toReadMatterPath to set
     */
    public void setToReadMatterPath(String toReadMatterPath) {
        this.toReadMatterPath = toReadMatterPath;
    }

    /**
     * @return the toReadMatterSum
     */
    public Integer getToReadMatterSum() {
        return toReadMatterSum;
    }

    /**
     * @param toReadMatterSum
     *            the toReadMatterSum to set
     */
    public void setToReadMatterSum(Integer toReadMatterSum) {
        this.toReadMatterSum = toReadMatterSum;
    }

    /**
     * @return the mailPath
     */
    public String getMailPath() {
        return mailPath;
    }

    /**
     * @param mailPath
     *            the mailPath to set
     */
    public void setMailPath(String mailPath) {
        this.mailPath = mailPath;
    }

    /**
     * @return the mailSum
     */
    public Integer getMailSum() {
        return mailSum;
    }

    /**
     * @param mailSum
     *            the mailSum to set
     */
    public void setMailSum(Integer mailSum) {
        this.mailSum = mailSum;
    }

    /**
     * @return the meetingPath
     */
    public String getMeetingPath() {
        return meetingPath;
    }

    /**
     * @param meetingPath
     *            the meetingPath to set
     */
    public void setMeetingPath(String meetingPath) {
        this.meetingPath = meetingPath;
    }

    /**
     * @return the meetingSum
     */
    public Integer getMeetingSum() {
        return meetingSum;
    }

    /**
     * @param meetingSum
     *            the meetingSum to set
     */
    public void setMeetingSum(Integer meetingSum) {
        this.meetingSum = meetingSum;
    }

    /**
     * @return the schedulePath
     */
    public String getSchedulePath() {
        return schedulePath;
    }

    /**
     * @param schedulePath
     *            the schedulePath to set
     */
    public void setSchedulePath(String schedulePath) {
        this.schedulePath = schedulePath;
    }

    /**
     * @return the scheduleSum
     */
    public Integer getScheduleSum() {
        return scheduleSum;
    }

    /**
     * @param scheduleSum
     *            the scheduleSum to set
     */
    public void setScheduleSum(Integer scheduleSum) {
        this.scheduleSum = scheduleSum;
    }

    /**
     * @return the notReadSum
     */
    public Integer getNotReadSum() {
        return notReadSum;
    }

    /**
     * @param notReadSum
     *            the notReadSum to set
     */
    public void setNotReadSum(Integer notReadSum) {
        this.notReadSum = notReadSum;
    }

    /**
     * @return the imMessageSum
     */
    public Integer getImMessageSum() {
        return imMessageSum;
    }

    /**
     * @param imMessageSum the imMessageSum to set
     */
    public void setImMessageSum(Integer imMessageSum) {
        this.imMessageSum = imMessageSum;
    }

    /**
     * @return the otherSum
     */
    public Integer getOtherSum() {
        return otherSum;
    }

    /**
     * @param otherSum the otherSum to set
     */
    public void setOtherSum(Integer otherSum) {
        this.otherSum = otherSum;
    }

	public String getInteRequestFilePath() {
		return inteRequestFilePath;
	}

	public void setInteRequestFilePath(String inteRequestFilePath) {
		this.inteRequestFilePath = inteRequestFilePath;
	}

	public Integer getInteRequestFileSum() {
		return inteRequestFileSum;
	}

	public void setInteRequestFileSum(Integer inteRequestFileSum) {
		this.inteRequestFileSum = inteRequestFileSum;
	}
}
