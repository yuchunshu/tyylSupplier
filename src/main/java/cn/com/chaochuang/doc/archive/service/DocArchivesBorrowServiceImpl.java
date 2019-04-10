package cn.com.chaochuang.doc.archive.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.beancopy.BeanCopyUtil;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.doc.archive.bean.DocArchivesBorrowEditBean;
import cn.com.chaochuang.doc.archive.domain.DocArchives;
import cn.com.chaochuang.doc.archive.domain.DocArchivesBorrow;
import cn.com.chaochuang.doc.archive.reference.ArchStatus;
import cn.com.chaochuang.doc.archive.reference.BorrowStatus;
import cn.com.chaochuang.doc.archive.repository.DocArchivesBorrowRepository;
import cn.com.chaochuang.doc.archive.repository.DocArchivesRepository;

/**
 * @author dengl 2017.11.30
 *
 */
@Service
@Transactional
public class DocArchivesBorrowServiceImpl extends SimpleUuidCrudRestService<DocArchivesBorrow> implements DocArchivesBorrowService{
	
	@Autowired
    private DocArchivesBorrowRepository 		repository;
	
	@Autowired
	private DocArchivesRepository				docArchivesRepository;
	
	@Override
	public SimpleDomainRepository<DocArchivesBorrow, String> getRepository() {
		return this.repository;
	}

	@Override
	public String saveBorrowArch(DocArchivesBorrowEditBean bean) {
		DocArchivesBorrow borrow = null;
		if(StringUtils.isEmpty(bean.getIds())){//借阅单选
			borrow = BeanCopyUtil.copyBean(bean, DocArchivesBorrow.class);
			if(borrow.getHandleUserId() == null){
				borrow.setHandleUserId(Long.valueOf(UserTool.getInstance().getCurrentUserId()));
				borrow.setHandleUserName(UserTool.getInstance().getCurrentUserName());
			}
			borrow.setArchStatus(BorrowStatus.借阅);
			borrow.setCreateTime(new Date());
			this.repository.save(borrow);
			if(StringUtils.isNotEmpty(borrow.getId())){//更新归档管理表中档案状态为外借
				DocArchives archives = this.docArchivesRepository.findOne(borrow.getArchId());
				if(archives != null){
					archives.setArchStatus(ArchStatus.外借);
					this.docArchivesRepository.save(archives);
				}
			}
			return borrow.getId();
		}else{//借阅多选
			String[] ids = bean.getIds().split(",");
			for(String archId : ids){
				DocArchives archives = this.docArchivesRepository.findOne(Long.valueOf(archId));
				borrow = BeanCopyUtil.copyBean(bean, DocArchivesBorrow.class);
				borrow.setArchId(Long.valueOf(archId));
				borrow.setFileId(archives.getFileId());
				if(borrow.getHandleUserId() == null){
					borrow.setHandleUserId(Long.valueOf(UserTool.getInstance().getCurrentUserId()));
					borrow.setHandleUserName(UserTool.getInstance().getCurrentUserName());
				}
				borrow.setArchStatus(BorrowStatus.借阅);
				borrow.setCreateTime(new Date());
				this.repository.save(borrow);
				if(StringUtils.isNotEmpty(borrow.getId())){//更新归档管理表中档案状态为外借
					archives.setArchStatus(ArchStatus.外借);
					this.docArchivesRepository.save(archives);
				}
			}
			return borrow.getId();
		}
	}

}
