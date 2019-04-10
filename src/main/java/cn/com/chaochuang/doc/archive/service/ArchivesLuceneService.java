package cn.com.chaochuang.doc.archive.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import cn.com.chaochuang.common.bean.EasyUIPage;
import cn.com.chaochuang.doc.archive.bean.LuceneEditBean;

/**
 * @author dengl 2017.12.07
 *
 */
public interface ArchivesLuceneService {
	
	/**
	 * 公文档案检索
	 * @param info
	 * @return
	 */
	EasyUIPage archiveSearchLuceneIndex(LuceneEditBean bean, HttpServletRequest request);
	
	/**
	 * 构建索引
	 * @param info
	 * @return
	 * @throws Exception
	 */
	boolean writeLuceneIndex(LuceneEditBean bean) throws Exception;
	
	/**
	 * 删除索引
	 * @param info
	 * @return
	 * @throws IOException
	 */
	boolean removeLuceneIndex(LuceneEditBean bean) throws IOException;
	
}
