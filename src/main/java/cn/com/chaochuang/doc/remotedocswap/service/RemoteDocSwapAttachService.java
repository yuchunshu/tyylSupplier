/*
 * FileName:    RemoteDocSwapAttachService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月21日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.service;

import java.util.List;
import java.util.Map;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapAttach;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteAttachType;

/**
 * @author yuandl
 *
 */
public interface RemoteDocSwapAttachService extends CrudRestService<RemoteDocSwapAttach, Long> {
    // /**
    // * 保存公文交换上传附件记录
    // *
    // * @param multipartFile
    // * 附件文件
    // * @param attachType
    // * 附件类型
    // * @return
    // */
    // RemoteDocSwapAttach saveRemoteDocSwapAttach(MultipartFile multipartFile, String attachType);

    Map<String, List<RemoteDocSwapAttach>> getAttachMap(Long remoteDocSwapContentId, RemoteAttachType attachType);

    List<RemoteDocSwapAttach> findByContentIdAndAttachType(Long remoteDocSwapContentId, RemoteAttachType attachType);

    /**
     * 删除公文交换附件
     *
     * @param attachId
     */
    void deleteRemoteDocSwapAttach(Long attachId);
}
