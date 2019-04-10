package cn.com.chaochuang.doc.event.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.event.bean.OuterDocFileEditBean;
import cn.com.chaochuang.doc.event.domain.OaOuterDocFile;
import cn.com.chaochuang.doc.event.domain.OaOuterDocFileFeedBack;
import cn.com.chaochuang.doc.event.repository.OaOuterDocFileFeedBackRepository;
import cn.com.chaochuang.doc.event.repository.OaOuterDocFileRepository;

/**
 * @author yuchunshu 2017.11.25
 *
 */
@Service
@Transactional
public class OaOuterDocFileFeedBackServiceImpl extends SimpleUuidCrudRestService<OaOuterDocFileFeedBack> implements OaOuterDocFileFeedBackService {

	@Autowired
    private OaOuterDocFileFeedBackRepository 		repository;
	
	@Autowired
	private OaOuterDocFileRepository 				outerDocRepository;
	
	@Override
	public SimpleDomainRepository<OaOuterDocFileFeedBack, String> getRepository() {
		return this.repository;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map findOuterDocMapByOuterId(String outerId) {
		Map feedBackMap = new HashMap();
		List<OaOuterDocFileFeedBack> feedBackList = repository.findByOuterIdOrderByCreateTimeDesc(outerId);
		if (Tools.isNotEmptyList(feedBackList)) {
			for (OaOuterDocFileFeedBack feedBack : feedBackList) {
				if (feedBack.getFeedbackType() != null) {
					List<OaOuterDocFileFeedBack> preList = (List<OaOuterDocFileFeedBack>) feedBackMap
							.get(feedBack.getFeedbackType().getKey());
					if (preList == null) {
						preList = new ArrayList<OaOuterDocFileFeedBack>();
					}
					preList.add(feedBack);
					feedBackMap.put(feedBack.getFeedbackType().getKey(), preList);
				}

			}
		}
		return feedBackMap;
	}

	@Override
	public boolean saveFeedBack(OuterDocFileEditBean bean) {

		OaOuterDocFile obj = outerDocRepository.findOne(bean.getId().toString());
		Date now = new Date();
		OaOuterDocFileFeedBack feedBack = new OaOuterDocFileFeedBack();
		
		obj.setStatus(bean.getFeedbackType());
		obj.setFinishTime(now);
		outerDocRepository.save(obj);
		
		
		
		feedBack.setFeedbackType(bean.getFeedbackType());
		feedBack.setContactName(bean.getContactName());
		feedBack.setContactPhone(bean.getContactPhone());
		feedBack.setReason(bean.getReason());
		feedBack.setOuterId(obj.getId());
		feedBack.setCreateTime(now);
		feedBack.setBackTime(bean.getBackTime());
		feedBack.setUndoTime(bean.getUndoTime());
		feedBack.setInvalidTime(bean.getInvalidTime());
		feedBack.setDocReadBody(bean.getDocReadBody());
		repository.save(feedBack);
		
		return false;
	
	}
    

}
