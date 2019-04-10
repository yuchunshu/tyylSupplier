/*
 * FileName:    SheetUtils.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年2月3日 (LJX) 1.0 Create
 */

package jxl.write.biff;

import jxl.BooleanCell;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.biff.FormulaData;
import jxl.write.Blank;
import jxl.write.Boolean;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WriteException;

import common.Assert;

/**
 * @author LJX
 *
 */
public class SheetUtils {
    public static void copyRow(Cell[] row, int targetRow, WritableSheet sheet) {
        for (int j = 0; j < row.length; j++) {
            Cell cell = row[j];
            CellType ct = cell.getType();

            // Encase the calls to addCell in a try-catch block
            // These should not generate any errors, because we are
            // copying from an existing spreadsheet. In the event of
            // errors, catch the exception and then bomb out with an
            // assertion
            try {
                if (ct == CellType.LABEL) {
                    Label l = new Label((LabelCell) cell);
                    sheet.addCell(l.copyTo(l.getColumn(), targetRow));
                } else if (ct == CellType.NUMBER) {
                    Number n = new Number((NumberCell) cell);
                    sheet.addCell(n.copyTo(n.getColumn(), targetRow));
                } else if (ct == CellType.DATE) {
                    DateTime dt = new DateTime((DateCell) cell);
                    sheet.addCell(dt.copyTo(dt.getColumn(), targetRow));
                } else if (ct == CellType.BOOLEAN) {
                    Boolean b = new Boolean((BooleanCell) cell);
                    sheet.addCell(b.copyTo(b.getColumn(), targetRow));
                } else if (ct == CellType.NUMBER_FORMULA) {
                    ReadNumberFormulaRecord fr = new ReadNumberFormulaRecord((FormulaData) cell);
                    sheet.addCell(fr.copyTo(fr.getColumn(), targetRow));
                } else if (ct == CellType.STRING_FORMULA) {
                    ReadStringFormulaRecord fr = new ReadStringFormulaRecord((FormulaData) cell);
                    sheet.addCell(fr.copyTo(fr.getColumn(), targetRow));
                } else if (ct == CellType.BOOLEAN_FORMULA) {
                    ReadBooleanFormulaRecord fr = new ReadBooleanFormulaRecord((FormulaData) cell);
                    sheet.addCell(fr.copyTo(fr.getColumn(), targetRow));
                } else if (ct == CellType.DATE_FORMULA) {
                    ReadDateFormulaRecord fr = new ReadDateFormulaRecord((FormulaData) cell);
                    sheet.addCell(fr.copyTo(fr.getColumn(), targetRow));
                } else if (ct == CellType.FORMULA_ERROR) {
                    ReadErrorFormulaRecord fr = new ReadErrorFormulaRecord((FormulaData) cell);
                    sheet.addCell(fr.copyTo(fr.getColumn(), targetRow));
                } else if (ct == CellType.EMPTY) {
                    if (cell.getCellFormat() != null) {
                        // It is a blank cell, rather than an empty cell, so
                        // it may have formatting information, so
                        // it must be copied
                        Blank b = new Blank(cell);
                        sheet.addCell(b.copyTo(b.getColumn(), targetRow));
                    }
                }
            } catch (WriteException e) {
                Assert.verify(false);
            }
        }
    }
}
