/*
 * FileName:    MailWebService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年5月25日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.webservice.service;

import javax.jws.WebService;

/**
 * @author huangwq
 *
 */

@WebService
public interface MailWebService {
    public String sendMail(String emailId, String address, String addressName, String content, String subject,
                    String status, String userId, String emailType);

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
    public String recoverMail(String trashId, String userId);

    /**
     * 邮件详细信息
     *
     * @param emailId
     * @param userId
     * @return
     * @Author:huangwq
     * @Since:2016-5-24
     *
     */
    public String readMailInfo(String emailId, String userId);

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
    public String mailTranTemp(String emailIds, String userId);

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
    public String mailTranRecieve(String emailIds, String userId);

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
    public String revokeMail(String emailIds, String userId);

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
    public String delMail(String emailIds, String userId);

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
    public String selectMailDrafts(String pageSize, String pageNumber, String userId);

    /***
     * 删除发件箱邮件
     *
     * @param emailIds
     * @param userId
     * @return
     */

    public String delSendMail(String emailIds, String userId);

    /***
     * 移动存档箱邮件到垃圾箱
     *
     * @param emailIds
     *            邮件id
     * @param userId
     *            当前用户id
     * @return
     */
    public String mailTranPige(String emailIds, String userId);

    /***
     * 删除存档箱邮件
     *
     * @param emailIds
     *            邮件id
     * @param userId
     *            当前用户id
     * @return
     */
    public String delPigeMail(String emailIds, String userId);

    /***
     * 移动草稿箱邮件到垃圾箱
     *
     * @param emailIds
     *            邮件id
     * @param userId
     *            当前用户id
     * @return
     */
    public String mailTranDtn(String emailIds, String userId);

    /**
     * 收件箱未读邮件条数
     *
     * @param userId
     *            当前用户id
     * @return
     */
    public String mailReCount(String userId);

    /**
     * 获取邮箱收件箱信息
     *
     * @param lastOutputTime
     * @return
     */
    public String getOAMailInboxInfo(String lastoutputTime);

    /**
     * 获取邮箱发件箱信息
     *
     * @param lastOutputTime
     * @return
     */
    public String getOAMailOutboxInfo(String lastoutputTime);

    /**
     * 获取邮件的附件
     *
     * @param fileName
     * @param offset
     * @param reads
     * @return
     */
    public byte[] uploadStreamAttachFile(String fileName, Long offset, Integer reads);

    /**
     * 回复邮件
     *
     * @param rmMailId
     * @param content
     * @param subject
     * @return
     */
    public String ReplyMail(String rmMailId, String content, String subject);

    /**
     * 邮件数据变更（删除或移动）
     *
     * @param changeScript
     * @return
     */
    public String getChangeEmail(String changeScript);

}
