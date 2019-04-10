/*
 * FileName:    SysAttachService.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月26日 (HM) 1.0 Create
 */

package cn.com.chaochuang.common.attach.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.attach.bean.AttachBean;
import cn.com.chaochuang.common.attach.domain.SysAttach;

/**
 * @author HM
 *
 */
public interface SysAttachService extends CrudRestService<SysAttach, String> {

    Map<String, List<SysAttach>> getAttachMap(String ownerId, String ownerType);

    Map<String, List<SysAttach>> getAttachMap(String ownerId, String[] ownerType);
    
    boolean deleteAttach(String attachId);

    SysAttach linkAttachAndOwner(String attachId, String ownerId, String ownerType);

    List<SysAttach> findByOwnerIdAndOwnerType(String ownerId, String ownerType);

    List<SysAttach> findByOwnerId(String ownerId);

    /**
     * 删除属主附件
     *
     * @param ownerId
     * @param ownerType
     */
    void deleteOwnerAttach(String ownerId, String ownerType);


    public void deleteAttach(List<SysAttach> attachs);

    /**
     * 拷贝附近，返回附件ID
     * @param attachId
     * @return
     */
    public SysAttach copyAttach(SysAttach attach);

    /**
     * 返回附件路径
     * @param attachId
     * @return
     */
    public String getAttachPath(SysAttach attach);

    public String batchDownload(HttpServletResponse response,List<AttachBean> attachList,String downLoadAttachName);


}
