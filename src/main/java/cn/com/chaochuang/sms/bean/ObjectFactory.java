
package cn.com.chaochuang.sms.bean;

import javax.xml.bind.annotation.XmlRegistry;


@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public ArrayOfSmSendInfo createArrayOfSmSendInfo() {
        return new ArrayOfSmSendInfo();
    }

    public SmSendInfo createSmSendInfo() {
        return new SmSendInfo();
    }

}
