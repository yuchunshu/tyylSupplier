/*
 * FileName:    Tools.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年3月28日 (LLM) 1.0 Create
 */

package cn.com.chaochuang.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import cn.com.chaochuang.common.dictionary.DictionaryPool;
import cn.com.chaochuang.doc.event.reference.DateFlag;
import cn.com.chaochuang.oa.workingday.domain.WorkingDay;

/**
 * @author LLM
 *
 */
public abstract class Tools {
    /** 按 yyyy-MM-dd HH:mm:ss 格式化时间. */
    public static final SimpleDateFormat DATE_TIME_FORMAT    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /** 按 yyyyMMddHHmmss 格式化时间. */
    public static final SimpleDateFormat DATE_TIME_FORMAT2   = new SimpleDateFormat("yyyyMMddHHmmss");
    /** 按 yyyy-MM-dd HH:mm 格式化时间. */
    public static final SimpleDateFormat DATE_MINUTE_FORMAT  = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    /** 按 yyyy-MM-dd 格式化日期. */
    public static final SimpleDateFormat DATE_FORMAT         = new SimpleDateFormat("yyyy-MM-dd");
    /** 按 yyyy/MM/dd 格式化日期. */
    public static final SimpleDateFormat DATE_FORMAT2        = new SimpleDateFormat("yyyy/MM/dd");
    /** 按 yyyy年MM月dd日 格式化日期. */
    public static final SimpleDateFormat DATE_FORMAT3        = new SimpleDateFormat("yyyy年MM月dd日");
    /** 按 yyyyMMdd 格式化日期. */
    public static final SimpleDateFormat DATE_FORMAT4        = new SimpleDateFormat("yyyyMMdd");
    /** 按 HH:mm 格式化日期. */
    public static final SimpleDateFormat DATE_FORMAT5        = new SimpleDateFormat("HH:mm");
    /** 按 M-d H:m 格式化日期. */
    public static final SimpleDateFormat DATE_FORMAT6        = new SimpleDateFormat("M月d日 H:mm");
    /** 按 yyyy 格式化日期. */
    public static final SimpleDateFormat DATE_FORMAT7        = new SimpleDateFormat("yyyy");
    /** 按 yyyy-MM-dd'T'HH:mm:ss.SSSZ 格式化日期. */
    public static final SimpleDateFormat DATE_FORMAT8        = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    /** 按 yyyy年MM月dd日 HH:mm 格式化日期. */
    public static final SimpleDateFormat DATE_FORMAT9        = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    /** 数值格式化 */
    public static final DecimalFormat    DECIMAL_FORMAT      = new DecimalFormat("#0.######");
    /** 两位小数标准格式化 */
    public static final DecimalFormat    STANDARD_DEC_FORMAT = new DecimalFormat("#0.00");

    /** 当前浏览器是否是IE的引用关键字 */
    public static final String           IEBROWSER           = "iebrowser";

    /**
     * 将字符串数组转换成List
     *
     * @param strAry
     *            字符串数组
     * @return List strAry中的元素
     */
    public static List getListFromArray(Object[] strAry) {
        List tmpList = new ArrayList();
        for (int i = 0; i < strAry.length; i++) {
            tmpList.add(strAry[i]);
        }
        return tmpList;
    }

    /**
     * 将列表中对象的部分属性数据转换成数组
     *
     * @param src
     *            源列表
     * @param property
     *            对象属性
     * @return 数组
     */
    public static Object[] changeListToArray(List src, String property) {
        if (property == null) {
            return src.toArray();
        }
        BeanWrapper infoWrapper;
        Object[] result = new Object[src.size()];
        for (int i = 0; i < src.size(); ++i) {
            infoWrapper = new BeanWrapperImpl(src.get(i));
            result[i] = infoWrapper.getPropertyValue(property);
        }
        return result;
    }


