/**
 *
 */
package cn.com.chaochuang.doc.countersign.repository;

import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.doc.countersign.domain.DocCountersign;

/**
 * @author hzr 2016年5月16日
 *
 */
public interface CountersignRepository extends SimpleDomainRepository<DocCountersign, Long> {

    List<DocCountersign> findByFileId(String fileId);

}
