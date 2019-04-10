package cn.com.chaochuang.doc.archive.service;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.beancopy.BeanCopyUtil;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.doc.archive.bean.DocArchivesDestroyEditBean;
import cn.com.chaochuang.doc.archive.domain.DocArchives;
import cn.com.chaochuang.doc.archive.domain.DocArchivesDestroy;
import cn.com.chaochuang.doc.archive.reference.ArchStatus;
import cn.com.chaochuang.doc.archive.repository.DocArchivesDestroyRepository;
import cn.com.chaochuang.doc.archive.repository.DocArchivesRepository;

/**
 * @author dengl 2017.11.30
 *
 */
@Service
@Transactional
public class DocArchivesDestroyServiceImpl extends SimpleUuidCrudRestService<DocArchivesDestroy> implements DocArchivesDestroyService{
	
	@Autowired
    private DocArchivesDestroyRepository		repository;
	
	@Autowired
	private DocArchivesRepository				docArchivesRepository;
    
	@Override
	public SimpleDomainRepository<DocArchivesDestroy, String> getRepository() {
		return this.repository;
	}

	@Override
	public String saveDestroyArch(DocArchivesDestroyEditBean bean) {
		DocArchivesDestroy destroy = null;
		if(StringUtils.isEmpty(bean.getIds())){//销毁单选
			destroy = BeanCopyUtil.copyBean(bean, DocArchivesDestroy.class);
			this.repository.save(destroy);
			if(StringUtils.isNotEmpty(destroy.getId())){//更新归档管理表中档案状态为销毁
				DocArchives archives = this.docArchivesRepository.findOne(destroy.getArchId());
				if(archives != null){
					archives.setArchStatus(ArchStatus.销毁);
					this.docArchivesRepository.save(archives);
				}
			}
			return destroy.getId();
		}else{//销毁多选
			String[] ids = bean.getIds().split(",");
			for(String archId : ids){
				DocArchives archives = this.docArchivesRepository.findOne(Long.valueOf(archId));
				destroy = BeanCopyUtil.copyBean(bean, DocArchivesDestroy.class);
				destroy.setArchId(Long.valueOf(archId));
				destroy.setFileId(archives.getFileId());
				this.repository.save(destroy);
				if(StringUtils.isNotEmpty(destroy.getId())){//更新归档管理表中档案状态为销毁
					archives.setArchStatus(ArchStatus.销毁);
					this.docArchivesRepository.save(archives);
				}
			}
			return destroy.getId();
		}
	}

}
