/*
 * FileName:    DocsFolderRepository.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月13日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.docs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.oa.docs.domain.DocsFolder;

/**
 * @author HM
 *
 */
public interface DocsFolderRepository extends SimpleDomainRepository<DocsFolder, Long> {

    /**
     * 查询根节点
     * @return
     */
    @Query("select f from DocsFolder f where f.parentId is null or f.id=f.parentId order by f.folderCode asc ")
    public List<DocsFolder> getRoots();

    /**
     * 查询子节点数量
     * @param parentId
     * @return
     */
    @Query("select count(*) from DocsFolder f where f.parentId=:va and f.id<>f.parentId")
    public Integer haveChildren(@Param("va") Long parentId);

    /**
     * 查询子节点
     * @param parentId
     * @return
     */
    public List<DocsFolder> findByParentIdOrderByFolderCodeAsc(Long parentId);

}
