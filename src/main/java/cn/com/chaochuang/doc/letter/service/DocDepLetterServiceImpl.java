/*
 * FileName:    DocDepLetterServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年11月25日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.doc.letter.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysDepartmentService;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.letter.bean.DepLetterInfo;
import cn.com.chaochuang.doc.letter.domain.DocDepLetter;
import cn.com.chaochuang.doc.letter.domain.DocDepLetterReceiver;
import cn.com.chaochuang.doc.letter.reference.LetterReceiverStatus;
import cn.com.chaochuang.doc.letter.reference.LetterStatus;
import cn.com.chaochuang.doc.letter.repository.DocDepLetterReceiverRepository;
import cn.com.chaochuang.doc.letter.repository.DocDepLetterRepository;
import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.service.SysAttachService;

/**
 * @author LJX
 *
 */
@Service
@Transactional
public class DocDepLetterServiceImpl extends SimpleUuidCrudRestService<DocDepLetter> implements DocDepLetterService {
    @Autowired
    private DocDepLetterRepository         repository;

    @Autowired
    private SysAttachService               attachService;

    @Autowired
    private SysDepartmentService           departmentService;

    @Autowired
    private DocDepLetterReceiverRepository receiverRepository;

    @Autowired
    private DocDepLetterReceiverService    receiverService;


    @Override
    public SimpleDomainRepository<DocDepLetter, String> getRepository() {
        return repository;
    }

    @Override
    public String saveDepLetter(DepLetterInfo info) {
        DocDepLetter letter = null;
        Date now = new Date();
        if (StringUtils.isNotBlank(info.getId())) {
            letter = this.findOne(info.getId());
        }
        if (letter == null) {
            letter = new DocDepLetter();
        }
        BeanUtils.copyProperties(info, letter, "id");
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        letter.setCreateDate(now);
        if (user.getDepartment() != null) {
            letter.setCreatorAncestorDep(user.getDepartment().getUnitId());
            letter.setCreatorDeptId(user.getDepartment().getId());
        }
        letter.setCreatorId(user.getId());
        letter.setCreatorName(user.getUserName());
        letter.setStatus(LetterStatus.在办);
        this.repository.save(letter);
        // 处理附件
        // List<SysAttach> oldAttachs = this.attachService.findByOwnerIdAndOwnerType(String.valueOf(letter.getId()),
        // DocDepLetter.class.getSimpleName());
        if (info.getAttachs() != null && info.getAttachs().length > 0) {
            letter.setAttachFlag(AttachFlag.有附件);
            for (String id : info.getAttachs()) {
                SysAttach attach = this.attachService.findOne(id);
                attach.setOwnerId(letter.getId());
                attach.setOwnerType(DocDepLetter.class.getSimpleName());
                this.attachService.persist(attach);
                // if (oldAttachs != null && oldAttachs.contains(attach)) {
                // oldAttachs.remove(attach);// 从旧附件列表中删除
                // }
            }

        } else {
            letter.setAttachFlag(AttachFlag.无附件);
        }
        // // 删除排除附件
        // this.attachService.deleteAttach(oldAttachs);

        // 部门接收
        if (StringUtils.isNotBlank(info.getIncepterDepts())) {
            String[] deptArr = info.getIncepterDepts().split(",");
            for (String id : deptArr) {
                if (StringUtils.isNotBlank(id)) {
                    SysDepartment d = this.departmentService.findOne(Long.valueOf(id));
                    DocDepLetterReceiver receiver = new DocDepLetterReceiver();
                    receiver.setReceiveDate(now);
                    receiver.setReceiverDeptId(d.getId());
                    receiver.setReceiverAncestorDep(d.getUnitId());
                    receiver.setStatus(LetterReceiverStatus.未签收);
                    receiver.setLetterId(letter.getId());
                    receiver.setAttachFlag(letter.getAttachFlag());
                    this.receiverRepository.save(receiver);

                }
            }
        }

        this.repository.save(letter);

        return letter.getId();
    }


