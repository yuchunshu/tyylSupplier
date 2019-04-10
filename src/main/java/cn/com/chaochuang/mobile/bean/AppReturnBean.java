/**
 *
 */
package cn.com.chaochuang.mobile.bean;

import java.util.List;

/**
 * @author hzr 2017.8.14
 *
 */
public class AppReturnBean {

	/** 返回类型（0部门，1人员）*/
	private String 		dtype;

	/** 返回数据*/
	private List        data;

	public String getDtype() {
		return dtype;
	}


	public AppReturnBean(String dtype, List data) {
		super();
		this.dtype = dtype;
		this.data = data;
	}


	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}


}
