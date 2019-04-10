/*
 * FileName:    MijiEntityRepositoryFilter.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2014
 * History:     2014年12月12日 (guig) 1.0 Create
 */

package cn.com.chaochuang.common.miji.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.repository.util.ClassUtils;
import org.springframework.stereotype.Component;

import cn.com.chaochuang.common.jpa.data.FilterRepositoryAction;
import cn.com.chaochuang.common.jpa.data.RepositoryFilter;
import cn.com.chaochuang.common.jpa.data.SimpleFilterRepositoryAction;
import cn.com.chaochuang.common.jpa.data.support.FilterRepositoryUtil;
import cn.com.chaochuang.common.miji.domain.MijiSupport;
import cn.com.chaochuang.common.miji.reference.MijiType;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.UserHelper;

/**
 * @author guig
 *
 */
@Component
public class MijiEntityRepositoryFilter implements RepositoryFilter {

    public static String FILTER_NAME = "miji";

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.jpa.data.RepositoryFilter#getFilterName()
     */
    @Override
    public String getFilterName() {
        return FILTER_NAME;
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.jpa.data.RepositoryFilter#canFilter(java.lang.Class, java.lang.Class)
     */
    @Override
    public <T> boolean canFilter(Class<?> repositoryInterface, Class<T> domainType) {
        return MijiSupport.class.isAssignableFrom(domainType) && !SysUser.class.isAssignableFrom(domainType)
                        && !FilterRepositoryUtil.isNoFilterRepository(repositoryInterface);
    }

    protected String getTargetFieldName(final Class<?> domainType) {
        if (ClassUtils.hasProperty(domainType, MijiSupport.MIJI_FIELD_NAME)) {
            return MijiSupport.MIJI_FIELD_NAME;
        }
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.jpa.data.RepositoryFilter#getFilterRepositoryAction(java.lang.Class,
     *      java.lang.Class)
     */
    @Override
    public <T> FilterRepositoryAction<T> getFilterRepositoryAction(final Class<?> repositoryInterface,
                    final Class<T> domainType) {
        final String fieldName = getTargetFieldName(domainType);

        if (StringUtils.isBlank(fieldName)) {
            return null;
        }

        return new SimpleFilterRepositoryAction<T>() {

            @Override
            protected Predicate bindToPredicate(Predicate origPredicate, Root<?> root, CriteriaQuery<?> query,
                            CriteriaBuilder cb) {
                Path expression = root.get(fieldName);

                MijiType currentUserMiji = UserHelper.getCurrentUser().getMiji();

                Predicate mijiPredicate = cb.or(cb.isNull(expression),
                                cb.lessThanOrEqualTo(expression, currentUserMiji));

                return (null == origPredicate) ? cb.isTrue(mijiPredicate) : cb.and(mijiPredicate, origPredicate);
            }

        };

    }
}
