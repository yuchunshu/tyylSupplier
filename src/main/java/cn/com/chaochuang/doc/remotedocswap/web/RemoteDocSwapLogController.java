/*
 * FileName:    RemoteDocSwapEnvelopeController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.doc.remotedocswap.bean.DocSwapLogShowBean;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapLog;
import cn.com.chaochuang.doc.remotedocswap.repository.RemoteDocSwapLogRepository;


/** 
 * @ClassName: RemoteDocSwapLogController 
 * @Description: 公文交换日志——控制跳转
 * @author: chunshu
 * @date: 2017年7月13日 上午9:54:46  
 */
@Controller
@RequestMapping("remotedocswap/log")
public class RemoteDocSwapLogController {

    @Autowired
    private ConversionService          conversionService;
    
    @Autowired
    private RemoteDocSwapLogRepository logRepository;
    
    @Autowired
    private LogService             	   logService;
    
    /**
     * 限时公文日志页面
     */
    @RequestMapping("/docLogList")
    public ModelAndView list() {
    	ModelAndView mav = new ModelAndView("/doc/remotedocswap/log/docLogList");
         
        return mav;
    }

    /**
     * 列表数据——限时公文日志查询
     */
    @RequestMapping("/listDocLog.json")
    @ResponseBody
    public Page docSendListJson(Integer page, Integer rows, HttpServletRequest request) {
    	Page p = new Page();
    	try {
    		SearchBuilder<RemoteDocSwapLog, Long> searchBuilder = new SearchBuilder<RemoteDocSwapLog, Long>(
                    conversionService);
		    searchBuilder.clearSearchBuilder().findSearchParam(request);
		    // 排序
		    searchBuilder.appendSort(Direction.DESC, "logTime");
		
		    SearchListHelper<RemoteDocSwapLog> listHelper = new SearchListHelper<RemoteDocSwapLog>();
		    listHelper.execute(searchBuilder, logRepository, page, rows);
		    
		    p.setRows(BeanCopyBuilder.buildList(listHelper.getList(), DocSwapLogShowBean.class));
		    p.setTotal(listHelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "公文交换-限时日志查询-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
    	
        return p;
    }
    
    
    
}
