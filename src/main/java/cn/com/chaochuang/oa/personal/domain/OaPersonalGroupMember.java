/*
 * FileName:    OaPersonalGroupMember.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月26日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.personal.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.common.user.domain.SysUser;

/**
 * @author HM
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
public class OaPersonalGroupMember extends LongIdEntity {

    private static final long serialVersionUID = -2948922907389286200L;

    /** 群组编号 */
    private Long              groupId;

    /** 成员编号 */
    // private Long memberId;
    @ManyToOne
    @JoinColumn(name = "memberId")
    private SysUser           member;

    /**
     * @return the groupId
     */
    public Long getGroupId() {
        return groupId;
    }

    /**
     * @param groupId
     *            the groupId to set
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the member
     */
    public SysUser getMember() {
        return member;
    }

    /**
     * @param member
     *            the member to set
     */
    public void setMember(SysUser member) {
        this.member = member;
    }

}
