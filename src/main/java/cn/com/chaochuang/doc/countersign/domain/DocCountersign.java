package cn.com.chaochuang.doc.countersign.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.common.reference.SignStatus;
import cn.com.chaochuang.common.reference.SignStatusConverter;
import cn.com.chaochuang.common.user.domain.SysUser;

/**
 * @author hzr 2016年5月16日
 * 公文会签
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
@Table(name = "oa_doc_countersign")
public class DocCountersign extends LongIdEntity {

    private static final long serialVersionUID = 4931671782819228481L;
    
    // 发送人
    @ManyToOne
    @JoinColumn(name = "send_man")
    private SysUser           sendUser;
    // 公文标题
    private String            title;
    // 发送时间
    private Date              sendTime;
   
    // 会签人
    private Long              cosignMan;
    @ManyToOne
    @JoinColumn(name = "cosignMan", insertable = false, updatable = false)
    private SysUser           cosignUser;

    // 会签时间
    private Date              cosignTime;
    // 文件id
    private String            fileId;
    // 会签意见
    private String            opinion;
    // 状态 0未签收, 1已签收, 2已回复;
    @Convert(converter = SignStatusConverter.class)
    private SignStatus        status;
    // 文件类型 
    private String            fileType;
    // 任务id
    private String            taskId;
    
    
    public SysUser getSendUser() {
        return sendUser;
    }
    public void setSendUser(SysUser sendUser) {
        this.sendUser = sendUser;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Date getSendTime() {
        return sendTime;
    }
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
    public Long getCosignMan() {
        return cosignMan;
    }
    public void setCosignMan(Long cosignMan) {
        this.cosignMan = cosignMan;
    }
    public Date getCosignTime() {
        return cosignTime;
    }
    public void setCosignTime(Date cosignTime) {
        this.cosignTime = cosignTime;
    }
    public String getFileId() {
        return fileId;
    }
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
    public String getOpinion() {
        return opinion;
    }
    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
    public String getTaskId() {
        return taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public String getFileType() {
        return fileType;
    }
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public SignStatus getStatus() {
        return status;
    }
    public void setStatus(SignStatus status) {
        this.status = status;
    }
    public SysUser getCosignUser() {
        return cosignUser;
    }
    public void setCosignUser(SysUser cosignUser) {
        this.cosignUser = cosignUser;
    }

}
