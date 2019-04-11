/*
 * FileName:    ExcelParser.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年2月3日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.excelview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.CellFeatures;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableWorkbook;

/**
 * @author LJX
 *
 */
public class ExcelParser {

    private Pattern      pattern;

    private List         actionList;

    public static String FLAG_FOREACH = "#foreach(";
    public static String FLAG_END     = "#end";
    public static String FLAG_LINES   = "#lines=";

    private static int   IS_CONTINUE  = -1;

    public ExcelParser() {
        pattern = Pattern.compile("#[a-z]|\\$!?\\{?[a-z]");
    }

    public int preProcessCell(final ExcelParser parser, final Sheet sheet, Cell cell, List result, final String c) {
        if (hasVelocityToken(c)) {
            int fp;
            int lsPos = c.indexOf(FLAG_LINES);
            if ((lsPos >= 0) && (fp = c.indexOf(FLAG_FOREACH, lsPos + FLAG_LINES.length())) >= 0 && (fp > lsPos)) {
                int lsEnd = c.indexOf(FLAG_END, fp + FLAG_FOREACH.length());
                if (lsEnd > 0) {
                    String ls = c.substring(lsPos + FLAG_LINES.length(), fp).trim();
                    int lines = Integer.parseInt(ls);

                    ForeachCellCommandNode fcc = new ForeachCellCommandNode(cell.getColumn(), cell.getRow(),
                                    c.substring(fp, lsEnd + FLAG_END.length()), cell.getRow() + lines, c.substring(
                                                    lsEnd + FLAG_END.length()).trim());
                    fcc.preProcessSheet(parser, sheet);
                    result.add(fcc);
                    return cell.getRow() + lines - 1;
                }
            } else {
                result.add(new SimpleCellCommandNode(cell.getColumn(), cell.getRow(), c));
            }
        }
        return IS_CONTINUE;
    }

    public List preProcessSheet(final ExcelParser parser, final Sheet sheet, int startCol, int startRow, int endRow) {
        List result = new ArrayList();
        for (int i = startRow; i < endRow; i++) {
            for (int j = startCol; j < sheet.getColumns(); j++) {
                Cell cell = sheet.getCell(j, i);
                CellFeatures cf = cell.getCellFeatures();
                if (null != cf) {
                    String c = cf.getComment();
                    int nexti = preProcessCell(parser, sheet, cell, result, c);
                    if (nexti != IS_CONTINUE) {
                        i = nexti;
                        break;
                    }
                }
            }
            startCol = 0;
        }
        return result;
    }

    public void preProcessWorkbook(final Workbook template) {
        actionList = null;
        if (null != template) {
            actionList = new ArrayList();
            Sheet[] sheets = template.getSheets();
            for (int i = 0; i < sheets.length; i++) {
                Sheet sheet = sheets[i];
                actionList.add(preProcessSheet(this, sheet, 0, 0, sheet.getRows()));
            }
        }
    }

    public boolean hasVelocityToken(final String data) {
        if (null != data) {
            return pattern.matcher(data).find();
        }
        return false;
    }

    public void buildSheet(final List commadList, final ParsedWritableSheet sheet, final Sheet templateSheet,
                    IMergeData mergeData) throws Exception {
        if (commadList != null) {
            String sn = sheet.getName();
            if (hasVelocityToken(sn)) {
                sheet.setName(mergeData.evaluate(sn));
            }
            ParsedWritableSheet pws = sheet;
            for (Iterator iter = commadList.iterator(); iter.hasNext();) {
                ICommandNode element = (ICommandNode) iter.next();
                if (element.getRow() > pws.getCurrentOriginRowIndex()) {
                    pws.setCurrentRowIndex(pws.getCurrentRowIndex() + element.getRow() - pws.getCurrentOriginRowIndex());
                    pws.setCurrentOriginRowIndex(element.getRow());
                    sheet.setNewLine(true);
                }
                element.build(this, pws, templateSheet, mergeData);
            }
        }
    }

    private List getCommandList(int index) {
        if (actionList != null && index >= 0 && actionList.size() > index) {
            return (List) actionList.get(index);
        }
        return null;
    }

    public void buildExcelFromTemplate(final WritableWorkbook out, final Workbook template, IMergeData mergeData)
                    throws Exception {
        if (null != out) {
            for (int i = 0; i < out.getNumberOfSheets(); i++) {
                buildSheet(getCommandList(i), new ParsedWritableSheet(out.getSheet(i)), template.getSheet(i), mergeData);
            }
        }
    }

    public static void main(String[] args) {
        ExcelParser ep = new ExcelParser();
        System.out.println("$!{zltz}==" + ep.hasVelocityToken("$!{zltz}"));
    }
}
