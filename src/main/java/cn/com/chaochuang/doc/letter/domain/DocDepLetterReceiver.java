/*
 * FileName:    DocDepLetterReceiver.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年11月25日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.doc.letter.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;
import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.common.reference.AttachFlagConverter;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.doc.letter.reference.LetterReceiverStatus;
import cn.com.chaochuang.doc.letter.reference.LetterReceiverStatusConverter;
import cn.com.chaochuang.common.attach.domain.SysAttach;

/**
 * @author LJX
 *
 *         部门函件接收
 */
@Entity
@Table(name = "oa_doc_dep_letter_receiver")
public class DocDepLetterReceiver extends StringUuidEntity {

    private static final long    serialVersionUID = -3365599106084179002L;
    private String               letterId;
    /** 接收部门 */
    private Long                 receiverDeptId;
    @ManyToOne
    @JoinColumn(name = "receiverDeptId", insertable = false, updatable = false)
    private SysDepartment        receiverDept;
    /** 接收单位编号 */
    private Long                 receiverAncestorDep;
    @ManyToOne
    @JoinColumn(name = "receiverAncestorDep", insertable = false, updatable = false)
    private SysDepartment        receiverAncestor;
    /** 接收日期 */
    @Temporal(TemporalType.TIMESTAMP)
    private Date                 receiveDate;
    /** 反馈日期 */
    private Date                 reversionDate;
    /** 反馈内容 */
    private String               content;
    /** 补充说明 */
    private String               replenish;
    /** 状态 */
    @Convert(converter = LetterReceiverStatusConverter.class)
    private LetterReceiverStatus status;
    /** 附件标识 */
    @Convert(converter = AttachFlagConverter.class)
    private AttachFlag           attachFlag;

    @OneToOne
    @JoinColumn(name = "letterId", insertable = false, updatable = false)
    private DocDepLetter         letter;

    /** 只用于显示 */
    @Transient
    private List<SysAttach>      attachs;

    /**
     * @return the letter
     */
    public DocDepLetter getLetter() {
        return letter;
    }

    /**
     * @param letter
     *            the letter to set
     */
    public void setLetter(DocDepLetter letter) {
        this.letter = letter;
    }

    /**
     * @return the letterId
     */
    public String getLetterId() {
        return letterId;
    }

    /**
     * @param letterId
     *            the letterId to set
     */
    public void setLetterId(String letterId) {
        this.letterId = letterId;
    }

    /**
     * @return the receiverDeptId
     */
    public Long getReceiverDeptId() {
        return receiverDeptId;
    }

    /**
     * @param receiverDeptId
     *            the receiverDeptId to set
     */
    public void setReceiverDeptId(Long receiverDeptId) {
        this.receiverDeptId = receiverDeptId;
    }

    /**
     * @return the receiverAncestorDep
     */
    public Long getReceiverAncestorDep() {
        return receiverAncestorDep;
    }

    /**
     * @param receiverAncestorDep
     *            the receiverAncestorDep to set
     */
    public void setReceiverAncestorDep(Long receiverAncestorDep) {
        this.receiverAncestorDep = receiverAncestorDep;
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
     * @return the reversionDate
     */
    public Date getReversionDate() {
        return reversionDate;
    }

    /**
     * @param reversionDate
     *            the reversionDate to set
     */
    public void setReversionDate(Date reversionDate) {
        this.reversionDate = reversionDate;
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
     * @return the replenish
     */
    public String getReplenish() {
        return replenish;
    }

    /**
     * @param replenish
     *            the replenish to set
     */
    public void setReplenish(String replenish) {
        this.replenish = replenish;
    }

    /**
     * @return the status
     */
    public LetterReceiverStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(LetterReceiverStatus status) {
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

    public SysDepartment getReceiverDept() {
        return receiverDept;
    }

    public void setReceiverDept(SysDepartment receiverDept) {
        this.receiverDept = receiverDept;
    }

    public SysDepartment getReceiverAncestor() {
        return receiverAncestor;
    }

    public void setReceiverAncestor(SysDepartment receiverAncestor) {
        this.receiverAncestor = receiverAncestor;
    }

    public List<SysAttach> getAttachs() {
        return attachs;
    }

    public void setAttachs(List<SysAttach> attachs) {
        this.attachs = attachs;
    }

}
