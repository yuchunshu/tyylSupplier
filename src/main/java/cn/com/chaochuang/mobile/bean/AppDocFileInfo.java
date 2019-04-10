/**
 *
 */
package cn.com.chaochuang.mobile.bean;

import java.util.Date;
import java.util.List;

import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapType;
import org.dozer.Mapping;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.workflow.def.domain.WfNode;

/**
 * @author hzr 2017.8.11
 *
 */
public class AppDocFileInfo {

    /** 收文或发文 */
    @Mapping("fileType.value")
    private String          fileType;
    /** 文电标题 */
    private String          title;
    /** 公文字号 */
    private String          docCode;
    /** 自然编号，如2017-0006 */
    private String          sncode;
    /** 发文(来文)单位 */
    private String          sendUnit;
    /** 处理时限（截止日期） */
    private Date            expiryDate;
    /** 办理工作日时限 */
    private Integer         limitDay;
    /** 主送 */
    private String          mainSend;
    /** 抄报 */
    private String          reportSend;
    /** 抄送 */
    private String          copySend;
    /** 公开 */
    @Mapping("openFlag.value")
    private String          openFlag;
    /** 公文摘要 */
    private String          digest;
    /** 拟办意见 */
    private String          suggestion;

    private String          remark;
    /** 主题词 */
    private String          keyword;

    /** 校对人 */
    private Long            reviewer;
    /** 一校 */
    private String          reviewer1;
    /** 二校 */
    private String          reviewer2;
    /** 打印人 */
    private Long            printMan;
    /** 打印份数 */
    private Integer         copies;
    /** 拟稿时间 */
    private Date            createDate;
    /** 紧急程度 */
    @Mapping("urgencyLevel.value")
    private String          urgency;
    /** 密级 */
    @Mapping("dense.value")
    private String          dense;
    /** 状态 */
    @Mapping("status.value")
    private String          status;
    /** 归档类型 */
    // @Mapping("archiveFlag.value")
    // private String archiveFlag;

    /** 判断手机端是否显示并选择归档类型 */
    private boolean         showArch;

    /** 拟稿人 */
    private Long            creatorId;
    private String          creatorName;
    /** 部门全称*/
    @Mapping("createrDept.unitDept.deptName")
    private String           fullName;
    /** 文种 */
    private String          docCate;
    /** 联系电话 */
    private String          telephone;
    /** 备用信息 */
    private String          otherInfo;
    /** 打字员 */
    private String          typist;
    /** 收文日期 */
    private Date            receiveDate;
    /** 编号类型 */
    private String          sncodeType;

    /** 版本号（用于多用户保存互斥判断） */
    private Integer         version_;

    /** 附件列表 */
    private List<SysAttach> attachList;
    /** 功能按钮列表 */
    private List<String>    funcList;
    /** 可回退的环节 */
    private List<WfNode>    backNodeList;

    /** 审批意见 */
    private String          opinion;
    /** 签批意见 */
    private String          signContent;
    /** 流程定义 */
    private String          flowId;
    /** 流程实例ID */
    private String          flowInstId;
    /** 当前环节ID */
    private String          curNodeId;
    /** 当前环节实例ID */
    private String          curNodeInstId;
    /** 公文转出标识 */
    private String          outFlag;
    /** 是否可编辑标识 */
    private String          editFlag;
    private String          modelKey;
    @Mapping("documentType.value")
    private String documentType;

    /**
     * 意见列表
     */
    private List<AppDocOpinion> opinionList;


    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getLimitDay() {
        return limitDay;
    }

    public void setLimitDay(Integer limitDay) {
        this.limitDay = limitDay;
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

    public String getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(String openFlag) {
        this.openFlag = openFlag;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getReviewer() {
        return reviewer;
    }

    public void setReviewer(Long reviewer) {
        this.reviewer = reviewer;
    }

    public Long getPrintMan() {
        return printMan;
    }

    public void setPrintMan(Long printMan) {
        this.printMan = printMan;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getDense() {
        return dense;
    }

    public void setDense(String dense) {
        this.dense = dense;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getDocCate() {
        return docCate;
    }

    public void setDocCate(String docCate) {
        this.docCate = docCate;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getTypist() {
        return typist;
    }

    public void setTypist(String typist) {
        this.typist = typist;
    }

    public Integer getVersion_() {
        return version_;
    }

    public void setVersion_(Integer version_) {
        this.version_ = version_;
    }

    public List<SysAttach> getAttachList() {
        return attachList;
    }

    public void setAttachList(List<SysAttach> attachList) {
        this.attachList = attachList;
    }

    public List<String> getFuncList() {
        return funcList;
    }

    public void setFuncList(List<String> funcList) {
        this.funcList = funcList;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getCurNodeId() {
        return curNodeId;
    }

    public void setCurNodeId(String curNodeId) {
        this.curNodeId = curNodeId;
    }

    public List<WfNode> getBackNodeList() {
        return backNodeList;
    }

    public void setBackNodeList(List<WfNode> backNodeList) {
        this.backNodeList = backNodeList;
    }

    public String getCurNodeInstId() {
        return curNodeInstId;
    }

    public void setCurNodeInstId(String curNodeInstId) {
        this.curNodeInstId = curNodeInstId;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getSncodeType() {
        return sncodeType;
    }

    public void setSncodeType(String sncodeType) {
        this.sncodeType = sncodeType;
    }

    public boolean isShowArch() {
        return showArch;
    }

    public void setShowArch(boolean showArch) {
        this.showArch = showArch;
    }

    public String getSignContent() {
        return signContent;
    }

    public void setSignContent(String signContent) {
        this.signContent = signContent;
    }

    public String getOutFlag() {
        return outFlag;
    }

    public void setOutFlag(String outFlag) {
        this.outFlag = outFlag;
    }

    public List<AppDocOpinion> getOpinionList() {
        return opinionList;
    }

    public void setOpinionList(List<AppDocOpinion> opinionList) {
        this.opinionList = opinionList;
    }

    public String getFlowInstId() {
        return flowInstId;
    }

    public void setFlowInstId(String flowInstId) {
        this.flowInstId = flowInstId;
    }

    public String getReviewer1() {
        return reviewer1;
    }

    public void setReviewer1(String reviewer1) {
        this.reviewer1 = reviewer1;
    }

    public String getReviewer2() {
        return reviewer2;
    }

    public void setReviewer2(String reviewer2) {
        this.reviewer2 = reviewer2;
    }

    public String getEditFlag() {
        return editFlag;
    }

    public void setEditFlag(String editFlag) {
        this.editFlag = editFlag;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getModelKey() {
        return modelKey;
    }

    public void setModelKey(String modelKey) {
        this.modelKey = modelKey;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
}
