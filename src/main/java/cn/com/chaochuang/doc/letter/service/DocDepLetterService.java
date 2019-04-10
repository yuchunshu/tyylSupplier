/*
 * FileName:    DocDepLetterService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年11月25日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.doc.letter.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.doc.letter.bean.DepLetterInfo;
import cn.com.chaochuang.doc.letter.domain.DocDepLetter;
import cn.com.chaochuang.doc.letter.domain.DocDepLetterReceiver;

/**
 * @author LJX
 *
 */
public interface DocDepLetterService extends CrudRestService<DocDepLetter, String> {

    public String saveDepLetter(DepLetterInfo info);

    /**
     * 部门反馈
     *
     * @param info
     * @return
     */
    public String reversion(DepLetterInfo info);

    /**
     * 变更状态 如：签收 ，需补充
     *
     * @param receiveId
     * @param content
     * @return
     */
    public String changeReceiveStatus(DepLetterInfo info);

    public void deleteLetter(String[] ids);

    /**
     * 根据fileId查找部门函件
     *
     * @param fileId
     * @return
     */
    public List<DocDepLetter> selectLettersByFileId(String fileId);

    /**
     * 根据函件ID查询接收
     *
     * @param letterId
     * @return
     */
    public List<DocDepLetterReceiver> selectReceiversByLetterId(String letterId);

    /**
     * 根据公文ID查询部门函件接收
     *
     * @param fileId
     * @return
     */
    public List<DocDepLetterReceiver> selectReceiversByFileId(String fileId);

    /**
     * 根据公文Id查询相关部门函件数量
     */
    public Integer countLettersByFileId(String fileId);

}
