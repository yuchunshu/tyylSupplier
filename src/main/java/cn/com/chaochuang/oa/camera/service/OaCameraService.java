/*
 * FileName:    OaCameraService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.camera.service;


import java.util.Date;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.oa.camera.bean.CameraEditBean;
import cn.com.chaochuang.oa.camera.domain.OaCamera;

/**
 * @author yuchunshu
 *
 */
public interface OaCameraService extends CrudRestService<OaCamera, String> {

    public String saveCamera(CameraEditBean bean);

    public boolean delCamera(String id);

    /**
     * 用于过滤发布范围为部门时，只有该部门的人能查看
     *
     * @param title
     * @param department
     * @param fromDate
     * @param toDate
     * @param cameraType
     * @param currentDeptId
     * @param page
     * @param rows
     * @return cameraList
     */
    public Page selectAllForDeptShow(String title, String department, Date fromDate, Date toDate, String cameraType,
                    String currentDeptId, Integer page, Integer rows);
}
