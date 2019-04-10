/*
 * FileName:    ExcelUtil.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年8月27日 (LLM) 1.0 Create
 */

package cn.com.chaochuang.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.converter.ExcelxToHtmlConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;

/**
 * @author LLM
 *
 */
public class ExcelUtil {
    public static final String OBJECT_TYPE_LIST = "list";

    public static final String OBJECT_TYPE_MAP  = "map";

    public static final String OBJECT_TYPE_OBJ  = "object";

    /**
     * 取得指定单元格行和列
     *
     * @param keyMap
     *            所有单元格行、列集合
     * @param key
     *            单元格标识
     * @return 0：列 1：行（列表型数据不记行，即1无值）
     */
    public static int[] getPos(Map keyMap, String key) {
        int[] ret = new int[2];

        if (keyMap.containsKey(key)) {
            ExcelModuleData excelData = (ExcelModuleData) keyMap.get(key);
            ret[0] = excelData.getRow();
            ret[1] = excelData.getColumn();
        }
        return ret;
    }

    /**
     * 取对应格子的值
     *
     * @param sheet
     * @param rowNo
     *            行
     * @param cellNo
     *            列
     * @return
     * @throws IOException
     */
    public static String getCellValue(XSSFSheet sheet, int rowNo, int cellNo) {
        String cellValue = null;
        XSSFRow row = sheet.getRow(rowNo);
        XSSFCell cell = row.getCell(cellNo);
        if (cell != null) {
            if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                cellValue = getCutDotStr(Double.toString(cell.getNumericCellValue()));
            } else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                cellValue = cell.getStringCellValue();
            }
            if (cellValue != null) {
                cellValue = cellValue.trim();
            }
        } else {
            cellValue = null;
        }
        return cellValue;
    }

    /**
     * 取整数
     *
     * @param srcString
     * @return
     */
    private static String getCutDotStr(String srcString) {
        String newString = "";
        if (srcString != null && srcString.endsWith(".0")) {
            newString = srcString.substring(0, srcString.length() - 2);
        } else {
            newString = srcString;
        }
        return newString;
    }

    /**
     * 读数据模板
     *
     * @param 模板地址
     * @throws IOException
     */
    public static Map[] getTemplateFile(String templateFileName) throws IOException {
        FileInputStream fis = new FileInputStream(templateFileName);
        XSSFWorkbook wbPartModule = new XSSFWorkbook(fis);
        int numOfSheet = wbPartModule.getNumberOfSheets();
        Map[] templateMap = new LinkedHashMap[numOfSheet];
        for (int i = 0; i < numOfSheet; i++) {
            XSSFSheet sheet = wbPartModule.getSheetAt(i);
            templateMap[i] = new LinkedHashMap();
            readSheet(templateMap[i], sheet);
        }
        fis.close();
        return templateMap;
    }

    /**
     * 读模板数据的样式值置等信息
     *
     * @param keyMap
     * @param sheet
     */
    private static void readSheet(Map keyMap, XSSFSheet sheet) {
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();

        for (int j = firstRowNum; j <= lastRowNum; j++) {
            XSSFRow rowIn = sheet.getRow(j);
            if (rowIn == null) {
                continue;
            }
            int firstCellNum = rowIn.getFirstCellNum();
            int lastCellNum = rowIn.getLastCellNum();
            if (firstCellNum < 0) {
                continue;
            }
            for (int k = firstCellNum; k <= lastCellNum; k++) {
                // XSSFCell cellIn = rowIn.getCell((short) k);
                XSSFCell cellIn = rowIn.getCell(k);
                if (cellIn == null) {
                    continue;
                }

                int cellType = cellIn.getCellType();
                if (XSSFCell.CELL_TYPE_STRING != cellType) {
                    continue;
                }
                String cellValue = cellIn.getStringCellValue();
                if (cellValue == null) {
                    continue;
                }
                cellValue = cellValue.trim();
                ExcelModuleData excelData = new ExcelModuleData();
                if (cellValue.length() > 0 && cellValue.indexOf("[") >= 0) {
                    excelData.setObjectType(OBJECT_TYPE_LIST);
                    excelData.setObjectName(cellValue.substring(1, cellValue.indexOf("[")));
                    excelData.setPropertyName(cellValue.substring(cellValue.indexOf("[") + 1, cellValue.length() - 1));
                    excelData.setRow(j);
                    excelData.setColumn(k);
                    excelData.setCellStyle(cellIn.getCellStyle());
                } else if (cellValue.length() > 0 && cellValue.indexOf("${") >= 0) {
                    String key = cellValue.substring(cellValue.indexOf("{") + 1, cellValue.length() - 1);
                    String[] item = key.split("\\.");
                    excelData.setObjectType(OBJECT_TYPE_OBJ);
                    if (item.length == 2) {
                        excelData.setObjectName(item[0]);
                        excelData.setPropertyName(item[1]);
                    } else {
                        excelData.setObjectName(key);
                        excelData.setPropertyName(key);
                    }
                    excelData.setCellStyle(cellIn.getCellStyle());
                    excelData.setRow(j);
                    excelData.setColumn(k);
                    // System.out.println("row=" + excelData.getRow() + ";col=" + excelData.getColumn());
                } else if (cellValue.length() > 0 && cellValue.indexOf("get(") > 0) {
                    excelData.setObjectType(OBJECT_TYPE_MAP);
                    excelData.setObjectName(cellValue.substring(1, cellValue.indexOf("get") - 1));
                    excelData.setPropertyName(cellValue.substring(cellValue.indexOf("(") + 1, cellValue.length() - 1));
                    excelData.setCellStyle(cellIn.getCellStyle());
                    excelData.setRow(j);
                    excelData.setColumn(k);
                }
                if (OBJECT_TYPE_LIST.equals(excelData.getObjectType())) {
                    if (keyMap.containsKey(excelData.getObjectName())) {
                        ((ExcelModuleData) keyMap.get(excelData.getObjectName())).addSubData(excelData);
                    } else {
                        ExcelModuleData subData = new ExcelModuleData();
                        subData.setObjectName(excelData.getObjectName());
                        subData.setCellStyle(excelData.getCellStyle());
                        subData.setPropertyName(excelData.getPropertyName());
                        subData.setRow(excelData.getRow());
                        subData.setColumn(excelData.getColumn());
                        excelData.addSubData(subData);
                        keyMap.put(excelData.getObjectName(), excelData);
                    }
                } else if (OBJECT_TYPE_OBJ.equals(excelData.getObjectType())
                                || OBJECT_TYPE_MAP.equals(excelData.getObjectType())) {
                    if (keyMap.containsKey(excelData.getObjectName())) {
                        ((ExcelModuleData) keyMap.get(excelData.getObjectName())).addSubData(excelData);
                    } else {
                        keyMap.put(excelData.getObjectName(), excelData);
                    }
                }
            }
        }
    }

    /**
     * 获取格式，不适于循环方法中使用，wb.createCellStyle()次数超过4000将抛异常
     *
     * @param keyMap
     * @param key
     * @return
     */
    public static CellStyle getStyle(Map keyMap, String key, XSSFWorkbook wb) {
        CellStyle cellStyle = null;

        cellStyle = (CellStyle) keyMap.get(key + "CellStyle");
        // 当字符超出时换行
        cellStyle.setWrapText(true);
        CellStyle newStyle = wb.createCellStyle();
        newStyle.cloneStyleFrom(cellStyle);
        return newStyle;
    }

    /**
     * Excel单元格输出
     *
     * @param sheet
     * @param row
     *            行
     * @param cell
     *            列
     * @param value
     *            值
     * @param cellStyle
     *            样式
     */
    public static void setValue(XSSFSheet sheet, int row, int cell, Object value, CellStyle cellStyle) {
        XSSFRow rowIn = sheet.getRow(row);
        if (rowIn == null) {
            rowIn = sheet.createRow(row);
        }
        XSSFCell cellIn = rowIn.getCell(cell);
        if (cellIn == null) {
            cellIn = rowIn.createCell(cell);
        }
        if (cellStyle != null) {
            // 修复产生多超过4000 cellStyle 异常
            // CellStyle newStyle = wb.createCellStyle();
            // newStyle.cloneStyleFrom(cellStyle);
            // cellIn.setCellStyle(cellStyle);
        }
        // 对时间格式进行单独处理
        if (value == null) {
            cellIn.setCellValue("");
        } else {
            if (isCellDateFormatted(cellStyle)) {
                cellIn.setCellValue((Date) value);
            } else {
                cellIn.setCellValue(new XSSFRichTextString(value.toString()));
            }
        }
    }

    /**
     * 根据表格样式判断是否为日期格式
     *
     * @param cellStyle
     * @return
     */
    public static boolean isCellDateFormatted(CellStyle cellStyle) {
        if (cellStyle == null) {
            return false;
        }
        int i = cellStyle.getDataFormat();
        String f = cellStyle.getDataFormatString();

        return org.apache.poi.ss.usermodel.DateUtil.isADateFormat(i, f);
    }

    public static Boolean replaceExcelSign(String templatePath, String outPath, String outFileName,
                    Map<String, Object> map) {
        InputStream is = null;
        HSSFWorkbook workbook = null;
        FileOutputStream fOut = null;
        try {
            is = new FileInputStream(templatePath);
            workbook = new HSSFWorkbook(is);
            HSSFSheet sheet = null;
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                sheet = workbook.getSheetAt(i);
                for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {
                    HSSFRow row = sheet.getRow(j);
                    if (row != null) {
                        for (int k = 0; k < row.getLastCellNum(); k++) {
                            HSSFCell cell = row.getCell(k);
                            if (cell != null && StringUtils.isNotBlank(cell.toString().trim())) {
                                String cellValue = cell.toString().trim();
                                if (cellValue.matches("[<].*?[>]")) {
                                    String cellTemp = cell.toString().trim().substring(1, cellValue.length() - 1);
                                    cell.setCellValue(map.get(cellTemp) != null ? map.get(cellTemp).toString() : "");
                                }
                            }
                        }
                    }
                }
            }
            File outPathFile = new File(outPath);
            if (!outPathFile.exists()) {
                outPathFile.mkdirs();
            }
            fOut = new FileOutputStream(new File(outPathFile, outFileName));
            workbook.write(fOut);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fOut != null) {
                try {
                    fOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * excel转html
     *
     * @param fileName
     * @param tempPath
     * @param os
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ParserConfigurationException
     * @throws TransformerException
     * @throws InvalidFormatException
     */
    public static void convert2Html(InputStream is, String tempPath, OutputStream os) throws FileNotFoundException,
                    IOException, ParserConfigurationException, TransformerException, InvalidFormatException {

        Workbook wb = WorkbookFactory.create(is);
        if (wb instanceof XSSFWorkbook) {
            XSSFWorkbook xWb = (XSSFWorkbook) wb;
            ExcelxToHtmlConverter converter = new ExcelxToHtmlConverter();
            converter.convert(xWb, os);
        } else if (wb instanceof HSSFWorkbook) {
            HSSFWorkbook workbook = (HSSFWorkbook) wb;
            ExcelToHtmlConverter excelToHtmlConverter = new ExcelToHtmlConverter(
                            DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
            excelToHtmlConverter.processWorkbook(workbook);

            Document htmlDocument = excelToHtmlConverter.getDocument();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DOMSource domSource = new DOMSource(htmlDocument);
            StreamResult streamResult = new StreamResult(out);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer serializer = tf.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "gbk");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");
            serializer.transform(domSource, streamResult);
            out.close();
            os.write(new String(out.toByteArray()).getBytes());
        } else {
            os.write(new String("<!doctype html><html><head><meta charset='utf-8' />"
                            + "<meta http-equiv='X-UA-Compatible' content='IE=edge' />"
                            + "<meta name='viewport' content='width=device-width, initial-scale=1' /></head>"
                            + "<body>无法预览此文档</body></html>").getBytes("utf-8"));
        }
    }

}
