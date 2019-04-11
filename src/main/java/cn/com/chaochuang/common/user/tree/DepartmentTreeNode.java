/*
 * FileName:    DepartmentTreeNode.java
 * Description:
 * Company:     
 * Copyright:    (c) 2014
 * History:     2014年7月18日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.user.tree;

import java.util.ArrayList;
import java.util.List;

import cn.com.chaochuang.common.user.domain.SysDepartment;

/**
 * @author LaoZhiYong
 *
 */
public class DepartmentTreeNode {

    /** 本结点对应的DepartmentTree. */
    public SysDepartment department;
    /** 子结点列表. */
    private List         children = new ArrayList();

    /**
     * 构造方法.
     *
     * @param department
     *            DepartmentTree实例
     */
    public DepartmentTreeNode(SysDepartment department) {
        this.department = department;
    }

    /**
     * 构造方法.
     *
     * @param department
     *            DepartmentTree实例
     * @param indent
     *            部门所在层次
     */
    public DepartmentTreeNode(SysDepartment department, Integer indent) {
        this.department = department;
    }

    /**
     * 增加一个子结点.
     *
     * @param child
     *            DepartmentTreeNode实例
     */
    public void addChild(DepartmentTreeNode child) {
        this.children.add(child);
    }

    /**
     * 设置子部门列表
     *
     * @param treeNodeList
     *            节点列表
     */
    public void setChild(List treeNodeList) {
        this.children = treeNodeList;
    }

    /**
     * @see com.spower.basesystem.tree.command.ITreeNode#getChildren()
     */
    public List getChildren() {
        return this.children;
    }

    /**
     * @see com.spower.basesystem.tree.command.ITreeNode#getNodeKey()
     */
    public String getNodeKey() {
        return this.department.getId().toString();
    }

    /**
     * @see com.spower.basesystem.tree.command.ITreeNode#getNodeName()
     */
    public String getNodeName() {
        return this.department.getDeptName();
    }

    /**
     * @see com.spower.basesystem.tree.command.ITreeNode#isLeaf()
     */
    public boolean isLeaf() {
        return this.children.isEmpty();
    }

    /**
     * @return 返回 department。
     */
    public SysDepartment getDepartment() {
        return department;
    }
}
