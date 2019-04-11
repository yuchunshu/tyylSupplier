package cn.com.chaochuang.supplier.service;



import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.beancopy.BeanCopyUtil;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.supplier.bean.SupContactsEditBean;
import cn.com.chaochuang.supplier.domain.SupContacts;
import cn.com.chaochuang.supplier.repository.SupContactsRepository;

@Service
@Transactional
public class SupContactsServiceImpl extends SimpleLongIdCrudRestService<SupContacts> implements SupContactsService{

    @Autowired
    private SupContactsRepository   repository;

    @Override
    public SimpleDomainRepository<SupContacts, Long> getRepository() {
        return this.repository;
    }

	@Override
	public boolean delContacts(String id) {
		if (StringUtils.isNotBlank(id)) {
			repository.delete(Long.parseLong(id));
            return true;
        }
		return false;
	}

	@Override
	public String saveSupContacts(SupContactsEditBean bean) {
		SupContacts contacts = null;
        if (bean != null && bean.getId() != null) {
        	contacts = this.repository.findOne(bean.getId());
        } else {
        	contacts = new SupContacts();
        }
        contacts = BeanCopyUtil.copyBean(bean, SupContacts.class);
        // 保证取到自动生成的ID
        contacts = this.repository.save(contacts);

        return contacts.getId().toString();
    }

}
