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
import cn.com.chaochuang.doc.expiredate.bean.SysNodeExpireDateEditBean;
import cn.com.chaochuang.doc.expiredate.domain.SysDeadLineType;
import cn.com.chaochuang.doc.expiredate.domain.SysNodeExpireDate;
import cn.com.chaochuang.doc.expiredate.reference.DeadlineType;
import cn.com.chaochuang.doc.expiredate.reference.DeadlineTypeConverter;
import cn.com.chaochuang.doc.expiredate.repository.SysDeadLineTypeRepository;
import cn.com.chaochuang.doc.expiredate.repository.SysNodeExpireDateRepository;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapTypeConverter;

/**
 * @author yuchunshu
 *
 */
@Service
@Transactional
public class SysNodeExpireDateServiceImpl extends SimpleUuidCrudRestService<SysNodeExpireDate> implements SysNodeExpireDateService {

    @Autowired
    private SysNodeExpireDateRepository repository;
    
    @Autowired
    private SysDeadLineTypeRepository   deadLineTypeRepository;

    @Override
    public SimpleDomainRepository<SysNodeExpireDate, String> getRepository() {
        return this.repository;
    }

	@Override
	public String saveSysNodeExpireDate(SysNodeExpireDateEditBean bean) {
		SysNodeExpireDate nodeExpireDate = null;
		
		if (bean != null && bean.getId() != null) {
			nodeExpireDate = this.repository.findOne(bean.getId());
        } else {
        	nodeExpireDate = new SysNodeExpireDate();
        }
		nodeExpireDate = BeanCopyUtil.copyBean(bean, SysNodeExpireDate.class);
        
		// 保证取到自动生成的ID
		nodeExpireDate = this.repository.save(nodeExpireDate);
        
		return nodeExpireDate.getId();
	}

	@Override
	public Map<String, SysNodeExpireDate> getSysNodeExpireDateMap(String deadlineType) {

        List<SysNodeExpireDate> list = null;
        if (!Tools.isEmptyString(deadlineType)) {
        	list = this.repository.findByDeadlineType(new DeadlineTypeConverter().convertToEntityAttribute(deadlineType));
        }
        if (list != null && list.size() > 0) {
        	Map<String, SysNodeExpireDate> map = new HashMap<String, SysNodeExpireDate>();
        	for(SysNodeExpireDate obj:list){
        		map.put(obj.getNodeId(), obj);
        	}
            return map;
        }
        return null;
    
	}

	@Override
	public boolean delSysNodeExpireDate(String id) {
		if (StringUtils.isNotBlank(id)) {
            repository.delete(id);
            return true;
        }
        return false;
	}

	@Override
	public boolean repeatInfoCheck(String documentType,String deadlineType, String nodeId,String id) {
		boolean result = false;
		if (StringUtils.isNotBlank(id)) {
			SysNodeExpireDate obj = repository.findOne(id);
			if(null != obj){
				if(!nodeId.equals(obj.getNodeId()) || !deadlineType.equals(obj.getDeadlineType().getKey())){
					List<SysNodeExpireDate> list = this.repository.findByDocumentTypeAndDeadlineTypeAndNodeId(new RemoteDocSwapTypeConverter().convertToEntityAttribute(documentType),new DeadlineTypeConverter().convertToEntityAttribute(deadlineType), nodeId);
		    		if (list != null && list.size() > 0) {
		    			result = true;
		            }
				}
			}
        }else{
        	List<SysNodeExpireDate> list = this.repository.findByDocumentTypeAndDeadlineTypeAndNodeId(new RemoteDocSwapTypeConverter().convertToEntityAttribute(documentType),new DeadlineTypeConverter().convertToEntityAttribute(deadlineType), nodeId);
    		if (list != null && list.size() > 0) {
    			result = true;
            }
        }
		
		
		return result;
	}

	@Override
	public Integer getExpireDay(String documentType, String deadlineType) {
		List<SysDeadLineType> list = deadLineTypeRepository.findByDeadlineTypeAndDocumentType(new DeadlineTypeConverter().convertToEntityAttribute(deadlineType),new RemoteDocSwapTypeConverter().convertToEntityAttribute(documentType));
		if (list != null && list.size() > 0) {
			return list.get(0).getExpireDay();
        }
		return 0;
	}

	@Override
	public Integer getRemainingTime(String documentType, String deadlineType,String id) {
		Integer day = 0;
		Integer hour = 0;
		Integer minute = 0;
		Integer totalDay = 0;
		Integer result = -1;
		
		SysNodeExpireDate nodeExpireDate = null;
		if(!Tools.isEmptyString(id)){
			nodeExpireDate = repository.findOne(id);
		}else{
			nodeExpireDate = new SysNodeExpireDate();
		}
		
		
		//获取总限办时间
		List<SysDeadLineType> totalList = deadLineTypeRepository.findByDeadlineTypeAndDocumentType(new DeadlineTypeConverter().convertToEntityAttribute(deadlineType),new RemoteDocSwapTypeConverter().convertToEntityAttribute(documentType));
		if (totalList != null && totalList.size() > 0) {
			totalDay = totalList.get(0).getExpireDay();
			
			List<SysNodeExpireDate> list = repository.findByDocumentTypeAndDeadlineType(new RemoteDocSwapTypeConverter().convertToEntityAttribute(documentType),new DeadlineTypeConverter().convertToEntityAttribute(deadlineType));
			
			if(new DeadlineTypeConverter().convertToEntityAttribute(deadlineType).equals(DeadlineType.特急)){
				if (list != null && list.size() > 0) {
					for(SysNodeExpireDate obj:list){
						if(nodeExpireDate != obj){//修改时过滤当前设置
							minute += Integer.parseInt(obj.getExpireMinute());
						}
					}
		        }
				result = totalDay*24*60 - minute;
			}else{
				if (list != null && list.size() > 0) {
					for(SysNodeExpireDate obj:list){
						if(nodeExpireDate != obj){//修改时过滤当前设置
							day += Integer.parseInt(obj.getExpireDay());
							hour += Integer.parseInt(obj.getExpireHour());
						}
						
					}
		        }
				result = totalDay*24 - day*24 - hour;
			}
        }
		
		
		
		return result;
	}
    
}
