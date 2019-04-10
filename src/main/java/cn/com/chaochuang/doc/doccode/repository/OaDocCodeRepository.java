package cn.com.chaochuang.doc.doccode.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.doc.doccode.domain.OaDocCode;
import cn.com.chaochuang.doc.event.reference.DocCate;
import cn.com.chaochuang.workflow.reference.WfBusinessType;

/**
 * @author yuandl 2016-12-04
 *
 */
public interface OaDocCodeRepository extends SimpleDomainRepository<OaDocCode, Long> {

    public List<OaDocCode> findByCodeTypeOrderByCodeSortAsc(WfBusinessType codeType);

    @Query("select f.docCate from OaDocCode f where f.codeType=:codeType group by f.docCate")
    List<DocCate> findByCodeTypeGroupByDocCate(@Param("codeType") WfBusinessType codeType);

    List<OaDocCode> findByDocCateAndCodeTypeOrderByCodeSortAsc(DocCate docCate, WfBusinessType codeType);

    List<OaDocCode> findByCodeNameAndCodeType(String codeName, WfBusinessType codeType);

    List<OaDocCode> findByTemplateId(Long templateId);
}
