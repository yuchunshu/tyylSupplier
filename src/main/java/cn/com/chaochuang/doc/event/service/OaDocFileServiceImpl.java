/*
 * FileName:    OaDocFileService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.event.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import cn.com.chaochuang.common.attach.repository.SysAttachRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.common.reference.ReadStatus;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.countersign.service.CountersignService;
import cn.com.chaochuang.doc.event.bean.FileShowBean;
import cn.com.chaochuang.doc.event.bean.OaDocFileEditBean;
import cn.com.chaochuang.doc.event.bean.OaDocFileQueryBean;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.event.reference.EmergencyLevelType;
import cn.com.chaochuang.doc.event.reference.FileStatusFlag;
import cn.com.chaochuang.doc.event.reference.UrgencyLevelType;
import cn.com.chaochuang.doc.event.repository.OaDocFileRepository;
import cn.com.chaochuang.doc.lucene.bean.LuceneInfo;
import cn.com.chaochuang.doc.lucene.service.LuceneFactory;
import cn.com.chaochuang.doc.process.service.DocProcessService;
import cn.com.chaochuang.doc.readmatter.domain.ReadMatter;
import cn.com.chaochuang.doc.readmatter.service.ReadMatterService;
import cn.com.chaochuang.oa.message.app.reference.MesType;
import cn.com.chaochuang.oa.message.app.service.AppMessageService;
import cn.com.chaochuang.workflow.def.domain.WfNode;
import cn.com.chaochuang.workflow.def.service.WfNodeService;
import cn.com.chaochuang.workflow.inst.domain.WfFlowInst;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.inst.service.WfFlowInstService;
import cn.com.chaochuang.workflow.inst.service.WfNodeInstService;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfInstStatus;
import cn.com.chaochuang.workflow.util.WorkflowUtils;
import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.common.bean.ReturnBean;

/**
 * @author huangwq
 *
 */
@Service
@Transactional
public class OaDocFileServiceImpl extends SimpleUuidCrudRestService<OaDocFile> implements OaDocFileService {

    @Autowired
    private SysUserService         userService;

    @Autowired
    private OaDocFileRepository    repository;

    @Autowired
    private ReadMatterService      readMatterService;

    @Autowired
    private CountersignService     countersignService;

    @Autowired
    protected ConversionService    conversionService;

    @Autowired
    protected DocProcessService    docProcessService;

    @Autowired
    private SysAttachService       attachService;
    @Autowired
    private SysAttachRepository    attachRepository;

    @Autowired
    private WfFlowInstService      flowInstService;

    @Autowired
    private WfNodeInstService      nodeInstService;
    
    @Autowired
    private AppMessageService      appMessageService;
    
    @Autowired
    private LogService             logService;
    
    @Autowired
    private WfNodeService 		   nodeService;
    
    @Override
    public SimpleDomainRepository<OaDocFile, String> getRepository() {
        return this.repository;
    }

    @Override
    public void save(OaDocFile obj) {
        this.repository.save(obj);
    }

