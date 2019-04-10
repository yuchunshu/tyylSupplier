/*
 * FileName:    DocsFolderService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月13日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.docs.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.oa.docs.bean.DocsFolderTreeBean;
import cn.com.chaochuang.oa.docs.domain.DocsFolder;

/**
 * @author HM
 *
 */
public interface DocsFolderService extends CrudRestService<DocsFolder, Long> {

    /**
     * 延迟加载目录树节点
     * @param parentId
     * @return
     */
    public List<DocsFolderTreeBean> lazyFolderTree(Long parentId);

    /**
     * 获取最新的目录编码
     * @param parentId
     * @return
     */
    public String getNewFolderCode(Long parentId);

    /**
     * 创建新节点
     * @param parentId
     * @return
     */
    public DocsFolderTreeBean ceateNewNode(Long parentId);

    /**
     * 删除目录
     * @param id
     * @return errMsg 错误信息
     */
    public String delFolder(Long id);

    /**
     * 更新节点
     * @param id
     * @param folderName
     * @return
     */
    public Long updateFolder(Long id, String folderName);
}
