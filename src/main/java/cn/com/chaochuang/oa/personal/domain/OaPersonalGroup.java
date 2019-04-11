/*
 * FileName:    OaPersonalGroup.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月26日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.personal.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.common.user.domain.SysUser;

/**
 * @author HM
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "group_id")) })
public class OaPersonalGroup extends LongIdEntity {

    private static final long          serialVersionUID = 908843662139702040L;

    /** 群组名称 */
    private String                     groupName;

    /** 所属用户 */
    @ManyToOne
    @JoinColumn(name = "userId")
    private SysUser                    user;

    /** 创建日期 */
    private Date                       createDate;

    /** 排序号 */
    private Long                       orderNum;

    /** 群组成员 */
    @OneToMany
    @JoinColumn(name = "groupId")
    private Set<OaPersonalGroupMember> memberSet;

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName
     *            the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return the user
     */
    public SysUser getUser() {
        return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(SysUser user) {
        this.user = user;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     *            the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the orderNum
     */
    public Long getOrderNum() {
        return orderNum;
    }

    /**
     * @param orderNum
     *            the orderNum to set
     */
    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * @return the memberSet
     */
    public Set<OaPersonalGroupMember> getMemberSet() {
        return memberSet;
    }

    /**
     * @param memberSet
     *            the memberSet to set
     */
    public void setMemberSet(Set<OaPersonalGroupMember> memberSet) {
        this.memberSet = memberSet;
    }
}
