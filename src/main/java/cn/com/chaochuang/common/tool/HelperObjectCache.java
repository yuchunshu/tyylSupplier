/*
 * FileName:    HelperObjectCache.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月13日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.tool;

import java.util.Map;

/**
 * @author LJX
 *
 */
public final class HelperObjectCache {
    private static Map     helperMap           = null;

    private static boolean allowHelperOverride = false;

    /**
     * @return Returns the helperMap.
     */
    public static Map getHelperMap() {
        return helperMap;
    }

    /**
     * @param helperMap
     *            The helperMap to set.
     */
    public void setHelperMap(Map helperMap) {
        HelperObjectCache.helperMap = helperMap;
    }

    /**
     * @return 是否允许覆盖同名上下文
     */
    public static boolean getAllowHelperOverride() {
        return allowHelperOverride;
    }

    /**
     * @param allowHelperOverride
     *            The allowHelperOverride to set.
     */
    public void setAllowHelperOverride(boolean allowHelperOverride) {
        HelperObjectCache.allowHelperOverride = allowHelperOverride;
    }
}
