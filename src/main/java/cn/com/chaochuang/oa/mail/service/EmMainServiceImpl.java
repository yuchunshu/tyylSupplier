/*
 * FileName:    OaLawServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.mail.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.oa.datachange.reference.DataChangeTable;
import cn.com.chaochuang.oa.datachange.reference.OperationType;
import cn.com.chaochuang.oa.datachange.service.DataChangeService;
import cn.com.chaochuang.oa.mail.bean.MailEditBean;
import cn.com.chaochuang.oa.mail.domain.EmAttach;
import cn.com.chaochuang.oa.mail.domain.EmIncepter;
import cn.com.chaochuang.oa.mail.domain.EmMain;
import cn.com.chaochuang.oa.mail.reference.EmailType;
import cn.com.chaochuang.oa.mail.reference.IncType;
import cn.com.chaochuang.oa.mail.reference.IncepterStatus;
import cn.com.chaochuang.oa.mail.reference.MailMesFlag;
import cn.com.chaochuang.oa.mail.reference.MailStatus;
import cn.com.chaochuang.oa.mail.reference.ReceiptFlag;
import cn.com.chaochuang.oa.mail.reference.SaveFlag;
import cn.com.chaochuang.oa.mail.repository.EmMainRepository;
import cn.com.chaochuang.oa.personal.service.OaPersonalGroupService;

/**
 * @author HM
 *
 */
@Service
@Transactional
public class EmMainServiceImpl extends SimpleUuidCrudRestService<EmMain> implements EmMainService {

    @Autowired
    private EmMainRepository       repository;

    @Autowired
    private EmAttachService        attachService;

    @Autowired
    private SysUserService         userService;

    @Autowired
    private DataChangeService      dataChangeService;

    @Autowired
    private OaPersonalGroupService groupService;

    @Autowired
    private EmIncepterService      incepterService;

    @PersistenceContext
    private EntityManager          em;

    @Override
    public SimpleDomainRepository<EmMain, String> getRepository() {
        return this.repository;
    }

