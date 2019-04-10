/*
 * FileName:    RemoteDocSwapEnvelopeService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.service;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.doc.event.bean.OaDocFileEditBean;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.remotedocswap.bean.DocSwapEnvelopeEditBean;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapEnvelope;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteUnitIdentifier;


/** 
 * @ClassName: RemoteDocSwapEnvelopeService 
 * @Description: 公文交换封首内容接口实现
 * @author: chunshu
 * @date: 2017年7月6日 下午2:45:16  
 */
public interface RemoteDocSwapEnvelopeService extends CrudRestService<RemoteDocSwapEnvelope, Long> {

    /**
     * 创建远程公文交换
     *
     * @param info
     *            远程交换信息
     * @return TODO
     */
    public boolean createRemoteDocSwap(DocSwapEnvelopeEditBean bean);
    
    /**
     * 创建远程公文交换，多个单位
     *
     * @param info
     *            远程交换信息
     * @return TODO
     */
    public boolean createMultiRemoteDocSwap(DocSwapEnvelopeEditBean bean);

    /**
     * 保存自治区交换公文
     *
     * @param file
     *            文件路径
     */
    void saveReceiveRemoteDocSwap(File file) throws Exception;

    /**
     * 创建公文交换反馈
     *
     * @param info
     * @return TODO
     */
    boolean createRemoteDocSwapFeedback(DocSwapEnvelopeEditBean bean);

    /**
     * 查询远程公文交换明细
     *
     * @param envelopeId
     *            公文交换编号
     * @return
     */
    Map selectRemoteDocSwapEnvelopeDetail(Long envelopeId);

    /** 
     * @Title: findEnvelopeMapByLinkIdAndType 
     * @Description: 查询关联的封首内容
     * @param envelopeId
     * @return
     * @return: Map
     */
    Map findEnvelopeMapByLinkIdAndType(Long envelopeId);
    /**
     * 批量删除公文交换记录
     *
     * @param envelopeIds
     *            公文交换记录编号集合
     */
    void deleteRemoteDocSwapEnvelope(String[] envelopeIds);

    /**
     * 删除公文交换记录
     *
     * @param envelopeId
     *            公文交换记录编号
     */
    void deleteRemoteDocSwapEnvelope(Long envelopeId);
    
    
    /**
     * 签收公文
     * @param bean
     * @param currentUser
     */
    boolean signDocSwap(DocSwapEnvelopeEditBean bean, SysUser currentUser);
    
    /**
     * 批量签收公文
     * @param bean
     * @param currentUser
     */
    boolean signDocSwapList(DocSwapEnvelopeEditBean bean, SysUser currentUser);
    
    /**
     * 保存反馈纸质公文信息
     * @param obj
     * @param bean
     */
    String saveFeedBackPaperDoc(OaDocFile obj,OaDocFileEditBean bean);
    
    /**
     * 公文统计-本系统
     * @param year 统计年份
     * @return
     */
    Map statistic(String year);
    
    /**
     * 公文统计-区直系统
     * @param year 统计年份
     * @return
     */
    Map statisticByOuter(String year);
    
    /** 
     * @Title: selectDocReceiveFinishPage 
     * @Description: 归档查询
     * @param title 公文标题
     * @param senderAncestorName 来文单位
     * @param signerName 签收人
     * @param sendBeginTime 发送开始时间
     * @param sendEndTime 发送结束时间
     * @param limitBeginTime 限办开始时间
     * @param limitEndTime 限办结束时间
     * @param signBeginDate 签收开始时间
     * @param signEndDate 签收结束时间
     * @param documentType 公文类型
     * @param envStatus 反馈情况
     * @param receiveFlag 公文分类
     * @param overTimeFlag 是否超时
     * @param page
     * @param rows
     * @return
     * @return: Page
     */
    public Page selectDocReceiveFinishPage(String title, String senderAncestorName,String signerName, Date sendBeginTime, Date sendEndTime,
    		Date limitBeginTime,Date limitEndTime, Date signBeginDate,Date signEndDate,String documentType,String envStatus
    		,String receiveFlag,String overTimeFlag, Integer page, Integer rows,String sort,String order);
    
    /** 
     * @Title: sendMsg 
     * @Description: 短信发送
     * @param msg 内容
     * @param recieverType 限时公文后台错误时/限时公文工作日获取失败时
     * @return: void
     */
    void sendMsg(String msg,String recieverType);
    
    /** 
     * @Title: sendMessageToReceiver 
     * @Description: 当有新的限时办结签收公文时候，短信通知指定人员
     * @param title
     * @param limitTime
     * @param unit
     * @return
     * @return: boolean
     */
    boolean sendMessageToReceiver(String title, Date limitTime, RemoteUnitIdentifier unit);
    
    /** 
     * @Title: noticeDocSwapSigner 
     * @Description: 提醒签收人反馈情况
     * @param envelope
     * @return: void
     */
    void noticeDocSwapSigner(RemoteDocSwapEnvelope envelope);

    /** 
     * @Title: noticeDocSwapDealer 
     * @Description: 提醒当前办理人办理
     * @param calendar
     * @param overtime
     * @return: void
     */
    void noticeDocSwapDealer(Calendar calendar, boolean overtime);
    
    /** 
     * @Title: noticeByCalendar 
     * @Description: 其他短信模板处理方法
     * @param calendar
     * @param overtime
     * @return: void
     */
    void noticeByCalendar(Calendar calendar,boolean overtime);
    
    /** 
     * @Title: findLimitDocList 
     * @Description: 根据限时时限查询限时办结公文
     * @param calendar
     * @return
     * @return: List<RemoteDocSwapEnvelope>
     */
    List<RemoteDocSwapEnvelope> findLimitDocList(Calendar calendar);
    
    
    void saveFordoForDocSwap(String nextDealers,String nextNodeId,RemoteDocSwapEnvelope envelope);
}
