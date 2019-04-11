/*
 * FileName:    LogInfo.java
 * Description:
 * Company:     
 * Copyright:    (c) 2014
 * History:     2014年12月10日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.log.service;

/**
 * @author LaoZhiYong
 *
 */
public class LogInfo {
    /** Ip */
    private String ip;
    /** Url */
    private String url;

    /**
     * @param ip
     * @param url
     */
    public LogInfo(String ip, String url) {
        super();
        this.ip = ip;
        this.url = url;
    }

    public LogInfo() {

    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     *            the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
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
}
