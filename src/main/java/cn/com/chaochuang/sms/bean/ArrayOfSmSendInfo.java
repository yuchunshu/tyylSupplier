
package cn.com.chaochuang.sms.bean;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSmSendInfo", propOrder = {
    "smSendInfo"
})
public class ArrayOfSmSendInfo {

    @XmlElement(name = "SmSendInfo", nillable = true)
    protected List<SmSendInfo> smSendInfo;

    public List<SmSendInfo> getSmSendInfo() {
        if (smSendInfo == null) {
            smSendInfo = new ArrayList<SmSendInfo>();
        }
        return this.smSendInfo;
    }

	public void setSmSendInfo(List<SmSendInfo> smSendInfo) {
		this.smSendInfo = smSendInfo;
	}

}
