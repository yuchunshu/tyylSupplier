package cn.com.chaochuang.supplier.service;



import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.common.beancopy.BeanCopyUtil;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.oa.notice.domain.OaNotice;
import cn.com.chaochuang.supplier.bean.SupAssessmentEditBean;
import cn.com.chaochuang.supplier.domain.SupAssessment;
import cn.com.chaochuang.supplier.repository.SupAssessmentRepository;

@Service
@Transactional
public class SupAssessmentServiceImpl extends SimpleLongIdCrudRestService<SupAssessment> implements SupAssessmentService{

    @Autowired
    private SupAssessmentRepository   repository;
    
    @Autowired
    private SysAttachService          attachService;

    @Override
    public SimpleDomainRepository<SupAssessment, Long> getRepository() {
        return this.repository;
    }

	@Override
	public boolean delAssessment(String id) {
		if (StringUtils.isNotBlank(id)) {
			repository.delete(Long.parseLong(id));
            return true;
        }
		return false;
	}

	@Override
	public String saveSupAssessment(SupAssessmentEditBean bean) {
		SupAssessment assess = null;
        if (bean != null && bean.getId() != null) {
        	assess = this.repository.findOne(bean.getId());
        } else {
        	assess = new SupAssessment();
        }
        assess = BeanCopyUtil.copyBean(bean, SupAssessment.class);
        // 保证取到自动生成的ID
        assess = this.repository.save(assess);

        String attachIds = bean.getAttach();

        // 连接附件
        List<String> oldIdsForDel = new ArrayList<String>();
        if (null != bean.getId()) {
            // 旧的附件id
            List<SysAttach> oldAttachs = this.attachService.findByOwnerIdAndOwnerType(bean.getId().toString(),
            		SupAssessment.class.getSimpleName());
            if (oldAttachs != null) {
                for (SysAttach a : oldAttachs) {
                    oldIdsForDel.add(a.getId().toString());
                }
            }
        }
        
        if (StringUtils.isNotBlank(attachIds)) {
            String[] idArray = StringUtils.split(attachIds, ",");
            Long ownerId = assess.getId();
            for (String aIdStr : idArray) {
                // Long aId = Long.valueOf(aIdStr);
                this.attachService.linkAttachAndOwner(aIdStr, ownerId.toString(), SupAssessment.class.getSimpleName());
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
        
        return assess.getId().toString();
    }

}
