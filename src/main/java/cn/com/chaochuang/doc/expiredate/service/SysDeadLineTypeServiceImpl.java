/*
 * FileName:    OaDocDeptFileServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年9月14日 (yuchunshu) 1.0 Create
 */

package cn.com.chaochuang.doc.expiredate.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.beancopy.BeanCopyUtil;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.expiredate.bean.SysDeadLineTypeEditBean;
import cn.com.chaochuang.doc.expiredate.bean.SysNodeExpireDateEditBean;
import cn.com.chaochuang.doc.expiredate.domain.SysDeadLineType;
import cn.com.chaochuang.doc.expiredate.domain.SysNodeExpireDate;
import cn.com.chaochuang.doc.expiredate.reference.DeadlineTypeConverter;
import cn.com.chaochuang.doc.expiredate.repository.SysDeadLineTypeRepository;
import cn.com.chaochuang.doc.expiredate.repository.SysNodeExpireDateRepository;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapTypeConverter;

/**
 * @author yuchunshu
 *
 */
@Service
@Transactional
public class SysDeadLineTypeServiceImpl extends SimpleUuidCrudRestService<SysDeadLineType> implements SysDeadLineTypeService {

    @Autowired
    private SysDeadLineTypeRepository repository;

    @Override
    public SimpleDomainRepository<SysDeadLineType, String> getRepository() {
        return this.repository;
    }

	@Override
	public String saveSysDeadLineType(SysDeadLineTypeEditBean bean) {
		SysDeadLineType sysDeadLineType = null;
		
		if (bean != null && bean.getId() != null) {
			sysDeadLineType = this.repository.findOne(bean.getId());
        } else {
        	sysDeadLineType = new SysDeadLineType();
        }
		sysDeadLineType = BeanCopyUtil.copyBean(bean, SysDeadLineType.class);
        
		// 保证取到自动生成的ID
		sysDeadLineType = this.repository.save(sysDeadLineType);
        
		return sysDeadLineType.getId();
	}

	@Override
	public Map<String, String> getSysDeadLineTypeMap() {

        List<SysDeadLineType> list = this.repository.findAll();
        if (list != null && list.size() > 0) {
        	Map<String, String> map = new HashMap<String, String>();
        	for(SysDeadLineType obj:list){
        		map.put(obj.getDeadlineType().getKey() + obj.getDocumentType().getKey(), obj.getExpireDay().toString());
        	}
            return map;
        }
        return null;
    
	}

	@Override
	public boolean delSysDeadLineType(String id) {
		if (StringUtils.isNotBlank(id)) {
            repository.delete(id);
            return true;
        }
        return false;
	}

	@Override
	public boolean repeatInfoCheck(String deadlineType, String documentType,String id) {
		boolean result = false;
		if (StringUtils.isNotBlank(id)) {
			SysDeadLineType obj = repository.findOne(id);
			if(null != obj){
				if(!documentType.equals(obj.getDocumentType().getKey()) || !deadlineType.equals(obj.getDeadlineType().getKey())){
					List<SysDeadLineType> list = this.repository.findByDeadlineTypeAndDocumentType(new DeadlineTypeConverter().convertToEntityAttribute(deadlineType), new RemoteDocSwapTypeConverter().convertToEntityAttribute(documentType));
		    		if (list != null && list.size() > 0) {
		    			result = true;
		            }
				}
			}
        }else{
			List<SysDeadLineType> list = this.repository.findByDeadlineTypeAndDocumentType(new DeadlineTypeConverter().convertToEntityAttribute(deadlineType), new RemoteDocSwapTypeConverter().convertToEntityAttribute(documentType));
    		if (list != null && list.size() > 0) {
    			result = true;
            }
        }
		
		
		return result;
	}
    
}
