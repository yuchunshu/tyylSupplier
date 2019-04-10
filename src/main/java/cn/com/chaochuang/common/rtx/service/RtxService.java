/*
 * FileName:    RtxService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2014
 * History:     2014年7月18日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.rtx.service;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.rtx.domain.InfRtx;

/**
 * @author LaoZhiYong
 *
 */
public interface RtxService extends CrudRestService<InfRtx, Long> {

    /**
     * 群发消息
     * @param receiveMans
     * @param content
     * @return 返回错误信息， 正常返回null
     */
    public String sendMessages(Long[] receiveMans, String content);

}
