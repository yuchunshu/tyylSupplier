/*
 * FileName:    FileShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月24日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.docread.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.dozer.Mapping;

import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.doc.docread.reference.DocProperty;
import cn.com.chaochuang.doc.docread.reference.DocReadStatus;
import cn.com.chaochuang.doc.event.reference.DocCate;

/**
 * @author HeYunTao
 *
 */
public class DocReadShowBean {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private Long             id;
    private DocProperty      docPropertyId;
    private DocCate          docCate;
    private String           sendManName;
    @Mapping("sendDept.deptName")
    private String           sendDepName;
    private Date             sendTime;
    private String           sendTimeShow;
    private String           docTitle;
    private DocReadStatus    status;
    private String           readMan;
    private String           docNumber;
    private AttachFlag       attachFlag;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return the sendDepName
     */
    public String getSendDepName() {
        return sendDepName;
    }

    /**
     * @param sendDepName
     *            the sendDepName to set
     */
    public void setSendDepName(String sendDepName) {
        this.sendDepName = sendDepName;
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
        if (this.sendTime != null) {
            this.sendTimeShow = sdf.format(this.sendTime);
        }
    }

    /**
     * @return the sendTimeShow
     */
    public String getSendTimeShow() {
        return sendTimeShow;
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

}
