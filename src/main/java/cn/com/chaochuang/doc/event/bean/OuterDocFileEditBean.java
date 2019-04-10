package cn.com.chaochuang.doc.event.bean;


import java.util.Date;

import javax.persistence.Convert;


import cn.com.chaochuang.doc.event.reference.OuterDocStatus;
import cn.com.chaochuang.doc.event.reference.OuterDocStatusConverter;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapTypeConverter;

/**
 * @author yuchunshu 2017.11.25
 *
 */
public class OuterDocFileEditBean {

    private String            id;
    private String 			  ids;
    /** 标题 */
    private String            title;

    /** 公文类型（办件：0 阅件：1） */
    @Convert(converter = RemoteDocSwapTypeConverter.class)
    private RemoteDocSwapType documentType;
    
    private String 			  deadlineType;
    
    /**联系人*/
    private String 			  contactName;
    /**联系电话*/
    private String 			  contactPhone;
    
    @Convert(converter = OuterDocStatusConverter.class)
    private OuterDocStatus 	  feedbackType;
    
    /** 反馈原因 */
    private String 			  reason;

    /** 退回时间 */
    private Date              backTime;
    
    /** 撤销时间 */
    private Date              undoTime;
    
    /** 作废时间 */
    private Date              invalidTime;
    
    /** 传阅对象 */
    private String 			  docReadBody;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public OuterDocStatus getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(OuterDocStatus feedbackType) {
		this.feedbackType = feedbackType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getBackTime() {
		return backTime;
	}

	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}

	public Date getUndoTime() {
		return undoTime;
	}

	public void setUndoTime(Date undoTime) {
		this.undoTime = undoTime;
	}

	public Date getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	public String getDocReadBody() {
		return docReadBody;
	}

	public void setDocReadBody(String docReadBody) {
		this.docReadBody = docReadBody;
	}
    
}
