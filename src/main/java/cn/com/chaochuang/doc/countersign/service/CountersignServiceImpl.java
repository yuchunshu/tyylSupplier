package cn.com.chaochuang.doc.countersign.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.reference.SignStatus;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.countersign.domain.DocCountersign;
import cn.com.chaochuang.doc.countersign.repository.CountersignRepository;
import cn.com.chaochuang.doc.event.bean.FileShowBean;

/**
 * @author hzr 2016年5月16日
 *
 */
@Service
@Transactional
public class CountersignServiceImpl extends SimpleLongIdCrudRestService<DocCountersign> implements CountersignService {

    @Autowired
    private CountersignRepository repository;

    @Override
    public SimpleDomainRepository<DocCountersign, Long> getRepository() {
        return this.repository;
    }

    @Override
    public Long saveDocCountersgin(String userIds, String type, FileShowBean bean) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        String[] signs = userIds.split(",");
        for (String s : signs) {
            if (!Tools.isEmptyString(s) && !"undefined".equals(s)) {
                DocCountersign sign = new DocCountersign();
                sign.setCosignMan(Long.valueOf(s));
                sign.setCosignTime(null);
                sign.setFileId(bean.getId());
                sign.setFileType(type);
                sign.setOpinion(null);
                sign.setSendTime(new Date());
                sign.setSendUser(user);
                sign.setStatus(SignStatus.未签收);
                sign.setTaskId(bean.getCurTaskId());
                sign.setTitle(bean.getTitle());
                this.repository.save(sign);
            }
        }

        return null;
    }

    @Override
    public List<DocCountersign> selectDocCountersignByFileId(String fileId) {
        return this.repository.findByFileId(fileId);
    }

    @Override
    public void deleteDocCountersignByFileId(String fileId) {
        this.repository.delete(this.selectDocCountersignByFileId(fileId));
    }

    
}
