package cn.com.chaochuang.supplier.service;



import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.supplier.bean.SupUnitEditBean;
import cn.com.chaochuang.supplier.domain.SupUnit;

public interface SupUnitService extends CrudRestService<SupUnit, Long> {

	public boolean delUnit(String id);
	
	public String saveSupUnit(SupUnitEditBean bean);
	
	public void exchange(Long[] ids, Long groupId);
}
