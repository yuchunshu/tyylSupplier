/*
 * FileName:    ISmsWebService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年6月1日 (HM) 1.0 Create
 */

package cn.com.chaochuang.webservice.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author HM
 *
 */
@WebService(targetNamespace = "http://transfer.server.webservice.spower.com")
public interface ISmsWebService {

    /**
     * 短信发送 <br>
     * *注意：最好对返回结果进行转码。 new String(result.getBytes("utf-8"), "utf-8");
     *
     * @param policyIdentifier
     *            短信发送单位标识，由管理员分配，为必填参数
     * @param extendPhone
     *            短信发送扩展号，如：短信发送号码为123，扩展号为01，则短信的发送号码为12301。非必填参数
     * @param receivePhones
     *            短信接收人的号码，有多个号码使用英文逗号分隔，接收号码数控制在100人以内。必填参数
     * @param content
     *            短信内容，内容不能超过250个字（中文和非中文均算一个字），必填参数
     * @return 1、错误信息返回：{"ERRORMSG" : "错误描述"} 2、正常执行返回：[{"sendPhone":"短信发送号码", "phone":"接收人号码1", "sendingId":"查询号"},
     *         {"sendPhone":"短信发送号码", "phone":"接收人号码2", "sendingId":"查询号"}]
     */
    @WebResult(name = "out", targetNamespace = "http://transfer.server.webservice.spower.com")
    @WebMethod
    String sendSms(@WebParam(name = "policyIdentifier") String policyIdentifier,
                    @WebParam(name = "extendPhone") String extendPhone,
                    @WebParam(name = "receivePhones") String receivePhones, @WebParam(name = "content") String content);

    /**
     * 短信查询号，多个号码使用英文逗号分隔，查询号的数量控制在100个以内<br>
     * *注意：最好对返回结果进行转码。 new String(result.getBytes("utf-8"), "utf-8");
     *
     * @param smSendingIds
     * @return [{"phone":"接收人号码1", "sendingId":"查询号", "smMessage":"短信已发送/短信未发送", "receiveTime":"短信发送时间"},
     *         {"phone":"接收人号码2", "sendingId":"查询号", "smMessage":"短信已发送/短信未发送", "receiveTime":"短信发送时间"}]
     */
    @WebResult(name = "out", targetNamespace = "http://transfer.server.webservice.spower.com")
    @WebMethod
    String querySms(@WebParam(name = "smSendingIds") String smSendingIds);
}
