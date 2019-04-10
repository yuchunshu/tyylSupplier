package cn.com.chaochuang.doc.event.repository;



import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.doc.event.domain.OaOuterDocFileFeedBack;

/**
 * @author yuchunshu 2017.11.25
 *
 */
public interface OaOuterDocFileFeedBackRepository extends SimpleDomainRepository<OaOuterDocFileFeedBack, String> {

	List<OaOuterDocFileFeedBack> findByOuterIdOrderByCreateTimeDesc(String outerId);
}
