/*
 * FileName:    RemoteDocSwapAttachRepository.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月21日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.repository;

import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapAttach;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteAttachType;

/**
 * @author yuandl
 *
 */
public interface RemoteDocSwapAttachRepository extends SimpleDomainRepository<RemoteDocSwapAttach, Long> {

    public List<RemoteDocSwapAttach> findByRemoteDocSwapContentIdAndRemoteAttachType(Long contentId,
                    RemoteAttachType attachType);
}
