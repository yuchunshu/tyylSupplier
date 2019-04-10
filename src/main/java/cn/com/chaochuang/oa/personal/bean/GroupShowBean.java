/*
 * FileName:    GroupShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年2月23日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.personal.bean;


/**
 * @author HM
 *
 */
public class GroupShowBean {

    /** id */
    private Long   id;

    /** 群组名称 */
    private String groupName;

    /** 创建人 */
    private String userName;

    /** 创建日期 */
    // private Date createDate;
    private String createDateShow;

    /** 排序号 */
    private Long   orderNum;

    /** 群组成员名称 */
    private String groupMembers;

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
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the createDateShow
     */
    public String getCreateDateShow() {
        return createDateShow;
    }

    /**
     * @param createDateShow
     *            the createDateShow to set
     */
    public void setCreateDateShow(String createDateShow) {
        this.createDateShow = createDateShow;
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
     * @return the groupMembers
     */
    public String getGroupMembers() {
        return groupMembers;
    }

    /**
     * @param groupMembers
     *            the groupMembers to set
     */
    public void setGroupMembers(String groupMembers) {
        this.groupMembers = groupMembers;
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
