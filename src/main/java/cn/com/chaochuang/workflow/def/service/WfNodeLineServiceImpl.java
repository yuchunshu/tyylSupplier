package cn.com.chaochuang.workflow.def.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.workflow.def.domain.WfNodeLine;
import cn.com.chaochuang.workflow.def.repository.WfNodeLineRepository;

/**
 * @author hzr 2017年10月13日
 *
 */
@Service
@Transactional
public class WfNodeLineServiceImpl extends SimpleUuidCrudRestService<WfNodeLine>  implements WfNodeLineService {

    @Autowired
    private WfNodeLineRepository repository;

	@Override
	public SimpleDomainRepository<WfNodeLine, String> getRepository() {
		return repository;
	}


	@Override
	public List<WfNodeLine> getToNodes(String flowId, String fromNodeId) {
		return this.repository.findByFlowIdAndFromNodeId(flowId, fromNodeId);
	}


	@Override
	public List<WfNodeLine> getNextNodes(String flowId, String fromNodeId) {
		return this.repository.findByFlowIdAndFromNodeIdAndDirFlag(flowId, fromNodeId, "0");
	}

	@Override
	public List<WfNodeLine> getBackNodes(String flowId, String fromNodeId) {
		return this.repository.findByFlowIdAndFromNodeIdAndDirFlag(flowId, fromNodeId, "1");
	}


	@Override
	public String getAutoSubmitNodeIdByFromNodeId(String flowId, String fromNodeId) {
		List<WfNodeLine> list = getNextNodes(flowId, fromNodeId);
		for(WfNodeLine line: list) {
			if(YesNo.是.getKey().equals(line.getAutoSubmit())) {
				return line.getToNodeId();
			}
		}
		return "";
	}

	@Override
	public String getFirstNextNodeId(String flowId, String fromNodeId) {
		List<WfNodeLine> list = getNextNodes(flowId, fromNodeId);
		if (list != null && !list.isEmpty()) {
			return list.get(0).getToNodeId();
		}
		return "";
	}

	@Override
	public List<WfNodeLine> findByFlowIdAndDirFlag(String flowId, String dirFlag) {
		return this.repository.findByFlowIdAndDirFlag(flowId, dirFlag);
	}



}
