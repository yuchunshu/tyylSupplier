package cn.com.chaochuang.crm.project.service;



import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.supplier.bean.SupDeviceEditBean;
import cn.com.chaochuang.supplier.domain.SupDevice;

public interface SupDeviceService extends CrudRestService<SupDevice, Long> {

	public boolean delDevice(String id);
	
	public String saveSupDevice(SupDeviceEditBean bean);
	
}
