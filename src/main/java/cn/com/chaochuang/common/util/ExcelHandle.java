/*
 * FileName:    ExcelHandle.java
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
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * @author LLM
 *
 */
public final class ExcelHandle {
    /** 模版excel文件构造数据 */
    private static Map<String, Map[]>           tempFileMap  = new HashMap<String, Map[]>();
    /** 输出文件流 */
    private static Map<String, FileInputStream> tempStream   = new HashMap<String, FileInputStream>();
    /** 模版工作簿 */
    private static Map<String, XSSFWorkbook>    tempWorkbook = new HashMap<String, XSSFWorkbook>();

    /**
     * @param tempFilePath
     * @param outputFilePath
     * @param datas
     * @param sheet
     */
    public static void outputExcel(String tempFilePath, String outputFilePath, Map<String, Object> datas, int sheet) {
        try {
            writeData(tempFilePath, datas, sheet);
            File file = new File(outputFilePath);
            OutputStream os = new FileOutputStream(file);
            // 写到输出流并关闭资源
            writeAndClose(tempFilePath, os);
            os.flush();
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 将数据写入模板
     *
     * @param tempFilePath
     * @param datas
     * @param sheet
     * @throws IOException
     */
    public static void writeData(String tempFilePath, Map<String, Object> datas, int sheet) throws IOException {
        // 获取模板填充格式位置等数据
        Map temp = ExcelHandle.getTemp(tempFilePath, sheet);
        // 按模板为写入板
        XSSFWorkbook temWorkbook = getTempWorkbook(tempFilePath);
        // 数据填充的sheet
        XSSFSheet wsheet = temWorkbook.getSheetAt(sheet);

        ExcelModuleData excelData;
        Object sourceData = null;
        int listRange = 0, curRow = 0;
        for (Iterator it = temp.entrySet().iterator(); it.hasNext();) {
            Entry entry = (Entry) it.next();
            excelData = (ExcelModuleData) entry.getValue();
            sourceData = datas.get(entry.getKey());
            if (sourceData == null) {
                continue;
            }
            if (excelData.getRow() < curRow) {
                excelData.setRow(excelData.getRow() + listRange);
            }
            if (ExcelUtil.OBJECT_TYPE_LIST.equals(excelData.getObjectType())) {
                List listDatas = (List) sourceData;
                listRange += listDatas.size();
                curRow = excelData.getRow();
                for (Object item : listDatas) {
                    BeanWrapper wrapper = new BeanWrapperImpl(item);
                    for (ExcelModuleData data : excelData.getDatas()) {
                        if (wrapper.isReadableProperty(data.getPropertyName())) {
                            ExcelUtil.setValue(wsheet, curRow, data.getColumn(),
                                            wrapper.getPropertyValue(data.getPropertyName()), data.getCellStyle());
                        }
                    }
                    curRow++;
                }
            } else if (ExcelUtil.OBJECT_TYPE_MAP.equals(excelData.getObjectType())) {
                ExcelUtil.setValue(wsheet, excelData.getRow(), excelData.getColumn(),
                                ((Map) sourceData).get(excelData.getPropertyName()), excelData.getCellStyle());
                if (excelData.getDatas().size() > 0) {
                    for (ExcelModuleData data : excelData.getDatas()) {
                        if (data.getRow() < curRow) {
                            data.setRow(data.getRow() + listRange);
                        }
                        ExcelUtil.setValue(wsheet, data.getRow(), data.getColumn(),
                                        ((Map) sourceData).get(data.getPropertyName()), data.getCellStyle());
                    }
                }
            } else if (ExcelUtil.OBJECT_TYPE_OBJ.equals(excelData.getObjectType())) {
                ExcelUtil.setValue(wsheet, excelData.getRow(), excelData.getColumn(), sourceData,
                                excelData.getCellStyle());
                if (excelData.getDatas().size() > 0) {
                    BeanWrapper wrapper = new BeanWrapperImpl(sourceData);
                    for (ExcelModuleData data : excelData.getDatas()) {
                        if (data.getRow() < curRow) {
                            data.setRow(data.getRow() + listRange);
                        }
                        ExcelUtil.setValue(wsheet, data.getRow(), data.getColumn(),
                                        wrapper.getPropertyValue(data.getPropertyName()), data.getCellStyle());
                    }
                }
            }
        }
    }

    /**
     * 获得模板输入流
     *
     * @param tempFilePath
     * @return
     * @throws FileNotFoundException
     */
    private static FileInputStream getFileInputStream(String tempFilePath) throws FileNotFoundException {
        if (!tempStream.containsKey(tempFilePath)) {
            tempStream.put(tempFilePath, new FileInputStream(tempFilePath));
        }

        return tempStream.get(tempFilePath);
    }

    /**
     * 获得输入工作区
     *
     * @param tempFilePath
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     */
    private static XSSFWorkbook getTempWorkbook(String tempFilePath) throws FileNotFoundException, IOException {
        if (!tempWorkbook.containsKey(tempFilePath)) {
            tempWorkbook.put(tempFilePath, new XSSFWorkbook(getFileInputStream(tempFilePath)));
        }
        return tempWorkbook.get(tempFilePath);
    }

    /**
     * 获取模板数据
     *
     * @param tempFilePath
     *            模板文件路径
     * @param sheet
     * @return
     * @throws IOException
     */
    private static Map getTemp(String tempFilePath, int sheet) throws IOException {
        if (!tempFileMap.containsKey(tempFilePath)) {
            tempFileMap.put(tempFilePath, ExcelUtil.getTemplateFile(tempFilePath));
        }
        return tempFileMap.get(tempFilePath)[sheet];
    }

    /**
     * 资源关闭
     *
     * @param tempFilePath
     *            模板文件路径
     * @param os
     *            输出流
     * @throws IOException
     * @throws FileNotFoundException
     */
    private static void writeAndClose(String tempFilePath, OutputStream os) throws FileNotFoundException, IOException {
        if (getTempWorkbook(tempFilePath) != null) {
            getTempWorkbook(tempFilePath).write(os);
            tempWorkbook.remove(tempFilePath);
        }
        if (getFileInputStream(tempFilePath) != null) {
            getFileInputStream(tempFilePath).close();
            tempStream.remove(tempFilePath);
        }
    }
}
