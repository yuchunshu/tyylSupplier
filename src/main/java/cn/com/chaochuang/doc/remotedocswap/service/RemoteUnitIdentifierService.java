/*
 * FileName:    RemoteUnitIdentifierService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.service;

import java.util.List;
import java.util.Map;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteUnitIdentifier;

/**
 * @author yuandl
 *
 */
public interface RemoteUnitIdentifierService extends CrudRestService<RemoteUnitIdentifier, Long> {

    /**
     * 从字典中获取单位数据
     */
    public RemoteUnitIdentifier getDefUnitIdentifier(String unitIdentifier);

    /**
     * 从标识码中获取机构代码
     */
    public String getUnitOrgFromIdentifier(String identifier);

    /**
     * 根据名称和单位查找交换单位
     */
    public List<RemoteUnitIdentifier> findByUnitNameAndUnitIndentifier(String unitName, String unitIdentifier);
    
    
    public Map findAllBySubUnitIdentifier();

    
    /**
     * 获取公文交换单位部门列表
     */
	public List getChildrenBean();

}
