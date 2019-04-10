/*
 * FileName:    NoticeShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.doc.log.bean;

import java.util.Date;

import cn.com.chaochuang.common.util.Tools;

/** 
 * @ClassName: DocModifyLogQueryBean 
 * @Description: 公文编辑记录编辑
 * @author: yuchunshu
 * @date: 2017年9月5日 下午10:34:40  
 */
public class DocModifyLogQueryBean {

    private String      id;

    /** 事项id */
    private String 		itemId;
    /** 公文id */
    private String 		fileId;
    /** 编辑前内容 */
    private String 		oldContent;
    /** 编辑后内容 */
    private String 		newContent;
    /** 编辑类型 */
    private String 		modType;
    /** 编辑时间 */
    private Date 		modTime;
    private String      modTimeShow;
    /** 编辑人 */
    private String 		modCreater;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getOldContent() {
		return oldContent;
	}
	public void setOldContent(String oldContent) {
		this.oldContent = oldContent;
	}
	public String getNewContent() {
		return newContent;
	}
	public void setNewContent(String newContent) {
		this.newContent = newContent;
	}
	public String getModType() {
		return modType;
	}
	public void setModType(String modType) {
		this.modType = modType;
	}
	public Date getModTime() {
		return modTime;
	}
	public void setModTime(Date modTime) {
		this.modTime = modTime;
		this.modTime = modTime;
        if (this.modTime != null) {
            this.modTimeShow = Tools.DATE_MINUTE_FORMAT.format(this.modTime);
        }
	}
	public String getModCreater() {
		return modCreater;
	}
	public void setModCreater(String modCreater) {
		this.modCreater = modCreater;
	}
	public String getModTimeShow() {
		return modTimeShow;
	}
	public void setModTimeShow(String modTimeShow) {
		this.modTimeShow = modTimeShow;
	}

}
