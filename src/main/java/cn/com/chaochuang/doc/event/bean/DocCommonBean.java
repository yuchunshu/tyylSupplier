/**
 * 南宁超创信息工程有限公司
 */
package cn.com.chaochuang.doc.event.bean;

import java.util.Date;

import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.doc.event.reference.DenseType;
import cn.com.chaochuang.doc.event.reference.FileStatusFlag;
import cn.com.chaochuang.doc.event.reference.OpenFlag;
import cn.com.chaochuang.doc.event.reference.UrgencyLevelType;
import cn.com.chaochuang.workflow.reference.WfBusinessType;

/**
 * @author hzr 2016年2月17日
 */
public class DocCommonBean {

	/** 注入当前登录用户（移动端无直接取得，只能传参写入）*/
	private SysUser          currentUser;

    private String           id;
    private String           title;

    /** 收文/发文 */
    private WfBusinessType   fileType;
    /** 文号 */
    /** 来文字号规则：W－年－序号；年即当前年度，为四位，如2008，序号四位，从0001～9999，依次递增 */
    private String           docCode;

    /** 编 号 */
    /** 编 号:1-9+流水号 */
    private String           sncode;
    private String           fileYear;
    private String           fileCode;
    /** 处理时限 */
    private Date             expiryDate;
    /** 工作日时限 */
    private Integer          limitDay;
    /** 发文时限要求（文字说明） */
    private String           limitStr;
    /** 拟稿日期 */
    private Date             createDate;
    /** 密级 */
    private DenseType        dense;
    /** 公开 */
    private OpenFlag         openFlag;
    /** 紧急程度 */
    private UrgencyLevelType urgencyLevel;
    /** 状态 */
    private FileStatusFlag   status;
    /** 打印人 */
    private Long             printMan;
    /** 打印份数 */
    private Integer          copies;
    /** 印发日期 */
    private Date             printDate;
    /** 创建人 */
    private Long             creatorId;
    private String           creatorName;
    /** 拟稿人部门编号 */
    private Long             creatorDepId;
    /** 正文ID */
    private String           docId;
    /** 档案ID */
    private Long             archId;
    /** 是否有附件 '0 无 1 有附件' */
    private AttachFlag       attachFlag;

    /** 附件 */
    private String           attach;
    /** 文件路径 */
    private String           docpath;
    /** 文件名 */
    private String           docname;
    /** 备注 */
    private String           remark;

    /** 当前公文关联的流程实例ID */
    private String           flowInstId;
    /** 当前公文关联的流程定义ID */
    private String           flowId;

    /** 当前办理环节 */
    private String           curNodeId;
    private String           curNodeInstId;
    /** 办理意见 */
    private String           opinion;
    /** 意见日期 */
    private Date             opinionDate;
    /** 办理结果（是否通过） */
    private String           auditPass;

    // 呈送提交时的用的变量
    // /** 下个环节的key（流程图中的环节id） */
    // private String nextTaskKey;
    /** 下一环节定义 */
    private String           nextNodeId;
    /** 下个环节的办理人(可以有多个) */
    private String           nextDealers;
    // /** 下个环节的办理部门 */
    // private Long nextDealDept;

    /**摘要*/
    private String 			 digest;

    /**流程启动判断标记*/
    private boolean          startFlag;


    /**
     * @return the sncode
     */
    public String getSncode() {
        return sncode;
    }

    /**
     * @param sncode
     *            the sncode to set
     */
    public void setSncode(String sncode) {
        this.sncode = sncode;
    }

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

    public String getLimitStr() {
		return limitStr;
	}

	public void setLimitStr(String limitStr) {
		this.limitStr = limitStr;
	}

	public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public DenseType getDense() {
        return dense;
    }

    public void setDense(DenseType dense) {
        this.dense = dense;
    }

    public OpenFlag getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(OpenFlag openFlag) {
        this.openFlag = openFlag;
    }

    public UrgencyLevelType getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(UrgencyLevelType urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public FileStatusFlag getStatus() {
        return status;
    }

    public void setStatus(FileStatusFlag status) {
        this.status = status;
    }

    public Long getPrintMan() {
        return printMan;
    }

    public void setPrintMan(Long printMan) {
        this.printMan = printMan;
    }

	public Integer getCopies() {
		return copies;
	}

	public void setCopies(Integer copies) {
		this.copies = copies;
	}

	public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Long getCreatorDepId() {
        return creatorDepId;
    }

    public void setCreatorDepId(Long creatorDepId) {
        this.creatorDepId = creatorDepId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public Long getArchId() {
        return archId;
    }

    public void setArchId(Long archId) {
        this.archId = archId;
    }

    public AttachFlag getAttachFlag() {
        return attachFlag;
    }

    public void setAttachFlag(AttachFlag attachFlag) {
        this.attachFlag = attachFlag;
    }

    public Integer getLimitDay() {
        return limitDay;
    }

    public void setLimitDay(Integer limitDay) {
        this.limitDay = limitDay;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getDocpath() {
        return docpath;
    }

    public void setDocpath(String docpath) {
        this.docpath = docpath;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFlowInstId() {
        return flowInstId;
    }

    public void setFlowInstId(String flowInstId) {
        this.flowInstId = flowInstId;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getAuditPass() {
        return auditPass;
    }

    public void setAuditPass(String auditPass) {
        this.auditPass = auditPass;
    }

    public String getNextNodeId() {
        return nextNodeId;
    }

    public void setNextNodeId(String nextNodeId) {
        this.nextNodeId = nextNodeId;
    }

    public String getNextDealers() {
        return nextDealers;
    }

    public void setNextDealers(String nextDealers) {
        this.nextDealers = nextDealers;
    }

    public String getFileYear() {
        return fileYear;
    }

    public void setFileYear(String fileYear) {
        this.fileYear = fileYear;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public Date getOpinionDate() {
        return opinionDate;
    }

    public void setOpinionDate(Date opinionDate) {
        this.opinionDate = opinionDate;
    }

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public WfBusinessType getFileType() {
		return fileType;
	}

	public void setFileType(WfBusinessType fileType) {
		this.fileType = fileType;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getCurNodeId() {
		return curNodeId;
	}

	public void setCurNodeId(String curNodeId) {
		this.curNodeId = curNodeId;
	}

	public String getCurNodeInstId() {
		return curNodeInstId;
	}

	public void setCurNodeInstId(String curNodeInstId) {
		this.curNodeInstId = curNodeInstId;
	}

	public Date getPrintDate() {
		return printDate;
	}

	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}

	public boolean isStartFlag() {
		return startFlag;
	}

	public void setStartFlag(boolean startFlag) {
		this.startFlag = startFlag;
	}

	public SysUser getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(SysUser currentUser) {
		this.currentUser = currentUser;
	}


}
