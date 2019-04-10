/*
 * FileName:    PersonalGroupSelectShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月7日 (HM) 1.0 Create
 */

package cn.com.chaochuang.common.select.bean;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import cn.com.chaochuang.oa.personal.domain.OaPersonalGroupMember;

/**
 * @author HM
 *
 */
public class PersonalGroupSelectShowBean {

    private Long                       id;

    private String                     groupName;

    private Set<OaPersonalGroupMember> memberSet;

    private String                     memberNames;

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
     * @return the memberSet
     */
    public Set<OaPersonalGroupMember> getMemberSet() {
        return null;
    }

    /**
     * @param memberSet
     *            the memberSet to set
     */
    public void setMemberSet(Set<OaPersonalGroupMember> memberSet) {
        this.memberSet = memberSet;
        if (this.memberSet != null && this.memberSet.size() > 0) {
            this.memberNames = "";
            for (OaPersonalGroupMember m : memberSet) {
                this.memberNames += m.getMember().getUserName() + ",";
            }
            if (StringUtils.isNotBlank(this.memberNames)) {
                this.memberNames = this.memberNames.substring(0, this.memberNames.length() - 1);
            }
        }
    }

    /**
     * @return the memberNames
     */
    public String getMemberNames() {
        return memberNames;
    }

}
