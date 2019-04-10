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
import cn.com.chaochuang.common.reference.SignStatus;
import cn.com.chaochuang.doc.docread.reference.DocProperty;
import cn.com.chaochuang.doc.event.reference.DocCate;

/**
 * @author HeYunTao
 *
 */
public class DocreadRecShowBean {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private Long             id;

    @Mapping("docread.id")
    private Long             docreadId;

    @Mapping("docread.docPropertyId")
    private DocProperty      docPropertyId;

    @Mapping("docread.docCate")
    private DocCate          docCate;

    @Mapping("docread.sendManName")
    private String           sendManName;

    @Mapping("docread.sendDept.deptName")
    private String           sendDepName;

    @Mapping("docread.sendTime")
    private Date             sendTime;

    private String           sendTimeShow;

    @Mapping("docread.docTitle")
    private String           docTitle;

    private SignStatus       status;

    @Mapping("docread.docNumber")
    private String           docNumber;

    @Mapping("docread.attachFlag")
    private AttachFlag       attachFlag;

    /**
     * @return the sdf
     */
    public SimpleDateFormat getSdf() {
        return sdf;
    }

    /**
     * @param sdf
     *            the sdf to set
     */
    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

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
     * @return the docreadId
     */
    public Long getDocreadId() {
        return docreadId;
    }

    /**
     * @param docreadId
     *            the docreadId to set
     */
    public void setDocreadId(Long docreadId) {
        this.docreadId = docreadId;
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
        if (this.sendTime != null) {
            this.sendTimeShow = sdf.format(this.sendTime);
        }
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

    public SignStatus getStatus() {
        return status;
    }

    public void setStatus(SignStatus status) {
        this.status = status;
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

    /**
     * @return the sendTimeShow
     */
    public String getSendTimeShow() {
        if (this.sendTime != null) {
            this.sendTimeShow = sdf.format(this.sendTime);
        }
        return sendTimeShow;
    }
}
