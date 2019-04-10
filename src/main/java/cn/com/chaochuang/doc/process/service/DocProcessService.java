/*
 * FileName:    processService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年4月27日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.process.service;

import java.util.Collection;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.data.persistence.SearchFilter;
import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.process.domain.DocProcess;

/**
 * @author huangwq
 *
 */
public interface DocProcessService extends CrudRestService<DocProcess, Long> {

    /**
     * 保存收文登记簿
     *
     * @param currentUser
     * @param receiveFile
     * @param dealerArr
     *            【下一环节处理人】用于插入 当前处理科室 字段
     */
    void saveDocProcess(SysUser currentUser, OaDocFile receiveFile);

    void delDocProcess(OaDocFile obj);

    /**
     * 部门/个人登记簿Page
     *
     * @param values
     * @param page
     * @param rows
     * @return
     */
    public Page selectProcessPage(Collection<SearchFilter> values, Integer page, Integer rows);

}
