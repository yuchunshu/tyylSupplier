package cn.com.chaochuang.doc.expiredate.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.expiredate.service.SysNodeExpireDateService;
import cn.com.chaochuang.workflow.def.domain.WfNode;
import cn.com.chaochuang.workflow.def.repository.WfNodeRepository;
import cn.com.chaochuang.workflow.reference.WfBusinessType;



/** 
 * @ClassName: NodeExpireDateController 
 * @Description: 环节限办时间设置
 * @author: yuchunshu
 * @date: 2017年10月11日 下午5:52:12  
 */
@Controller
@RequestMapping("doc/nodeExpireDate")
public class NodeExpireDateController {

    @Autowired
    protected WfNodeRepository 	   	   nodeRepository;
    
    @Autowired
    protected SysNodeExpireDateService sysNodeExpireDateService;

    @RequestMapping("/editNodeExpireDate")
    public ModelAndView editNodeExpireDate(String expiryDate,String flowId,String flowType,String nodeExpireMinutes,String nodeIds) {
        ModelAndView mav = new ModelAndView("/doc/receivefile/editNodeExpireDate");
        List<WfNode> flowNodeList = new ArrayList<WfNode>();
        if(WfBusinessType.收文.getKey().equals(flowType)){
        	flowNodeList = nodeRepository.findByFlowIdAndNodeNameNotInOrderByIdAsc(flowId,new String[]{"收文登记"});
            // 窗口高度
            mav.addObject("dialogHeight", "350");
        }else if(WfBusinessType.发文.getKey().equals(flowType)){
        	flowNodeList = nodeRepository.findByFlowIdAndNodeNameNotInOrderByIdAsc(flowId,new String[]{"草拟","处室审核","局办公室核文"});
            // 窗口高度
            mav.addObject("dialogHeight", "450");
        }
        
        // 显示名称
        mav.addObject("dataText", "userName");
        // 标题
        mav.addObject("selectDialogTitle", "环节限办时间设置");
        // 窗口宽度
        mav.addObject("dialogWidth", "550");
        
        mav.addObject("expiryDate", expiryDate);
        mav.addObject("flowNodeList", flowNodeList);
        
        String[] nodeIdsArr = nodeIds.split(",");
        String[] nodeExpireMinutesArr = nodeExpireMinutes.split(",");
        
        Map<String,Integer> nodeMap = new HashMap<String,Integer>();
        for(int i=0;i<nodeIdsArr.length;i++){
        	if(!Tools.isEmptyString(nodeIdsArr[i])){
        		nodeMap.put(nodeIdsArr[i], Integer.parseInt(nodeExpireMinutesArr[i]));
        	}
        }
        
        mav.addObject("nodeMap", nodeMap);
        return mav;
    }
    
}
