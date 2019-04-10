/*
 * FileName:    ReceiveFileEditBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.docread.bean;

import java.util.Date;

import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.doc.docread.reference.DocProperty;
import cn.com.chaochuang.doc.docread.reference.DocReadStatus;
import cn.com.chaochuang.doc.event.reference.DocCate;

/**
 * @author HeYunTao
 *
 */
public class DocReadEditBean {

    private Long          id;
    private String        docFileId;
    private DocProperty   docPropertyId;
    private DocCate       docCate;
    private Long          sendMan;
    private String        sendManName;
    private Long          sendDeptId;
    private Date          sendTime;
    private String        docTitle;
    private DocReadStatus status;
    private String        readMan;
    private String        readDept;
    private String        docNumber;
    private String        remark;
    private String        attach;
    private YesNo         toSigned;
    private String        sendUnit;

    public String getSendUnit() {
        return sendUnit;
    }

    public void setSendUnit(String sendUnit) {
        this.sendUnit = sendUnit;
    }

    public YesNo getToSigned() {
        return toSigned;
    }

    public void setToSigned(YesNo toSigned) {
        this.toSigned = toSigned;
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
     * @return the attach
     */
    public String getAttach() {
        return attach;
    }

    /**
     * @param attach
     *            the attach to set
     */
    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(String docFileId) {
        this.docFileId = docFileId;
    }

}
