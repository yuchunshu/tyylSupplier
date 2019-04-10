/*
 * FileName:    EmMainRepository.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.message.app.repository;

import java.util.Date;
import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.oa.message.app.domain.OaAppMessage;
import cn.com.chaochuang.oa.message.app.reference.MesStatus;
import cn.com.chaochuang.oa.message.app.reference.MesType;

/**
 * @author HM
 *
 */
public interface AppMessageRepository extends SimpleDomainRepository<OaAppMessage, Long> {

    public List<OaAppMessage> findByIncepterIdAndMesTypeAndStatus(Long incepterId, MesType mesType, MesStatus status);

    public List<OaAppMessage> findByIncepterIdAndStatus(Long incepterId, MesStatus status);

    public List<OaAppMessage> findByIsReadAndIncepterId(YesNo isRead, Long incepterId);

    public List<OaAppMessage> findByIncepterIdAndSenderIdAndMesTypeAndStatus(Long incepterId, Long senderId, MesType mesType, MesStatus status);

    public List<OaAppMessage> findByIncepterIdAndCreateTimeLessThan(Long incepterId, Date createTime);

    public List<OaAppMessage> findByIncepterId(Long incepterId);
}
