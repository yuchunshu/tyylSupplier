package cn.com.chaochuang.workflow.def.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.domain.ValidAble;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.reference.ManOrDept;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.select.bean.UserSelectShowBean;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.repository.SysDepartmentRepository;
import cn.com.chaochuang.common.user.repository.SysUserRepository;
import cn.com.chaochuang.common.user.service.SysDepartmentService;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.workflow.def.bean.NodeDeptEditBean;
import cn.com.chaochuang.workflow.def.bean.NodeDeptSelectTreeBean;
import cn.com.chaochuang.workflow.def.domain.WfNode;
import cn.com.chaochuang.workflow.def.domain.WfNodeDept;
import cn.com.chaochuang.workflow.def.repository.WfNodeDeptRepository;
import cn.com.chaochuang.workflow.his.domain.WfHisDealer;
import cn.com.chaochuang.workflow.his.repository.WfHisDealerRepository;

@Service
@Transactional
public class WfNodeDeptServiceImpl extends SimpleLongIdCrudRestService<WfNodeDept>
                implements WfNodeDeptService {

    @Autowired
    private WfNodeDeptRepository    repository;

    @Autowired
    private SysUserService          userService;

    @Autowired
    private SysUserRepository       userRepository;

    @Autowired
    private SysDepartmentService    departmentService;

    @Autowired
    private WfNodeService       	nodeService;

    @Autowired
    private SysDepartmentRepository departmentRepository;

    @Autowired
    private WfHisDealerRepository   hisDealerRepository;

    @Override
    public SimpleDomainRepository<WfNodeDept, Long> getRepository() {
        return repository;
    }

    @Override
    public Long saveNodeDept(NodeDeptEditBean bean) {
        WfNodeDept receive = null;
        if (bean != null && bean.getId() != null) {
            receive = repository.findOne(bean.getId());
        } else {
            receive = new WfNodeDept();
        }
        BeanUtils.copyProperties(bean, receive);
        repository.save(receive);

        return receive.getId();
    }

    @Override
    public void delNodeDept(Long id) {
        if (id != null) {
            WfNodeDept receive = repository.findOne(id);
            if (receive != null) {
                repository.delete(receive);
            }
        }
    }

    @Override
    public List<WfNodeDept> findByFlowIdAndNodeId(String flowId, String nodeId) {
        return repository.findByFlowIdAndNodeId(flowId, nodeId);
    }

    @Override
    public String allNodeDept(String flowId, List<WfNode> nodes) {
        if (StringUtils.isNotBlank(flowId) && Tools.isNotEmptyList(nodes)) {
            Map<String, String> map = new HashMap<String, String>();
            for (WfNode node : nodes) {
                // 先找接收人,找到下一步就选人
                if (StringUtils.isNotBlank(node.getReceiverIds())) {
                    map.put(node.getId(), ManOrDept.个人.getKey());
                } else {
                // 没有接收人就找接收部门
                    List<WfNodeDept> receiveDeps = this.repository.findByFlowIdAndNodeId(flowId, node.getId());
                    if (Tools.isNotEmptyList(receiveDeps)) {
                    	 map.put(node.getId(), ManOrDept.部门.getKey());
                        }
                    }
                }

            return JSONObject.toJSONString(map);
        }
        return null;
    }


    @Override
    public String nextNodeManOrDept(String flowId, String nodeId) {
        if (StringUtils.isNotBlank(flowId) && StringUtils.isNotBlank(nodeId)) {
            // 先找接收人,找到下一步就选人
            WfNode node = this.nodeService.findOne(nodeId);
            if (StringUtils.isNotBlank(node.getReceiverIds())) {
                return ManOrDept.个人.getKey();
            } else {
            // 没有接收人就找接收部门
                List<WfNodeDept> receiveDeps = this.repository.findByFlowIdAndNodeId(flowId, nodeId);
                if (Tools.isNotEmptyList(receiveDeps)) {
                	 return ManOrDept.部门.getKey();
                }
            }
        }
        return null;
    }


    @Override
    public List<UserSelectShowBean> selectReceiver(String flowId, String nodeId,String userNameLike) {
    	if(StringUtils.isNotBlank(nodeId) && StringUtils.isNotBlank(flowId)){
    		WfNode node = this.nodeService.findOne(nodeId);

    		if (StringUtils.isNotBlank(node.getReceiverIds())) {
    			String[] receives = node.getReceiverIds().split(",");
	            List<UserSelectShowBean> users = new ArrayList<UserSelectShowBean>();
	            for (int i=0; i<receives.length; i++) {
	                SysUser u = this.userService.findOne(new Long(receives[i]));
	                if (null != u) {
	                	if(StringUtils.isNotBlank(userNameLike)){
	                		if(u.getUserName().contains(userNameLike)){
	                			users.add(BeanCopyBuilder.buildObject(u, UserSelectShowBean.class));
	                		}
	                	}else{
	                		users.add(BeanCopyBuilder.buildObject(u, UserSelectShowBean.class));
	                	}
	                }
	            }
	            return users;
	        }
    	}
        return null;
    }

    @Override
    public List<NodeDeptSelectTreeBean> selectDeps(String flowId, String nodeId) {
    	if(StringUtils.isNotBlank(nodeId) && StringUtils.isNotBlank(flowId)){
	        List<WfNodeDept> receiveDeps = this.repository.findByFlowIdAndNodeId(flowId, nodeId);
	        if (Tools.isNotEmptyList(receiveDeps)) {
	            // 定义接收部门树
	            List<NodeDeptSelectTreeBean> deps = new ArrayList<NodeDeptSelectTreeBean>();
	            // 接收部门对应用户
	            Map<Long, List<UserSelectShowBean>> depOfUsers = new HashMap<Long, List<UserSelectShowBean>>();
	            for (WfNodeDept receiveDep : receiveDeps) {
	                SysDepartment d = this.departmentService.findOne(receiveDep.getReceiveDepId());
	                if (null != d) {
	                	NodeDeptSelectTreeBean tree = new NodeDeptSelectTreeBean(d.getId(), d.getDeptName(),receiveDep.getDutyIds());
	                    List<SysUser> users = this.userRepository.findByDepartmentIdAndValidOrderBySortAsc(d.getId(),
	                                    ValidAble.VALID);
	                    if (Tools.isNotEmptyList(users)) {
	                        // 将部门下的用户放进depOfUsers
	                        depOfUsers.put(d.getId(), BeanCopyBuilder.buildList(users, UserSelectShowBean.class));
	                    }
	                    // 子部门
	                    List<SysDepartment> subDeps = this.departmentRepository
	                                    .findByDeptParentAndValidOrderByOrderNumAsc(d.getId(), ValidAble.VALID);
	                    if (Tools.isNotEmptyList(subDeps)) {
	                        List<NodeDeptSelectTreeBean> subTrees = new ArrayList<NodeDeptSelectTreeBean>();
	                        for (SysDepartment subDep : subDeps) {
	                        	NodeDeptSelectTreeBean tree2 = new NodeDeptSelectTreeBean(subDep.getId(), subDep.getDeptName(),receiveDep.getDutyIds());
	                            tree2.setState("open");
	                            subTrees.add(tree2);
	                            List<SysUser> users2 = this.userRepository
	                                            .findByDepartmentIdAndValidOrderBySortAsc(subDep.getId(), ValidAble.VALID);
	                            if (Tools.isNotEmptyList(users2)) {
	                                depOfUsers.put(subDep.getId(),
	                                                BeanCopyBuilder.buildList(users2, UserSelectShowBean.class));
	                            }
	                        }
	                        // 将子部门放进父部门
	                        tree.setChildren(subTrees);
	                    }
	                    // 将部门数放进部门列表中
	                    deps.add(tree);
	                }

	            }
	            return deps;
	        }
    	}
        return null;
    }

    @Override
    public List<UserSelectShowBean> selectHistory(String nodeId) {
        List<UserSelectShowBean> hisUsers = new ArrayList<UserSelectShowBean>();
    	if(StringUtils.isNotBlank(nodeId)){
	        List<WfHisDealer> history = this.hisDealerRepository.findByNodeIdAndOwnerIdOrderByUseTimeDesc(nodeId,
	                        Long.valueOf(UserTool.getInstance().getCurrentUserId()));
	        if (Tools.isNotEmptyList(history)) {
	            for (int i = 0; i < history.size(); i++) {
	            	if (null != history.get(i).getDealer() && SysUser.VALID.equals(history.get(i).getDealer().getValid())) {
	                    hisUsers.add(BeanCopyBuilder.buildObject(history.get(i).getDealer(), UserSelectShowBean.class));
	                }
	                if (i >= 5) {
	                    break;
	                }
	            }
	            return hisUsers;
	        }
        }
        return hisUsers;
    }

}
