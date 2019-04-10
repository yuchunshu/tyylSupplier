/*
 * FileName:    SmsPhraseType.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年12月05日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.sms.repository;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.sms.domain.SmsPhraseType;

import java.util.List;

/**
 * @author Shicx
 */
public interface SmsPhraseTypeRepository extends SimpleDomainRepository<SmsPhraseType, Long> {

    /**
     * 查找用户的短语类型
     * @param userId
     * @return
     */
    List<SmsPhraseType> findByUserId(Long userId);
}
