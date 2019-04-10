package cn.com.chaochuang.doc.archive.bean;

/**
 * @author dengl 2017.12.01
 *
 */
public class ArchivesStatisShowBean {
	
	private Long	depId;
	
	/** 部门名称*/
	private String 	deptName;
	
	/** 档案总数*/
	private Long 	totalFiles;
	
	/** 在库数量*/
	private	Long	stockTotal;
	
	/** 在借数量*/
	private Long	borrowTotal;
	
	/** 销毁数量*/
	private Long    destroyTotal;
	
	private String 	dataStr;
	
    private String adjustNum(Long num) {
    	if (num == null) return "0"; else return num.toString();
    }
    
    public String getDataStr() {
    	return "在库:" + adjustNum(this.stockTotal) + ","
    			+ "外借:" + adjustNum(this.borrowTotal) + ","
    			+ "销毁:" + adjustNum(this.destroyTotal);
	}

	public void setDataStr(String dataStr) {
		this.dataStr = dataStr;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Long getTotalFiles() {
		return totalFiles;
	}

	public void setTotalFiles(Long totalFiles) {
		this.totalFiles = totalFiles;
	}

	public Long getBorrowTotal() {
		return borrowTotal;
	}

	public void setBorrowTotal(Long borrowTotal) {
		this.borrowTotal = borrowTotal;
	}

	public Long getDestroyTotal() {
		return destroyTotal;
	}

	public void setDestroyTotal(Long destroyTotal) {
		this.destroyTotal = destroyTotal;
	}

	public Long getStockTotal() {
		return stockTotal;
	}

	public void setStockTotal(Long stockTotal) {
		this.stockTotal = stockTotal;
	}

	public Long getDepId() {
		return depId;
	}

	public void setDepId(Long depId) {
		this.depId = depId;
	}
}
