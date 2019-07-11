package cn.com.chaochuang.crm.project.service;



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
import cn.com.chaochuang.crm.project.bean.ProjectEditBean;
import cn.com.chaochuang.crm.project.domain.Project;
import cn.com.chaochuang.crm.project.repository.ProjectRepository;

@Service
@Transactional
public class ProjectServiceImpl extends SimpleLongIdCrudRestService<Project> implements ProjectService{

    @Autowired
    private ProjectRepository   repository;
    
    @Autowired
    private SysAttachService      attachService;

    @Override
    public SimpleDomainRepository<Project, Long> getRepository() {
        return this.repository;
    }

	@Override
	public boolean delProject(String id) {
		if (StringUtils.isNotBlank(id)) {
			repository.delete(Long.parseLong(id));
            return true;
        }
		return false;
	}

	@Override
	public String saveProject(ProjectEditBean bean) {
		Project project = null;
        if (bean != null && bean.getId() != null) {
        	project = this.repository.findOne(bean.getId());
        } else {
        	project = new Project();
        }
        project = BeanCopyUtil.copyBean(bean, Project.class);
        // 保证取到自动生成的ID
        project = this.repository.save(project);

        String attachIds = bean.getAttach();

        // 连接附件
        List<String> oldIdsForDel = new ArrayList<String>();
        if (null != bean.getId()) {
            // 旧的附件id
            List<SysAttach> oldAttachs = this.attachService.findByOwnerIdAndOwnerType(bean.getId().toString(),
            		Project.class.getSimpleName());
            if (oldAttachs != null) {
                for (SysAttach a : oldAttachs) {
                    oldIdsForDel.add(a.getId().toString());
                }
            }
        }
        
        if (StringUtils.isNotBlank(attachIds)) {
            String[] idArray = StringUtils.split(attachIds, ",");
            Long ownerId = project.getId();
            for (String aIdStr : idArray) {
                // Long aId = Long.valueOf(aIdStr);
                this.attachService.linkAttachAndOwner(aIdStr, ownerId.toString(), Project.class.getSimpleName());
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
        
        return project.getId().toString();
    }

}
