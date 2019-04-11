/*
 * FileName:    ICommandNode.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年2月3日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.excelview;

import jxl.Sheet;

/**
 * @author LJX
 *
 */
public interface ICommandNode {

    int getRow();

    int getColumn();

    void build(final ExcelParser excelParser, final ParsedWritableSheet out, final Sheet template,
                    final IMergeData mergeData) throws Exception;

    void clear(final ExcelParser excelParser, final ParsedWritableSheet out, final Sheet template,
                    final IMergeData mergeData) throws Exception;
}