/*
 * FileName:    SearchListHelper.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年1月8日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.util;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;

/**
 * @author LaoZhiYong
 *
 */
public class SearchListHelper<T> {
    private List<T> list;
    private Long    count;

    public void execute(SearchBuilder<T, ?> searchBuilder, SimpleDomainRepository<T, ?> repository, Integer page,
                    Integer rows) {
        Page<T> p = searchBuilder.findAllByPage(repository, page - 1, rows);
        list = p.getContent();
        count = p.getTotalElements();
    }

    /**
     * @return the list
     */
    public List<T> getList() {
        return list;
    }

    /**
     * @param list
     *            the list to set
     */
    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * @return the count
     */
    public Long getCount() {
        return count;
    }

    /**
     * @param count
     *            the count to set
     */
    public void setCount(Long count) {
        this.count = count;
    }

}
