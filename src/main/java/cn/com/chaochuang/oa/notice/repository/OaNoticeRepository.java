/*
 * FileName:    OaNoticeRepository.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.notice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.reference.StatusFlag;
import cn.com.chaochuang.oa.notice.domain.OaNotice;

/**
 * @author HM
 *
 */
public interface OaNoticeRepository extends SimpleDomainRepository<OaNotice, String> {
    List<OaNotice> findByStatusAndIdGreaterThanOrderByIdDesc(StatusFlag status, String id);

    @Query("select e from OaNotice e where e.status = 1 and e.createDate >:time")
    List<OaNotice> selectOaNoticeBySaveDate(@Param("time") Date lastInputTime);

    List<OaNotice> findByStatus(StatusFlag status);
}
