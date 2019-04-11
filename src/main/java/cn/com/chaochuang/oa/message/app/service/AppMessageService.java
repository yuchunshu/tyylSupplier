/*
 * FileName:    AppMessageService.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.message.app.service;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.oa.message.app.domain.OaAppMessage;
import cn.com.chaochuang.oa.message.app.reference.MesType;

/**
 * @author HM
 *
 */
public interface AppMessageService extends CrudRestService<OaAppMessage, Long> {

    /**
     * 更新消息
     *
     * @param userId
     * @param type
     * @return
     */
    public int updateMsg(Long userId, MesType mesType);

    /**
     * 保存用户消息
     *
     * @param userId
     * @return
     */
    public String saveUserMessage(Long userId);

    /**
     * 新增一条消息
     *
     * @param incepterId
     *            接收人id
     * @param senderId
     *            发送人id
     * @param content
     *            消息内容
     * @param mesType
     *            消息类型
     */
    public void insertOaAppMsg(Long incepterId, Long senderId, String content, MesType mesType);

    /**
     * 新增一条消息
     *
     * @param incepterId
     *            接收人id
     * @param senderId
     *            发送人id
     * @param content
     *            消息内容
     * @param mesType
     *            消息类型
     * @param url
     *            消息跳转地址 <br>
     *            格式："appId=XX&tabTitle=XXX&tabUrl=XXXX" 前面不带'/'号 <br>
     *            例如："appId=1&tabTitle=收件箱&tabUrl=/oa/mail/inbox/list"<br>
     *            注意：tabUrl只能写列表页的URL，不能写读取具体某一条数据的URL<br>
     *            可调用SysApp类里的静态常量来填写对应子系统的appId
     */
    public void insertOaAppMsg(Long incepterId, Long senderId, String content, MesType mesType, String url);

    /**
     * 消息阅知
     *
     * @param id
     * @return
     */
    public Long readMsg(Long id);

    /**
     * 未阅消息数量
     *
     * @return
     */
    public int countNotRead();

    /**
     * 批量阅知
     * @param ids
     */
    public void readMsgs(Long[] ids);

    /**
     * 清理所有消息
     */
    public void cleanAll();

    /**
     * 清理消息， 保留最后一条记录创建日期所在的那个月份的记录
     */
    public void cleanExceptLastMonth();

    /**
     * 全部已阅
     */
    public void readAll();

    /**
     * 清理消息， 保留30天的记录（从今天起算）
     */
    public void cleanExceptLastThirty();
}
