/*
 * FileName:    SearchBuilderHelper.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年1月10日 (guig) 1.0 Create
 */

package cn.com.chaochuang.common.util;

import java.io.Serializable;

import javax.servlet.ServletRequest;

import org.springframework.core.convert.ConversionService;

import cn.com.chaochuang.common.data.persistence.SearchBuilder;

/**
 * @author guig
 *
 */
public abstract class SearchBuilderHelper {

    public static <T, ID extends Serializable> SearchBuilder<T, ID> bindSearchBuilder(
                    final ConversionService conversionService, final ServletRequest request) {
        SearchBuilder<T, ID> searchBuilder = new SearchBuilder<T, ID>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        return searchBuilder;
    }

}
