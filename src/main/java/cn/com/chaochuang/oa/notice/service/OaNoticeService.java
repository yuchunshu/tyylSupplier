/*
 * FileName:    OaNoticeService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.notice.service;

import java.util.Date;
import java.util.List;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.oa.notice.bean.NoticeEditBean;
import cn.com.chaochuang.oa.notice.domain.OaNotice;

/**
 * @author HM
 *
 */
public interface OaNoticeService extends CrudRestService<OaNotice, String> {
    /**
     * @param id
     * @return
     */
    List<OaNotice> getOaNotice(String id);

    public String saveNotice(NoticeEditBean bean);

    public boolean delNotice(String id);

    /**
     * 用于过滤发布范围为部门时，只有该部门的人能查看
     *
     * @param title
     * @param departmentId
     * @param fromDate
     * @param toDate
     * @param currentDeptId
     * @param page
     * @param rows
     * @return noticeList
     */
    public Page selectAllForDeptShow(String title, String departmentId, Date fromDate, Date toDate,
                    String currentDeptId, Integer page, Integer rows);

    /**
     * 获取最新的通知公告信息
     *
     * @param lastOutputTime
     * @return
     */
    public List<OaNotice> selectRecentNotice(String lastOutputTime);

}
