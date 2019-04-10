package cn.com.chaochuang.sms.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import cn.com.chaochuang.sms.bean.ArrayOfSmSendInfo;

@WebService(targetNamespace = "http://webservice.sms.spower.com", name = "ISmsExServicePortType")
@XmlSeeAlso({ObjectFactory.class, ObjectFactory.class})
public interface ISmsExServicePortType {

    @WebMethod
    @RequestWrapper(localName = "smsExJson", targetNamespace = "http://webservice.sms.spower.com", className = "com.spower.sms.webservice.SmsExJson")
    @ResponseWrapper(localName = "smsExJsonResponse", targetNamespace = "http://webservice.sms.spower.com", className = "com.spower.sms.webservice.SmsExJsonResponse")
    @WebResult(name = "out", targetNamespace = "http://webservice.sms.spower.com")
    public java.lang.String smsExJson(
        @WebParam(name = "in0", targetNamespace = "http://webservice.sms.spower.com")
        java.lang.String in0
    );

    @WebMethod
    @RequestWrapper(localName = "smsEx", targetNamespace = "http://webservice.sms.spower.com", className = "com.spower.sms.webservice.SmsEx")
    @ResponseWrapper(localName = "smsExResponse", targetNamespace = "http://webservice.sms.spower.com", className = "com.spower.sms.webservice.SmsExResponse")
    public void smsEx(
        @WebParam(name = "in0", targetNamespace = "http://webservice.sms.spower.com")
        ArrayOfSmSendInfo in0
    );
}
