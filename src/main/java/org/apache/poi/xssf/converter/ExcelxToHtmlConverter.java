package org.apache.poi.xssf.converter;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.converter.ExcelToHtmlUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;

import cn.com.chaochuang.common.util.Tools;

public class ExcelxToHtmlConverter {
    private String             cssClassPrefixCell  = "c";

    private String             cssClassPrefixDiv   = "d";

    private String             cssClassPrefixRow   = "r";

    private String             cssClassPrefixTable = "t";

    private Map<Short, String> excelStyleToClass   = new LinkedHashMap<Short, String>();

    private HtmlFacade         htmlFacade;

    public ExcelxToHtmlConverter() {
        htmlFacade = new HtmlFacade();
    }

    /**
     * 解析并转换
     * 
     * @throws IOException
     */
    public void convert(Workbook wb, OutputStream os) throws IOException {
        StringBuilder sb = new StringBuilder();

        int sheetNum = wb.getNumberOfSheets();
        for (int i = 0; i < sheetNum; i++) {
            sb.append(parseSheet(wb.getSheetAt(i)));
        }

        String styleSheet = htmlFacade.updateStylesheet();
        StringBuilder document = new StringBuilder();
        document.append("<!doctype html><html><head>");
        document.append("<META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        if (StringUtils.isNotBlank(styleSheet)) {
            document.append("<style type=\"text/css\">" + styleSheet + "</style>");
        }
        document.append("</head><body>");
        document.append(sb.toString());
        document.append("</body></html>");
        os.write(document.toString().getBytes("utf-8"));
    }

    /**
     * 解析sheet
     * 
     * @param sheet
     * @return
     */
    private String parseSheet(Sheet sheet) {
        StringBuilder sb = new StringBuilder();
        int lastRowNum = sheet.getLastRowNum();
        Row row = null;

        sb.append("<h2>" + sheet.getSheetName() + "</h2>");
        Map<String, String> spanMap[] = getRowSpanColSpanMap(sheet);// 获取合并单元格信息

        String cssTableStyle = "border-collapse:collapse;border-spacing:0;";
        String cssClass = htmlFacade.getOrCreateCssClass(cssClassPrefixTable, cssTableStyle);
        if (StringUtils.isNotBlank(cssClass)) {
            sb.append("<table class='" + cssClass + "'>");
        } else {
            sb.append("<table style='border-collapse:collapse;' width='100%'>");
        }

        for (int i = sheet.getFirstRowNum(); i <= lastRowNum; i++) {
            row = sheet.getRow(i);
            sb.append(processRow(row, spanMap));
        }

        sb.append("</table>");
        return sb.toString();
    }

    /**
     * 解析row
     * 
     * @param row
     * @return
     */
    private String processRow(Row row, Map<String, String> spanMap[]) {
        StringBuilder sb = new StringBuilder();
        if (row == null) {
            sb.append("<tr><td>&nbsp;</td></tr>");
        } else {
            sb.append("<tr>");
            int lasColNum = row.getLastCellNum();
            Cell cell = null;
            for (int i = 0; i < lasColNum; i++) {
                cell = row.getCell(i);
                sb.append(processCell(cell, spanMap));
            }
            sb.append("</tr>");
        }

        return sb.toString();
    }

    /**
     * 解析cell
     * 
     * @param cell
     * @return
     */
    private String processCell(Cell cell, Map<String, String> spanMap[]) {
        StringBuilder sb = new StringBuilder();

        if (cell == null) {
            sb.append("<td>&nbsp;</td>");
        } else {
            int rowNum = cell.getRowIndex();
            int colNum = cell.getColumnIndex();
            if (spanMap[0].containsKey(rowNum + "," + colNum)) {
                String spanStr = spanMap[0].get(rowNum + "," + colNum);
                spanMap[0].remove(rowNum + "," + colNum);

                int bottomeRow = Integer.valueOf(spanStr.split(",")[0]);
                int bottomeCol = Integer.valueOf(spanStr.split(",")[1]);
                int rowSpan = bottomeRow - rowNum + 1;
                int colSpan = bottomeCol - colNum + 1;

                sb.append("<td rowspan= '" + rowSpan + "' colspan= '" + colSpan + "'");
            } else if (spanMap[1].containsKey(rowNum + "," + colNum)) {
                spanMap[1].remove(rowNum + "," + colNum);
                return sb.toString();
            } else {
                sb.append("<td ");
            }

            String cellValue = this.getCellValue(cell);

            String cellClass = getCellStyle(cell, sb);
            if (StringUtils.isNotBlank(cellClass)) {
                sb.append("class='" + cellClass + "'>");
            } else {
                sb.append(">");
            }

            if (StringUtils.isBlank(cellValue)) {
                sb.append("&nbsp;");
            } else {
                // 将ascii码为160的空格转换为html下的空格（&nbsp;）
                sb.append(cellValue.replace(String.valueOf((char) 160), "&nbsp;"));
            }
            sb.append("</td>");

        }

        return sb.toString();
    }

