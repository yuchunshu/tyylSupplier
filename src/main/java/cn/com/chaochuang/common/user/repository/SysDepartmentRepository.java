/*
 * FileName:    SysDepartmentRepository.java
 * Description:
 * Company:     
 * Copyright:    (c) 2014
 * History:     2014年7月18日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.reference.AdministrativeLevel;
import cn.com.chaochuang.common.user.domain.SysDepartment;

/**
 * @author LaoZhiYong
 *
 */
public interface SysDepartmentRepository extends SimpleDomainRepository<SysDepartment, Long> {

    public List<SysDepartment> findByDeptParentAndValidOrderByOrderNumAsc(Long parentId, Integer valid);

    public List<SysDepartment> findByDeptParentAndValidAndDeptLevelOrderByOrderNumAsc(Long parentId, Integer valid,AdministrativeLevel deptLevel);

    
    @Query("select d from SysDepartment d where d.id=d.deptParent and d.id=d.unitId and d.valid=:va order by d.orderNum ")
    public List<SysDepartment> findByRootDepartment(@Param("va") Integer valid);
    
    @Query("select d from SysDepartment d where d.id=d.unitId and d.valid=:va and d.deptLevel=:deptLevel order by d.orderNum ")
    public List<SysDepartment> findByRootDepartmentByLevel(@Param("va") Integer valid,@Param("deptLevel") AdministrativeLevel deptLevel);

    @Query("select d from SysDepartment d where d.unitId is not null and d.valid=:va order by d.orderNum ")
    public List<SysDepartment> findAllRuleoutUnit(@Param("va") Integer valid);

    public List<SysDepartment> findByValid(Integer valid);

    public List<SysDepartment> findByDeptName(String deptName);

    public List<SysDepartment> findByDeptNameAndValid(String deptName, Integer valid);

    public List<SysDepartment> findByValidOrderByOrderNumAscIdAsc(Integer valid);

    public List<SysDepartment> findByValidOrderByOrderNumAsc(Integer valid);
    
    public List<SysDepartment> findByDeptNameLikeAndValid(String deptName, Integer valid);
    
    List<SysDepartment> findByDeptCodeLikeAndValid(@Param("deptCode") String deptCode,@Param("valid") Integer valid);

//    @Query("select d from SysDepartment d where d.valid=:valid and d.deptType=:deptType and length(d.deptCode)=:codeLength order by d.orderNum ")
//    List<SysDepartment> findByDeptTypeAndDeptCodeLength(@Param("deptType") String deptType,@Param("codeLength") Integer codeLength,@Param("valid") Integer valid);

}