    public static Map<String, Object> changeListToMap(String[] propertyName, Object[] list) {
        Map<String, Object> map = new HashedMap();
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < propertyName.length; j++) {
                map.put(propertyName[j], list[j]);
            }
        }
        return map;
    }


    /**
     * 将字符数组拼凑成字符串
     *
     * @param src
     *            源数组
     * @param splitChr
     *            元素之间的分隔符
     * @param quote
     *            是否用单引号括起元素
     * @return 拼凑成的字符串
     */
    public static String changeArrayToString(Object[] src, String splitChr, boolean quote) {
        List tmpList = getListFromArray(src);
        return changeArrayToString(tmpList, splitChr, quote);
    }

    /**
     * 将字符数组拼凑成字符串,要去空格
     *
     * @param src
     *            源数组
     * @param splitChr
     *            元素之间的分隔符
     * @param quote
     *            是否用单引号括起元素
     * @return 拼凑成的字符串
     */
    public static String changeArrayToStringTrim(Object[] src, String splitChr, boolean quote) {
        List tmpList = getListFromArray(src);
        return changeArrayToStringTrim(tmpList, splitChr, quote);
    }

    /**
     * 将列表拼凑成字符串,要去空格
     *
     * @param src
     *            源数组
     * @param splitChr
     *            元素之间的分隔符
     * @param quote
     *            是否用单引号括起元素
     * @return 拼凑成的字符串
     */
    public static String changeArrayToStringTrim(List src, String splitChr, boolean quote) {
        if (!Tools.isNotEmptyList(src)) {
            return "";
        }
        StringBuffer buffer = new StringBuffer("");
        Object obj;
        for (int i = 0; i < src.size(); ++i) {
            obj = (src.get(i) == null) ? src.get(i) : src.get(i).toString().trim();
            buffer.append(quote ? "'" : "").append(obj).append(quote ? "'" : "")
                            .append((splitChr != null && i < src.size() - 1) ? splitChr : "");
        }
        return buffer.toString();
    }

    /**
     * 将字符数组拼凑成字符串
     *
     * @param src
     *            源数组
     * @param splitChr
     *            元素之间的分隔符
     * @param quote
     *            是否用单引号括起元素
     * @return 拼凑成的字符串
     */
    public static String changeArrayToString(List src, String splitChr, boolean quote) {
        if (!Tools.isNotEmptyList(src)) {
            return "";
        }
        StringBuffer buffer = new StringBuffer("");
        for (int i = 0; i < src.size(); ++i) {
            buffer.append(quote ? "'" : "").append(src.get(i)).append(quote ? "'" : "")
                            .append((splitChr != null && i < src.size() - 1) ? splitChr : "");
        }
        return buffer.toString();
    }

    /**
     * 将对象列表指定的属性拼凑成字符串
     *
     * @param src
     *            对象数组
     * @param property
     *            要组装的对象属性
     * @param splitChr
     *            元素之间的分隔符
     * @param quote
     *            是否用单引号括起元素
     * @return 拼凑成的字符串
     */
    public static String changeArrayToString(List src, String property, String splitChr, boolean quote) {
        StringBuffer buffer = new StringBuffer("");
        BeanWrapper infoWrapper;
        for (int i = 0; i < src.size(); ++i) {
            infoWrapper = new BeanWrapperImpl(src.get(i));
            buffer.append(quote ? "'" : "").append(infoWrapper.getPropertyValue(property)).append(quote ? "'" : "")
                            .append((splitChr != null && i < src.size() - 1) ? splitChr : "");
        }
        return buffer.toString();
    }

    /**
     * 将Map指定的属性拼凑成字符串
     *
     * @param src
     *            Map
     * @param splitChr
     *            元素之间的分隔符
     * @param key
     *            是否取Map的key来组装
     * @param quote
     *            是否用单引号括起元素
     * @return 拼凑成的字符串
     */
    public static String changeArrayToString(Map src, String splitChr, boolean key, boolean quote) {
        StringBuffer buffer = new StringBuffer("");
        for (Iterator iter = src.entrySet().iterator(); iter.hasNext();) {
            Map.Entry element = (Map.Entry) iter.next();
            if (key) {
                buffer.append(quote ? "'" : "").append(element.getKey()).append(quote ? "'" : "")
                                .append((splitChr != null && iter.hasNext()) ? splitChr : "");
            } else {
                buffer.append(quote ? "'" : "").append(element.getValue()).append(quote ? "'" : "")
                                .append((splitChr != null && iter.hasNext()) ? splitChr : "");
            }
        }
        return buffer.toString();
    }

    /**
     * 判断字符串是否为空（null和零长度的字符串都是空）
     *
     * @param str
     *            源字符串
     * @return true：空字符串 false：非空
     */
    public static boolean isEmptyString(String str) {
        boolean result = true;

        if (str == null || str.trim().length() == 0) {
            return result;
        }
        return false;
    }

    /**
     * 判断字符串是否为空（null和零长度的字符串都是空）并且要等于equ指定的字符串
     *
     * @param str
     *            源字符串
     * @param equ
     *            要相等的字符串
     * @return true：等于equ字符串 false：空或不等于equ
     */
    public static boolean isNotEmptyString(String str, String equ) {
        if (isEmptyString(str) || isEmptyString(equ)) {
            return false;
        }
        return str.equals(equ);
    }

    public static boolean isNotEmptyArray(Object[] obj) {
        return obj != null && obj.length > 0;
    }

    /**
     * 判断list是否为非空列表（null和零长度的字符串都是空）
     *
     * @param list
     *            源列表
     * @return true：非空列表 false：空列表
     */
    public static boolean isNotEmptyList(List list) {
        boolean result = true;

        if (list != null && !list.isEmpty()) {
            return result;
        }
        return false;
    }

    /**
     * 判断map是否为非空Map（null和零长度的字符串都是空）
     *
     * @param map
     *            源map
     * @return true：非空map false：空map
     */
    public static boolean isNotEmptyMap(Map map) {
        boolean result = true;

        if (map != null && !map.isEmpty()) {
            return result;
        }
        return false;
    }

    /**
     * 判断set是否为非空Set（null和零长度的字符串都是空）
     *
     * @param set
     *            源set
     * @return true：非空set false：空set
     */
    public static boolean isNotEmptySet(Set set) {
        boolean result = true;

        if (set != null && !set.isEmpty()) {
            return result;
        }
        return false;
    }

    /**
     * 返回截断的字符串
     *
     * @param source
     *            源字符串
     * @param start
     *            截断起始位置
     * @param end
     *            截断终止位置
     * @return 截断的字符串
     */
    public static String substring(String source, int start, int end) {
        if (Tools.isEmptyString(source)) {
            return "";
        }
        if (start >= source.length()) {
            return "";
        }
        if (end >= source.length()) {
            return source.substring(start, source.length());
        }
        return source.substring(start, end);
    }

    /**
     * 获取类名（去掉包前缀）
     *
     * @param className
     *            原类名
     * @return 类名
     */
    public static String getClassName(String className) {
        String[] paths = className.split("\\.");
        String tmps = className;
        if (paths.length > 1) {
            tmps = paths[paths.length - 1];
        }
        return tmps;
    }

    /**
     * 字符转换 全角转成半角
     *
     * @param source
     *            全角字符串
     * @return 半角字符串
     */
    public static String transSBCToDBC(String source) {
        if (Tools.isEmptyString(source)) {
            return "";
        }
        String result = "";
        String temp = "";
        byte[] b = null;
        for (int i = 0; i < source.length(); i++) {
            try {
                temp = source.substring(i, i + 1);
                b = temp.getBytes("unicode");
            } catch (java.io.UnsupportedEncodingException e) {
                return source;
            }
            if (b[2] == -1) {
                b[3] = (byte) (b[3] + 32);
                b[2] = 0;
                try {
                    result += new String(b, "unicode");
                } catch (java.io.UnsupportedEncodingException e) {
                    return source;
                }
            } else {
                result += temp;
            }
        }
        return result;
    }

    /**
     * 判断天数是否为负值，如果为负数就返回0
     *
     * @param date
     *            被减日期
     * @param date2
     *            要减日期
     * @return 天数
     */
    public static Integer subtractDateNegative(Date date1, Date date2) {
        Integer result = formatSubDateForDate(date1, date2);
        return (result.intValue() < 0) ? new Integer(-1) : result;
    }

    /**
     * 日期的加减
     *
     * @param sourceDate
     *            源日期
     * @param diff
     *            与源日期相差的天数
     * @return 最终日期
     */
    public static Date diffDate(Date sourceDate, int diff) {
        if (sourceDate == null) {
            sourceDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(sourceDate);
        c.add(Calendar.DAY_OF_YEAR, diff);
        return c.getTime();
    }

    // 取当前日期所在周的第一天
    public static Date getWeekFirstDay(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.set(Calendar.DAY_OF_WEEK, 1);
        return cal.getTime();
    }

    // 取当前日期所在周的最后一天
    public static Date getWeekLastDay(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.set(Calendar.DAY_OF_WEEK, 7);
        // cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    // 取给定日期是星期几，星期日为0，星期一为1....
    public static int getDayOfWeek(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    // 日期加减
    public static Date getAddDay(Date dt, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * 计算从今天至endDate（含endDate）有多少双休日
     *
     * @param endDate
     * @return
     */
    public static int countRestDay(Date endDate) {
        Integer lastDate = Tools.subtractDateNegative(endDate, new Date());
        int restDay = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // endDate当天也算工作日
        for (int i = 0; i < lastDate.intValue() + 1; i++) {
            // 从今天开始计算双休日数量
            if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                            || (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
                restDay += 1;
            }
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return restDay;
    }

    /**
     * 计算从startDate至endDate（含endDate）有工作日
     *
     * @param endDate
     * @param startDate
     * @return
     */
    public static Integer countWorkingDay(Date endDate, Date startDate) {
        Integer lastDate = Tools.subtractDateNegative(endDate, startDate);
        if (lastDate == null) {
            return null;
        }
        if (lastDate.intValue() < 0) {
            return new Integer(-1);
        }
        int day = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        WorkingDay workingDay = null;
        Map<String, WorkingDay> dict = DictionaryPool.getInstance().get(WorkingDay.class.getSimpleName());
        for (int i = 0; i < lastDate.intValue(); i++) {
            // 从明天开始计算有多少工作日
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            if (dict != null)
                workingDay = dict.get(Tools.DATE_FORMAT4.format(calendar.getTime()));
            if (workingDay == null) {
                if (!Tools.checkWeekend(calendar.getTime())) {
                    day++;
                }
            } else {
                if (DateFlag.工作日.equals(workingDay.getDateFlag())) {
                    day++;
                }
            }
        }
        return new Integer(day);
    }

    /**
     * 获取一个月的日期范围。如：2013-01-01至2013-02-01
     *
     * @return 字符串数组，数组第一个元素起始日期，数组第二个元素结束日期
     */
    public static Date[] getMonthRange() {
        Date[] result = new Date[2];
        // 起始时间为当前月份的1日
        String date1 = DATE_FORMAT.format(new Date());
        date1 = date1.substring(0, date1.length() - 2) + "01";
        Calendar cal = Calendar.getInstance();
        try {
            result[0] = DATE_FORMAT.parse(date1);
            cal.setTime(DATE_FORMAT.parse(date1));
            cal.add(Calendar.MONTH, 1);
            // 结束日期为当前月的下一月的1日
            result[1] = cal.getTime();
        } catch (Exception ex) {
            return null;
        }
        return result;
    }

    /**
     * 根据年份获取这一年的日期范围。如：2017-01-01至2017-12-31
     * 如果当期日期小于结束日期，那么结束日期为当期日期。如  年份为2017,当期日期为2017-07-01 ，那么返回日期数组为：2017-01-01 00:00:00至2017-07-01 23:59:59
     * @return 日期数组，数组第一个元素起始时间，数组第二个元素结束时间
     */
    public static Date[] getYearRange(int year){//日期查询
    	
    	Date[] result = new Date[2];
		Calendar calendar = new GregorianCalendar();//定义一个日历，变量作为年初
		Calendar calendarEnd = new GregorianCalendar();//定义一个日历，变量作为年末
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);//设置年初的日期为1月1日0点0分0秒
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
		calendarEnd.set(Calendar.YEAR, year);
		calendarEnd.set(Calendar.MONTH, 11);
		calendarEnd.set(Calendar.DAY_OF_MONTH, 31);//设置年末的日期为12月31日23点59分59秒
		calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
		calendarEnd.set(Calendar.MINUTE, 59);
		calendarEnd.set(Calendar.SECOND, 59);  
		result[0] = calendar.getTime();
		
		if(new Date().getTime() < calendarEnd.getTime().getTime()){
			calendarEnd.setTime(new Date());
		}
		result[1] = calendarEnd.getTime();
    	 
		return result; 
	}
    
    /**
     * 格式化文本，在文本的头部加两个汉字空格
     *
     * @param source
     * @return
     */
    public static String formatTextWhitespace(CharSequence source) {
        if (Tools.isEmptyString(source.toString())) {
            return "";
        }
        int idx = 0, strLen = source.length();
        for (int i = 0; i < strLen; i++) {
            if (!(Character.isWhitespace(source.charAt(i)) || source.charAt(i) == '　')) {
                return "　　" + source.subSequence(idx, strLen).toString();
            }
            idx++;
        }
        return source.toString();
    }

    /**
     * 判断日期是否是周末
     *
     * @param source
     * @return
     */
    public static boolean checkWeekend(String source) {
        Date date = null;
        try {
            date = DATE_FORMAT.parse(source);
        } catch (Exception ex) {

        }
        if (date == null) {
            return false;
        }
        return checkWeekend(date);
    }

    /**
     * 判断日期是否是周末
     *
     * @param date
     * @return
     */
    public static boolean checkWeekend(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 0 || week == 6) {
            return true;
        }
        return false;
    }

    /**
     * 判断日期是否在时指定间段内
     *
     * @param compareDate
     *            比较日期
     * @param betweenDateLow
     *            时间段1
     * @param betweenDateHi
     *            时间段2
     * @return 比较结果：true compareDate在betweenDateLow和betweenDateHi之间
     */
    public static boolean isBetweenDate(Date compareDate, Date betweenDateLow, Date betweenDateHi) {
        boolean result = false;
        String comps = DATE_FORMAT.format(compareDate), dateLow = DATE_FORMAT.format(betweenDateLow), dateHi = DATE_FORMAT
                        .format(betweenDateHi);
        if (comps.compareTo(dateLow) >= 0 && comps.compareTo(dateHi) <= 0) {
            result = true;
        }
        return result;
    }

    /**
     * 判断时间是否在指定时间段内
     *
     * @param compareTime
     *            比较时间
     * @param timeLow
     *            时间段1，HH:mm格式
     * @param timeHi
     *            时间段2，HH:mm格式
     * @return 比较结果：true compareDate在timeLow和timeHi之间
     */
    public static boolean isBetweenTime(Date compareTime, String timeLow, String timeHi) {
        boolean result = false;
        String comps = DATE_FORMAT5.format(compareTime);
        if (comps.compareTo(timeLow) >= 0 && comps.compareTo(timeHi) <= 0) {
            result = true;
        }
        return result;
    }

    /**
     * 在指定字符串上补充num个空格
     *
     * @param source
     *            原字符串
     * @param num
     *            需要补充的空格数
     * @return 补充空格后的字符串
     */
    public static String addBlack(String source, int num) {
        if (num <= 0) {
            return source;
        }
        if (Tools.isEmptyString(source)) {
            source = "";
        }
        for (int i = 0; i < num; i++) {
            source += " ";
        }
        return source;
    }

    /**
     * 获取num个空格的字符串
     *
     * @param num
     *            需要补充的空格数
     * @return 添加空格后的字符串
     */
    public static String getBlack(int num) {
        String result = "";
        if (num <= 0) {
            return result;
        }
        for (int i = 0; i < num; i++) {
            result += " ";
        }
        return result;
    }

    /**
     * 求一个字符串的md5值
     *
     * @param target
     *            字符串
     * @return md5 value ,null if target is null
     */
    public static String getAStringMD5(String target) {
        if (target == null) {
            return null;
        }
        return DigestUtils.md5Hex(target);
    }

    /**
     * 比较一个字符串的md5值是否与给定的md5值相等,字符串为null返回false
     *
     * @param targetStr
     * @param targetMD5
     * @return
     */
    public static boolean compareAStringMD5(String targetStr, String targetMD5) {
        if (targetStr == null) {
            return false;
        }
        return getAStringMD5(targetStr).equals(targetMD5);
    }

    /**
     *
     * @param sourceDate
     * @return
     */
    public static String formatMobileDateTime(Date sourceDate) {
        // 时间格式由：汉字标识+时间格式组成， 1分钟之内统称“刚刚”，一小时之内为“x分钟之前”，与当前日期相同为“今天 xx:xx”
        String result = "";
        /** 按 H:m 格式化日期. */
        SimpleDateFormat formatScript = Tools.DATE_FORMAT6;
        if (sourceDate == null) {
            return result;
        }
        Date curDate = new Date();
        int diffDate = Tools.formatSubDateForDate(curDate, sourceDate);
        if (diffDate == 0) {
            int diffMin = formatSubDateForMinutes(curDate, sourceDate);
            if (diffMin < 1) {
                return "刚刚";
            } else if (diffMin < 60) {
                return diffMin + "分钟前";
            } else {
                formatScript = new SimpleDateFormat("今天 H:mm");
            }
        } else if (diffDate == 1) {
            formatScript = new SimpleDateFormat("昨天 H:mm");
        }
        result = formatScript.format(sourceDate);
        return result;
    }

    /**
     * 日期相减 计算相差毫秒数
     *
     * @param endDate
     *            结束日期
     * @param startDate
     *            开始日期
     * @return
     */
    public static long subtractDate(Date endDate, Date startDate) {
        // date1和date2都不能为null 否则返回0
        if (endDate == null || startDate == null) {
            return new Integer(0);
        }
        return endDate.getTime() - startDate.getTime();
    }

    /**
     * 日期相减 计算相差天数
     *
     * @param endDate
     * @param startDate
     * @return
     */
    public static Integer formatSubDateForDate(Date endDate, Date startDate) {
        if (endDate == null || startDate == null) {
            return new Integer(0);
        }
        Calendar temp = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        temp.setTime(endDate);
        end.set(temp.get(Calendar.YEAR), temp.get(Calendar.MONTH), temp.get(Calendar.DATE));
        temp.setTime(startDate);
        start.set(temp.get(Calendar.YEAR), temp.get(Calendar.MONTH), temp.get(Calendar.DATE));

        double t = (end.getTimeInMillis() - start.getTimeInMillis()) / (1000 * 60 * 60 * 24D);// 化为天数
        return new Integer((int) Math.floor(t));
        // return end.get(Calendar.DATE) - start.get(Calendar.DATE);// 获取相差天数

    }

    /**
     * 日期相减 计算相差分钟数
     *
     * @param endDate
     * @param startDate
     * @return
     */
    public static Integer formatSubDateForMinutes(Date endDate, Date startDate) {
        double diff = subtractDate(endDate, startDate);
        double formatDiff = diff / (1000 * 60);
        return new Integer((int) Math.floor(formatDiff)); // 取相差分钟数，向下取整
    }

    /**
     * 转换文件大小
     *
     * @param size
     *            文件大小
     * @return 转换后字符串
     */
    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        }
        return String.format("%d B", size);
    }

    /**
     * 返回当前浏览器类型是否为IE
     *
     * @param request
     *            HttpServletRequest实例
     * @return 当前浏览器类型是否为IE
     */
    public static boolean isIEBrowser(HttpServletRequest request) {
        String browser = (String) request.getSession().getAttribute(IEBROWSER);
        return (!Tools.isEmptyString(browser) && browser.equalsIgnoreCase("true")) ? true : false;
    }

    public static String getFileExtension(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int i = filename.lastIndexOf('.');

            if ((i > -1) && (i < (filename.length() - 1))) {
                return filename.substring(i + 1);
            }
        }
        return "";
    }

    /**
     * 判断是否是有效的可提交的工作日期 允许最迟提交工作记录的天数=4 返回值： 0 = 可以编辑 1 = 过期了 2 = 超前了
     */
    public static int getValidEditDayFlag(Date wdt) {
        int res = 0;
        Date lastCanEditDt = Tools.getAddDay(new Date(), -4);
        if (lastCanEditDt.after(wdt)) {
            // 过期了
            res = 1;
        } else if (wdt.after(new Date())) {
            // 超前了，还没到填写时间呢
            res = 2;
        }
        return res;
    }

    /**
     * 根据日期得到中文星期几
     *
     * @param date
     *            日期
     *
     * @return 星期几
     */
    public static String chineseWeekDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        String[] weeks = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        return weeks[weekDay - 1];
    }

    /**
     * @param bt
     * @param stringbuffer
     */
    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    /**
     * 算出字节数组的MD5码
     *
     * @param bytes
     * @return
     */
    private static String bufferToHex(byte bytes[]) {
        int m = 0, n = bytes.length;
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    /**
     * 获取文件的ND5码
     *
     * @param file
     *            要获取MD5码的文件
     * @return
     * @throws IOException
     */
    public static String getFileMd5Code(File file) throws IOException {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (java.security.NoSuchAlgorithmException ex) {
            throw new AssertionError(ex);
        }
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        md.update(byteBuffer);
        return bufferToHex(md.digest());
    }

    /**
     * 拆分编号，并返回最后4位
     *
     */
    public static String getNextScnode(String scnode, String sncodeType) {
        String resultCode = "";
        if ("0".equals(sncodeType)) {
            resultCode = "zh";
        }
        else if ("2".equals(sncodeType)) {
            resultCode = "JY";
        }
        Calendar c = Calendar.getInstance();
        if (!Tools.isEmptyString(scnode)) {
            int n = scnode.indexOf("-");
            String code = scnode.substring(n + 1, scnode.length());
            int max = Integer.valueOf(code);
            max += 1;
            String s = String.valueOf(max);
            String newSnode = "";
            if (s.length() == 1) {
                newSnode = "000" + max;
            } else if (s.length() == 2) {
                newSnode = "00" + max;
            } else if (s.length() == 3) {
                newSnode = "0" + max;
            } else if (s.length() == 4) {
                newSnode = "" + max;
            }
            resultCode += c.get(Calendar.YEAR) + "-" + newSnode;
        } else {
            resultCode += c.get(Calendar.YEAR) + "-" + "0001";
        }
        return resultCode;
    }
    
    /**
     * 判断是否为合法的日期时间字符串
     * @param str_input
     * @return boolean;符合为true,不符合为false
     */
    public static boolean isDate(String str_input,String rDateFormat){
    	if (!Tools.isEmptyString(str_input)) {
	         SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
	         formatter.setLenient(false);
	         try {
	             formatter.format(formatter.parse(str_input));
	         } catch (Exception e) {
	             return false;
	         }
	         return true;
	     }
    	return false;
    }
    
    /**
     * 根据天数小时数，返回计算后的绝对时间
     *
     * @param limitDay
     * @param hour
     * @param baseDate
     * @return
     */
    public static Date getWorkingDateByDayAndHour(Integer limitDay,Integer hour, Date baseDate) {
        Date result = null;
        int idx = 0;
        int ids = 0;
        Date now = (baseDate == null) ? new Date() : baseDate;
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        while (idx < limitDay) {
            c.add(Calendar.DAY_OF_YEAR, 1);

            if (!Tools.checkWeekend(c.getTime())) {
                idx++;
                result = c.getTime();
            }
        }
        while (ids < hour) {
        	c.add(Calendar.HOUR_OF_DAY, 1);
        	
        	if (!Tools.checkWeekend(c.getTime())) {
        		ids++;
        		result = c.getTime();
        	}
        }
        return result;
    }
    
    /**
     * 根据分钟数，返回计算后的绝对时间
     *
     * @param minute
     * @param baseDate
     * @return
     */
    public static Date getWorkingDateByMinute(Integer minute,Date baseDate) {
        Date result = null;
        int idx = 0;
        Date now = (baseDate == null) ? new Date() : baseDate;
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        while (idx < minute) {
            c.add(Calendar.MINUTE, 1);

            if (!Tools.checkWeekend(c.getTime())) {
                idx++;
                result = c.getTime();
            }
        }
        
        return result;
    }
    
}
