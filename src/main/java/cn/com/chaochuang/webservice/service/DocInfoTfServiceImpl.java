package cn.com.chaochuang.webservice.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.repository.SysDepartmentRepository;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.event.service.OaDocFileService;
import cn.com.chaochuang.oa.message.app.service.AppMessageService;
import cn.com.chaochuang.webservice.bean.DeptNodesInfo;
import cn.com.chaochuang.webservice.bean.DocFileInfo;
import cn.com.chaochuang.webservice.bean.DocFileUpdate;
import cn.com.chaochuang.webservice.bean.NameAndIdEntity;

/**
 * @author huangwq
 *
 */
@Component
@WebService
public class DocInfoTfServiceImpl implements DocInfoTfService {

    @Autowired
    protected OaDocFileService      docFileService;

    // @Autowired
    // private AuditOpinionService docOpinionService;

    @Autowired
    private SysUserService          userService;

    // @Autowired
    // private NodeSettingService nodeFuncService;
    //
    // @Autowired
    // private FlowIncepterService flowIncepterService;

    @Autowired
    private AppMessageService       appMessageService;

    @Autowired
    private LogService              logService;

    @Autowired
    private SysDepartmentRepository deptRepository;

    @Override
    public String getDocInfo(String fileId) {
        DocFileInfo info = new DocFileInfo();
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        String docId = null;
        if (fileId != null) {
            OaDocFile sendfile = docFileService.findOne(fileId);
            if (sendfile != null) {
                docId = sendfile.getId();
                info.setTitle(sendfile.getTitle());
                info.setDocNumber(sendfile.getDocCode());
                if (sendfile.getUrgencyLevel() != null)
                    info.setEmergencyLevel(sendfile.getUrgencyLevel().getValue());
                if (sendfile.getDense() != null)
                    info.setSecretLevel(sendfile.getDense().getValue());
                if (sendfile.getExpiryDate() != null)
                    info.setLimitDate(Tools.DATE_FORMAT.format(sendfile.getExpiryDate()));
                info.setDocType(sendfile.getFileType().getKey());
                info.setRmInstanceId(sendfile.getId());
                info.setRedactor(sendfile.getCreatorName());
                info.setRedactDate(sendfile.getCreateDate());
                // info.setRedactOpinion(sendfile.getCreateInfo);
                if (sendfile.getOpenFlag() != null)
                    info.setPublish(sendfile.getOpenFlag().getValue());
                info.setCreateDate(sendfile.getCreateDate());
                info.setMainSend(sendfile.getMainSend());
                info.setCopySend(sendfile.getCopySend());
                info.setProcessNumber(sendfile.getSncode());
            }

            List nextList = null;
            try {
                // 历史轨迹

                // 返回下一环节列表供选择

            } catch (Exception e) {
                e.printStackTrace();
            }
            info.setRemoteFlowNode(nextList);
            // 保存意见
            // info.setRemoteFlowOpinions(docOpinon(fordo, docId));
            try {
                json = mapper.writeValueAsString(info) + ",";
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        if (!json.equals("")) {
            return "[" + json.substring(0, json.length() - 1) + "]";
        }
        return json;

    }

    @Override
    public String sumitDocFile(String fordoId, String receiveMans, String nextNodeId, String opinions, Long senderId) {
        DocFileUpdate bean = new DocFileUpdate();
        bean.setFordoId(Long.valueOf(fordoId));
        bean.setReceiveMans(receiveMans);
        bean.setNextNodeId(nextNodeId);
        bean.setOpinions(opinions);
        bean.setSenderId(senderId);
        String returnInfo = this.saveTaskDeal(bean);
        return returnInfo;
    }

    // 可选择的部门和人员
    private List<DeptNodesInfo> depUserInfo() {

        List<DeptNodesInfo> nodeList = new ArrayList<DeptNodesInfo>();
        // 办公厅部门
        List<SysDepartment> depList = (List<SysDepartment>) this.deptRepository.findAll();
        for (SysDepartment dep : depList) {
            DeptNodesInfo nodeinfo = new DeptNodesInfo();
            nodeinfo.setDeptName(dep.getDeptName());
            // 部门下的人员
            List<NameAndIdEntity> userList = new ArrayList<NameAndIdEntity>();
            List<SysUser> uses = userService.findBydetpId(dep.getId());
            for (SysUser u : uses) {
                NameAndIdEntity entity = new NameAndIdEntity();
                entity.setId(u.getId().toString());
                entity.setName(u.getUserName());
                userList.add(entity);
            }
            nodeinfo.setUsersList(userList);
            nodeList.add(nodeinfo);
        }
        return nodeList;
    }


    public String saveTaskDeal(DocFileUpdate bean) {

        // 办理

        // 写入待办事项
        // docFileService.saveFordoData(docbean, cu, bean.getSenderId());

        // logService.saveDefaultLog("从手机端发文任务办理：" + bean.getSenderId() + "，环节ID：'" + bean.getSenderId() + "，实例ID："
        // + fordo.getFlowInstId(), null);
        return null;
    }

    @Override
    public String getFinishDocInfo(String fileId, String fileType) {
        DocFileInfo info = new DocFileInfo();
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        String docId = null;
        OaDocFile sendfile = docFileService.findOne(fileId);
        if (sendfile != null) {
            docId = sendfile.getId();
            info.setTitle(sendfile.getTitle());
            info.setDocNumber(sendfile.getDocCode());
            if (sendfile.getUrgencyLevel() != null)
                info.setEmergencyLevel(sendfile.getUrgencyLevel().getValue());
            if (sendfile.getDense() != null)
                info.setSecretLevel(sendfile.getDense().getValue());
            if (sendfile.getExpiryDate() != null)
                info.setLimitDate(Tools.DATE_FORMAT.format(sendfile.getExpiryDate()));
            info.setDocType(sendfile.getFileType().getKey());
            info.setRmInstanceId(sendfile.getId());
            info.setRedactor(sendfile.getCreatorName());
            info.setRedactDate(sendfile.getCreateDate());
            if (sendfile.getOpenFlag() != null)
                info.setPublish(sendfile.getOpenFlag().getValue());
            info.setCreateDate(sendfile.getCreateDate());
            info.setMainSend(sendfile.getMainSend());
            info.setCopySend(sendfile.getCopySend());
            info.setProcessNumber(sendfile.getSncode());
        }

        // 办理轨迹
        // List<TaskInfoBean> tkList = this.docWorkflowService.getHistoryTasksByFileId(fileId);
        // 保存意见
        // info.setRemoteFlowOpinions(tkList);

        try {
            json = mapper.writeValueAsString(info) + ",";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (!json.equals("")) {
            return "[" + json.substring(0, json.length() - 1) + "]";
        }
        return json;
    }

    public byte[] uploadStreamAttachFile(String savePath, Long offset, Integer reads) {
        File f = new File(savePath);
        try {
            return FileUtils.readFileToByteArray(f);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
