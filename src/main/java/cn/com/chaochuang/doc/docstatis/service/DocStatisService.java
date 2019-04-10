/*
 * FileName:    DocStatisService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年10月13日 ( HeYunTao) 1.0 Create
 */

package cn.com.chaochuang.doc.docstatis.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.chaochuang.doc.docstatis.bean.DocStatisShowBean;

/**
 * @author HeYunTao
 *
 */
public interface DocStatisService {

    /**
     * 公文统计
     *
     * @param userId
     * @return
     */

    public List<DocStatisShowBean> summary(String deptId, String userId, Date fromDay, Date toDay, boolean showUsers);

    public Map pieStatistical(String deptId, String userId, Date fromDay, Date toDay);

    public Map columnStatistical(String deptId, String userId, Date fromDay, Date toDay);
}
