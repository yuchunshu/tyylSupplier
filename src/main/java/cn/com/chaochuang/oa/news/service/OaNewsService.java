/*
 * FileName:    OaNewsService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.news.service;

import java.util.Date;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.oa.news.bean.NewsEditBean;
import cn.com.chaochuang.oa.news.domain.OaNews;

/**
 * @author yuchunshu
 *
 */
public interface OaNewsService extends CrudRestService<OaNews, String> {
  
    public String saveNews(NewsEditBean bean);

    public boolean delNews(String id);

    /**
     * 用于过滤发布范围为部门时，只有该部门的人能查看
     *
     * @param title
     * @param department
     * @param fromDate
     * @param toDate
     * @param newsType
     * @param currentDeptId
     * @param page
     * @param rows
     * @return newsList
     */
    public Page selectAllForDeptShow(String title, String department, Date fromDate, Date toDate,String newsType,
                    String currentDeptId, Integer page, Integer rows);

}
