
package cn.com.chaochuang.sms.webservice;

import javax.xml.bind.annotation.XmlRegistry;


@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.spower.sms.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SmsExJson }
     * 
     */
    public SmsExJson createSmsExJson() {
        return new SmsExJson();
    }

    /**
     * Create an instance of {@link SmsExJsonResponse }
     * 
     */
    public SmsExJsonResponse createSmsExJsonResponse() {
        return new SmsExJsonResponse();
    }

    /**
     * Create an instance of {@link SmsEx }
     * 
     */
    public SmsEx createSmsEx() {
        return new SmsEx();
    }

    /**
     * Create an instance of {@link SmsExResponse }
     * 
     */
    public SmsExResponse createSmsExResponse() {
        return new SmsExResponse();
    }

}
