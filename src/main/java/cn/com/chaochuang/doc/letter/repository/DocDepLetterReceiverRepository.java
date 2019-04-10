/*
 * FileName:    DocDepLetterReceiverRepository.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年11月25日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.doc.letter.repository;

import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.doc.letter.domain.DocDepLetterReceiver;

/**
 * @author LJX
 *
 */
public interface DocDepLetterReceiverRepository extends SimpleDomainRepository<DocDepLetterReceiver, String> {

    public DocDepLetterReceiver findByLetterIdAndReceiverDeptId(String letterId, Long deptId);

    public List<DocDepLetterReceiver> findByLetterId(String letterId);

    /**
     * 根据部门函件公文id查询接收
     * 
     * @param fileId
     * @return
     */
    public List<DocDepLetterReceiver> findByLetterFileIdOrderByLetterCreateDateDesc(String fileId);
}
