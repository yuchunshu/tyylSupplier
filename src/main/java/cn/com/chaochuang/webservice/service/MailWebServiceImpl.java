/*
 * FileName:    TestWebService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年5月23日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.webservice.service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.com.chaochuang.common.beancopy.BeanCopyUtil;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.oa.mail.bean.MailEditBean;
import cn.com.chaochuang.oa.mail.domain.EmAttach;
import cn.com.chaochuang.oa.mail.domain.EmIncepter;
import cn.com.chaochuang.oa.mail.domain.EmMain;
import cn.com.chaochuang.oa.mail.reference.EmailType;
import cn.com.chaochuang.oa.mail.reference.IncepterStatus;
import cn.com.chaochuang.oa.mail.reference.MailStatus;
import cn.com.chaochuang.oa.mail.reference.ReceiptFlag;
import cn.com.chaochuang.oa.mail.service.EmAttachService;
import cn.com.chaochuang.oa.mail.service.EmDustbinService;
import cn.com.chaochuang.oa.mail.service.EmIncepterService;
import cn.com.chaochuang.oa.mail.service.EmMainService;
import cn.com.chaochuang.webservice.bean.MailAttachInfo;
import cn.com.chaochuang.webservice.bean.MailInboxInfo;
import cn.com.chaochuang.webservice.bean.MailOutboxInfo;
import cn.com.chaochuang.webservice.utils.EmUtils;
import cn.com.chaochuang.webservice.utils.JsonDateValueProcessor;

/**
 * @author huangwq
 *
 */
@Component
@WebService
public class MailWebServiceImpl implements MailWebService {

    @Autowired
    private EmMainService     mailService;
    @Autowired
    private SysUserService    userService;

    @Autowired
    private EmIncepterService emIncepterService;

    @Autowired
    private EmAttachService   emAttachService;

    @Autowired
    private EmDustbinService  dustbinService;

    private Long              maxLen = Long.valueOf(50);

    @Value("${upload.rootpath}")
    private String            rootPath;

    /**
     * 邮件发送
     *
     * @param mailId
     * @param address
     * @param addressName
     * @param attachId
     * @param bccAdd
     * @param bccAddName
     * @param content
     * @param copyAdd
     * @param copyAddName
     * @param receiptFlag
     * @param title
     * @param status
     * @param userId
     * @return
     * @Author:huangwq
     * @Since:2016-5-24
     *
     */
    public String sendMail(String emailId, String address, String addressName, String content, String subject,
                    String status, String userId, String emailType) {
        String opType = status;
        MailEditBean info = new MailEditBean();
        if (!Tools.isEmptyString(emailId)) {
            info.setId(emailId);
        }
        info.setManAddress(address);
        info.setManAddressNames(addressName);
        info.setContent(content);
        info.setTitle(subject);
        // 手机端暂时为个人邮件
        info.setEmailType(EmailType.个人邮件);
        info.setSender(Long.valueOf(userId));
        if (opType.equals("1")) {// 保存到草稿箱
            info.setStatus(MailStatus.草稿);
        }
        if (opType.equals("2")) {// 发送
            info.setStatus(MailStatus.已发送);
        }
        // 默认不回执
        info.setReceiptFlag(ReceiptFlag.否);
        String mailId = this.mailService.saveMail(info);

        // 把邮件id返回手机端保存

        return mailId.toString();
    }

    /**
     * 垃圾箱邮件恢复
     *
     * @param trashId
     * @param userId
     * @return
     * @Author:huangwq
     * @Since:2016-5-24
     *
     */
    public String recoverMail(String trashId, String userId) {
        return null;
    }

    /**
     * 阅读邮件
     *
     * @param emailId
     * @param userId
     * @return
     * @Author:huangwq
     * @Since:2016-5-24
     *
     */
    public String readMailInfo(String emailId, String userId) {
        this.mailService.readMail(emailId, Long.valueOf(userId));
        return "";
    }

