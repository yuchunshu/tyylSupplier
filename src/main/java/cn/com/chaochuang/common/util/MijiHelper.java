/*
 * FileName:    MijiHelper.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2014
 * History:     2014年12月25日 (guig) 1.0 Create
 */

package cn.com.chaochuang.common.util;

import cn.com.chaochuang.common.miji.domain.MijiSupport;
import cn.com.chaochuang.common.miji.reference.MijiType;
import cn.com.chaochuang.common.user.domain.SysUser;

/**
 * @author guig
 *
 */
public class MijiHelper {

    private final static ThreadLocal<Boolean> inViewScope = new ThreadLocal<Boolean>();

    public static void enterViewRender() {
        inViewScope.set(Boolean.TRUE);
    }

    public static void quitViewRender() {
        inViewScope.set(Boolean.FALSE);
    }

    public static boolean isInViewScope() {
        Boolean result = inViewScope.get();
        if (null != result) {
            return result;
        }

        return false;
    }

    public static boolean isAutoMiji(Object obj) {
        if (SysUser.class.isInstance(obj)) {
            return false;
        }
        if (MijiSupport.class.isInstance(obj)) {
            return true;
        }
        return false;
    }

    public static MijiType getCurrentDefaultMiji() {
        SysUser user = UserHelper.getCurrentUser();
        if (null != user) {
            return user.getMiji();
        }
        return null;
    }

    public static boolean currentUserMijiLower(MijiType objMiji) {
        if (null == objMiji) {
            return false;
        }

        SysUser user = UserHelper.getCurrentUser();
        if ((null != user) && (null != user.getMiji())) {
            return (objMiji.getKey().compareTo(user.getMiji().getKey()) > 0);
        }

        return true;
    }

}
