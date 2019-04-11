package cn.com.chaochuang.supplier.service;


import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.beancopy.BeanCopyUtil;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.supplier.bean.SupUnitEditBean;
import cn.com.chaochuang.supplier.domain.SupUnit;
import cn.com.chaochuang.supplier.repository.SupUnitRepository;

@Service
@Transactional
public class SupUnitServiceImpl extends SimpleLongIdCrudRestService<SupUnit> implements SupUnitService{

    @Autowired
    private SupUnitRepository   repository;

    @Override
    public SimpleDomainRepository<SupUnit, Long> getRepository() {
        return this.repository;
    }

	@Override
	public boolean delUnit(String id) {
		if (StringUtils.isNotBlank(id)) {
			repository.delete(Long.parseLong(id));
            return true;
        }
		return false;
	}

	@Override
	public String saveSupUnit(SupUnitEditBean bean) {
		SupUnit unit = null;
        if (bean != null && bean.getId() != null) {
        	unit = this.repository.findOne(bean.getId());
        } else {
        	unit = new SupUnit();
        }
        unit = BeanCopyUtil.copyBean(bean, SupUnit.class);
        // 保证取到自动生成的ID
        unit = this.repository.save(unit);

        return unit.getId().toString();
    }

	@Override
	public void exchange(Long[] ids, Long groupId) {
        if (ids != null && ids.length > 0 && groupId != null) {
            List<SupUnit> unitList = repository.findAll(Arrays.asList(ids));
            if (unitList != null) {
                for (SupUnit unit : unitList) {
                	unit.setGroupId(groupId);
                    repository.save(unit);
                }
            }
        }
    }
    
}
