/*
 * FileName:    OaNoticeRepository.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.notice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    /**
     * 查询用户收到的和创建的公告
     * @param userId
     * @param deptId
     * @param title
     * @param page
     * @return
     */
    @Query("select notice from OaNotice notice where notice.status='1' and ((notice.publishType='0') or (notice.publishType='1' and notice.publishDepId = :deptId) or (notice.creatorId=:userId)) and notice.title like :title order by notice.displayType desc,notice.publishDate desc,notice.id desc")
    Page<OaNotice> findPersonalNoticeList(@Param("userId")Long userId, @Param("deptId")Long deptId, @Param("title")String title, Pageable page);
}
