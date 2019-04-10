
package cn.com.chaochuang.sms.bean;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SmSendInfo", propOrder = {
    "content",
    "isUserDefinedQuery",
    "modifyFlag",
    "notPage",
    "opType",
    "pageNumber",
    "pageSize",
    "receiveMan",
    "receiveManId",
    "receivePhone",
    "reqDeliveryReport",
    "sendDepId",
    "sendId",
    "sendManId",
    "sendTime",
    "serviceId",
    "sysType",
    "timingSendFlag",
    "timingSendTime",
    "token"
})
public class SmSendInfo {

    @XmlElement(nillable = true)
    protected String content;
    @XmlElement(nillable = true)
    protected Boolean isUserDefinedQuery;
    @XmlElement(nillable = true)
    protected String modifyFlag;
    @XmlElement(nillable = true)
    protected Boolean notPage;
    @XmlElement(nillable = true)
    protected String opType;
    @XmlElement(nillable = true)
    protected Integer pageNumber;
    @XmlElement(nillable = true)
    protected Integer pageSize;
    @XmlElement(nillable = true)
    protected String receiveMan;
    @XmlElement(nillable = true)
    protected Long receiveManId;
    @XmlElement(nillable = true)
    protected String receivePhone;
    @XmlElement(nillable = true)
    protected Integer reqDeliveryReport;
    @XmlElement(nillable = true)
    protected Long sendDepId;
    @XmlElement(nillable = true)
    protected Integer sendId;
    @XmlElement(nillable = true)
    protected Long sendManId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar sendTime;
    @XmlElement(nillable = true)
    protected String serviceId;
    @XmlElement(nillable = true)
    protected String sysType;
    @XmlElement(nillable = true)
    protected String timingSendFlag;
    @XmlSchemaType(name = "dateTime")
    protected Date timingSendTime;
    @XmlElement(nillable = true)
    protected Long token;

    /**
     * 获取content属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置content属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * 获取isUserDefinedQuery属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsUserDefinedQuery() {
        return isUserDefinedQuery;
    }

    /**
     * 设置isUserDefinedQuery属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsUserDefinedQuery(Boolean value) {
        this.isUserDefinedQuery = value;
    }

    /**
     * 获取modifyFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifyFlag() {
        return modifyFlag;
    }

    /**
     * 设置modifyFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifyFlag(String value) {
        this.modifyFlag = value;
    }

    /**
     * 获取notPage属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNotPage() {
        return notPage;
    }

    /**
     * 设置notPage属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNotPage(Boolean value) {
        this.notPage = value;
    }

    /**
     * 获取opType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOpType() {
        return opType;
    }

    /**
     * 设置opType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOpType(String value) {
        this.opType = value;
    }

    /**
     * 获取pageNumber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPageNumber() {
        return pageNumber;
    }

    /**
     * 设置pageNumber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPageNumber(Integer value) {
        this.pageNumber = value;
    }

    /**
     * 获取pageSize属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 设置pageSize属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPageSize(Integer value) {
        this.pageSize = value;
    }

    /**
     * 获取receiveMan属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiveMan() {
        return receiveMan;
    }

    /**
     * 设置receiveMan属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiveMan(String value) {
        this.receiveMan = value;
    }

    /**
     * 获取receiveManId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getReceiveManId() {
        return receiveManId;
    }

    /**
     * 设置receiveManId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setReceiveManId(Long value) {
        this.receiveManId = value;
    }

    /**
     * 获取receivePhone属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceivePhone() {
        return receivePhone;
    }

    /**
     * 设置receivePhone属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceivePhone(String value) {
        this.receivePhone = value;
    }

    /**
     * 获取reqDeliveryReport属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getReqDeliveryReport() {
        return reqDeliveryReport;
    }

    /**
     * 设置reqDeliveryReport属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setReqDeliveryReport(Integer value) {
        this.reqDeliveryReport = value;
    }

    /**
     * 获取sendDepId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSendDepId() {
        return sendDepId;
    }

    /**
     * 设置sendDepId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSendDepId(Long value) {
        this.sendDepId = value;
    }

    /**
     * 获取sendId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSendId() {
        return sendId;
    }

    /**
     * 设置sendId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSendId(Integer value) {
        this.sendId = value;
    }

    /**
     * 获取sendManId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSendManId() {
        return sendManId;
    }

    /**
     * 设置sendManId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSendManId(Long value) {
        this.sendManId = value;
    }

    /**
     * 获取sendTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSendTime() {
        return sendTime;
    }

    /**
     * 设置sendTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSendTime(XMLGregorianCalendar value) {
        this.sendTime = value;
    }

    /**
     * 获取serviceId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceId() {
        return serviceId;
    }

    /**
     * 设置serviceId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceId(String value) {
        this.serviceId = value;
    }

    /**
     * 获取sysType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysType() {
        return sysType;
    }

    /**
     * 设置sysType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysType(String value) {
        this.sysType = value;
    }

    /**
     * 获取timingSendFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimingSendFlag() {
        return timingSendFlag;
    }

    /**
     * 设置timingSendFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimingSendFlag(String value) {
        this.timingSendFlag = value;
    }

    /**
     * 获取timingSendTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getTimingSendTime() {
        return timingSendTime;
    }

    /**
     * 设置timingSendTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimingSendTime(Date value) {
        this.timingSendTime = value;
    }

    /**
     * 获取token属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getToken() {
        return token;
    }

    /**
     * 设置token属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setToken(Long value) {
        this.token = value;
    }

}
