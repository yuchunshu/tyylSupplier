package cn.com.chaochuang.doc.docread.bean;

import java.util.Date;

import cn.com.chaochuang.common.reference.SignStatus;


/**
 * @author HeYunTao
 *
 */

public class ReceivesReadEditBean {

    private Long              id;
    private Long              docReadId;
    private String            opinion;
    private Date              opinionTime;
    private SignStatus        status;

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

    public SignStatus getStatus() {
        return status;
    }

    public void setStatus(SignStatus status) {
        this.status = status;
    }


}