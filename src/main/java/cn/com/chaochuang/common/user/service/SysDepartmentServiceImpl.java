/*
 * FileName:    SysDepartmentServiceImpl.java
 * Description:
 * Company:     
 * Copyright:    (c) 2014
 * History:     2014年7月18日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.user.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.bean.CommonTreeBean;
import cn.com.chaochuang.common.data.domain.ValidAble;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.reference.AdministrativeLevel;
import cn.com.chaochuang.common.reference.AdministrativeLevelConverter;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.bean.DepTreeBean;
import cn.com.chaochuang.common.user.bean.DepartmentEditBean;
import cn.com.chaochuang.common.user.bean.DepartmentSelectShowBean;
import cn.com.chaochuang.common.user.bean.DepartmentTreeShowBean;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.repository.SysDepartmentRepository;
import cn.com.chaochuang.oa.datachange.reference.DataChangeTable;
import cn.com.chaochuang.oa.datachange.reference.OperationType;
import cn.com.chaochuang.oa.datachange.service.DataChangeService;

/**
 * @author LaoZhiYong
 *
 */
@Service
@Transactional
public class SysDepartmentServiceImpl extends SimpleLongIdCrudRestService<SysDepartment> implements
                SysDepartmentService {

    @Autowired
    private SysDepartmentRepository repository;

    @Autowired
    private SysUserService          userService;

    @Autowired
    private DataChangeService       dataChangeService;

    @Override
    public SimpleDomainRepository<SysDepartment, Long> getRepository() {
        return repository;
    }

    @Override
    public List<DepTreeBean> getDepTree(Long parentId) {
        List<DepTreeBean> tree = null;
        List<SysDepartment> depts = null;
        if (parentId == null) {
            depts = this.repository.findByRootDepartment(ValidAble.VALID);
        } else {
            depts = this.repository.findByDeptParentAndValidOrderByOrderNumAsc(parentId, ValidAble.VALID);
        }
        if (depts != null && depts.size() > 0) {
            tree = new ArrayList<DepTreeBean>();
            for (SysDepartment dep : depts) {
                // 若parentId不为空，则判断获取的组织机构数据不包含parentId
                if (parentId != null && dep.getId().equals(parentId)) {
                    continue;
                }
                List<SysDepartment> supDep = this.repository.findByDeptParentAndValidOrderByOrderNumAsc(dep.getId(),
                                ValidAble.VALID);
                Boolean isLeaf = (supDep != null && supDep.size() > 0) ? false : true;
                tree.add(new DepTreeBean(dep.getId(), dep.getDeptName(), isLeaf));
            }
        }
        return tree;
    }

    public List<SysDepartment> getDeptRuleoutUnit() {
        return this.repository.findAllRuleoutUnit(1);
    }

    @Override
    public List<SysDepartment> findAllValid() {
        return this.repository.findByValid(ValidAble.VALID);
    }

    @Override
    public String delDept(Long id) {
        SysDepartment detp = this.repository.findOne(id);
        List<SysUser> uList = this.userService.findBydetpId(id);
        if (uList != null && uList.size() > 0) {
            return "该部门人数不为0， 不能删除!";
        }
        detp.setValid(ValidAble.INVALID);// 无效
        // 保存数据变动
        dataChangeService.saveDataChange("dept_id=" + id, DataChangeTable.组织结构, OperationType.修改);
        return null;
    }

    @Override
    public Long saveDept(DepartmentEditBean dept) {
        SysDepartment d = null;
        if (dept.getId() != null) {
            d = this.repository.findOne(dept.getId());
            // 保存数据变动
            dataChangeService.saveDataChange("dept_id=" + d.getId(), DataChangeTable.组织结构, OperationType.修改);
        } else {
            d = new SysDepartment();
            d.setValid(ValidAble.VALID);
            d.setUnitId(dept.getUnitId()); // 单位ID
            d.setDeptParent(dept.getDeptParent());

        }
        d.setDeptName(StringUtils.trim(dept.getDeptName()));
        d.setDeptCode(StringUtils.trimToNull(dept.getDeptCode()));
        d.setOrderNum(dept.getOrderNum());
        d.setDeptLevel(dept.getDeptLevel());
        this.repository.save(d);
        // 保存数据变动
        dataChangeService.saveDataChange("dept_id=" + d.getId(), DataChangeTable.组织结构, OperationType.新增);
        return d.getId();
    }

    @Override
    public List<SysDepartment> findByDeptName(String deptName) {
        return this.repository.findByDeptName(deptName);
    }

    @Override
    public List<SysDepartment> findAllExceptTopUnit() {
        SysDepartment topDept = (SysDepartment) UserTool.getInstance().getCurrentUserDepartment();
        if (topDept != null) {
            topDept = topDept.getUnitDept();
        }
        List<SysDepartment> dList = this.repository.findByValid(ValidAble.VALID);
        if (topDept != null && dList != null) {
            for (int i = 0; i < dList.size(); i++) {
                if (dList.get(i).getDeptName().equals(topDept.getDeptName())) {
                    dList.remove(i);
                    break;
                }
            }
        }
        return dList;
    }

    @Override
    public List<DepartmentTreeShowBean> getDepartmentTree(Long parentId) {
        List<DepartmentTreeShowBean> tree = null;
        List<SysDepartment> depts = null;
        if (parentId == null) {
            depts = this.repository.findByRootDepartment(ValidAble.VALID);
        } else {
            depts = this.repository.findByDeptParentAndValidOrderByOrderNumAsc(parentId, ValidAble.VALID);
        }

        if (depts != null && depts.size() > 0) {
            tree = new ArrayList<DepartmentTreeShowBean>();
            for (SysDepartment dept : depts) {
                // 若parentId不为空，则判断获取的组织机构数据不包含parentId
                if (parentId != null && dept.getId().equals(parentId)) {
                    continue;
                }
                tree.add(new DepartmentTreeShowBean(dept.getId(), dept.getDeptName(), "open", "icon-home", this
                                .findSubTreeByDeptParentId(dept.getId())));
            }
        }
        return tree;
    }

    @Override
    public List<DepartmentTreeShowBean> findSubTreeByDeptParentId(Long parentId) {
        List<DepartmentTreeShowBean> children = null;
        if (parentId != null) {
            List<SysDepartment> subList = this.repository.findByDeptParentAndValidOrderByOrderNumAsc(parentId,
                            ValidAble.VALID);
            if (subList != null && subList.size() > 0) {
                children = new ArrayList<DepartmentTreeShowBean>();
                for (SysDepartment d : subList) {
                    if (d.getParentDepartment() != null && d.getParentDepartment().getId() != d.getId()) {
                        // 递归
                        List<DepartmentTreeShowBean> childrenList = findSubTreeByDeptParentId(d.getId());
                        String status = "closed";
                        if (childrenList == null || childrenList.size() <= 0) {
                            status = "open";
                        }
                        children.add(new DepartmentTreeShowBean(d.getId(), d.getDeptName(), status, "icon-angle-right",
                                        childrenList));
                    }
                }
            }
        }
        return children;
    }

    @Override
    public List<DepartmentTreeShowBean> getDepartmentTreeOnlyThreeLayers(Long parentId) {
        List<DepartmentTreeShowBean> tree = null;
        List<SysDepartment> depts = null;
        if (parentId == null) {
            depts = this.repository.findByRootDepartment(ValidAble.VALID);
        } else {
            depts = this.repository.findByDeptParentAndValidOrderByOrderNumAsc(parentId, ValidAble.VALID);
        }

        if (depts != null && depts.size() > 0) {
            tree = new ArrayList<DepartmentTreeShowBean>();
            for (SysDepartment dept : depts) {
                // 若parentId不为空，则判断获取的组织机构数据不包含parentId
                if (parentId != null && dept.getId().equals(parentId)) {
                    continue;
                }

                // 第二层
                List<DepartmentTreeShowBean> secondList = this.getSubLeaf(dept.getId());
                if (secondList != null && secondList.size() > 0) {
                    for (DepartmentTreeShowBean sDep : secondList) {
                        // 第三层
                        List<DepartmentTreeShowBean> thirdList = this.getSubLeaf(sDep.getId());
                        sDep.setChildren(thirdList);
                    }
                }

                tree.add(new DepartmentTreeShowBean(dept.getId(), dept.getDeptName(), "open", "icon-home", secondList));
            }
        }
        return tree;
    }

    @Override
    public List<DepartmentTreeShowBean> getSubLeaf(Long parentId) {
        List<DepartmentTreeShowBean> children = null;
        if (parentId != null) {
            List<SysDepartment> subList = this.repository.findByDeptParentAndValidOrderByOrderNumAsc(parentId,
                            ValidAble.VALID);
            if (subList != null && subList.size() > 0) {
                children = new ArrayList<DepartmentTreeShowBean>();
                for (SysDepartment d : subList) {
                    if (d.getParentDepartment() != null && d.getParentDepartment().getId() != d.getId()) {
                        children.add(new DepartmentTreeShowBean(d.getId(), d.getDeptName(), "open", "", null));
                    }
                }
            }
        }
        return children;
    }

    @Override
    public List<SysDepartment> getSubDeps(Long parentId) {
        return this.repository.findByDeptParentAndValidOrderByOrderNumAsc(parentId, ValidAble.VALID);
    }

    @Override
    public List<DepartmentSelectShowBean> getSubDepSelectBeans(Long parentId, String containDeps) {
        List<SysDepartment> deps = this.getSubDeps(parentId);
        List<DepartmentSelectShowBean> returnList = new ArrayList<DepartmentSelectShowBean>();
        if (deps != null && deps.size() > 0) {
            String depArr[] = null;
            if (StringUtils.isNotBlank(containDeps)) {
                depArr = containDeps.split(",");
            }
            for (SysDepartment d : deps) {
                DepartmentSelectShowBean bean = new DepartmentSelectShowBean();
                bean.setId(d.getId());
                bean.setDeptName(d.getDeptName());
                SysDepartment parent = d.getParentDepartment();
                if (parent != null) {
                    bean.setParentDepId(parent.getId());
                    bean.setParentDepName(parent.getDeptName());
                }

                if (depArr != null && depArr.length > 0) {
                    for (String depId : depArr) {
                        if (Long.valueOf(depId) == d.getId()) {
                            bean.setChecked(true);
                            break;
                        }
                    }
                }

                returnList.add(bean);
            }
        }
        return returnList;
    }

    @Override
    public List<CommonTreeBean> getTreeRootBean() {
        List<SysDepartment> depts = this.repository.findByRootDepartment(ValidAble.VALID);
        List<CommonTreeBean> returnList = new ArrayList<CommonTreeBean>();
        if (depts != null && depts.size() > 0) {
            for (SysDepartment d : depts) {
                CommonTreeBean bean = new CommonTreeBean();
                bean.setState("closed");
                bean.setId(d.getId().toString());
                bean.setText(d.getDeptName());
                returnList.add(bean);
            }
        }
        return returnList;
    }

    @Override
    public List<CommonTreeBean> getChildrenBean(Long id) {
        List<SysDepartment> depts = this.repository.findByDeptParentAndValidOrderByOrderNumAsc(id, ValidAble.VALID);
        List<CommonTreeBean> returnList = new ArrayList<CommonTreeBean>();
        if (depts != null && depts.size() > 0) {
            for (SysDepartment d : depts) {
                if (!d.getId().equals(id)) {
                    CommonTreeBean bean = new CommonTreeBean();
                    List<SysDepartment> children = this.repository.findByDeptParentAndValidOrderByOrderNumAsc(
                                    d.getId(), ValidAble.VALID);
                    if (children == null || children.size() == 0) {
                        bean.setState("open");
                    } else {
                        bean.setState("closed");
                    }
                    bean.setId(d.getId().toString());
                    bean.setText(d.getDeptName());
                    returnList.add(bean);
                }
            }
        }
        return returnList;
    }

    @Override
    public List<SysDepartment> findAllValidOrderByOrderNumAsc() {
        return this.repository.findByValidOrderByOrderNumAsc(ValidAble.VALID);
    }

    @Override
    public List<CommonTreeBean> getTreeRootBeanByLevel(String level) {
    	
    	//根据当前部门数据只能这样处理，以后部门数据结构有变动再调整
    	//区级、市级部门树首单位都是广西食品药品监督管理局和广西壮族自治区食品安全委员会，县级部门树 首单位 为14个地级市
    	List<SysDepartment> depts = new ArrayList<SysDepartment>();
    	if(AdministrativeLevel.区级.getKey().equals(level)){
    		depts = this.repository.findByRootDepartment(ValidAble.VALID);
    	}else if(AdministrativeLevel.市级.getKey().equals(level)){
    		depts = this.repository.findByRootDepartment(ValidAble.VALID);
    	}else if(AdministrativeLevel.县级.getKey().equals(level)){
    		depts = this.repository.findByRootDepartmentByLevel(ValidAble.VALID,AdministrativeLevel.市级);
    	}
        
        List<CommonTreeBean> returnList = new ArrayList<CommonTreeBean>();
        if (depts != null && depts.size() > 0) {
            for (SysDepartment d : depts) {
            	//过滤没有子部门的部门
            	List<SysDepartment> children = this.repository.findByDeptParentAndValidAndDeptLevelOrderByOrderNumAsc(
                        d.getId(), ValidAble.VALID,new AdministrativeLevelConverter().convertToEntityAttribute(level));
		        if (children == null || children.size() == 0) {
		            continue;
		        }
                CommonTreeBean bean = new CommonTreeBean();
                bean.setState("closed");
                bean.setId(d.getId().toString());
                bean.setText(d.getDeptName());
                returnList.add(bean);
            }
        }
        return returnList;
    }
    
	@Override
	public List<CommonTreeBean> getChildrenBeanByLevel(Long id, String level) {

        List<SysDepartment> depts = this.repository.findByDeptParentAndValidAndDeptLevelOrderByOrderNumAsc(id, ValidAble.VALID,new AdministrativeLevelConverter().convertToEntityAttribute(level));
        List<CommonTreeBean> returnList = new ArrayList<CommonTreeBean>();
        if (depts != null && depts.size() > 0) {
            for (SysDepartment d : depts) {
                if (!d.getId().equals(id)) {
                    CommonTreeBean bean = new CommonTreeBean();
                    List<SysDepartment> children = this.repository.findByDeptParentAndValidAndDeptLevelOrderByOrderNumAsc(
                                    d.getId(), ValidAble.VALID,new AdministrativeLevelConverter().convertToEntityAttribute(level));
                    if (children == null || children.size() == 0) {
                        bean.setState("open");
                    } else {
                        bean.setState("closed");
                    }
                    bean.setId(d.getId().toString());
                    bean.setText(d.getDeptName());
                    returnList.add(bean);
                }
            }
        }
        return returnList;
    
	}

}
