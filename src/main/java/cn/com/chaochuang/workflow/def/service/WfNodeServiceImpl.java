package cn.com.chaochuang.workflow.def.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleCrudRestService;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.service.SysDepartmentService;
import cn.com.chaochuang.workflow.def.bean.NodeChartBean;
import cn.com.chaochuang.workflow.def.bean.NodeDeptEditBean;
import cn.com.chaochuang.workflow.def.bean.NodeEditBean;
import cn.com.chaochuang.workflow.def.domain.WfNode;
import cn.com.chaochuang.workflow.def.domain.WfNodeDept;
import cn.com.chaochuang.workflow.def.domain.WfNodeLine;
import cn.com.chaochuang.workflow.def.repository.WfNodeRepository;
import cn.com.chaochuang.workflow.util.WorkflowUtils;

/**
 * @author hzr 2017-5-8
 *
 */
@Service
@Transactional
public class WfNodeServiceImpl extends SimpleCrudRestService<WfNode, String> implements WfNodeService {

    @Autowired
    private WfNodeRepository 	    repository;

    @Autowired
    private WfNodeDeptService    	nodeDeptService;

    @Autowired
    private WfNodeLineService    	nodeLineService;

    @Autowired
    private SysDepartmentService 	deptService;

    @Override
    public SimpleDomainRepository<WfNode, String> getRepository() {
        return repository;
    }

    private int getNewNodeSort(String flowId) {
        List<WfNode> list = this.findByFlowId(flowId);
        if (list != null && list.size() > 0) {
        	return list.size() + 1;
        } else {
        	//说明是首个环节
        	return 1;
        }
    }

    @Override
    public String saveNode(NodeEditBean bean) {
        WfNode node = null;
        if (StringUtils.isNotBlank(bean.getId())) {
            node = repository.findOne(bean.getId());
        }
        if(node == null){
            node = new WfNode();
        }
    	if (bean.getSort() == null) {
    		bean.setSort(getNewNodeSort(bean.getFlowId()));
    	}
        BeanUtils.copyProperties(bean, node);
        repository.save(node);


        if (bean.getSort().intValue() == 1) {
        	//创建首环节流向
        	List<WfNodeLine> list = this.nodeLineService.getNextNodes(node.getFlowId(), WorkflowUtils.NODE_START);
        	if (list != null && list.isEmpty()) {
            	WfNodeLine line = new WfNodeLine(node.getFlowId(), WorkflowUtils.NODE_START, node.getId(), "0");
            	this.nodeLineService.persist(line);
        	}
        }

        //先清空所有流向
    	List<WfNodeLine> list = this.nodeLineService.getToNodes(node.getFlowId(), node.getId());
    	for(WfNodeLine line: list) {
    		this.nodeLineService.delete(line);
    	}
        //保存正向流向信息
        if(StringUtils.isNotBlank(bean.getNextNodeIds())) {
        	String[] ids = bean.getNextNodeIds().split(",");
        	for (String id: ids) {
        		if (id.equals(node.getId())) continue;

        		WfNodeLine line = new WfNodeLine(node.getFlowId(), node.getId(), id, "0");
        		if (id.equals(bean.getSubmitNodeId())) {
        			line.setAutoSubmit(YesNo.是.getKey());
        		}
        		this.nodeLineService.persist(line);
        	}
        }

        //保存退回流向信息
        if(StringUtils.isNotBlank(bean.getBackNodeIds())) {
        	String[] ids = bean.getBackNodeIds().split(",");
        	for (String id: ids) {
        		if (id.equals(node.getId())) continue;

        		WfNodeLine line = new WfNodeLine(node.getFlowId(), node.getId(), id, "1");
        		line.setLineTitle("退回");
        		this.nodeLineService.persist(line);
        	}
        }


        List<WfNodeDept> ndlist = this.nodeDeptService.findByFlowIdAndNodeId(node.getFlowId(), node.getId());
    	for(WfNodeDept nd: ndlist) {
    		this.nodeDeptService.delete(nd);
    	}
        //有办理部门定义
        if(StringUtils.isNotBlank(bean.getNodeDeptIds())) {
        	String[] ids = bean.getNodeDeptIds().split(",");
        	for (String id: ids) {
        		SysDepartment dept = this.deptService.findOne(new Long(id));
        		if (dept != null) {
        			NodeDeptEditBean obj = new NodeDeptEditBean();
            		obj.setFlowId(node.getFlowId());
            		obj.setNodeId(node.getId());
            		obj.setReceiveDepId(dept.getId());
            		obj.setReceiveAncestorDep(dept.getUnitId());
            		this.nodeDeptService.saveNodeDept(obj);
        		}
        	}
        }

        return node.getId();
    }

