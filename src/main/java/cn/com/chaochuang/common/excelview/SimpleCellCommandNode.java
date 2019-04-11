/*
 * FileName:    SimpleCellCommandNode.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年2月3日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.excelview;

import jxl.CellType;
import jxl.Sheet;
import jxl.write.Blank;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFeatures;

/**
 * @author LJX
 *
 */
public class SimpleCellCommandNode extends AbstractCommandNode {

    // public static String FLAG_MODEL_ROW = "CurrentROW";
    // public static String FLAG_MODEL_COL = "CurrentCOL";

    public SimpleCellCommandNode(int col, int row, final String command) {
        super(col, row, command);
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
            throw new Exception("error in SimpleCellCommandNode.build, the node row is " + this.row
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

        // org.apache.velocity.context.Context context = mergeData.getContext();
        // Object o1, o2;
        // o1 = context.put(FLAG_MODEL_ROW, new Integer(this.row));
        // o2 = context.put(FLAG_MODEL_COL, new Integer(this.col));

        try {
            String value = mergeData.evaluate(this.command);
            boolean isFormula = 0 == ((value != null) ? value.trim().indexOf("=") : 2);
            if (isFormula) {
                value = value.trim().substring(1);
            }
            if (writableCell.getType().equals(CellType.LABEL)) {
                if (isFormula) {
                    Formula bc = new Formula(writableCell.getColumn(), writableCell.getRow(), value);
                    bc.setCellFeatures(writableCell.getWritableCellFeatures());
                    bc.setCellFormat(writableCell.getCellFormat());
                    out.addCell(bc);
                } else {
                    Label l = (Label) writableCell;
                    l.setString((value != null) ? value.toString() : "");
                }
            } else if (writableCell.getType().equals(CellType.NUMBER)) {
                jxl.write.Number n = (jxl.write.Number) writableCell;
                if (value == null || value.trim().length() == 0) {
                    WritableCell bc = new Blank(n.getColumn(), n.getRow());
                    bc.setCellFeatures(n.getWritableCellFeatures());
                    bc.setCellFormat(n.getCellFormat());
                    out.addCell(bc);
                } else {
                    if (isFormula) {
                        Formula bc = new Formula(n.getColumn(), n.getRow(), value);
                        bc.setCellFeatures(n.getWritableCellFeatures());
                        bc.setCellFormat(n.getCellFormat());
                        out.addCell(bc);
                    } else {
                        n.setValue(Double.parseDouble(value));
                    }
                }
            } else if (writableCell.getType().equals(CellType.EMPTY)) {
                if (isFormula) {
                    Formula bc = new Formula(writableCell.getColumn(), writableCell.getRow(), value);
                    bc.setCellFeatures(writableCell.getWritableCellFeatures());
                    bc.setCellFormat(writableCell.getCellFormat());
                    out.addCell(bc);
                } else {
                    WritableCell bc = new Label(writableCell.getColumn(), writableCell.getRow(), value);
                    bc.setCellFeatures(writableCell.getWritableCellFeatures());
                    bc.setCellFormat(writableCell.getCellFormat());
                    out.addCell(bc);
                }
            }
            out.setNewLine(false);
        } finally {
            // if (o1 == null) {
            // context.remove(FLAG_MODEL_ROW);
            // } else {
            // context.put(FLAG_MODEL_ROW, o1);
            // }
            // if (o2 == null) {
            // context.remove(FLAG_MODEL_COL);
            // } else {
            // context.put(FLAG_MODEL_COL, o2);
            // }
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
            throw new Exception("error in SimpleCellCommandNode.clear, the node row is " + this.row
                            + " but CurrentOriginRowIndex is " + out.getCurrentOriginRowIndex());
        }

        int nowRow = (this.row > out.getCurrentRowIndex()) ? this.row : out.getCurrentRowIndex();

        WritableCell writableCell = (WritableCell) out.getCell(this.col, nowRow);
        WritableCellFeatures wcf = writableCell.getWritableCellFeatures();

        out.setCurrentRowIndex(nowRow);
        if (wcf != null)
            wcf.removeComment();

        WritableCell bc = new Blank(writableCell.getColumn(), writableCell.getRow());
        bc.setCellFeatures(writableCell.getWritableCellFeatures());
        bc.setCellFormat(writableCell.getCellFormat());
        out.addCell(bc);

    }

}
