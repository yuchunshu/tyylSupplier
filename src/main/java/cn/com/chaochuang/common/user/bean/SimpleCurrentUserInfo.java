/*
 * FileName:    SimpleCurrentUserInfo.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2014
 * History:     2014年7月22日 (guig) 1.0 Create
 */

package cn.com.chaochuang.common.user.bean;

import java.io.Serializable;

import org.springframework.util.Assert;

import cn.com.chaochuang.common.security.CurrentUserInfo;
import cn.com.chaochuang.common.user.domain.SysUser;

/**
 * @author guig
 *
 */
public class SimpleCurrentUserInfo implements CurrentUserInfo, Serializable {

    private static final long serialVersionUID = -1981294937108087808L;

    private SysUser           user;

    public SimpleCurrentUserInfo(final SysUser user) {
        Assert.notNull(user, "param user must not be null!");
        this.user = user;
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.user.security.CurrentUserInfo#getUserAccountName()
     */
    @Override
    public String getUserAccountName() {
        return user.getAccount();
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.user.security.CurrentUserInfo#getUserName()
     */
    @Override
    public String getUserName() {
        return user.getUserName();
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.user.security.CurrentUserInfo#getUserId()
     */
    @Override
    public String getUserId() {
        return user.getId().toString();
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.user.security.CurrentUserInfo#getUserDepartmentName()
     */
    @Override
    public String getUserDepartmentName() {
        if (null != user.getDepartment()) {
            return user.getDepartment().getDeptName();
        }
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.user.security.CurrentUserInfo#getUserDepartmentId()
     */
    @Override
    public String getUserDepartmentId() {
        if (null != user.getDepartment()) {
            return user.getDepartment().getId().toString();
        }
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.user.security.CurrentUserInfo#getUserObject()
     */
    @Override
    public Object getUserObject() {
        return user;
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.user.security.CurrentUserInfo#getUserDepartmentObject()
     */
    @Override
    public Object getUserDepartmentObject() {
        return user.getDepartment();
    }

}