    /**
     * 存档
     *
     * @param emailIds
     * @param userId
     * @return
     * @Author:huangwq
     * @Since:2016-5-24
     *
     */
    public String mailTranTemp(String emailIds, String userId) {
        return "";
    }

    /**
     * 移到垃圾箱
     *
     * @param emailIds
     * @param userId
     * @return
     * @Author:huangwq
     * @Since:2016-5-24
     *
     */
    public String mailTranRecieve(String emailIds, String userId) {
        dustbinService.inboxTrash(emailIds, Long.valueOf(userId));
        return null;
    }

    /**
     * 撤回
     *
     * @param emailIds
     * @param userId
     * @return
     * @Author:huangwq
     * @Since:2016-5-24
     *
     */
    public String revokeMail(String emailIds, String userId) {
        return null;
    }

    /**
     * 删除垃圾箱邮件
     *
     * @param emailIds
     * @param userId
     * @return
     * @Author:huangwq
     * @Since:2016-5-24
     *
     */
    public String delMail(String emailIds, String userId) {
        return null;
    }

    /**
     * 邮件垃圾箱
     *
     * @param pageSize
     * @param pageNumber
     * @param userId
     * @return
     * @Author:huangwq
     * @Since:2016-5-24
     *
     */
    public String selectMailDrafts(String pageSize, String pageNumber, String userId) {
        return null;
    }

    /***
     * 删除发件箱邮件
     *
     * @param emailIds
     * @param userId
     * @return
     */

    public String delSendMail(String emailIds, String userId) {
        this.mailService.delMail(emailIds);
        return null;
    }

    /***
     * 移动存档箱邮件到垃圾箱
     *
     * @param emailIds
     *            邮件id
     * @param userId
     *            当前用户id
     * @return
     */
    public String mailTranPige(String emailIds, String userId) {
        return null;
    }

    /***
     * 删除存档箱邮件
     *
     * @param emailIds
     *            邮件id
     * @param userId
     *            当前用户id
     * @return
     */
    public String delPigeMail(String emailIds, String userId) {
        return null;
    }

    /***
     * 移动草稿箱邮件到垃圾箱
     *
     * @param emailIds
     *            邮件id
     * @param userId
     *            当前用户id
     * @return
     */
    public String mailTranDtn(String emailIds, String userId) {
        dustbinService.draftsTrash(emailIds);
        return null;
    }

    /**
     * 收件箱未读邮件条数
     *
     * @param userId
     *            当前用户id
     * @return
     */
    public String mailReCount(String userId) {
        return null;
    }

