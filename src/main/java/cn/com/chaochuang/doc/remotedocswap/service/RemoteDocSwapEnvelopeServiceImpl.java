/*
 * FileName:    RemoteDocSwapEnvelopeServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.repository.SysAttachRepository;
import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.tool.XmlTemplateUtil;
import cn.com.chaochuang.common.upload.support.UploadManager;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.AttachHelper;
import cn.com.chaochuang.common.util.AttachUtils;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.event.bean.OaDocFileEditBean;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.event.reference.DenseType;
import cn.com.chaochuang.doc.event.repository.OaDocFileRepository;
import cn.com.chaochuang.doc.expiredate.reference.DeadlineTypeConverter;
import cn.com.chaochuang.doc.remotedocswap.ConvectorUtils;
import cn.com.chaochuang.doc.remotedocswap.DocSwapTools;
import cn.com.chaochuang.doc.remotedocswap.bean.DocStatisticQueryData;
import cn.com.chaochuang.doc.remotedocswap.bean.DocSwapEnvelopeEditBean;
import cn.com.chaochuang.doc.remotedocswap.bean.DocSwapEnvelopeShowBean;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapAttach;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapConfig;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapContent;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapEnvelope;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapLog;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteUnitIdentifier;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteAttachType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteContentStatus;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapLogStatus;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapLogType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapTypeConverter;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvRecFlag;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvRecFlagConverter;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvStatus;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvStatusConverter;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteFeedBackType;
import cn.com.chaochuang.doc.remotedocswap.repository.RemoteDocSwapAttachRepository;
import cn.com.chaochuang.doc.remotedocswap.repository.RemoteDocSwapConfigRepository;
import cn.com.chaochuang.doc.remotedocswap.repository.RemoteDocSwapContentRepository;
import cn.com.chaochuang.doc.remotedocswap.repository.RemoteDocSwapEnvelopeRepository;
import cn.com.chaochuang.doc.remotedocswap.repository.RemoteDocSwapLogRepository;
import cn.com.chaochuang.doc.remotedocswap.schedule.ReceiveRemoteDocSwapSchedule;
import cn.com.chaochuang.doc.sms.bean.SmsBaseInfo;
import cn.com.chaochuang.doc.sms.service.SmsService;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.inst.service.WfNodeInstService;
import cn.com.chaochuang.workflow.reference.WfInstStatus;

/**
 * @author yuandl
 *
 */
