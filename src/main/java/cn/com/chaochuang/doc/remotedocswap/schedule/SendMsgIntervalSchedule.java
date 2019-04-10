package cn.com.chaochuang.doc.remotedocswap.schedule;


import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import cn.com.chaochuang.doc.remotedocswap.service.RemoteDocSwapEnvelopeService;
/** 
 * @ClassName: SendMsgIntervalSchedule 
 * @Description: 公文短信发送定时任务，发送超时、待办、反馈、办理的公文
 * @author: yuchunshu
 * @date: 2017年9月19日 上午11:44:44  
 */
@Component
@PropertySource("WEB-INF/global.spring.properties")
public class SendMsgIntervalSchedule{
    
    @Autowired
    private RemoteDocSwapEnvelopeService envelopeService;
    
//	@Scheduled(cron = "${sendMsgIntervalSchedule.cron}")//每天9点 16点 发送一次
    void process() {
		
        Calendar calendar = Calendar.getInstance();
        if(calendar.get(Calendar.HOUR_OF_DAY) >= 8 && calendar.get(Calendar.HOUR_OF_DAY) <= 17) {
        	//办理短信提醒模板
        	//查询 接收公文&&状态为签收&&限办日期-当前日期=1  的交换公文
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
            envelopeService.noticeByCalendar(calendar, false);
            
            //查询 接收公文&&状态为签收&&限办日期=当前日期 的交换公文
            calendar = Calendar.getInstance();
            envelopeService.noticeByCalendar(calendar, false);
            
            //超时短信提醒模板
            // 查询 接收公文&&状态为签收&&限办日期=当前日期-1 限办日期
            calendar = Calendar.getInstance();
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
            envelopeService.noticeByCalendar(calendar, true);
        }
    
	}
	
}
