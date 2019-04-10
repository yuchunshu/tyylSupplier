package cn.com.chaochuang.doc.lucene.bean;

import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.common.attach.domain.SysAttach;

/**
 *
 * @author LJX
 *
 */
public class LuceneInfo {

	/** 公文附件 */
	private SysAttach attach;
	
	/** 关键字 */
	private String    keyword;
	
	/** 页码 */
	private Integer   page;
	
	/** 每页显示数量 */
	private Integer   rows;
	
	/** 公文 */
	private OaDocFile file;

	/** 公文状态 */
	private String    status;

	public LuceneInfo() {

	}

	public LuceneInfo(OaDocFile file, SysAttach attach) {
		this.attach = attach;
		this.file = file;
	}

	public SysAttach getAttach() {
		return attach;
	}

	public void setAttach(SysAttach attach) {
		this.attach = attach;
	}

	public OaDocFile getFile() {
		return file;
	}

	public void setFile(OaDocFile file) {
		this.file = file;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
