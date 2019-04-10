/*
 * FileName:    DocsFolderServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月13日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.docs.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.oa.docs.bean.DocsFolderTreeBean;
import cn.com.chaochuang.oa.docs.domain.DocsFolder;
import cn.com.chaochuang.oa.docs.domain.OaDocs;
import cn.com.chaochuang.oa.docs.repository.DocsFolderRepository;

/**
 * @author HM
 *
 */
@Service
@Transactional
public class DocsFolderServiceImpl extends SimpleLongIdCrudRestService<DocsFolder> implements DocsFolderService{

    @Autowired
    private DocsFolderRepository repository;

    @Autowired
    private DocsService          docsService;

    @Override
    public SimpleDomainRepository<DocsFolder, Long> getRepository() {
        return repository;
    }

    @Override
    public List<DocsFolderTreeBean> lazyFolderTree(Long parentId) {
        List<DocsFolderTreeBean> returnList = new ArrayList<DocsFolderTreeBean>(); // 返回的list
        List<DocsFolder> folders = null; // 数据list
        if(parentId == null) {
            folders = repository.getRoots(); // 根节点
        } else {
            folders = repository.findByParentIdOrderByFolderCodeAsc(parentId); // 子节点
        }
        if (folders != null && folders.size() > 0) {
            for (DocsFolder f : folders) {
                if (parentId != null && f.getId() == f.getParentId()) {  // 排除根节点
                    break;
                }
                DocsFolderTreeBean bean = BeanCopyBuilder.buildObject(f, DocsFolderTreeBean.class);

                // 是否含有子节点
                Integer childrenCount = repository.haveChildren(f.getId());
                if (childrenCount != null && childrenCount > 0) {
                    bean.setState("closed");
                } else {
                    bean.setState("open");
                }
                returnList.add(bean);
            }
        }
        return returnList;
    }

    @Override
    public String getNewFolderCode(Long parentId) {
        List<DocsFolder> folders = null;
        if (null == parentId) {
            folders = repository.getRoots();
        } else {
            folders = repository.findByParentIdOrderByFolderCodeAsc(parentId);
        }
        if (folders != null && folders.size() > 0) {
            String folderCode = folders.get(folders.size() - 1).getFolderCode();
            if (StringUtils.isNotBlank(folderCode)) {
                int length = folderCode.length();
                StringBuilder fmStr = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    fmStr.append("0");
                }
                DecimalFormat format = new DecimalFormat(fmStr.toString());
                Long code = Long.valueOf(folderCode);
                code++; // 同级别最大值 + 1
                return format.format(code);
            }
        } else {
            if (parentId != null) {
                DocsFolder parent = repository.findOne(parentId);
                if (parent != null) {
                    return parent.getFolderCode() + "001";
                }
            }
        }
        return "001";
    }

    @Override
    public DocsFolderTreeBean ceateNewNode(Long parentId) {
        DocsFolder folder = new DocsFolder();
        folder.setFolderCode(getNewFolderCode(parentId));
        String folderName = "NewFolder_" + Tools.DATE_TIME_FORMAT2.format(new Date());
        folder.setFolderName(folderName);
        folder.setParentId(parentId);
        if (null == parentId) {
            folder.setFullFloderName(folderName);
        } else {
            DocsFolder parent = repository.findOne(parentId);
            if (parent != null) {
                folder.setFullFloderName(parent.getFullFloderName() + "/" + folderName);
            }
        }
        repository.save(folder);

        DocsFolderTreeBean bean = BeanCopyBuilder.buildObject(folder, DocsFolderTreeBean.class);
        bean.setState("open");
        return bean;
    }

    @Override
    public String delFolder(Long id) {
        if (id != null) {
            DocsFolder folder = repository.findOne(id);
            if (folder != null) {
                List<OaDocs> docsList = docsService.findByFolderId(folder.getId());
                if (docsList != null && docsList.size() > 0) {
                    return "目录不为空，无法删除！";
                } else {
                    repository.delete(folder);
                }
            } else {
                return "该目录不存在！";
            }
        } else {
            return "该目录不存在！";
        }
        return null;
    }

    @Override
    public Long updateFolder(Long id, String folderName) {
        if(id != null) {
            DocsFolder folder = repository.findOne(id);
            if (folder != null) {
                String full = folder.getFullFloderName();
                if (StringUtils.isNotBlank(full)) {
                    full = full.replaceAll(folder.getFolderName(), folderName);
                }
                folder.setFolderName(folderName);
                folder.setFullFloderName(full);
                repository.save(folder);
                return folder.getId();
            }
        }
        return null;
    }

}
