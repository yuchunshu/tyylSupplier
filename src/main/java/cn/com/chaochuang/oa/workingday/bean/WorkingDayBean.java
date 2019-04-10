/*
 * FileName:    NoticeShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.workingday.bean;

import java.util.Date;

import cn.com.chaochuang.doc.event.reference.DateFlag;

/**
 * @author HM
 *
 */
public class WorkingDayBean {
    private Date     dayDate;

    private DateFlag dateFlag;

    public Date getDayDate() {
        return dayDate;
    }

    public void setDayDate(Date dayDate) {
        this.dayDate = dayDate;
    }

    public DateFlag getDateFlag() {
        return dateFlag;
    }

    public void setDateFlag(DateFlag dateFlag) {
        this.dateFlag = dateFlag;
    }
}
