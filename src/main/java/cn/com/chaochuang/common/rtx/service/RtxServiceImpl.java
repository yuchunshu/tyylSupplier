/*
 * FileName:    RtxServiceImpl.java
 * Description:
 * Company:     
 * Copyright:    (c) 2014
 * History:     2014年7月18日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.rtx.service;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.rtx.domain.InfRtx;
import cn.com.chaochuang.common.rtx.repository.RtxRepository;
import cn.com.chaochuang.common.security.util.UserTool;

/**
 * @author LaoZhiYong
 *
 */
@Service
@Transactional
public class RtxServiceImpl extends SimpleLongIdCrudRestService<InfRtx> implements RtxService {

    @Autowired
    private RtxRepository repository;

    @Override
    public SimpleDomainRepository<InfRtx, Long> getRepository() {
        return repository;
    }

    @Override
    public String sendMessages(Long[] receiveMans, String content) {
        if (receiveMans != null && receiveMans.length > 0) {
            String curUserIdStr = UserTool.getInstance().getCurrentUserId();
            if (StringUtils.isNotBlank(curUserIdStr)) {
                Long sendMan = Long.parseLong(curUserIdStr);
                for (Long receive : receiveMans) {
                    InfRtx msg = new InfRtx(sendMan, receive, content);
                    repository.save(msg);
                }
                return null;
            }
            return "当前用户登录超时！";
        }
        return "接收人ID为空！";
    }

}
