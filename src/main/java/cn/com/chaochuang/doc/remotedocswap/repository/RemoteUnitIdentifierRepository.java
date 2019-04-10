/*
 * FileName:    RemoteUnitIdentifierRepository.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteUnitIdentifier;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteUnitFlag;

/**
 * @author yuandl
 *
 */
public interface RemoteUnitIdentifierRepository extends SimpleDomainRepository<RemoteUnitIdentifier, Long> {

    /**
     * 根据标识查找交换单位
     */
    public List<RemoteUnitIdentifier> findByIdentifierFlag(RemoteUnitFlag flag);

    /**
     * 根据名称和单位查找交换单位
     */
    public List<RemoteUnitIdentifier> findByUnitNameLikeAndUnitIdentifierLikeOrderByIdAsc(String unitName,
                    String identifier);
    
    /**
     * 查询单位标识前9位和名称
     */
    @Query("select substring(unitIdentifier, 1, 9) as unitIdentifier, unitName from RemoteUnitIdentifier")
    public List<Object[]> findAllBySubUnitIdentifier();
    
    public List<RemoteUnitIdentifier> findByIdentifierNameAndValidOrderByOrderNumAsc(String identifierName, Integer valid);
    
    @Query("select f.identifierName from RemoteUnitIdentifier f where f.identifierFlag != 0 group by f.identifierName,f.identifierFlag order by f.identifierFlag asc")
    public List<String> findByIdentifierFlagGroupByIdentifierName();

}
