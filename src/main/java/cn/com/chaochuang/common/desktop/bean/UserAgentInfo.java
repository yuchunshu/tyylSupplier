/*
 * FileName:    UserAgentEdit.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年10月9日 (pangj) 1.0 Create
 */

package cn.com.chaochuang.common.desktop.bean;

import java.util.Date;

import cn.com.chaochuang.common.user.domain.SysUser;

/**
 * @author pangj
 *
 */
public class UserAgentInfo {

    private Long    id;

    /** 用户ID */
    private Long    userId;

    /** 创建时间 */
    private Date    createTime;

    /** 代理人 */
    private Long    agentId;

    private SysUser agentUser;

    /** 代理开始时间 */
    private Date    beginTime;

    /** 代理结束时间 */
    private Date    endTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SysUser getAgentUser() {
        return agentUser;
    }

    public void setAgentUser(SysUser agentUser) {
        this.agentUser = agentUser;
    }

}
