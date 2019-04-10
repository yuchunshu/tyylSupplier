/*
 * FileName:    RemoteWorkDateRepository.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.repository;

import java.util.List;


import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteWorkDate;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteWorkDateType;


/** 
 * @ClassName: RemoteWorkDateRepository 
 * @Description: 工作日jpa
 * @author: chunshu
 * @date: 2017年8月4日 上午11:02:19  
 */
public interface RemoteWorkDateRepository extends SimpleDomainRepository<RemoteWorkDate, Long> {

    public List<RemoteWorkDate> findByFromDateAndType(String fromDate, RemoteWorkDateType type);
    
    public List<RemoteWorkDate> findByType(RemoteWorkDateType type);
    
    public void deleteByType(RemoteWorkDateType type);
    
    public void deleteByTypeAndFromDate(RemoteWorkDateType type,String FromDate);
    
    public RemoteWorkDate findByFromDateAndTypeAndWorkDays(String fromDate, RemoteWorkDateType type,Integer workDays);
    
    public RemoteWorkDate findByToDateAndType(String toDate, RemoteWorkDateType type);
}
