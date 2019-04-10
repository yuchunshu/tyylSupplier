/*
 * FileName:    OaDocReceiveFile.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.readmatter.bean;

/**
 * @author huangwq
 *
 */

public class ReadMatterOppBean {

    // 阅知id
    private String    comId;
    // 意见
    private String    opinion;

    private String    taskId;

    /**
     * @return the comId
     */
    public String getComId() {
        return comId;
    }

    /**
     * @param comId
     *            the comId to set
     */
    public void setComId(String comId) {
        this.comId = comId;
    }

    /**
     * @return the opinion
     */
    public String getOpinion() {
        return opinion;
    }

    /**
     * @param opinion
     *            the opinion to set
     */
    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


}
