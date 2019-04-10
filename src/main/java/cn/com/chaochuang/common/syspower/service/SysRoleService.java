package cn.com.chaochuang.common.syspower.service;

import java.util.List;
import java.util.Set;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.select.bean.UserSelectShowBean;
import cn.com.chaochuang.common.syspower.bean.RoleCheckedBean;
import cn.com.chaochuang.common.syspower.bean.RoleInfo;
import cn.com.chaochuang.common.syspower.domain.SysRole;

public interface SysRoleService extends CrudRestService<SysRole, Long> {

    public Long saveRole(RoleInfo roleBean);

    public String deleteRole(Long roleId);

    public String powerIds(Long roleId);

    List<RoleCheckedBean> getRolesAndChecked(Set<SysRole> userRoles);

    public List<Long> getRoleIdListByUserId(Long userId);

    public abstract List<UserSelectShowBean> getUsersByRoleId(Long paramLong);
}
