package cn.com.chaochuang.doc.lucene.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import cn.com.chaochuang.common.bean.EasyUIPage;
import cn.com.chaochuang.doc.lucene.bean.LuceneInfo;

/**
 * 
 * @author LJX
 *
 */
public interface LuceneService {
	/**
	 * 全文检索
	 * @param info
	 * @return
	 */
	EasyUIPage searchLuceneIndex(LuceneInfo info, HttpServletRequest request);
	/**
	 * 构建索引
	 * @param info
	 * @return
	 * @throws Exception
	 */
	boolean writeLuceneIndex(LuceneInfo info) throws Exception;
	/**
	 * 删除索引
	 * @param info
	 * @return
	 * @throws IOException
	 */
	boolean removeLuceneIndex(LuceneInfo info) throws IOException;

}
