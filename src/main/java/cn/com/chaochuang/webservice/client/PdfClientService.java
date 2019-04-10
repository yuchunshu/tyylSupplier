package cn.com.chaochuang.webservice.client;

import cn.com.chaochuang.webservice.utils.CxfFileWrapper;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * 2017-12-26
 *
 * @author Shicx
 */
@WebService(targetNamespace = "http://pdfserver.gxfdaoa.chaochuang.com.cn")
public interface PdfClientService {

    /**
     * 接收上传的文件
     * @param fileData
     * @return
     */
    @WebMethod
    String uploadDocFile(@WebParam(name = "fileData") CxfFileWrapper fileData);

    /**
     * 转换并下载pdf文件
     * @param filePath
     * @return
     */
    @WebMethod
    CxfFileWrapper transferAndDownload(@WebParam(name = "filePath") String filePath);
}
