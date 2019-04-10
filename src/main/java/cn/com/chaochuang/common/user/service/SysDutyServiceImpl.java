package cn.com.chaochuang.common.user.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.user.domain.SysDuty;
import cn.com.chaochuang.common.user.repository.SysDutyRepository;
import cn.com.chaochuang.common.user.repository.SysUserRepository;

@Service
@Transactional
public class SysDutyServiceImpl extends SimpleLongIdCrudRestService<SysDuty> implements SysDutyService {

    @Autowired
    private SysDutyRepository repository;
    
    @Autowired
    private SysUserRepository userRepository;

    @Override
    public SimpleDomainRepository<SysDuty, Long> getRepository() {
        return repository;
    }

	@Override
	public void deleteDuty(Long id) {
		if(id != null){
			this.userRepository.deleteDutyId(id);
			this.delete(id);
		}
	}
}
