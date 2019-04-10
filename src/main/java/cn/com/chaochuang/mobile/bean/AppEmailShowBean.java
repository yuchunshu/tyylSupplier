/*
 * FileName:    MailShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月3日 (HM) 1.0 Create
 */

package cn.com.chaochuang.mobile.bean;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.oa.mail.reference.MailStatus;
import cn.com.chaochuang.oa.mail.reference.ReceiptFlag;
import org.dozer.Mapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author HM
 */
public class AppEmailShowBean {

    //
    private String id;

    //标题
    private String title;

    //发送人
    @Mapping("sender.id")
    private String senderId;
    @Mapping("sender.userName")
    private String senderName;

    //接收人
    private String manAddressNames;
    private String manAddress;

    //内容
    private String content;

    /** 附件列表 */
    private List<SysAttach> attachList;

    /**
     * 状态
     */
    private MailStatus status;

    /**
     * 保存日期
     */
    private Date saveDate;

    /**
     * 发送日期
     */
    private Date sendDate;

    /**
     * 删除时间
     */
    private Date delDate;

    /**
     * 回执:0-否，1-是
     */
    @Mapping("receiptFlag.key")
    private String receiptFlag;

    /**
     * 地址名称 : 收件人/或单位名称s
     */
    private String addressName;

    /**
     * 有无附件
     */
    private AttachFlag attachFlag;

    /**
     * 阅读日期
     */
    private Date readDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getManAddressNames() {
        return manAddressNames;
    }

    public void setManAddressNames(String manAddressNames) {
        this.manAddressNames = manAddressNames;
    }

    public String getManAddress() {
        return manAddress;
    }

    public void setManAddress(String manAddress) {
        this.manAddress = manAddress;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<SysAttach> getAttachList() {
        return attachList;
    }

    public void setAttachList(List<SysAttach> attachList) {
        this.attachList = attachList;
    }

    public MailStatus getStatus() {
        return status;
    }

    public void setStatus(MailStatus status) {
        this.status = status;
    }

    public Date getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getDelDate() {
        return delDate;
    }

    public void setDelDate(Date delDate) {
        this.delDate = delDate;
    }

    public String getReceiptFlag() {
        return receiptFlag;
    }

    public void setReceiptFlag(String receiptFlag) {
        this.receiptFlag = receiptFlag;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public AttachFlag getAttachFlag() {
        return attachFlag;
    }

    public void setAttachFlag(AttachFlag attachFlag) {
        this.attachFlag = attachFlag;
    }

    public Date getReadDate() {
        return readDate;
    }

    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }
}
