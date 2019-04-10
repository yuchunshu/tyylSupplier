/*
 * FileName:    MeetingAttend.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年10月28日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.webservice.bean;

import cn.com.chaochuang.common.reference.SignStatus;

/**
 * @author huangwq
 *
 */
public class MeetingAttend {

    private Long       meetingId;

    /** 会议参与者 */
    private Long       attendees;
    private SignStatus signStatus;

    /** 原会议参与者表流水号 */
    private Long       rmAttendId;

    private Long       rmUserid;

    /**
     * @return the meetingId
     */
    public Long getMeetingId() {
        return meetingId;
    }

    /**
     * @param meetingId
     *            the meetingId to set
     */
    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    /**
     * @return the attendees
     */
    public Long getAttendees() {
        return attendees;
    }

    /**
     * @param attendees
     *            the attendees to set
     */
    public void setAttendees(Long attendees) {
        this.attendees = attendees;
    }

    /**
     * @return the signStatus
     */
    public SignStatus getSignStatus() {
        return signStatus;
    }

    /**
     * @param signStatus
     *            the signStatus to set
     */
    public void setSignStatus(SignStatus signStatus) {
        this.signStatus = signStatus;
    }

    /**
     * @return the rmAttendId
     */
    public Long getRmAttendId() {
        return rmAttendId;
    }

    /**
     * @param rmAttendId
     *            the rmAttendId to set
     */
    public void setRmAttendId(Long rmAttendId) {
        this.rmAttendId = rmAttendId;
    }

    /**
     * @return the rmUserid
     */
    public Long getRmUserid() {
        return rmUserid;
    }

    /**
     * @param rmUserid
     *            the rmUserid to set
     */
    public void setRmUserid(Long rmUserid) {
        this.rmUserid = rmUserid;
    }

}
