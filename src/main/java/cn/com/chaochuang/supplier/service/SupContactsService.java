package cn.com.chaochuang.supplier.service;



import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.supplier.bean.SupContactsEditBean;
import cn.com.chaochuang.supplier.domain.SupContacts;

public interface SupContactsService extends CrudRestService<SupContacts, Long> {

	public boolean delContacts(String id);
	
	public String saveSupContacts(SupContactsEditBean bean);
	
}
