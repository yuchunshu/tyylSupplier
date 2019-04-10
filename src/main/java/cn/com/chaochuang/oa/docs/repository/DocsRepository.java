/*
 * FileName:    DocsRepository.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年4月15日 (WTL) 1.0 Create
 */

package cn.com.chaochuang.oa.docs.repository;

import java.util.Date;
import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.oa.docs.domain.OaDocs;
import cn.com.chaochuang.oa.docs.reference.DocsStatus;

/**
 * @author WTL
 *
 */
public interface DocsRepository extends SimpleDomainRepository<OaDocs, Long> {

    public List<OaDocs> findByStatusAndCreateDateLessThan(DocsStatus status, Date createDate);

    public List<OaDocs> findByFolderId(Long folderId);

}
