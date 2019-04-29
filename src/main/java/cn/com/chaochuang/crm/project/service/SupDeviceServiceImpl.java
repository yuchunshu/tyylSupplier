package cn.com.chaochuang.crm.project.service;



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
import cn.com.chaochuang.supplier.bean.SupDeviceEditBean;
import cn.com.chaochuang.supplier.domain.SupDevice;
import cn.com.chaochuang.supplier.repository.SupDeviceRepository;

@Service
@Transactional
public class SupDeviceServiceImpl extends SimpleLongIdCrudRestService<SupDevice> implements SupDeviceService{

    @Autowired
    private SupDeviceRepository   repository;
    
    @Autowired
    private SysAttachService      attachService;

    @Override
    public SimpleDomainRepository<SupDevice, Long> getRepository() {
        return this.repository;
    }

	@Override
	public boolean delDevice(String id) {
		if (StringUtils.isNotBlank(id)) {
			repository.delete(Long.parseLong(id));
            return true;
        }
		return false;
	}

	@Override
	public String saveSupDevice(SupDeviceEditBean bean) {
		SupDevice device = null;
        if (bean != null && bean.getId() != null) {
        	device = this.repository.findOne(bean.getId());
        } else {
        	device = new SupDevice();
        }
        device = BeanCopyUtil.copyBean(bean, SupDevice.class);
        // 保证取到自动生成的ID
        device = this.repository.save(device);

        String attachIds = bean.getAttach();

        // 连接附件
        List<String> oldIdsForDel = new ArrayList<String>();
        if (null != bean.getId()) {
            // 旧的附件id
            List<SysAttach> oldAttachs = this.attachService.findByOwnerIdAndOwnerType(bean.getId().toString(),
            		SupDevice.class.getSimpleName());
            if (oldAttachs != null) {
                for (SysAttach a : oldAttachs) {
                    oldIdsForDel.add(a.getId().toString());
                }
            }
        }
        
        if (StringUtils.isNotBlank(attachIds)) {
            String[] idArray = StringUtils.split(attachIds, ",");
            Long ownerId = device.getId();
            for (String aIdStr : idArray) {
                // Long aId = Long.valueOf(aIdStr);
                this.attachService.linkAttachAndOwner(aIdStr, ownerId.toString(), SupDevice.class.getSimpleName());
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
        
        return device.getId().toString();
    }

}
