/*
 * FileName:    DocStatisticService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年01月16日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.remotedocswap.JsonDateValueProcessor;
import cn.com.chaochuang.doc.remotedocswap.bean.DocStatisticData;
import cn.com.chaochuang.doc.remotedocswap.bean.DocStatisticQueryData;
import cn.com.chaochuang.doc.remotedocswap.bean.DocStatisticResult;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapContent;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapLog;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteWorkDate;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapLogStatus;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapLogType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteWorkDateType;
import cn.com.chaochuang.doc.remotedocswap.repository.RemoteDocSwapContentRepository;
import cn.com.chaochuang.doc.remotedocswap.repository.RemoteDocSwapLogRepository;
import cn.com.chaochuang.doc.remotedocswap.repository.RemoteWorkDateRepository;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


/** 
 * @ClassName: DocStatisticServiceImpl 
 * @Description: 公文台账接口实现
 * @author: chunshu
 * @date: 2017年7月17日 下午9:23:50  
 */
@Service
@Transactional
public class DocStatisticServiceImpl implements DocStatisticService{
  
	private String 						   fileEncoding = "gbk";
	
	@Value(value = "${remotedocswap.query.folder.path}")
    private String 						   queryFolderPath;
	
	@Value(value = "${remotedocswap.data.folder.path}")
	private String 						   dataFolderPath;

	@Autowired
    private RemoteDocSwapContentRepository contentRepository;
	
	@Autowired
    private RemoteUnitIdentifierService    unitService;
	
	@Autowired
    private RemoteDocSwapLogRepository 	   remoteDocSwapLogRepository;
	
	@Autowired
	private RemoteWorkDateRepository 	   remoteWorkDateRepository;
	
	@Override
    public void createQueryFile(DocStatisticQueryData queryData) throws IOException {
        //用于标记每次查询，获取数据时读取 uuid+".json" 的文件。
        queryData.setQueryUuid(queryData.getQueryType()+"-"+new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "-" + UUID.randomUUID().toString());
        //开始查询
        queryData.setStartQuery(true);
        File file = new File(queryFolderPath);
        if(!file.exists()){
            file.mkdirs();
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(true));
        JSONObject jsonObject = JSONObject.fromObject(queryData,jsonConfig);
        File queryFile = new File(queryFolderPath+File.separator+queryData.getQueryUuid()+".cc");
        if(queryFile.createNewFile()){
            FileUtils.write(queryFile,jsonObject.toString());
        }
    }

