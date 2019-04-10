/*
 * FileName:    ExcelModuleData.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年8月27日 (LLM) 1.0 Create
 */

package cn.com.chaochuang.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;

/**
 * @author LLM
 *
 */
public class ExcelModuleData {
    /** 属性名 */
    private String                propertyName;
    /** 对象类型 */
    private String                objectType;
    /** 对象名 */
    private String                objectName;
    /** 行号 */
    private Integer               row;
    /** 列号 */
    private Integer               column;
    /** 单元格类型 */
    private XSSFCellStyle         cellStyle;
    /** 所属数据 */
    private List<ExcelModuleData> datas = new ArrayList();

    /**
     * @return the propertyName
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * @param propertyName
     *            the propertyName to set
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * @return the objectType
     */
    public String getObjectType() {
        return objectType;
    }

    /**
     * @param objectType
     *            the objectType to set
     */
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    /**
     * @return the objectName
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     * @param objectName
     *            the objectName to set
     */
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    /**
     * @return the row
     */
    public Integer getRow() {
        return row;
    }

    /**
     * @param row
     *            the row to set
     */
    public void setRow(Integer row) {
        this.row = row;
    }

    /**
     * @return the column
     */
    public Integer getColumn() {
        return column;
    }

    /**
     * @param column
     *            the column to set
     */
    public void setColumn(Integer column) {
        this.column = column;
    }

    /**
     * @return the datas
     */
    public List<ExcelModuleData> getDatas() {
        return datas;
    }

    /**
     * @param datas
     *            the datas to set
     */
    public void setDatas(List<ExcelModuleData> datas) {
        this.datas = datas;
    }

    /**
     * @return the cellStyle
     */
    public XSSFCellStyle getCellStyle() {
        return cellStyle;
    }

    /**
     * @param cellStyle
     *            the cellStyle to set
     */
    public void setCellStyle(XSSFCellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

    /**
     * @param data
     */
    public void addSubData(ExcelModuleData data) {
        if (this.datas == null) {
            this.datas = new ArrayList<ExcelModuleData>();
        }
        this.datas.add(data);
    }
}
