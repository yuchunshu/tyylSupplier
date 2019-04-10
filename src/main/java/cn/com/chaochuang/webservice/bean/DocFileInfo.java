package cn.com.chaochuang.webservice.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *
 * @ClassName:ScheduleEditController
 * @author huangwq
 * @date 2015年08月10
 * @Version:CRM v1
 */
public class DocFileInfo {
    /** 公文标题 */
    private String              title;
    /** 文号 */
    private String              docNumber;
    /** 紧急程度 */
    private String              emergencyLevel;
    /** 密级 */
    private String              secretLevel;
    /** 来文单位 */
    private String              sourceDept;
    /** 办理期限 */
    private String              limitDate;
    /** 公文类别 */
    private String              docType;
    /** 原系统公文编号 */
    private String              rmInstanceId;
    /** 流程编号 */
    private String              flowId;
    /** 文电摘要 */
    private String              digest;
    /** 来文日期 显示 */
    private String              receiveTimeShow;

    /** 拟搞人 */
    private String              redactor;
    /** 拟稿时间 */
    private Date                redactDate;
    /** 拟搞人部门名称 */
    private String              redactorDeptName;
    /** 拟办意见 */
    private String              redactOpinion;
    /** 签发人 */
    private String              signMan;
    /** 签发时间 */
    private Date                signDate;
    /** 公开方式 */
    private String              publish;
    /** 创建时间 */
    private Date                createDate;
    /** 数据导入时间 */
    private Date                inputDate;
    /** 创建人id */
    private Long                creatorId;
    /** 创建人姓名 */
    private String              creatorName;
    /** 创建人部门id */
    private Long                creatorDeptId;
    /** 创建人部门名称 */
    private String              creatorDeptName;
    /** 主送单位 */
    private String              mainSend;
    /** 抄送单位 */
    private String              copySend;
    /** 自然编号（自编号） */
    private String              processNumber;
    /** 公文附件集合 */
    // private List<DocFileAttachInfo> remoteDocfileAttach = new ArrayList<DocFileAttachInfo>();
    /** 流程集合 */
    private List                remoteFlowNode    = new ArrayList();

    /** 按钮信息 */
    private String[]            buttonType;
    /** 公文主办处室 (保存到经办列表) */
    private Long                redactDeptId;
    /** 公文所属单位 */
    private Long                unitOrgId;
    private String              shareFlag;

    // 环节人员树形
    private List<DeptNodesInfo> depInfoList;

    /** 移动端待办id */
    private Long                fordoId;

    /**
     * @return the redactorDeptName
     */
    public String getRedactorDeptName() {
        return redactorDeptName;
    }

    /**
     * @param redactorDeptName
     *            the redactorDeptName to set
     */
    public void setRedactorDeptName(String redactorDeptName) {
        this.redactorDeptName = redactorDeptName;
    }

    /**
     * @return the signMan
     */
    public String getSignMan() {
        return signMan;
    }

    /**
     * @param signMan
     *            the signMan to set
     */
    public void setSignMan(String signMan) {
        this.signMan = signMan;
    }

    /**
     * @return the digest
     */
    public String getDigest() {
        return digest;
    }

    /**
     * @param digest
     *            the digest to set
     */
    public void setDigest(String digest) {
        this.digest = digest;
    }

    /**
     * @return the redactor
     */
    public String getRedactor() {
        return redactor;
    }

    /**
     * @param redactor
     *            the redactor to set
     */
    public void setRedactor(String redactor) {
        this.redactor = redactor;
    }

    /**
     * @return the publish
     */
    public String getPublish() {
        return publish;
    }

