/*
 * FileName:    HtmlTools.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月22日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.common.velocity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
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
    
    /**
     * 判断操作系统版本信息
     *
     * @param userAgent
     *            后台通过request.getHeader("user-agent")获取， velocity通过$request.getHeader("User-Agent")获取
     * @return
     */
    public static String getOSVersion(String userAgent) {

        if (StringUtils.isNotBlank(userAgent)) {
            if (userAgent.contains("Windows")) {// 主流应用靠前
                /**
                 * ****************** Windows ******************
                 * 判断依据:http://msdn.microsoft.com/en-us/library/ms537503(v=vs.85).aspx
                 */
                if (userAgent.contains("Windows NT 10.0") || userAgent.contains("Windows NT 6.4")) {
                    return "Win10";
                } else if (userAgent.contains("Windows NT 6.3")) {
                    return "Win8.1";
                } else if (userAgent.contains("Windows NT 6.2")) {
                    return "Win8";
                } else if (userAgent.contains("Windows NT 6.1")) {
                    return "Win7";
                } else if (userAgent.contains("Windows NT 6.0")) {
                    return "WinVista";
                } else if (userAgent.contains("Windows NT 5.2")) {
                    return "WinXP x64";
                } else if (userAgent.contains("Windows NT 5.1")) {
                    return "WinXP";
                } else if (userAgent.contains("Windows NT 5.01")) {
                    return "Win2000 SP1";
                } else if (userAgent.contains("Windows NT 5.0")) {
                    return "Win2000";
                } else if (userAgent.contains("Windows NT 4.0")) {
                    return "WinNT 4.0";
                } else if (userAgent.contains("Windows 98; Win 9x 4.90")) {
                    return "WinME";
                } else if (userAgent.contains("Windows 98")) {
                    return "Win98";
                } else if (userAgent.contains("Windows 95")) {
                    return "Win95";
                } else if (userAgent.contains("Windows CE")) {
                    return "WinCE";
                }
            } else if (userAgent.contains("Mac OS X")) {
                Pattern pattern = Pattern.compile("OS [\\d._]*");
                Matcher matcher = pattern.matcher(userAgent);
                if (matcher.find()) {
                    return matcher.group().trim();
                }
            } else if (userAgent.contains("Android")) {
                return userAgent.substring(userAgent.indexOf("Android"),
                                userAgent.indexOf(";", userAgent.indexOf("Android")));
            }
        }
        return "OtherOS";
    }

    /**
     * 获取浏览器版本信息
     *
     * @param userAgent
     *            后台通过request.getHeader("user-agent")获取， velocity通过$request.getHeader("User-Agent")获取
     * @return
     */
    public static String getBrowserVersion(String userAgent) {
        // 判断顺序不要随意改动特别是edge chrome safari
        String cp = "";
        if (userAgent.contains("compatible")) {
            cp = "compatible";
        }
        if (userAgent.contains("MSIE 6")) {
            return cp + " IE6";
        } else if (userAgent.contains("MSIE 7")) {
            return cp + " IE7";
        } else if (userAgent.contains("MSIE 8")) {
            return cp + " IE8";
        } else if (userAgent.contains("MSIE 9")) {
            return cp + " IE9";
        } else if (userAgent.contains("MSIE 10")) {
            return cp + " IE10";
        } else if (userAgent.contains("Trident/7") && userAgent.contains("rv:11")) {
            return cp + " IE11";
        } else if (userAgent.contains("Edge")) {
            return userAgent.substring(userAgent.indexOf("Edge"));
        }
        // TODO 其他浏览器的判断应该写在这里， 因为他们大都包含了safari等字样

        // end
        else if (userAgent.contains("Firefox")) {
            return userAgent.substring(userAgent.indexOf("Firefox"));
        } else if (userAgent.contains("Chrome")) {
            return userAgent.substring(userAgent.indexOf("Chrome"), userAgent.indexOf(" ", userAgent.indexOf("Chrome")));
        } else if (userAgent.contains("Safari")) {
            return userAgent.substring(userAgent.indexOf("Safari"));
        }
        return userAgent.substring(userAgent.indexOf("("));
    }
}
