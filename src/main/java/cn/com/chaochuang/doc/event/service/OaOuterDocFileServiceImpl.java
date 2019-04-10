package cn.com.chaochuang.doc.event.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.event.bean.OaDocFileEditBean;
import cn.com.chaochuang.doc.event.bean.OuterDocFileEditBean;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.event.domain.OaOuterDocFile;
import cn.com.chaochuang.doc.event.domain.OaOuterDocFileFeedBack;
import cn.com.chaochuang.doc.event.reference.OuterDocStatus;
import cn.com.chaochuang.doc.event.repository.OaDocFileRepository;
import cn.com.chaochuang.doc.event.repository.OaOuterDocFileFeedBackRepository;
import cn.com.chaochuang.doc.event.repository.OaOuterDocFileRepository;
import cn.com.chaochuang.doc.process.service.DocProcessService;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapAttach;
import cn.com.chaochuang.workflow.inst.domain.WfFlowInst;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.inst.service.WfFlowInstService;
import cn.com.chaochuang.workflow.inst.service.WfNodeInstService;
import cn.com.chaochuang.workflow.reference.WfInstStatus;
import cn.com.chaochuang.workflow.util.WorkflowUtils;
import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.common.bean.ReturnBean;

/**
 * @author yuchunshu 2017.11.25
 *
 */
@Service
@Transactional
public class OaOuterDocFileServiceImpl extends SimpleUuidCrudRestService<OaOuterDocFile> implements OaOuterDocFileService {

    @Autowired
    private OaOuterDocFileRepository    		repository;
    
    @Autowired
    private OaDocFileRepository    				docRepository;

    @Autowired
    protected ConversionService    				conversionService;

    @Autowired
    protected DocProcessService    				docProcessService;

    @Autowired
    private WfFlowInstService      				flowInstService;

    @Autowired
    private WfNodeInstService      				nodeInstService;
    
    @Autowired
    private OaOuterDocFileFeedBackRepository    feedBackRepository;
    
    @Autowired
    private SysAttachService    				sysAttachService;
    
    @Override
    public SimpleDomainRepository<OaOuterDocFile, String> getRepository() {
        return this.repository;
    }


    @Override
    public ReturnBean saveOuterDocFile(OaDocFileEditBean bean) {
        OaDocFile docFile = null;
    	docFile = this.docRepository.findOne(bean.getId());

        if (docFile == null) {
            return new ReturnBean("公文不存在，无法执行操作！", docFile, false);
        }
        
        //获取主流程id,生成系统外发的环节实例，作为跟踪外发后流程的签收状态
        WfFlowInst flowInst = flowInstService.getMainFlowInstByEntityId(docFile.getId());
        Date now = new Date();
        
        SysUser user = bean.getCurrentUser();
        
        if(!Tools.isEmptyString(bean.getDeptIds())){
    		String[] deptIdsArr = bean.getDeptIds().split(",");
        	String[] deptNamesArr = bean.getDeptNames().split(",");
        	if(deptIdsArr.length == deptNamesArr.length){
        		for(int i=0;i<deptIdsArr.length;i++){
        			
        			//新建系统外发的流程环节实例：环节是系统外发，状态为未签收
        			WfNodeInst nodeInst = new WfNodeInst();
    		        nodeInst.setFlowInstId(flowInst.getId());
    				nodeInst.setInstStatus(WfInstStatus.未签收);
    				nodeInst.setCurNodeId(WorkflowUtils.NODE_OUTER);
    				nodeInst.setPreDealerId(user.getId());
    				nodeInst.setDealDeptId(Long.parseLong(deptIdsArr[i]));
    				nodeInst.setArrivalTime(now);
    				nodeInst = nodeInstService.getRepository().save(nodeInst);
        			
    				//保存系统外发记录，保存环节id
        	        OaOuterDocFile obj = new OaOuterDocFile();
	        		BeanUtils.copyProperties(docFile, obj);
    				obj.setReceiveDeptId(Long.parseLong(deptIdsArr[i]));
    				obj.setReceiveDeptName(deptNamesArr[i]);
    				obj.setSenderId(user.getId());
    				obj.setSenderName(user.getUserName());
    				obj.setSenderDeptId(user.getDepartment().getId());
    				obj.setSenderDeptName(user.getDepartment().getDeptName());
    				obj.setSendTime(new Date());
    				obj.setFileId(docFile.getId());
    				obj.setNodeInstId(nodeInst.getId());
    				obj.setStatus(OuterDocStatus.未签收);
    				
    				obj.setUrgencyLevel(bean.getUrgencyLevel());
    				obj.setExpiryDate(bean.getExpiryDate());
    				obj.setDeadlineType(bean.getDeadlineType());
    				obj.setLimitDay(bean.getLimitDay());
    				repository.save(obj);
    				
        		}
        	}
    	}

        return new ReturnBean("保存成功", null, true);
    }


