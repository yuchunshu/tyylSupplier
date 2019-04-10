package cn.com.chaochuang.doc.lucene.bean;

/**
 * 
 * @author LJX
 *
 */
public class LuceneData {
	/**公文ID*/
	private String fileId;
	/** 公文名称 */
    private String fileName;
    /**公文类型*/
    private String fileType;
    /** 命中的检索内容 */
    private String hitContent;
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getHitContent() {
		return hitContent;
	}
	public void setHitContent(String hitContent) {
		this.hitContent = hitContent;
	}
    
    

}
