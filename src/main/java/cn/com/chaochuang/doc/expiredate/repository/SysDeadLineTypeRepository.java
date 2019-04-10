
package cn.com.chaochuang.doc.expiredate.repository;


import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.doc.expiredate.domain.SysDeadLineType;
import cn.com.chaochuang.doc.expiredate.reference.DeadlineType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapType;

/**
 * @author yuchunshu
 *
 */
public interface SysDeadLineTypeRepository extends SimpleDomainRepository<SysDeadLineType, String> {

	List<SysDeadLineType> findByDeadlineTypeAndDocumentType(DeadlineType deadlineType,RemoteDocSwapType documentType);
	
}
