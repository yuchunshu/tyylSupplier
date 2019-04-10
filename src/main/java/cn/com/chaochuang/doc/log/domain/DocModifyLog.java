package cn.com.chaochuang.doc.log.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;

/** 
 * @ClassName: DocModifyLog 
 * @Description: 公文编辑记录
 * @author: yuchunshu
 * @date: 2017年9月5日 下午10:34:40  
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "modify_log_id")) })
@Table(name = "oa_doc_modify_log")
public class DocModifyLog extends StringUuidEntity {

    /**  */
    private static final long serialVersionUID = -3543550105960266797L;

    /** 事项id */
    private String 			  itemId;
    /** 公文id */
    private String 			  fileId;
    /** 编辑前内容 */
    private String 			  oldContent;
    /** 编辑后内容 */
    private String 			  newContent;
    /** 编辑类型 2：修改（暂时只有修改类型） */
    private String 			  modType;
    /** 编辑时间 */
    private Date 			  modTime;
    /** 编辑人 */
    private String 			  modCreater;
    
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
	}
	public String getModCreater() {
		return modCreater;
	}
	public void setModCreater(String modCreater) {
		this.modCreater = modCreater;
	}

}
