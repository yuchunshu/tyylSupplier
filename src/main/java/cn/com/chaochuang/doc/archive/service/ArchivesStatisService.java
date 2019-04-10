package cn.com.chaochuang.doc.archive.service;

import java.util.Collection;
import java.util.Map;

import cn.com.chaochuang.common.data.persistence.SearchFilter;

/**
 * @author dengl 2017.12.01
 *
 */
public interface ArchivesStatisService {
	
	/** 档案统计 */
    public Map<String, Object> statArchives(Collection<SearchFilter> searchFilters);

}