	@Override
	public DocStatisticResult readQueryFile(DocStatisticQueryData queryData) throws IOException {
        //获取数据时读取 uuid+".json" 的文件。
        File dataFile = new File(dataFolderPath+File.separator+queryData.getQueryUuid()+".json");
        DocStatisticResult result = null;
        boolean foreach = true;
        long t1 = System.currentTimeMillis();
        //循环读取文件，读取到文件或者30秒后返回页面
        while(foreach){
        	long t2 = System.currentTimeMillis();
        	if(dataFile.exists()){
        		foreach = false;
        	}
        	if(t2-t1 > 30*1000){
                break;
            }
        }
        
        if(dataFile.exists()){
        	Map unitMap = unitService.findAllBySubUnitIdentifier();
            String data = FileUtils.readFileToString(dataFile,fileEncoding);
            data = data.replace("\r\n","");
            JSONObject jsonObj = JSONObject.fromObject(data);
            // 定义一个Map
            Map<String, Class<DocStatisticData>> map = new HashMap<String, Class<DocStatisticData>>();
            map.put("result", DocStatisticData.class); // key为私有变量的属性名
            result = (DocStatisticResult) JSONObject.toBean(jsonObj, DocStatisticResult.class,map);
            if(result!=null&&result.getResult()!=null){
                for(DocStatisticData statisticData:result.getResult()){
                    RemoteDocSwapContent content = contentRepository.findEnvelopeByYwlsh(statisticData.getYwlsh());
                    statisticData.setLinkDoc(content);
                    //根据单位标识获取单位名称
                    statisticData.setReceiveUnitCode((String) unitMap.get(statisticData.getReceiveUnitCode()));
                    if(DocStatisticQueryData.QUERY_TYPE_ERROR_DOC.equals(queryData.getQueryType())){
                    	statisticData.setSendUnitCode((String) unitMap.get(statisticData.getSendUnitCode()));
                    }
                }
                //获取总数
                queryData.setTotalCount(result.getTotalCount());
            }
        }
        //结束查询
        queryData.setStartQuery(false);
        
        return result;
    
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map readCountFile(DocStatisticQueryData queryData) throws IOException {
		//获取数据时读取 uuid+".json" 的文件。
		File dataFile = new File(dataFolderPath+File.separator+queryData.getQueryUuid()+".json");
		DocStatisticResult result = null;
		boolean foreach = true;
		long t1 = System.currentTimeMillis();
		//循环读取文件，读取到文件或者30秒后返回页面
		while(foreach){
			long t2 = System.currentTimeMillis();
			if(dataFile.exists()){
				foreach = false;
			}
			if(t2-t1 > 30*1000){
				break;
			}
		}
		// 定义一个Map
		Map<String, Integer> sendMap = new HashMap<String, Integer>();
		Map<String, Integer> receiveMap = new HashMap<String, Integer>();
		Map<String, Map<String,Integer>> resultMap = new HashMap<String, Map<String,Integer>>();
		
		if(dataFile.exists()){
			String data = FileUtils.readFileToString(dataFile,fileEncoding);
			data = data.replace("\r\n","");
			JSONObject jsonObj = JSONObject.fromObject(data);
			JSONObject sendDataJson = (JSONObject) jsonObj.get("sendData");
			JSONObject receiveDataJson = (JSONObject) jsonObj.get("receiveData");
			JSONObject sendDataResult = (JSONObject) sendDataJson.get("resultMap");
			JSONObject receiveDataResult = (JSONObject) receiveDataJson.get("resultMap");
			
			sendMap.put("all", Integer.parseInt(sendDataResult.getString("1-")) + Integer.parseInt(sendDataResult.getString("2-")));
			sendMap.put("0", Integer.parseInt(sendDataResult.getString("1-0")) + Integer.parseInt(sendDataResult.getString("2-0")));
			sendMap.put("1", Integer.parseInt(sendDataResult.getString("1-1")) + Integer.parseInt(sendDataResult.getString("2-1")));
			sendMap.put("2", Integer.parseInt(sendDataResult.getString("1-2")) + Integer.parseInt(sendDataResult.getString("2-2")));
			sendMap.put("3", Integer.parseInt(sendDataResult.getString("1-3")) + Integer.parseInt(sendDataResult.getString("2-3")));
			sendMap.put("4", Integer.parseInt(sendDataResult.getString("1-4")) + Integer.parseInt(sendDataResult.getString("2-4")));
			sendMap.put("5", Integer.parseInt(sendDataResult.getString("1-5")) + Integer.parseInt(sendDataResult.getString("2-5")));
			sendMap.put("6", Integer.parseInt(sendDataResult.getString("1-6")) + Integer.parseInt(sendDataResult.getString("2-6")));
			
			receiveMap.put("all", Integer.parseInt(receiveDataResult.getString("1-")) + Integer.parseInt(receiveDataResult.getString("2-")));
			receiveMap.put("0", Integer.parseInt(receiveDataResult.getString("1-0")) + Integer.parseInt(receiveDataResult.getString("2-0")));
			receiveMap.put("1", Integer.parseInt(receiveDataResult.getString("1-1")) + Integer.parseInt(receiveDataResult.getString("2-1")));
			receiveMap.put("2", Integer.parseInt(receiveDataResult.getString("1-2")) + Integer.parseInt(receiveDataResult.getString("2-2")));
			receiveMap.put("3", Integer.parseInt(receiveDataResult.getString("1-3")) + Integer.parseInt(receiveDataResult.getString("2-3")));
			receiveMap.put("4", Integer.parseInt(receiveDataResult.getString("1-4")) + Integer.parseInt(receiveDataResult.getString("2-4")));
			receiveMap.put("5", Integer.parseInt(receiveDataResult.getString("1-5")) + Integer.parseInt(receiveDataResult.getString("2-5")));
			receiveMap.put("6", Integer.parseInt(receiveDataResult.getString("1-6")) + Integer.parseInt(receiveDataResult.getString("2-6")));
			
			resultMap.put("sendMap", sendMap);
			resultMap.put("receiveMap", receiveMap);
		}
		
		return resultMap;
		
	}

	@Override
    public boolean checkWorkDate() {
        RemoteDocSwapLog log = new RemoteDocSwapLog();
        log.setLogType(RemoteDocSwapLogType.工作日);
        log.setLogTime(new Date());
        //当前日期字符串
        String curDateFmt = Tools.DATE_FORMAT.format(new Date());
        //查询是否有当前日期的工作日数据
        List<RemoteWorkDate> workDateList = remoteWorkDateRepository.findByFromDateAndType(curDateFmt, RemoteWorkDateType.工作日);
        if(workDateList==null||workDateList.size()==0){
            log.setStatus(RemoteDocSwapLogStatus.失败);
            log.setLog("没有获取到"+curDateFmt+"工作日数据。");
            this.remoteDocSwapLogRepository.save(log);
            return false;
        }
        log.setStatus(RemoteDocSwapLogStatus.成功);
        log.setLog("成功获取到"+curDateFmt+"工作日数据。");
        this.remoteDocSwapLogRepository.save(log);
        return true;
    }
	
	@Override
    public boolean getWorkDate() throws Exception{
        boolean result = true;
        RemoteDocSwapLog log = new RemoteDocSwapLog();
        log.setLogType(RemoteDocSwapLogType.工作日);
        log.setLogTime(new Date());

        DocStatisticQueryData queryData = new DocStatisticQueryData();
        queryData.setQueryType(DocStatisticQueryData.QUERY_TYPE_WORK_DATE);
        queryData.setWorkCurDate(new Date());
        this.createQueryFile(queryData);
        
        //获取数据时读取 uuid+".json" 的文件。
        File dataFile = new File(dataFolderPath+File.separator+queryData.getQueryUuid()+".json");
        boolean foreach = true;
        long t1 = System.currentTimeMillis();
        //循环读取文件，读取到文件或者30秒后返回页面
        while(foreach){
        	long t2 = System.currentTimeMillis();
        	if(dataFile.exists()){
        		foreach = false;
        	}
        	if(t2-t1 > 30*1000){
                break;
            }
        }
        
        if(dataFile.exists()){
            String data = FileUtils.readFileToString(dataFile,fileEncoding);
            data = data.replace("\r\n","");
            JSONArray jsonArr = JSONArray.fromObject(data);
            if(jsonArr.size()>0) {
                //删除之前的日期数据
                remoteWorkDateRepository.deleteByType(RemoteWorkDateType.工作日);
                for (Iterator iter = jsonArr.iterator(); iter.hasNext(); ) {
                    JSONObject jsonObject = (JSONObject) iter.next();
                    RemoteWorkDate dateResult = (RemoteWorkDate) JSONObject.toBean(jsonObject, RemoteWorkDate.class);
                    dateResult.setType(RemoteWorkDateType.工作日);
                    remoteWorkDateRepository.save(dateResult);
                }
//                this.initWorkDate();
                log.setStatus(RemoteDocSwapLogStatus.成功);
                log.setLog("获取数据成功");
                result = true;
            }else{
                log.setStatus(RemoteDocSwapLogStatus.失败);
                log.setLog("数据文件"+dataFile.getName()+"的数据为空。");
                result = false;
            }
        }else{
            log.setStatus(RemoteDocSwapLogStatus.失败);
            log.setLog("数据文件"+dataFile.getName()+"不存在。请检查光闸或内网程序");
            result = false;
        }
        this.remoteDocSwapLogRepository.save(log);
        return result;
    }

	@Override
	public boolean saveSelfWorkDate(String festivalDateJson, String extraDateJson) {
        if(!Tools.isEmptyString(festivalDateJson)){
            //节假日
            JSONObject jsonObject = JSONObject.fromObject(festivalDateJson);
            Set<String> yearSet = jsonObject.keySet();
            for(String year : yearSet){
                //删除整年的数据
                remoteWorkDateRepository.deleteByTypeAndFromDate(RemoteWorkDateType.节假日,year);
                List<String> dateList = (List<String>) jsonObject.get(year);
                List<RemoteWorkDate> workDateList = new ArrayList<RemoteWorkDate>();
                for(String toDate:dateList){
                	RemoteWorkDate rwd = new RemoteWorkDate();
                	rwd.setToDate(toDate);
                	rwd.setFromDate(year);
                	rwd.setType(RemoteWorkDateType.节假日);
                	workDateList.add(rwd);
                }
                remoteWorkDateRepository.save(workDateList);
                
            }
        }
        if(!Tools.isEmptyString(extraDateJson)){
            //加班日
            JSONObject jsonObject = JSONObject.fromObject(extraDateJson);
            Set<String> yearSet = jsonObject.keySet();
            for(String year : yearSet){
            	//删除整年的数据
                remoteWorkDateRepository.deleteByTypeAndFromDate(RemoteWorkDateType.加班日,year);
                List<String> dateList = (List<String>) jsonObject.get(year);
                List<RemoteWorkDate> workDateList = new ArrayList<RemoteWorkDate>();
                for(String toDate:dateList){
                	RemoteWorkDate rwd = new RemoteWorkDate();
                	rwd.setToDate(toDate);
                	rwd.setFromDate(year);
                	rwd.setType(RemoteWorkDateType.加班日);
                	workDateList.add(rwd);
                }
                remoteWorkDateRepository.save(workDateList);
            }
        }
//        this.initSelfWorkDate();
        return true;
    
	}

	@Override
	public List<String> findSelfWorkDateByType(RemoteWorkDateType type) {
		List<String> dateArr=new ArrayList<String>();
        List<RemoteWorkDate> workDateList = remoteWorkDateRepository.findByType(type);
        if(workDateList!=null&&workDateList.size()>0){
            for(RemoteWorkDate workDate : workDateList){
                dateArr.add(workDate.getToDate());
            }
        }
        return dateArr;
	}

	@Override
	public String getWorkingDate(Integer limitDay, Date date) {
		String result = "";
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		Date now = (date == null) ? new Date() : date;
		//获取区政府的工作日数据
		RemoteWorkDate remoteWorkDate = remoteWorkDateRepository.findByFromDateAndTypeAndWorkDays(Tools.DATE_FORMAT.format(now), RemoteWorkDateType.工作日, limitDay);
		
		if(null!=remoteWorkDate){
			result = remoteWorkDate.getToDate();
		}else{
			Date resultDate = getWorkDay(limitDay);
			result = sd.format(resultDate);
		}
		 
		return result;
	}
    
	@Override
    public Date getWorkDay(int workDay) {
        Calendar c1 = Calendar.getInstance();
        //
        c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
        for (int i = 0; i < workDay; i++) {
            //
            int dayOfWeek = c1.get(Calendar.DAY_OF_WEEK);
            //是否是周末
            if (Calendar.SATURDAY == dayOfWeek || Calendar.SUNDAY == dayOfWeek) {
                //是否需要加班
                if(!isExtraWorkDay(c1)) {
                    workDay = workDay + 1;
                }
            }else{
                //是否是节假日
                if(isFestivalDay(c1)) {
                    workDay = workDay + 1;
                }
            }
            c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
        }
        if(workDay>0){
            c1.set(Calendar.DATE, c1.get(Calendar.DATE) - 1);
        }
        //设置时分秒
        c1.set(Calendar.HOUR_OF_DAY,23);
        c1.set(Calendar.MINUTE,59);
        c1.set(Calendar.SECOND,59);
        return c1.getTime();
    }
    
	@Override
	public boolean isFestivalDay(Calendar calendar){
       String dateStr = Tools.DATE_FORMAT.format(calendar.getTime());
       RemoteWorkDate remoteWorkDate = remoteWorkDateRepository.findByToDateAndType(dateStr, RemoteWorkDateType.节假日);
       if(null!=remoteWorkDate){
    	   return true;
		}
       return false;
   }
  
	@Override
	public boolean isExtraWorkDay(Calendar calendar){
	   String dateStr = Tools.DATE_FORMAT.format(calendar.getTime());
       RemoteWorkDate remoteWorkDate = remoteWorkDateRepository.findByToDateAndType(dateStr, RemoteWorkDateType.加班日);
       if(null!=remoteWorkDate){
    	   return true;
		}
       return false;
   }
}