    /**
     * 获取邮箱收件箱信息
     *
     * @param lastOutputTime
     * @return
     */
    public String getOAMailInboxInfo(String lastOutputTime) {
        String json = "";
        JSONObject jsonObject = null;
        try {
            List<EmIncepter> list = this.emIncepterService.selectMailInbox(lastOutputTime);
            if (list == null)
                return "";
            MailInboxInfo inbox;
            for (EmIncepter incepter : list) {
                inbox = new MailInboxInfo();
                // 接收人用户
                SysUser user = incepter.getIncepter();
                if (user != null) {
                    inbox.setReceiveName(user.getUserName());
                    inbox.setReceiveUserId(Long.valueOf(user.getId()));
                }
                if (incepter.getMail() != null) {
                    EmMain emMain = incepter.getMail();
                    inbox.setInputTime(emMain.getSaveDate());
                    inbox.setSubject(emMain.getTitle());
                    inbox.setContent(emMain.getContent());
                    inbox.setDigest(EmUtils.getDigest(emMain.getContent(), maxLen.intValue()));
                    if (emMain.getReceiptFlag() != null) {
                        inbox.setIsReply(emMain.getReceiptFlag().getKey());
                    }
                    inbox.setReceiveTime(emMain.getSaveDate());
                    if (emMain.getSender() != null) {
                        inbox.setSendName(emMain.getSender().getUserName());
                        inbox.setSendUserId(emMain.getSender().getId());
                    }
                    inbox.setRmMailId(emMain.getId().toString());
                    if (emMain.getId() != null) {
                        inbox.setMessageId(incepter.getEmailId().toString());
                        this.getMailAttachments(inbox, incepter.getEmailId());
                    }
                }
                if (IncepterStatus.已收件.equals(incepter.getStatus())) {
                    inbox.setIsRead(MailInboxInfo.READ);
                } else {
                    inbox.setIsRead(MailInboxInfo.NOTREAD);
                }
                jsonObject = this.getJSONObject(inbox, true);
                json += jsonObject.toString() + ",";
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        if (!json.equals("")) {
            return "[" + json.substring(0, json.length() - 1) + "]";
        }
        return json;
    }

    /**
     * 获取邮箱发件箱信息
     *
     * @param lastOutputTime
     * @return
     */
    public String getOAMailOutboxInfo(String lastoutputTime) {
        String json = "";
        JSONObject jsonObject = null;
        try {
            List<EmMain> list = this.mailService.selectMailOutbox(lastoutputTime);
            if (list == null)
                return "";
            MailOutboxInfo outbox;
            for (EmMain email : list) {
                outbox = new MailOutboxInfo();
                outbox.setSubject(EmUtils.filterHtmlTag(email.getTitle()));
                outbox.setContent(EmUtils.filterHtmlTag(email.getContent()));
                outbox.setInputTime(email.getSaveDate());
                outbox.setSendTime(email.getSendDate());
                outbox.setSendUserId(email.getSender().getId());
                outbox.setReceiveAddr(email.getAddress());
                outbox.setDigest(EmUtils.getDigest(email.getContent(), maxLen.intValue()));
                outbox.setRmMailId(email.getId());
                outbox.setReceiveName(email.getAddressName());
                outbox.setStatus(email.getStatus().getKey());
                outbox.setSendName(email.getSender().getUserName());
                if (email.getId() != null) {
                    // 获取邮件附件信息
                    this.getMailAttachments(outbox, email.getId());
                }
                jsonObject = this.getJSONObject(outbox, true);
                json += jsonObject.toString() + ",";
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        if (!json.equals("")) {
            return "[" + json.substring(0, json.length() - 1) + "]";
        }
        return json;
    }

    /**
     * 获取邮件的附件
     *
     * @param fileName
     * @param offset
     * @param reads
     * @return
     */
    public byte[] uploadStreamAttachFile(String fileName, Long offset, Integer reads) {
        RandomAccessFile randomFile = null;
        byte[] buffer = new byte[reads];
        try {
            File file = new File(fileName);
            // 文件不存在
            if (!file.exists()) {
                return new byte[0];
            }
            // 当文件获取偏移值超过文件大小则不进行读取
            if (offset >= file.length()) {
                return new byte[0];
            }
            // 算出适合的缓存读取大小
            if ((offset + reads) > file.length()) {
                reads = Long.valueOf(file.length() - offset).intValue();
            }
            randomFile = new RandomAccessFile(fileName, "r");
            randomFile.seek(offset);
            int readed = randomFile.read(buffer);
            // 文件已经上传完
            if (readed == -1) {
                return new byte[0];
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException exf) {
                    return new byte[0];
                }
            }
        }
        return buffer;
    }

    /**
     * 回复邮件
     *
     * @param rmMailId
     * @param content
     * @param subject
     * @return
     */
    public String ReplyMail(String rmMailId, String content, String subject) {
        if (StringUtils.isNotBlank(rmMailId)) {
            EmMain em = this.mailService.mailReplay(rmMailId);
            MailEditBean info = new MailEditBean();
            if (em != null) {
                info = BeanCopyUtil.copyBean(em, MailEditBean.class);
            }
            info.setContent(content);
            info.setTitle(subject);
            this.mailService.saveMail(info);
        }
        return "";
    }

    /**
     * 邮件数据变更（删除或移动）
     *
     * @param changeScript
     * @return
     */
    public String getChangeEmail(String changeScript) {
        String json = "";
        JSONObject jsonObject = null;
        if (Tools.isEmptyString(changeScript)) {
            return json;
        }
        String[] items = changeScript.split("=");
        // 记录标识字段必须为id或者为email_id
        if (items.length != 2 || (!items[0].equals("id") && !items[0].equals("email_id"))) {
            return json;
        }
        try {
            // 根据id获取mailOutboxInfo对象，再将mailOutboxInfo转成json字符串
            if (items[0].equals("id")) {
                String id = (items[0].equals("id")) ? items[1] : null;
                EmIncepter emIncepter = emIncepterService.findOne(id);
                MailInboxInfo inbox = new MailInboxInfo();
                if (emIncepter.getMail() != null) {
                    EmMain emMain = emIncepter.getMail();
                    inbox.setInputTime(emMain.getSaveDate());
                    inbox.setSubject(emMain.getTitle());
                    inbox.setContent(emMain.getContent());
                    inbox.setDigest(EmUtils.getDigest(emMain.getContent(), maxLen.intValue()));
                    if (emMain.getReceiptFlag() != null) {
                        inbox.setIsReply(emMain.getReceiptFlag().getKey());
                    }
                    inbox.setReceiveTime(emMain.getSaveDate());
                    if (emMain.getSender() != null) {
                        inbox.setSendName(emMain.getSender().getUserName());
                        inbox.setSendUserId(emMain.getSender().getId());
                    }
                    inbox.setRmMailId(emMain.getId().toString());
                    if (emMain.getId() != null) {
                        this.getMailAttachments(inbox, emIncepter.getEmailId());
                    }
                }
                if (IncepterStatus.已收件.equals(emIncepter.getStatus())) {
                    inbox.setIsRead(MailInboxInfo.READ);
                } else {
                    inbox.setIsRead(MailInboxInfo.NOTREAD);
                }
                inbox.setStatus(emIncepter.getStatus().getKey());
                inbox.setRmMailId(emIncepter.getEmailId().toString());
                inbox.setReceiveUserId(emIncepter.getIncepter().getId());
                jsonObject = this.getJSONObject(inbox, true);
                json += jsonObject.toString() + ",";
            }
            if (items[0].equals("email_id")) {
                String emailId = (items[0].equals("email_id")) ? items[1] : null;
                EmMain emain = mailService.findOne(emailId);
                MailOutboxInfo outbox = new MailOutboxInfo();
                if (emain != null) {
                    // 邮件状态修改为逻辑删除
                    outbox.setStatus(emain.getStatus().getKey());
                    outbox.setRmMailId(emain.getId());
                    outbox.setSendUserId(emain.getSender().getId());
                    jsonObject = this.getJSONObject(outbox, true);
                    json += jsonObject.toString() + ",";
                }
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return json;
    }

    /**
     * 获取邮件附件信息
     *
     * @param file
     * @param instId
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void getMailAttachments(Object file, String emailId) {
        List<EmAttach> attachLists = emAttachService.findByEmailId(emailId);
        if (attachLists == null || attachLists.isEmpty()) {
            return;
        }
        for (EmAttach item : attachLists) {
            MailAttachInfo attachment = new MailAttachInfo();
            // 默认为非图文附件
            attachment.setIsImage("0");
            attachment.setFileSize(item.getFileSize());
            attachment.setSaveName(item.getSaveName());
            attachment.setTrueName(item.getTrueName());
            attachment.setSavePath(this.rootPath + "/" + item.getSavePath());
            attachment.setRmAttachId(item.getId().toString());
            if (file instanceof MailInboxInfo) {
                MailInboxInfo inbox = (MailInboxInfo) file;
                inbox.getRemoteAttachs().add(attachment);
            }
            if (file instanceof MailOutboxInfo) {
                MailOutboxInfo outbox = (MailOutboxInfo) file;
                outbox.getRemoteAttachs().add(attachment);
            }
        }
    }

    /**
     * 将transObject对象转换成JSONObject对象
     *
     * @param transObject
     * @return
     */
    private JSONObject getJSONObject(Object transObject, boolean formatTime) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(formatTime));
        return JSONObject.fromObject(transObject, jsonConfig);
    }

}
