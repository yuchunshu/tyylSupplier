/*
 * FileName:    UserHelper.java
 * Description:
 * Company:     
 * Copyright:    (c) 2014
 * History:     2014年7月22日 (guig) 1.0 Create
 */

package cn.com.chaochuang.common.util;

import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;

/**
 * @author guig
 *
 */
public abstract class UserHelper {

    public static SysUser getCurrentUser() {
        return (SysUser) UserTool.getInstance().getCurrentUser();
    }

    public static SysDepartment getCurrentUserDepartment() {
        return (SysDepartment) UserTool.getInstance().getCurrentUserDepartment();
    }
}
