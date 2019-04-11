/*
 * FileName:    HtmlTools.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年7月22日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.common.velocity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;

import cn.com.chaochuang.common.util.AttachUtils;

/**
 * @author yuandl
 *
 */
@DefaultKey("htmlTools")
@ValidScope(Scope.APPLICATION)
public class HtmlTools {
    /**
     * 获取文件名的后缀
     *
     * @param fileName
     *            文件名
     * @return
     */
    public String getFileSuffix(String fileName) {
        return AttachUtils.getFileSuffix(fileName);
    }

    /**
     * 获取文件的base64编码
     *
     * @param path
     * @return
     */
    public String encodeBase64File(String path) {
        return AttachUtils.encodeBase64File(path);
    }
    
    /**
     * 日期相减 计算相差天数
     *
     * @param endDate
     * @param startDate 为空则使用当前日期
     * @return
     */
    public static Integer calculateDayInterval(Date endDate,Date startDate) {
        if (endDate == null) {
            return 0;
        }
        if(startDate==null){
            startDate = new Date();
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDate=sdf.parse(sdf.format(startDate));
            endDate=sdf.parse(sdf.format(endDate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(endDate);
            long time2 = cal.getTimeInMillis();
            long between_days=(time2-time1)/(1000*3600*24);

            return Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
