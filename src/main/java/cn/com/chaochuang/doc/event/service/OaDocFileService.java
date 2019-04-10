/*
 * FileName:    OaDocFileService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.event.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;

import cn.com.chaochuang.common.bean.ReturnBean;
import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.doc.event.bean.FileShowBean;
import cn.com.chaochuang.doc.event.bean.OaDocFileEditBean;
import cn.com.chaochuang.doc.event.bean.OaDocFileQueryBean;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.workflow.reference.WfBusinessType;

/**
 * @author huangwq
 *
 */

public interface OaDocFileService extends CrudRestService<OaDocFile, String> {

    public ReturnBean saveDocFile(OaDocFileEditBean bean);

    public ReturnBean updateDocFileInfo(OaDocFileEditBean bean, HttpServletRequest request);

    public void save(OaDocFile obj);

    public OaDocFile findByProcInstId(String procInstId);

    public boolean delDocFile(String id);

    public boolean delDocFile(String id, boolean force);

    public List selectReceiveType();

    public void saveReadMatter(String userId, FileShowBean bean);

    /** 办结 */
    public void finishTheDoc(String id);

    /** 将在办公文删除流程相关信息，并恢复回暂存状态 */
    public void resetTheFlowDoc(String procInstId);

    /** 通过流程定义ID查询相关公文 */
    public List<OaDocFile> selectFlowDocByProcDefId(String procDefId);

    public List<OaDocFile> selectDocFile(String id, HttpServletRequest request, Integer page, Integer rows);

    /**
     * 根据公文类型和年份生成新的收文号
     *
     * @param fileType
     * @param fileYear
     * @return
     */
    public String generateNewFileCode(WfBusinessType fileType, String fileYear);

    /**
     * 查询公文信息
     *
     * @param bean
     * @return
     */
    public List<OaDocFile> selectDocFiles(OaDocFileQueryBean bean);

    /**
     * 分页查询公文信息
     *
     * @param bean
     * @param page
     * @param rows
     * @param sort
     * @param order
     * @return
     */
    public Page<OaDocFile> selectDocFilesPage(OaDocFileQueryBean bean, Integer page, Integer rows,String sort,String order);

    /**
     * 根据公文字号和类型，查询公文数据
     *
     * @param docCode
     * @param fileType
     * @return
     */
    public Integer selectCountByDocCodeAndFileTypeExpId(String docCode, WfBusinessType fileType, String id);

    /**
     * 保存全文检索索引
     *
     * @param file
     */
    public void saveLuceneIndex(OaDocFile file);

    /**
     * 删除全文检索索引
     *
     * @param file
     */
    public void removeLuceneIndex(OaDocFile file);

    
    /** 
     * @Title: docMove 
     * @Description: 公文办理迁移
     * @param nodeInstId
     * @param flowInstId
     * @param userId
     * @return
     */
    public void docMove(String nodeInstId,String flowInstId,Long userId);
    
    /** 
     * @Title: docTransfer 
     * @Description: 公文办理转移
     * @param nodeInstId
     * @param flowInstId
     * @param userId
     * @return
     */
    public void docTransfer(String nodeInstId,String flowInstId,Long userId);
    
    /**
     * 自动生成字号(docCode3)
     * @param docCode
     * @param fileType
     * @param id
     * @return
     */
    public String createDocCode(String codeName,String year, WfBusinessType fileType, String id);
    
}