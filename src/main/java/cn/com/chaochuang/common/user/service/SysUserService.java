package cn.com.chaochuang.common.user.service;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.syspower.bean.DepAndUserTreeBean;
import cn.com.chaochuang.common.user.bean.UserEditBean;
import cn.com.chaochuang.common.user.domain.SysUser;

public interface SysUserService extends CrudRestService<SysUser, Long> {

    public List<SysUser> loadAllActiveUsers();

    public Collection<SysUser> findUsers(Long[] ids);

    public List<SysUser> findBydetpId(Long deptId);

    public Long saveUser(UserEditBean u, HttpServletRequest request);

    public void updateUserInfo(UserEditBean u);

    public void delUser(Long id);

    public List<SysUser> getUsersByIdsGroupByDep(String ids);

    /**
     * 修改密码， 当新密码为空时，重置为初始值
     *
     * @param newPwd
     *            新密码
     * @param userId
     *            用户ID
     * @return
     */
    public boolean updatePassword(String newPwd, Long userId);

    public void unLockedUser(String ids, HttpServletRequest request);

    public List<SysUser> findByUserName(String userName);

    public List<SysUser> findUsersForAutocomplete(String search, String exceptUserIds, Integer page, Integer rows);

    List<DepAndUserTreeBean> roleUsers(Long roleId);

    public List<SysUser> findByValidOrderBySort(Integer valid);

    SysUser findByAccount(String account);
    
    boolean isOwnByRoleName(String roleName);

    boolean updatePasswordMobile(String newpw, SysUser user);
}
