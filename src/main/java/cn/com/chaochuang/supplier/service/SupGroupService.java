package cn.com.chaochuang.supplier.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.supplier.bean.SupGroupTreeBean;
import cn.com.chaochuang.supplier.domain.SupGroup;

public interface SupGroupService extends CrudRestService<SupGroup, Long> {

    /**
     * 延迟加载分组树节点
     * @param parentId
     * @return
     */
    public List<SupGroupTreeBean> lazyGroupTree(Long parentId);

    /**
     * 获取最新的分组编码
     * @param parentId
     * @return
     */
    public String getNewGroupCode(Long parentId);

    /**
     * 创建新节点
     * @param parentId
     * @return
     */
    public SupGroupTreeBean ceateNewNode(Long parentId);

    /**
     * 删除分组
     * @param id
     * @return errMsg 错误信息
     */
    public String delGroup(Long id);

    /**
     * 更新节点
     * @param id
     * @param folderName
     * @return
     */
    public Long updateGroup(Long id, String groupName);
}
