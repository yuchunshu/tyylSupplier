package cn.com.chaochuang.doc.archive.service;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.doc.archive.bean.DocArchivesDestroyEditBean;
import cn.com.chaochuang.doc.archive.domain.DocArchivesDestroy;

/**
 * @author dengl 2017.11.30
 *
 */
public interface DocArchivesDestroyService extends CrudRestService<DocArchivesDestroy,String> {
	
	/** 保存销毁信息*/
	String saveDestroyArch(DocArchivesDestroyEditBean bean);
}