	@Override
	public boolean signOuterDoc(OuterDocFileEditBean bean, SysUser currentUser) {
		OaOuterDocFile obj = repository.findOne(bean.getId().toString());
//		SysUser currentUser = (SysUser) UserTool.getInstance().getCurrentUser();
		Date now = new Date();
		OaOuterDocFileFeedBack feedBack = new OaOuterDocFileFeedBack();
		
		obj.setStatus(OuterDocStatus.已签收);
		obj.setSignerName(currentUser.getUserName());
		obj.setSignDate(now);
		repository.save(obj);
		
		
		//记录签收人和签收时间
		feedBack.setFeedbackType(OuterDocStatus.已签收);
		feedBack.setSignerId(currentUser.getId());
		feedBack.setSignerName(currentUser.getUserName());
		feedBack.setSignerDeptId(currentUser.getDepartment().getId());
		feedBack.setSignerDeptName(currentUser.getDepartment().getDeptName());
		feedBack.setSignDate(now);
		feedBack.setContactName(bean.getContactName());
		feedBack.setContactPhone(bean.getContactPhone());
		feedBack.setOuterId(obj.getId());
		feedBack.setCreateTime(now);
		feedBackRepository.save(feedBack);
		
		//修改外单位来文环节实例的状态：未签收——>已签收
		WfNodeInst nodeInst = nodeInstService.findOne(obj.getNodeInstId());
		nodeInst.setInstStatus(WfInstStatus.已签收);
		nodeInst.setDealTime(new Date());
		nodeInstService.getRepository().save(nodeInst);
		return false;
	}


	@Override
	public boolean signOuterDocList(OuterDocFileEditBean bean, SysUser currentUser) {
//		if(!Tools.isEmptyString(ids)) {
//			String[] idsArr = ids.split(",");
//			for (String idStr : idsArr) {
//				this.signOuterDoc(idStr);
//			}
//		}
//		return true;
		
		if(bean!=null&&bean.getIds()!=null&&currentUser!=null) {
			String[] ids = bean.getIds().split(",");
			for (String idStr : ids) {
				bean.setId(idStr);
				this.signOuterDoc(bean,currentUser);
			}
		}
		return true;
	}


	@Override
	public List<SysAttach> copyAttach(String outerId) {
		OaOuterDocFile obj = repository.findOne(outerId);
		List<SysAttach> attachs = new ArrayList<SysAttach>();
		if(null != obj){
			attachs = sysAttachService.findByOwnerIdAndOwnerType(obj.getFileId(), "OaDocFile");
		}
		
        List<SysAttach> sysAttachList = new ArrayList<SysAttach>();

        if(Tools.isNotEmptyList(attachs)){
        	for(SysAttach attach:attachs){
        		SysAttach sysAttach = new SysAttach();
        		BeanUtils.copyProperties(attach, sysAttach, new String[] { "id" });
//        		sysAttach.setFileSize(0L);
//        		sysAttach.setIsImage(IsImage.非图文公告);
//        		sysAttach.setAttachType(AttachUtils.getFileSuffix(attach.getTrueName()));
        		sysAttachList.add(sysAttach);
        		sysAttachService.getRepository().save(sysAttach);
        	}
        }
		
		return sysAttachList;
	}

}
