package cn.com.chaochuang.supplier.service;



import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.supplier.bean.SupContractEditBean;
import cn.com.chaochuang.supplier.domain.SupContract;

public interface SupContractService extends CrudRestService<SupContract, Long> {

	public boolean delContract(String id);
	
	public String saveSupContract(SupContractEditBean bean);
	
}
