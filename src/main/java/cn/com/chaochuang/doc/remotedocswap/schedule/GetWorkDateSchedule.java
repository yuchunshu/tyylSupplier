package cn.com.chaochuang.doc.remotedocswap.schedule;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapConfig;
import cn.com.chaochuang.doc.remotedocswap.service.DocStatisticService;
import cn.com.chaochuang.doc.remotedocswap.service.RemoteDocSwapEnvelopeService;
/** 
 * @ClassName: GetWorkDateSchedule 
 * @Description: 工作日定时任务，每天9点检查是否含有当前的工作日信息，每天13点通过内网程序获取一周内工作日数据
 * @author: chunshu
 * @date: 2017年8月4日 上午11:44:44  
 */
@Component
@PropertySource("WEB-INF/global.spring.properties")
public class GetWorkDateSchedule{

    
    @Autowired
    private DocStatisticService 		 docStatisticService;
    
    @Autowired
    private RemoteDocSwapEnvelopeService envelopeService;
    
	@Scheduled(cron = "${getWorkDateSchedule.checkWorkDate.cron}")//每天9点触发 
    void checkWorkDate() {
        try {
        	if (!docStatisticService.checkWorkDate()){
        		envelopeService.sendMsg(Tools.DATE_FORMAT.format(new Date())+" 工作日获取错误。",RemoteDocSwapConfig.WORK_DAY_ERROR_RECIEVER);
          }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
	}
	
//	@Scheduled(cron = "0 0/1 *  * * ? ")//每隔1分钟执行一次
//	@Scheduled(cron = "${getWorkDateSchedule.getWorkDate.cron}")//每天13点触发 
    void getWorkDate() {
        try {
        	docStatisticService.getWorkDate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
	}
}
