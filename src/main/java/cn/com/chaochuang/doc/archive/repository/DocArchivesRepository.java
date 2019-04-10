/*
 * FileName:    OaNoticeRepository.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.doc.archive.repository;

import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.doc.archive.domain.DocArchives;

/**
 * @author huangwq
 *
 */
public interface DocArchivesRepository extends SimpleDomainRepository<DocArchives, Long> {
	
	List<DocArchives> findByArchNo(String archNo);
	
    List<DocArchives> findByArchNoAndCounterNoAndArchType(String archNo,String counterNo,String archType);
    
}
