/*
 * FileName:    MessageInfoBean.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年5月10日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.message.app.bean;

/**
 * @author HM
 *
 */
public class MessageInfoBean {

    private Long    id;

    /** 邮件路径 */
    private String  mailPath;

    /** 邮件数量 */
    private Integer mailSum;

    /** 即时消息数量 */
    private Integer imMessageSum;

    /** 其余业务模块消息(信访、监督等) */
    private Integer otherSum;

    /** 未读消息 */
    private Integer notReadSum;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

	public String getMailPath() {
		return mailPath;
	}

	public void setMailPath(String mailPath) {
		this.mailPath = mailPath;
	}

	public Integer getMailSum() {
		return mailSum;
	}

	public void setMailSum(Integer mailSum) {
		this.mailSum = mailSum;
	}

	public Integer getImMessageSum() {
		return imMessageSum;
	}

	public void setImMessageSum(Integer imMessageSum) {
		this.imMessageSum = imMessageSum;
	}

	public Integer getOtherSum() {
		return otherSum;
	}

	public void setOtherSum(Integer otherSum) {
		this.otherSum = otherSum;
	}

	public Integer getNotReadSum() {
		return notReadSum;
	}

	public void setNotReadSum(Integer notReadSum) {
		this.notReadSum = notReadSum;
	}

    
}
