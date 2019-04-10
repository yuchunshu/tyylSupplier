package cn.com.chaochuang.doc.docread.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.common.reference.SignStatus;
import cn.com.chaochuang.common.reference.SignStatusConverter;
import cn.com.chaochuang.common.user.domain.SysUser;

/**
 * @author HeYunTao
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
@Table(name = "oa_doc_read_receives")
public class DocReadReceives extends LongIdEntity {

    private Long              docReadId;
    private Long              receiverId;
    private String            receiverName;
    private Long              receiverDeptId;
    private String            receiverDeptName;
    @Convert(converter = SignStatusConverter.class)
    private SignStatus        status;
    private Date              readTime;
    private String            opinion;
    private Date              opinionTime;
    @ManyToOne
    @JoinColumn(name = "receiverId", insertable = false, updatable = false)
    private SysUser           receiver;
    @OneToOne
    @JoinColumn(name = "docReadId", insertable = false, updatable = false)
    private DocRead           docread;

    /**
     * @return the docReadId
     */
    public Long getDocReadId() {
        return docReadId;
    }

    /**
     * @param docReadId
     *            the docReadId to set
     */
    public void setDocReadId(Long docReadId) {
        this.docReadId = docReadId;
    }

    /**
     * @return the receiverId
     */
    public Long getReceiverId() {
        return receiverId;
    }

    /**
     * @param receiverId
     *            the receiverId to set
     */
    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    /**
     * @return the receiverName
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * @param receiverName
     *            the receiverName to set
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
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
     * @return the receiverDeptName
     */
    public String getReceiverDeptName() {
        return receiverDeptName;
    }

    /**
     * @param receiverDeptName
     *            the receiverDeptName to set
     */
    public void setReceiverDeptName(String receiverDeptName) {
        this.receiverDeptName = receiverDeptName;
    }

    public SignStatus getStatus() {
        return status;
    }

    public void setStatus(SignStatus status) {
        this.status = status;
    }

    /**
     * @return the readTime
     */
    public Date getReadTime() {
        return readTime;
    }

    /**
     * @param readTime
     *            the readTime to set
     */
    public void setReadTime(Date readTime) {
        this.readTime = readTime;
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
     * @return the opinionTime
     */
    public Date getOpinionTime() {
        return opinionTime;
    }

    /**
     * @param opinionTime
     *            the opinionTime to set
     */
    public void setOpinionTime(Date opinionTime) {
        this.opinionTime = opinionTime;
    }

    /**
     * @return the receiver
     */
    public SysUser getReceiver() {
        return receiver;
    }

    /**
     * @param receiver
     *            the receiver to set
     */
    public void setReceiver(SysUser receiver) {
        this.receiver = receiver;
    }

    /**
     * @return the docread
     */
    public DocRead getDocread() {
        return docread;
    }

    /**
     * @param docread
     *            the docread to set
     */
    public void setDocread(DocRead docread) {
        this.docread = docread;
    }

}