/*
 * FileName:    EmDustbinServiceImpl.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.mail.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.oa.datachange.reference.DataChangeTable;
import cn.com.chaochuang.oa.datachange.reference.OperationType;
import cn.com.chaochuang.oa.datachange.service.DataChangeService;
import cn.com.chaochuang.oa.mail.bean.MailShowBean;
import cn.com.chaochuang.oa.mail.domain.EmDustbin;
import cn.com.chaochuang.oa.mail.domain.EmIncepter;
import cn.com.chaochuang.oa.mail.domain.EmMain;
import cn.com.chaochuang.oa.mail.domain.EmTempStorage;
import cn.com.chaochuang.oa.mail.reference.DelFlag;
import cn.com.chaochuang.oa.mail.reference.IncepterStatus;
import cn.com.chaochuang.oa.mail.reference.IsNeedback;
import cn.com.chaochuang.oa.mail.reference.MailStatus;
import cn.com.chaochuang.oa.mail.reference.ReceiptFlag;
import cn.com.chaochuang.oa.mail.repository.EmDustbinRepository;

/**
 * @author HM
 *
 */
@Service
@Transactional
public class EmDustbinServiceImpl extends SimpleUuidCrudRestService<EmDustbin> implements EmDustbinService {

    @Autowired
    private EmDustbinRepository  repository;

    @Autowired
    private EmMainService        emMainService;

    @Autowired
    private EmIncepterService    incepterService;

    @Autowired
    private EmTempStorageService tempStorageService;

    @Autowired
    private DataChangeService    dataChangeService;

    @PersistenceContext
    private EntityManager        em;

    @Override
    public SimpleDomainRepository<EmDustbin, String> getRepository() {
        return this.repository;
    }

