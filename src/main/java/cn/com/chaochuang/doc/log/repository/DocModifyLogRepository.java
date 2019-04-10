package cn.com.chaochuang.doc.log.repository;



import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.doc.log.domain.DocModifyLog;

public interface DocModifyLogRepository extends SimpleDomainRepository<DocModifyLog, String> {

	List<DocModifyLog> findByItemIdAndFileId(String itemId,String fileId);
}
