/*
 * FileName:    CongressDocsserviceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年4月15日 (WTL) 1.0 Create
 */

package cn.com.chaochuang.oa.docs.service;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.upload.support.UploadFileInfoPersistence;
import cn.com.chaochuang.common.upload.support.UploadFileItem;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.AttachUtils;
import cn.com.chaochuang.oa.docs.bean.DocsEditBean;
import cn.com.chaochuang.oa.docs.domain.OaDocs;
import cn.com.chaochuang.oa.docs.reference.DocsStatus;
import cn.com.chaochuang.oa.docs.repository.DocsRepository;

/**
 * @author WTL
 *
 */
@Service
@Transactional
public class DocsServiceImpl extends SimpleLongIdCrudRestService<OaDocs> implements DocsService,
                UploadFileInfoPersistence {

    @Value(value = "${upload.appid.docs}")
    private String           appid;

    @Value(value = "${upload.rootpath}")
    private String           rootPath;

    @Autowired
    private LogService       logService;

    @Autowired
    private DocsRepository   repository;

    @Autowired
    private EntityManager    em;

    @Autowired
    private SysAttachService attachService;

    @Autowired
    private SysUserService   userService;

    @Override
    public SimpleDomainRepository<OaDocs, Long> getRepository() {
        return this.repository;
    }

    @Override
    public String getAppId() {
        return appid;
    }

    @Override
    public String saveUploadFileInfo(UploadFileItem fileItem) {

        OaDocs doc = new OaDocs();
        doc.setFileName(fileItem.getTrueName());
        doc.setFileSize(fileItem.getFileSize());
        doc.setSaveName(fileItem.getSaveName());
        doc.setSavePath(fileItem.getSavePath());
        SysUser curUser = (SysUser) UserTool.getInstance().getCurrentUser();
        if (curUser != null) {
            doc.setUserId(curUser.getId());
            SysDepartment dep = curUser.getDepartment();
            if (dep != null) {
                doc.setDepId(dep.getId());
            }
        }
        doc.setCreateDate(new Date());
        doc.setStatus(DocsStatus.临时文件);
        this.persist(doc);
        return doc.getId().toString();
    }

    @Override
    public UploadFileItem getUploadFileInfo(String id) {
        if (null != id) {
            OaDocs doc = repository.findOne(Long.parseLong(id));
            if (null != doc) {
                UploadFileItem fileItem = new UploadFileItem();

                fileItem.setTrueName(doc.getFileName());
                fileItem.setSaveName(doc.getSaveName());
                fileItem.setSavePath(doc.getSavePath());
                fileItem.setFileSize(doc.getFileSize());

                return fileItem;
            }
        }

        return null;
    }

    @Override
    public Long saveCongressDocs(DocsEditBean bean) {
        if (null != bean) {
            Long fileId = bean.getId();
            if (fileId != null) {
                OaDocs doc = repository.findOne(fileId);
                if (doc != null) {
                    BeanUtils.copyProperties(bean, doc);
                    doc.setStatus(DocsStatus.已保存);
                    this.persist(doc);
                    // 清理临时文件
                    deleteTempDocs();
                    return doc.getId();
                }
            }
        }
        return null;
    }

    public void saveCongressDocs(String keyword, Long[] docsIds, Long folderId) {
        if (docsIds != null && docsIds.length > 0) {
            List<OaDocs> docsList = repository.findAll(Arrays.asList(docsIds));
            if (docsList != null && docsList.size() > 0) {
                for (OaDocs doc : docsList) {
                    doc.setKeyword(keyword);
                    doc.setFolderId(folderId);
                    doc.setStatus(DocsStatus.已保存);
                    this.persist(doc);
                }
            }
        }
        deleteTempDocs();
    }

    @Override
    public void deleteCongressDocs(Long[] ids, HttpServletRequest request) {
        if (null != ids && ids.length > 0) {
            List<OaDocs> docList = repository.findAll(Arrays.asList(ids));
            if (null != docList && docList.size() > 0) {
                for (int i = docList.size() - 1; i >= 0; i--) {
                    OaDocs doc = docList.get(i);
                    logService.saveDefaultLog("删除会议资料库：" + doc.getFileName() + ", 文件流水号：" + doc.getId(), request);
                    AttachUtils.removeFile(this.rootPath + File.separator + doc.getSavePath() + doc.getSaveName());
                    repository.delete(doc);
                }
            }
        }
    }

    /**
     * 清理临时文件
     */
    private void deleteTempDocs() {
        Calendar cal = Calendar.getInstance();
        // 清空时分秒
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0); // 24小时制的 0点
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);

        List<OaDocs> docList = repository.findByStatusAndCreateDateLessThan(DocsStatus.临时文件, cal.getTime());
        if (docList != null && docList.size() > 0) {
            for (int i = docList.size() - 1; i >= 0; i--) {
                OaDocs doc = docList.get(i);
                AttachUtils.removeFile(this.rootPath + File.separator + doc.getSavePath() + doc.getSaveName());
                repository.delete(doc);
            }
        }
    }

    @Override
    public List<OaDocs> findByFolderId(Long folderId) {
        if (folderId != null) {
            return repository.findByFolderId(folderId);
        }
        return null;
    }

    @Override
    public void exchange(Long[] ids, Long folderId) {
        if (ids != null && ids.length > 0 && folderId != null) {
            List<OaDocs> docsList = repository.findAll(Arrays.asList(ids));
            if (docsList != null) {
                for (OaDocs doc : docsList) {
                    doc.setFolderId(folderId);
                    repository.save(doc);
                }
            }
        }
    }
}
