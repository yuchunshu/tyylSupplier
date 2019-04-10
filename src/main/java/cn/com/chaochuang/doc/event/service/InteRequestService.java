package cn.com.chaochuang.doc.event.service;

import java.util.Collection;
import java.util.Map;

import cn.com.chaochuang.common.data.persistence.SearchFilter;

/**
 * @author dengl 2017.12.17
 *
 */
public interface InteRequestService {
	
	/** 内部请示查询 */
    public Map<String, Object> inteRequest(Collection<SearchFilter> searchFilters);
}
