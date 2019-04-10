/*
 * FileName:    TransferService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年5月25日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.webservice.service;

import javax.jws.WebService;

/**
 * @author huangwq
 *
 */
@WebService
public interface TransferService {
    /**
     * 获取词条更改数据
     *
     * @param changeScript
     * @return
     */

    public String getDocLemmaInfo(String changeScript);

    /**
     * 获取词条数据
     *
     * @param id
     * @return
     */
    public String getPersonalWordData(Long id);

    /**
     * 获取会议信息数据
     */
    public String getPublicDataInfo(String id);

    /**
     * 获取公共通讯录数据
     *
     * @param id
     * @return
     */
    public String getContactCardsData(Long id);

    /**
     * 获取待办事宜信息
     *
     * @param forDoId
     * @param oaPendingHandleDtsId
     * @return
     */
    public String selectPendingItemInfo(String oaPendingHandleDtsId);

    /**
     * 获取公文办理数据
     *
     * @param jsonStr
     *            参数Json
     * @return
     */
    public String getDocTransactInfo(String instNoId, String lastOutputTime);

    /**
     * 获取公共通讯录数据
     *
     * @return
     */
    public String getDepLinkmanInfo(String changeScript);

    /**
     * 获取OA公告变更信息
     *
     * @param changeScript
     *
     * @return
     */
    public String getChangePubInfo(String changeScript);

    /**
     * 获取公文的附件
     *
     * @param fileName
     * @param offset
     * @param reads
     * @return
     */
    public byte[] uploadStreamAttachFile(String fileName, Long offset, Integer reads);

    /**
     * 获取OA用户表变更的数据
     *
     * @param changeScript
     * @return
     */
    public String getChangeUser(String changeScript);

    /**
     * 获取OA组织机构表变更的数据
     *
     * @param changeScript
     * @return
     */
    public String getChangeDepartment(String changeScript);

    /**
     * 获取OA的已办节点信息
     */
    public String getOAHistoryNodes(Long instId);

    /**
     * 获取OA的改变数据
     *
     * @param lastOutputTime
     * @return
     */
    public String getDataChange();

    /**
     * 可以选择的人员信息
     *
     */
    public String getSelectNodesAndButtonInfo(String nextNodeInfo);

    /**
     * 获取OA的法律法规信息
     *
     * @return
     */
    public String getOAPolicyInfo(String lastInputTime);

    /**
     * 获取法律法规附件
     *
     * @param MaxRmPolicyId
     * @return
     */
    public String getPolicyAttachFile(String MaxRmPolicyId);

    /**
     * 获取法律法规数据变更
     */
    public String getChangePolicyInfo(String changeScript);

    /**
     * 提交公文办理数据
     *
     * @param jsonStr
     *            参数Json
     * @return
     */
    public String setDocTransactInfo(String jsonStr);

    /**
     * 根据流程实例id查看远程OA是否可以取回
     * */
    public String isRollback(String instId, Long userId);

    /**
     * 取公文附件信息
     */
    public String getDocAttachInfo(Long maxRmAttachId);

    /**
     * 获取通知公告附件信息
     *
     * @return
     */
    public String getPubInfoAttachInfo(Long id);

    /**
     * 获取已办公文
     */
    public String getFinishDocFile(String lastInputTime);
}
