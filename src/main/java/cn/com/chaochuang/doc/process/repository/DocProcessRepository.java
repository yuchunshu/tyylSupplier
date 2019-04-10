/*
 * FileName:    ReceiveProcessRepository.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年4月27日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.process.repository;

import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.doc.process.domain.DocProcess;
import cn.com.chaochuang.doc.event.domain.OaDocFile;

/**
 * @author huangwq
 *
 */
public interface DocProcessRepository extends SimpleDomainRepository<DocProcess, Long> {

    DocProcess findByDepIdAndDocFile(Long depId, OaDocFile docFile);

    List<DocProcess> findByDocFile(OaDocFile receiveFile);

}
