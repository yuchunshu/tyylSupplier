/*
 * FileName:    IMergeData.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年2月3日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.excelview;

/**
 * @author LJX
 *
 */
public interface IMergeData {
    String evaluate(final String data) throws Exception;

    org.apache.velocity.context.Context getContext();
}