    /**
     * @param publish
     *            the publish to set
     */
    public void setPublish(String publish) {
        this.publish = publish;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the docNumber
     */
    public String getDocNumber() {
        return docNumber;
    }

    /**
     * @param docNumber
     *            the docNumber to set
     */
    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    /**
     * @return the emergencyLevel
     */
    public String getEmergencyLevel() {
        return emergencyLevel;
    }

    /**
     * @param emergencyLevel
     *            the emergencyLevel to set
     */
    public void setEmergencyLevel(String emergencyLevel) {
        this.emergencyLevel = emergencyLevel;
    }

    /**
     * @return the sourceDept
     */
    public String getSourceDept() {
        return sourceDept;
    }

    /**
     * @param sourceDept
     *            the sourceDept to set
     */
    public void setSourceDept(String sourceDept) {
        this.sourceDept = sourceDept;
    }

    /**
     * @return the limitDate
     */
    public String getLimitDate() {
        return limitDate;
    }

    /**
     * @param limitDate
     *            the limitDate to set
     */
    public void setLimitDate(String limitDate) {
        this.limitDate = limitDate;
    }

    /**
     * @return the docType
     */
    public String getDocType() {
        return docType;
    }

    /**
     * @param docType
     *            the docType to set
     */
    public void setDocType(String docType) {
        this.docType = docType;
    }

    /**
     * @return the flowId
     */
    public String getFlowId() {
        return flowId;
    }

    /**
     * @param flowId
     *            the flowId to set
     */
    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     *            the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the inputDate
     */
    public Date getInputDate() {
        return inputDate;
    }

    /**
     * @param inputDate
     *            the inputDate to set
     */
    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    /**
     * @return the secretLevel
     */
    public String getSecretLevel() {
        return secretLevel;
    }

    /**
     * @param secretLevel
     *            the secretLevel to set
     */
    public void setSecretLevel(String secretLevel) {
        this.secretLevel = secretLevel;
    }

    /**
     * @param rmInstanceId
     *            the rmInstanceId to set
     */
    public void setRmInstanceId(String rmInstanceId) {
        this.rmInstanceId = rmInstanceId;
    }

    /**
     * @return the creatorId
     */
    public Long getCreatorId() {
        return creatorId;
    }

    /**
     * @param creatorId
     *            the creatorId to set
     */
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * @return the creatorName
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * @param creatorName
     *            the creatorName to set
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    /**
     * @return the creatorDeptId
     */
    public Long getCreatorDeptId() {
        return creatorDeptId;
    }

    /**
     * @param creatorDeptId
     *            the creatorDeptId to set
     */
    public void setCreatorDeptId(Long creatorDeptId) {
        this.creatorDeptId = creatorDeptId;
    }

    /**
     * @return the creatorDeptName
     */
    public String getCreatorDeptName() {
        return creatorDeptName;
    }

    /**
     * @param creatorDeptName
     *            the creatorDeptName to set
     */
    public void setCreatorDeptName(String creatorDeptName) {
        this.creatorDeptName = creatorDeptName;
    }

    /**
     * @return the receiveTimeShow
     */
    public String getReceiveTimeShow() {
        return receiveTimeShow;
    }

    /**
     * @param receiveTimeShow
     *            the receiveTimeShow to set
     */
    public void setReceiveTimeShow(String receiveTimeShow) {
        this.receiveTimeShow = receiveTimeShow;
    }

    /**
     * @return the signDate
     */
    public Date getSignDate() {
        return signDate;
    }

    /**
     * @param signDate
     *            the signDate to set
     */
    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    /**
     * @return the redactDate
     */
    public Date getRedactDate() {
        return redactDate;
    }

    /**
     * @param redactDate
     *            the redactDate to set
     */
    public void setRedactDate(Date redactDate) {
        this.redactDate = redactDate;
    }

    /**
     * @return the redactOpinion
     */
    public String getRedactOpinion() {
        return redactOpinion;
    }

    /**
     * @param redactOpinion
     *            the redactOpinion to set
     */
    public void setRedactOpinion(String redactOpinion) {
        this.redactOpinion = redactOpinion;
    }

    /**
     * @return the mainSend
     */
    public String getMainSend() {
        return mainSend;
    }

    /**
     * @param mainSend
     *            the mainSend to set
     */
    public void setMainSend(String mainSend) {
        this.mainSend = mainSend;
    }

    /**
     * @return the copySend
     */
    public String getCopySend() {
        return copySend;
    }

    /**
     * @param copySend
     *            the copySend to set
     */
    public void setCopySend(String copySend) {
        this.copySend = copySend;
    }

    /**
     * @return the processNumber
     */
    public String getProcessNumber() {
        return processNumber;
    }

    /**
     * @param processNumber
     *            the processNumber to set
     */
    public void setProcessNumber(String processNumber) {
        this.processNumber = processNumber;
    }

    /**
     * @return the unitOrgId
     */
    public Long getUnitOrgId() {
        return unitOrgId;
    }

    /**
     * @param unitOrgId
     *            the unitOrgId to set
     */
    public void setUnitOrgId(Long unitOrgId) {
        this.unitOrgId = unitOrgId;
    }

    /**
     * @return the redactDeptId
     */
    public Long getRedactDeptId() {
        return redactDeptId;
    }

    /**
     * @param redactDeptId
     *            the redactDeptId to set
     */
    public void setRedactDeptId(Long redactDeptId) {
        this.redactDeptId = redactDeptId;
    }

    public String getShareFlag() {
        return shareFlag;
    }

    public void setShareFlag(String shareFlag) {
        this.shareFlag = shareFlag;
    }

    /**
     * @return the rmInstanceId
     */
    public String getRmInstanceId() {
        return rmInstanceId;
    }

    /**
     * @return the remoteDocfileAttach
     */
    // public List<DocFileAttachInfo> getRemoteDocfileAttach() {
    // return remoteDocfileAttach;
    // }

    /**
     * @param remoteDocfileAttach
     *            the remoteDocfileAttach to set
     */
    // public void setRemoteDocfileAttach(List<DocFileAttachInfo> remoteDocfileAttach) {
    // this.remoteDocfileAttach = remoteDocfileAttach;
    // }

    /**
     * @return the remoteFlowNode
     */
    public List<FlowNodeBeanInfo> getRemoteFlowNode() {
        return remoteFlowNode;
    }

    /**
     * @param remoteFlowNode
     *            the remoteFlowNode to set
     */
    public void setRemoteFlowNode(List remoteFlowNode) {
        this.remoteFlowNode = remoteFlowNode;
    }

    /**
     * @return the buttonType
     */
    public String[] getButtonType() {
        return buttonType;
    }

    /**
     * @param buttonType
     *            the buttonType to set
     */
    public void setButtonType(String[] buttonType) {
        this.buttonType = buttonType;
    }

    /**
     * @return the depInfoList
     */
    public List<DeptNodesInfo> getDepInfoList() {
        return depInfoList;
    }

    /**
     * @param depInfoList
     *            the depInfoList to set
     */
    public void setDepInfoList(List<DeptNodesInfo> depInfoList) {
        this.depInfoList = depInfoList;
    }

    /**
     * @return the fordoId
     */
    public Long getFordoId() {
        return fordoId;
    }

    /**
     * @param fordoId
     *            the fordoId to set
     */
    public void setFordoId(Long fordoId) {
        this.fordoId = fordoId;
    }

}
