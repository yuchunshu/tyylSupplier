/*
 * FileName:    SelectNodesInfo.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年4月13日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.webservice.bean;

import java.util.List;

/**
 * @author huangwq
 *
 */
public class SelectNodesInfo {

    /** 节点名 */
    private String                nodeName;
    /** 提交类型，直接提交或选人提交或选部门后再选人 */
    private String                submitType;
    /** 当前用户的祖先部门id */
    private Long                  ancestorDep;
    /** 每一个节点下的可选部门集合 type=selectDeptsAndUsers */
    private List<DeptNodesInfo>   deptNodesList;
    /** 选择个数限制 */
    private Integer               limitNum;
    /** 每一个节点下的可选部门 type=selectDepts */
    private List<SelectDeptInfo>  departmentsList;
    /** 每一个节点下的可选人员集合 type=selectUsers */
    private List<NameAndIdEntity> usersList;
    /** 直接提交给的人员 type=toUsers 使用,分割 */
    private NameAndIdEntity       userInfo;
    /** 直接提交给的部门 type=toDepts 使用,分割 */
    private String                deptsId;
    /** 下一节点 */
    private String                nextNodeId;

    /**
     * @return the limitNum
     */
    public Integer getLimitNum() {
        return limitNum;
    }

    /**
     * @param limitNum
     *            the limitNum to set
     */
    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    /**
     * @return the departmentsList
     */
    public List<SelectDeptInfo> getDepartmentsList() {
        return departmentsList;
    }

    /**
     * @param departmentsList
     *            the departmentsList to set
     */
    public void setDepartmentsList(List<SelectDeptInfo> departmentsList) {
        this.departmentsList = departmentsList;
    }

    /**
     * @return the deptsId
     */
    public String getDeptsId() {
        return deptsId;
    }

    /**
     * @param deptsId
     *            the deptsId to set
     */
    public void setDeptsId(String deptsId) {
        this.deptsId = deptsId;
    }

    /**
     * @return the nodeName
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * @param nodeName
     *            the nodeName to set
     */
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    /**
     * @return the deptNodesList
     */
    public List<DeptNodesInfo> getDeptNodesList() {
        return deptNodesList;
    }

    /**
     * @param deptNodesList
     *            the deptNodesList to set
     */
    public void setDeptNodesList(List<DeptNodesInfo> deptNodesList) {
        this.deptNodesList = deptNodesList;
    }

    /**
     * @return the usersList
     */
    public List<NameAndIdEntity> getUsersList() {
        return usersList;
    }

    /**
     * @param usersList
     *            the usersList to set
     */
    public void setUsersList(List<NameAndIdEntity> usersList) {
        this.usersList = usersList;
    }

    /**
     * @return the submitType
     */
    public String getSubmitType() {
        return submitType;
    }

    /**
     * @param submitType
     *            the submitType to set
     */
    public void setSubmitType(String submitType) {
        this.submitType = submitType;
    }

    /**
     * @return the nextNodeId
     */
    public String getNextNodeId() {
        return nextNodeId;
    }

    /**
     * @param nextNodeId
     *            the nextNodeId to set
     */
    public void setNextNodeId(String nextNodeId) {
        this.nextNodeId = nextNodeId;
    }

    /**
     * @return the userInfo
     */
    public NameAndIdEntity getUserInfo() {
        return userInfo;
    }

    /**
     * @param userInfo
     *            the userInfo to set
     */
    public void setUserInfo(NameAndIdEntity userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * @return the ancestorDep
     */
    public Long getAncestorDep() {
        return ancestorDep;
    }

    /**
     * @param ancestorDep
     *            the ancestorDep to set
     */
    public void setAncestorDep(Long ancestorDep) {
        this.ancestorDep = ancestorDep;
    }

}
