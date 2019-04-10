/*
 * FileName:    OaDocDeptFileRepository.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年9月14日 (yuchunshu) 1.0 Create
 */

package cn.com.chaochuang.doc.expiredate.repository;


import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.doc.expiredate.domain.OaDocNodeExpireDate;

/**
 * @author yuchunshu
 *
 */
public interface OaDocNodeExpireDateRepository extends SimpleDomainRepository<OaDocNodeExpireDate, String> {

	OaDocNodeExpireDate findByFileIdAndNodeId(String fileId, String nodeId);
}
