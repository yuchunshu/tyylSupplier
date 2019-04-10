/*
 * FileName:    RemoteDocSwapEnvelopeController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.web;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.remotedocswap.bean.DocStatisticQueryData;
import cn.com.chaochuang.doc.remotedocswap.bean.DocStatisticResult;
import cn.com.chaochuang.doc.remotedocswap.bean.DocSwapEnvelopeShowBean;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapConfig;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapContent;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapEnvelope;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteWorkDate;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvRecFlag;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvStatus;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteWorkDateType;
import cn.com.chaochuang.doc.remotedocswap.repository.RemoteWorkDateRepository;
import cn.com.chaochuang.doc.remotedocswap.service.DocStatisticService;
import cn.com.chaochuang.doc.remotedocswap.service.RemoteDocSwapContentService;
import cn.com.chaochuang.doc.remotedocswap.service.RemoteDocSwapEnvelopeService;
import net.sf.json.JSONArray;



/** 
 * @ClassName: DocStatisticMultiController 
 * @Description: 公文交换——公文台账——控制跳转
 * @author: chunshu
 * @date: 2017年7月17日 上午9:54:46  
 */
@Controller
@RequestMapping("remotedocswap/statistic")
public class DocStatisticMultiController {
    
    @Autowired
    private DocStatisticService 	     docStatisticService;
    
    @Autowired
    private RemoteDocSwapEnvelopeService envelopeService;
    
    @Autowired
    private RemoteDocSwapContentService  contentService;
    
    @Autowired
    private ConversionService            conversionService;
    
    @Autowired
	private RemoteWorkDateRepository 	 remoteWorkDateRepository;
    
    @Autowired
    private LogService             		 logService;
    
    /**
     * 公文台账页面
     */
    @RequestMapping("{type}")
    public ModelAndView ledger(@PathVariable String type) {
    	ModelAndView mav = new ModelAndView("/doc/remotedocswap/statistic/statistic");
        switch (type) {
        case "statistic":// 公文统计
        	mav = new ModelAndView("/doc/remotedocswap/statistic/statistic");
        	
        	break;
        case "sendList":// 发送列表
        	mav = new ModelAndView("/doc/remotedocswap/statistic/sendList");
        	break;
        case "receiveList":// 接受列表
        	mav = new ModelAndView("/doc/remotedocswap/statistic/receiveList");
        	break;
        case "errorList":// 错误列表
        	mav = new ModelAndView("/doc/remotedocswap/statistic/errorList");
        	break;
        default:
            return null;
        }
        return mav;
    }
    
    /**
     * 公文统计-本系统
     */
    @RequestMapping("/statistic.json")
    @ResponseBody
    public Map statistic(String year) {
    	Map result = new HashMap();
    	result = envelopeService.statistic(year);
        return result;
    }

    /**
     * 公文统计-区直系统
     */
    @RequestMapping("/statisticByOuter.json")
    @ResponseBody
    public Map statisticByOuter(String year) {
    	Map result = new HashMap();
    	result = envelopeService.statisticByOuter(year);
        return result;
    }
    
