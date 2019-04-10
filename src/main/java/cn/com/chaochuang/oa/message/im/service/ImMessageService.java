/*
 * FileName:    ImMessageService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年5月24日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.message.im.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.syspower.bean.DepAndUserTreeBean;
import cn.com.chaochuang.oa.message.im.domain.OaImMessage;

/**
 * @author HM
 *
 */
public interface ImMessageService extends CrudRestService<OaImMessage, Long> {

    /**
     * 发送一条消息
     *
     * @param incepterId
     * @param content
     * @return
     */
    public Long sendMessage(Long incepterId, String content, HttpServletRequest request);

    /**
     * 接收指定人员的消息
     *
     * @return
     */
    public List<OaImMessage> receiveMessage(Long senderId);

    /**
     * 所有未读消息发送人的id
     *
     * @return
     */
    public List<Long> messageAlert();

    /**
     * 与指定人员的历史消息
     *
     * @param senderId
     * @return
     */
    public Map<String, Object> historyMsg(Long senderId, Long curUserId, Integer page, Integer rows, String condition);

    /**
     * 删除多条消息记录
     *
     * @param ids
     */
    public void deleteMsg(Long[] ids);

    /**
     * 人员树
     *
     * @return
     */
    public List<DepAndUserTreeBean> getUserTree();
}
