package cn.com.chaochuang.doc.util;

import cn.com.chaochuang.doc.event.reference.DocSource;

/**
 * 公文工具类
 * 
 * @author yuandl 2016-11-25
 *
 */
public class DocFileUtils {

    /** 公文版本起始 */
    public static final Integer VER_NUM_START = new Integer(0);

    /** 文书科处室ID */
    public static final Long    DEP_WENSHUKE  = Long.valueOf("32");

    public static String getFlowIdByDocSource(DocSource ds) {
        if (DocSource.外单位来文.equals(ds)) {
            return "F01";
        } else if (DocSource.区局内部收文.equals(ds)) {
            return "F01";
        } else if (DocSource.经费请示.equals(ds)) {
            return "F01";
        } else if (DocSource.审批表.equals(ds)) {
            return "F01";
        } else if (DocSource.群众来信.equals(ds)) {
            return "F01";
        } else if (DocSource.上级转来信.equals(ds)) {
            return "F01";
        } else if (DocSource.处室外单位来文.equals(ds)) {
            return "F03";
        } else if (DocSource.处室内部收文.equals(ds)) {
            return "F03";
        } else if (DocSource.区局发文.equals(ds)) {
            return "F02";
        } else if (DocSource.代拟发文.equals(ds)) {
            return "";
        } else if (DocSource.处室发文.equals(ds)) {
            return "";
        } else {
            return "";
        }
    }
}
