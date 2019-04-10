/*
 * FileName:    Result.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年1月7日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.bean;

/**
 * @author LaoZhiYong
 *
 */
public class Result {
    private String success;
    private String errorMsg;

    public Result(String success, String errorMsg) {
        this.success = success;
        this.errorMsg = errorMsg;
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

    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg
     *            the errorMsg to set
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
