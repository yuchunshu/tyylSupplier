package cn.com.chaochuang.doc.archive.service;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.doc.archive.bean.DocPaperArchivesEditBean;
import cn.com.chaochuang.doc.archive.domain.DocPaperArchives;
/**
 * @author dengl 2017.11.24
 *
 */
public interface DocPaperArchivesService extends CrudRestService<DocPaperArchives, String>{
	
	/** 保存纸质信息*/
	String saveDocPaperArchives(DocPaperArchivesEditBean bean);
}
