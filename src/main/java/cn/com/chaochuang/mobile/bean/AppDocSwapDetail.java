package cn.com.chaochuang.mobile.bean;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.doc.event.reference.DocCate;
import cn.com.chaochuang.doc.event.reference.UrgencyLevelType;
import org.dozer.Mapping;

import java.util.Date;
import java.util.List;

/**
 * 2018-7-6
 *
 * @author shicx
 */
public class AppDocSwapDetail {

    /**
     * 关联公文Id
     */
    private String docFileId;
    private DocCate docCate;
    private Long sendMan;
    private String sendManName;
    private Date sendTime;
    private String docTitle;
    private String readMan;
    private String readDept;
    private String docNumber;
    private String remark;
    private String attachFlag;
    private String sendUnit;
    private Date createTime;
    @Mapping("urgencyLevel.value")
    private UrgencyLevelType urgencyLevel;

    private List<SysAttach> attachList;

    public String getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(String docFileId) {
        this.docFileId = docFileId;
    }

    public DocCate getDocCate() {
        return docCate;
    }

    public void setDocCate(DocCate docCate) {
        this.docCate = docCate;
    }

    public Long getSendMan() {
        return sendMan;
    }

    public void setSendMan(Long sendMan) {
        this.sendMan = sendMan;
    }

    public String getSendManName() {
        return sendManName;
    }

    public void setSendManName(String sendManName) {
        this.sendManName = sendManName;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getReadMan() {
        return readMan;
    }

    public void setReadMan(String readMan) {
        this.readMan = readMan;
    }

    public String getReadDept() {
        return readDept;
    }

    public void setReadDept(String readDept) {
        this.readDept = readDept;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAttachFlag() {
        return attachFlag;
    }

    public void setAttachFlag(String attachFlag) {
        this.attachFlag = attachFlag;
    }

    public String getSendUnit() {
        return sendUnit;
    }

    public void setSendUnit(String sendUnit) {
        this.sendUnit = sendUnit;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public UrgencyLevelType getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(UrgencyLevelType urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public List<SysAttach> getAttachList() {
        return attachList;
    }

    public void setAttachList(List<SysAttach> attachList) {
        this.attachList = attachList;
    }
}
