/*
 * FileName:    ImMessageRepository.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年5月24日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.message.im.repository;

import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.oa.message.im.domain.OaImMessage;
import cn.com.chaochuang.oa.message.im.reference.ImDelFlag;
import cn.com.chaochuang.oa.message.im.reference.ImMesStatus;

/**
 * @author HM
 *
 */
public interface ImMessageRepository extends SimpleDomainRepository<OaImMessage, Long> {

    /**
     * 查询多人之间的消息
     * @param senderIds
     * @param incetperIds
     * @param OutdelFlag
     * @param indelFlag
     * @return
     */
    List<OaImMessage> findBySenderIdInAndIncepterIdInAndOutdelFlagAndIndelFlagOrderByIdDesc(Long[] senderIds,
                    Long[] incetperIds, ImDelFlag OutdelFlag, ImDelFlag indelFlag);

    /**
     * 按状态和接收删除标志查询指定人员的消息
     * @param status
     * @param indelFlag
     * @return
     */
    List<OaImMessage> findBySenderIdAndIncepterIdAndStatusInAndIndelFlag(Long senderId, Long incepterId, ImMesStatus[] status, ImDelFlag indelFlag);

    /**
     * 按接收人id、 状态、 接收删除标志查询
     * @param incepterId
     * @param status
     * @param indelFlag
     * @return
     */
    List<OaImMessage> findByIncepterIdAndStatusInAndIndelFlag(Long incepterId, ImMesStatus[] status, ImDelFlag indelFlag);
}
