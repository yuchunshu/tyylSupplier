/*
 * FileName:    ReturnInfo.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年1月23日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.common.bean;

/**
 * @author Shicx
 *
 */
public class ReturnInfo {

    private String id;
    private String error;
    private String success;


    public ReturnInfo(String error, String success) {
        super();
        this.error = error;
        this.success = success;
    }

    /**
     * @param id
     * @param error
     * @param success
     */
    public ReturnInfo(String id, String error, String success) {
        super();
        this.id = id;
        this.error = error;
        this.success = success;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error
     *            the error to set
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * @return the success
     */
    public String getSuccess() {
        return success;
    }

    /**
     * @param success
     *            the success to set
     */
    public void setSuccess(String success) {
        this.success = success;
    }

}
