package cn.com.chaochuang.doc.archive.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.repository.SysAttachRepository;
import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.common.beancopy.BeanCopyUtil;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.archive.bean.DocPaperArchivesEditBean;
import cn.com.chaochuang.doc.archive.bean.LuceneEditBean;
import cn.com.chaochuang.doc.archive.domain.DocArchives;
import cn.com.chaochuang.doc.archive.domain.DocArchivesBasic;
import cn.com.chaochuang.doc.archive.domain.DocPaperArchives;
import cn.com.chaochuang.doc.archive.reference.ArchStatus;
import cn.com.chaochuang.doc.archive.reference.BasicType;
import cn.com.chaochuang.doc.archive.reference.FileType;
import cn.com.chaochuang.doc.archive.repository.DocArchivesBasicRepository;
import cn.com.chaochuang.doc.archive.repository.DocArchivesRepository;
import cn.com.chaochuang.doc.archive.repository.DocPaperArchivesRepository;

/**
 * @author dengl 2017.11.24
 *
 */
@Service
@Transactional
public class DocPaperArchivesServiceImpl extends SimpleUuidCrudRestService<DocPaperArchives> implements DocPaperArchivesService{
	
	@Autowired
    private DocPaperArchivesRepository   	repository;
	
	@Autowired
	private DocArchivesRepository			docArchivesRepository;
	
	@Autowired
	private SysAttachService				attachService;
	
	@Autowired
	private DocArchivesBasicRepository 		archivesBasicRepository;
	
	@Autowired
	private SysAttachRepository				attachRepository;

	@Override
	public SimpleDomainRepository<DocPaperArchives, String> getRepository() {
		return this.repository;
	}

	@Override
	public String saveDocPaperArchives(DocPaperArchivesEditBean bean) {
		DocPaperArchives paper = null;
		DocArchives archives = null;
		if(bean != null && StringUtils.isNotEmpty(bean.getTitle())){
			paper = new DocPaperArchives();
			archives = new DocArchives();
			paper.setTitle(bean.getTitle());
			paper.setDocCode(bean.getDocCode());
			paper.setSncode(bean.getSncode());
			paper.setDense(bean.getDense());
			paper.setUrgencyLevel(bean.getUrgencyLevel());
			paper.setCreateDate(bean.getPaperCreateDate());
			paper.setCreatorName(bean.getCreatorName());
			this.repository.save(paper);
			String[] attachArray = null;
			if(StringUtils.isNotBlank(bean.getAttach())){
				attachArray = bean.getAttach().split(",");
				if (Tools.isNotEmptyArray(attachArray)) {
					for (String id : attachArray) {
						SysAttach attach = this.attachService.findOne(id);
						attach.setOwnerId(paper.getId());
						attach.setOwnerType(DocPaperArchives.class.getSimpleName());
						this.attachService.persist(attach);
					}
				}
			}
			if(StringUtils.isNotEmpty(paper.getId())){
				archives = BeanCopyUtil.copyBean(bean, DocArchives.class);
				if (archives.getDepId() == null) {
		            archives.setDepId(Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));
		        }
				archives.setFileType(FileType.纸质);
				archives.setFileId(paper.getId());
				archives.setArchStatus(ArchStatus.在库);
				if (StringUtils.isNotEmpty(archives.getCounterNo())) {// 获取柜位号名称
					List<DocArchivesBasic> counterNoName = this.archivesBasicRepository.findByBasicCodeAndBasicTypeAndDepId(
							archives.getCounterNo(), BasicType.档案柜,
							Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));
					if (counterNoName.size() > 0 && counterNoName != null) {
						archives.setCounterNoName(counterNoName.get(0).getBasicName());
					}
				}
				if (StringUtils.isNotEmpty(archives.getArchType())) {// 获取案卷类型名称
					List<DocArchivesBasic> archTypeName = this.archivesBasicRepository.findByBasicCodeAndBasicTypeAndDepId(
							archives.getArchType(), BasicType.案卷类型,
							Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));
					if(archTypeName.size() > 0 && archTypeName != null){
						archives.setArchTypeName(archTypeName.get(0).getBasicName());
					}
				}
				if (StringUtils.isNotEmpty(archives.getMediumType())) {// 获取介质类型名称
					List<DocArchivesBasic> mediumTypeName = this.archivesBasicRepository.findByBasicCodeAndBasicTypeAndDepId(
							archives.getMediumType(), BasicType.介质类型,
							Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));
					if(mediumTypeName.size() > 0 && mediumTypeName != null){
						archives.setMediumTypeName(mediumTypeName.get(0).getBasicName());
					}
				}
		        this.docArchivesRepository.save(archives);
		        //纸质公文归档成功后，保存档案检索索引
		        if (archives.getId() != null) {
					List<SysAttach> attachList = null;
					if(StringUtils.isNotEmpty(paper.getId())){
						attachList = this.attachRepository.findByOwnerId(paper.getId());
						for(SysAttach attach : attachList){
							LuceneEditBean info = new LuceneEditBean(attach, paper, archives);
							try {
								ArchivesLuceneFactory.getInstance().writeIndex(info);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
		        }
			}
		}
		return paper.getId();
	}
	
}
