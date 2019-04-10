/*
 * FileName:    OaReceiveFileRepository.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.event.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.event.reference.FileStatusFlag;
import cn.com.chaochuang.workflow.reference.WfBusinessType;

/**
 * @author huangwq
 *
 */
public interface OaDocFileRepository extends SimpleDomainRepository<OaDocFile, String> {

    OaDocFile findByFlowInstId(String procInstId);

    OaDocFile findByIdAndCreatorIdAndStatus(String id, Long creatorId, FileStatusFlag status);

    List<OaDocFile> findByFlowId(String procDefId);
    
    List<OaDocFile> findByStatusAndSuperviseFlag(FileStatusFlag status,String superviseFlag);

    @Query("select max(f.sncode) from OaDocFile f where f.fileType=:fileType and f.fileYear=:fileYear")
    public Integer selectMaxSncodeByFileTypeAndFileYear(@Param("fileType") WfBusinessType fileType,
                    @Param("fileYear") String fileYear);

    @Query("select count(f.id) from OaDocFile f where f.docCode=:docCode and f.fileType=:fileType and f.id!=:expId and f.status!=:status")
    public Integer selectCountByDocCodeAndFileTypeExpId(@Param("docCode") String docCode,
                    @Param("fileType") WfBusinessType fileType, @Param("expId") String id,
                    @Param("status") FileStatusFlag status);

    @Query("select count(f.id) from OaDocFile f where f.docCode=:docCode and f.fileType=:fileType  and f.status!=:status")
    public Integer selectCountByDocCodeAndFileType(@Param("docCode") String docCode,
                    @Param("fileType") WfBusinessType fileType,
                    @Param("status") FileStatusFlag status);
    
    @Query("select max(f.sncode) from OaDocFile f where f.fileYear=:fileYear and f.sncodeType=:sncodeType")
    public String selectMaxSncodeByFileYearAndSncodeType(@Param("fileYear") String fileYear, @Param("sncodeType") String sncodeType);
    
    @Query("select f from OaDocFile f where f.status='1' and to_char(f.expiryDate,'yyyy-mm-dd') = ?1")
    public List<OaDocFile> findLimitDocFileList(String dateStr);
    
    @Query("select f from OaDocFile f where f.fileType=:fileType and f.id!=:expId and f.status!=:status")
    public List<OaDocFile> findByByDocCodeAndFileTypeExpId(@Param("fileType") WfBusinessType fileType, 
    														@Param("expId") String id,
    														@Param("status") FileStatusFlag status);
    
    @Query("select f from OaDocFile f where f.fileType=:fileType and f.status!=:status")
    public List<OaDocFile> findByByDocCodeAndFileType(@Param("fileType") WfBusinessType fileType, 
    														@Param("status") FileStatusFlag status);
}
