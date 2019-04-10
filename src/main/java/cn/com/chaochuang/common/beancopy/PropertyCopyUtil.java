/*
 * FileName:    BeanCopyUtil.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年1月8日 (guig) 1.0 Create
 */

package cn.com.chaochuang.common.beancopy;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author guig
 *
 */
public abstract class PropertyCopyUtil {

    public static <T> T copy(final Object sourObject, final Class<T> targetClazz) {
        try {
            T destObject = targetClazz.newInstance();
            PropertyUtils.copyProperties(destObject, sourObject);
            return destObject;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T copy(final Object sourObject, final T targetObject) {
        try {
            PropertyUtils.copyProperties(targetObject, sourObject);
            return targetObject;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
