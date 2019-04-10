package cn.com.chaochuang.doc.event.domain;

import java.util.Date;

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
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.event.reference.OuterDocStatus;
import cn.com.chaochuang.doc.event.reference.OuterDocStatusConverter;

/**
 * @author yuchunshu 2017.11.25
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "back_id")) })
@Table(name = "oa_outer_doc_file_feedback")
public class OaOuterDocFileFeedBack extends StringUuidEntity {

    private static final long  serialVersionUID      = 1153312316351132725L;

    /** 关联系统外发ID */
    private String  		   outerId;
    @ManyToOne
    @JoinColumn(name = "outerId", insertable = false, updatable = false)
    private OaOuterDocFile     outerDoc;
    
    /** 签收时间 */
    private Date               signDate;
    @Transient
    private String             signDateShow;
    
    /** 签收人ID */
    private Long               signerId;
    
    /** 签收人名称 */
    private String             signerName;
    
    /** 签收人部门ID */
    private Long               signerDeptId;
    
    /** 签收人部门名称 */
    private String             signerDeptName;
    
    /** 联系人 */
    private String             contactName;
    
    /** 联系电话 */
    private String             contactPhone;
    
    /** 退回时间 */
    private Date               backTime;
    @Transient
    private String             backTimeShow;
    
    /** 反馈原因 退回、转阅件、不办理原因 */
    private String             reason;
    
    /** 撤销时间 */
    private Date               undoTime;
    @Transient
    private String             undoTimeShow;
    
    /** 作废时间 */
    private Date               invalidTime;
    @Transient
    private String             invalidTimeShow;
    
    /** 反馈类型 */
    @Convert(converter = OuterDocStatusConverter.class)
    private OuterDocStatus     feedbackType;

    /** 入库时间 */
    private Date               createTime;
    @Transient
    private String             createTimeShow;
    
    /** 传阅对象 */
    private String 			   docReadBody;
    
	public OaOuterDocFile getOuterDoc() {
		return outerDoc;
	}

	public void setOuterDoc(OaOuterDocFile outerDoc) {
		this.outerDoc = outerDoc;
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

	public Long getSignerId() {
		return signerId;
	}

	public void setSignerId(Long signerId) {
		this.signerId = signerId;
	}

	public String getSignerName() {
		return signerName;
	}

	public void setSignerName(String signerName) {
		this.signerName = signerName;
	}

	public Long getSignerDeptId() {
		return signerDeptId;
	}

	public void setSignerDeptId(Long signerDeptId) {
		this.signerDeptId = signerDeptId;
	}

	public String getSignerDeptName() {
		return signerDeptName;
	}

	public void setSignerDeptName(String signerDeptName) {
		this.signerDeptName = signerDeptName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Date getBackTime() {
		return backTime;
	}

	public void setBackTime(Date backTime) {
		this.backTime = backTime;
		if (this.backTime != null) {
            this.backTimeShow = Tools.DATE_TIME_FORMAT.format(this.backTime);
        }
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getUndoTime() {
		return undoTime;
	}

	public void setUndoTime(Date undoTime) {
		this.undoTime = undoTime;
		if (this.undoTime != null) {
            this.undoTimeShow = Tools.DATE_TIME_FORMAT.format(this.undoTime);
        }
	}

	public Date getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
		if (this.invalidTime != null) {
            this.invalidTimeShow = Tools.DATE_TIME_FORMAT.format(this.invalidTime);
        }
	}

	public OuterDocStatus getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(OuterDocStatus feedbackType) {
		this.feedbackType = feedbackType;
	}

	public String getSignDateShow() {
		return signDateShow;
	}

	public String getBackTimeShow() {
		return backTimeShow;
	}

	public String getUndoTimeShow() {
		return undoTimeShow;
	}

	public String getInvalidTimeShow() {
		return invalidTimeShow;
	}

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
		if (this.createTime != null) {
            this.createTimeShow = Tools.DATE_TIME_FORMAT.format(this.createTime);
        }
	}

	public String getCreateTimeShow() {
		return createTimeShow;
	}

	public String getDocReadBody() {
		return docReadBody;
	}

	public void setDocReadBody(String docReadBody) {
		this.docReadBody = docReadBody;
	}

}
