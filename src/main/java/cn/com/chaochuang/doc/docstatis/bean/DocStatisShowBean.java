/*
 * FileName:    DocCountShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年5月17日 ( HeYunTao) 1.0 Create
 */

package cn.com.chaochuang.doc.docstatis.bean;


/**
 * @author hzr 2017.5.25
 *
 */
public class DocStatisShowBean implements Comparable<DocStatisShowBean>{

    /** 用户 */
    private Long   userId;
    private String userName;
    /** 所属 部门 */
    private Long   deptId;
    private String deptName;
    /** 创建文数 */
    private Long   createNum;
    /** 经办总数 */
    private Long   dealed;
    /** 办结总数 */
    private Long   finished;
    /** 待办总数 */
    private Long   fordo;
    /** 待阅总数 */
    private Long   reading;
    /** 已阅总数 */
    private Long   readed;

    private String dataStr;

    private String itemName;




    private String adjustNum(Long num) {
    	if (num == null) return "0"; else return num.toString();
    }

    public String getDataStr() {
    	return "创建:" + adjustNum(this.createNum) + ","
    			+ "经办:" + adjustNum(this.dealed) + ","
    			+ "办结:" + adjustNum(this.finished) + ","
    			+ "待办:" + adjustNum(this.fordo) + ","
    			+ "待阅:" + adjustNum(this.reading) + ","
    			+ "已阅:" + adjustNum(this.readed);
	}

	public void setDataStr(String dataStr) {
		this.dataStr = dataStr;
	}

	public String getItemName() {
		if (this.userName != null) {
			return userName;
		} else {
			return deptName;
		}
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the reading
     */
    public Long getReading() {
        return reading;
    }

    /**
     * @param reading
     *            the reading to set
     */
    public void setReading(Long reading) {
        this.reading = reading;
    }

    /**
     * @return the readed
     */
    public Long getReaded() {
        return readed;
    }

    /**
     * @param readed
     *            the readed to set
     */
    public void setReaded(Long readed) {
        this.readed = readed;
    }


    public Long getFordo() {
		return fordo;
	}

	public void setFordo(Long fordo) {
		this.fordo = fordo;
	}

	public Long getDealed() {
		return dealed;
	}

	public void setDealed(Long dealed) {
		this.dealed = dealed;
	}

	public Long getFinished() {
		return finished;
	}

	public void setFinished(Long finished) {
		this.finished = finished;
	}

	/**
     * @return the createNum
     */
    public Long getCreateNum() {
        return createNum != null ? createNum : 0l;
    }

    /**
     * @param createNum
     *            the createNum to set
     */
    public void setCreateNum(Long createNum) {
        this.createNum = createNum;
    }

    /**
     * @return the deptName
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * @param deptName
     *            the deptName to set
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	@Override
	public int compareTo(DocStatisShowBean arg0) {
		long i = this.deptId.longValue() - arg0.deptId.longValue();
		if (i > 0) {
			return 1;
		} else if (i < 0) {
			return -1;
		} else {
			return 0;
		}
	}

}
