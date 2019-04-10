package cn.com.chaochuang.doc.remotedocswap.bean;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapContent;

/** 
 * @ClassName: DocStatisticData 
 * @Description: 公文台账列表返回结果
 * @author: chunshu
 * @date: 2017年7月18日 下午10:29:28  
 */
public class DocStatisticData {

	/**公文唯一标识 */
    private String 				 docID;
    
	/**标题 */
    private String 				 docTile;
    
    /**文号 */
    private String 				 docNo;
    
    /**发文单位组织结构代码 */
    private String 				 sendUnitCode;
    
    /**收文单位组织结构代码 */
    private String 				 receiveUnitCode;
    
    /**发文时间 */
    private String 				 sendTime;
    
    private Date 				 sendTimeObj;
    
    /**限时办结时间 */
    private String 				 limitTime;
    
    private Date 				 limitTimeObj;
    
    /**公文来源 */
    private String 				 docFrom;
    
    /**业务流水号 */
    private String 				 ywlsh;
    
    /**联系人 */
    private String 				 contact;
    
    /**联系电话 */
    private String 				 phone;
    
    /**公文类型 */
    private String 				 docType;
    
    /** */
    private String 				 businessID;
    
    /**公文状态，0：在办，1：已办，2：已撤销，3：已退文，4：超时未办结，5：超时办结，6：错误文，默认空：全部（正常在办、已办的文） */
    private String 				 docStatus;

    /**入库时间 */
    private String 				 insertTime;
    
    /**错误原因 */
    private String 				 errorReason;
    
    private String 				 type;
    
    private String 				 srcFileName;

    private RemoteDocSwapContent linkDoc;

    public Date getSendTimeObj() {
        try {
            if(sendTime!=null) {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sendTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Date getLimitTimeObj() {
        try {
            if(limitTime!=null) {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(limitTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getDocTile() {
        return docTile;
    }

    public void setDocTile(String docTile) {
        this.docTile = docTile;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getSendUnitCode() {
        return sendUnitCode;
    }

    public void setSendUnitCode(String sendUnitCode) {
        this.sendUnitCode = sendUnitCode;
    }

    public String getReceiveUnitCode() {
        return receiveUnitCode;
    }

    public void setReceiveUnitCode(String receiveUnitCode) {
        this.receiveUnitCode = receiveUnitCode;
    }

    public String getDocFrom() {
        return docFrom;
    }

    public void setDocFrom(String docFrom) {
        this.docFrom = docFrom;
    }

    public String getYwlsh() {
        return ywlsh;
    }

    public void setYwlsh(String ywlsh) {
        this.ywlsh = ywlsh;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getBusinessID() {
        return businessID;
    }

    public void setBusinessID(String businessID) {
        this.businessID = businessID;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    public RemoteDocSwapContent getLinkDoc() {
        return linkDoc;
    }

    public void setLinkDoc(RemoteDocSwapContent linkDoc) {
        this.linkDoc = linkDoc;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(String limitTime) {
        this.limitTime = limitTime;
    }

    public void setSendTimeObj(Date sendTimeObj) {
        this.sendTimeObj = sendTimeObj;
    }

    public void setLimitTimeObj(Date limitTimeObj) {
        this.limitTimeObj = limitTimeObj;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSrcFileName() {
        return srcFileName;
    }

    public void setSrcFileName(String srcFileName) {
        this.srcFileName = srcFileName;
    }
}
