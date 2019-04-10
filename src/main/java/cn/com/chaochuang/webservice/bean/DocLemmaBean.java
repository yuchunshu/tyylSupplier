/*
 * FileName:    DocLemmaBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年6月16日 (Administrator@USER-20150531ET) 1.0 Create
 */

package cn.com.chaochuang.webservice.bean;

/**
 * @author Administrator@USER-20150531ET
 *
 */
public class DocLemmaBean {

    private String lemmaName;
    private String lemmaType;
    private char   Display;
    private Long   userId;
    private Long   rmLemmaId;
    private char   commonLemma;
    private String mdfCode;

    /**
     * @return the lemmaName
     */
    public String getLemmaName() {
        return lemmaName;
    }

    /**
     * @param lemmaName
     *            the lemmaName to set
     */
    public void setLemmaName(String lemmaName) {
        this.lemmaName = lemmaName;
    }

    /**
     * @return the lemmaType
     */
    public String getLemmaType() {
        return lemmaType;
    }

    /**
     * @param lemmaType
     *            the lemmaType to set
     */
    public void setLemmaType(String lemmaType) {
        this.lemmaType = lemmaType;
    }

    /**
     * @return the isDisplay
     */
    public char getDisplay() {
        return Display;
    }

    /**
     * @param isDisplay
     *            the isDisplay to set
     */
    public void setDisplay(char isDisplay) {
        this.Display = isDisplay;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the rmLemmaId
     */
    public Long getRmLemmaId() {
        return rmLemmaId;
    }

    /**
     * @param rmLemmaId
     *            the rmLemmaId to set
     */
    public void setRmLemmaId(Long rmLemmaId) {
        this.rmLemmaId = rmLemmaId;
    }

    /**
     * @return the commonLemma
     */
    public char getCommonLemma() {
        return commonLemma;
    }

    /**
     * @param commonLemma
     *            the commonLemma to set
     */
    public void setCommonLemma(char commonLemma) {
        this.commonLemma = commonLemma;
    }

    /**
     * @return the mdfCode
     */
    public String getMdfCode() {
        return mdfCode;
    }

    /**
     * @param mdfCode
     *            the mdfCode to set
     */
    public void setMdfCode(String mdfCode) {
        this.mdfCode = mdfCode;
    }

}
