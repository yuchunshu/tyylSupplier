package cn.com.chaochuang.doc.archive.service;

import java.io.IOException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.doc.archive.bean.LuceneEditBean;

/**
 * 
 * @author dengl 2017.12.08
 *
 */
@Service
public class ArchivesSimpleLuceneFactory  extends ArchivesLuceneFactory implements InitializingBean {

	@Autowired
	private ArchivesLuceneService luceneService; 
	
	@Override
	public boolean writeIndex(LuceneEditBean bean) throws Exception {
		return this.luceneService.writeLuceneIndex(bean);
	}

	@Override
	public boolean removeIndex(LuceneEditBean bean) throws IOException {
		return this.luceneService.removeLuceneIndex(bean);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.setInstance(this);
	}

}