    @Override
    public String reversion(DepLetterInfo info) {
        if (StringUtils.isNotBlank(info.getLetterId())) {
            DocDepLetter letter = this.findOne(info.getLetterId());
            if (letter != null) {
                DocDepLetterReceiver receiver = this.receiverRepository.findByLetterIdAndReceiverDeptId(letter.getId(),
                                Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));
                if (receiver != null && !LetterReceiverStatus.已反馈.equals(receiver.getStatus())) {// 已反馈的不做处理
                    receiver.setContent(info.getContent());
                    receiver.setReversionDate(new Date());
                    receiver.setStatus(LetterReceiverStatus.已反馈);
                    this.receiverRepository.save(receiver);

                    // 处理附件
                    List<SysAttach> oldaAttachs = this.attachService.findByOwnerIdAndOwnerType(receiver.getId(),
                                    DocDepLetterReceiver.class.getSimpleName());
                    if (info.getAttachs() != null && info.getAttachs().length > 0) {
                        for (String id : info.getAttachs()) {
                            SysAttach a = this.attachService.findOne(id);
                            a.setOwnerId(receiver.getId());
                            a.setOwnerType(DocDepLetterReceiver.class.getSimpleName());
                            this.attachService.persist(a);
                            if (Tools.isNotEmptyList(oldaAttachs) && oldaAttachs.contains(a)) {
                                oldaAttachs.remove(a);
                            }
                        }
                    }
                    this.attachService.deleteAttach(oldaAttachs);

                    return letter.getId();
                }
            }
        }
        return null;
    }

    @Override
    public String changeReceiveStatus(DepLetterInfo info) {
        if (StringUtils.isNotBlank(info.getReceiveId()) && info.getReceiverStatus() != null) {
            DocDepLetterReceiver r = this.receiverService.findOne(info.getReceiveId());
            if (r != null) {
                // 签收：判断当前用户所在部门是否是接收部门,并且未签收。
                if (LetterReceiverStatus.已签收.equals(info.getReceiverStatus())
                                && UserTool.getInstance().getCurrentUserDepartmentId()
                                                .equals(String.valueOf(r.getReceiverDeptId()))
                                && LetterReceiverStatus.未签收.equals(r.getStatus())) {
                    r.setStatus(LetterReceiverStatus.已签收);

                }
                // 需补充：判断当前用户所在部门是否是发送部门，并且不是需补充状态。
                if (LetterReceiverStatus.需补充.equals(info.getReceiverStatus())
                                && UserTool.getInstance().getCurrentUserDepartmentId()
                                                .equals(String.valueOf(r.getLetter().getCreatorDeptId()))
                                && LetterReceiverStatus.已反馈.equals(r.getStatus())) {
                    r.setStatus(LetterReceiverStatus.需补充);
                    r.setReplenish(info.getReplenish());// 补充说明

                }
                this.receiverService.persist(r);
                return r.getId();
            }
        }
        return null;
    }

    @Override
    public void deleteLetter(String[] ids) {
        if (ids != null && ids.length > 0) {
            for (String id : ids) {
                DocDepLetter letter = this.findOne(id);
                if (letter != null) {
                    List<DocDepLetterReceiver> receives = this.receiverRepository.findByLetterId(id);
                    if (Tools.isNotEmptyList(receives)) {
                        for (DocDepLetterReceiver receive : receives) {
                            // 删除附件
                            this.attachService.deleteAttach(this.attachService.findByOwnerIdAndOwnerType(
                                            receive.getId(), DocDepLetterReceiver.class.getSimpleName()));
                            this.receiverService.delete(receive);
                        }
                    }
                    // 删除部门函件附件
                    List<SysAttach> letterAttachs = this.attachService.findByOwnerIdAndOwnerType(id,
                                    DocDepLetter.class.getSimpleName());
                    this.attachService.deleteAttach(letterAttachs);

                    repository.delete(letter);
                }
            }
        }
    }

    @Override
    public List<DocDepLetter> selectLettersByFileId(String fileId) {
        if (StringUtils.isNotBlank(fileId)) {
            return repository.findByFileIdOrderByCreateDateDesc(fileId);
        }
        return null;
    }

    @Override
    public Integer countLettersByFileId(String fileId) {
        if (StringUtils.isNotBlank(fileId)) {
            return repository.countByFileId(fileId);
        }
        return new Integer(0);
    }

    @Override
    public List<DocDepLetterReceiver> selectReceiversByFileId(String fileId) {
        if (StringUtils.isNotBlank(fileId)) {
            return receiverRepository.findByLetterFileIdOrderByLetterCreateDateDesc(fileId);
        }
        return null;
    }

    @Override
    public List<DocDepLetterReceiver> selectReceiversByLetterId(String letterId) {
        if (StringUtils.isNotBlank(letterId)) {
            return receiverRepository.findByLetterId(letterId);
        }
        return null;
    }


}
