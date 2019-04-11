package cn.com.chaochuang.supplier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.supplier.domain.SupGroup;

public interface SupGroupRepository extends SimpleDomainRepository<SupGroup, Long> {

    /**
     * 查询根节点
     * @return
     */
    @Query("select f from SupGroup f where f.parentId is null or f.id=f.parentId order by f.groupCode asc ")
    public List<SupGroup> getRoots();

    /**
     * 查询子节点数量
     * @param parentId
     * @return
     */
    @Query("select count(*) from SupGroup f where f.parentId=:va and f.id<>f.parentId")
    public Integer haveChildren(@Param("va") Long parentId);

    /**
     * 查询子节点
     * @param parentId
     * @return
     */
    public List<SupGroup> findByParentIdOrderByGroupCodeAsc(Long parentId);

}
