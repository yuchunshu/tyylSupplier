package cn.com.chaochuang.doc.lucene.service;

import java.io.IOException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.doc.lucene.bean.LuceneInfo;

/**
 * 
 * @author LJX
 *
 */
@Service
public class SimpleLuceneFactory  extends LuceneFactory implements InitializingBean {

	@Autowired
	private LuceneService luceneService; 
	
	@Override
	public boolean writeIndex(LuceneInfo info) throws Exception {
		return this.luceneService.writeLuceneIndex(info);
	}

	@Override
	public boolean removeIndex(LuceneInfo info) throws IOException {
		return this.luceneService.removeLuceneIndex(info);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.setInstance(this);
	}

}