    /**
     * 获取单元格值
     * 
     * @param cell
     * @return
     */
    private String getCellValue(Cell cell) {
        String result = "";
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_NUMERIC:// 数字类型
            if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                Date date = cell.getDateCellValue();
                if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    result = sdf.format(date);
                } else {
                    result = Tools.DATE_FORMAT.format(date);
                }
            } else if (cell.getCellStyle().getDataFormat() == 58) {
                // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                double value = cell.getNumericCellValue();
                Date date = DateUtil.getJavaDate(value);
                result = Tools.DATE_FORMAT.format(date);
            } else {
                double value = cell.getNumericCellValue();
                CellStyle style = cell.getCellStyle();
                DecimalFormat format = new DecimalFormat();
                String temp = style.getDataFormatString();
                // 单元格设置成常规
                if (temp.equals("General")) {
                    format.applyPattern("#");
                }
                result = format.format(value);
            }
            break;
        case Cell.CELL_TYPE_STRING:// 字符串类型
            result = cell.getRichStringCellValue().toString();
            break;
        case Cell.CELL_TYPE_BLANK://
            result = "";
            break;
        default:
            result = "";
            break;
        }

        return result;
    }

    /**
     * 获取单元格样式
     * 
     * @param cell
     * @param sb
     */
    private String getCellStyle(Cell cell, StringBuilder sb) {
        XSSFCellStyle cellStyle = ((XSSFCell) cell).getCellStyle();
        if (cellStyle != null) {
            final Short cellStyleKey = Short.valueOf(cellStyle.getIndex());

            String cssStyle = buildStyle(cellStyle);
            String cssClass = htmlFacade.getOrCreateCssClass(cssClassPrefixCell, cssStyle);
            excelStyleToClass.put(cellStyleKey, cssClass);

            return cssClass;
        }
        return "";
    }

    /**
     * 生成单元格样式
     * 
     * @param cellStyle
     * @return
     */
    private String buildStyle(XSSFCellStyle cellStyle) {
        StringBuilder style = new StringBuilder();

        style.append("white-space:pre-wrap;");
        ExcelToHtmlUtils.appendAlign(style, cellStyle.getAlignment());
        ExcelxUtils.appendVerticalAlign(style, cellStyle.getVerticalAlignment());

        if (cellStyle.getFillPattern() == 0) {

        } else if (cellStyle.getFillPattern() == 1) {
            // 背景颜色
            XSSFColor foregroundColor = cellStyle.getFillForegroundColorColor();
            if (foregroundColor != null) {
                style.append("background-color: #" + foregroundColor.getARGBHex().substring(2) + ";");
            }
        } else {
            XSSFColor backgroundColor = cellStyle.getFillBackgroundColorColor();
            if (backgroundColor != null) {
                style.append("background-color: #" + backgroundColor.getARGBHex().substring(2) + ";");
            }
        }
        ExcelxUtils.buildStyle_border(style, "top", cellStyle.getBorderTop(), cellStyle.getTopBorderXSSFColor());
        ExcelxUtils.buildStyle_border(style, "right", cellStyle.getBorderRight(), cellStyle.getRightBorderXSSFColor());
        ExcelxUtils.buildStyle_border(style, "bottom", cellStyle.getBorderBottom(),
                        cellStyle.getBottomBorderXSSFColor());
        ExcelxUtils.buildStyle_border(style, "left", cellStyle.getBorderLeft(), cellStyle.getLeftBorderXSSFColor());

        XSSFFont font = cellStyle.getFont();
        ExcelxUtils.buildStyle_font(style, font);
        return style.toString();
    }

    /**
     * 获取表格的合并单元格信息
     * 
     * @param sheet
     * @return
     */
    private Map<String, String>[] getRowSpanColSpanMap(Sheet sheet) {
        Map<String, String> map0 = new HashMap<String, String>();
        Map<String, String> map1 = new HashMap<String, String>();
        int mergedNum = sheet.getNumMergedRegions();

        CellRangeAddress range = null;
        for (int i = 0; i < mergedNum; i++) {
            range = sheet.getMergedRegion(i);
            int topRow = range.getFirstRow();
            int topCol = range.getFirstColumn();
            int bottomRow = range.getLastRow();
            int bottomCol = range.getLastColumn();
            map0.put(topRow + "," + topCol, bottomRow + "," + bottomCol);
            int tempRow = topRow;

            while (tempRow <= bottomRow) {
                int tempCol = topCol;
                while (tempCol <= bottomCol) {
                    map1.put(tempRow + "," + tempCol, "");
                    tempCol++;
                }
                tempRow++;
            }
            map1.remove(topRow + "," + topCol);
        }
        Map[] map = { map0, map1 };
        return map;
    }
}
