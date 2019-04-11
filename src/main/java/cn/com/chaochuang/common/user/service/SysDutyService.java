package cn.com.chaochuang.common.user.service;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.user.domain.SysDuty;

public interface SysDutyService extends CrudRestService<SysDuty, Long> {
	public void deleteDuty(Long id);
}
