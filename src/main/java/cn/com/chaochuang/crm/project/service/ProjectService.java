package cn.com.chaochuang.crm.project.service;



import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.crm.project.bean.ProjectEditBean;
import cn.com.chaochuang.crm.project.domain.Project;

public interface ProjectService extends CrudRestService<Project, Long> {

	public boolean delProject(String id);
	
	public String saveProject(ProjectEditBean bean);
	
}
