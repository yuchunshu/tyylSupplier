package cn.com.chaochuang.crm.customer.service;



import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.crm.customer.bean.CustomerEditBean;
import cn.com.chaochuang.crm.customer.domain.Customer;

public interface CustomerService extends CrudRestService<Customer, Long> {

	public boolean delCustomer(String id);
	
	public String saveCustomer(CustomerEditBean bean);
	
}
