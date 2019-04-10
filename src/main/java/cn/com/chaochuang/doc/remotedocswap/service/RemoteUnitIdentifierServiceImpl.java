/*
 * FileName:    RemoteUnitIdentifierServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.bean.CommonTreeBean;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteUnitIdentifier;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteUnitFlag;
import cn.com.chaochuang.doc.remotedocswap.repository.RemoteUnitIdentifierRepository;

/**
 * @author yuandl
 *
 */
@Service
@Transactional
public class RemoteUnitIdentifierServiceImpl extends SimpleLongIdCrudRestService<RemoteUnitIdentifier> implements
                RemoteUnitIdentifierService, InitializingBean {

    @Autowired
    private RemoteUnitIdentifierRepository repository;

    /** 交换单位缓存字典 */
    private Map                            unitMap;

    /** 默认交换单位缓存字典 */
    private Map                            defUnitMap;

    @Override
    public SimpleDomainRepository<RemoteUnitIdentifier, Long> getRepository() {
        return this.repository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.unitMap = new HashMap<String, RemoteUnitIdentifier>();
        List<RemoteUnitIdentifier> list = this.repository.findAll();
        for (RemoteUnitIdentifier unit : list) {
            unitMap.put(unit.getUnitIdentifier(), unit);
        }

        this.defUnitMap = new HashMap<String, RemoteUnitIdentifier>();
        List<RemoteUnitIdentifier> defList = this.repository.findByIdentifierFlag(RemoteUnitFlag.默认发送单位);
        for (RemoteUnitIdentifier defUnit : defList) {
            defUnitMap.put(defUnit.getUnitIdentifier(), defUnit);
        }
    }

    /**
     * 获取交换单位缓存字典
     */
    private Map getUnitMap() {
        if (this.unitMap == null) {
            this.unitMap = new HashMap<String, RemoteUnitIdentifier>();
            List<RemoteUnitIdentifier> list = this.repository.findAll();
            for (RemoteUnitIdentifier unit : list) {
                unitMap.put(unit.getUnitIdentifier(), unit);
            }
        }
        return this.unitMap;
    }

    /**
     * 获取默认发送单位
     */
    private Map getDefUnitMap() {
        if ( !Tools.isNotEmptyMap(this.defUnitMap) ) {
            this.defUnitMap = new HashMap<String, RemoteUnitIdentifier>();
            List<RemoteUnitIdentifier> defList = this.repository.findByIdentifierFlag(RemoteUnitFlag.默认发送单位);
            for (RemoteUnitIdentifier defUnit : defList) {
                defUnitMap.put(defUnit.getUnitIdentifier(), defUnit);
            }
        }

        return this.defUnitMap;
    }

    @Override
    public String getUnitOrgFromIdentifier(String identifier) {
        if (StringUtils.isEmpty(identifier)) {
            return "";
        }
        String[] items = identifier.split("-");
        if (items.length > 1) {
            return items[0];
        }
        return identifier;
    }

    @Override
    public RemoteUnitIdentifier getDefUnitIdentifier(String unitIdentifier) {
        if (StringUtils.isEmpty(unitIdentifier)) {// 找默认单位
            for (Iterator iter = getDefUnitMap().entrySet().iterator(); iter.hasNext();) {
                Map.Entry item = (Entry) iter.next();
                return (RemoteUnitIdentifier) item.getValue();
            }
        } else {// 找指定单位
            RemoteUnitIdentifier unit;
            for (Iterator iter = getUnitMap().entrySet().iterator(); iter.hasNext();) {
                Map.Entry item = (Map.Entry) iter.next();
                unit = (RemoteUnitIdentifier) item.getValue();
                if (unit.getUnitIdentifier().indexOf(unitIdentifier) == 0) {
                    return unit;
                }
            }
        }
        return null;
    }

    @Override
    public List<RemoteUnitIdentifier> findByUnitNameAndUnitIndentifier(String unitName, String unitIdentifier) {
        return this.repository.findByUnitNameLikeAndUnitIdentifierLikeOrderByIdAsc(unitName, unitIdentifier);
    }

	@Override
	public Map findAllBySubUnitIdentifier() {
		Map result = new HashMap<>();
		List<Object[]> list = this.repository.findAllBySubUnitIdentifier();
        for (Object[] unit : list) {
        	result.put(unit[0], unit[1]);
        }
        return result;
	}

	@Override
	public List<CommonTreeBean> getChildrenBean() {
        List<String> depts = this.repository.findByIdentifierFlagGroupByIdentifierName();
        List<CommonTreeBean> returnList = new ArrayList<CommonTreeBean>();
        
        CommonTreeBean bean1 = new CommonTreeBean();
        bean1.setState("open");
        bean1.setId("1");
        bean1.setText("常用单位");
        returnList.add(bean1);
        
        if (depts != null && depts.size() > 0) {
            for (String d : depts) {
                    CommonTreeBean bean = new CommonTreeBean();
                    bean.setState("open");
//                    bean.setId(d.getId().toString());
                    bean.setText(d);
                    returnList.add(bean);
            }
        }
        return returnList;
    }
}
