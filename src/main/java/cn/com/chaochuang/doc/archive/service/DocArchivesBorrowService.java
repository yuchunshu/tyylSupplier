package cn.com.chaochuang.doc.archive.service;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.doc.archive.bean.DocArchivesBorrowEditBean;
import cn.com.chaochuang.doc.archive.domain.DocArchivesBorrow;

/**
 * @author dengl 2017.11.30
 *
 */
public interface DocArchivesBorrowService extends CrudRestService<DocArchivesBorrow,String>{
	
	/** 保存借阅信息*/
	String saveBorrowArch(DocArchivesBorrowEditBean bean);
}
