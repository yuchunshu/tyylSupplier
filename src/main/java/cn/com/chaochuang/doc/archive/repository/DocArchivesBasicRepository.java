package cn.com.chaochuang.doc.archive.repository;

import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.doc.archive.domain.DocArchivesBasic;
import cn.com.chaochuang.doc.archive.reference.BasicType;

/**
 * @author shiql 2017.11.22
 *
 */
public interface DocArchivesBasicRepository extends SimpleDomainRepository<DocArchivesBasic, String> {

    List<DocArchivesBasic> findByBasicCode(String basicCode);
    
    List<DocArchivesBasic> findByBasicType(BasicType basicType);
    
    List<DocArchivesBasic> findByBasicCodeAndBasicTypeAndDepId(String basicCode,BasicType basicType,Long depId);
    
    List<DocArchivesBasic> findByBasicTypeAndDepId(BasicType basicType,Long depId);

}
