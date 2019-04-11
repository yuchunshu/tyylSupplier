/*
 * FileName:    ForeachCellCommandNode.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年2月3日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.excelview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jxl.Sheet;
import jxl.write.WritableCell;
import jxl.write.WritableCellFeatures;

import org.apache.velocity.context.Context;

/**
 * @author LJX
 *
 *
 */
public class ForeachCellCommandNode extends AbstractCommandNode {

    public static String KEY_FOREACH_LOOP = "loop";

    protected int        endRow;

    protected List       actionList;

    protected String     foreachStr;
    protected String     commandCurrent;

    public ForeachCellCommandNode(int col, int row, final String command, int endRow, final String commandCurrent) {
        super(col, row, command);
        this.endRow = endRow;
        int pos = command.indexOf(ExcelParser.FLAG_FOREACH);
        if (pos >= 0) {
            foreachStr = command.substring(pos);
        } else {
            foreachStr = null;
        }
        this.commandCurrent = (commandCurrent != null) ? commandCurrent.trim() : "";
    }

    public void preProcessSheet(final ExcelParser parser, final Sheet sheet) {
        List temp = null;
        if (this.commandCurrent.length() > 0) {
            temp = new ArrayList();
            parser.preProcessCell(parser, sheet, sheet.getCell(this.col, this.row), temp, commandCurrent);
        }
        actionList = parser.preProcessSheet(parser, sheet, super.col + 1, super.row, this.endRow);
        if (temp != null && temp.size() > 0) {
            actionList.add(0, temp.get(0));
        }

    }

    /**
     * （non Javadoc）
     *
     * @see com.spower.excel.engine.parse.ICommandNode#build(com.spower.excel.engine.parse.ExcelParser,
     *      com.spower.excel.engine.parse.ParsedWritableSheet, jxl.Sheet, com.spower.excel.engine.IMergeData)
     */
    public void build(final ExcelParser excelParser, final ParsedWritableSheet out, final Sheet template,
                    final IMergeData mergeData) throws Exception {
        if (out.getCurrentOriginRowIndex() != this.row) {
            throw new Exception("error in ForeachCellCommandNode.build, the node row is " + this.row
                            + " but CurrentOriginRowIndex is " + out.getCurrentOriginRowIndex());
        }

        int nowRow = (this.row > out.getCurrentRowIndex()) ? this.row : out.getCurrentRowIndex();

        WritableCell writableCell = (WritableCell) out.getCell(this.col, nowRow);
        WritableCellFeatures wcf = writableCell.getWritableCellFeatures();
        // if (null != wcf) {
        // if (!command.equals(wcf.getComment())) {
        // throw new Exception("error in SimpleCellCommandNode.build, the command is " + this.command +
        // " but the target command is " + wcf.getComment());
        // }
        out.setCurrentRowIndex(nowRow);
        if (wcf != null)
            wcf.removeComment();
        if (foreachStr != null) {
            Context context = mergeData.getContext();
            Object oldLoop = context.get(KEY_FOREACH_LOOP);
            try {
                IForeachRun fr = new IForeachRun() {

                    private boolean isFirst = true;

                    boolean         isRun   = false;

                    /**
                     * （non Javadoc）
                     *
                     * @see com.spower.excel.engine.parse.IForeachRun#isRun()
                     */
                    public boolean isRun() {
                        return isRun;
                    }

                    /**
                     * （non Javadoc）
                     *
                     * @throws Exception
                     * @see com.spower.excel.engine.parse.IForeachRun#foreach()
                     */
                    public void foreach() throws Exception {
                        isRun = true;
                        if (!isFirst) {
                            out.insertRowsFromTemplate(template, row, endRow);
                            WritableCell writableCell = (WritableCell) out.getCell(col, out.getCurrentRowIndex());
                            WritableCellFeatures wcf = writableCell.getWritableCellFeatures();
                            if (null != wcf && wcf.getComment() != null) {
                                if (wcf.getComment().indexOf(command) >= 0) {
                                    wcf.removeComment();
                                }
                            }
                        }
                        // Context context = mergeData.getContext();
                        // Object oldKey = context.get(keyName);
                        // context.put(keyName, data);
                        try {
                            excelParser.buildSheet(actionList, out, template, mergeData);
                            if (!out.isNewLine()) {
                                out.setCurrentRowIndex(out.getCurrentRowIndex() + 1);
                                out.setNewLine(true);
                            }
                            out.setCurrentOriginRowIndex(row);
                        } finally {
                            // context.put(keyName, oldKey);
                        }
                        isFirst = false;
                    }
                };
                context.put(KEY_FOREACH_LOOP, fr);
                mergeData.evaluate(this.command);
                if (!fr.isRun()) {
                    this.clear(excelParser, out, template, mergeData);
                }
            } finally {
                out.setCurrentOriginRowIndex(this.endRow);
                context.put(KEY_FOREACH_LOOP, oldLoop);
            }
        }
        // }
    }

    /**
     * （non Javadoc）
     *
     * @see com.spower.excel.engine.parse.ICommandNode#clear(com.spower.excel.engine.parse.ExcelParser,
     *      com.spower.excel.engine.parse.ParsedWritableSheet, jxl.Sheet, com.spower.excel.engine.IMergeData)
     */
    public void clear(ExcelParser excelParser, ParsedWritableSheet out, Sheet template, IMergeData mergeData)
                    throws Exception {
        if (out.getCurrentOriginRowIndex() != this.row) {
            throw new Exception("error in ForeachCellCommandNode.clear, the node row is " + this.row
                            + " but CurrentOriginRowIndex is " + out.getCurrentOriginRowIndex());
        }

        int nowRow = (this.row > out.getCurrentRowIndex()) ? this.row : out.getCurrentRowIndex();

        WritableCell writableCell = (WritableCell) out.getCell(this.col, nowRow);
        WritableCellFeatures wcf = writableCell.getWritableCellFeatures();
        out.setCurrentRowIndex(nowRow);
        if (wcf != null)
            wcf.removeComment();

        for (Iterator iter = actionList.iterator(); iter.hasNext();) {
            ICommandNode node = (ICommandNode) iter.next();
            node.clear(excelParser, out, template, mergeData);
        }
    }

}
