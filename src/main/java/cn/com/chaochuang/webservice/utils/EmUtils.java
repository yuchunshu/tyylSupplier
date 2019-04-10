package cn.com.chaochuang.webservice.utils;

import cn.com.chaochuang.common.util.Tools;

/***
 *
 * @author HeYunTao
 *
 */
public class EmUtils {

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
     * 去除内容中的html标签
     *
     * @param source
     * @return
     */
    public static String filterHtmlTag(String source) {
        if (Tools.isEmptyString(source)) {
            return "";
        }
        String content = source.replaceAll("</?[^>]+>", ""); // 剔出<html>的标签
        content = content.replaceAll("<a>\\s*|\t|\r|\n</a>", "");// 去除字符串中的空格,回车,换行符,制表符
        return content;
    }

    /**
     * 返回摘要信息
     *
     * @param source
     *            原内容
     * @param maxLen
     *            摘要长度
     * @return 摘要信息
     */
    public static String getDigest(String source, int maxLen) {
        String digest = substring(filterHtmlTag(source), 0, maxLen);
        if (Tools.isEmptyString(digest)) {
            return "(无摘要)";
        }
        return digest;
    }
}
