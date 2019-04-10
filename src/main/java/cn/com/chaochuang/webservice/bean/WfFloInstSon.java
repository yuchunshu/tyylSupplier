package cn.com.chaochuang.webservice.bean;

import java.util.Date;

public class WfFloInstSon {
    /** 收文暂存 */
    public static final String STATUS_FLAG_0 = "0";
    /** 收文在办 */
    public static final String STATUS_FLAG_1 = "1";
    /** 收文待归档 */
    public static final String STATUS_FLAG_2 = "2";
    /** 收文归档 */
    public static final String STATUS_FLAG_3 = "3";
    /** 收文逻辑删除 */
    public static final String STATUS_FLAG_4 = "4";
    /** 收文退回 */
    public static final String STATUS_FLAG_5 = "5";
    /** 收文取回 */
    public static final String STATUS_FLAG_6 = "6";
    /** 附件无 */
    public static final String ATTACHFLAG_0  = "0";
    /** 附件有 */
    public static final String ATTACHFLAG_1  = "1";

    public final static String FLAG_STATUS_0 = "0"; // 暂存
    public final static String FLAG_STATUS_1 = "1"; // 在办
    public final static String FLAG_STATUS_2 = "2"; // 办结
    public final static String FLAG_STATUS_3 = "3"; // 归档
    public final static String FLAG_STATUS_4 = "4"; // 逻辑删除
    public final static String FLAG_STATUS_5 = "5"; // 退回

    /** 收文ID */
    private Long               fileId;

    private Long               instId;
    /** 文电标题 */
    private String             title;
    /** 来文字号规则：W－年－序号；年即当前年度，为四位，如2008，序号四位，从0001～9999，依次递增 */
    private String             docCode;
    /** 编 号:1-9+流水号 */
    private String             sncode;
    /** 来文单位 */
    private String             sendUnit;
    /** 处理时间 */
    private String             expiryDate;
    /** 状态 */
    private String             status;
    /** 收文日期 */
    private Date               receiveDate;
    /** 拟稿时间 */
    private Date               createDate;
    /** 紧急程度 */
    private String             urgencyLevel;
    /** 拟稿人 user_id */
    private Integer            creater;
    /** 0 无 1 有附件 */
    private String             attachFlag;

    private String             createrName;

    /** 拟办意见 */
    private String             opinion;
    /** 附件 */
    private Long[]             attachId;

    private String             nextDealer;

    private String             nextDeputyDealer;

    private String             waitOther;
    private String             endCond;
    private String             SpecialDelivery;

    private String             remark;
    /** 文种编号 */
    private String             docCate;

    private String             flowType;

    /** 拟稿人部门编号 */
    private Integer            createrDepId;
    /** 拟稿人部门名称 */
    private String             createrDepName;

    /** 创建单位 */
    private Integer            createrOrgDepId;

    /** 公文类别 */
    private String             docType;

    private String             mainSend;
    private String             reportSend;
    private String             copySend;
    private String             dense;
    private String             openFlag;
    private Integer            reviewer;
    private Integer            printMan;
    private Long               docId;

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    public String getSncode() {
        return sncode;
    }

    public void setSncode(String sncode) {
        this.sncode = sncode;
    }

    public String getSendUnit() {
        return sendUnit;
    }

    public void setSendUnit(String sendUnit) {
        this.sendUnit = sendUnit;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(String urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public Integer getCreater() {
        return creater;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    public String getAttachFlag() {
        return attachFlag;
    }

    public void setAttachFlag(String attachFlag) {
        this.attachFlag = attachFlag;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Long[] getAttachId() {
        return attachId;
    }

    public void setAttachId(Long[] attachId) {
        this.attachId = attachId;
    }

    public String getNextDealer() {
        return nextDealer;
    }

    public void setNextDealer(String nextDealer) {
        this.nextDealer = nextDealer;
    }

    public String getNextDeputyDealer() {
        return nextDeputyDealer;
    }

    public void setNextDeputyDealer(String nextDeputyDealer) {
        this.nextDeputyDealer = nextDeputyDealer;
    }

    public String getWaitOther() {
        return waitOther;
    }

    public void setWaitOther(String waitOther) {
        this.waitOther = waitOther;
    }


    public String getEndCond() {
		return endCond;
	}

	public void setEndCond(String endCond) {
		this.endCond = endCond;
	}

	public String getSpecialDelivery() {
        return SpecialDelivery;
    }

    public void setSpecialDelivery(String specialDelivery) {
        SpecialDelivery = specialDelivery;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDocCate() {
        return docCate;
    }

    public void setDocCate(String docCate) {
        this.docCate = docCate;
    }

    public String getMainSend() {
        return mainSend;
    }

    public void setMainSend(String mainSend) {
        this.mainSend = mainSend;
    }

    public String getReportSend() {
        return reportSend;
    }

    public void setReportSend(String reportSend) {
        this.reportSend = reportSend;
    }

    public String getCopySend() {
        return copySend;
    }

    public void setCopySend(String copySend) {
        this.copySend = copySend;
    }

    public String getDense() {
        return dense;
    }

    public void setDense(String dense) {
        this.dense = dense;
    }

    public String getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(String openFlag) {
        this.openFlag = openFlag;
    }

    public Integer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Integer reviewer) {
        this.reviewer = reviewer;
    }

    public Integer getPrintMan() {
        return printMan;
    }

    public void setPrintMan(Integer printMan) {
        this.printMan = printMan;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public static String getStatusFlag0() {
        return STATUS_FLAG_0;
    }

    public static String getStatusFlag1() {
        return STATUS_FLAG_1;
    }

    public static String getStatusFlag2() {
        return STATUS_FLAG_2;
    }

    public static String getStatusFlag3() {
        return STATUS_FLAG_3;
    }

    public static String getStatusFlag4() {
        return STATUS_FLAG_4;
    }

    public static String getStatusFlag5() {
        return STATUS_FLAG_5;
    }

    public static String getStatusFlag6() {
        return STATUS_FLAG_6;
    }

    public static String getAttachflag0() {
        return ATTACHFLAG_0;
    }

    public static String getAttachflag1() {
        return ATTACHFLAG_1;
    }

    public static String getFlagStatus0() {
        return FLAG_STATUS_0;
    }

    public static String getFlagStatus1() {
        return FLAG_STATUS_1;
    }

    public static String getFlagStatus2() {
        return FLAG_STATUS_2;
    }

    public static String getFlagStatus3() {
        return FLAG_STATUS_3;
    }

    public static String getFlagStatus4() {
        return FLAG_STATUS_4;
    }

    public static String getFlagStatus5() {
        return FLAG_STATUS_5;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public Long getInstId() {
        return instId;
    }

    public void setInstId(Long instId) {
        this.instId = instId;
    }

    public Integer getCreaterDepId() {
        return createrDepId;
    }

    public void setCreaterDepId(Integer createrDepId) {
        this.createrDepId = createrDepId;
    }

    public String getCreaterDepName() {
        return createrDepName;
    }

    public void setCreaterDepName(String createrDepName) {
        this.createrDepName = createrDepName;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public Integer getCreaterOrgDepId() {
        return createrOrgDepId;
    }

    public void setCreaterOrgDepId(Integer createrOrgDepId) {
        this.createrOrgDepId = createrOrgDepId;
    }

}
