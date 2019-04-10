/*
 * FileName:    NodeReceiveSelectBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年11月29日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.workflow.def.bean;

import java.util.List;
import java.util.Map;

import cn.com.chaochuang.common.select.bean.DepartmentSelectBean;
import cn.com.chaochuang.common.select.bean.UserSelectShowBean;

/**
 * @author LJX
 *
 */
public class NodeDeptSelectBean {
    /** 接收标识 0:人,1:部门 */
    private String                              receiveFlag;
    /** 用户 */
    private List<UserSelectShowBean>            users;
    /** 部门 */
    private List<DepartmentSelectBean>          deps;
    /** 部门对应用户 */
    private Map<Long, List<UserSelectShowBean>> depOfUsers;
    /** 历史选择 */
    private List<UserSelectShowBean>            history;

    /**
     * @return the receiveFlag
     */
    public String getReceiveFlag() {
        return receiveFlag;
    }

    /**
     * @param receiveFlag
     *            the receiveFlag to set
     */
    public void setReceiveFlag(String receiveFlag) {
        this.receiveFlag = receiveFlag;
    }

    /**
     * @return the users
     */
    public List<UserSelectShowBean> getUsers() {
        return users;
    }

    /**
     * @param users
     *            the users to set
     */
    public void setUsers(List<UserSelectShowBean> users) {
        this.users = users;
    }

    /**
     * @return the deps
     */
    public List<DepartmentSelectBean> getDeps() {
        return deps;
    }

    /**
     * @param deps
     *            the deps to set
     */
    public void setDeps(List<DepartmentSelectBean> deps) {
        this.deps = deps;
    }

    /**
     * @return the depOfUsers
     */
    public Map<Long, List<UserSelectShowBean>> getDepOfUsers() {
        return depOfUsers;
    }

    /**
     * @param depOfUsers
     *            the depOfUsers to set
     */
    public void setDepOfUsers(Map<Long, List<UserSelectShowBean>> depOfUsers) {
        this.depOfUsers = depOfUsers;
    }

    /**
     * @return the history
     */
    public List<UserSelectShowBean> getHistory() {
        return history;
    }

    /**
     * @param history
     *            the history to set
     */
    public void setHistory(List<UserSelectShowBean> history) {
        this.history = history;
    }

}
