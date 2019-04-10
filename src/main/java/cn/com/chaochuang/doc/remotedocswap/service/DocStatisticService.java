package cn.com.chaochuang.doc.remotedocswap.service;


import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.chaochuang.doc.remotedocswap.bean.DocStatisticQueryData;
import cn.com.chaochuang.doc.remotedocswap.bean.DocStatisticResult;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteWorkDateType;

 
/** 
 * @ClassName: DocStatisticService 
 * @Description: 公文台账接口
 * @author: chunshu
 * @date: 2017年7月18日 下午5:39:07  
 */
public interface DocStatisticService{

    /**
     * 生成查询文件
     * @param docStatisticQueryData
     * @throws IOException
     */
    void createQueryFile(DocStatisticQueryData docStatisticQueryData) throws IOException;

     
    /**
     * 从文件中读取数据
     * @param queryData
     * @return
     * @throws IOException
     */
    DocStatisticResult readQueryFile(DocStatisticQueryData queryData) throws IOException;
    
    /**
     * 从文件中读取公文数量数据
     * @param queryData
     * @return
     * @throws IOException
     */
    Map readCountFile(DocStatisticQueryData queryData) throws IOException;
    
    
    /**
     * 检查是否含有当前的工作日信息
     * @return
     */
    boolean checkWorkDate();
    
    /**
     * 通过内网程序获取工作日数据
     */
    boolean getWorkDate() throws Exception;
    
    /**
     * 保存自定义的工作日数据
     * @param festivalDateJson 节假日 以年份为key，日期集合为value。如{"2017",["2017-01-01"]}
     * @param extraDateJson 以年份为key，日期集合为value。如{"2017",["2017-01-01"]}
     * @return
     */
    boolean saveSelfWorkDate(String festivalDateJson, String extraDateJson);
    
    /** 
     * @Title: findSelfWorkDateByType 
     * @Description: 获取自定义工作日数据
     * @param type
     * @return
     * @return: List<String>
     */
    List<String> findSelfWorkDateByType(RemoteWorkDateType type);
    
    
    /** 
     * @Title: getWorkDay 
     * @Description: 根据限办工日获取自定义的限时时间
     * @param workDay
     * @return
     * @return: Date
     */
    Date getWorkDay(int workDay);
    
    /** 
     * @Title: getWorkingDate 
     * @Description: 根据限办工日和当前日期获取 区政府工作日限时时间，如果获取不到区政府工作日限时时间，则获取自定义保存的工作日限时时间
     * @param limitDay
     * @param date
     * @return
     * @return: String
     */
    String getWorkingDate(Integer limitDay,Date date);
    
    /** 
	 * @Title: isFestivalDay 
	 * @Description: 判断是否节假日
	 * @param calendar
	 * @return
	 * @return: boolean
	 */
	boolean isFestivalDay(Calendar calendar);
	
	/** 
	 * @Title: isFestivalDay 
	 * @Description: 判断是否需要加班
	 * @param calendar
	 * @return
	 * @return: boolean
	 */
    boolean isExtraWorkDay(Calendar calendar);
}
