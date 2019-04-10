
package cn.com.chaochuang.sms.webservice;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import cn.com.chaochuang.sms.bean.SmSendInfo;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "in0"
})
@XmlRootElement(name = "smsEx")
public class SmsEx {

    @XmlElement(required = true, nillable = true)
    protected List<SmSendInfo> in0;

	public List<SmSendInfo> getIn0() {
		return in0;
	}

	public void setIn0(List<SmSendInfo> in0) {
		this.in0 = in0;
	}

}
