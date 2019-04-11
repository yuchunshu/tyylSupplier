/*
 * FileName:    EmDustbinService.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.mail.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.oa.mail.bean.MailShowBean;
import cn.com.chaochuang.oa.mail.domain.EmDustbin;

/**
 * @author HM
 *
 */
public interface EmDustbinService extends CrudRestService<EmDustbin, String> {

    /**
     * 草稿移动到垃圾箱
     *
     * @param mailIds
     *            邮件Id
     */
    public boolean draftsTrash(String mailIds);

    /**
     * 收件箱移动到垃圾箱
     *
     * @param mailIds
     *            邮件IDs
     * @param incepterId
     *            收件人ID
     */
    public boolean inboxTrash(String mailIds, Long incepterId);

    /**
     * 存档箱移到到垃圾箱
     *
     * @param tmpIds
     */
    public boolean archiveTrash(String tmpIds);

    /**
     * 垃圾批量恢复
     *
     * @param delIds
     * @param incepterId
     */
    public boolean recoverTrash(String delIds, Long incepterId);

    /**
     * 垃圾批量删除
     *
     * @param delIds
     */
    public boolean deleteTrash(String delIds, Long incepterId);

    /**
     * 垃圾箱列表 分页
     *
     * @param page
     * @param rows
     * @return
     */

    public List<MailShowBean> seleceEmDustbin(Integer page, Integer rows);

    /**
     * 垃圾箱列表总数
     *
     * @return
     */
    Long coutEmDustbin();

}