    @Override
    public String saveMail(MailEditBean bean) {
        if (bean != null) {
            EmMain mail = null;
            if (bean.getId() != null) {
                mail = this.repository.findOne(bean.getId());
                // 保存数据变动
                dataChangeService.saveDataChange("email_id=" + bean.getId(), DataChangeTable.内部邮件主表, OperationType.修改);
            }
            if (mail == null) {
                mail = new EmMain();
                mail.setSaveDate(new Date());
                if (bean.getSender() == null) {
                    mail.setSender((SysUser) UserTool.getInstance().getCurrentUser());
                } else {
                    mail.setSender(this.userService.findOne(bean.getSender()));
                }
                mail.setMesFlag(MailMesFlag.不留言);
            }
            
            if(null == bean.getReceiptFlag()){
            	bean.setReceiptFlag(ReceiptFlag.否);
            }
            
            mail.setTitle(bean.getTitle());
            mail.setContent(bean.getContent());
            mail.setReceiptFlag(bean.getReceiptFlag());
            MailStatus status = bean.getStatus();
            mail.setStatus(status);

            EmailType emailType = bean.getEmailType();
            mail.setEmailType(emailType);
            mail.setAddress(bean.getManAddress());
            mail.setAddressName(bean.getManAddressNames());
            // if (EmailType.个人邮件.equals(emailType)) {
            // mail.setAddress(bean.getManAddress());
            // mail.setAddressName(bean.getManAddressNames());
            // }
            // if (EmailType.科室邮件.equals(emailType)) {
            // mail.setAddress(bean.getUnitAddress());
            // mail.setAddressName(bean.getUnitAddressNames());
            // }
            // if (EmailType.群组邮件.equals(emailType)) {
            // mail.setAddress(bean.getGroupAddress());
            // mail.setAddressName(bean.getGroupAddressNames());
            // }

            if (MailStatus.已发送.equals(status)) {
                mail.setSaveFlag(SaveFlag.保存);
                mail.setSendDate(new Date());

            } else if (MailStatus.草稿.equals(status)) {
                mail.setSaveFlag(SaveFlag.不保存);
            }

            String attachIds = bean.getAttachIds();
            if (StringUtils.isNotBlank(attachIds)) {
                mail.setAttachFlag(AttachFlag.有附件);
            } else {
                mail.setAttachFlag(AttachFlag.无附件);
            }

            mail = this.repository.save(mail);

            // 接收人信息保存
            if (MailStatus.已发送.equals(status)) {
                String incepterIds = mail.getAddress();
                if (StringUtils.isNotBlank(incepterIds)) {
                    String[] incIdArr = incepterIds.split(",");
                    for (String incId : incIdArr) {
                        SysUser incUser = this.userService.findOne(Long.valueOf(incId));
                        if (incUser == null) {
                            continue;
                        }
                        EmIncepter incepter = new EmIncepter();
                        incepter.setEmailId(mail.getId());
                        incepter.setIncType(IncType.普通收件人);
                        incepter.setStatus(IncepterStatus.未处理);
                        incepter.setIncepter(incUser);
                        this.incepterService.persist(incepter);
                    }
                }
            }

            // 连接附件
            List<String> oldIdsForDel = new ArrayList<String>();
            if (bean.getId() != null) {
                // 旧的附件id
                List<EmAttach> oldAttachs = this.attachService.findByEmailId(bean.getId());
                if (oldAttachs != null) {
                    for (EmAttach a : oldAttachs) {
                        oldIdsForDel.add(a.getId().toString());
                    }
                }
            }
            if (StringUtils.isNotBlank(attachIds)) {
                String[] idArray = StringUtils.split(attachIds, ",");
                for (String aIdStr : idArray) {
                    // Long aId = Long.valueOf(aIdStr);
                    this.attachService.linkAttachAndEmail(aIdStr, mail.getId());
                    if (oldIdsForDel.contains(aIdStr)) {
                        oldIdsForDel.remove(aIdStr);
                    }
                }
            }
            // 删除剩余的
            if (oldIdsForDel.size() > 0) {
                for (String delAttachId : oldIdsForDel) {
                    this.attachService.deleteAttach(delAttachId);
                }
            }
            // System.out.println(mail.getId());

            return mail.getId();
        }
        return null;
    }

