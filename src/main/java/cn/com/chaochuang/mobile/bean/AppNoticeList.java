/*
 * FileName:    InfopubShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年2月24日 (zhongli) 1.0 Create
 */

package cn.com.chaochuang.mobile.bean;

import cn.com.chaochuang.common.reference.StatusFlag;
import org.dozer.Mapping;

import java.util.Date;

/**
 * 公共发布类
 *
 * @author zhongli
 *
 */
public class AppNoticeList {

    private String           id;
    /** 标题 */
    private String           title;

    /** 发布日期 */
    private Date             publishDate;

    /** 部门名称 */
    @Mapping("publishDept.deptName")
    private String           publishDeptName;

    /** 创建人 */
    private String           creatorName;

    /** 状态 */
    private StatusFlag status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public StatusFlag getStatus() {
        return status;
    }

    public void setStatus(StatusFlag status) {
        this.status = status;
    }

    public String getPublishDeptName() {
        return publishDeptName;
    }

    public void setPublishDeptName(String publishDeptName) {
        this.publishDeptName = publishDeptName;
    }
}
