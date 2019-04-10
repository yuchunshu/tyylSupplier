package cn.com.chaochuang.common.desktop.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.common.user.domain.SysUser;

/**
 * @author hzr 2016年10月9日 用户代理记录（一般用于公文收发代理）
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
@Table(name = "sys_user_agent")
public class UserAgent extends LongIdEntity {

    /** 用户ID */
    private Long    userId;

    /** 创建时间 */
    private Date    createTime;

    /** 代理人 */
    private Long    agentId;

    @ManyToOne
    @JoinColumn(name = "agentId", insertable = false, updatable = false)
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

    public SysUser getAgentUser() {
        return agentUser;
    }

    public void setAgentUser(SysUser agentUser) {
        this.agentUser = agentUser;
    }

}
