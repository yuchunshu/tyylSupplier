package cn.com.chaochuang.webservice.utils;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;

/**
 * 2017-12-26
 * CXF上传和下载文件对象包装类 由于CXF的DataHandler无法获取文件名和文件类型，需要在上传和下载时附带文件名
 * @author Shicx
 */
@XmlType(name = "CxfFileWrapper")
public class CxfFileWrapper {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件二进制数据
     */
    private DataHandler file;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    //注解该字段为二进制流
    @XmlMimeType("application/octet-stream")
    public DataHandler getFile() {
        return file;
    }

    public void setFile(DataHandler file) {
        this.file = file;
    }
}
