package cn.com.chaochuang.doc.archive.bean;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.doc.archive.domain.DocArchives;
import cn.com.chaochuang.doc.archive.domain.DocPaperArchives;
import cn.com.chaochuang.doc.event.domain.OaDocFile;

/**
 * @author dengl 2017.12.07
 *
 */

public class LuceneEditBean {
	
	/** 公文附件 */
	private SysAttach 			attach;
	
	/** 关键字 */
	private String    			keyword;
	
	/** 页码 */
	private Integer   			page;
	
	/** 每页显示数量 */
	private Integer   			rows;
	
	/** 电子公文 */
	private OaDocFile 			file;
	
	/** 纸质公文*/
	private DocPaperArchives 	paperFile;
	
	/** 档案*/
	private DocArchives			archives;
	
	public LuceneEditBean() {
		super();
	}
	
	public LuceneEditBean(SysAttach attach, OaDocFile file, DocArchives archives) {
		super();
		this.attach = attach;
		this.file = file;
		this.archives = archives;
	}
	
	public LuceneEditBean(SysAttach attach, DocPaperArchives paperFile, DocArchives archives) {
		super();
		this.attach = attach;
		this.paperFile = paperFile;
		this.archives = archives;
	}
	
	public LuceneEditBean(SysAttach attach, OaDocFile file, DocPaperArchives paperFile, DocArchives archives) {
		super();
		this.attach = attach;
		this.file = file;
		this.paperFile = paperFile;
		this.archives = archives;
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

	public DocPaperArchives getPaperFile() {
		return paperFile;
	}

	public void setPaperFile(DocPaperArchives paperFile) {
		this.paperFile = paperFile;
	}

	public DocArchives getArchives() {
		return archives;
	}

	public void setArchives(DocArchives archives) {
		this.archives = archives;
	}
}
