package cn.com.chaochuang.crm.customer.service;



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
import cn.com.chaochuang.crm.customer.bean.CustomerEditBean;
import cn.com.chaochuang.crm.customer.domain.Customer;
import cn.com.chaochuang.crm.customer.repository.CustomerRepository;

@Service
@Transactional
public class CustomerServiceImpl extends SimpleLongIdCrudRestService<Customer> implements CustomerService{

    @Autowired
    private CustomerRepository   repository;
    
    @Autowired
    private SysAttachService      attachService;

    @Override
    public SimpleDomainRepository<Customer, Long> getRepository() {
        return this.repository;
    }

	@Override
	public boolean delCustomer(String id) {
		if (StringUtils.isNotBlank(id)) {
			repository.delete(Long.parseLong(id));
            return true;
        }
		return false;
	}

	@Override
	public String saveCustomer(CustomerEditBean bean) {
		Customer customer = null;
        if (bean != null && bean.getId() != null) {
        	customer = this.repository.findOne(bean.getId());
        } else {
        	customer = new Customer();
        }
        customer = BeanCopyUtil.copyBean(bean, Customer.class);
        // 保证取到自动生成的ID
        customer = this.repository.save(customer);

        String attachIds = bean.getAttach();

        // 连接附件
        List<String> oldIdsForDel = new ArrayList<String>();
        if (null != bean.getId()) {
            // 旧的附件id
            List<SysAttach> oldAttachs = this.attachService.findByOwnerIdAndOwnerType(bean.getId().toString(),
            		Customer.class.getSimpleName());
            if (oldAttachs != null) {
                for (SysAttach a : oldAttachs) {
                    oldIdsForDel.add(a.getId().toString());
                }
            }
        }
        
        if (StringUtils.isNotBlank(attachIds)) {
            String[] idArray = StringUtils.split(attachIds, ",");
            Long ownerId = customer.getId();
            for (String aIdStr : idArray) {
                // Long aId = Long.valueOf(aIdStr);
                this.attachService.linkAttachAndOwner(aIdStr, ownerId.toString(), Customer.class.getSimpleName());
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
        
        return customer.getId().toString();
    }

}
