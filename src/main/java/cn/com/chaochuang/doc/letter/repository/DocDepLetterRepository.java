/*
 * FileName:    DocDepLetterRepository.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年11月25日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.doc.letter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.doc.letter.domain.DocDepLetter;

/**
 * @author LJX
 *
 */
public interface DocDepLetterRepository extends SimpleDomainRepository<DocDepLetter, String> {

    /**
     * 根据fileId查找，按创建日期降序
     * 
     * @param fileId
     * @return
     */
    public List<DocDepLetter> findByFileIdOrderByCreateDateDesc(String fileId);

    /**
     * 根据fileId，查找函件数量
     * 
     * @param fileId
     * @return
     */
    @Query("select count(l.id) from DocDepLetter l where l.fileId=:fileId")
    public Integer countByFileId(@Param("fileId") String fileId);
}