    @Override
    public void delNode(String id) {
        if (id != null) {
            WfNode node = repository.findOne(id);
            if (node != null) {
                repository.delete(node);
            }
        }
    }

    @Override
    public WfNode getByNodeId(String nodeId) {

    	if (WorkflowUtils.NODE_END.equals(nodeId)) {
    		WfNode fn = new WfNode();
    		fn.setId(nodeId);
    		return fn;
    	}

    	WfNode node = this.findOne(nodeId);
	    if (node == null) {
			throw new RuntimeException("环节定义表出错，找不到相应环节配置。");
	    } else {
	    	return node;
	    }
    }

    @Override
    public List<WfNode> findByFlowIdAndComFlag(String flowId, YesNo comFlag) {
        return repository.findByFlowIdAndComFlag(flowId, comFlag);
    }

    @Override
    public List<WfNode> findByFlowId(String flowId) {
        return repository.findByFlowIdOrderByIdAsc(flowId);
    }

    @Override
    public List<WfNode> findBySubflowId(String subflowId) {
        return repository.findBySubflowIdOrderByIdAsc(subflowId);
    }


	@Override
	public WfNode getFirstTrueNode(String flowId) {
		List<WfNodeLine> list = this.nodeLineService.getNextNodes(flowId, WorkflowUtils.NODE_START);
		if (list == null || list.isEmpty()) {
		    throw new RuntimeException("请检查流程开始环节是否已配置。");
		}
		// 第一个环节定义
		return list.get(0).getToNode();
	}

	@Override
	public List<NodeChartBean> findByFlowIdAndNodeIdIsNotExist(String flowId, String nodeIds) {
		List<NodeChartBean> returnList = new ArrayList<NodeChartBean>();//要返回的环节List
		//该流程所有的环节,包括开始和结束
		List<WfNode> fnList = this.findByFlowIdOrStartIdOrEndId(flowId,WorkflowUtils.NODE_START,WorkflowUtils.NODE_END);
		List<WfNodeLine> lineList = this.nodeLineService.findByFlowIdAndDirFlag(flowId, "0");//正方向的线

		//设置环节，并连线
		for(WfNode node:fnList){
			NodeChartBean bean = new NodeChartBean();
			String curNodeId = node.getId();
			bean.setNodeId(curNodeId);
			bean.setNodeName(node.getNodeName());
			for(WfNodeLine line : lineList){//连线
				String fromNodeId = line.getFromNodeId();
				String toNodeId = line.getToNodeId();
				if(StringUtils.isNotBlank(fromNodeId) && fromNodeId.equals(curNodeId)){
					bean.setNextNodeIds((bean.getNextNodeIds()==null?"":bean.getNextNodeIds())+toNodeId+",");
				}
				if(StringUtils.isNotBlank(toNodeId) && toNodeId.equals(curNodeId)){
					bean.setPrevNodeIds((bean.getPrevNodeIds()==null?"":bean.getPrevNodeIds())+fromNodeId+",");
				}
			}
			returnList.add(bean);
		}

		//流程图已有的环节不再需要
		Iterator<NodeChartBean> it = returnList.iterator();
		while(it.hasNext()){
			NodeChartBean bean = it.next();
			if(nodeIds.indexOf(bean.getNodeId()) >= 0){
				it.remove();
			}
		}
		return returnList;
	}

	@Override
	public List<WfNode> findByFlowIdOrStartIdOrEndId(String flowId, String startId, String endId) {
		if(StringUtils.isBlank(startId)){
			startId = "";
		}
		if(StringUtils.isBlank(endId)){
			endId = "";
		}
		return this.repository.findByFlowIdAndEndId(flowId, startId, endId);
	}
}
