/*
 * FileName:    EmTempStorageService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.mail.service;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.oa.mail.domain.EmTempStorage;

/**
 * @author HM
 *
 */
public interface EmTempStorageService extends CrudRestService<EmTempStorage, String> {

    /**
     * 邮件批量存档
     *
     * @param incIds
     *            em_incepter表的主键
     */
    public void archiveMails(String incIds);

    /**
     * 存档批量删除
     *
     * @param tmpIds
     * @return
     */
    public boolean deleteArchive(String tmpIds);

}
