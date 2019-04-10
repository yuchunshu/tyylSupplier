package cn.com.chaochuang.webservice.bean;

import java.util.Date;

import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.common.reference.StatusFlag;

public class MeetingInfo {
    /** 会议名称 */
    private String     meetingName;
    /** 入录时间 */
    private Date       meetingDate;
    /** 会议地点 */
    private String     meetingPlace;
    /** 会议内容 */
    private String     content;
    /** 会议组织者 */
    private String     organiger;
    /** 发布人 */
    private Long       creatorId;
    /** 会议参与者 */
    private String     attentObj;
    /** （0：暂存；1：发布;2删除） */
    private StatusFlag rmMeetingState;

    private AttachFlag attachFlag;

    /** 远程会议id */
    private Long       rmMeetingId;

    /**
     * @return the meetingName
     */
    public String getMeetingName() {
        return meetingName;
    }

    /**
     * @param meetingName
     *            the meetingName to set
     */
    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    /**
     * @return the meetingDate
     */
    public Date getMeetingDate() {
        return meetingDate;
    }

    /**
     * @param meetingDate
     *            the meetingDate to set
     */
    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    /**
     * @return the meetingPlace
     */
    public String getMeetingPlace() {
        return meetingPlace;
    }

    /**
     * @param meetingPlace
     *            the meetingPlace to set
     */
    public void setMeetingPlace(String meetingPlace) {
        this.meetingPlace = meetingPlace;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the organiger
     */
    public String getOrganiger() {
        return organiger;
    }

    /**
     * @param organiger
     *            the organiger to set
     */
    public void setOrganiger(String organiger) {
        this.organiger = organiger;
    }

    /**
     * @return the attentObj
     */
    public String getAttentObj() {
        return attentObj;
    }

    /**
     * @param attentObj
     *            the attentObj to set
     */
    public void setAttentObj(String attentObj) {
        this.attentObj = attentObj;
    }

    /**
     * @return the rmMeetingState
     */
    public StatusFlag getRmMeetingState() {
        return rmMeetingState;
    }

    /**
     * @param rmMeetingState
     *            the rmMeetingState to set
     */
    public void setRmMeetingState(StatusFlag rmMeetingState) {
        this.rmMeetingState = rmMeetingState;
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
     * @return the rmMeetingId
     */
    public Long getRmMeetingId() {
        return rmMeetingId;
    }

    /**
     * @param rmMeetingId
     *            the rmMeetingId to set
     */
    public void setRmMeetingId(Long rmMeetingId) {
        this.rmMeetingId = rmMeetingId;
    }

    /**
     * @return the creatorId
     */
    public Long getCreatorId() {
        return creatorId;
    }

    /**
     * @param creatorId
     *            the creatorId to set
     */
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

}
