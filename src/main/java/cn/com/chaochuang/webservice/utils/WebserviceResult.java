package cn.com.chaochuang.webservice.utils;

import com.alibaba.fastjson.JSON;

/**
 * 2017-12-26
 *
 * @author Shicx
 */
public class WebserviceResult {

    /**
     * 0 失败  1 成功
     */
    public static final String STA_ERR="0";
    public static final String STA_SUC="1";


    private String status;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toErrorJson(String error){
        this.message=error;
        this.status=STA_ERR;
        return JSON.toJSONString(this);
    }

    public String toSuccessJson(String success){
        this.message=success;
        this.status=STA_SUC;
        return JSON.toJSONString(this);
    }
}
