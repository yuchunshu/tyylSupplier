/*
 * FileName:    RemoteDocSwapContentRepository.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapContent;

/**
 * @author yuandl
 *
 */
public interface RemoteDocSwapContentRepository extends SimpleDomainRepository<RemoteDocSwapContent, Long> {

    /**
     * 根据封首查询封体
     */
    public List<RemoteDocSwapContent> findByEnvelopeId(Long envelopeId);

    /**
     * 根据封首UUID查询封体
     */
    @Query("select e from RemoteDocSwapContent e where envelopeUuid = ?1 and envelope.linkEnvelopeId is null and envelope.id is not null")
    public List<RemoteDocSwapContent> findByEnvelopeUuid(String envelopeUuid);

    @Query("select c from RemoteDocSwapContent c where c.envelopeUuid = ?1 and c.envelope.envelopeType = '0' and c.envelope.receiveFlag in ('0','1','3')")
    public RemoteDocSwapContent findEnvelopeByYwlsh(String ywlsh);
}