    @Override
    public boolean draftsTrash(String mailIds) {
        if (StringUtils.isNotBlank(mailIds)) {
            String[] mailIdArr = mailIds.split(",");
            for (String mailId : mailIdArr) {
                EmMain mail = this.emMainService.findOne(mailId);
                if (mail != null) {
                    EmDustbin dustbin = new EmDustbin();
                    dustbin.setAttachFlag(mail.getAttachFlag());
                    dustbin.setContent(mail.getContent());
                    dustbin.setDelDate(new Date());
                    dustbin.setEmailId(mail.getId());
                    if (ReceiptFlag.是.equals(mail.getReceiptFlag())) {
                        dustbin.setIsNeedback(IsNeedback.回执);
                    } else {
                        dustbin.setIsNeedback(IsNeedback.不回执);
                    }
                    dustbin.setSendDate(mail.getSendDate());
                    dustbin.setSender(mail.getSender());
                    dustbin.setSenderName(mail.getSender().getUserName());
                    dustbin.setTitle(mail.getTitle());
                    this.repository.save(dustbin);

                    mail.setStatus(MailStatus.垃圾);
                    this.emMainService.persist(mail);

                    // 保存数据变动
                    dataChangeService.saveDataChange("email_id=" + mailId, DataChangeTable.内部邮件主表, OperationType.修改);
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean inboxTrash(String mailIds, Long incepterId) {
        if (StringUtils.isNotBlank(mailIds)) {
            String[] mailIdArr = mailIds.split(",");
            for (String mailId : mailIdArr) {
                EmIncepter emIncepter = this.incepterService.findByEmailIdAndIncepterId(mailId, incepterId);
                if (emIncepter != null) {
                    EmMain mail = emIncepter.getMail();
                    if (mail != null) {
                        EmDustbin dustbin = new EmDustbin();
                        dustbin.setAttachFlag(mail.getAttachFlag());
                        dustbin.setContent(mail.getContent());
                        dustbin.setDelDate(new Date());
                        dustbin.setEmailId(mail.getId());
                        if (ReceiptFlag.是.equals(mail.getReceiptFlag())) {
                            dustbin.setIsNeedback(IsNeedback.回执);
                        } else {
                            dustbin.setIsNeedback(IsNeedback.不回执);
                        }
                        dustbin.setSendDate(mail.getSendDate());
                        dustbin.setSender(mail.getSender());
                        dustbin.setSenderName(mail.getSender().getUserName());
                        dustbin.setTitle(mail.getTitle());

                        dustbin.setIncepter(emIncepter.getIncepter());
                        dustbin.setReadDate(emIncepter.getReadDate());
                        dustbin.setWritebackDate(emIncepter.getWritebackDate());
                        this.repository.save(dustbin);

                        emIncepter.setStatus(IncepterStatus.垃圾);
                        incepterService.persist(emIncepter);

                        // 保存数据变动
                        dataChangeService.saveDataChange("id=" + emIncepter.getId(), DataChangeTable.邮件接收表,
                                        OperationType.修改);
                    }
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean archiveTrash(String tmpIds) {
        if (StringUtils.isNotBlank(tmpIds)) {
            String[] tmpIdArr = tmpIds.split(",");
            for (String tmpId : tmpIdArr) {
                EmTempStorage tempStorage = this.tempStorageService.findOne(tmpId);
                if (tempStorage != null) {
                    EmDustbin dustbin = new EmDustbin();
                    dustbin.setAttachFlag(tempStorage.getAttachFlag());
                    dustbin.setContent(tempStorage.getContent());
                    dustbin.setDelDate(new Date());
                    dustbin.setEmailId(tempStorage.getEmailId());
                    dustbin.setIncepter(tempStorage.getIncepter());
                    dustbin.setIsNeedback(tempStorage.getIsNeedback());
                    dustbin.setReadDate(tempStorage.getReadDate());
                    dustbin.setSendDate(tempStorage.getSendDate());
                    dustbin.setSender(tempStorage.getSender());
                    if (tempStorage.getSender() != null) {
                        dustbin.setSenderName(tempStorage.getSender().getUserName());
                    }
                    dustbin.setTitle(tempStorage.getTitle());
                    dustbin.setWritebackDate(tempStorage.getWritebackDate());
                    this.repository.save(dustbin);
                    this.tempStorageService.delete(tempStorage);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean recoverTrash(String delIds, Long incepterId) {
        if (StringUtils.isNotBlank(delIds)) {
            EmMain emMain;
            EmDustbin emDustbin;
            EmIncepter emIncepter;
            String[] delIdArr = delIds.split(",");
            for (String delId : delIdArr) {
                emDustbin = this.findOne(delId);
                if (emDustbin != null) {
                    emIncepter = this.incepterService.findByEmailIdAndIncepterId(emDustbin.getEmailId(), incepterId);
                    emMain = this.emMainService.findOne(emDustbin.getEmailId());
                    // 收件人为空 ： 恢复至草稿箱
                    if (emIncepter == null) {
                        if (emMain != null) {
                            emMain.setStatus(MailStatus.草稿);
                            this.emMainService.persist(emMain);
                            // 保存数据变动
                            dataChangeService.saveDataChange("email_id=" + emMain.getId(), DataChangeTable.内部邮件主表,
                                            OperationType.修改);
                        }
                    } else {
                        IncepterStatus incStatus = emIncepter.getStatus();
                        // 收件人状态为存档，恢复至存档箱
                        if (IncepterStatus.存档.equals(incStatus)) {
                            EmTempStorage emTempStorage = new EmTempStorage();
                            emTempStorage.setAttachFlag(emDustbin.getAttachFlag());
                            emTempStorage.setContent(emDustbin.getContent());
                            emTempStorage.setEmailId(emDustbin.getEmailId());
                            emTempStorage.setIncepter(emDustbin.getIncepter());
                            emTempStorage.setIsNeedback(emDustbin.getIsNeedback());
                            emTempStorage.setPigeDate(new Date());
                            emTempStorage.setReadDate(emDustbin.getReadDate());
                            emTempStorage.setSendDate(emDustbin.getSendDate());
                            emTempStorage.setSender(emDustbin.getSender());
                            emTempStorage.setTitle(emDustbin.getTitle());
                            emTempStorage.setWritebackDate(emDustbin.getWritebackDate());
                            this.tempStorageService.persist(emTempStorage);
                            emMain.setStatus(MailStatus.归档);
                            this.emMainService.persist(emMain);
                            // 保存数据变动
                            dataChangeService.saveDataChange("email_id=" + emMain.getId(), DataChangeTable.内部邮件主表,
                                            OperationType.修改);
                        } else {
                            // 未读和已读信件恢复到收件箱
                            emIncepter.setStatus(IncepterStatus.已收件);
                            this.incepterService.persist(emIncepter);
                            // 保存数据变动
                            dataChangeService.saveDataChange("id=" + emIncepter.getId(), DataChangeTable.邮件接收表,
                                            OperationType.修改);
                        }
                    }
                    this.repository.delete(emDustbin);
                }
                emMain = null;
                emDustbin = null;
                emIncepter = null;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteTrash(String delIds, Long incepterId) {

        if (StringUtils.isNotBlank(delIds)) {
            EmMain emMain;
            EmDustbin emDustbin;
            EmIncepter emIncepter;
            String[] delIdArr = delIds.split(",");
            for (String delId : delIdArr) {
                emDustbin = this.findOne(delId);
                if (emDustbin != null) {
                    emMain = this.emMainService.findOne(emDustbin.getEmailId());

                    emIncepter = this.incepterService.findByEmailIdAndIncepterId(emDustbin.getEmailId(), incepterId);
                    if (emIncepter == null) {
                        // 如果为草稿则直接逻辑删除 emMain
                        if (emMain != null) {
                            emMain.setStatus(MailStatus.逻辑删除);
                            this.emMainService.persist(emMain);
                            // 保存数据变动
                            dataChangeService.saveDataChange("email_id=" + emMain.getId(), DataChangeTable.内部邮件主表,
                                            OperationType.修改);
                        }
                    } else {
                        // 逻辑删除接收人信息
                        emIncepter.setDelFlag(DelFlag.已删除);
                        emIncepter.setStatus(IncepterStatus.垃圾);
                        this.incepterService.persist(emIncepter);
                        // 保存数据变动
                        dataChangeService.saveDataChange("id=" + emIncepter.getId(), DataChangeTable.邮件接收表,
                                        OperationType.修改);
                    }
                    // 物理删除垃圾箱记录
                    this.repository.delete(emDustbin);
                }

                emMain = null;
                emDustbin = null;
                emIncepter = null;
            }
            return true;
        }

        return false;
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.oa.mail.service.EmDustbinService#seleceEmDustbin()
     */
    @Override
    public List<MailShowBean> seleceEmDustbin(Integer page, Integer rows) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select e.id,e.title,e.delDate,e.attachFlag ");
        sqlBuilder.append(this.buildMyQuerySqlForEmDustbin());
        Query query = em.createQuery(sqlBuilder.toString());
        ArrayList<Object> list = (ArrayList<Object>) query.setMaxResults(rows).setFirstResult((page - 1) * rows)
                        .getResultList();
        List<MailShowBean> results = new ArrayList<MailShowBean>();
        String[] names = { "id", "title", "delDate", "attachFlag" };
        if (list != null && list.size() > 0) {
            for (Object o : list) {
                Map<String, Object> map = toMap(names, (Object[]) o);
                MailShowBean showBean = new MailShowBean();
                try {
                    PropertyUtils.copyProperties(showBean, map);
                    results.add(showBean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        return results;
    }

    @Override
    public Long coutEmDustbin() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" SELECT COUNT(DISTINCT e.id) ");
        sqlBuilder.append(this.buildMyQuerySqlForEmDustbin());
        Query query = em.createQuery(sqlBuilder.toString());
        ArrayList<Object> list = (ArrayList<Object>) query.getResultList();
        if (list != null && list.size() == 1) {
            return Long.valueOf(list.get(0).toString());
        } else {
            return new Long(0);
        }
    }

    private String buildMyQuerySqlForEmDustbin() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" FROM EmDustbin AS e  WHERE (e.incepter.id = '")
                        .append(UserTool.getInstance().getCurrentUserId()).append("' AND e.sender.id IS NOT NULL )");
        sqlBuilder.append(" OR ( e.sender.id = '").append(UserTool.getInstance().getCurrentUserId())
                        .append("' AND e.incepter.id IS NULL )");
        sqlBuilder.append(" ORDER BY e.delDate DESC ");
        return sqlBuilder.toString();
    }

    /**
     * 属性复制
     */
    private Map<String, Object> toMap(String[] propertyName, Object[] list) {
        Map<String, Object> map = new HashedMap();
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < propertyName.length; j++) {
                map.put(propertyName[j], list[j]);
            }
        }
        return map;
    }

}
