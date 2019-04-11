/*
 * FileName:    AppMessageServiceImpl.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.message.app.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.oa.message.app.domain.OaAppMessage;
import cn.com.chaochuang.oa.message.app.reference.MesStatus;
import cn.com.chaochuang.oa.message.app.reference.MesType;
import cn.com.chaochuang.oa.message.app.repository.AppMessageRepository;

/**
 * @author HM
 *
 */
@Service
@Transactional
public class AppMessageServiceImpl extends SimpleLongIdCrudRestService<OaAppMessage> implements AppMessageService {

    @Value(value = "${messageUrl.mailPath}")
    private String              mailPath;

    @Autowired
    private AppMessageRepository repository;

    @Autowired
    private SysUserService       uService;

    @Override
    public SimpleDomainRepository<OaAppMessage, Long> getRepository() {
        return this.repository;
    }

    @Override
    public int updateMsg(Long userId, MesType mesType) {
        List<OaAppMessage> msgList = repository.findByIncepterIdAndMesTypeAndStatus(userId, mesType, MesStatus.未提示);
        if (msgList != null && msgList.size() > 0) {
            for (OaAppMessage msg : msgList) {
                msg.setStatus(MesStatus.已提示);
                msg.setArrivalTime(new Date());
                this.persist(msg);
            }
            return msgList.size();
        }
        return 0;
    }

    @Override
    public String saveUserMessage(Long userId) {
        List<OaAppMessage> msgList = repository.findByIncepterIdAndStatus(userId, MesStatus.未提示);
        StringBuffer messageInfo = new StringBuffer();
        if (msgList != null && msgList.size() > 0) {
            messageInfo.append("您收到 ").append(msgList.size()).append(" 条信息：").append('\r').append('\n');
            for (OaAppMessage msg : msgList) {
                messageInfo.append(msg.getContent()).append('\r').append('\n');
                msg.setStatus(MesStatus.已提示);
                msg.setArrivalTime(new Date());
                this.persist(msg);
            }
        }
        return messageInfo.toString();
    }

    @Override
    public void insertOaAppMsg(Long incepterId, Long senderId, String content, MesType mesType) {
        OaAppMessage message = new OaAppMessage(incepterId, senderId, content, mesType, new Date(), MesStatus.未提示,
                        YesNo.否);
        switch (mesType) {

        case 邮件:
            message.setUrl("appId=1&tabTitle=收件箱&tabUrl=" + mailPath);
            break;

        default:
            break;
        }
        this.persist(message);
    }

    @Override
    public void insertOaAppMsg(Long incepterId, Long senderId, String content, MesType mesType, String url) {
        OaAppMessage message = new OaAppMessage(incepterId, senderId, content, mesType, new Date(), MesStatus.未提示,
                        YesNo.否, url);
        message.setCreateTime(new Date());
        this.persist(message);
    }

    @Override
    public Long readMsg(Long id) {
        if (id != null) {
            OaAppMessage msg = repository.findOne(id);
            if (msg != null) {
                msg.setIsRead(YesNo.是);
                this.persist(msg);
                return msg.getId();
            }
        }
        return null;
    }

    @Override
    public int countNotRead() {
        String curUserId = UserTool.getInstance().getCurrentUserId();
        if (StringUtils.isNotBlank(curUserId)) {
            Long incepterId = Long.valueOf(curUserId);
            List list = this.repository.findByIsReadAndIncepterId(YesNo.否, incepterId);
            if (list != null) {
                return list.size();
            }
        }
        return 0;
    }

    @Override
    public void readMsgs(Long[] ids) {

        if (ids != null && ids.length > 0) {
            List<OaAppMessage> list = repository.findAll(Arrays.asList(ids));
            if (list != null && list.size() > 0) {
                for (OaAppMessage mes : list) {
                    mes.setIsRead(YesNo.是);
                    this.persist(mes);
                }
            }
        }

    }

    @Override
    public void cleanAll() {
        String curUserId = UserTool.getInstance().getCurrentUserId();
        if (StringUtils.isNotBlank(curUserId)) {
            List<OaAppMessage> msgList = repository.findByIncepterId(Long.valueOf(curUserId));
            if (msgList != null && msgList.size() > 0) {
                repository.delete(msgList);
            }
        }
    }

    @Override
    public void cleanExceptLastMonth() {
        List<OaAppMessage> msgList = repository.findAll(new Sort(Direction.DESC, "createTime"));
        if (msgList != null && msgList.size() > 0) {
            Date lastDate = msgList.get(0).getCreateTime(); // 所有记录中最大的创建日期
            Calendar cal = Calendar.getInstance();
            cal.setTime(lastDate);
            cal.set(Calendar.DAY_OF_MONTH, 1); // 设置为该日期所属月份的1号
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MINUTE, 0);
            Date lastMonthDate = cal.getTime();
            String curUserId = UserTool.getInstance().getCurrentUserId();
            if (StringUtils.isNotBlank(curUserId)) {
                List<OaAppMessage> deleteMsgs = repository.findByIncepterIdAndCreateTimeLessThan(Long.valueOf(curUserId),
                                lastMonthDate); // 查询所有比该月一号要小的记录
                if (deleteMsgs != null && deleteMsgs.size() > 0) {
                    repository.delete(deleteMsgs); // 删除
                }
            }
        }
    }

    @Override
    public void readAll() {
        String curUserId = UserTool.getInstance().getCurrentUserId();
        if (StringUtils.isNotBlank(curUserId)) {
            List<OaAppMessage> msgList = repository.findByIsReadAndIncepterId(YesNo.否, Long.valueOf(curUserId));
            if (msgList != null && msgList.size() > 0) {
                for (OaAppMessage msg : msgList) {
                    msg.setIsRead(YesNo.是);
                    this.persist(msg);
                }
            }
        }
    }

    @Override
    public void cleanExceptLastThirty() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -30); // 设置为30天前的日期
        // 清空时分秒
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        Date lastMonthDate = cal.getTime();
        String curUserId = UserTool.getInstance().getCurrentUserId();
        if (StringUtils.isNotBlank(curUserId)) {
            List<OaAppMessage> deleteMsgs = repository.findByIncepterIdAndCreateTimeLessThan(Long.valueOf(curUserId),
                            lastMonthDate); // 查询所有30天以前的记录
            if (deleteMsgs != null && deleteMsgs.size() > 0) {
                repository.delete(deleteMsgs); // 删除
            }
        }
    }
}
