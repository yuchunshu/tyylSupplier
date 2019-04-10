package cn.com.chaochuang.mobile.bean;

import cn.com.chaochuang.common.reference.SignStatus;
import cn.com.chaochuang.common.reference.SignStatusConverter;

import javax.persistence.*;

/**
 * @author ZL
 * @category 移动端接收转阅显示类
 */
public class AppDocReadReceivedBean {

    /**
	 *
	 */
    private String            id;
    private String            docReadId;
    private String            receiverName;
    @Convert(converter = SignStatusConverter.class)
    private SignStatus        status;
    private String            readTime;
    private String            opinion;
    private String            opinionTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocReadId() {
        return docReadId;
    }

    public void setDocReadId(String docReadId) {
        this.docReadId = docReadId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public SignStatus getStatus() {
        return status;
    }

    public void setStatus(SignStatus status) {
        this.status = status;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getOpinionTime() {
        return opinionTime;
    }

    public void setOpinionTime(String opinionTime) {
        this.opinionTime = opinionTime;
    }
}