    /**
     * 从文件中读取发送的公文数据
     * @param page
     * @param rows
     * @param request
     * @param response
     * @param queryData
     * @return
     */
    @RequestMapping("/listReadSend.json")
    @ResponseBody
    public Page readSendList(Integer page, Integer rows,HttpServletRequest request, HttpServletResponse response ,DocStatisticQueryData queryData){
        ModelAndView mav = new ModelAndView("/doc/remotedocswap/statistic/sendlist");
        
        try {
        	//将查询条件写入文件，放入指定文件夹中
        	queryData.setQueryType(DocStatisticQueryData.QUERY_TYPE_SEND);
        	queryData.setPageSize(rows);
        	queryData.setPageIndex(page);
            docStatisticService.createQueryFile(queryData);
            
            //从文件中读取发送的公文数据
            DocStatisticResult result = docStatisticService.readQueryFile(queryData);
            
            mav.addObject("result",result);
            mav.addObject("queryData",queryData);
            Page p = new Page();
            p.setRows(result.getResult());
            p.setTotal(Long.valueOf(result.getTotalCount()));
            return p;
        } catch (Exception e) {
            logService.saveLog(SjType.普通操作, "公文台账-发送列表-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
        }
		return null;
    }

    
    /**
     * 从文件中读取接收的公文数据
     * @param page
     * @param rows
     * @param request
     * @param response
     * @param queryData
     * @return
     */
    @RequestMapping("/listReadReceive.json")
    @ResponseBody
    public Page readReceiveList(Integer page, Integer rows,HttpServletRequest request, HttpServletResponse response ,DocStatisticQueryData queryData){
        ModelAndView mav = new ModelAndView("/doc/remotedocswap/statistic/receiveList");
        
        try {
        	//将查询条件写入文件，放入指定文件夹中
        	queryData.setQueryType(DocStatisticQueryData.QUERY_TYPE_RECEIVE);
        	queryData.setPageSize(rows);
        	queryData.setPageIndex(page);
            docStatisticService.createQueryFile(queryData);
            
            //从文件中读取接收的公文数据
            DocStatisticResult result = docStatisticService.readQueryFile(queryData);
            
            mav.addObject("result",result);
            mav.addObject("queryData",queryData);
            Page p = new Page();
            p.setRows(result.getResult());
            p.setTotal(Long.valueOf(result.getTotalCount()));
            return p;
        } catch (Exception e) {
        	logService.saveLog(SjType.普通操作, "公文台账-接收列表-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
        }
		return null;
    }
    
    
    /**
     * 从文件中读取接收的错误公文列表
     * @param page
     * @param rows
     * @param request
     * @param response
     * @param queryData
     * @return
     */
    @RequestMapping("/listReadError.json")
    @ResponseBody
    public Page readErrorList(Integer page, Integer rows,HttpServletRequest request, HttpServletResponse response ,DocStatisticQueryData queryData){
        ModelAndView mav = new ModelAndView("/doc/remotedocswap/statistic/errorList");
        
        try {
        	//将查询条件写入文件，放入指定文件夹中
        	queryData.setQueryType(DocStatisticQueryData.QUERY_TYPE_ERROR_DOC);
        	queryData.setPageSize(rows);
        	queryData.setPageIndex(page);
            docStatisticService.createQueryFile(queryData);
            
            //从文件中读取接收的公文数据
            DocStatisticResult result = docStatisticService.readQueryFile(queryData);
            
            mav.addObject("result",result);
            mav.addObject("queryData",queryData);
            Page p = new Page();
            p.setRows(result.getResult());
            p.setTotal(Long.valueOf(result.getTotalCount()));
            return p;
        } catch (Exception e) {
        	logService.saveLog(SjType.普通操作, "公文台账-错误公文-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
        }
		return null;
    }
 
    /**
     * 公文统计——发送查询
     */
    @RequestMapping("listDocSendByStatistic")
    public ModelAndView docSendListByStatistic(String type,String year) {
    	ModelAndView mav = new ModelAndView("/doc/remotedocswap/statistic/docSendListByStatistic");
    	mav.addObject("staType", type); 
    	
    	Date[] dateRange  = Tools.getYearRange(Integer.parseInt(year));
    	mav.addObject("sendBeginTime", dateRange[0]); 
    	mav.addObject("sendEndTime", dateRange[1]); 
        return mav;
    }
    
    /**
     * 公文统计——发送查询
     */
    @RequestMapping("/listDocSendByStatistic.json")
    @ResponseBody
    public Page docSendListByStatisticJson(Integer page, Integer rows, HttpServletRequest request) {
    	Page p = new Page();
    	try {
    		SearchBuilder<RemoteDocSwapEnvelope, Long> searchBuilder = new SearchBuilder<RemoteDocSwapEnvelope, Long>(
                    conversionService);
		    searchBuilder.clearSearchBuilder().findSearchParam(request);
		    //查询接收记录的默认条件是：收发标志为0（receiveFlag）
		    searchBuilder.getFilterBuilder().equal("receiveFlag", RemoteEnvRecFlag.发送);
		    // 排序
		    List<String> orders = new ArrayList<String>();
		    orders.add("id");
		    orders.add("envStatus");
		    orders.add("sendTime");
		    searchBuilder.appendSort(Direction.DESC, orders);
		    
		    String staType = request.getParameter("staType");//统计类型
		    
		    if("1".equals(staType)){//全部
		    	searchBuilder.getFilterBuilder().in("envStatus",
		    			new Object[] { RemoteEnvStatus.已办结,RemoteEnvStatus.不办理,RemoteEnvStatus.转阅件,
						RemoteEnvStatus.办结传阅,RemoteEnvStatus.办结归档,RemoteEnvStatus.未签收,RemoteEnvStatus.已签收 });
		    }else if("2".equals(staType)){//已办结
		    	searchBuilder.getFilterBuilder().in("envStatus",
		                new Object[] { RemoteEnvStatus.已办结,RemoteEnvStatus.不办理,RemoteEnvStatus.转阅件,RemoteEnvStatus.办结传阅,RemoteEnvStatus.办结归档 });
		    }else if("3".equals(staType)){//未办结
		    	searchBuilder.getFilterBuilder().in("envStatus",
		    			new Object[] { RemoteEnvStatus.未签收,RemoteEnvStatus.已签收 });
		    }else if("4".equals(staType)){//已预警
		    	Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 3);
		    	searchBuilder.getFilterBuilder().in("envStatus",
		                new Object[] { RemoteEnvStatus.未签收,RemoteEnvStatus.已签收 });
		    	searchBuilder.getFilterBuilder().lessThan("limitTime", calendar.getTime());
		    }else if("5".equals(staType)){//超时未办结
		    	searchBuilder.getFilterBuilder().in("envStatus",
		                new Object[] { RemoteEnvStatus.未签收,RemoteEnvStatus.已签收 });
		    	searchBuilder.getFilterBuilder().lessThan("limitTime", new Date());
		    }
		    
		    
		    SearchListHelper<RemoteDocSwapEnvelope> listHelper = new SearchListHelper<RemoteDocSwapEnvelope>();
		    listHelper.execute(searchBuilder, envelopeService.getRepository(), page, rows);
		    
		    p.setRows(BeanCopyBuilder.buildList(listHelper.getList(), DocSwapEnvelopeShowBean.class));
		    p.setTotal(listHelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "公文统计-发送查询-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
    	
        return p;
    }
    
    
    /**
     * 公文统计——接收查询
     */
    @RequestMapping("listDocReceiveByStatistic")
    public ModelAndView docReceiveListByStatistic(String type,String year) {
    	ModelAndView mav = new ModelAndView("/doc/remotedocswap/statistic/docReceiveListByStatistic");
    	mav.addObject("staType", type); 
    	
    	Date[] dateRange  = Tools.getYearRange(Integer.parseInt(year));
    	mav.addObject("sendBeginTime", dateRange[0]); 
    	mav.addObject("sendEndTime", dateRange[1]); 
        return mav;
    }
    
    
    /**
     * 列表数据——接收查询
     */
    @RequestMapping("/listDocReceiveByStatistic.json")
    @ResponseBody
    public Page docReceiveListByStatisticJson(Integer page, Integer rows, HttpServletRequest request) {
    	Page p = new Page();
    	try {
    		SearchBuilder<RemoteDocSwapContent, Long> searchBuilder = new SearchBuilder<RemoteDocSwapContent, Long>(
                    conversionService);
		    searchBuilder.clearSearchBuilder().findSearchParam(request);
		
		    //查询接收记录的默认条件是：收发标志为1,3（receiveFlag）
		    searchBuilder.getFilterBuilder().in("envelope.receiveFlag",new Object[] { RemoteEnvRecFlag.接收,RemoteEnvRecFlag.纸质公文} );
		    
		    String staType = request.getParameter("staType");//统计类型
		    
		    if("1".equals(staType)){//全部
		    	searchBuilder.getFilterBuilder().in("envelope.envStatus",
		    			new Object[] { RemoteEnvStatus.已办结,RemoteEnvStatus.不办理,RemoteEnvStatus.转阅件,
						RemoteEnvStatus.办结传阅,RemoteEnvStatus.办结归档,RemoteEnvStatus.未签收,RemoteEnvStatus.已签收 });
		    }else if("2".equals(staType)){//已办结
		    	searchBuilder.getFilterBuilder().in("envelope.envStatus",
		                new Object[] { RemoteEnvStatus.已办结,RemoteEnvStatus.不办理,RemoteEnvStatus.转阅件,RemoteEnvStatus.办结传阅,RemoteEnvStatus.办结归档 });
		    }else if("3".equals(staType)){//未办结
		    	searchBuilder.getFilterBuilder().in("envelope.envStatus",
		    			new Object[] { RemoteEnvStatus.未签收,RemoteEnvStatus.已签收 });
		    }else if("4".equals(staType)){//已预警
		    	Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 3);
		    	searchBuilder.getFilterBuilder().in("envelope.envStatus",
		                new Object[] { RemoteEnvStatus.未签收,RemoteEnvStatus.已签收 });
		    	searchBuilder.getFilterBuilder().lessThan("envelope.limitTime", calendar.getTime());
		    }else if("5".equals(staType)){//超时未办结
		    	searchBuilder.getFilterBuilder().in("envelope.envStatus",
		                new Object[] { RemoteEnvStatus.未签收,RemoteEnvStatus.已签收 });
		    	searchBuilder.getFilterBuilder().lessThan("envelope.limitTime", new Date());
		    }
		    
		    // 排序
		    List<String> orders = new ArrayList<String>();
		    orders.add("finishTime");
		    orders.add("envelope.id");
		    searchBuilder.appendSort(Direction.DESC, orders);
		
		    SearchListHelper<RemoteDocSwapContent> listHelper = new SearchListHelper<RemoteDocSwapContent>();
		    listHelper.execute(searchBuilder, contentService.getRepository(), page, rows);
		    
		    p.setRows(BeanCopyBuilder.buildList(listHelper.getList(), DocSwapEnvelopeShowBean.class));
		    p.setTotal(listHelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "公文统计-接收查询-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
    	
        
        return p;
    }
    
    
    /**
     * 公文统计——限时公文工作日
     */
    @RequestMapping("workDayConfig")
    public ModelAndView workDayConfig() {
    	ModelAndView mav = new ModelAndView("/doc/remotedocswap/statistic/config/workDayConfig");
    	//自定义的工作日信息
        //放假日期
        List<String> selfFesDateList = docStatisticService.findSelfWorkDateByType(RemoteWorkDateType.节假日);
        JSONArray object = JSONArray.fromObject(selfFesDateList);
        mav.addObject("selfFesDateList",object.toString());
        //加班日期
        List<String> selfWorkDateList = docStatisticService.findSelfWorkDateByType(RemoteWorkDateType.加班日);
        object = JSONArray.fromObject(selfWorkDateList);
        mav.addObject("selfWorkDateList",object.toString());
        
        return mav;
    }
    
    /**
     * 列表数据——工作日数据
     */
    @RequestMapping("/listWorkDay.json")
    @ResponseBody
    public Page workDayListJson(Integer page, Integer rows, HttpServletRequest request) {
    	Page p = new Page();
    	try {
    		SearchBuilder<RemoteWorkDate, Long> searchBuilder = new SearchBuilder<RemoteWorkDate, Long>(
                    conversionService);
		    searchBuilder.clearSearchBuilder().findSearchParam(request);
		    searchBuilder.getFilterBuilder().equal("type", RemoteWorkDateType.工作日);
		
		    // 排序
		    searchBuilder.appendSort(Direction.DESC, "fromDate");
		    searchBuilder.appendSort(Direction.DESC, "workDays");
		    
		
		    SearchListHelper<RemoteWorkDate> listHelper = new SearchListHelper<RemoteWorkDate>();
		    listHelper.execute(searchBuilder, remoteWorkDateRepository, page, rows);
		    
		    p.setRows(BeanCopyBuilder.buildList(listHelper.getList(), RemoteWorkDate.class));
		    p.setTotal(listHelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "公文交换-限时公文工作日-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
        
        return p;
    }
    
    /**
     * 重新获取工作日
     */
    @RequestMapping("/getReloadWorkDay.json")
    @ResponseBody
    public ReturnInfo reloadWorkDayJson(HttpServletRequest request, HttpServletResponse response) {

    	try {
            if(docStatisticService.getWorkDate()) {
//                docStatisticService.initWorkDate();
            	return new ReturnInfo("1", null, "获取数据成功！");
            }else{
        		envelopeService.sendMsg(Tools.DATE_FORMAT.format(new Date())+" 工作日获取错误。",RemoteDocSwapConfig.WORK_DAY_ERROR_RECIEVER);
            	return new ReturnInfo(null, "获取数据失败！", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "获取数据失败！", null);
        }
         
    }
    
    /**
     * 保存自定义的工作日数据
     */
    @RequestMapping("/saveSelfWorkDate.json")
    @ResponseBody
    public ReturnInfo saveSelfWorkDate(HttpServletRequest request, HttpServletResponse response) {

        try {
            
        	String festivalDateJson =  request.getParameter("festivalDateJson");
        	String extraDateJson =  request.getParameter("extraDateJson");
            boolean result = docStatisticService.saveSelfWorkDate(festivalDateJson,extraDateJson);

            return new ReturnInfo("1", null, "保存成功！");
        } catch (Exception e) {
            return new ReturnInfo(null, "保存失败！", null);
        }
    }
    
    /**
     * 公文统计——发送列表-区直
     */
    @RequestMapping("listSendByOuter")
    public ModelAndView sendListByOuter(String type,String year) {
    	ModelAndView mav = new ModelAndView("/doc/remotedocswap/statistic/sendList");
    	mav.addObject("staType", type); 
    	
    	Date[] dateRange  = Tools.getYearRange(Integer.parseInt(year));
    	mav.addObject("sendBeginTime", dateRange[0]); 
    	mav.addObject("sendEndTime", dateRange[1]); 
        return mav;
    }
    
    /**
     * 公文统计——发送列表-区直
     */
    @RequestMapping("listReceiveByOuter")
    public ModelAndView receiveListByOuter(String type,String year) {
    	ModelAndView mav = new ModelAndView("/doc/remotedocswap/statistic/receiveList");
    	mav.addObject("staType", type); 
    	
    	Date[] dateRange  = Tools.getYearRange(Integer.parseInt(year));
    	mav.addObject("sendBeginTime", dateRange[0]); 
    	mav.addObject("sendEndTime", dateRange[1]); 
        return mav;
    }
}
