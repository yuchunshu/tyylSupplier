/*
 * FileName:    EmTempStorageServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.mail.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.oa.datachange.reference.DataChangeTable;
import cn.com.chaochuang.oa.datachange.reference.OperationType;
import cn.com.chaochuang.oa.datachange.service.DataChangeService;
import cn.com.chaochuang.oa.mail.domain.EmIncepter;
import cn.com.chaochuang.oa.mail.domain.EmMain;
import cn.com.chaochuang.oa.mail.domain.EmTempStorage;
import cn.com.chaochuang.oa.mail.reference.IncepterStatus;
import cn.com.chaochuang.oa.mail.reference.IsNeedback;
import cn.com.chaochuang.oa.mail.reference.MailStatus;
import cn.com.chaochuang.oa.mail.reference.ReceiptFlag;
import cn.com.chaochuang.oa.mail.repository.EmTempStorageRepository;

/**
 * @author HM
 *
 */
@Service
@Transactional
public class EmTempStorageServiceImpl extends SimpleUuidCrudRestService<EmTempStorage> implements EmTempStorageService {

    @Autowired
    private EmTempStorageRepository repository;

    @Autowired
    private EmIncepterService       incepterService;

    @Autowired
    private EmMainService           mailService;

    @Autowired
    private DataChangeService       dataChangeService;

    @Override
    public SimpleDomainRepository<EmTempStorage, String> getRepository() {
        return this.repository;
    }

    @Override
    public void archiveMails(String incIds) {
        if (StringUtils.isNotBlank(incIds)) {
            String incIdArr[] = incIds.split(",");
            for (String incId : incIdArr) {
                EmIncepter emIncepter = this.incepterService.findOne(incId);
                if (emIncepter != null) {
                    EmTempStorage archive = new EmTempStorage();
                    EmMain mail = emIncepter.getMail();
                    if (mail != null) {
                        archive.setAttachFlag(mail.getAttachFlag());
                        archive.setContent(mail.getContent());
                        archive.setEmailId(mail.getId());
                        ReceiptFlag recFlag = mail.getReceiptFlag();
                        if (ReceiptFlag.是.equals(recFlag)) {
                            archive.setIsNeedback(IsNeedback.回执);
                        } else {
                            archive.setIsNeedback(IsNeedback.不回执);
                        }
                        archive.setPigeDate(new Date());
                        archive.setSendDate(mail.getSendDate());
                        archive.setSender(mail.getSender());
                        archive.setTitle(mail.getTitle());
                    }
                    archive.setIncepter(emIncepter.getIncepter());
                    archive.setReadDate(emIncepter.getReadDate());
                    archive.setWritebackDate(emIncepter.getWritebackDate());
                    this.repository.save(archive); // 保存存档信息

                    mail.setStatus(MailStatus.归档);
                    this.mailService.persist(mail);

                    // 保存数据变动
                    dataChangeService.saveDataChange("id=" + emIncepter.getId(), DataChangeTable.邮件接收表,
                                    OperationType.修改);
                    emIncepter.setStatus(IncepterStatus.存档);
                    this.incepterService.persist(emIncepter);
                }
            }
        }

    }

    public boolean deleteArchive(String tmpIds) {
        if (StringUtils.isNotBlank(tmpIds)) {
            String idArr[] = tmpIds.split(",");
            for (String id : idArr) {
                this.repository.delete(id);
            }
            return true;
        }

        return false;
    }

}
