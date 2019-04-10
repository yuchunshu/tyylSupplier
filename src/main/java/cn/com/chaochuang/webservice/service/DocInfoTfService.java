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
public interface DocInfoTfService {
    /**
     * 根据id 取公文信息
     */
    public String getDocInfo(String fileId);

    /**
     * 办理公文
     *
     * @param fordoId
     *            原待办id
     * @param receiveMans
     *            下一环节办理人id
     * @param nextNoteId
     *            下一环节id
     * @param opinions
     *            审批意见
     * @param senderId
     *            发送人
     * @return
     */
    public String sumitDocFile(String fordoId, String receiveMans, String nextNodeId, String opinions, Long senderId);

    /**
     * 根据公文id 取已办公文信息
     */
    public String getFinishDocInfo(String fileId, String fileType);

    /**
     * 读取公文正文
     *
     * @param fileName
     * @param offset
     * @param reads
     * @return
     */
    public byte[] uploadStreamAttachFile(String fileName, Long offset, Integer reads);
}
