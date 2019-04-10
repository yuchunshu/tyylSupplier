/*
 * FileName:    DocDepLetter.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年11月25日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.doc.letter.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;
import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.common.reference.AttachFlagConverter;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.event.reference.UrgencyLevelType;
import cn.com.chaochuang.doc.event.reference.UrgencyLevelTypeConverter;
import cn.com.chaochuang.doc.letter.reference.LetterStatus;
import cn.com.chaochuang.doc.letter.reference.LetterStatusConverter;

/**
 * @author LJX
 *
 *         部门函件
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "letter_id")) })
@Table(name = "oa_doc_dep_letter")
public class DocDepLetter extends StringUuidEntity {

    private static final long serialVersionUID = 2426640747790807245L;
    /** 公文ID */
    private String            fileId;
    @ManyToOne
    @JoinColumn(name = "fileId", insertable = false, updatable = false)
    private OaDocFile         docFile;
    /** 标题 */
    private String            title;
    /** 函件编号 */
    private String            letterCode;
    /** 接收部门 */
    private String            incepterDept;
    /** 处理时限日期 */
    private Date              expiryDate;
    /** 紧急程度 */
    @Convert(converter = UrgencyLevelTypeConverter.class)
    private UrgencyLevelType  urgencyLevel;
    /** 内容 */
    private String            content;
    /** 状态 */
    @Convert(converter = LetterStatusConverter.class)
    private LetterStatus      status;
    /** 创建人 */
    private Long              creatorId;
    /** 创建人姓名 */
    private String            creatorName;
    /** 创建人部门 */
    private Long              creatorDeptId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creatorDeptId", insertable = false, updatable = false)
    private SysDepartment     creatorDep;
    /** 创建人单位 */
    private Long              creatorAncestorDep;
    @OneToOne
    @JoinColumn(name = "creatorAncestorDep", insertable = false, updatable = false)
    private SysDepartment     unit;
    /** 创建日期 */
    @Temporal(TemporalType.TIMESTAMP)
    private Date              createDate;
    /** 附件标识 */
    @Convert(converter = AttachFlagConverter.class)
    private AttachFlag        attachFlag;
    /** 办理工作日时限 */
    private Integer           limitDay;
    /** 版本号 */
    private Integer           apprVer;
    // /** 接收部门 */
    // @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE })
    // @JoinColumn(name = "letterId")
    // private Set<DocDepLetterReceiver> receivers;

    /**
     * @return the creatorDep
     */
    public SysDepartment getCreatorDep() {
        return creatorDep;
    }

    public Integer getApprVer() {
        return apprVer;
    }

    public void setApprVer(Integer apprVer) {
        this.apprVer = apprVer;
    }

    /**
     * @return the fileId
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * @param fileId
     *            the fileId to set
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    /**
     * @param creatorDep
     *            the creatorDep to set
     */
    public void setCreatorDep(SysDepartment creatorDep) {
        this.creatorDep = creatorDep;
    }

    /**
     * @return the unit
     */
    public SysDepartment getUnit() {
        return unit;
    }

    /**
     * @param unit
     *            the unit to set
     */
    public void setUnit(SysDepartment unit) {
        this.unit = unit;
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
     * @return the letterCode
     */
    public String getLetterCode() {
        return letterCode;
    }

    /**
     * @param letterCode
     *            the letterCode to set
     */
    public void setLetterCode(String letterCode) {
        this.letterCode = letterCode;
    }

    /**
     * @return the incepterDept
     */
    public String getIncepterDept() {
        return incepterDept;
    }

    /**
     * @param incepterDept
     *            the incepterDept to set
     */
    public void setIncepterDept(String incepterDept) {
        this.incepterDept = incepterDept;
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
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content) {
        this.content = content;
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
     * @return the creatorAncestorDep
     */
    public Long getCreatorAncestorDep() {
        return creatorAncestorDep;
    }

    /**
     * @param creatorAncestorDep
     *            the creatorAncestorDep to set
     */
    public void setCreatorAncestorDep(Long creatorAncestorDep) {
        this.creatorAncestorDep = creatorAncestorDep;
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
     * @return the status
     */
    public LetterStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(LetterStatus status) {
        this.status = status;
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

    public OaDocFile getDocFile() {
        return docFile;
    }

    public void setDocFile(OaDocFile docFile) {
        this.docFile = docFile;
    }

    // /**
    // * @return the receivers
    // */
    // public Set<DocDepLetterReceiver> getReceivers() {
    // return receivers;
    // }
    //
    // /**
    // * @param receivers
    // * the receivers to set
    // */
    // public void setReceivers(Set<DocDepLetterReceiver> receivers) {
    // this.receivers = receivers;
    // }

}
