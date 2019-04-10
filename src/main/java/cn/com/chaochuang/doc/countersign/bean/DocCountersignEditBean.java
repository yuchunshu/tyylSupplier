package cn.com.chaochuang.doc.countersign.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.dozer.Mapping;

import cn.com.chaochuang.common.reference.SignStatus;


/**
 * @author hzr 2016年5月16日
 *
 */
public class DocCountersignEditBean {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private Long             id;
    // 发送人

    private Long             sendMan;
    @Mapping("sendUser.userName")
    private String           sendManName;
    // 公文标题
    private String           title;
    // 发送时间
    private Date             sendTime;

    private String           sendTimeShow;
    // 会签人
    private Long             cosignMan;
    // 会签时间
    private Date             cosignTime;
    // 文件id
    private String           fileId;
    // 会签意见
    private String           opinion;
    // 状态 0未签收, 1已签收, 2已回复;
    private SignStatus       status;
    // 文件类型 0收文1发文
    private String           fileType;
    // 任务id
    private String           taskId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
        if (this.sendTime != null) {
            this.sendTimeShow = sdf.format(this.sendTime);
        }
    }

    public Long getCosignMan() {
        return cosignMan;
    }

    public void setCosignMan(Long cosignMan) {
        this.cosignMan = cosignMan;
    }

    public Date getCosignTime() {
        return cosignTime;
    }

    public void setCosignTime(Date cosignTime) {
        this.cosignTime = cosignTime;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public SignStatus getStatus() {
        return status;
    }

    public void setStatus(SignStatus status) {
        this.status = status;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * @return the sendTimeShow
     */
    public String getSendTimeShow() {
        return sendTimeShow;
    }

    /**
     * @param sendTimeShow
     *            the sendTimeShow to set
     */
    public void setSendTimeShow(String sendTimeShow) {
        this.sendTimeShow = sendTimeShow;
    }

}