@Service
@Transactional
public class RemoteDocSwapEnvelopeServiceImpl extends SimpleLongIdCrudRestService<RemoteDocSwapEnvelope> implements
                RemoteDocSwapEnvelopeService {

    /** 封体处理完后的存放路径 */
    @Value(value = "${remote.docswap.envelopeDonePath}")
    private String                          envelopeDonePath;

    /** 封首存放路径 */
    @Value(value = "${remote.docswap.envelopePath}")
    private String                       	envelopePath;
    
    /** 封体发送的存放路径 */
    @Value(value = "${remote.docswap.envelopeSendPath}")
    private String                          envelopeSendPath;

    /** 封首文件字段映射 */
    private Map<String, String>             envelopeFieldMap        = new HashMap<String, String>();

    /** 封体文件字段映射 */
    private Map<String, String>             envelopeContentFieldMap = new HashMap<String, String>();

    @Autowired
    private RemoteDocSwapEnvelopeRepository repository;
    
    @Autowired
    private RemoteDocSwapContentRepository  contentRepository;
    
    @Autowired
    private RemoteDocSwapAttachRepository   attachRepository;
    
    @Autowired
    private RemoteDocSwapLogRepository      logRepository;

    @Autowired
    private RemoteDocSwapContentService     contentService;

    @Autowired
    private RemoteUnitIdentifierService     unitIndentifierService;

    @Autowired
    private RemoteDocSwapAttachService      attachService;

    @Autowired
    private ConversionService               conversionService;
    
    @Autowired
    private SysAttachRepository             sysAttachRepository;
    
    @PersistenceContext
    private EntityManager           		em;
    
    @Autowired
    private RemoteDocSwapConfigService      configService;
    
    @Autowired
    private RemoteDocSwapConfigRepository   configRepository;
    
    @Autowired
    private DocStatisticService 	        docStatisticService;
    
    @Autowired
    private OaDocFileRepository 	        docFileRepository;
    
    @Autowired
    private WfNodeInstService 	       	 	nodeInstService;
    
    @Autowired
    private SmsService     				    smsService;
    
    public Map<String, String> getEnvelopeFieldMap() {
		return envelopeFieldMap;
	}

	public void setEnvelopeFieldMap(Map<String, String> envelopeFieldMap) {
		this.envelopeFieldMap = envelopeFieldMap;
	}

	public Map<String, String> getEnvelopeContentFieldMap() {
		return envelopeContentFieldMap;
	}

	public void setEnvelopeContentFieldMap(Map<String, String> envelopeContentFieldMap) {
		this.envelopeContentFieldMap = envelopeContentFieldMap;
	}

	@Override
    public SimpleDomainRepository<RemoteDocSwapEnvelope, Long> getRepository() {
        return this.repository;
    }

    @Override
    public boolean createRemoteDocSwap(DocSwapEnvelopeEditBean bean) {
        SysUser currentUser = (SysUser) UserTool.getInstance().getCurrentUser();
        RemoteDocSwapEnvelope envelope = null;
        RemoteDocSwapContent envelopeContent = null;
        if (bean.getId() == null) {
            // 生成远程公文交换文件包名:文件包名为UUID
            String packageName = UUID.randomUUID().toString();
            // 生成封首文件：envelope
            envelope = new RemoteDocSwapEnvelope();
            try {
                BeanUtils.copyProperties(bean, envelope);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            //根据天数计算时间，已在控制器计算日期，这里只是防止日期获取不到
    		if (bean.getLimitTime() == null && bean.getDeadlineDay() != null && bean.getDeadlineDay() > 0) {
    			Calendar calendar = Calendar.getInstance();
    			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + bean.getDeadlineDay());
    			bean.setLimitTime(calendar.getTime());
    		}
            envelope.setLimitTime(bean.getLimitTime());
            envelope.setEnvelopeType(RemoteEnvType.cooperate);
            envelope.setDeadlineType(bean.getDeadlineType());
            envelope.setEnvStatus(RemoteEnvStatus.未签收);
            envelope.setReceiveFlag(RemoteEnvRecFlag.发送);
            envelope.setSendTime(new Date());
            envelope.setSenderId(currentUser.getId());
            envelope.setSenderDept(currentUser.getDepartment().getId());
            envelope.setSenderName(currentUser.getUserName());
            envelope.setSenderAncestorName(currentUser.getDepartment().getParentDepartment().getDeptName());
            envelope.setSenderAncestorOrg(unitIndentifierService.getDefUnitIdentifier(null).getUnitIdentifier());
            envelope.setEnvelopeUuid(packageName);
            this.repository.save(envelope);

            bean.setId(envelope.getId());
            // 生成封内文件
            envelopeContent = new RemoteDocSwapContent();
            try {
                BeanUtils.copyProperties(bean, envelopeContent, new String[] { "id" });
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            
            envelopeContent.setLetterId(UUID.randomUUID().toString());
    		envelopeContent.setBussinessType(envelopeContent.getResourceIden());
            envelopeContent.setEnvelopeId(envelope.getId());
            envelopeContent.setSendOrg(unitIndentifierService.getUnitOrgFromIdentifier(unitIndentifierService
                            .getDefUnitIdentifier(null).getUnitIdentifier()));
            envelopeContent.setSendOrgName(unitIndentifierService.getDefUnitIdentifier(null).getUnitName());
            envelopeContent.setFeedbackOrg(unitIndentifierService.getUnitOrgFromIdentifier(bean
                            .getSecondaryUnitIdentifier()));
            envelopeContent.setEnvelopeUuid(packageName);
            envelopeContent.setSendOrgName(currentUser.getDepartment().getParentDepartment().getDeptName());
            envelopeContent.setConStatus(RemoteContentStatus.起始);
            envelopeContent.setFeedbackFlag("A");
            this.contentService.getRepository().save(envelopeContent);
        } else {
            envelope = this.repository.findOne(bean.getId());
            try {
                BeanUtils.copyProperties(bean, envelope, new String[] { "id" });
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            this.repository.save(envelope);
            // envelopeContent = this.selectRemoteDocSwapContent(bean.getEnvelopeId());
            envelopeContent = this.contentService.getByEnvelopeId(bean.getId());
            try {
                BeanUtils.copyProperties(bean, envelopeContent, new String[] { "id" });
                // 反馈方的组织机构码不能为空
                if (Tools.isEmptyString(envelopeContent.getFeedbackOrg())) {
                    envelopeContent.setFeedbackOrg(unitIndentifierService.getUnitOrgFromIdentifier(bean
                                    .getSecondaryUnitIdentifier()));
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            contentService.getRepository().save(envelopeContent);
        }

        //保存附件记录
        this.toSaveAttach(bean,envelopeContent);
		//生成xml文件
		this.documentToXml(envelope,envelopeContent);
        
        return true;
    }

    /** 
     * @Title: toSaveAttach 
     * @Description: 保存正文和附件信息
     * @param info
     * @param envelopeContent
     * @return: void
     */
    private void toSaveAttach(DocSwapEnvelopeEditBean info, RemoteDocSwapContent envelopeContent){
		 
    	// 保存.gw正文记录
		if (!Tools.isEmptyString(info.getGwFileId())) {
			Long attachId = new Long(info.getGwFileId());
			RemoteDocSwapAttach doc = attachService.findOne(attachId);
			doc.setRemoteDocSwapContentId(envelopeContent.getId());
			doc.setRemoteAttachType(RemoteAttachType.正文);
            attachService.getRepository().save(doc);
			//将正文信息返回给封内对象，以便后续使用
			envelopeContent.setGwFileDoc(doc);
        }
		
		// 保存.gd正文记录
		if (!Tools.isEmptyString(info.getGdFileId())) {
			Long attachId = new Long(info.getGdFileId());
			RemoteDocSwapAttach doc = attachService.findOne(attachId);
			doc.setRemoteDocSwapContentId(envelopeContent.getId());
			doc.setRemoteAttachType(RemoteAttachType.正文);
            attachService.getRepository().save(doc);
			//将正文信息返回给封内对象，以便后续使用
			envelopeContent.setGdFileDoc(doc);
		}
		
		// 保存.tif正文记录
		if (!Tools.isEmptyString(info.getTifFileId())) {
			Long attachId = new Long(info.getTifFileId());
			RemoteDocSwapAttach doc = attachService.findOne(attachId);
			doc.setRemoteDocSwapContentId(envelopeContent.getId());
			doc.setRemoteAttachType(RemoteAttachType.正文);
            attachService.getRepository().save(doc);
			 
			//转换pdf  this.convertTifAndSave方法
            RemoteDocSwapAttach pdfAttach;
			try {
//				RemoteDocSwapAttach doc_now = new RemoteDocSwapAttach();;
//				BeanUtils.copyProperties(doc, doc_now, new String[] { "id" });
//				doc_now.setSavePath(UploadManager.getInstance().getUploadRootPath() + "/" +doc_now.getSavePath() + doc_now.getSaveName());
				pdfAttach = this.convertTifAndSave(doc);
				//设置remoteAttachType为RemoteDocSwapAttach.ATTACH_TYPE_CNVDOC 保存pdf附件信息到 RemoteDocSwapEnvelope 表
				pdfAttach.setRemoteAttachType(RemoteAttachType.转换过的正文附件);
				pdfAttach.setRemoteDocSwapContentId(doc.getRemoteDocSwapContentId());
				attachService.getRepository().save(pdfAttach);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			//将正文信息返回给封内对象，以便后续使用
			envelopeContent.setTifFileDoc(doc);
		}
		
		// 保存附件记录
		if(info.getSwapFileAttachId()!=null){
			Set<RemoteDocSwapAttach> attachList = new HashSet<RemoteDocSwapAttach>();
			for(int i = 0; i < info.getSwapFileAttachId().length; i++){
				Long attachId = new Long(info.getSwapFileAttachId()[i]);
                RemoteDocSwapAttach attach = attachService.findOne(attachId);
                attach.setRemoteDocSwapContentId(envelopeContent.getId());
                attach.setRemoteAttachType(RemoteAttachType.其他);
                attachList.add(attach);
                attachService.getRepository().save(attach);
			}
			//将信息返回给封内对象，以便后续使用
			envelopeContent.setRemoteDocSwapAttachs(attachList);
		}
       
      
	}
    
    
    
    @Override
    public void saveReceiveRemoteDocSwap(File file) throws Exception {
        File[] fileList = file.listFiles();
        File envelopeFile = null;
        Date currentDate = new Date();
        RemoteDocSwapLog docSwapLog = new RemoteDocSwapLog();
        docSwapLog.setLogType(RemoteDocSwapLogType.公文交换);
        docSwapLog.setLogTime(currentDate);
        docSwapLog.setLog("文件夹名称："+file.getAbsolutePath());

        // 保存新收文登记
        boolean sendMsg=false;
        RemoteDocSwapEnvelope remoteDocSwapEnvelope = null;
        RemoteDocSwapContent remoteDocSwapContent = null;
        try {
        	Map<String, File> fileMap = new HashMap<String, File>();
            Map<String, File> fileMd5Map = new HashMap<String, File>();
		    for (int i = 0; i < fileList.length; i++) {
		        if(fileList[i].exists()) {
					 //以文件名为key
	                fileMap.put(fileList[i].getName(), fileList[i]);
	                //计算文件的md5值,以md5 小写值为key
	                String md5 = DocSwapTools.getFileMd5Code(fileList[i]);
					fileMd5Map.put(md5.toLowerCase(), fileList[i]);
		        }
		    }
	        // 找到封首文件
	        if (fileMap.containsKey("envelope.xml")) {
	            envelopeFile = (File) fileMap.get("envelope.xml");
	            // 分析封首文件
	            remoteDocSwapEnvelope = this.buildEnvelopeData(envelopeFile);
	            // 保存分析结果
                if (remoteDocSwapEnvelope == null) {
                    throw new RuntimeException("封首数据为空");
                }
                List<RemoteDocSwapAttach> attachs = new ArrayList<RemoteDocSwapAttach>();
                if (fileMap.containsKey(remoteDocSwapEnvelope.getEnvelopeContentPath())) {
                    envelopeFile = (File) fileMap.get(remoteDocSwapEnvelope.getEnvelopeContentPath());
                    remoteDocSwapContent = this.buildEnvelopeContent(fileMd5Map,envelopeFile, remoteDocSwapEnvelope, attachs);
                }
                if (remoteDocSwapContent == null) {
                    throw new RuntimeException("封内数据为空");
                }
                
                // 查询封体是否有关联的封首
                List<RemoteDocSwapContent> datas = this.contentService.findByEnvelopeUuid(remoteDocSwapContent
                                .getEnvelopeUuid());
                // 设置封首的发送日期和发送单位信息
                RemoteUnitIdentifier unit = unitIndentifierService.getDefUnitIdentifier(remoteDocSwapContent
                                .getSendOrg());
              
                if (Tools.isNotEmptyList(datas)) {
                    //有关联的公文
                    RemoteDocSwapContent content = datas.get(0);
                    RemoteDocSwapEnvelope envelope = datas.get(0).getEnvelope();
                    remoteDocSwapEnvelope.setLinkEnvelopeId(envelope.getId());
                    // 若有相关的公文则认为当前收到的公文为相关公文的反馈
                    if (RemoteDocSwapContent.BUS_TYPE_SWHZ.equals(remoteDocSwapContent.getBussinessType())) {
                        //公文反馈
                        envelope.setEnvStatus(RemoteEnvStatus.已签收);
                        remoteDocSwapEnvelope.setEnvelopeType(RemoteEnvType.feedback);
                        remoteDocSwapEnvelope.setSignerDeptName(remoteDocSwapContent.getSignReceiveDept());
                        remoteDocSwapEnvelope.setSignerName(remoteDocSwapContent.getSignReceiveMan());
                        remoteDocSwapEnvelope.setSignDate(remoteDocSwapContent.getSignReceiveDate());
                        //签收时间
                        envelope.setSignDate(currentDate);
                    } else if(RemoteDocSwapContent.BUS_TYPE_FKGW.equals(remoteDocSwapContent.getBussinessType())){
                        //办结
                        envelope.setEnvStatus(RemoteEnvStatus.已办结);
                        if (remoteDocSwapContent.getFinishTime() != null) {
                            //办结反馈
                            remoteDocSwapEnvelope.setEnvelopeType(RemoteEnvType.办结反馈);
                        } else {
                            //正文反馈
                            remoteDocSwapEnvelope.setEnvelopeType(RemoteEnvType.正文反馈);
                        }
                        content.setFinishTime(currentDate);
                    }else if(RemoteDocSwapContent.BUS_TYPE_CLQK.equals(remoteDocSwapContent.getBussinessType())){
                        //处理情况
                        remoteDocSwapEnvelope.setEnvelopeType(RemoteEnvType.办结反馈);
                        if(RemoteDocSwapContent.DEAL_RES_TYPE_UNDEAL.equals(remoteDocSwapContent.getDealResultType())){
                            //不处理
                            envelope.setEnvStatus(RemoteEnvStatus.不办理);
                        }else if(RemoteDocSwapContent.DEAL_RES_TYPE_TO_READ.equals(remoteDocSwapContent.getDealResultType())){
                            //转阅件
                            envelope.setEnvStatus(RemoteEnvStatus.转阅件);
                        }else{
                            //办结
                            envelope.setEnvStatus(RemoteEnvStatus.已办结);
                        }
                        content.setFinishTime(currentDate);
                    }else if(RemoteDocSwapContent.BUS_TYPE_XGSX.equals(remoteDocSwapContent.getBussinessType())){
                        //修改时限
                        envelope.setDeadlineType(remoteDocSwapEnvelope.getDeadlineType());
                        envelope.setLimitTime(remoteDocSwapEnvelope.getLimitTime());
                        envelope.setModifyDeadlineTime(remoteDocSwapEnvelope.getModifyDeadlineTime());
                        remoteDocSwapEnvelope.setEnvelopeType(RemoteEnvType.修改时限);
                    }else if(RemoteDocSwapContent.BUS_TYPE_ZFGW.equals(remoteDocSwapContent.getBussinessType())){
                        //作废公文
                        envelope.setEnvStatus(RemoteEnvStatus.已作废);
                        //作废时间
                        envelope.setInvalidTime(remoteDocSwapContent.getInvalidTime());
                        //设置签收时间，方便在列表排序
                        if(envelope.getSignDate()==null){
                            envelope.setSignDate(remoteDocSwapContent.getInvalidTime());
                        }
                        remoteDocSwapEnvelope.setEnvelopeType(RemoteEnvType.作废);
                        remoteDocSwapEnvelope.setInvalidTime(remoteDocSwapContent.getInvalidTime()!=null?remoteDocSwapContent.getInvalidTime():new Date() );
                        content.setFinishTime(currentDate);
                    }else if(RemoteDocSwapContent.BUS_TYPE_CXGW.equals(remoteDocSwapContent.getBussinessType())){
                        //撤销公文
                        envelope.setEnvStatus(RemoteEnvStatus.已撤销);
                        //撤销时间
                        envelope.setUndoTime(remoteDocSwapContent.getUndoTime());
                        //设置签收时间，方便在列表排序
                        if(envelope.getSignDate()==null){
                            envelope.setSignDate(remoteDocSwapContent.getUndoTime());
                        }
                        remoteDocSwapEnvelope.setEnvelopeType(RemoteEnvType.撤销);
                        remoteDocSwapEnvelope.setUndoTime(remoteDocSwapContent.getUndoTime()!=null?remoteDocSwapContent.getUndoTime():new Date());
                        content.setFinishTime(currentDate);
                    }else if(RemoteDocSwapContent.BUS_TYPE_SYXX.equals(remoteDocSwapContent.getBussinessType())){
                        //阅件传阅
                        remoteDocSwapEnvelope.setEnvelopeType(RemoteEnvType.传阅);
                        envelope.setEnvStatus(RemoteEnvStatus.办结传阅);
                        content.setDocReadTime(remoteDocSwapContent.getDocReadTime());
                        content.setFinishTime(currentDate);
                    }else if(RemoteDocSwapContent.BUS_TYPE_GDXX.equals(remoteDocSwapContent.getBussinessType())){
                        //阅件归档
                        remoteDocSwapEnvelope.setEnvelopeType(RemoteEnvType.归档);
                        envelope.setEnvStatus(RemoteEnvStatus.办结归档);
                        content.setDocFileTime(remoteDocSwapContent.getDocFileTime());
                        content.setFinishTime(currentDate);
                    }else if(RemoteDocSwapContent.BUS_TYPE_TW.equals(remoteDocSwapContent.getBussinessType())){
                        //退文
                        remoteDocSwapEnvelope.setEnvelopeType(RemoteEnvType.退文);
                        envelope.setEnvStatus(RemoteEnvStatus.退文);
                        content.setDocFileTime(remoteDocSwapContent.getDocFileTime());
                        content.setFinishTime(currentDate);
                    }else{
                        //重复的读取公文
                        remoteDocSwapEnvelope.setEnvelopeType(RemoteEnvType.重复读取的公文);
                        docSwapLog.setStatus(RemoteDocSwapLogStatus.重复);
                    }
                    // 设置receiveFlag值
                    remoteDocSwapEnvelope.setReceiveFlag(RemoteEnvRecFlag.关联);
                    this.repository.save(envelope);
                
                } else {

                    //没有关联的公文，说明是新收到的公文
                    if(RemoteDocSwapContent.BUS_TYPE_DZGW.equals(remoteDocSwapContent.getBussinessType())){
                        remoteDocSwapEnvelope.setReceiveFlag(RemoteEnvRecFlag.接收);
                        remoteDocSwapEnvelope.setEnvelopeType(RemoteEnvType.cooperate);
                        //发送短信通知接收人
                        sendMsg=this.sendMessageToReceiver(remoteDocSwapContent.getTitle(),remoteDocSwapEnvelope.getLimitTime(),unit);
                    }else{
                        //属于反馈公文，但是没有找到相关联的记录
                        remoteDocSwapEnvelope.setReceiveFlag(RemoteEnvRecFlag.错误公文);
                        remoteDocSwapEnvelope.setEnvelopeType(RemoteEnvType.重复读取的公文);
                        docSwapLog.setStatus(RemoteDocSwapLogStatus.错误);
                    }
                     
                }
                
                remoteDocSwapEnvelope.setEnvStatus(RemoteEnvStatus.未签收);
                this.repository.save(remoteDocSwapEnvelope);
                remoteDocSwapContent.setEnvelopeId(remoteDocSwapEnvelope.getId());
                
                // 设置封首的发送日期和发送单位信息
                if (unit != null) {
                    remoteDocSwapEnvelope.setSenderAncestorName(unit.getUnitName());
                    remoteDocSwapEnvelope.setSenderAncestorOrg(unit.getUnitIdentifier());
                    remoteDocSwapContent.setSendOrgName(unit.getUnitName());
                }
                remoteDocSwapEnvelope.setTitle(remoteDocSwapContent.getTitle());
                remoteDocSwapEnvelope.setSendTime(remoteDocSwapContent.getSendTime());
                remoteDocSwapEnvelope.setSenderAncestorName(remoteDocSwapContent.getSendOrgName());
                this.repository.save(remoteDocSwapEnvelope);
                this.contentRepository.save(remoteDocSwapContent);
                if (attachs.size() > 0) {
                    for (int i = 0; i < attachs.size(); i++) {
                        if(".tif".equals(attachs.get(i).getAffixSuffix())){
                            //tif 正文转pdf
                            RemoteDocSwapAttach pdfAttach = this.convertTifAndSave(attachs.get(i));
                            pdfAttach.setRemoteAttachType(RemoteAttachType.转换过的正文附件);
                            pdfAttach.setRemoteDocSwapContentId(remoteDocSwapContent.getId());
                            this.attachRepository.save(pdfAttach);
                            
                        }
                        attachs.get(i).setRemoteDocSwapContentId(remoteDocSwapContent.getId());
                        this.attachRepository.save(attachs.get(i));
                    }
                }
                remoteDocSwapContent.setRemoteDocSwapAttachs(new HashSet<RemoteDocSwapAttach>(attachs));
                //添加读取记录
                if(docSwapLog.getStatus()==null) {
                    docSwapLog.setStatus(RemoteDocSwapLogStatus.成功);
                }
                if(remoteDocSwapContent.getEnvelopeUuid()!=null) {
                    docSwapLog.setMemo(remoteDocSwapContent.getEnvelopeUuid());
                }
                if(remoteDocSwapContent.getEnvelopeId()!=null) {
                    docSwapLog.setDataId(remoteDocSwapContent.getEnvelopeId() + "");
                }
                //读取成功后添加 success.txt文件 标识已经读取过文件
                File successFile = new File(file.getAbsolutePath() + File.separatorChar + ReceiveRemoteDocSwapSchedule.SUCCESS_FILE_NAME);
                if (successFile.createNewFile()) {
                    FileWriter writer = new FileWriter(successFile);
                    writer.write(new Date().toString()+"短信发送情况："+sendMsg);
//                    writer.write(new Date().toString());
                    writer.close();
                }
              
	        }
	        else{
                //添加读取记录
                docSwapLog.setLog("没有找到xml文件");
                docSwapLog.setStatus(RemoteDocSwapLogStatus.失败);
                File successFile = new File(file.getAbsolutePath() + File.separatorChar + ReceiveRemoteDocSwapSchedule.FAILURE_FILE_NAME);
                if (successFile.createNewFile()) {
                    FileWriter writer = new FileWriter(successFile);
                    writer.write(new Date().toString());
                    writer.write("没有找到xml文件");
                    writer.close();
                }
            }
	        
	        this.logRepository.save(docSwapLog);
            //不成功则发送短信通知
            if(!RemoteDocSwapLogStatus.成功.equals(docSwapLog.getStatus())){
                this.sendErrorMsg(envelopeFile);
            }
        } catch (IOException ex) {
            //异常添加failure.txt文件
            File failureFile = new File(file.getAbsolutePath()+File.separatorChar+ReceiveRemoteDocSwapSchedule.FAILURE_FILE_NAME);
            try {
                if(failureFile.createNewFile()){
                    FileWriter writer = new FileWriter(failureFile);
                    StringWriter stringWriter= new StringWriter();
                    PrintWriter printWriter= new PrintWriter(stringWriter);
                    ex.printStackTrace(printWriter);
                    StringBuffer errorMsg= stringWriter.getBuffer();
                    writer.write(errorMsg.toString());
                    writer.close();
                }
                //发送短信提醒出错
                this.sendErrorMsg(envelopeFile);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
            throw ex;
        
		}
        
    }

    /**
     * 构建接收到的envelope
     *
     * @param envelopeFile
     * @return
     */
    private RemoteDocSwapEnvelope buildEnvelopeData(File envelopeFile) {
        SAXReader reader = new SAXReader();
        Map elementValueMap = new HashMap();
        RemoteDocSwapEnvelope envelope = null;
        try {
            Document reportDoc = reader.read(envelopeFile);
            Element root = reportDoc.getRootElement();
            this.recursionElementData(root, elementValueMap, envelopeFieldMap);
	        // 获取xml文件内容
	        if (Tools.isNotEmptyMap(elementValueMap)) {
	            BeanWrapper infoWrapper = new BeanWrapperImpl(RemoteDocSwapEnvelope.class);
	            this.buildWrapperDataList(elementValueMap, infoWrapper);
	            envelope = (RemoteDocSwapEnvelope) infoWrapper.getWrappedInstance();
	            List fileMapList = (List) elementValueMap.get("file");
                if (DocSwapTools.isNotEmptyList(fileMapList)) {
                    //第一个文件名作为封内文件名
                    Map fileMap = (Map) fileMapList.get(0);
                    envelope.setEnvelopeContentPath((String) fileMap.get("envelopeContentPath"));
                }
                envelope.setFileMapList(fileMapList);
	        }
            return envelope;
        } catch (Exception ex) {
        	ex.printStackTrace();
            return null;
        }
    }

    /**
     * 构建封体文件
     *
     * @param envelopeContentFile
     * @param envelope
     * @param attachs
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("deprecation")
	private RemoteDocSwapContent buildEnvelopeContent(Map<String, File> fileMd5Map,File envelopeContentFile, RemoteDocSwapEnvelope envelope,
                    List attachs) throws Exception {
        SAXReader reader = new SAXReader();
        Map elementValueMap = new HashMap();
        RemoteDocSwapContent remoteDocSwapContent = null;
        try {
            Document reportDoc = reader.read(envelopeContentFile);
            Element root = reportDoc.getRootElement();
            this.recursionElementData(root, elementValueMap, envelopeContentFieldMap);
        } catch (Exception ex) {
            return null;
        }
        if (Tools.isNotEmptyMap(elementValueMap)) {
        	 //反馈时间
            Object limitTime=elementValueMap.get("limitTime");
            if(limitTime!=null&&!Tools.isEmptyString(limitTime.toString())&&!"-1".equals(limitTime.toString())) {
            	if(Tools.isDate(limitTime.toString(),"yyyy-MM-dd HH:mm:ss")){
            		envelope.setLimitTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(limitTime.toString()));
            	}else{
            		envelope.setLimitTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(limitTime.toString()))));
            	}
            }
            Object deadlineType=elementValueMap.get("fksjyq.type");
            if(deadlineType!=null){
                envelope.setDeadlineType(deadlineType.toString());
            }
            Object docType=elementValueMap.get("dzgw.type");
            //是办件还是阅件
            if(docType!=null){
            	if("0".equals(docType.toString())){
            		envelope.setDocumentType(RemoteDocSwapType.办件);
            	}else{
            		envelope.setDocumentType(RemoteDocSwapType.阅件);
            	}
            } 
        	
            Object emergencyLevel=elementValueMap.get("emergencyLevel");
            if("加急".equals(emergencyLevel)){
            	elementValueMap.put("emergencyLevel", "急件");
            }
            
            BeanWrapper infoWrapper = new BeanWrapperImpl(RemoteDocSwapContent.class);
            this.buildWrapperDataList(elementValueMap, infoWrapper);
            // 封体对象
            remoteDocSwapContent = (RemoteDocSwapContent) infoWrapper.getWrappedInstance();
//            Map attachMap = (Map) elementValueMap.get("dzgwbswj");//电子公文版式文件 TODO 什么符号？ by yuandl 20160721
//            RemoteDocSwapAttach attach = this.buildAttachFromMap(attachMap);
//            if (attach != null) {
//                remoteDocSwapContent.setSwapDoc(attach);
//                attachs.add(attach);
//            }
            
            // 正文信息
            RemoteDocSwapAttach attach;
            List attachMapList = (List) elementValueMap.get("dzgwbswj");
            //封首文件列出的文件名为key md5为value
            Map<String,String> fileNameMap = toFileNameMap(envelope.getFileMapList());
            if (Tools.isNotEmptyList(attachMapList)) {
                for (int i = 0; i < attachMapList.size(); i++) {
                    attach = this.buildAttachFromMap((Map) attachMapList.get(i),envelopeContentFile,fileNameMap,fileMd5Map);
                    if (attach != null) {
                        attach.setRemoteAttachType(RemoteAttachType.正文);
                        attachs.add(attach);
                    }
                }
            }
            
            //附件信息
            List attachLists = (List) elementValueMap.get("gwfj");
            if (Tools.isNotEmptyList(attachLists)) {
                for (int i = 0; i < attachLists.size(); i++) {
                    attach = this.buildAttachFromMap((Map) attachLists.get(i),envelopeContentFile, fileNameMap, fileMd5Map);
                    if (attach != null) {
                        attach.setRemoteAttachType(RemoteAttachType.其他);
                        attachs.add(attach);
                    }
                }
            }
            
//            List attachLists = (List) elementValueMap.get("gwfj");//公文附件 TODO 什么符号？ by yuandl 20160721
//            if (Tools.isNotEmptyList(attachLists)) {
//                for (int i = 0; i < attachLists.size(); i++) {
//                    attach = new RemoteDocSwapAttach();
//                    attach = this.buildAttachFromMap((Map) attachLists.get(i));
//                    if (attach != null) {
//                        attachs.add(attach);
//                    }
//                }
//            }
        }
        return remoteDocSwapContent;
    }
    
    /**
     * @param attachMap
     * @return
     */
    private RemoteDocSwapAttach buildAttachFromMap(Map attachMap) {
        RemoteDocSwapAttach attach = null;
        File file;
        if (Tools.isNotEmptyMap(attachMap)) {
            if (!Tools.isEmptyString((String) attachMap.get("trueName"))
                            && !Tools.isEmptyString((String) attachMap.get("fileBase64"))) {
                attach = new RemoteDocSwapAttach();
                attach.setTrueName((String) attachMap.get("trueName"));
                String savePath = AttachHelper.appendYearMonthDayPath(StringUtils.trimToEmpty(this.envelopeSendPath));
                savePath = FilenameUtils.normalize(savePath.trim() + "/", true);
                attach.setSavePath(savePath);
                attach.setSaveName(AttachUtils.getDocUniqueFileName("", AttachUtils.getFileSuffix(attach.getTrueName())));
                try {
                    file = AttachUtils.base64ToFile((String) attachMap.get("fileBase64"), attach.getPhysicalFile());
                    attach.setFileSize(Long.valueOf(file.length()));
                } catch (Exception ex) {
                    return null;
                }
            }
        }
        return attach;
    }

    /**
     * 所有异常往外抛出，在调用方法出捕获。
     * @param attachMap
     * @param fileNameMap
     * @param fileMd5Map
     * @return
     */
    private RemoteDocSwapAttach buildAttachFromMap(Map attachMap, File contentFile, Map<String, String> fileNameMap, Map<String, File> fileMd5Map) throws IOException {
        RemoteDocSwapAttach attach = null;
        if (DocSwapTools.isNotEmptyMap(attachMap)) {
            String fileName = (String) attachMap.get("fileName");
            if (!Tools.isEmptyString(fileName)) {
                attach = new RemoteDocSwapAttach();
                attach.setTrueName(fileName);
                //移动附件到指定文件夹
                String savePath = AttachHelper.appendYearMonthDayPath(StringUtils.trimToEmpty(this.envelopeSendPath));
                savePath = FilenameUtils.normalize(savePath.trim() + "/", true);
                String prefix = attach.getTrueName().substring(attach.getTrueName().lastIndexOf("."));
                String saveName=AttachUtils.getUniqueFileName() + prefix;
//                savePath = savePath + saveName;
//                String saveName = AttachUtils.getDocUniqueFileName("",AttachUtils.getFileSuffix(attach.getTrueName()) );
                attach.setSavePath(savePath);
                attach.setSaveName(saveName);
                //通过fileName获取文件md5，再通过md5获取文件file对象，使用file对象复制文件
                String fileMd5=fileNameMap.get(fileName).toLowerCase();
                File sourceFile = fileMd5Map.get(fileMd5);
                File targetFile = new File(attach.getPhysicalFile());
                FileUtils.copyFile(sourceFile,targetFile);
               
            }
        }
        return attach;
    }
    /**
     * @param datas
     * @param infoWrapper
     */
    private void buildWrapperDataList(Map datas, BeanWrapper infoWrapper) {
        synchronized (this) {
            String key = "";
            Class cl = null;
            Object value = null;
            try {
                for (Iterator it = datas.keySet().iterator(); it.hasNext();) {
                    key = (String) it.next();
                    // 过滤逃逸字符且将全角字符转换成半角
                    value = datas.get(key);
                    // 若数据值不是字符串则跳过
                    if (value.getClass().getName().indexOf("String") <= 0) {
                        continue;
                    }
                    cl = infoWrapper.getPropertyType(key);
                    if (cl != null && !Tools.isEmptyString(value.toString()) && infoWrapper.isWritableProperty(key)) {
                        if (cl.getName().indexOf("Date") > 0) {
                            // 如果日期带时间就用DataTime格式化，否则就用Data格式化
                            try {
                                if (value.toString().length() > 10) {
                                    infoWrapper.setPropertyValue(key, Tools.DATE_TIME_FORMAT.parse(value.toString()));
                                } else {
                                    infoWrapper.setPropertyValue(key, Tools.DATE_FORMAT.parse(value.toString()));
                                }
                            } catch (Exception ex) {
                            }
                        } else if (cl.getName().indexOf("Integer") > 0) {
                            infoWrapper.setPropertyValue(key, Integer.valueOf(value.toString()));
                        } else {
                            infoWrapper.setPropertyValue(key, value);
                        }
                    }
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * 递归获取xml内容
     *
     * @param node
     * @param elementMap
     */
    private void recursionElementData(Element node, Map elementMap, Map nodeMap) {
        for (Iterator it = node.elementIterator(); it.hasNext();) {
            Element data = (Element) it.next();
            String nodeName = (String) nodeMap.get(data.getName());
            System.out.println("data.getName()=" + data.getName());
            if (Tools.isEmptyString(nodeName)) {
                nodeName = (String) nodeMap.get(node.getName() + "." + data.getName());
            }
            Attribute attribute = data.attribute(0);
            if (attribute != null && !Tools.isEmptyString(attribute.getValue())) {
                elementMap.put(data.getName() + "." + attribute.getName(), attribute.getValue().trim());
            }
            if (data.elementIterator().hasNext()) {
                if (nodeMap.containsKey(data.getName())) {
                    String mapName = (String) nodeMap.get(data.getName());
                    if (mapName.equals("Map")) {
                        System.out.println("Map data=" + data.getName());
                        elementMap.put(data.getName(), this.recursionObjectElementData(data, nodeMap));
                    } else if (mapName.equals("List")) {
                        List tmps = new ArrayList();
                        if (elementMap.containsKey(data.getName())) {
                            tmps = (List) elementMap.get(data.getName());
                        }
                        tmps.add(this.recursionObjectElementData(data, nodeMap));
                        elementMap.put(data.getName(), tmps);
                    }
                } else {
                    this.recursionElementData(data, elementMap, nodeMap);
                }
            } else if (!Tools.isEmptyString(nodeName)) {
                elementMap.put(nodeName, data.getText().trim());
            }
        }
    }

    /**
     * @param node
     * @return
     */
    private Map recursionObjectElementData(Element node, Map nodeMap) {
        Map result = new HashMap();
        String nodeName;
        for (Iterator it = node.elementIterator(); it.hasNext();) {
            Element data = (Element) it.next();
            nodeName = (String) nodeMap.get(data.getName());
            System.out.println("List nodeName=" + nodeName);
            if (!Tools.isEmptyString(nodeName)) {
                result.put(nodeName, data.getText().trim());
            }
        }
        return result;
    }

    //文件名为key md5为value
    private Map<String,String> toFileNameMap(List fileMapList) {
        Map<String,String> fileMap = new HashMap<String, String>();
        for(int i=0;i<fileMapList.size();i++){
            Map<String,String> dataMap = (Map<String, String>) fileMapList.get(i);
            fileMap.put(dataMap.get("envelopeContentPath"),dataMap.get("envelopeContentMD5"));
        }
        return fileMap;
    }
    
    @Override
    public boolean createRemoteDocSwapFeedback(DocSwapEnvelopeEditBean bean) {
        Map result = new HashMap();
        SysUser currentUser = (SysUser) UserTool.getInstance().getCurrentUser();
        // 根据envelopeId获取自治区公文交换记录
        if (bean.getId() == null) {
            return false;
        }
        String xmlType=null;
        
        RemoteDocSwapEnvelope envelopeFeedback = new RemoteDocSwapEnvelope();
        RemoteDocSwapContent envelopeContentFeedback = new RemoteDocSwapContent();
        Date currentDate = new Date();
        //与反馈公文相关联的封首实体
        RemoteDocSwapEnvelope envelope = this.repository.findOne(bean.getId());
        //与反馈公文相关联的封内实体
        RemoteDocSwapContent envelopeContent = this.contentService.getByEnvelopeId(bean.getId());

        if(currentUser!=null) {
			envelopeContentFeedback.setSignReceiveMan(currentUser.getUserName());
			envelopeContentFeedback.setSignReceiveDept(currentUser.getDepartment().getDeptName());
		}
        if(RemoteFeedBackType.办结反馈.getKey().equals(bean.getFeedBackType())){
			xmlType=RemoteDocSwapContent.BUS_TYPE_CLQK;
			//  办结反馈,设置封首状态为办结
			if(RemoteDocSwapContent.DEAL_RES_TYPE_TO_READ.equals(bean.getDealResultType())){
				//转阅件
				envelope.setEnvStatus(RemoteEnvStatus.转阅件);
				envelopeContentFeedback.setDealResult(bean.getConvertReadDoc());
			}else if(RemoteDocSwapContent.DEAL_RES_TYPE_UNDEAL.equals(bean.getDealResultType())){
				//不办理
				envelope.setEnvStatus(RemoteEnvStatus.不办理);
				envelopeContentFeedback.setDealResult(bean.getUndoReason());
			}else{
				//办结
				envelope.setEnvStatus(RemoteEnvStatus.已办结);
			}

			envelopeContent.setFinishTime(currentDate);

			//  设置反馈公文类型为办结反馈
			envelopeFeedback.setEnvelopeType(RemoteEnvType.办结反馈);
			envelopeFeedback.setTitle("(处理情况)"+envelope.getTitle());
			envelopeContentFeedback.setBussinessType(RemoteDocSwapContent.BUS_TYPE_CLQK);
			envelopeContentFeedback.setConStatus(RemoteContentStatus.中间);
			//办结信息
			envelopeContentFeedback.setDealResultType(bean.getDealResultType());
			envelopeContentFeedback.setTitle(envelopeFeedback.getTitle());
			envelopeContentFeedback.setContactName(bean.getContactName());
			envelopeContentFeedback.setContactPhone(bean.getContactPhone());
			envelopeContentFeedback.setFinishMemo(bean.getFinishMemo());
			envelopeContentFeedback.setFinishTime(currentDate);
			// TODO: 2016/11/21 联系人暂时使用当前用户
//			envelopeContentFeedback.setContactName(currentUser.getUserName());
//			envelopeContentFeedback.setContactPhone(currentUser.getMobile());
		}else if(RemoteFeedBackType.正文反馈.getKey().equals(bean.getFeedBackType())){
			xmlType=RemoteDocSwapContent.BUS_TYPE_FKGW;
			//  正文反馈，设置封首状态
			envelope.setEnvStatus(RemoteEnvStatus.已办结);
			envelopeContent.setFinishTime(currentDate);
			envelopeFeedback.setTitle(bean.getTitle());
			envelopeFeedback.setRemark(bean.getRemark());
			//  设置反馈公文类型为正文反馈
			envelopeContentFeedback.setEmergencyLevel(bean.getEmergencyLevel());
			envelopeFeedback.setEnvelopeType(RemoteEnvType.正文反馈);
			envelopeContentFeedback.setFinishTime(currentDate);
			//成文日期
			envelopeContentFeedback.setDocFileTime(bean.getDocFileTime());
			envelopeContentFeedback.setPrintOrg(bean.getPrintOrg());
			envelopeContentFeedback.setPrintDate(bean.getPrintDate());
			envelopeContentFeedback.setDocNumber(bean.getDocNumber());
			envelopeContentFeedback.setContactName(bean.getContactName());
			envelopeContentFeedback.setContactPhone(bean.getContactPhone());
			envelopeContentFeedback.setTitle(envelopeFeedback.getTitle());
			envelopeContentFeedback.setConStatus(RemoteContentStatus.起始);
			envelopeContentFeedback.setBussinessType(RemoteDocSwapContent.BUS_TYPE_FKGW);
		}else if(bean.getFeedBackType().equals(RemoteFeedBackType.撤销.getKey())){
			//撤销公文
			xmlType=RemoteDocSwapContent.BUS_TYPE_CXGW;
			envelopeContent.setFinishTime(currentDate);
			//设置签收时间，方便在列表排序
			if(envelope.getSignDate()==null){
				envelope.setSignDate(currentDate);
			}
			envelope.setEnvStatus(RemoteEnvStatus.已撤销);
			envelope.setUndoTime(currentDate);
			envelopeFeedback.setEnvelopeType(RemoteEnvType.撤销);
			envelopeFeedback.setUndoTime(currentDate);
			envelopeFeedback.setTitle("(撤销)"+envelope.getTitle());
			envelopeContentFeedback.setConStatus(RemoteContentStatus.中间);
			envelopeContentFeedback.setTitle(envelopeFeedback.getTitle());
			envelopeContentFeedback.setBussinessType(RemoteDocSwapContent.BUS_TYPE_CXGW);
			envelopeContentFeedback.setContactName(bean.getContactName());
			envelopeContentFeedback.setContactPhone(bean.getContactPhone());
		}else if(RemoteFeedBackType.作废.getKey().equals(bean.getFeedBackType())){
			//作废公文
			xmlType=RemoteDocSwapContent.BUS_TYPE_ZFGW;
			envelopeContent.setFinishTime(currentDate);
			//设置签收时间，方便在列表排序
			if(envelope.getSignDate()==null){
				envelope.setSignDate(currentDate);
			}
			envelope.setEnvStatus(RemoteEnvStatus.已作废);
			envelope.setInvalidTime(currentDate);
			envelopeFeedback.setEnvelopeType(RemoteEnvType.作废);
			envelopeFeedback.setInvalidTime(currentDate);
			envelopeFeedback.setTitle("(作废)"+envelope.getTitle());
			envelopeContentFeedback.setConStatus(RemoteContentStatus.中间);
			envelopeContentFeedback.setTitle(envelopeFeedback.getTitle());
			envelopeContentFeedback.setBussinessType(RemoteDocSwapContent.BUS_TYPE_ZFGW);
			envelopeContentFeedback.setContactName(bean.getContactName());
			envelopeContentFeedback.setContactPhone(bean.getContactPhone());
		}else if(RemoteFeedBackType.修改限时.getKey().equals(bean.getFeedBackType())){
			//修改限时
			xmlType=RemoteDocSwapContent.BUS_TYPE_XGSX;
			if(bean.getLimitTime()!=null) {
				envelope.setLimitTime(bean.getLimitTime());
			}
			envelopeFeedback.setLimitTime(envelope.getLimitTime());
			envelope.setModifyDeadlineTime(currentDate);
			envelope.setDeadlineType(bean.getDeadlineType());
			envelopeFeedback.setTitle("(修改限时)"+envelope.getTitle());
			envelopeFeedback.setModifyDeadlineTime(currentDate);
			envelopeFeedback.setDeadlineType(bean.getDeadlineType());
			envelopeFeedback.setEnvelopeType(RemoteEnvType.修改时限);
			envelopeContentFeedback.setTitle(envelopeFeedback.getTitle());
			envelopeContentFeedback.setConStatus(RemoteContentStatus.中间);
			envelopeContentFeedback.setFeedbackFlag(RemoteDocSwapContent.FB_DATAS);
			envelopeContentFeedback.setBussinessType(RemoteDocSwapContent.BUS_TYPE_XGSX);
			envelopeContentFeedback.setEmergencyLevel(bean.getEmergencyLevel());
			envelopeContentFeedback.setContactName(bean.getContactName());
			envelopeContentFeedback.setContactPhone(bean.getContactPhone());
		}else if(RemoteFeedBackType.办结传阅.getKey().equals(bean.getFeedBackType())){
			//阅件传阅
			xmlType=RemoteDocSwapContent.BUS_TYPE_SYXX;
			envelope.setEnvStatus(RemoteEnvStatus.办结传阅);
			envelopeFeedback.setTitle("(传阅)"+envelope.getTitle());
			envelopeFeedback.setEnvelopeType(RemoteEnvType.传阅);
			envelopeContent.setFinishTime(currentDate);
			envelopeContent.setDocReadTime(currentDate);
			envelopeContentFeedback.setConStatus(RemoteContentStatus.中间);
			envelopeContentFeedback.setTitle(envelopeFeedback.getTitle());
			envelopeContentFeedback.setDocReadBody(bean.getDocReadBody());
			envelopeContentFeedback.setDocReadTime(currentDate);
			envelopeContentFeedback.setContactName(bean.getContactName());
			envelopeContentFeedback.setBussinessType(RemoteDocSwapContent.BUS_TYPE_SYXX);
			envelopeContentFeedback.setContactPhone(bean.getContactPhone());
		}else if(RemoteFeedBackType.办结归档.getKey().equals(bean.getFeedBackType())){
			//阅件归档
			xmlType=RemoteDocSwapContent.BUS_TYPE_GDXX;
			envelope.setEnvStatus(RemoteEnvStatus.办结归档);
			envelopeFeedback.setEnvelopeType(RemoteEnvType.归档);
			envelopeFeedback.setTitle("(阅件归档)"+envelope.getTitle());
			envelopeContent.setFinishTime(currentDate);
			envelopeContent.setDocFileTime(currentDate);
			envelopeContentFeedback.setDocFileTime(currentDate);
			envelopeContentFeedback.setConStatus(RemoteContentStatus.中间);
			envelopeContentFeedback.setTitle(envelopeFeedback.getTitle());
			envelopeContentFeedback.setContactName(bean.getContactName());
			envelopeContentFeedback.setContactPhone(bean.getContactPhone());
			envelopeContentFeedback.setBussinessType(RemoteDocSwapContent.BUS_TYPE_GDXX);
		}else if(RemoteFeedBackType.退文.getKey().equals(bean.getFeedBackType())){
			//退文
			xmlType=RemoteDocSwapContent.BUS_TYPE_TW;
			envelopeContent.setFinishTime(currentDate);
			//设置签收时间，方便在列表排序
			if(envelope.getSignDate()==null){
				envelope.setSignDate(currentDate);
			}
			envelope.setEnvStatus(RemoteEnvStatus.退文);
			envelopeFeedback.setEnvelopeType(RemoteEnvType.退文);
			envelopeFeedback.setTitle("(退文)"+envelope.getTitle());
			envelopeContent.setBackTime(currentDate);
			envelopeContentFeedback.setConStatus(RemoteContentStatus.中间);
			envelopeContentFeedback.setTitle(envelopeFeedback.getTitle());
			envelopeContentFeedback.setBackTime(currentDate);
			envelopeContentFeedback.setBackReason(bean.getBackReason());
			envelopeContentFeedback.setContactName(bean.getContactName());
			envelopeContentFeedback.setContactPhone(bean.getContactPhone());
			envelopeContentFeedback.setBussinessType(RemoteDocSwapContent.BUS_TYPE_TW);
		}
        
        this.repository.save(envelope);
        this.contentRepository.save(envelopeContent);
        

        envelopeContentFeedback.setResourceIden(RemoteDocSwapContent.BUS_TYPE_DZGW);
		envelopeContentFeedback.setSendTime(currentDate);
		//反馈公文标识
		envelopeFeedback.setSendTime(currentDate);
		envelopeFeedback.setReceiveFlag(RemoteEnvRecFlag.关联);
        envelopeFeedback.setEnvelopeUuid(envelope.getEnvelopeUuid());
        envelopeFeedback.setSecondaryUnitIdentifier(envelope.getSecondaryUnitIdentifier());
        envelopeFeedback.setIdentifier(envelope.getIdentifier());
        envelopeFeedback.setSenderId(currentUser.getId());
        envelopeFeedback.setSenderName(currentUser.getUserName());
        envelopeFeedback.setSenderDept(currentUser.getDepartment().getId());
        envelopeFeedback.setSenderAncestorDept(currentUser.getDepartment().getDeptParent());
        envelopeFeedback.setSenderAncestorName(this.unitIndentifierService.getDefUnitIdentifier(null).getUnitName());
        envelopeFeedback.setSenderAncestorOrg(this.unitIndentifierService.getDefUnitIdentifier(null).getUnitIdentifier());
        envelopeFeedback.setLinkEnvelopeId(envelope.getId());
        try {
        	this.repository.save(envelopeFeedback);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		envelopeContentFeedback.setEmergencyLevel(bean.getEmergencyLevel());
		envelopeContentFeedback.setSecretLevel(bean.getSecretLevel());
		envelopeContentFeedback.setLetterId(UUID.randomUUID().toString());
        envelopeContentFeedback.setEnvelopeId(envelopeFeedback.getId());
        envelopeContentFeedback.setEnvelopeUuid(envelopeContent.getEnvelopeUuid());
        envelopeContentFeedback.setSendOrg(envelopeContent.getSendOrg());
        envelopeContentFeedback.setSendOrgName(envelopeContent.getSendOrgName());
        envelopeContentFeedback.setFeedbackOrg(envelopeContent.getFeedbackOrg());
        envelopeContentFeedback.setSignReceiveDate(currentDate);
        this.contentRepository.save(envelopeContentFeedback);
        
        //保存附件记录
  		this.toSaveAttach(bean,envelopeContentFeedback);
  		//生成xml文件
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("xmlReceiveFlag", envelope.getReceiveFlag());
		model.put("envelope", envelopeFeedback);
		model.put("envelopeContent", envelopeContentFeedback);
		this.createFeedBackToXml(model,xmlType);
        return true;
    }

    @Override
    public Map selectRemoteDocSwapEnvelopeDetail(Long envelopeId) {
        Map result = new HashMap();
        //
        RemoteDocSwapEnvelope envelope = this.repository.findOne(envelopeId);
        result.put("envelope", envelope);
        //
        RemoteDocSwapContent envelopeContent = this.contentService.getByEnvelopeId(envelopeId);
        result.put("envelopeContent", envelopeContent);

        // 正文附件
        result.put("docFilehMap", attachService.getAttachMap(envelopeContent.getId(), RemoteAttachType.转换过的正文附件));
        
        // 封体附件
        result.put("attachMap", attachService.getAttachMap(envelopeContent.getId(), RemoteAttachType.其他));
        return result;
    }

    /**
     * tif文件转pdf
     * @param remoteDocSwapAttach
     * @return
     */
    private RemoteDocSwapAttach convertTifAndSave(RemoteDocSwapAttach remoteDocSwapAttach) throws IOException, DocumentException {
        File tifFile = new File(remoteDocSwapAttach.getPhysicalFile());
        //生成的pdf文件名和路径
        String pdfFileName = "tif"+AttachUtils.getUniqueFileName()+".pdf";
        String pdfFilePath =  tifFile.getParent()+"/"+pdfFileName;
        //转pdf
        ConvectorUtils.convertTifToPdf(remoteDocSwapAttach.getPhysicalFile(),pdfFilePath);

        RemoteDocSwapAttach pdfAttach = new RemoteDocSwapAttach();
        pdfAttach.setSavePath(remoteDocSwapAttach.getSavePath());
        pdfAttach.setSaveName(pdfFileName);
        pdfAttach.setFileSize(remoteDocSwapAttach.getFileSize());
        //原文件名
        String fileName = remoteDocSwapAttach.getTrueName().replace(".tif",".pdf");
        pdfAttach.setTrueName(fileName);
        return pdfAttach;
    }
    
    @Override
    public void deleteRemoteDocSwapEnvelope(String[] envelopeIds) {
        // 批量删除公文交换
        for (int i = 0; i < envelopeIds.length; i++) {
            this.deleteRemoteDocSwapEnvelope(Long.valueOf(envelopeIds[i]));
        }
    }

    @Override
    public void deleteRemoteDocSwapEnvelope(Long envelopeId) {
        List<RemoteDocSwapEnvelope> datas = this.repository.findByLinkEnvelopeId(envelopeId);
        if (Tools.isNotEmptyList(datas)) {
            for (int i = 0; i < datas.size(); i++) {
                this.deleteRemoteDocSwapEnvelopeDetail(datas.get(i).getId());
            }
        }
        this.deleteRemoteDocSwapEnvelopeDetail(envelopeId);
    }

    /**
     * 删除公文交换记录
     *
     * @param envelopeId
     */
    private void deleteRemoteDocSwapEnvelopeDetail(Long envelopeId) {
        RemoteDocSwapAttach attach;
        RemoteDocSwapEnvelope envelope = this.repository.findOne(envelopeId);
        List<RemoteDocSwapContent> datas = this.contentService.findByEnvelopeUuid(envelope.getEnvelopeUuid());
        for (int i = 0; i < datas.size(); i++) {
            // if (datas.get(i).getRemoteDocSwapAttachs() != null) {
            // for (Iterator it = datas.get(i).getRemoteDocSwapAttachs().iterator(); it.hasNext();) {
            // attach = (RemoteDocSwapAttach) it.next();
            // AttachUtils.removeFile(attach.getPhysicalFile());
            // this.attachService.getRepository().delete(attach);
            // }
            // }
            this.contentService.getRepository().delete(datas.get(i));
        }
        this.repository.delete(envelope);
    }
    
    
    /** 
     * @Title: documentToXml 
     * @Description: 生成封首、封内文件
     * @param envelope
     * @param content
     * @return: void
     */
    private void documentToXml(RemoteDocSwapEnvelope envelope, RemoteDocSwapContent content){
		//文件夹名称
		String folderName = new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"-"+content.getLetterId();
		String generatefilesPath = UploadManager.getInstance().getUploadRootPath() + this.envelopePath + folderName + "/";
		String envelopeContentName = "dzgw.xml";
		if(!Tools.isEmptyString(content.getBussinessType())){
			envelopeContentName=content.getBussinessType()+ ".xml";
		}
		DocSwapTools.createFilePath(generatefilesPath);
		OutputStreamWriter write = null;

		//日志记录
		RemoteDocSwapLog swapLog = new RemoteDocSwapLog();
		swapLog.setLog("发送文件路径："+generatefilesPath);
		swapLog.setLogTime(new Date());
		swapLog.setLogType(RemoteDocSwapLogType.公文交换);
		swapLog.setStatus(RemoteDocSwapLogStatus.成功);
		swapLog.setDataId(envelope.getId()+"");
		swapLog.setMemo("业务流水号："+content.getEnvelopeUuid());
		try {
			//拷贝附件
			DocSwapTools.copyFile(content.getGdFileDoc().getPhysicalFile(),generatefilesPath+content.getGdFileDoc().getTrueName());
			DocSwapTools.copyFile(content.getGwFileDoc().getPhysicalFile(),generatefilesPath+content.getGwFileDoc().getTrueName());
			DocSwapTools.copyFile(content.getTifFileDoc().getPhysicalFile(),generatefilesPath+content.getTifFileDoc().getTrueName());
			if(content.getRemoteDocSwapAttachs()!=null) {
				for (RemoteDocSwapAttach attach :content.getRemoteDocSwapAttachs()){
			        DocSwapTools.copyFile(attach.getPhysicalFile(),generatefilesPath+attach.getTrueName());
				}
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("envelope", envelope);
			model.put("envelopeContent", content);
			 
			DocSwapTools.createFilePath(generatefilesPath);
			
			// 先生成封内文件，文件名由AttachUtils生成，再算出封内文件的MD5码
            write = new OutputStreamWriter(new FileOutputStream(generatefilesPath
                            + envelopeContentName), "utf-8");
            // 生成封内文件
            XmlTemplateUtil.renderXmlTemplate("/doc/remotedocswap/xmltemplate/envelopeContentTemplate.vm", model, write, "utf-8");

            write.flush();
            write.close();
            // 算出封内文件的MD5码
            File file = new File(generatefilesPath + envelopeContentName);
            if (file.exists()) {
                String md5 = Tools.getFileMd5Code(file);
                if (!Tools.isEmptyString(md5)) {
                    ((RemoteDocSwapEnvelope) model.get("envelope")).setEnvelopeContentPath(envelopeContentName);
                    ((RemoteDocSwapEnvelope) model.get("envelope")).setEnvelopeContentMD5(md5);
                }
            }
            // 生成封首文件
            write = new OutputStreamWriter(new FileOutputStream(generatefilesPath + "envelope.xml"), "utf-8");
            XmlTemplateUtil.renderXmlTemplate("/doc/remotedocswap/xmltemplate/envelopeTemplate.vm", model, write, "utf-8");
            write.flush();
            write.close();
			 
            this.logRepository.save(swapLog);
		} catch (Exception e) {
			StringWriter stringWriter= new StringWriter();
			PrintWriter printWriter= new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			StringBuffer errorMsg= stringWriter.getBuffer();
			String subMsg = errorMsg.substring(0,errorMsg.length()>900?900:errorMsg.length());
			swapLog.setMemo(subMsg);
			swapLog.setStatus(RemoteDocSwapLogStatus.失败);
			this.logRepository.save(swapLog);
			// 删除已生成的文件
			DocSwapTools.deleteRecursively(new File(generatefilesPath));
		} finally {
			if (write != null) {
				try {
					write.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
    
    /**
	 * 生成反馈的xml文件
	 * @param model
	 * @return
     */
	private String createFeedBackToXml(Map<String, Object> model,String xmlTypeName){
		//文件夹名称
		RemoteDocSwapContent content = (RemoteDocSwapContent) model.get("envelopeContent");
		String folderName = new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"-"+content.getLetterId();
//		String generatefilesPath = this.envelopeTempPath+ folderName + "/";
		String generatefilesPath = UploadManager.getInstance().getUploadRootPath() + this.envelopePath + folderName + "/";
		String envelopeContentName = xmlTypeName +".xml";
		if(RemoteDocSwapContent.BUS_TYPE_ZZGW.equals(xmlTypeName)){
			envelopeContentName = RemoteDocSwapContent.BUS_TYPE_DZGW +".xml";
		}
		OutputStreamWriter write = null;

		//日志记录
		RemoteDocSwapLog swapLog = new RemoteDocSwapLog();
		swapLog.setLog("反馈文件路径："+generatefilesPath);
		swapLog.setLogTime(new Date());
		swapLog.setLogType(RemoteDocSwapLogType.反馈);
		swapLog.setStatus(RemoteDocSwapLogStatus.成功);
		swapLog.setDataId(content.getEnvelopeId()+"");
		try {
			//拷贝附件
			if(content.getGdFileDoc()!=null) {
				DocSwapTools.copyFile(content.getGdFileDoc().getPhysicalFile(),generatefilesPath+content.getGdFileDoc().getTrueName());
				DocSwapTools.copyFile(content.getGwFileDoc().getPhysicalFile(),generatefilesPath+content.getGwFileDoc().getTrueName());
				DocSwapTools.copyFile(content.getTifFileDoc().getPhysicalFile(),generatefilesPath+content.getTifFileDoc().getTrueName());
			}
			if(content.getRemoteDocSwapAttachs()!=null) {
				for (RemoteDocSwapAttach attach :content.getRemoteDocSwapAttachs()){
					DocSwapTools.copyFile(attach.getPhysicalFile(),generatefilesPath+attach.getTrueName());
				}
			}
			DocSwapTools.createFilePath(generatefilesPath);
			// 先生成封内文件，文件名由AttachUtils生成，再算出封内文件的MD5码
			write = new OutputStreamWriter(new FileOutputStream(generatefilesPath + envelopeContentName), "utf-8");
			// 生成封内文件
			if(RemoteDocSwapContent.BUS_TYPE_CXGW.equals(xmlTypeName)){
				XmlTemplateUtil.renderXmlTemplate("/doc/remotedocswap/xmltemplate/envelopeContentCxgwTemplate.vm", model, write, "utf-8");
			}else if(RemoteDocSwapContent.BUS_TYPE_ZFGW.equals(xmlTypeName)){
				XmlTemplateUtil.renderXmlTemplate("/doc/remotedocswap/xmltemplate/envelopeContentZfgwTemplate.vm", model, write, "utf-8");
			}else if(RemoteDocSwapContent.BUS_TYPE_XGSX.equals(xmlTypeName)){
				XmlTemplateUtil.renderXmlTemplate("/doc/remotedocswap/xmltemplate/envelopeContentXgsxTemplate.vm", model, write, "utf-8");
			}else if(RemoteDocSwapContent.BUS_TYPE_SWHZ.equals(xmlTypeName)){
				XmlTemplateUtil.renderXmlTemplate("/doc/remotedocswap/xmltemplate/envelopeContentSwhzTemplate.vm", model, write, "utf-8");
			}else if(RemoteDocSwapContent.BUS_TYPE_CLQK.equals(xmlTypeName)){
				XmlTemplateUtil.renderXmlTemplate("/doc/remotedocswap/xmltemplate/envelopeContentClqkTemplate.vm", model, write, "utf-8");
			}else if(RemoteDocSwapContent.BUS_TYPE_GDXX.equals(xmlTypeName)){
				XmlTemplateUtil.renderXmlTemplate("/doc/remotedocswap/xmltemplate/envelopeContentGdxxTemplate.vm", model, write, "utf-8");
			}else if(RemoteDocSwapContent.BUS_TYPE_SYXX.equals(xmlTypeName)){
				XmlTemplateUtil.renderXmlTemplate("/doc/remotedocswap/xmltemplate/envelopeContentSyxxTemplate.vm", model, write, "utf-8");
			}else if(RemoteDocSwapContent.BUS_TYPE_TW.equals(xmlTypeName)){
				XmlTemplateUtil.renderXmlTemplate("/doc/remotedocswap/xmltemplate/envelopeContentTwTemplate.vm", model, write, "utf-8");
			}else if(RemoteDocSwapContent.BUS_TYPE_ZZGW.equals(xmlTypeName)){
				//纸子公文
				XmlTemplateUtil.renderXmlTemplate("/doc/remotedocswap/xmltemplate/paperdoc/dzgw.vm", model, write, "utf-8");
			}else{
				XmlTemplateUtil.renderXmlTemplate("/doc/remotedocswap/xmltemplate/envelopeFeekbackContentTemplate.vm", model, write, "utf-8");
			}
			write.flush();
			write.close();
			// 算出封内文件的MD5码
			File file = new File(generatefilesPath + envelopeContentName);
			if (file.exists()) {
				String md5 = DocSwapTools.getFileMd5Code(file);
				if (!Tools.isEmptyString(md5)) {
					((RemoteDocSwapEnvelope) model.get("envelope")).setEnvelopeContentPath(envelopeContentName);
					((RemoteDocSwapEnvelope) model.get("envelope")).setEnvelopeContentMD5(md5);
				}
			}
			// 生成封首文件
			write = new OutputStreamWriter(new FileOutputStream(generatefilesPath + "envelope.xml"), "utf-8");
			if(RemoteDocSwapContent.BUS_TYPE_ZZGW.equals(xmlTypeName)){
				//纸质公文封首
				XmlTemplateUtil.renderXmlTemplate("/doc/remotedocswap/xmltemplate/paperdoc/envelope.vm", model, write, "utf-8");
			}else{
				XmlTemplateUtil.renderXmlTemplate("/doc/remotedocswap/xmltemplate/envelopeTemplate.vm", model, write, "utf-8");
			}
			write.flush();
			write.close();
			this.logRepository.save(swapLog);
			return generatefilesPath;
		} catch (Exception e) {
			StringWriter stringWriter= new StringWriter();
			PrintWriter printWriter= new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			StringBuffer errorMsg= stringWriter.getBuffer();
			String subMsg = errorMsg.substring(0,errorMsg.length()>900?900:errorMsg.length());
			swapLog.setMemo(subMsg);
			swapLog.setStatus(RemoteDocSwapLogStatus.失败);
			this.logRepository.save(swapLog);
			// 删除已生成的文件
			DocSwapTools.deleteRecursively(new File(generatefilesPath));
		} finally {
			if (write != null) {
				try {
					write.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public Map findEnvelopeMapByLinkIdAndType(Long envelopeId) {
		Map envelopeMap = new HashMap();
		List<RemoteDocSwapEnvelope> envelopeList = this.repository.findByLinkEnvelopeId(envelopeId);
		if(Tools.isNotEmptyList(envelopeList)){
			for(RemoteDocSwapEnvelope envelope:envelopeList){
				if(envelope.getEnvelopeType()!=null) {
					List<RemoteDocSwapEnvelope> preList=(List<RemoteDocSwapEnvelope>) envelopeMap.get(envelope.getEnvelopeType());
					if(preList==null){
						preList = new ArrayList<RemoteDocSwapEnvelope>();
						//关联的封内实体
						RemoteDocSwapContent content = this.contentService.getByEnvelopeId(envelope.getId());
						//正文反馈显示附件
						if(Tools.isNotEmptySet(content.getRemoteDocSwapAttachs())){
							// 正文附件
							envelopeMap.put("docFilehMap", attachService.getAttachMap(content.getId(), RemoteAttachType.转换过的正文附件));
					        
					        // 封体附件
					        envelopeMap.put("attachMap", attachService.getAttachMap(content.getId(), RemoteAttachType.其他));
						}
						envelope.setContent(content);
						
					}
					preList.add(envelope);
					envelopeMap.put(envelope.getEnvelopeType().getKey(),preList);
				}
			}
		}
		
		return envelopeMap;
	}

	@Override
	public boolean signDocSwap(DocSwapEnvelopeEditBean bean, SysUser currentUser) {
		if(bean!=null&&bean.getId()!=null&&currentUser!=null){
			Long id = bean.getId();
			//封首
			RemoteDocSwapEnvelope envelope = this.repository.findOne(id);
			//封内
			RemoteDocSwapContent docSwapContent = this.contentService.getByEnvelopeId(id);
			if (envelope != null && docSwapContent != null) {
				//属于未签收公文才可以签收
				if(!RemoteEnvStatus.已签收.equals(envelope.getEnvStatus())){
					//保存附件
//					this.copyDocSwapAttachToSysAffix(docSwapContent.getRemoteDocSwapAttachs());
					//设置签收人信息
					envelope.setSignerId(currentUser.getId());
					envelope.setSignerName(currentUser.getUserName());
					envelope.setSignerDeptId(currentUser.getDepartment().getId());
					envelope.setSignDate(new Date());
					//联系人
					if(bean.getContactName()!=null){
						docSwapContent.setContactName(bean.getContactName());
					}else{
						docSwapContent.setContactName(currentUser.getUserName());
					}
					if(bean.getContactPhone()!=null){
						docSwapContent.setContactPhone(bean.getContactPhone());
					}else{
						docSwapContent.setContactPhone(currentUser.getMobile());
					}
					if (currentUser.getDepartment() != null) {
						envelope.setSignerDeptName(currentUser.getDepartment().getDeptName());
					}
					//修改公文交换状态
					envelope.setEnvStatus(RemoteEnvStatus.已签收);
					this.repository.save(envelope);
					//生成反馈信息
					Map<String, Object> model=this.saveFeedBackInfo(envelope,docSwapContent,currentUser);
					//生成xml文件
					this.createFeedBackToXml(model,RemoteDocSwapContent.BUS_TYPE_SWHZ);
				}
			}
			return true;
		}
		return false;
	
	}
	
	/**
	 * 生成反馈信息
	 * @param envelope
	 * @param envelopeContent
	 * @param currentUser
	 */
	private Map<String, Object> saveFeedBackInfo(RemoteDocSwapEnvelope envelope, RemoteDocSwapContent envelopeContent, SysUser currentUser) {
		RemoteDocSwapEnvelope envelopeFeedback = new RemoteDocSwapEnvelope();
		RemoteDocSwapContent envelopeContentFeedback = new RemoteDocSwapContent();
		if (envelopeContent == null) {
			throw new RuntimeException("找不到封体文件");
		}
//		NullBeanUtils.copyProperties(envelopeFeedback,envelope);
		BeanUtils.copyProperties(envelope, envelopeFeedback, new String[] { "id" });
		//新增，将id赋空值
		envelopeFeedback.setId(null);
		envelopeFeedback.setEnvelopeType(RemoteEnvType.feedback);
		envelopeFeedback.setTitle("(回复)"+envelope.getTitle()+"_回复");
		envelopeFeedback.setSendTime(new Date());
		envelopeContentFeedback.setSignReceiveMan(currentUser.getUserName());
		if (currentUser.getDepartment() != null) {
			envelopeFeedback.setSignerDeptName(currentUser.getDepartment().getDeptName());
			envelopeContentFeedback.setSignReceiveDept(currentUser.getDepartment().getDeptName());
		}

		envelopeFeedback.setSenderAncestorName(this.unitIndentifierService.getDefUnitIdentifier(null).getUnitName());
		envelopeFeedback.setSenderAncestorOrg(unitIndentifierService.getDefUnitIdentifier(null).getUnitIdentifier());
		envelopeFeedback.setLinkEnvelopeId(envelope.getId());
		envelopeFeedback.setReceiveFlag(RemoteEnvRecFlag.关联);

		this.repository.save(envelopeFeedback);
//		NullBeanUtils.copyProperties(envelopeContentFeedback,envelopeContent);
		BeanUtils.copyProperties(envelopeContent, envelopeContentFeedback, new String[] { "id" });
		//新增，将id赋空值
		envelopeContentFeedback.setId(null);
		envelopeContentFeedback.setEnvelopeId(envelopeFeedback.getId());
		//联系人
		envelopeContentFeedback.setContactName(currentUser.getUserName());
		envelopeContentFeedback.setContactPhone(currentUser.getMobile());
		envelopeContentFeedback.setTitle(envelopeFeedback.getTitle());
		envelopeContentFeedback.setLetterId(UUID.randomUUID().toString());
		envelopeContentFeedback.setBussinessType(RemoteDocSwapContent.BUS_TYPE_SWHZ);
		envelopeContentFeedback.setResourceIden(RemoteDocSwapContent.BUS_TYPE_DZGW);
		envelopeContentFeedback.setConStatus(RemoteContentStatus.中间);
		envelopeContentFeedback.setSignReceiveDate(new Date());
		this.contentRepository.save(envelopeContentFeedback);
		envelopeContentFeedback.setRemoteDocSwapAttachs(null);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("envelope", envelopeFeedback);
		model.put("envelopeContent", envelopeContentFeedback);
		return model;
	}

	@Override
	public boolean signDocSwapList(DocSwapEnvelopeEditBean bean, SysUser currentUser) {
		if(bean!=null&&bean.getEnvelopeIds()!=null&&currentUser!=null) {
			String[] envelopeIds = bean.getEnvelopeIds().split(",");
			for (String idStr : envelopeIds) {
				Long id = new Long(idStr);
				bean.setId(id);
				this.signDocSwap(bean,currentUser);
			}
		}
		return true;
	}

	@Override
	public String saveFeedBackPaperDoc(OaDocFile obj,OaDocFileEditBean bean) {

		SysUser currentUser = (SysUser) UserTool.getInstance().getCurrentUser();
		if(obj!=null) {
//			RemoteUnitIdentifier senderUnitID=this.getDefUnitIdentifier(null);
//			if(senderUnitID==null){
//				throw new RuntimeException("找不到发送单位");
//			}
			String packageName = UUID.randomUUID().toString();
			RemoteDocSwapEnvelope envelope = new RemoteDocSwapEnvelope();
			envelope.setTitle(obj.getTitle());
			envelope.setIdentifier(bean.getIdentifier());
			envelope.setEnvelopeType(RemoteEnvType.cooperate);
			envelope.setEnvStatus(RemoteEnvStatus.未签收);
			envelope.setReceiveFlag(RemoteEnvRecFlag.纸质公文);
			envelope.setSenderAncestorOrg(unitIndentifierService.getDefUnitIdentifier(null).getUnitIdentifier());
			envelope.setEnvelopeUuid(packageName);
			envelope.setInstId(obj.getId());
			envelope.setSenderAncestorName(bean.getSendUnit());
			//设置发送人信息
			envelope.setSenderId(currentUser.getId());
			envelope.setSenderDept(currentUser.getDepartment().getId());
			envelope.setSenderName(currentUser.getUserName());
			if(bean.getSecondaryUnitIdentifierName()==null){
				envelope.setSecondaryUnitIdentifierName("自治区人民政府办公厅");
			}
			//
			if(bean.getDocumentType()==null){
				//默认办件
				envelope.setDocumentType(RemoteDocSwapType.办件);
			}else{
				envelope.setDocumentType(bean.getDocumentType());
			}
			
			//根据天数计算时间，已在控制器计算日期，这里只是防止日期获取不到
    		if (bean.getExpiryDate() == null && bean.getLimitDay() != null && bean.getLimitDay() > 0) {
    			Calendar calendar = Calendar.getInstance();
    			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + bean.getLimitDay());
    			bean.setExpiryDate(calendar.getTime());
    		}
			envelope.setLimitTime(bean.getExpiryDate());
			envelope.setDeadlineType(bean.getDeadlineType());

			// TODO: 2016/12/24 发送时间是否默认
			envelope.setSendTime(new Date());
			envelope.setSenderId(currentUser.getId());
			envelope.setSenderDept(currentUser.getDepartment().getId());
			envelope.setSenderName(currentUser.getUserName());
			envelope.setSenderAncestorOrg(unitIndentifierService.getDefUnitIdentifier(null).getUnitIdentifier());
			envelope.setEnvelopeUuid(packageName);
			this.repository.save(envelope);
			//将envelopeId放入对象中返回
//			bean.setEnvelopeId(envelope.getId());
			//封内文件
			RemoteDocSwapContent content = new RemoteDocSwapContent();
			content.setEnvelopeUuid(packageName);
			//文号格式
			if(obj.getDocCode()!=null) {
				String docNum = obj.getDocCode().replace("[", "〔").replace("]", "〕");
				content.setDocNumber(docNum);
			}
			content.setEnvelopeId(envelope.getId());
			content.setLetterId(UUID.randomUUID().toString());
			content.setBussinessType(RemoteDocSwapContent.BUS_TYPE_DZGW);
			content.setResourceIden(RemoteDocSwapContent.BUS_TYPE_DZGW);
			content.setSendOrg(unitIndentifierService.getUnitOrgFromIdentifier(unitIndentifierService
                    .getDefUnitIdentifier(null).getUnitIdentifier()));
			content.setSendOrgName(unitIndentifierService.getDefUnitIdentifier(null).getUnitName());
			content.setTitle(envelope.getTitle());

			content.setSecretLevel(DenseType.非涉密);
			content.setEmergencyLevel(bean.getEmergencyLevel());
			content.setPrintOrg(bean.getPrintOrg());
			content.setPrintOrgAbb(bean.getPrintOrgAbb());
			content.setPrintDate(bean.getPrintDate());
			content.setContactName(bean.getTypist());
			content.setContactPhone(bean.getTelephone());
			//纸质公文固定的反馈方007565768，123456789是测试环境
			//content.setFeedbackOrg("007565768");
			content.setFeedbackOrg("123456789");
			this.contentRepository.save(content);
			
			// 保存附件记录
			if(!Tools.isEmptyString(bean.getAttach())){
				Set<RemoteDocSwapAttach> attachList = new HashSet<RemoteDocSwapAttach>();
				String[] attachIds = bean.getAttach().split(",");
				for(int i = 0; i < attachIds.length; i++){
					SysAttach sysAttach = sysAttachRepository.findOne(attachIds[i]);
	                RemoteDocSwapAttach attach = new RemoteDocSwapAttach();
	                attach.setRemoteDocSwapContentId(content.getId());
	                attach.setRemoteAttachType(RemoteAttachType.其他);
	                attach.setSaveName(sysAttach.getSaveName());
	                attach.setFileSize(sysAttach.getFileSize());
	                attach.setTrueName(sysAttach.getTrueName());
	                attach.setSavePath(sysAttach.getSavePath());
	                attachList.add(attach);
	                attachService.getRepository().save(attach);
				}
				//将信息返回给封内对象，以便后续使用
				content.setRemoteDocSwapAttachs(attachList);
			}
			
			//生成xml文件
			String xmlType =RemoteDocSwapContent.BUS_TYPE_ZZGW;
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("envelope", envelope);
			model.put("envelopeContent", content);
			this.createFeedBackToXml(model,xmlType);
			return "反馈信息成功。";
		}
		return null;
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map statistic(String year) {
		Map result = new HashMap();
    	
		Date[] dateRange  = Tools.getYearRange(Integer.parseInt(year));
		
    	Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 3);
		
		
		//--------------------------------发文----------------------------
		SearchBuilder<RemoteDocSwapEnvelope, Long> sendSearchBuilder = new SearchBuilder<RemoteDocSwapEnvelope, Long>(conversionService);
    	//发文：未办结
		sendSearchBuilder.getFilterBuilder().equal("receiveFlag", RemoteEnvRecFlag.发送);
		sendSearchBuilder.getFilterBuilder().in("envStatus",
                new Object[] { RemoteEnvStatus.未签收,RemoteEnvStatus.已签收 });
		sendSearchBuilder.getFilterBuilder().greaterThan("sendTime", dateRange[0]);
		sendSearchBuilder.getFilterBuilder().lessThan("sendTime", dateRange[1]);
    	List<RemoteDocSwapEnvelope> notSendList = sendSearchBuilder.findAll(repository);
        Integer notSendNum = notSendList.size();
        
        //发文：已办结
        sendSearchBuilder.getFilterBuilder().equal("receiveFlag", RemoteEnvRecFlag.发送);
        sendSearchBuilder.getFilterBuilder().in("envStatus",
                new Object[] { RemoteEnvStatus.已办结,RemoteEnvStatus.不办理,RemoteEnvStatus.转阅件,RemoteEnvStatus.办结传阅,RemoteEnvStatus.办结归档 });
        List<RemoteDocSwapEnvelope> alreadySendList = sendSearchBuilder.findAll(repository);
        Integer alreadySendNum = alreadySendList.size();
        
        //发文：总数=未办结+已办结
        Integer sendTotalNum = notSendNum + alreadySendNum;
        
        //发文：超时未办结（办结时间<当前时间）
        sendSearchBuilder.getFilterBuilder().equal("receiveFlag", RemoteEnvRecFlag.发送);
        sendSearchBuilder.getFilterBuilder().in("envStatus",
                new Object[] { RemoteEnvStatus.未签收,RemoteEnvStatus.已签收 });
        sendSearchBuilder.getFilterBuilder().lessThan("limitTime", new Date());
    	List<RemoteDocSwapEnvelope> overtimeSendList = sendSearchBuilder.findAll(repository);
        Integer overtimeSendNum = overtimeSendList.size();
        
        //发文：已预警 = 未办结（办结时间 + 72小时  <当前时间）
        sendSearchBuilder.getFilterBuilder().equal("receiveFlag", RemoteEnvRecFlag.发送);
        sendSearchBuilder.getFilterBuilder().in("envStatus",
                new Object[] { RemoteEnvStatus.未签收,RemoteEnvStatus.已签收 });
        sendSearchBuilder.getFilterBuilder().lessThan("limitTime", calendar.getTime());
		List<RemoteDocSwapEnvelope> warnSendList = sendSearchBuilder.findAll(repository);
		Integer warnSendNum = warnSendList.size();
		
		//--------------------------------收文----------------------------
		SearchBuilder<RemoteDocSwapEnvelope, Long> receiveSearchBuilder = new SearchBuilder<RemoteDocSwapEnvelope, Long>(conversionService);
		//收文（电子和纸质）：未办结
		receiveSearchBuilder.getFilterBuilder().in("receiveFlag",
                new Object[] { RemoteEnvRecFlag.接收,RemoteEnvRecFlag.纸质公文 });
		receiveSearchBuilder.getFilterBuilder().in("envStatus",
    			new Object[] { RemoteEnvStatus.未签收,RemoteEnvStatus.已签收 });
		receiveSearchBuilder.getFilterBuilder().greaterThan("sendTime", dateRange[0]);
		receiveSearchBuilder.getFilterBuilder().lessThan("sendTime", dateRange[1]);
    	List<RemoteDocSwapEnvelope> notReceiveList = receiveSearchBuilder.findAll(repository);
        Integer notReceiveNum = notReceiveList.size();
        
        //收文（电子和纸质）：已办结
        receiveSearchBuilder.getFilterBuilder().in("receiveFlag",
                new Object[] { RemoteEnvRecFlag.接收,RemoteEnvRecFlag.纸质公文 });
        receiveSearchBuilder.getFilterBuilder().in("envStatus",
                new Object[] { RemoteEnvStatus.已办结,RemoteEnvStatus.不办理,RemoteEnvStatus.转阅件,RemoteEnvStatus.办结传阅,RemoteEnvStatus.办结归档 });
        List<RemoteDocSwapEnvelope> alreadyReceiveList = receiveSearchBuilder.findAll(repository);
        Integer alreadyReceiveNum = alreadyReceiveList.size();
        
        //收文（电子和纸质）：总数=未办结+已办结
        Integer receiveTotalNum = notReceiveNum + alreadyReceiveNum;
        
        //收文（电子和纸质）：超时未办结（办结时间<当前时间）
        receiveSearchBuilder.getFilterBuilder().in("receiveFlag",
                new Object[] { RemoteEnvRecFlag.接收,RemoteEnvRecFlag.纸质公文 });
        receiveSearchBuilder.getFilterBuilder().in("envStatus",
                new Object[] { RemoteEnvStatus.未签收,RemoteEnvStatus.已签收 });
        receiveSearchBuilder.getFilterBuilder().lessThan("limitTime", new Date());
    	List<RemoteDocSwapEnvelope> overtimeReceiveList = receiveSearchBuilder.findAll(repository);
        Integer overtimeReceiveNum = overtimeReceiveList.size();
        
        //收文（电子和纸质）：已预警 = 未办结（办结时间 + 72小时  <当前时间）
        receiveSearchBuilder.getFilterBuilder().in("receiveFlag",
                new Object[] { RemoteEnvRecFlag.接收,RemoteEnvRecFlag.纸质公文 });
        receiveSearchBuilder.getFilterBuilder().in("envStatus",
                new Object[] { RemoteEnvStatus.未签收,RemoteEnvStatus.已签收 });
		
        receiveSearchBuilder.getFilterBuilder().lessThan("limitTime", calendar.getTime());
		List<RemoteDocSwapEnvelope> warnReceiveList = receiveSearchBuilder.findAll(repository);
		Integer warnReceiveNum = warnReceiveList.size();
		
		result.put("notSendNum", notSendNum);
		result.put("alreadySendNum", alreadySendNum);
		result.put("sendTotalNum", sendTotalNum);
		result.put("warnSendNum", warnSendNum);
		result.put("overtimeSendNum", overtimeSendNum);
        
		result.put("notReceiveNum", notReceiveNum);
        result.put("alreadyReceiveNum", alreadyReceiveNum);
        result.put("receiveTotalNum", receiveTotalNum);
        result.put("warnReceiveNum", warnReceiveNum);
        result.put("overtimeReceiveNum", overtimeReceiveNum);
        result.put("beginDate", Tools.DATE_FORMAT3.format(dateRange[0]));
        result.put("endDate", Tools.DATE_FORMAT3.format(dateRange[1]));
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map statisticByOuter(String year) {
		Map result = new HashMap();
    	
		Date[] dateRange  = Tools.getYearRange(Integer.parseInt(year));
		
    	Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 3);
		
		
		//--------------------------------发文----------------------------
		//发文：未办结
        Integer notSendNum = 0;
        //发文：已办结
        Integer alreadySendNum = 0;
        //发文：总数
        Integer sendTotalNum = 0;
        //发文：超时未办结（办结时间<当前时间）
        Integer overtimeSendNum = 0;
        //发文：已预警 = 未办结（办结时间 + 72小时  <当前时间）
		Integer warnSendNum = 0;
		
		//--------------------------------收文----------------------------
		//收文（电子和纸质）：未办结
        Integer notReceiveNum = 0;
        //收文（电子和纸质）：已办结
        Integer alreadyReceiveNum = 0;
        //收文（电子和纸质）：总数=未办结+已办结
        Integer receiveTotalNum = 0;
        //收文（电子和纸质）：超时未办结（办结时间<当前时间）
        Integer overtimeReceiveNum = 0;
        //收文（电子和纸质）：已预警 = 未办结（办结时间 + 72小时  <当前时间）
		Integer warnReceiveNum = 0;
		
		
		DocStatisticQueryData querySendData = new DocStatisticQueryData();
		querySendData.setQueryType(DocStatisticQueryData.QUERY_TYPE_TOTAL_COUNT);
		querySendData.setStartDate(Tools.DATE_FORMAT.format(dateRange[0]));
		querySendData.setEndDate(Tools.DATE_FORMAT.format(dateRange[1]));
		
        try {
			docStatisticService.createQueryFile(querySendData);
			//从文件中读取发送的公文数据
			Map<String, Map<String,Integer>> resultMap = docStatisticService.readCountFile(querySendData);
			Map<String, Integer> sendMap = resultMap.get("sendMap");
			Map<String, Integer> receiveMap = resultMap.get("receiveMap");
	        if(Tools.isNotEmptyMap(sendMap)){
	        	notSendNum = sendMap.get("0");
	        	alreadySendNum = sendMap.get("1");
	        	overtimeSendNum = sendMap.get("4");
	        	sendTotalNum = sendMap.get("all");
	        }
	        
	        if(Tools.isNotEmptyMap(receiveMap)){
	        	notReceiveNum = receiveMap.get("0");
	        	alreadyReceiveNum = receiveMap.get("1");
	        	overtimeReceiveNum = receiveMap.get("4");
	        	receiveTotalNum = receiveMap.get("all");
	        }
	        
//	        sendTotalNum = notSendNum + alreadySendNum;
//	        receiveTotalNum = notReceiveNum + alreadyReceiveNum;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		result.put("notSendNum_outer", notSendNum);
		result.put("alreadySendNum_outer", alreadySendNum);
		result.put("sendTotalNum_outer", sendTotalNum);
		result.put("warnSendNum_outer", warnSendNum);
		result.put("overtimeSendNum_outer", overtimeSendNum);
        
		result.put("notReceiveNum_outer", notReceiveNum);
        result.put("alreadyReceiveNum_outer", alreadyReceiveNum);
        result.put("receiveTotalNum_outer", receiveTotalNum);
        result.put("warnReceiveNum_outer", warnReceiveNum);
        result.put("overtimeReceiveNum_outer", overtimeReceiveNum);
        result.put("beginDate_outer", Tools.DATE_FORMAT3.format(dateRange[0]));
        result.put("endDate_outer", Tools.DATE_FORMAT3.format(dateRange[1]));
		return result;
	}
	
	@Override
	public boolean createMultiRemoteDocSwap(DocSwapEnvelopeEditBean bean) {
		if(bean.getSecondaryUnitIdentifier()!=null&&bean.getSecondaryUnitIdentifierName()!=null){
			String[] unitIds = bean.getSecondaryUnitIdentifier().split(",");
			String[] unitNames = bean.getSecondaryUnitIdentifierName().split(",");
			if(unitIds.length==unitNames.length){
				for(int i = 0;i<unitIds.length;i++){
					bean.setSecondaryUnitIdentifier(unitIds[i]);
					bean.setSecondaryUnitIdentifierName(unitNames[i]);
					bean.setId(null);
					this.createRemoteDocSwap(bean);
					//使用频率加1
					this.addUnitFrequency(unitIds[i]);
				}
			}
			return true;
		}
		return false;
	
	}
	
	// 使用频率加1
	private void addUnitFrequency(String unitId) {
		RemoteUnitIdentifier unit = unitIndentifierService.getDefUnitIdentifier(unitId);
		if(unit!=null){
			if(unit.getFrequency()==null){
				unit.setFrequency(1);
			}else{
				unit.setFrequency(unit.getFrequency()+1);
			}
			unitIndentifierService.getRepository().save(unit);
		}
	}

	@Override
	public Page selectDocReceiveFinishPage(String title, String senderAncestorName, String signerName,
			Date sendBeginTime, Date sendEndTime, Date limitBeginTime, Date limitEndTime, Date signBeginDate,
			Date signEndDate, String documentType, String envStatus, String receiveFlag, String overTimeFlag,
			Integer page, Integer rows,String sort,String order) {

        StringBuilder sqlBuilder = new StringBuilder(" select ");
        sqlBuilder.append(" t.envelope.id, t.title,t.envelope.senderAncestorName,t.envelope.sendTime, ");
        sqlBuilder.append(" t.envelope.signerName,t.envelope.signDate ,t.envelope.documentType ,t.envelope.receiveFlag ,t.envelope.envStatus ,t.envelope.limitTime ,t.finishTime ");
        sqlBuilder.append(" from RemoteDocSwapContent t ");
        sqlBuilder.append(" where t.envelope.receiveFlag in('1','3') ");
        sqlBuilder.append(" and t.envelope.envStatus in ('3','4','5','6','7','8','9','10') ");
        
        if ("0".equals(overTimeFlag)){
//			//查询超时办结公文公文
        	sqlBuilder.append(" and t.finishTime > envelope.limitTime ");
		}
		if ("1".equals(overTimeFlag)){
//			//查询超时办结公文公文
        	sqlBuilder.append(" and (t.finishTime <= envelope.limitTime or t.finishTime is null) ");
		}
        	
        // 以下是动态查询
        if (StringUtils.isNotBlank(title)) {
            sqlBuilder.append(" and t.title like ?1 ");
        }
        if (StringUtils.isNotBlank(senderAncestorName)) {
            sqlBuilder.append(" and t.envelope.senderAncestorName like ?2 ");
        }
        if (StringUtils.isNotBlank(signerName)) {
        	sqlBuilder.append(" and t.envelope.signerName like ?3 ");
        }
        if (sendBeginTime != null) {
            sqlBuilder.append(" and t.envelope.sendTime >= ?4 ");
        }
        if (sendEndTime != null) {
            sqlBuilder.append(" and t.envelope.sendTime <= ?5 ");
        }
        if (limitBeginTime != null) {
        	sqlBuilder.append(" and t.envelope.limitTime >= ?6 ");
        }
        if (limitEndTime != null) {
        	sqlBuilder.append(" and t.envelope.limitTime <= ?7 ");
        }
        if (signBeginDate != null) {
        	sqlBuilder.append(" and t.envelope.signDate >= ?8 ");
        }
        if (signEndDate != null) {
        	sqlBuilder.append(" and t.envelope.signDate <= ?9 ");
        }
        if (StringUtils.isNotBlank(documentType)) {
            sqlBuilder.append(" and t.envelope.documentType = ?10 ");
        }
        if (StringUtils.isNotBlank(envStatus)) {
        	sqlBuilder.append(" and t.envelope.envStatus = ?11 ");
        }
        if (StringUtils.isNotBlank(receiveFlag)) {
        	sqlBuilder.append(" and t.envelope.receiveFlag = ?12 ");
        }
        
        
        if(StringUtils.isNotBlank(sort)){
        	sort = sort.replace("Show", "");
        	if ("title".equals(sort)) {
        		sort = "t.title";
            }else if ("sendTime".equals(sort)) {
        		sort = "t.envelope.sendTime";
            }else if ("signDate".equals(sort)) {
        		sort = "t.envelope.signDate";
            }
        	
        	sqlBuilder.append(" order by " + sort + " " + order);
        }else{
        	sqlBuilder.append(" order by t.finishTime desc,t.envelope.id desc ");
        }
        
        
        Query query = em.createQuery(sqlBuilder.toString());

        if (StringUtils.isNotBlank(title)) {
        	query.setParameter(1, "%" + title + "%");
        }
        if (StringUtils.isNotBlank(senderAncestorName)) {
        	query.setParameter(2, "%" + senderAncestorName + "%");
        }
        if (StringUtils.isNotBlank(signerName)) {
        	query.setParameter(3, "%" + signerName + "%");
        }
        if (sendBeginTime != null) {
        	query.setParameter(4, sendBeginTime);
        }
        if (sendEndTime != null) {
        	query.setParameter(5, sendEndTime);
        }
        if (limitBeginTime != null) {
        	query.setParameter(6, limitBeginTime);
        }
        if (limitEndTime != null) {
        	query.setParameter(7, limitEndTime);
        }
        if (signBeginDate != null) {
        	query.setParameter(8, signBeginDate);
        }
        if (signEndDate != null) {
        	query.setParameter(9, signEndDate);
        }
        if (StringUtils.isNotBlank(documentType)) {
        	query.setParameter(10, new RemoteDocSwapTypeConverter().convertToEntityAttribute(documentType));
        }
        if (StringUtils.isNotBlank(envStatus)) {
        	query.setParameter(11, new RemoteEnvStatusConverter().convertToEntityAttribute(envStatus));
        }
        if (StringUtils.isNotBlank(receiveFlag)) {
        	query.setParameter(12, new RemoteEnvRecFlagConverter().convertToEntityAttribute(receiveFlag));
        }

        List totalList = query.getResultList();
        int total = 0;
        if (totalList != null) {
            total = totalList.size();
        }
        query.setFirstResult((page - 1) * rows);
        query.setMaxResults(rows);
        List<DocSwapEnvelopeShowBean> noticeList = new ArrayList<DocSwapEnvelopeShowBean>();
        List list = query.getResultList();
        String[] names = { "id", "title", "senderAncestorName", "sendTime", "signerName", "signDate", "documentType", "receiveFlag", "envStatus", "limitTime","finishTime"};
        for (Object o : list) {
            Map<String, Object> map = toMap(names, (Object[]) o);
            DocSwapEnvelopeShowBean b = new DocSwapEnvelopeShowBean();
            try {
                PropertyUtils.copyProperties(b, map);
                noticeList.add(b);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        Page p = new Page();
        p.setRows(noticeList);
        p.setTotal(Long.valueOf(total));
        return p;
    
	}
	
	private Map<String, Object> toMap(String[] propertyName, Object[] list) {
        Map<String, Object> map = new HashedMap();
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < propertyName.length; j++) {
                map.put(propertyName[j], list[j]);
            }
        }
        return map;
    }
	
	
	//发送错误信息给指定人员
    private void sendErrorMsg(File envelopeFile) {
        String errInfo = "";
        if(envelopeFile==null){
            errInfo = "找不到封首文件";
        }else{
            errInfo = envelopeFile.getParentFile().getName()+" 文件读取错误。";
        }
        this.sendMsg(errInfo,RemoteDocSwapConfig.ERROR_DATA_RECIEVER);
    }
    
    @Override
    public void sendMsg(String msg,String recieverType) {
        Map<String, String> configMap = configService.findConfigByType(RemoteDocSwapConfig.CONFIG_TYPE_MSG);
        if(configMap!=null){
            String reciever = configMap.get(recieverType);
            String sender = configMap.get(RemoteDocSwapConfig.SEND_MSG_SENDER);
            if(!Tools.isEmptyString(reciever)&&!Tools.isEmptyString(sender)) {
                SmsBaseInfo smsBaseInfo = new SmsBaseInfo();
                smsBaseInfo.setMessage(msg);
                smsBaseInfo.setSendManId(new Long(sender));
                //定时发送标志 默认为：0 即时发送 1：为定时发送
                smsBaseInfo.setTimingSendFlag("0");
                smsBaseInfo.setTimingSendTime(new Date());
                smsBaseInfo.setReqDeliveryReport(SmsBaseInfo.REPORT_NOT_NEED);
                this.smsService.sendUserIdListMessage(reciever, smsBaseInfo);
            }
        }
    }
    
    @Override
    public boolean sendMessageToReceiver(String title, Date limitTime, RemoteUnitIdentifier unit) {
        Map<String, String> configMap = configService.findConfigByType(RemoteDocSwapConfig.CONFIG_TYPE_MSG);
        if (unit!=null&&configMap.get(RemoteDocSwapConfig.SEND_MSG_TMP) != null && configMap.get(RemoteDocSwapConfig.SEND_MSG_SENDER) != null) {
            String roleName = unit.getRoleName();
            if(roleName!=null){
                //接收单位对应的角色不为空
                String idString =configMap.get(roleName);
                if(idString!=null){
                    //接收人id不为空
                    if(title==null){
                        title = "交换公文";
                    }
                    String msgData = configMap.get(RemoteDocSwapConfig.SEND_MSG_TMP).replace(RemoteDocSwapConfig.REPLACE_TITLE,title);
                    //替换限办日期
                    if(limitTime!=null){
                        msgData = msgData.replace(RemoteDocSwapConfig.REPLACE_TIME, Tools.DATE_FORMAT9.format(limitTime));
                    }else{
                        msgData = msgData.replace(RemoteDocSwapConfig.REPLACE_TIME, "");
                    }
                    //去掉标题中开头的html标签
                    int index=msgData.indexOf(">");
                    if(index!=-1&&index<msgData.length()){
                        msgData = msgData.substring(index+1);
                    }
                    SmsBaseInfo smsBaseInfo = new SmsBaseInfo();
                    smsBaseInfo.setMessage(msgData);
                    smsBaseInfo.setSendManId(new Long(configMap.get(RemoteDocSwapConfig.SEND_MSG_SENDER)));
                    //定时发送标志 默认为：0 即时发送 1：为定时发送
                    smsBaseInfo.setTimingSendFlag("0");
                    smsBaseInfo.setTimingSendTime(new Date());
                    smsBaseInfo.setReqDeliveryReport(SmsBaseInfo.REPORT_NOT_NEED);
                    this.smsService.sendUserIdListMessage(idString, smsBaseInfo);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
	public void noticeByCalendar(Calendar calendar, boolean overtime) {
    	List<RemoteDocSwapEnvelope> list = this.findLimitDocList(calendar);
        if(Tools.isNotEmptyList(list)){
            for(RemoteDocSwapEnvelope envelope : list){
                //提醒签收人反馈情况（包括纸质公文）
                this.noticeDocSwapSigner(envelope);
            }
        }
        // 提醒当前办理人办理（）
        this.noticeDocSwapDealer(calendar,overtime);
	}
    
	@Override
	public void noticeDocSwapSigner(RemoteDocSwapEnvelope envelope) {
        RemoteDocSwapConfig config = configRepository.findByConfigName(RemoteDocSwapConfig.NOTICE_SIGNER_MSG);
        RemoteDocSwapConfig senderConfig = configRepository.findByConfigName(RemoteDocSwapConfig.SEND_MSG_SENDER);
        //签收人id不为空，短信提醒模板内容不为空，发送人id不为空
        if(envelope!=null&&config!=null&&config.getConfigValue()!=null&&senderConfig.getConfigValue()!=null){
            SmsBaseInfo smsBaseInfo = new SmsBaseInfo();
            String msgData = config.getConfigValue().replace(RemoteDocSwapConfig.REPLACE_TITLE,envelope.getTitle());
            if(envelope.getLimitTime()!=null){
                //有限办时间
                String limitTimeFmt = Tools.DATE_FORMAT9.format(envelope.getLimitTime());
                msgData = msgData.replace(RemoteDocSwapConfig.REPLACE_TIME,limitTimeFmt);
            }else{
                msgData = msgData.replace(RemoteDocSwapConfig.REPLACE_TIME,"");
            }
            //去掉标题中开头的html标签
            int index=msgData.indexOf(">");
            if(index!=-1&&index<msgData.length()){
                msgData = msgData.substring(index+1);
            }
            Long receiverId=null;
            //判断是纸质还是电子
            if(RemoteEnvRecFlag.接收.getKey().equals(envelope.getReceiveFlag())){
                receiverId=envelope.getSignerId();
            }else if(RemoteEnvRecFlag.纸质公文.getKey().equals(envelope.getReceiveFlag())){
                receiverId=envelope.getSenderId();
            }
            if(receiverId==null){
                return;
            }
            smsBaseInfo.setMessage(msgData);
            smsBaseInfo.setSendManId(Long.valueOf(senderConfig.getConfigValue()));
            //定时发送标志 默认为：0 即时发送 1：为定时发送
            smsBaseInfo.setTimingSendFlag("0");
            smsBaseInfo.setTimingSendTime(new Date());
            smsBaseInfo.setReqDeliveryReport(SmsBaseInfo.REPORT_NOT_NEED);
            this.smsService.sendUserIdListMessage(receiverId+"", smsBaseInfo);
        }
		
	}

	@Override
	public void noticeDocSwapDealer(Calendar calendar, boolean overtime) {

        String dateStr = Tools.DATE_FORMAT.format(calendar.getTime());
        List<OaDocFile> fileList = docFileRepository.findLimitDocFileList(dateStr);
        
        //发送短信提醒
        String msgTemplate = RemoteDocSwapConfig.NOTICE_SEND_MSG;
        if(overtime){
            msgTemplate = RemoteDocSwapConfig.OVERTIME_SEND_MSG;
        }
        //发送短信模板
        RemoteDocSwapConfig config = configRepository.findByConfigName(msgTemplate);
        //发送人
        RemoteDocSwapConfig senderConfig = configRepository.findByConfigName(RemoteDocSwapConfig.SEND_MSG_SENDER);

        for(OaDocFile docFile : fileList) {
            RemoteDocSwapLog docSwapLog = new RemoteDocSwapLog();
            docSwapLog.setLogType(RemoteDocSwapLogType.发送短信);
            //有待办的人员id
            String ids = "";
            
            //根据公文id查询当前在办环节的办理人id
            List<WfNodeInst> nodeInstList = nodeInstService.findByEntityIdAndInstStatus(docFile.getId(), WfInstStatus.在办);
            for(WfNodeInst nodeInst : nodeInstList){
                ids+=nodeInst.getDealerId()+",";
            }
            if(!Tools.isEmptyString(ids)){
                ids = ids.substring(0,ids.length()-1);
            }
            
            RemoteDocSwapEnvelope envelope = repository.findByInstId(docFile.getId());
            
            //ids不为空，短信提醒模板内容不为空，发送人id不为空
            if(envelope == null){
                docSwapLog.setLog("定时短信提醒发送失败。电子公文空。");
                docSwapLog.setMemo("待办id:"+docFile.getId()+",待办标题:"+docFile.getTitle());
                logRepository.save(docSwapLog);
            }else if(Tools.isEmptyString(ids)){
                docSwapLog.setLog("定时短信提醒发送失败。发送人id为空。");
                docSwapLog.setMemo("待办id:"+docFile.getId()+",待办标题:"+docFile.getTitle());
                logRepository.save(docSwapLog);
            }else if(config==null||config.getConfigValue()==null||senderConfig.getConfigValue()==null){
                docSwapLog.setLog("定时短信提醒发送失败。短信模板为空。");
                docSwapLog.setMemo("待办id:"+docFile.getId()+",待办标题:"+docFile.getTitle());
                logRepository.save(docSwapLog);
            }else{
                SmsBaseInfo smsBaseInfo = new SmsBaseInfo();
                String msgData = config.getConfigValue().replace(RemoteDocSwapConfig.REPLACE_TITLE,docFile.getTitle());
                if(docFile.getExpiryDate()!=null){
                    //有限办时间
                    String limitTimeFmt = Tools.DATE_FORMAT9.format(docFile.getExpiryDate());
                    msgData = msgData.replace(RemoteDocSwapConfig.REPLACE_TIME,limitTimeFmt);
                }else{
                    msgData = msgData.replace(RemoteDocSwapConfig.REPLACE_TIME,"");
                }
                //去掉标题中开头的html标签
                int index=msgData.indexOf(">");
                if(index!=-1&&index<msgData.length()){
                    msgData = msgData.substring(index+1);
                }
                smsBaseInfo.setMessage(msgData);
                smsBaseInfo.setSendManId(Long.valueOf(senderConfig.getConfigValue()));
                //定时发送标志 默认为：0 即时发送 1：为定时发送
                smsBaseInfo.setTimingSendFlag("0");
                smsBaseInfo.setTimingSendTime(new Date());
                smsBaseInfo.setReqDeliveryReport(SmsBaseInfo.REPORT_NOT_NEED);
                this.smsService.sendUserIdListMessage(ids, smsBaseInfo);
            }

        }
		
	}

	@Override
	public List<RemoteDocSwapEnvelope> findLimitDocList(Calendar calendar) {
		if(calendar!=null) {
            String dateStr = Tools.DATE_FORMAT.format(calendar.getTime());
            List<RemoteDocSwapEnvelope> envelopeList = this.repository.findLimitDocList(dateStr);
            return envelopeList;
        }else{
            return null;
        }
	}

	@Override
	public void saveFordoForDocSwap(String nextDealers,String nextNodeId,RemoteDocSwapEnvelope envelope) {
        RemoteDocSwapConfig config = configRepository.findByConfigName(RemoteDocSwapConfig.FORDO_SEND_MSG);
        RemoteDocSwapConfig ignoreConfig = configRepository.findByConfigName(RemoteDocSwapConfig.IGNORE_ORDO_SEND_MSG);

        String msgData = null;
        if(config!=null&&config.getConfigValue()!=null){
            if(ignoreConfig!=null&&ignoreConfig.getConfigValue()!=null&&ignoreConfig.getConfigValue().contains(nextNodeId)){
                //忽略某个环节不发送短信
                msgData = "";
            }else{
                msgData = config.getConfigValue().replace(RemoteDocSwapConfig.REPLACE_TITLE,envelope.getTitle());
                if(envelope!=null&&envelope.getSelfLimitTime()!=null){
                    //有限办时间
                    String limitTimeFmt = Tools.DATE_FORMAT.format(envelope.getSelfLimitTime());
                    msgData=msgData.replace(RemoteDocSwapConfig.REPLACE_TIME,limitTimeFmt);
                }else{
                    msgData = msgData.replace(RemoteDocSwapConfig.REPLACE_TIME,"");
                }
                //去掉标题中开头的html标签
                int index=msgData.indexOf(">");
                if(index!=-1&&index<msgData.length()){
                    msgData = msgData.substring(index+1);
                }
            }
        }

        SysUser currentUser = (SysUser) UserTool.getInstance().getCurrentUser();
        if(envelope!=null&&envelope.getSelfLimitTime()!=null&& !Tools.isEmptyString(msgData)) {
            SmsBaseInfo smsBaseInfo = new SmsBaseInfo();
            smsBaseInfo.setMessage(msgData);
            smsBaseInfo.setSendManId(currentUser.getId());
            //定时发送标志 默认为：0 即时发送 1：为定时发送
            smsBaseInfo.setTimingSendFlag("0");
            smsBaseInfo.setTimingSendTime(new Date());
            smsBaseInfo.setReqDeliveryReport(SmsBaseInfo.REPORT_NOT_NEED);
            this.smsService.sendUserIdListMessage(nextDealers + "", smsBaseInfo);
        }
        
	}
	
}
