package cn.com.chaochuang.doc.archive.repository;

import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.doc.archive.domain.DocArchivesBorrow;

/**
 * @author dengl 2017.11.30
 *
 */
public interface DocArchivesBorrowRepository extends SimpleDomainRepository<DocArchivesBorrow,String>{
	
	List<DocArchivesBorrow> findByArchId(Long archId);

}
