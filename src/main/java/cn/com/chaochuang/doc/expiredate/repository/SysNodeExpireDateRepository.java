
package cn.com.chaochuang.doc.expiredate.repository;


import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.doc.expiredate.domain.SysNodeExpireDate;
import cn.com.chaochuang.doc.expiredate.reference.DeadlineType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapType;

/**
 * @author yuchunshu
 *
 */
public interface SysNodeExpireDateRepository extends SimpleDomainRepository<SysNodeExpireDate, String> {

	List<SysNodeExpireDate> findByDeadlineType(DeadlineType deadlineType);
	
	List<SysNodeExpireDate> findByDocumentTypeAndDeadlineTypeAndNodeId(RemoteDocSwapType documentType,DeadlineType deadlineType,String nodeId);
	
	List<SysNodeExpireDate> findByDocumentTypeAndDeadlineType(RemoteDocSwapType documentType,DeadlineType deadlineType);
}
