/*
 * FileName:    GroupEditBean.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年2月23日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.personal.bean;

/**
 * @author HM
 *
 */
public class GroupEditBean {

    /** id */
    private Long   id;

    /** 群组名称 */
    private String groupName;

    /** 排序号 */
    private Long   orderNum;

    /** 群组成员IDs */
    private String groupMemberIds;

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
     * @return the groupMemberIds
     */
    public String getGroupMemberIds() {
        return groupMemberIds;
    }

    /**
     * @param groupMemberIds
     *            the groupMemberIds to set
     */
    public void setGroupMemberIds(String groupMemberIds) {
        this.groupMemberIds = groupMemberIds;
    }
}
