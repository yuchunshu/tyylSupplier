package cn.com.chaochuang.doc.event.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;
import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.common.reference.AttachFlagConverter;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.event.reference.DenseType;
import cn.com.chaochuang.doc.event.reference.DenseTypeConverter;
import cn.com.chaochuang.doc.event.reference.DocCate;
import cn.com.chaochuang.doc.event.reference.DocCateConverter;
import cn.com.chaochuang.doc.event.reference.DocSource;
import cn.com.chaochuang.doc.event.reference.DocSourceConverter;
import cn.com.chaochuang.doc.event.reference.OpenFlag;
import cn.com.chaochuang.doc.event.reference.OpenFlagConverter;
import cn.com.chaochuang.doc.event.reference.OuterDocStatus;
import cn.com.chaochuang.doc.event.reference.OuterDocStatusConverter;
import cn.com.chaochuang.doc.event.reference.UrgencyLevelType;
import cn.com.chaochuang.doc.event.reference.UrgencyLevelTypeConverter;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapTypeConverter;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfBusinessTypeConverter;

/**
 * @author yuchunshu 2017.11.10
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "outer_id")) })
@Table(name = "oa_outer_doc_file")
public class OaOuterDocFile extends StringUuidEntity {

    private static final long  serialVersionUID      = 1153312316351132725L;

    /** 关联转内部公文后的ID */
    private String             insideId;
    
    /** 公文ID 关联转办公文ID*/
    private String             fileId;
    
    /** 环节实例ID */
    private String             nodeInstId;

    /** 收文或发文 */
    @Convert(converter = WfBusinessTypeConverter.class)
    private WfBusinessType     fileType;

    /** 收文日期 */
    private Date               receiveDate;
    @Transient
    private String             receiveDateShow;
    /** 拟稿时间 */
    private Date               createDate;
    @Transient
    private String             createDateShow;
    
    /** 拟稿人 user_id */
    private Long               creatorId;
    private String             creatorName;
    
    /** 拟稿人部门编号 */
    private String      	   creatorDepId;
    
    /** 文电标题 */
    private String             title;
    
    /** 文种 */
    @Convert(converter = DocCateConverter.class)
    private DocCate            docCate;
    
    /** 收文类型 */
    @Convert(converter = DocSourceConverter.class)
    private DocSource          sourceType;
    
    /** 来文字号规则：W－年－序号；年即当前年度，为四位，如2008，序号四位，从0001～9999，依次递增 */
    private String             docCode;
    
    /** 编 号，自然编号，如zh2017-0006 */
    private String             sncode;
    
    /** 主送 */
    private String             mainSend;
    
    /** 抄报 */
    private String             reportSend;
    
    /** 抄送 */
    private String             copySend;
    
    /** 处理时限（截止日期） */
    private Date               expiryDate;
    @Transient
    private String             expiryDateShow;
    
    /** 密级 */
    @Convert(converter = DenseTypeConverter.class)
    private DenseType          dense;
    
    /** 公开 */
    @Convert(converter = OpenFlagConverter.class)
    private OpenFlag           openFlag;

    /** 紧急程度 */
    @Convert(converter = UrgencyLevelTypeConverter.class)
    private UrgencyLevelType   urgencyLevel;
    
    /** 正文附件编号 */
    private String             docId;
    
    /** 发文(来文)单位 */
    private String             sendUnit;
    
    /** 0 无 1 有附件 */
    @Convert(converter = AttachFlagConverter.class)
    private AttachFlag         attachFlag;
    
    /** 备注 */
    private String             remark;
    
    /** 拟稿信息 */
    private String             createInfo;
    
    /** 上报签发人 */
    private String             typist;
    
    /** 办理工作日时限 */
    private Integer            limitDay;
    
    /** 打印份数 */
    private Integer            copies;
    
    /** 年份 */
    private String             fileYear;
    
    /** 公文摘要 */
    private String             digest;
    
    /** 编号类型 */
    private String             sncodeType;
    
    /** 流程定义ID */
    private String             flowId;
    
    /** 流程实例ID */
    private String             flowInstId;
    
    /** 主题词 */
    private String             keyword;
    
    /** 发文时限要求（文字说明）*/
    private String             limitStr;
    
    /** 联系电话 */
    private String             telephone;
    
    /** 处理意见 */
    private String             suggestion;
    
    /** 截止日期类型 */
    private String       	   deadlineType;
    
    /** 公文类型（办件：0 阅件：1） */
    @Convert(converter = RemoteDocSwapTypeConverter.class)
    private RemoteDocSwapType  documentType;
    
    /** 发送人ID */
    private Long               senderId;
    
    /** 发送人名称 */
    private String       	   senderName;
    
    /** 发送人部门ID */
    private Long               senderDeptId;
    
    /** 发送人部门名称 */
    private String       	   senderDeptName;
    
    /** 发送时间 */
    private Date               sendTime;
    @Transient
    private String             sendTimeShow;
    
    /** 接收部门ID */
    private Long               receiveDeptId;
    
    /** 接收部门名称 */
    private String       	   receiveDeptName;
    
    /** 状态 */
    @Convert(converter = OuterDocStatusConverter.class)
    private OuterDocStatus     status;
    
    /** 办结时间 */
    private Date               finishTime;
    @Transient
    private String             finishTimeShow;

    /** 签收时间 */
    private Date               signDate;
    @Transient
    private String             signDateShow;
    
    /** 签收人名称 */
    private String             signerName;
    
	public String getInsideId() {
		return insideId;
	}

	public void setInsideId(String insideId) {
		this.insideId = insideId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getNodeInstId() {
		return nodeInstId;
	}

	public void setNodeInstId(String nodeInstId) {
		this.nodeInstId = nodeInstId;
	}

	public WfBusinessType getFileType() {
		return fileType;
	}

	public void setFileType(WfBusinessType fileType) {
		this.fileType = fileType;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
		if (this.receiveDate != null) {
            this.receiveDateShow = Tools.DATE_TIME_FORMAT.format(this.receiveDate);
        }
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
		if (this.createDate != null) {
            this.createDateShow = Tools.DATE_TIME_FORMAT.format(this.createDate);
        }
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

	public String getCreatorDepId() {
		return creatorDepId;
	}

	public void setCreatorDepId(String creatorDepId) {
		this.creatorDepId = creatorDepId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public DocCate getDocCate() {
		return docCate;
	}

	public void setDocCate(DocCate docCate) {
		this.docCate = docCate;
	}

	public DocSource getSourceType() {
		return sourceType;
	}

	public void setSourceType(DocSource sourceType) {
		this.sourceType = sourceType;
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

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
		if (this.expiryDate != null) {
            this.expiryDateShow = Tools.DATE_TIME_FORMAT.format(this.expiryDate);
        }
	}

	public DenseType getDense() {
		return dense;
	}

	public void setDense(DenseType dense) {
		this.dense = dense;
	}

	public OpenFlag getOpenFlag() {
		return openFlag;
	}

	public void setOpenFlag(OpenFlag openFlag) {
		this.openFlag = openFlag;
	}

	public UrgencyLevelType getUrgencyLevel() {
		return urgencyLevel;
	}

	public void setUrgencyLevel(UrgencyLevelType urgencyLevel) {
		this.urgencyLevel = urgencyLevel;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getSendUnit() {
		return sendUnit;
	}

	public void setSendUnit(String sendUnit) {
		this.sendUnit = sendUnit;
	}

	public AttachFlag getAttachFlag() {
		return attachFlag;
	}

	public void setAttachFlag(AttachFlag attachFlag) {
		this.attachFlag = attachFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateInfo() {
		return createInfo;
	}

	public void setCreateInfo(String createInfo) {
		this.createInfo = createInfo;
	}

	public String getTypist() {
		return typist;
	}

	public void setTypist(String typist) {
		this.typist = typist;
	}

	public Integer getLimitDay() {
		return limitDay;
	}

	public void setLimitDay(Integer limitDay) {
		this.limitDay = limitDay;
	}

	public Integer getCopies() {
		return copies;
	}

	public void setCopies(Integer copies) {
		this.copies = copies;
	}

	public String getFileYear() {
		return fileYear;
	}

	public void setFileYear(String fileYear) {
		this.fileYear = fileYear;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getSncodeType() {
		return sncodeType;
	}

	public void setSncodeType(String sncodeType) {
		this.sncodeType = sncodeType;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getFlowInstId() {
		return flowInstId;
	}

	public void setFlowInstId(String flowInstId) {
		this.flowInstId = flowInstId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getLimitStr() {
		return limitStr;
	}

	public void setLimitStr(String limitStr) {
		this.limitStr = limitStr;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getDeadlineType() {
		return deadlineType;
	}

	public void setDeadlineType(String deadlineType) {
		this.deadlineType = deadlineType;
	}

	public RemoteDocSwapType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(RemoteDocSwapType documentType) {
		this.documentType = documentType;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Long getSenderDeptId() {
		return senderDeptId;
	}

	public void setSenderDeptId(Long senderDeptId) {
		this.senderDeptId = senderDeptId;
	}

	public String getSenderDeptName() {
		return senderDeptName;
	}

	public void setSenderDeptName(String senderDeptName) {
		this.senderDeptName = senderDeptName;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
		if (this.sendTime != null) {
            this.sendTimeShow = Tools.DATE_TIME_FORMAT.format(this.sendTime);
        }
	}

	public Long getReceiveDeptId() {
		return receiveDeptId;
	}

	public void setReceiveDeptId(Long receiveDeptId) {
		this.receiveDeptId = receiveDeptId;
	}

	public String getReceiveDeptName() {
		return receiveDeptName;
	}

	public void setReceiveDeptName(String receiveDeptName) {
		this.receiveDeptName = receiveDeptName;
	}

	public OuterDocStatus getStatus() {
		return status;
	}

	public void setStatus(OuterDocStatus status) {
		this.status = status;
	}

	public String getReceiveDateShow() {
		return receiveDateShow;
	}

	public String getCreateDateShow() {
		return createDateShow;
	}

	public String getExpiryDateShow() {
		return expiryDateShow;
	}

	public String getSendTimeShow() {
		return sendTimeShow;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
		if (this.finishTime != null) {
            this.finishTimeShow = Tools.DATE_TIME_FORMAT.format(this.finishTime);
        }
	}

	public String getFinishTimeShow() {
		return finishTimeShow;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
		if (this.signDate != null) {
            this.signDateShow = Tools.DATE_TIME_FORMAT.format(this.signDate);
        }
	}

	public String getSignerName() {
		return signerName;
	}

	public void setSignerName(String signerName) {
		this.signerName = signerName;
	}

	public String getSignDateShow() {
		return signDateShow;
	}

}
