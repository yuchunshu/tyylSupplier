/*
 * FileName:    OaReceiveFileRepository.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.readmatter.repository;

import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.doc.readmatter.domain.ReadMatter;

/**
 * @author huangwq
 *
 */
public interface ReadMatterRepository extends SimpleDomainRepository<ReadMatter, String> {

    List<ReadMatter> findByFileId(String FileId);

}
