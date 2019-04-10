/*
 * FileName:    OaDocReceiveFile.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.readmatter.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;
import cn.com.chaochuang.common.reference.ReadStatus;
import cn.com.chaochuang.common.reference.ReadStatusConverter;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfBusinessTypeConverter;

/**
 * @author huangwq
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
@Table(name = "oa_read_matter")
public class ReadMatter extends StringUuidEntity {

    // 发送人
    @ManyToOne
    @JoinColumn(name = "send_man")
    private SysUser           sendUser;
    // 公文标题
    private String            title;
    // 发送时间
    private Date              sendTime;
    // 阅知人,发送人
    private Long              readMan;
    // 文件id
    private String            fileId;
    // 阅知人意见
    private String            opinion;
    // 状态 0未阅 1已阅
    @Convert(converter = ReadStatusConverter.class)
    private ReadStatus        status;
    // 文件类型
    @Convert(converter = WfBusinessTypeConverter.class)
    private WfBusinessType    fileType;
    // 任务id
    private String            taskId;
    // 阅知时间
    private Date              readTime;

    @Transient
    private String            readname;

    /**
     * @return the sendTime
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * @param sendTime
     *            the sendTime to set
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * @return the fileId
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * @return the readMan
     */
    public Long getReadMan() {
        return readMan;
    }

    /**
     * @param readMan
     *            the readMan to set
     */
    public void setReadMan(Long readMan) {
        this.readMan = readMan;
    }

    /**
     * @param fileId
     *            the fileId to set
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
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


    public ReadStatus getStatus() {
        return status;
    }

    public void setStatus(ReadStatus status) {
        this.status = status;
    }

    /**
     * @return the sendUser
     */
    public SysUser getSendUser() {
        return sendUser;
    }

    /**
     * @param sendUser
     *            the sendUser to set
     */
    public void setSendUser(SysUser sendUser) {
        this.sendUser = sendUser;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the taskId
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * @param taskId
     *            the taskId to set
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * @return the readTime
     */
    public Date getReadTime() {
        return readTime;
    }

    /**
     * @param readTime
     *            the readTime to set
     */
    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    /**
     * @return the readname
     */
    public String getReadname() {
        return readname;
    }

    /**
     * @param readname
     *            the readname to set
     */
    public void setReadname(String readname) {
        this.readname = readname;
    }

    public WfBusinessType getFileType() {
        return fileType;
    }

    public void setFileType(WfBusinessType fileType) {
        this.fileType = fileType;
    }



}
