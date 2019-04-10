package cn.com.chaochuang.doc.event.bean;

import java.util.Date;

import cn.com.chaochuang.doc.event.reference.DenseType;
import cn.com.chaochuang.doc.event.reference.DocCate;
import cn.com.chaochuang.doc.event.reference.FileStatusFlag;
import cn.com.chaochuang.doc.event.reference.OpenFlag;
import cn.com.chaochuang.doc.event.reference.UrgencyLevelType;
import cn.com.chaochuang.workflow.reference.WfBusinessType;

/**
 * 公文查询Bean
 *
 * @author yuandl 2016-11-30
 *
 */
public class OaDocFileQueryBean {
    /** 流程实例ID */
    private String           flowInstId;
    /** 流程定义ID */
    private String           flowId;

    /** 收文或发文 */
    private WfBusinessType   fileType;

    /** 文电标题 */
    private String           title;
    /** 来文字号规则：W－年－序号；年即当前年度，为四位，如2008，序号四位，从0001～9999，依次递增 */
    private String           docCode;
    /** 编 号:1-9+流水号 */
    private String           sncode;
    /** 年份 */
    private String           fileYear;
    /** 收文号、发文号 */
    private String           fileCode;
    /** 发文(来文)单位 */
    private String           sendUnit;

    /** 公开 */
    private OpenFlag         openFlag;       // '公开：0-主动公开 1-依申请公开

    /** 紧急程度 */
    private UrgencyLevelType urgencyLevel;
    /** 状态 */
    private FileStatusFlag   status;

    private Long             dealerId;

    /** 拟稿人 user_id */
    private Long             creatorId;
    private String           creatorName;

    /** 拟稿人部门编号 */
    private Long             creatorDepId;
    /** 档案ID */
    private Long             archId;
    /** 文种 */
    private DocCate          docCate;
    /** 意见反馈函件编号 */
    private Long             docDepLetterId;
    /** 意见反馈状态 */
    private String           letterStatus;
    /** 密级 */
    private DenseType        dense;

    /** 创建日期开始 */
    private Date             createDateBegin;
    /** 创建日期结束 */
    private Date             createDateEnd;

    /** 收文或发文 */
    private String   		 businessType;
    
    /** 限办日期开始 */
    private Date             expiryDateBegin;
    
    /** 限办日期结束 */
    private Date             expiryDateEnd;
    
    /** 状态 */
    private String   		 instStatus;
    
    /** 主办处室 */
    private String   		 deptName;
    
    /** 经办类型 */
    private String   		 queryReadFlag;
    
    /** 经办部门id */
    private String   		 dealerDeptId;
    
    /** 督查督办 */
    private String   		 superviseFlag;
    
    public String getFlowInstId() {
        return flowInstId;
    }

    public void setFlowInstId(String flowInstId) {
        this.flowInstId = flowInstId;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public WfBusinessType getFileType() {
        return fileType;
    }

    public void setFileType(WfBusinessType fileType) {
        this.fileType = fileType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    public String getSncode() {
        return sncode;
    }

    public void setSncode(String sncode) {
        this.sncode = sncode;
    }

    public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
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

    public String getSendUnit() {
        return sendUnit;
    }

    public void setSendUnit(String sendUnit) {
        this.sendUnit = sendUnit;
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

	public Long getArchId() {
		return archId;
	}

	public void setArchId(Long archId) {
		this.archId = archId;
	}

	public DocCate getDocCate() {
		return docCate;
	}

	public void setDocCate(DocCate docCate) {
		this.docCate = docCate;
	}

	public Long getDocDepLetterId() {
		return docDepLetterId;
	}

	public void setDocDepLetterId(Long docDepLetterId) {
		this.docDepLetterId = docDepLetterId;
	}

	public String getLetterStatus() {
		return letterStatus;
	}

	public void setLetterStatus(String letterStatus) {
		this.letterStatus = letterStatus;
	}

	public DenseType getDense() {
		return dense;
	}

	public void setDense(DenseType dense) {
		this.dense = dense;
	}

	public Date getCreateDateBegin() {
        return createDateBegin;
    }

    public void setCreateDateBegin(Date createDateBegin) {
        this.createDateBegin = createDateBegin;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Date getExpiryDateBegin() {
		return expiryDateBegin;
	}

	public void setExpiryDateBegin(Date expiryDateBegin) {
		this.expiryDateBegin = expiryDateBegin;
	}

	public Date getExpiryDateEnd() {
		return expiryDateEnd;
	}

	public void setExpiryDateEnd(Date expiryDateEnd) {
		this.expiryDateEnd = expiryDateEnd;
	}

	public String getInstStatus() {
		return instStatus;
	}

	public void setInstStatus(String instStatus) {
		this.instStatus = instStatus;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getQueryReadFlag() {
		return queryReadFlag;
	}

	public void setQueryReadFlag(String queryReadFlag) {
		this.queryReadFlag = queryReadFlag;
	}

	public String getDealerDeptId() {
		return dealerDeptId;
	}

	public void setDealerDeptId(String dealerDeptId) {
		this.dealerDeptId = dealerDeptId;
	}

	public String getSuperviseFlag() {
		return superviseFlag;
	}

	public void setSuperviseFlag(String superviseFlag) {
		this.superviseFlag = superviseFlag;
	}

}
