package cn.com.chaochuang.doc.archive.bean;

import org.dozer.Mapping;

import cn.com.chaochuang.doc.event.reference.DenseType;

/**
 * @author HeYunTao
 *
 */
public class FileArcShowBean {

    private String    id;
    /** 标题 */
    private String    title;
    /** 档案编号 */
    @Mapping("archives.archNo")
    private String    archNo;
    /** 档案名称 */
    @Mapping("archives.archName")
    private String    archName;
    /** 编号 */
    private String    sncode;
    /** 文号 */
    private String    docCode;
    /** 来文单位 */
    private String    sendUnit;
    /** 拟稿人 */
    private String    creatorName;
    /** 密级 */
    private DenseType dense;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
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
     * @return the archNo
     */
    public String getArchNo() {
        return archNo;
    }

    /**
     * @param archNo
     *            the archNo to set
     */
    public void setArchNo(String archNo) {
        this.archNo = archNo;
    }

    /**
     * @return the archName
     */
    public String getArchName() {
        return archName;
    }

    /**
     * @param archName
     *            the archName to set
     */
    public void setArchName(String archName) {
        this.archName = archName;
    }

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

    /**
     * @return the sendUnit
     */
    public String getSendUnit() {
        return sendUnit;
    }

    /**
     * @param sendUnit
     *            the sendUnit to set
     */
    public void setSendUnit(String sendUnit) {
        this.sendUnit = sendUnit;
    }

  
    public String getDocCode() {
		return docCode;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	/**
     * @return the dense
     */
    public DenseType getDense() {
        return dense;
    }

    /**
     * @param dense
     *            the dense to set
     */
    public void setDense(DenseType dense) {
        this.dense = dense;
    }

}
