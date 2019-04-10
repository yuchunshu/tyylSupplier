/*
 * FileName:    OaDocReceiveFile.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.docread.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.common.reference.AttachFlagConverter;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.common.reference.YesNoConverter;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.doc.docread.reference.DocProperty;
import cn.com.chaochuang.doc.docread.reference.DocPropertyConverter;
import cn.com.chaochuang.doc.docread.reference.DocReadStatus;
import cn.com.chaochuang.doc.docread.reference.DocReadStatusConverter;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.event.reference.DocCate;
import cn.com.chaochuang.doc.event.reference.DocCateConverter;

/**
 * @author HeYunTao
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
@Table(name = "oa_doc_read")
public class DocRead extends LongIdEntity {

    private static final long    serialVersionUID = 1153312316351132725L;

    /** 关联公文Id */
    private String               docFileId;
    @ManyToOne
    @JoinColumn(name = "docFileId", insertable = false, updatable = false)
    private OaDocFile            docFile;

    @Convert(converter = DocPropertyConverter.class)
    private DocProperty          docPropertyId;

    @Convert(converter = DocCateConverter.class)
    private DocCate              docCate;

    private Long                 sendMan;

    private String               sendManName;

    private Long                 sendDeptId;

    @ManyToOne
    @JoinColumn(name = "sendDeptId", insertable = false, updatable = false)
    private SysDepartment        sendDept;

    private Date                 sendTime;

    private String               docTitle;

    @Convert(converter = DocReadStatusConverter.class)
    private DocReadStatus        status;

    private String               readMan;

    private String               readDept;

    private String               docNumber;

    private String               remark;

    @Convert(converter = AttachFlagConverter.class)
    private AttachFlag           attachFlag;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "docReadId", insertable = false, updatable = false)
    private Set<DocReadReceives> docReadReceivess;

    /** 是否转来文签收 */
    @Convert(converter = YesNoConverter.class)
    private YesNo                toSigned;
    /** 来文单位 */
    private String               sendUnit;
    /** 创建日期 */
    @Temporal(TemporalType.TIMESTAMP)
    private Date                 createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSendUnit() {
        return sendUnit;
    }

    public void setSendUnit(String sendUnit) {
        this.sendUnit = sendUnit;
    }

    /**
     * @return the docPropertyId
     */
    public DocProperty getDocPropertyId() {
        return docPropertyId;
    }

    /**
     * @param docPropertyId
     *            the docPropertyId to set
     */
    public void setDocPropertyId(DocProperty docPropertyId) {
        this.docPropertyId = docPropertyId;
    }

    public DocCate getDocCate() {
        return docCate;
    }

    public void setDocCate(DocCate docCate) {
        this.docCate = docCate;
    }

    /**
     * @return the sendMan
     */
    public Long getSendMan() {
        return sendMan;
    }

    /**
     * @param sendMan
     *            the sendMan to set
     */
    public void setSendMan(Long sendMan) {
        this.sendMan = sendMan;
    }

    /**
     * @return the sendManName
     */
    public String getSendManName() {
        return sendManName;
    }

    /**
     * @param sendManName
     *            the sendManName to set
     */
    public void setSendManName(String sendManName) {
        this.sendManName = sendManName;
    }

    /**
     * @return the sendDeptId
     */
    public Long getSendDeptId() {
        return sendDeptId;
    }

    /**
     * @param sendDeptId
     *            the sendDeptId to set
     */
    public void setSendDeptId(Long sendDeptId) {
        this.sendDeptId = sendDeptId;
    }

    /**
     * @return the sendTime
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * @param sendTime
     *            the sendTime to set
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * @return the docTitle
     */
    public String getDocTitle() {
        return docTitle;
    }

    /**
     * @param docTitle
     *            the docTitle to set
     */
    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    /**
     * @return the status
     */
    public DocReadStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(DocReadStatus status) {
        this.status = status;
    }

    /**
     * @return the readMan
     */
    public String getReadMan() {
        return readMan;
    }

    /**
     * @param readMan
     *            the readMan to set
     */
    public void setReadMan(String readMan) {
        this.readMan = readMan;
    }

    /**
     * @return the readDept
     */
    public String getReadDept() {
        return readDept;
    }

    /**
     * @param readDept
     *            the readDept to set
     */
    public void setReadDept(String readDept) {
        this.readDept = readDept;
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
     * @return the docReadReceivess
     */
    public Set<DocReadReceives> getDocReadReceivess() {
        return docReadReceivess;
    }

    /**
     * @param docReadReceivess
     *            the docReadReceivess to set
     */
    public void setDocReadReceivess(Set<DocReadReceives> docReadReceivess) {
        this.docReadReceivess = docReadReceivess;
    }

    public YesNo getToSigned() {
        return toSigned;
    }

    public void setToSigned(YesNo toSigned) {
        this.toSigned = toSigned;
    }

    public String getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(String docFileId) {
        this.docFileId = docFileId;
    }

    public OaDocFile getDocFile() {
        return docFile;
    }

    public void setDocFile(OaDocFile docFile) {
        this.docFile = docFile;
    }

}
