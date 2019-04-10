/*
 * FileName:    RemoteDocSwapConfigController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2017年9月18日 (yuchunshu) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapConfig;
import cn.com.chaochuang.doc.remotedocswap.service.RemoteDocSwapConfigService;


/** 
 * @ClassName: RemoteDocSwapConfigController 
 * @Description: 限时公文配置——控制跳转
 * @author: chunshu
 * @date: 2017年9月18日 上午9:54:46  
 */
@Controller
@RequestMapping("remotedocswap/config")
public class RemoteDocSwapConfigController {

    @Autowired
    private RemoteDocSwapConfigService      configService;
    
    @Autowired
    private LogService             			logService;
    
    /**
     * 编辑页面
     */
    @RequestMapping("/edit")
    public ModelAndView editPage(Long id) {
        ModelAndView mav = new ModelAndView("/doc/remotedocswap/config/edit");
        
        //短信配置
        Map<String, RemoteDocSwapConfig> configMap = new HashMap<String, RemoteDocSwapConfig>();
        List<RemoteDocSwapConfig> configList = configService.getRepository().findAll();
        if(configList!=null){
            for(RemoteDocSwapConfig config : configList){
                if(config.getConfigType()==null) {
                    configMap.put(config.getConfigName(),config);
                }else{
                    configMap.put(config.getConfigName()+"+"+config.getConfigType(),config);
                }
            }
        }
        mav.addObject("configMap",configMap);

        return mav;
    }

    /**
     * 保存数据
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo save(HttpServletRequest request, HttpServletResponse response) {

        try {
        	String[] configIdStr = request.getParameterValues("configId");
            String[] configValue = request.getParameterValues("configValue");
            
            boolean result = configService.saveSwapConfigList(configIdStr,configValue);
            
            return new ReturnInfo("1", null, "保存成功！");
        } catch (Exception e) {
        	logService.saveLog(SjType.普通操作, "公文交换-限时公文配置：失败！",LogStatus.失败, request);
			e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }
    
}
