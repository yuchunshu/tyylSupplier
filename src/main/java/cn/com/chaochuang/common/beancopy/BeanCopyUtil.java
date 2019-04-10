/*
 * FileName:    BeanCopyUtil.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年1月8日 (guig) 1.0 Create
 */

package cn.com.chaochuang.common.beancopy;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.DozerBeanMapper;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.dozer.util.DozerConstants;

/**
 * @author guig
 *
 */
public abstract class BeanCopyUtil {

    protected static final Log logger = LogFactory.getLog(BeanCopyUtil.class);

    private static Mapper      instance;
    private static long        newtimeout;
    private static boolean     isInRebel;

    static {
        isInRebel = StringUtils.isNotBlank(System.getProperty("rebel.notification.url"));
    }

    private static Mapper getNewMapper() {
        if ((null == instance) || (System.currentTimeMillis() > newtimeout)) {
            long st = System.currentTimeMillis();
            List<String> mappingFiles = new ArrayList<String>();
            mappingFiles.add(DozerConstants.DEFAULT_MAPPING_FILE);
            instance = new DozerBeanMapper(mappingFiles);

            newtimeout = System.currentTimeMillis() + 1000L * 10;// 开发环境下，每10秒使用新实例
            long en = System.currentTimeMillis();
            if (logger.isDebugEnabled()) {
                logger.debug("getNewMapper new instance run:" + Long.toString(en - st) + " ms");
            }
        }
        return instance;
    }

    public static <T> T copyBean(final Object sourObject, final Class<T> targetClazz) {
        if (null != sourObject) {
            long st = System.currentTimeMillis();
            Mapper mapper = isInRebel ? getNewMapper() : DozerBeanMapperSingletonWrapper.getInstance();
            T result = mapper.map(sourObject, targetClazz);
            long en = System.currentTimeMillis();
            if (logger.isDebugEnabled()) {
                logger.debug("copyBean run:" + Long.toString(en - st) + " ms");
            }
            return result;
        } else {
            return null;
        }
    }

    public static <T> Object copyBean2(final Object sourObject, Object targetObject) {
        if (null != sourObject) {
            long st = System.currentTimeMillis();
            Mapper mapper = isInRebel ? getNewMapper() : DozerBeanMapperSingletonWrapper.getInstance();
            mapper.map(sourObject, targetObject);
            long en = System.currentTimeMillis();
            if (logger.isDebugEnabled()) {
                logger.debug("copyBean run:" + Long.toString(en - st) + " ms");
            }
            return targetObject;
        } else {
            return null;
        }
    }

}
