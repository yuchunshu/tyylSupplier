/*
 * FileName:    ReturnInfo.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年1月23日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.common.bean;

/**
 * @author hzr 2017.6.30
 *
 */
public class ReturnBean {

    private String   message;
    private Object   object;
    private boolean  success;


	public ReturnBean() {
		super();
	}

	public ReturnBean(String message) {
		super();
		this.message = message;
		this.object = null;
		this.success = false;
	}

	public ReturnBean(String message, Object object, boolean success) {
		super();
		this.message = message;
		this.object = object;
		this.success = success;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}


}
