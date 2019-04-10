/*
 * FileName:    PendingItemQureyInfo.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2014
 * History:     2014年8月20日 (LLM) 1.0 Create
 */

package cn.com.chaochuang.webservice.bean;

import java.util.Date;

/**
 * @author huangwq
 *
 */
public class PendingItemQureyInfo {
    /**
     *
     */

    /** 实例编号 */
    private String  instanceId;
    /** 待办项编号 */
    private Long    recipientId;
    /** 待办事宜编号 */
    private Long    forDoId;
    /** 应用模块名 */
    private String  moduleName;
    /** 应用模块类型 */
    private String  moduleType;
    /** 模块名称 */
    private String  moduleNames;
    /** 不在的模块名称 */
    private String  nonModuleNames;
    /** 公文阅读时间 */
    private Date    readTime;
    /** 待办公文状态 */
    private String  status;
    /** 待办公文标题 */
    private String  title;
    /** 待办记录标志 */
    private String  flagId;
    /** 拟稿人 */
    private Integer redactor;
    /** 发送人 */
    private String  senderName;
    /** 发送时间（起始） */
    private Date    sendTimeBegin;
    /** 发送时间（结束） */
    private Date    sendTimeEnd;
    /** 文号 */
    private String  docNumber;
    /** 拟稿时间（起始） */
    private Date    redactTimeBegin;
    /** 拟稿时间（结束） */
    private Date    redactTimeEnd;
    /** 待办连接地址 */
    private String  url;
    /** 公文剩余办理时间（以天算） */
    private Date    limitTime;
    /** 接收人部门编号 */
    private Integer depId;
    /** 祖宗部门Id */
    private Integer ancestorDep;
    /** 公文自编号 */
    private String  processNumber;
    /** 催办标记 */
    private String  pressFlag;
    /** 来文单位 */
    private String  sourceDept;

    /**
     * 构造函数
     */
    public PendingItemQureyInfo() {
    }

    /**
     * 构造函数
     *
     * @param redactor
     */
    public PendingItemQureyInfo(Integer redactor) {
        this.redactor = redactor;
    }

    /**
     * 构造函数
     *
     * @param moduleName
     * @param flagId
     */
    public PendingItemQureyInfo(String moduleName, String flagId) {
        this.moduleName = moduleName;
        this.flagId = flagId;
    }

    /**
     * 构造函数
     *
     * @param moduleName
     * @param flagId
     * @param recipientId
     */
    public PendingItemQureyInfo(String moduleName, String flagId, Long recipientId) {
        this.moduleName = moduleName;
        this.flagId = flagId;
        this.recipientId = recipientId;
    }

    /**
     * 构造函数
     *
     * @param recipientId
     * @param url
     */
    public PendingItemQureyInfo(Long recipientId, String url) {
        this.recipientId = recipientId;
        this.url = url;
    }

    /**
     * 构造函数
     *
     * @param instanceId
     * @param redactor
     */
    public PendingItemQureyInfo(String instanceId, Integer redactor) {
        this.instanceId = instanceId;
        this.redactor = redactor;
    }

    /**
     * 构造函数
     *
     * @param moduleName
     * @param recipientId
     */
    public PendingItemQureyInfo(String moduleName, Long recipientId) {
        this.moduleName = moduleName;
        this.recipientId = recipientId;
    }

    /**
     * @return 返回 title。
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            要设置的 title。
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return Returns the moduleName.
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * @param moduleName
     *            The moduleName to set.
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * @return Returns the moduleType.
     */
    public String getModuleType() {
        return moduleType;
    }

    /**
     * @param moduleType
     *            The moduleType to set.
     */
    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    /**
     * @return Returns the moduleNames.
     */
    public String getModuleNames() {
        return moduleNames;
    }

    /**
     * @param moduleNames
     *            The moduleNames to set.
     */
    public void setModuleNames(String moduleNames) {
        this.moduleNames = moduleNames;
    }

    /**
     * @return Returns the nonModuleNames.
     */
    public String getNonModuleNames() {
        return nonModuleNames;
    }

    /**
     * @param nonModuleNames
     *            The nonModuleNames to set.
     */
    public void setNonModuleNames(String nonModuleNames) {
        this.nonModuleNames = nonModuleNames;
    }

    /**
     * @return 返回 forDoId。
     */
    public Long getForDoId() {
        return forDoId;
    }

