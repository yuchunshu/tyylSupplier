/*
 * FileName:    OaDocReceiveFile.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.event.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;
import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.common.reference.AttachFlagConverter;
import cn.com.chaochuang.common.reference.FeedbackFlag;
import cn.com.chaochuang.common.reference.FeedbackFlagConverter;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.doc.archive.domain.DocArchives;
import cn.com.chaochuang.doc.event.reference.DenseType;
import cn.com.chaochuang.doc.event.reference.DenseTypeConverter;
import cn.com.chaochuang.doc.event.reference.DocCate;
import cn.com.chaochuang.doc.event.reference.DocCateConverter;
import cn.com.chaochuang.doc.event.reference.DocSource;
import cn.com.chaochuang.doc.event.reference.DocSourceConverter;
import cn.com.chaochuang.doc.event.reference.FileStatusFlag;
import cn.com.chaochuang.doc.event.reference.FileStatusFlagConverter;
import cn.com.chaochuang.doc.event.reference.OpenFlag;
import cn.com.chaochuang.doc.event.reference.OpenFlagConverter;
import cn.com.chaochuang.doc.event.reference.UrgencyLevelType;
import cn.com.chaochuang.doc.event.reference.UrgencyLevelTypeConverter;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapTypeConverter;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfBusinessTypeConverter;

/**
 * @author hzr 2016.8.6
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "file_id")) })
@Table(name = "oa_doc_file")
public class OaDocFile extends StringUuidEntity {

    private static final long  serialVersionUID      = 1153312316351132725L;
    //
    // /**收文、发文标记*/
    // public static final String RECEIVE_FILE = "1";
    // public static final String SEND_FILE = "2";

    /** 所属正文附件标识名 */
    public static final String DOC_ATTACH_OWNER_MAIN = "OaDocFileMain";
    /** 所属正文PDF附件标识名 */
    public static final String DOC_ATTACH_OWNER_MAIN_PDF = "OaDocFileMainPdf";
    /** 所属非正文附件标识 */
    public static final String DOC_ATTACH_OWNER_DOC  = "OaDocFile";
    /** 正文附件type */
    public static final String DOC_ATTACH_MAIN_TYPE  = "mainFile";

    /** 流程实例ID */
    private String             flowInstId;
    /** 流程定义ID */
    private String             flowId;

    /** 收文或发文 */
    @Convert(converter = WfBusinessTypeConverter.class)
    private WfBusinessType     fileType;

    /** 文电标题 */
    private String             title;
    /** 来文字号规则：W－年－序号；年即当前年度，为四位，如2008，序号四位，从0001～9999，依次递增 */
    private String             docCode;
    /** 编 号，自然编号，如zh2017-0006 */
    private String             sncode;
    /** 编号类型 */
    private String             sncodeType;
    /** 年份 */
    private String             fileYear;
    /** 收文号、发文号 */
    private String             fileCode;
    /** 发文(来文)单位 */
    private String             sendUnit;
    /** 处理时限（截止日期） */
    private Date               expiryDate;
    /** 办理工作日时限 */
    private Integer            limitDay;
    /** 发文时限要求（文字说明）*/
    private String             limitStr;

    /** 主送 */
    private String             mainSend;
    /** 抄报 */
    private String             reportSend;
    /** 抄送 */
    private String             copySend;
    /** 公开 */
    @Convert(converter = OpenFlagConverter.class)
    private OpenFlag           openFlag;                                    // '公开：0-主动公开 1-依申请公开
    /** 是否督办 */
    private String             superviseFlag;
    /** 处室共享 */
    private String             deptShare;
    /** 公文摘要 */
    private String             digest;
    /** 处理意见 */
    private String             suggestion;

    private String             remark;

    /** 主题词 */
    private String             keyword;

    /** 校对人 */
    private Long               reviewer;
    /** 打印人 */
    private Long               printMan;
    /** 打印份数 */
    private Integer            copies;
    /** 印发日期 */
    private Date               printDate;

    /** 收文日期 */
    private Date               receiveDate;
    /** 拟稿时间 */
    private Date               createDate;
    /** 紧急程度 */
    @Convert(converter = UrgencyLevelTypeConverter.class)
    private UrgencyLevelType   urgencyLevel;
    /** 密级 */
    @Convert(converter = DenseTypeConverter.class)
    private DenseType          dense;
    /** 状态 */
    @Convert(converter = FileStatusFlagConverter.class)
    private FileStatusFlag     status;

    /** 拟稿人 user_id */
    private Long               creatorId;
    private String             creatorName;

    /** 拟稿人部门编号 */
    // private Long createrDepId;
    @ManyToOne
    @JoinColumn(name = "creatorDepId")
    private SysDepartment      createrDept;
    /** 档案ID */
    // private Long archId;
    /** 所属案卷 */
    // private DocArchives archives;
    @ManyToOne
    @JoinColumn(name = "archId")
    private DocArchives        archives;
    /** 收文类型 */
    @Convert(converter = DocSourceConverter.class)
    private DocSource          sourceType;
    /** 正文附件编号 */
    private String             docId;
    /** 0 无 1 有附件 */
    @Convert(converter = AttachFlagConverter.class)
    private AttachFlag         attachFlag;
    /** 文种 */
    @Convert(converter = DocCateConverter.class)
    private DocCate            docCate;
    /** 意见反馈函件编号 */
    private String             docDepLetterId;
    /** 意见反馈状态 */
    @Convert(converter = FeedbackFlagConverter.class)
    private FeedbackFlag       letterStatus;

    /** 联系电话 */
    private String             telephone;

    /** 拟稿信息 */
    private String             createInfo;
    /** 一校 */
    private String             reviewer1;
    /** 二校 */
    private String             reviewer2;
    /** 打字员 */
    private String             typist;

    /** 版本号（用于多用户保存互斥判断） */
    private Integer            version_;


    //------用于限时办结-----------------------------------
    /** 来文单位简称 */
    private String 			   printOrgAbb;
    //----------------------------------------------------


    @Transient
    private String             opinion;
    /** 附件 */
    @Transient
    private Long[]             attachId;
    /** 相关公文添加附件字符串 */
    @Transient
    private String             linkAttachIds;
    /** 相关公文编号 */
    @Transient
    private Long               linkFileId;
    @Transient
    private String             waitOther;
    @Transient
    private String             endCond;
    @Transient
    // 暂存下一个环节的nodeid
    private String             specialDelivery;
    @Transient
    /** 用于显示续签 */
    private List<String>       verNums;

    /** 公文类型（办件：0 阅件：1） */
    @Convert(converter = RemoteDocSwapTypeConverter.class)
    private RemoteDocSwapType  documentType;
    
    /** 截止日期类型 */
    private String       	   deadlineType;
    
    /** 父类ID（关联转办公文ID） */
    private String             parentId;
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
     * @return the docCode
     */
    public String getDocCode() {
        return docCode;
    }

    /**
     * @param docCode
     *            the docCode to set
     */
    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    /**
     * @return the sncode
     */
    public String getSncode() {
        return sncode;
    }

    /**
     * @param sncode
     *            the sncode to set
     */
    public void setSncode(String sncode) {
        this.sncode = sncode;
    }

    /**
     * @return the sendUnit
     */
    public String getSendUnit() {
        return sendUnit;
    }

    /**
     * @param sendUnit
     *            the sendUnit to set
     */
    public void setSendUnit(String sendUnit) {
        this.sendUnit = sendUnit;
    }

    /**
     * @return the expiryDate
     */
    public Date getExpiryDate() {
        return expiryDate;
    }

    /**
     * @param expiryDate
     *            the expiryDate to set
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * @return the receiveDate
     */
    public Date getReceiveDate() {
        return receiveDate;
    }

    /**
     * @param receiveDate
     *            the receiveDate to set
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @return the urgencyLevel
     */
    public UrgencyLevelType getUrgencyLevel() {
        return urgencyLevel;
    }

    /**
     * @param urgencyLevel
     *            the urgencyLevel to set
     */
    public void setUrgencyLevel(UrgencyLevelType urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    /**
     * @return the status
     */
    public FileStatusFlag getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(FileStatusFlag status) {
        this.status = status;
    }


    public Integer getCopies() {
		return copies;
	}

	public void setCopies(Integer copies) {
		this.copies = copies;
	}

	/**
     * @return the createrDept
     */
    public SysDepartment getCreaterDept() {
        return createrDept;
    }

    /**
     * @param createrDept
     *            the createrDept to set
     */
    public void setCreaterDept(SysDepartment createrDept) {
        this.createrDept = createrDept;
    }

    /**
     * @return the attachFlag
     */
    public AttachFlag getAttachFlag() {
        return attachFlag;
    }

    /**
     * @param attachFlag
     *            the attachFlag to set
     */
    public void setAttachFlag(AttachFlag attachFlag) {
        this.attachFlag = attachFlag;
    }

    public String getLimitStr() {
		return limitStr;
	}

	public void setLimitStr(String limitStr) {
		this.limitStr = limitStr;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
     * @return the opinion
     */
    public String getOpinion() {
        return opinion;
    }

    /**
     * @param opinion
     *            the opinion to set
     */
    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    /**
     * @return the attachId
     */
    public Long[] getAttachId() {
        return attachId;
    }

    /**
     * @param attachId
     *            the attachId to set
     */
    public void setAttachId(Long[] attachId) {
        this.attachId = attachId;
    }

    /**
     * @return the linkAttachIds
     */
    public String getLinkAttachIds() {
        return linkAttachIds;
    }

    /**
     * @param linkAttachIds
     *            the linkAttachIds to set
     */
    public void setLinkAttachIds(String linkAttachIds) {
        this.linkAttachIds = linkAttachIds;
    }

    /**
     * @return the linkFileId
     */
    public Long getLinkFileId() {
        return linkFileId;
    }

    /**
     * @param linkFileId
     *            the linkFileId to set
     */
    public void setLinkFileId(Long linkFileId) {
        this.linkFileId = linkFileId;
    }

    /**
     * @return the archives
     */
    public DocArchives getArchives() {
        return archives;
    }

    /**
     * @param archives
     *            the archives to set
     */
    public void setArchives(DocArchives archives) {
        this.archives = archives;
    }


    /**
     * @return the waitOther
     */
    public String getWaitOther() {
        return waitOther;
    }

    /**
     * @param waitOther
     *            the waitOther to set
     */
    public void setWaitOther(String waitOther) {
        this.waitOther = waitOther;
    }


    public String getEndCond() {
		return endCond;
	}

	public void setEndCond(String endCond) {
		this.endCond = endCond;
	}

	/**
     * @return the specialDelivery
     */
    public String getSpecialDelivery() {
        return specialDelivery;
    }

    /**
     * @param specialDelivery
     *            the specialDelivery to set
     */
    public void setSpecialDelivery(String specialDelivery) {
        this.specialDelivery = specialDelivery;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     *            the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the limitDay
     */
    public Integer getLimitDay() {
        return limitDay;
    }

    /**
     * @param limitDay
     *            the limitDay to set
     */
    public void setLimitDay(Integer limitDay) {
        this.limitDay = limitDay;
    }

    public String getDocDepLetterId() {
        return docDepLetterId;
    }

    public void setDocDepLetterId(String docDepLetterId) {
        this.docDepLetterId = docDepLetterId;
    }

    public FeedbackFlag getLetterStatus() {
        return letterStatus;
    }

    public void setLetterStatus(FeedbackFlag letterStatus) {
        this.letterStatus = letterStatus;
    }

    public String getFlowInstId() {
        return flowInstId;
    }

    public void setFlowInstId(String flowInstId) {
        this.flowInstId = flowInstId;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }



    /**
     * @return the dense
     */
    public DenseType getDense() {
        return dense;
    }

    /**
     * @param dense
     *            the dense to set
     */
    public void setDense(DenseType dense) {
        this.dense = dense;
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

    public OpenFlag getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(OpenFlag openFlag) {
        this.openFlag = openFlag;
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

    public DocSource getSourceType() {
        return sourceType;
    }

    public void setSourceType(DocSource sourceType) {
        this.sourceType = sourceType;
    }

    public WfBusinessType getFileType() {
        return fileType;
    }

    public void setFileType(WfBusinessType fileType) {
        this.fileType = fileType;
    }

    public DocCate getDocCate() {
        return docCate;
    }

    public void setDocCate(DocCate docCate) {
        this.docCate = docCate;
    }

    public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCreateInfo() {
        return createInfo;
    }

    public void setCreateInfo(String createInfo) {
        this.createInfo = createInfo;
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

    public String getTypist() {
        return typist;
    }

    public void setTypist(String typist) {
        this.typist = typist;
    }

	public Date getPrintDate() {
		return printDate;
	}

	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}

	public String getFileYear() {
        return fileYear;
    }

    public void setFileYear(String fileYear) {
        this.fileYear = fileYear;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }


    public Integer getVersion_() {
		return version_;
	}

	public void setVersion_(Integer version_) {
		this.version_ = version_;
	}


    public void setVerNums(List<String> verNums) {
        this.verNums = verNums;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    /**
     * @return the sncodeType
     */
    public String getSncodeType() {
        return sncodeType;
    }

    /**
     * @param sncodeType
     *            the sncodeType to set
     */
    public void setSncodeType(String sncodeType) {
        this.sncodeType = sncodeType;
    }


	public String getSuperviseFlag() {
		return superviseFlag;
	}

	public void setSuperviseFlag(String superviseFlag) {
		this.superviseFlag = superviseFlag;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getPrintOrgAbb() {
		return printOrgAbb;
	}

	public void setPrintOrgAbb(String printOrgAbb) {
		this.printOrgAbb = printOrgAbb;
	}

	public String getDeptShare() {
		return deptShare;
	}

	public void setDeptShare(String deptShare) {
		this.deptShare = deptShare;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public RemoteDocSwapType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(RemoteDocSwapType documentType) {
		this.documentType = documentType;
	}

	public String getDeadlineType() {
		return deadlineType;
	}

	public void setDeadlineType(String deadlineType) {
		this.deadlineType = deadlineType;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	


}
