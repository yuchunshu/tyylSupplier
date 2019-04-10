/*
 * FileName:    RemoteDocSwapEnvelopeRepository.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapEnvelope;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvRecFlag;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvStatus;

/**
 * @author yuandl
 *
 */
public interface RemoteDocSwapEnvelopeRepository extends SimpleDomainRepository<RemoteDocSwapEnvelope, Long> {

    @Query("select e from RemoteDocSwapEnvelope e where envelopeUuid = ?1 and linkEnvelopeId is null")
    public List<RemoteDocSwapEnvelope> findByEnvelopeUuid(String uuid);

    public List<RemoteDocSwapEnvelope> findByLinkEnvelopeId(Long linkEnvelopeId);

    public List<RemoteDocSwapEnvelope> findByReceiveFlagAndEnvStatus(RemoteEnvRecFlag recFlag, RemoteEnvStatus envStatus);
    
    @Query("select e from RemoteDocSwapEnvelope e where ((e.envStatus='2' and e.receiveFlag = '1') or (e.envStatus='1' and e.receiveFlag = '3')) and e.envelopeType = '0' and DATE_FORMAT(e.limitTime,'%Y-%m-%d') = ?1")
    public List<RemoteDocSwapEnvelope> findLimitDocList(String dateStr);
    
    public RemoteDocSwapEnvelope findByInstIdAndReceiveFlag(String instId,RemoteEnvRecFlag receiveFlag);
    
    public RemoteDocSwapEnvelope findByInstId(String instId);
}