    @Override
    public ReturnBean saveDocFile(OaDocFileEditBean bean) {
        OaDocFile docFile = null;
        int version = 0;

        boolean isNew = (StringUtils.isBlank(bean.getId()));

        SysUser creator = this.userService.findOne(bean.getCreatorId());
        if (!isNew) {
        	docFile = this.repository.findOne(bean.getId());
        	//保护一些信息不被修改
        	bean.setCreateDate(docFile.getCreateDate());
        	bean.setCreatorId(docFile.getCreatorId());
        	bean.setCreatorName(docFile.getCreatorName());
        	if (docFile.getVersion_() != null) {
        		version = docFile.getVersion_();
        	}
        } else {
        	docFile = new OaDocFile();
        	if (bean.getStatus() == null) {
        		bean.setStatus(FileStatusFlag.暂存);
        		bean.setCreateDate(new Date());
        	}
        	bean.setVersion_(0);
        }

        if(version != bean.getVersion_().intValue()) {
        	//如果版本号不一致，说明有冲突
        	return new ReturnBean("数据冲突，有其它人员同时对数据进行操作，请重新打开页面进行操作！", docFile, false);
        }

        if (!isNew) {
            docFile = this.repository.findOne(bean.getId());
        } else {
            docFile = new OaDocFile();
            bean.setFileYear(Tools.DATE_FORMAT7.format(new Date()));
        }
        // 生成新的自然编号
        if (Tools.isEmptyString(docFile.getSncode())) {
        	
        	//没有设置自然编号类型，默认为yyyy-nnnn
        	bean.setSncodeType(bean.getSncodeType()==null?"1":bean.getSncodeType());
            // 查询最大收文编号
            String maxSncode = this.repository.selectMaxSncodeByFileYearAndSncodeType(Tools.DATE_FORMAT7.format(new Date()), bean.getSncodeType());
            String sncode = Tools.getNextScnode(maxSncode, bean.getSncodeType());
            bean.setSncode(sncode);

        }else{
        	bean.setSncode(docFile.getSncode());
        }
        
        // 文电编号名称为空时不写
        if (!Tools.isEmptyString(bean.getDocCode())
                        && Tools.isEmptyString(bean.getDocCode().substring(0, bean.getDocCode().indexOf("[")))) {
            bean.setDocCode(null);
        }

        // 允许编辑公文信息：新增或设置了可编辑标识
        if (isNew || YesNo.是.equals(bean.getDocEditable())) {
            // docFile = BeanCopyUtil.copyBean(bean, OaDocFile.class);
            BeanUtils.copyProperties(bean, docFile);
            if (StringUtils.isNotBlank(bean.getFlowId())) {
                bean.setFlowId(bean.getFlowId());
            }
            docFile.setCreaterDept(creator.getDepartment());
            docFile.setCreatorId(creator.getId());
            docFile.setCreatorName(creator.getUserName());
            docFile.setVersion_(version + 1);
            if (docFile.getUrgencyLevel() == null) {
            	if(bean.getEmergencyLevel()!=null){
            		if(EmergencyLevelType.特急.equals(bean.getEmergencyLevel())){
            			docFile.setUrgencyLevel(UrgencyLevelType.特急);
            		}else if(EmergencyLevelType.急件.equals(bean.getEmergencyLevel())){
            			docFile.setUrgencyLevel(UrgencyLevelType.急件);
            		}else{
            			docFile.setUrgencyLevel(UrgencyLevelType.无);
            		}
            	}else{
            		docFile.setUrgencyLevel(UrgencyLevelType.无);
            	}
            }
            // FileStatusFlag fileStatus = bean.getStatus();
            // 时间设置
            Date date = new Date();
            String dateString = Tools.DATE_MINUTE_FORMAT.format(date);
            try {
                date = Tools.DATE_MINUTE_FORMAT.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (bean.getExpiryDate() == null) {
                // docFile.setExpiryDate(date + bean.getLimitDay());
            }

            if (docFile.getCreateDate() == null) {
                docFile.setCreateDate(date);
            }
        }

        String attachIds = bean.getAttach();
        if (StringUtils.isNotBlank(attachIds)) {
            docFile.setAttachFlag(AttachFlag.有附件);
        } else {
            docFile.setAttachFlag(AttachFlag.无附件);
        }

//        List<SysAttach> oldDocAttachs = null;
//        if (!isNew) {
//	        // 查找正文文件
//	        oldDocAttachs = this.attachService.findByOwnerIdAndOwnerType(bean.getId(), OaDocFile.DOC_ATTACH_OWNER_MAIN);
//        } else {
//        	oldDocAttachs = new ArrayList<SysAttach>();
//        }

        List<String> oldIdsForDel = new ArrayList<String>();
        if (!isNew) {
            // 查询旧的附件id
            List<SysAttach> oldAttachs = this.attachService.findByOwnerIdAndOwnerType(bean.getId(),
                            OaDocFile.class.getSimpleName());
            if (oldAttachs != null) {
                for (SysAttach a : oldAttachs) {
                    oldIdsForDel.add(a.getId().toString());
                }
            }
        }

        OaDocFile doc = this.repository.save(docFile);
        bean.setId(doc.getId());

        // 保存正文
        SysAttach attach = null;
        if (StringUtils.isNotBlank(bean.getDocId())) {
            attach = attachService.findOne(bean.getDocId());
            if(attach!=null) {
                doc.setDocId(attach.getId());
                this.persist(doc);
            }
        }
        if(attach==null){
            doc.setDocId(null);
            this.persist(doc);
        }

        if (attachIds != null) {
            String[] idArray = StringUtils.split(attachIds, ",");
            String ownerId = bean.getId();
            for (String aIdStr : idArray) {
                this.attachService.linkAttachAndOwner(aIdStr, ownerId, OaDocFile.class.getSimpleName());
                if (oldIdsForDel.contains(aIdStr))
                    oldIdsForDel.remove(aIdStr);
            }
        }
        // 删除剩余的
        if (oldIdsForDel.size() > 0) {
            for (String delAttachId : oldIdsForDel) {
                this.attachService.deleteAttach(delAttachId);
            }
        }

        // 加入全文检索
        if (!FileStatusFlag.暂存.equals(doc.getStatus())) {
            this.saveLuceneIndex(doc);
        }

        return new ReturnBean("保存成功", doc, true);
    }


    @Override
	public ReturnBean updateDocFileInfo(OaDocFileEditBean bean, HttpServletRequest request) {
    	if (StringUtils.isBlank(bean.getId())) return null;

        OaDocFile docFile = this.repository.findOne(bean.getId());
        if (docFile == null) {
        	return new ReturnBean("该公文不存在，请重新进入页面进行操作！");
        }

        int curVersion = docFile.getVersion_().intValue();

        if(curVersion != bean.getVersion_().intValue()) {
        	//如果版本号不一致，说明有冲突
        	return new ReturnBean("数据冲突，有其它人员同时对数据进行操作，请重新打开页面进行操作！", docFile, false);
        }

        if (StringUtils.isNotBlank(bean.getTitle()) && null != bean.getUrgencyLevel() && null != bean.getExpiryDate()) {
        	docFile.setTitle(bean.getTitle());
            //修改公文标题、紧急程度、限办时间的时候同时修改流程实例的标题、紧急程度、限办时间
            List<WfFlowInst> list = flowInstService.findByEntityId(bean.getId());
            for(WfFlowInst inst: list) {
            	inst.setTitle(bean.getTitle());
            	inst.setUrgencyLevel(bean.getUrgencyLevel().getKey());
            	inst.setExpiryDate(bean.getExpiryDate());
                flowInstService.getRepository().save(inst);
            }
        }else if (StringUtils.isNotBlank(bean.getTitle())){//内部请示
        	docFile.setTitle(bean.getTitle());
            //修改公文标题的时候同时修改流程实例的标题
            List<WfFlowInst> list = flowInstService.findByEntityId(bean.getId());
            for(WfFlowInst inst: list) {
            	inst.setTitle(bean.getTitle());
                flowInstService.getRepository().save(inst);
            }
        }

        docFile.setVersion_(curVersion + 1);
        if (bean.getCopies() != null) docFile.setCopies(bean.getCopies());
        if (StringUtils.isNotBlank(bean.getDocCode())) docFile.setDocCode(bean.getDocCode());
        if (StringUtils.isNotBlank(bean.getSendUnit())) docFile.setSendUnit(bean.getSendUnit());
        if (StringUtils.isNotBlank(bean.getSuggestion())) docFile.setSuggestion(bean.getSuggestion());
        if (null != bean.getUrgencyLevel()) docFile.setUrgencyLevel(bean.getUrgencyLevel());
        if (null != bean.getDense()) docFile.setDense(bean.getDense());
        if (null != bean.getOpenFlag()) docFile.setOpenFlag(bean.getOpenFlag());
        if (null != bean.getDocCate()) docFile.setDocCate(bean.getDocCate());
        if (null != bean.getLimitDay()) docFile.setLimitDay(bean.getLimitDay());
        if (null != bean.getExpiryDate()) docFile.setExpiryDate(bean.getExpiryDate());
        if (null != bean.getDeadlineType()) docFile.setDeadlineType(bean.getDeadlineType());
        if (null != bean.getDocumentType()) docFile.setDocumentType(bean.getDocumentType());
        if (StringUtils.isNotBlank(bean.getMainSend())) docFile.setMainSend(bean.getMainSend());
        if (StringUtils.isNotBlank(bean.getTypist())) docFile.setTypist(bean.getTypist());
        if (StringUtils.isNotBlank(bean.getKeyword())) docFile.setKeyword(bean.getKeyword());
        docFile.setReportSend(bean.getReportSend());
        docFile.setCopySend(bean.getCopySend());
        if(StringUtils.isNotBlank(bean.getRemark()))docFile.setRemark(bean.getRemark());
        docFile.setDigest(bean.getDigest());
        //docFile.setXXX(...);

        //查询当前环节定义，查看是否有编辑附件的权限
        WfNode node = nodeService.getByNodeId(bean.getCurNodeId());
    	//移动端不处理附件
        if (!bean.isFromMobile() && YesNo.是.equals(node.getDocEditable())) {
        	String attachIds = bean.getAttach();
            if (StringUtils.isNotBlank(attachIds)) {
                docFile.setAttachFlag(AttachFlag.有附件);
            } else {
                docFile.setAttachFlag(AttachFlag.无附件);
            }

            List<String> oldIdsForDel = new ArrayList<String>();
            // 查询旧的附件id
            List<SysAttach> oldAttachs = this.attachRepository.findByOwnerIdAndOwnerTypeIn(bean.getId(),
                            new String[]{OaDocFile.class.getSimpleName(),OaDocFile.DOC_ATTACH_OWNER_MAIN_PDF});
            if (oldAttachs != null) {
                for (SysAttach a : oldAttachs) {
                    oldIdsForDel.add(a.getId().toString());
                }
            }
            //判断公文正文附件是否发生变化，如果发生变化则写入日志。
            if(StringUtils.isNotBlank(docFile.getDocId())){
            	SysAttach mainAttach = attachService.findOne(docFile.getDocId());
            	if(mainAttach != null && !docFile.getDocId().equals(bean.getDocId())){
            		logService.saveLog(SjType.普通操作, "公文《" + docFile.getTitle() + "》，正文附件名称：《"+ mainAttach.getTrueName() +"》删除成功！",LogStatus.成功, request);
            	}
            }

            // 保存正文
            SysAttach attach = null;
            if (StringUtils.isNotBlank(bean.getDocId())) {
                attach = attachService.findOne(bean.getDocId());
                if(attach!=null) {
                    docFile.setDocId(attach.getId());
                    this.persist(docFile);
                }
            }
            if(attach==null){
                docFile.setDocId(null);
                this.persist(docFile);
            }

            if (attachIds != null) {
                String[] idArray = StringUtils.split(attachIds, ",");
                String ownerId = bean.getId();
                for (String aIdStr : idArray) {
                    this.attachService.linkAttachAndOwner(aIdStr, ownerId, OaDocFile.class.getSimpleName());
                    if (oldIdsForDel.contains(aIdStr))
                        oldIdsForDel.remove(aIdStr);
                }
            }
            
            
            // 删除剩余的
            if (oldIdsForDel.size() > 0) {
                for (String delAttachId : oldIdsForDel) {
                	String attachName = attachService.findOne(delAttachId).getTrueName();
                    this.attachService.deleteAttach(delAttachId);
                    logService.saveLog(SjType.普通操作, "公文《" + docFile.getTitle() + "》，附件名称：《"+ attachName +"》删除成功！",LogStatus.成功, request);
                }
            }
        }

        this.persist(docFile);

        return new ReturnBean("公文信息更新成功！", docFile, true);

	}

	@Override
    public OaDocFile findByProcInstId(String procInstId) {
        return this.repository.findByFlowInstId(procInstId);
    }

    @Override
    public boolean delDocFile(String id) {
        return this.delDocFile(id, false);
    }

    @Override
    public boolean delDocFile(String id, boolean force) {
        if (id != null) {
            OaDocFile obj = repository.findOne(id);
            if (obj != null) {
                // 只有强制标志=ture或暂存状态下，才能删除
                if (force || FileStatusFlag.暂存.equals(obj.getStatus())) {
                    // 删除DocProcess
                    docProcessService.delDocProcess(obj);
                    // 删除相关阅知
                    readMatterService.deleteReadMatterByFileId(obj.getId());
                    this.repository.delete(obj);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public List selectReceiveType() {
        return null;
    }

    @Override
    public void saveReadMatter(String userId, FileShowBean bean) {
        SysUser creator = (SysUser) UserTool.getInstance().getCurrentUser();
        String[] reads = userId.split(",");
        Date now = new Date();
        for (String r : reads) {
            if (!Tools.isEmptyString(r) && !"undefined".equals(r)) {
                ReadMatter rm = new ReadMatter();
                rm.setFileId(bean.getId());
                rm.setSendUser(creator);
                rm.setSendTime(now);
                rm.setStatus(ReadStatus.未阅);
                rm.setTitle(bean.getTitle());
                rm.setFileType(bean.getFileType());
                rm.setTaskId(bean.getCurTaskId());
                rm.setReadMan(Long.valueOf(r));
                this.readMatterService.getRepository().save(rm);
            }
        }
    }

    public void finishTheDoc(String id) {
        OaDocFile obj = this.repository.findOne(id);
        if (obj != null) {
            obj.setStatus(FileStatusFlag.待归档);
            this.repository.save(obj);

            //公文办结后，保存全文检索索引
            SysAttach attach = null;
            if (StringUtils.isNotBlank(obj.getDocId())) {
                attach = this.attachService.findOne(obj.getDocId());
            }
            LuceneInfo info = new LuceneInfo(obj, attach);
            try {
                LuceneFactory.getInstance().writeIndex(info);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void resetTheFlowDoc(String procInstId) {
        OaDocFile obj = this.findByProcInstId(procInstId);
        if (obj == null)
            return;

        // 删除DocProcess
        docProcessService.delDocProcess(obj);

        // 删除相关阅知
        readMatterService.deleteReadMatterByFileId(obj.getId());

        // 删除会签
        countersignService.deleteDocCountersignByFileId(obj.getId());

        // // 删除意见
        // docOpinionService.deleteOpinionsByFileId(obj.getId());

        // 删除附件

        // // 删除环节权限
        // nodeSettingService.deleteNodeSetting(obj.getFlowId());

        obj.setStatus(FileStatusFlag.暂存);
        obj.setFlowId(null);
        obj.setFlowInstId(null);
        this.repository.save(obj);
    }

    @Override
    public List<OaDocFile> selectDocFile(String id, HttpServletRequest request, Integer page, Integer rows) {
        SearchBuilder<OaDocFile, String> searchBuilder = new SearchBuilder<OaDocFile, String>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.getFilterBuilder().equal("id", id);
        SearchListHelper<OaDocFile> listhelper = new SearchListHelper<OaDocFile>();
        listhelper.execute(searchBuilder, this.getRepository(), page, rows);
        return listhelper.getList();
    }


    @Override
    public List<OaDocFile> selectFlowDocByProcDefId(String procDefId) {
        return this.repository.findByFlowId(procDefId);
    }

    @Override
    public String generateNewFileCode(WfBusinessType fileType, String fileYear) {
        if (fileType != null && StringUtils.isNotBlank(fileYear)) {
            Integer maxCode = repository.selectMaxSncodeByFileTypeAndFileYear(fileType, fileYear);
            if (maxCode != null) {
                // 收文：B20161129-1
                return (WfBusinessType.收文.equals(fileType) ? "B" : "F") + fileYear + "-"
                                + new Integer(maxCode.intValue() + 1).toString();
            } else {
                return (WfBusinessType.收文.equals(fileType) ? "B" : "F") + fileYear + "-1";
            }
        }
        return "";
    }

    /**
     * 生成查询条件
     *
     * @param bean
     * @return
     */
    private Specification<OaDocFile> getWhereClause(final OaDocFileQueryBean bean) {
        return new Specification<OaDocFile>() {

            @Override
            public Predicate toPredicate(Root<OaDocFile> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();

                if (StringUtils.isNotBlank(bean.getFlowId())) {
                    predicate.getExpressions().add(cb.equal(root.get("flowId"), bean.getFlowId()));
                }
                if (StringUtils.isNotBlank(bean.getFlowInstId())) {
                    predicate.getExpressions().add(cb.equal(root.get("flowInstId"), bean.getFlowInstId()));
                }
                if (bean.getFileType() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("fileType"), bean.getFileType()));
                }
                if (StringUtils.isNotBlank(bean.getTitle())) {
                    predicate.getExpressions().add(cb.like(root.get("title").as(String.class), bean.getTitle()));
                }
                if (StringUtils.isNotBlank(bean.getDocCode())) {
                    predicate.getExpressions().add(cb.like(root.get("docCode").as(String.class), bean.getDocCode()));
                }
                if (StringUtils.isNotBlank(bean.getSncode())) {
                    predicate.getExpressions().add(cb.like(root.get("sncode").as(String.class), bean.getSncode()));
                }
                if (StringUtils.isNotBlank(bean.getFileCode())) {
                    predicate.getExpressions().add(cb.like(root.get("fileCode").as(String.class), bean.getFileCode()));
                }
                if (StringUtils.isNotBlank(bean.getSendUnit())) {
                    predicate.getExpressions().add(cb.equal(root.get("sendUnit"), bean.getSendUnit()));
                }
                if (bean.getOpenFlag() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("openFlag"), bean.getOpenFlag()));
                }
                if (bean.getUrgencyLevel() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("urgencyLevel"), bean.getUrgencyLevel()));
                }
                if (bean.getStatus() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("status"), bean.getStatus()));
                }
                if (bean.getCreatorId() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("creatorId"), bean.getCreatorId()));
                }
                if (bean.getCreatorDepId() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("creatorDepId"), bean.getCreatorDepId()));
                }
                if (bean.getArchId() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("archId"), bean.getArchId()));
                }
                if (bean.getDocCate() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("docCate"), bean.getDocCate()));
                }
                if (bean.getDense() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("dense"), bean.getDense()));
                }

                if (bean.getCreateDateBegin() != null) {
                    predicate.getExpressions().add(
                                    cb.greaterThanOrEqualTo(root.get("createDate").as(Date.class),
                                                    bean.getCreateDateBegin()));
                }
                if (bean.getCreateDateEnd() != null) {
                    predicate.getExpressions()
                                    .add(cb.lessThanOrEqualTo(root.get("createDate").as(Date.class),
                                                    bean.getCreateDateEnd()));
                }
                return predicate;
            }

        };
    }

    @Override
    public List<OaDocFile> selectDocFiles(OaDocFileQueryBean bean) {
        return repository.findAll(getWhereClause(bean));
    }

    @Override
    public Page<OaDocFile> selectDocFilesPage(OaDocFileQueryBean bean, Integer page, Integer rows,String sort,String order) {
    	Sort sort1 = null;
        
        if(StringUtils.isNotBlank(sort)){
        	sort = sort.replace("Show", "");
        	sort1 = new Sort("asc".equals(order)? Direction.ASC:Direction.DESC, sort);
        }
        else{
        	sort1 = new Sort(Direction.DESC, "createDate");
        }
        
        PageRequest pagable = new PageRequest(page - 1, rows, sort1);
        return repository.findAll(getWhereClause(bean), pagable);
    }

    @Override
    public Integer selectCountByDocCodeAndFileTypeExpId(String docCode, WfBusinessType fileType, String id) {
    	Integer c = 0;
    	if (StringUtils.isNotBlank(id)){
    		c = repository.selectCountByDocCodeAndFileTypeExpId(docCode, fileType, id, FileStatusFlag.暂存);
    	}else{
    		c = repository.selectCountByDocCodeAndFileType(docCode, fileType, FileStatusFlag.暂存);
    	}
    	
        return c;
    }

    @Override
    public void saveLuceneIndex(OaDocFile file) {
        if (file != null) {
            SysAttach attach = null;
            if (StringUtils.isNotBlank(file.getDocId())) {
                attach = this.attachService.findOne(file.getDocId());
            }
            if(null != attach){
            	LuceneInfo info = new LuceneInfo(file, attach);
	        	try {
	        		LuceneFactory.getInstance().writeIndex(info);
	        	} catch (Exception e) {
	                 e.printStackTrace();
        		}
            }
           
        }
    }

    @Override
    public void removeLuceneIndex(OaDocFile file) {
        if (file != null) {
            SysAttach attach = null;
            if (StringUtils.isNotBlank(file.getDocId())) {
                attach = this.attachService.findOne(file.getDocId());
            }
            LuceneInfo info = new LuceneInfo(file, attach);
            try {
                LuceneFactory.getInstance().removeIndex(info);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

	@Override
	public void docMove(String nodeInstId, String flowInstId, Long userId) {
		
		WfFlowInst flowInst = flowInstService.findOne(flowInstId);
		if (flowInst == null) {
            throw new RuntimeException("操作失败！无法找到流程实例。");
        } else if (WfInstStatus.办结.equals(flowInst.getInstStatus())) {
        	throw new RuntimeException("操作失败！该流程已办结。");
        }
		
		WfNodeInst nodeInst = this.nodeInstService.findOne(nodeInstId);
		nodeInst.setDealerId(userId);
		nodeInstService.getRepository().save(nodeInst);
		
		appMessageService.insertOaAppMsg(userId, nodeInst.getPreDealerId(), "公文《" + flowInst.getTitle()
        + "》等待您的办理", MesType.待办);
		
	}
	
	@Override
	public void docTransfer(String nodeInstId, String flowInstId, Long userId) {
		
		WfFlowInst flowInst = flowInstService.findOne(flowInstId);
		if (flowInst == null) {
			throw new RuntimeException("操作失败！无法找到流程实例。");
		} else if (WfInstStatus.办结.equals(flowInst.getInstStatus())) {
			throw new RuntimeException("操作失败！该流程已办结。");
		}
		
		WfNodeInst nodeInst = this.nodeInstService.findOne(nodeInstId);
		WfNodeInst nodeInstNew = new WfNodeInst();
		BeanUtils.copyProperties(nodeInst, nodeInstNew, new String[] { "id" });
		if (nodeInst == null) {
            throw new RuntimeException("操作失败！无法找到流程当前环节实例。");
        } else if (WfInstStatus.办结.equals(nodeInst.getInstStatus())) {
        	throw new RuntimeException("操作失败！该环节已办结。");
        }
		
		Date now = new Date();
		//当前流程环节实例：环节改为办理转移，且已办结
		nodeInst.setDealTime(now);
		nodeInst.setInstStatus(WfInstStatus.办结);
		nodeInst.setCurNodeId(WorkflowUtils.NODE_TRANSFER);
		nodeInstService.getRepository().save(nodeInst);
		
		//为办理转移人 新增 当前流程环节实例
		nodeInstNew.setDealerId(userId);
		nodeInstNew.setArrivalTime(now);
		nodeInstService.getRepository().save(nodeInstNew);
		
		appMessageService.insertOaAppMsg(userId, nodeInst.getPreDealerId(), "公文《" + flowInst.getTitle()
		+ "》等待您的办理", MesType.待办);
		
	}

	@Override
    public String createDocCode(String codeName, String year, WfBusinessType fileType, String id){
		List<OaDocFile> docList = null;
		if (StringUtils.isNotBlank(id)) {
			docList = repository.findByByDocCodeAndFileTypeExpId(fileType, id, FileStatusFlag.暂存);
		}else{
			docList = repository.findByByDocCodeAndFileType(fileType, FileStatusFlag.暂存);
		}
    	String docCodeName = null;
    	String docCode2 = null;
    	String docCode3 = null;
    	Integer docCodeNum = 0;//字号(docCode3)，遍历数据，取最大
    	Integer codeNum = 0;
    	for(OaDocFile d:docList){
    		String dc = d.getDocCode();
    		if (StringUtils.isNotBlank(dc)) {
    			dc = dc.trim();
                int i = dc.indexOf("[");
                int j = dc.indexOf("]");
                int k = dc.indexOf("号");
                try {
                	docCodeName = dc.substring(0, i);
                    docCode2 = dc.substring(i+1, j);
                    docCode3 = dc.substring(j+1, dc.length()-1);
                    codeNum = Integer.valueOf(docCode3);
                } catch (Exception e) {
                	e.printStackTrace();
                }
                if(codeName.equals(docCodeName) && year.equals(docCode2)){
                	docCodeNum = codeNum > docCodeNum ? codeNum : docCodeNum;
                }
            }
    	}

    	return String.valueOf(++docCodeNum);
    }
}
