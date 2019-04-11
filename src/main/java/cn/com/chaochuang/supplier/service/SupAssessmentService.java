package cn.com.chaochuang.supplier.service;



import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.supplier.bean.SupAssessmentEditBean;
import cn.com.chaochuang.supplier.domain.SupAssessment;

public interface SupAssessmentService extends CrudRestService<SupAssessment, Long> {

	public boolean delAssessment(String id);
	
	public String saveSupAssessment(SupAssessmentEditBean bean);
	
}