    @Override
    public boolean getBack(String emailId) {
        if (emailId != null) {
            EmMain mail = this.repository.findOne(emailId);
            if (mail != null) {
                List<EmIncepter> incepters = this.incepterService.findByEmailId(emailId);
                if (incepters != null && incepters.size() > 0) {
                    List<String> incepterIds = new ArrayList<String>();
                    for (EmIncepter inc : incepters) {
                        // 收件人状态不为未处理不能删除
                        if (!IncepterStatus.未处理.equals(inc.getStatus())) {
                            return false;
                        } else {
                            incepterIds.add(inc.getId());
                        }
                    }
                    // 删除所有收件人
                    for (String incId : incepterIds) {
                        this.incepterService.delete(incId);
                    }
                    // 保存数据变动
                    dataChangeService.saveDataChange("email_id=" + emailId, DataChangeTable.邮件接收表, OperationType.删除);
                    // 将邮件存放到草稿箱
                    mail.setStatus(MailStatus.草稿);
                    mail.setSendDate(null);
                    mail.setSaveFlag(SaveFlag.不保存);
                    this.repository.save(mail);
                    // 保存数据变动
                    dataChangeService.saveDataChange("email_id=" + emailId, DataChangeTable.内部邮件主表, OperationType.修改);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public EmMain forwardingMail(String emailId) {
        EmMain mail = this.repository.findOne(emailId);
        if (mail != null) {
            EmMain forwarding = new EmMain(); // 转发
            forwarding.setAttachFlag(mail.getAttachFlag());
            forwarding.setContent(mail.getContent());
            forwarding.setEmailType(mail.getEmailType());
            forwarding.setMesFlag(MailMesFlag.不留言);
            forwarding.setSaveDate(new Date());
            forwarding.setSender((SysUser) UserTool.getInstance().getCurrentUser());
            forwarding.setTitle("转发:" + mail.getTitle());
            return forwarding;
        }
        return null;
    }

    @Override
    public void readMail(String emailId, Long receiveUserId) {
        EmMain mail = this.repository.findOne(emailId);
        if (mail != null) {
            EmIncepter incepter = this.incepterService.findByEmailIdAndIncepterId(emailId, receiveUserId);
            if (incepter != null && IncepterStatus.未处理.equals(incepter.getStatus())) {
                if (ReceiptFlag.是.equals(mail.getReceiptFlag())) {
                    EmMain receipt = new EmMain(); // 回执
                    // 回执收件人
                    SysUser receiptUser = mail.getSender();

                    if (receiptUser != null) {
                        receipt.setAddress(receiptUser.getId().toString());
                        receipt.setAddressName(receiptUser.getUserName());
                    }
                    if (receiveUserId != null) {
                        receipt.setSender(this.userService.findOne(receiveUserId));
                    }
                    receipt.setContent("邮件已阅读！");
                    receipt.setMesFlag(mail.getMesFlag());
                    receipt.setReceiptFlag(ReceiptFlag.否);
                    receipt.setSaveDate(new Date());
                    receipt.setTitle("回执:" + mail.getTitle());
                    receipt.setSaveFlag(SaveFlag.不保存);
                    receipt.setSendDate(new Date());
                    receipt.setMesFlag(MailMesFlag.不留言);
                    receipt.setStatus(MailStatus.已发送);
                    this.repository.save(receipt);

                    // 接收人信息保存
                    EmIncepter recIncepter = new EmIncepter();
                    recIncepter.setEmailId(receipt.getId());
                    recIncepter.setIncType(IncType.普通收件人);
                    recIncepter.setStatus(IncepterStatus.未处理);
                    SysUser incUser = this.userService.findOne(Long.valueOf(receipt.getAddress()));
                    recIncepter.setIncepter(incUser);
                    this.incepterService.persist(recIncepter);

                }
                // 修改原接收人状态
                incepter.setStatus(IncepterStatus.已收件);
                this.incepterService.persist(incepter);

                // 保存数据变动
                dataChangeService.saveDataChange("id=" + incepter.getId(), DataChangeTable.邮件接收表, OperationType.修改);

            }
        }
    }

    @Override
    public EmMain mailReplay(String emailId) {
        EmMain mail = this.repository.findOne(emailId);
        if (mail != null) {
            EmMain replay = new EmMain(); // 回复

            // 收件人
            SysUser replayUser = mail.getSender();
            if (replayUser != null) {
                replay.setAddress(replayUser.getId().toString());
                replay.setAddressName(replayUser.getUserName());
            }
            replay.setEmailType(EmailType.个人邮件);
            replay.setMesFlag(MailMesFlag.不留言);
            replay.setSaveDate(new Date());
            replay.setSender((SysUser) UserTool.getInstance().getCurrentUser());
            replay.setTitle("回复:" + mail.getTitle());
            return replay;
        }
        return null;
    }

    @Override
    public boolean delMail(String emailIds) {
        if (StringUtils.isNotBlank(emailIds)) {
            String idArr[] = emailIds.split(",");
            for (String id : idArr) {
                EmMain mail = this.repository.findOne(id);
                if (mail != null) {
                    // mail.setSaveFlag(SaveFlag.不保存);
                    mail.setStatus(MailStatus.逻辑删除);
                    this.repository.save(mail);
                    // 保存数据变动
                    dataChangeService.saveDataChange("email_id=" + mail.getId(), DataChangeTable.内部邮件主表,
                                    OperationType.修改);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.oa.mail.service.EmMainService#selectMailOutbox(java.lang.String)
     */
    @Override
    public List<EmMain> selectMailOutbox(String lastOutputTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = null;
        List<EmMain> list = null;
        try {
            if (Tools.isEmptyString(lastOutputTime)) {
                list = this.repository.selectEmMainById(0l);
            } else {
                time = sdf.parse(lastOutputTime);
                list = this.repository.selectEmMainBysaveDate(time);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }
}