    /**
     * @param forDoId
     *            要设置的 forDoId。
     */
    public void setForDoId(Long forDoId) {
        this.forDoId = forDoId;
    }

    /**
     * @return 返回 readTime。
     */
    public Date getReadTime() {
        return readTime;
    }

    /**
     * @param readTime
     *            要设置的 readTime。
     */
    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    /**
     * @return 返回 recipientId。
     */
    public Long getRecipientId() {
        return recipientId;
    }

    /**
     * @param recipientId
     *            要设置的 recipientId。
     */
    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    /**
     * @return 返回 status。
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            要设置的 status。
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the redactor
     */
    public Integer getRedactor() {
        return redactor;
    }

    /**
     * @param redactor
     *            the redactor to set
     */
    public void setRedactor(Integer redactor) {
        this.redactor = redactor;
    }

    /**
     * @return the senderName
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * @param senderName
     *            the senderName to set
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * @return the sendTimeBegin
     */
    public Date getSendTimeBegin() {
        return sendTimeBegin;
    }

    /**
     * @param sendTimeBegin
     *            the sendTimeBegin to set
     */
    public void setSendTimeBegin(Date sendTimeBegin) {
        this.sendTimeBegin = sendTimeBegin;
    }

    /**
     * @return the sendTimeEnd
     */
    public Date getSendTimeEnd() {
        return sendTimeEnd;
    }

    /**
     * @param sendTimeEnd
     *            the sendTimeEnd to set
     */
    public void setSendTimeEnd(Date sendTimeEnd) {
        this.sendTimeEnd = sendTimeEnd;
    }

    /**
     * @return the redactTimeBegin
     */
    public Date getRedactTimeBegin() {
        return redactTimeBegin;
    }

    /**
     * @param redactTimeBegin
     *            the redactTimeBegin to set
     */
    public void setRedactTimeBegin(Date redactTimeBegin) {
        this.redactTimeBegin = redactTimeBegin;
    }

    /**
     * @return the redactTimeEnd
     */
    public Date getRedactTimeEnd() {
        return redactTimeEnd;
    }

    /**
     * @param redactTimeEnd
     *            the redactTimeEnd to set
     */
    public void setRedactTimeEnd(Date redactTimeEnd) {
        this.redactTimeEnd = redactTimeEnd;
    }

    /**
     * @return the docNumber
     */
    public String getDocNumber() {
        return docNumber;
    }

    /**
     * @param docNumber
     *            the docNumber to set
     */
    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    /**
     * @return the flagId
     */
    public String getFlagId() {
        return flagId;
    }

    /**
     * @param flagId
     *            the flagId to set
     */
    public void setFlagId(String flagId) {
        this.flagId = flagId;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the instanceId
     */
    public String getInstanceId() {
        return instanceId;
    }

    /**
     * @param instanceId
     *            the instanceId to set
     */
    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    /**
     * @return the limitTime
     */
    public Date getLimitTime() {
        return limitTime;
    }

    /**
     * @param limitTime
     *            the limitTime to set
     */
    public void setLimitTime(Date limitTime) {
        this.limitTime = limitTime;
    }

    /**
     * @return the depId
     */
    public Integer getDepId() {
        return depId;
    }

    /**
     * @param depId
     *            the depId to set
     */
    public void setDepId(Integer depId) {
        this.depId = depId;
    }

    /**
     * @return the ancestorDep
     */
    public Integer getAncestorDep() {
        return ancestorDep;
    }

    /**
     * @param ancestorDep
     *            the ancestorDep to set
     */
    public void setAncestorDep(Integer ancestorDep) {
        this.ancestorDep = ancestorDep;
    }

    /**
     * @return the processNumber
     */
    public String getProcessNumber() {
        return processNumber;
    }

    /**
     * @param processNumber
     *            the processNumber to set
     */
    public void setProcessNumber(String processNumber) {
        this.processNumber = processNumber;
    }

    /**
     * @return the pressFlag
     */
    public String getPressFlag() {
        return pressFlag;
    }

    /**
     * @param pressFlag
     *            the pressFlag to set
     */
    public void setPressFlag(String pressFlag) {
        this.pressFlag = pressFlag;
    }

    /**
     * @return the sourceDept
     */
    public String getSourceDept() {
        return sourceDept;
    }

    /**
     * @param sourceDept
     *            the sourceDept to set
     */
    public void setSourceDept(String sourceDept) {
        this.sourceDept = sourceDept;
    }
}
