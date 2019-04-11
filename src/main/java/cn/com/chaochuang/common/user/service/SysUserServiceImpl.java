package cn.com.chaochuang.common.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.domain.ValidAble;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.IDictionaryBuilder;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.syspower.bean.DepAndUserTreeBean;
import cn.com.chaochuang.common.syspower.domain.SysRole;
import cn.com.chaochuang.common.syspower.service.SysRoleService;
import cn.com.chaochuang.common.user.bean.UserEditBean;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.repository.SysDepartmentRepository;
import cn.com.chaochuang.common.user.repository.SysUserRepository;
import cn.com.chaochuang.common.util.HashUtil;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.oa.datachange.reference.DataChangeTable;
import cn.com.chaochuang.oa.datachange.reference.OperationType;
import cn.com.chaochuang.oa.datachange.service.DataChangeService;

@Service
@Transactional
public class SysUserServiceImpl extends SimpleLongIdCrudRestService<SysUser>
                implements SysUserService, IDictionaryBuilder {

    @Autowired
    private SysUserRepository       repository;

    @Autowired
    private SysDepartmentRepository deptRepository;

    @Value("${user.initpassword}")
    private String                  initPasswordPlainText;

    @Value("${security.password.passwordTimeoutDays}")
    private String                  passwordTimeoutDays;

    @Autowired
    private LogService              logService;

    @Autowired
    private SysRoleService          roleService;

    @Value(value = "${super.admin.user}")
    private Long                    superUserId;

    @PersistenceContext
    private EntityManager           em;

    @Autowired
    private DataChangeService       dataChangeService;

    /** 树节点状态 ： 关闭 */
    private final String            TREE_STATUS_CLOSED = "closed";

    /** 树节点状态 ： 打开 */
    private final String            TREE_STATUS_OPEN   = "open";

    /** 树节点部门id前缀 */
    private final String            TREE_DEP_ID_PREFIX = "dep_";

    @Override
    public SimpleDomainRepository<SysUser, Long> getRepository() {
        return repository;
    }

    @Override
    public Collection<SysUser> findUsers(Long[] ids) {
        return repository.findAll(Arrays.asList(ids));
    }

    @Override
    public List<SysUser> loadAllActiveUsers() {
        return repository.findByValid(SysUser.VALID);
    }

    @Override
    public List<SysUser> findBydetpId(Long deptId) {
        if (deptId != null) {
            return this.repository.findByDepartmentIdAndValidOrderBySortAsc(deptId, ValidAble.VALID);
        }
        return null;
    }

    @Override
    public Long saveUser(UserEditBean u, HttpServletRequest request) {
        SysUser user = null;

        if (u.getId() != null) {
            user = this.repository.findOne(u.getId());
            // 超级管理员的信息只能自己修改，别人不能修改
            if (user.getId().equals(this.superUserId)
                            && !UserTool.getInstance().getCurrentUserId().equals(String.valueOf(this.superUserId))) {
                return user.getId();
            }
        }

        if (user == null) {
            user = new SysUser();
            user.setPassword(HashUtil.md5Text(initPasswordPlainText));
            user.setValid(ValidAble.VALID);// 有效
            user.setRegDate(new Date());
            user.setAccountLocked(false);
            user.setLoginFailCount(0);
        }

        user.setAccount(StringUtils.trim(u.getAccount()));
        user.setUserName(StringUtils.trim(u.getUserName()));
        user.setMobile(u.getMobile());
        user.setEmail(u.getEmail());
        user.setSort(u.getSort());
        user.setMiji(u.getMiji());
        user.setDutyId(u.getDutyId());
        // user.setTel(u.getTel());
        // user.setDuty(u.getDuty());
        // user.setIdCard(u.getIdCard());
        user.setDeptId(u.getDeptId());
        Long deptId = u.getDeptId();
        if (deptId != null) {
            SysDepartment dept = this.deptRepository.findOne(deptId);
            user.setDepartment(dept);
        } else {
            return null;
        }
        this.repository.save(user);

        // 保存数据变动
        dataChangeService.saveDataChange("user_id=" + user.getId(), DataChangeTable.人员, OperationType.修改);

        // 保存关联角色
        Set<SysRole> roleSet = user.getRoles();
        String roleIds = u.getRoles();
        if (StringUtils.isNotBlank(roleIds)) {
            String idArr[] = roleIds.split(",");
            if (roleSet == null) {
                roleSet = new HashSet<SysRole>();
            }
            roleSet.clear();
            for (String roleId : idArr) {
                SysRole role = this.roleService.findOne(Long.valueOf(roleId));
                if (role != null) {
                    roleSet.add(role);
                }
            }
        }
        user.setRoles(roleSet);

        // 日志保存
        StringBuilder logContent = new StringBuilder();
        if (u.getId() == null) {
            logContent.append("新增用户：").append(u.getUserName());
        } else {
            logContent.append("修改用户：").append(u.getUserName());
        }
        logContent.append("{");
        logContent.append("流水号：").append(user.getId() + "，");
        logContent.append("登录账号：").append(u.getAccount() + "，");
        if (user.getRoles() != null && user.getRoles().size() > 0) {
            logContent.append("角色：[");
            for (SysRole role : user.getRoles()) {
                logContent.append(role.getRoleName() + "；");
            }
            logContent.append("]");
        }
        logContent.append("}");
        this.logService.saveLog(SjType.部门用户, logContent.toString(),LogStatus.成功, request);

        return user.getId();
    }

    @Override
    public void updateUserInfo(UserEditBean u) {
        SysUser user = null;
        if (u.getId() != null) {
            user = this.repository.findOne(u.getId());
        }

        if (user == null) {
            return;
        }

        user.setMobile(u.getMobile());
        user.setEmail(u.getEmail());
        user.setSort(u.getSort());
        user.setTel(u.getTel());
        user.setDutyId(u.getDutyId());
        user.setHomeAddr(u.getHomeAddr());
        user.setHomeTel(u.getHomeTel());
        user.setFax(u.getFax());
        user.setPostcode(u.getPostcode());

        this.repository.save(user);
    }

    @Override
    public void delUser(Long id) {
        SysUser u = this.repository.findOne(id);
        u.setValid(ValidAble.INVALID);
        // 保存数据变动
        dataChangeService.saveDataChange("user_id=" + id, DataChangeTable.人员, OperationType.修改);
    }

    public String getInitPasswordPlainText() {
        return initPasswordPlainText;
    }

    public void setInitPasswordPlainText(String initPasswordPlainText) {
        this.initPasswordPlainText = initPasswordPlainText;
    }

    @Override
    public List<SysUser> getUsersByIdsGroupByDep(String ids) {
        List<SysUser> users = new ArrayList<SysUser>(); // 未按部门分组的list
        List<SysUser> returnList = new ArrayList<SysUser>(); // 返回的list
        if (StringUtils.isNotBlank(ids)) {
            String idArrStr[] = ids.split(",");
            Long[] idArr = new Long[idArrStr.length];
            for (int i = 0; i < idArrStr.length; i++) {
                idArr[i] = Long.valueOf(idArrStr[i]);
            }
            if (idArr.length > 0) {
                users = (List<SysUser>) this.findUsers(idArr);
            }

            if (users != null && users.size() > 0) {
                List<SysDepartment> deps = new ArrayList<SysDepartment>();
                for (SysUser u : users) {
                    SysDepartment d = u.getDepartment();
                    if (!deps.contains(d)) {
                        deps.add(d);
                    }
                }
                // 按部门分组
                for (SysDepartment d : deps) {
                    for (SysUser u : users) {
                        if (d.equals(u.getDepartment())) {
                            returnList.add(u);
                        }
                    }
                }
            }
        }
        return returnList;
    }

    @Override
    public boolean updatePassword(String newPwd, Long userId) {

        if (userId != null) {
            SysUser user = this.repository.findOne(userId);
            if (user != null) {

                // 超级管理员的密码只能自己修改，别人不能修改
                if (user.getId().equals(this.superUserId) && !UserTool.getInstance().getCurrentUserId()
                                .equals(String.valueOf(this.superUserId))) {
                    return true;
                }

                if (newPwd == null) { // 密码为空 重置密码为默认值
                    user.setPassword(HashUtil.md5Text(initPasswordPlainText));
                    this.repository.save(user);
                    return true;
                }
                // 修改用户密码
                user.setPassword(HashUtil.md5Text(newPwd));
                this.repository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<SysUser> findByUserName(String userName) {
        if (StringUtils.isNotBlank(userName)) {
            return repository.findByUserNameLikeOrderByUserNameAsc(userName.trim());
        }
        return null;
    }


    @Override
	public SysUser findByAccount(String account) {
		if (StringUtils.isNotBlank(account)) {
			List<SysUser> list = repository.findByAccount(account.trim());
			if (list != null && list.size() == 1) {
				return list.get(0);
			}
		}
		return null;
	}


	@Override
    public List<SysUser> findUsersForAutocomplete(String search, String exceptUserIds, Integer page, Integer rows) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" SELECT u FROM ").append(SysUser.class.getName()).append(" AS u");
        sqlBuilder.append(" WHERE 1=1 ");
        if (StringUtils.isNotBlank(search)) {
            search = search.trim();
            sqlBuilder.append(" AND (u.userName like '%").append(search).append("%' ");
            sqlBuilder.append(" OR u.department.deptName like '%").append(search).append("%') ");
        }
        if (StringUtils.isNotBlank(exceptUserIds)) {
            sqlBuilder.append(" AND u.id NOT IN (").append(exceptUserIds.trim()).append(") ");
        }
        sqlBuilder.append(" ORDER BY u.userName ASC ");
        Query query = em.createQuery(sqlBuilder.toString());
        query.setFirstResult((page - 1) * rows);
        query.setMaxResults(rows);
        List<SysUser> list = query.getResultList();
        return list;
    }

    @Override
    public void unLockedUser(String ids, HttpServletRequest request) {
        if (StringUtils.isNotBlank(ids)) {
            String str = "";
            for (String id : ids.split(",")) {
                SysUser u = this.repository.findOne(new Long(id));
                u.setAccountLocked(false);
                u.setLoginFailCount(0);
                if (StringUtils.isNotBlank(initPasswordPlainText)) {
                    u.setPassword(HashUtil.md5Text(initPasswordPlainText));
                }
                Long dayTime = (Integer.valueOf(passwordTimeoutDays) + 1) * 24 * 60 * 60 * 1000L;
                u.setLastPasswdDate(new Date(new Date().getTime() - dayTime));
                this.repository.save(u);
                str += u.getUserName() + "/" + u.getAccount() + ",";
                logService.saveLog(SjType.部门用户, "解锁用户:" + str,LogStatus.成功, request);
            }
        }
    }

    @Override
    public List<DepAndUserTreeBean> roleUsers(Long roleId) {
        List<DepAndUserTreeBean> tree = new ArrayList<DepAndUserTreeBean>();
        SysRole role = null;
        if (roleId != null) {
            role = this.roleService.findOne(roleId);
        }
        Set<SysUser> users = new HashSet<SysUser>();
        if (role != null && role.getUsers() != null) {
            users = role.getUsers();
        }

        List<SysDepartment> firstDeps = deptRepository.findByRootDepartment(ValidAble.VALID);
        if (firstDeps != null && firstDeps.size() > 0) {
            for (SysDepartment fd : firstDeps) { // 第一层
                DepAndUserTreeBean firstDepBean = new DepAndUserTreeBean(TREE_DEP_ID_PREFIX + fd.getId(),
                                fd.getDeptName(), false, TREE_STATUS_OPEN, "icon-home");
                List<DepAndUserTreeBean> firstChilren = new ArrayList<DepAndUserTreeBean>();
                boolean firstManFlag = false;

                List<SysDepartment> secondDeps = deptRepository.findByDeptParentAndValidOrderByOrderNumAsc(fd.getId(),
                                ValidAble.VALID);
                if (secondDeps != null && secondDeps.size() > 0) {
                    for (SysDepartment sd : secondDeps) { // 第二层
                        if (sd.getId() == fd.getId())
                            continue;
                        DepAndUserTreeBean secondDepBean = new DepAndUserTreeBean(TREE_DEP_ID_PREFIX + sd.getId(),
                                        sd.getDeptName(), false, TREE_STATUS_CLOSED, "icon-home");
                        List<DepAndUserTreeBean> secondChilren = new ArrayList<DepAndUserTreeBean>();
                        boolean secondManFlag = false;

                        List<SysDepartment> thirdDeps = deptRepository
                                        .findByDeptParentAndValidOrderByOrderNumAsc(sd.getId(), ValidAble.VALID);
                        if (thirdDeps != null && thirdDeps.size() > 0) {
                            for (SysDepartment td : thirdDeps) { // 第三层
                                if (td.getId() == sd.getId())
                                    continue;

                                DepAndUserTreeBean thirdDepBean = new DepAndUserTreeBean(
                                                TREE_DEP_ID_PREFIX + td.getId(), td.getDeptName(), false,
                                                TREE_STATUS_CLOSED, "icon-home");

                                List<DepAndUserTreeBean> thirdChilren = new ArrayList<DepAndUserTreeBean>();
                                List<SysDepartment> forDeps = deptRepository.findByDeptParentAndValidOrderByOrderNumAsc(
                                                td.getId(), ValidAble.VALID);
                                boolean tflag = true;
                                if (Tools.isNotEmptyList(forDeps)) {
                                    for (SysDepartment ford : forDeps) {

                                        List<DepAndUserTreeBean> forUsers = getDepUserListAndChecked(ford.getId(),
                                                        users);
                                        DepAndUserTreeBean forDepBean = new DepAndUserTreeBean(
                                                        TREE_DEP_ID_PREFIX + ford.getId(), ford.getDeptName(), false,
                                                        TREE_STATUS_CLOSED, "icon-home");

                                        forDepBean.setChildren(forUsers);
                                        thirdChilren.add(forDepBean);
                                        tflag = false;
                                    }
                                }

                                List<DepAndUserTreeBean> thirdUsers = getDepUserListAndChecked(td.getId(), users);
                                // if (!tflag && (thirdUsers == null) || (thirdUsers.size() <= 0)) {
                                // continue;
                                // }
                                thirdChilren.addAll(thirdUsers);
                                thirdDepBean.setChildren(thirdChilren);
                                secondChilren.add(thirdDepBean);
                                secondManFlag = true;
                            }

                        }

                        List<DepAndUserTreeBean> secondUsers = getDepUserListAndChecked(sd.getId(), users);
                        if ((!secondManFlag) && ((secondUsers == null) || (secondUsers.size() <= 0))) {
                            continue;
                        }

                        secondChilren.addAll(secondUsers);
                        secondDepBean.setChildren(secondChilren);
                        firstChilren.add(secondDepBean);
                        firstManFlag = true;
                    }
                }

                List<DepAndUserTreeBean> firstUsers = getDepUserListAndChecked(fd.getId(), users);
                if (!firstManFlag && ((firstUsers == null) || (firstUsers.size() <= 0))) {
                    continue;
                }
                firstChilren.addAll(firstUsers);
                firstDepBean.setChildren(firstChilren);
                tree.add(firstDepBean);
            }
        }

        return tree;
    }

    /**
     * 角色授权：查找同部门人员
     *
     * @param depId
     * @param users
     * @return
     */
    private List<DepAndUserTreeBean> getDepUserListAndChecked(Long depId, Set<SysUser> users) {
        List<DepAndUserTreeBean> beanList = new ArrayList<DepAndUserTreeBean>();
        if (depId != null) {
            List<SysUser> userList = this.repository.findByDepartmentIdAndValidOrderBySortAsc(depId, ValidAble.VALID);
            for (SysUser u : userList) {
                boolean check = users.contains(u) ? true : false;
                DepAndUserTreeBean bean = new DepAndUserTreeBean(u.getId().toString(), u.getUserName(), check,
                                TREE_STATUS_OPEN, "icon-man");
                bean.setUserFlag(true);
                beanList.add(bean);
            }
        }

        return beanList;
    }

    @Override
    public List<SysUser> findByValidOrderBySort(Integer valid) {
        return this.repository.findByValidOrderBySortAsc(valid);
    }

    @Override
    public String getDictionaryName() {
        return SysUser.class.getSimpleName();
    }

    @Override
    public Map<String, IDictionary> getDictionaryMap() {
        List<SysUser> list = this.loadAllActiveUsers();
        Map<String, IDictionary> data = new HashMap<String, IDictionary>();
        if (Tools.isNotEmptyList(list)) {
            for (SysUser user : list) {
                data.put(user.getId().toString(), user);
            }
        }
        return data;
    }

    @Override
    public boolean isRefreshable() {
        return false;
    }

    @Override
    public void refresh() {
        DictionaryRefresher.getInstance().refreshIDictionaryBuilder(this);
    }

    @Override
    public Class<?> getDictionaryClass() {
        return SysUser.class;
    }
}
