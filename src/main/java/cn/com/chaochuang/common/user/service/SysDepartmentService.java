/*
 * FileName:    SysDepartmentService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2014
 * History:     2014年7月18日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.user.service;

import java.util.List;

import cn.com.chaochuang.common.bean.CommonTreeBean;
import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.user.bean.DepTreeBean;
import cn.com.chaochuang.common.user.bean.DepartmentEditBean;
import cn.com.chaochuang.common.user.bean.DepartmentSelectShowBean;
import cn.com.chaochuang.common.user.bean.DepartmentTreeShowBean;
import cn.com.chaochuang.common.user.domain.SysDepartment;

/**
 * @author LaoZhiYong
 *
 */
public interface SysDepartmentService extends CrudRestService<SysDepartment, Long> {
    public List<DepTreeBean> getDepTree(Long parentId);

    public List<SysDepartment> getDeptRuleoutUnit();

    public List<SysDepartment> findAllValid();

    public List<SysDepartment> findAllValidOrderByOrderNumAsc();

    public String delDept(Long id);

    public Long saveDept(DepartmentEditBean dept);

    public List<SysDepartment> findByDeptName(String deptName);

    public List<SysDepartment> findAllExceptTopUnit();

    /**
     * 查询部门树(包括所有节点)
     *
     * @param parentId
     * @return
     */
    public List<DepartmentTreeShowBean> getDepartmentTree(Long parentId);

    /**
     * 按父ID查询子所有子节点
     *
     * @param parentId
     * @return list
     */
    public List<DepartmentTreeShowBean> findSubTreeByDeptParentId(Long parentId);

    /**
     * 查询部门树（三层）
     *
     * @param parentId
     * @return
     */
    public List<DepartmentTreeShowBean> getDepartmentTreeOnlyThreeLayers(Long parentId);

    /**
     * 获取下一层树节点
     *
     * @param parentId
     * @return
     */
    public List<DepartmentTreeShowBean> getSubLeaf(Long parentId);

    /**
     * 获取下一层部门列表
     *
     * @param parentId
     * @return
     */
    public List<SysDepartment> getSubDeps(Long parentId);

    /**
     * 科室邮件部门列表
     *
     * @param parentId
     * @param containDeps
     * @return
     */
    public List<DepartmentSelectShowBean> getSubDepSelectBeans(Long parentId, String containDeps);

    /**
     * 获取根节点
     *
     * @return
     */
    public List<CommonTreeBean> getTreeRootBean();

    /**
     * 获取子节点(一层)
     *
     * @return
     */
    public List<CommonTreeBean> getChildrenBean(Long id);
    
    /**
     * 根据部门级别获取根节点
     *
     * @return
     */
    public List<CommonTreeBean> getTreeRootBeanByLevel(String level);
    
    /**
     * 根据部门级别获取子节点(一层)
     *
     * @return
     */
    public List<CommonTreeBean> getChildrenBeanByLevel(Long id,String level);

    List<SysDepartment> getAllChildrens(Long deptId);
}
