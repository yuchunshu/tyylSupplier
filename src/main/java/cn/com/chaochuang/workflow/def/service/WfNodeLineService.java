/**
 *
 */
package cn.com.chaochuang.workflow.def.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.workflow.def.domain.WfNodeLine;

/**
 * @author hzr 2017年10月12日
 *
 */
public interface WfNodeLineService  extends CrudRestService<WfNodeLine, String> {

	/**取下个目标环节（包括正向和反向）*/
	List<WfNodeLine> getToNodes(String flowId, String fromNodeId);

	/**取下个目标环节（多个）*/
	List<WfNodeLine> getNextNodes(String flowId, String fromNodeId);
	/**取可退回环节（多个）*/
	List<WfNodeLine> getBackNodes(String flowId, String fromNodeId);

	/**取属性为自动提交的目标环节ID*/
	String getAutoSubmitNodeIdByFromNodeId(String flowId, String fromNodeId);

	/**取下个目标环节（有多个就默认取第一个）*/
	String getFirstNextNodeId(String flowId, String fromNodeId);

	List<WfNodeLine> findByFlowIdAndDirFlag(String flowId, String dirFlag);


}
