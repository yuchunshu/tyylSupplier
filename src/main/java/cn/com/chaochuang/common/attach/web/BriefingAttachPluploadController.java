/*
 * FileName:    AppItemAttachPluploadController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年12月2日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.attach.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.chaochuang.common.upload.support.PluploadController;


/**
 * @author rongln
 * @date 2017年12月22日 上午9:56:14
 * 
 */
@Controller
@RequestMapping("briefingattach")
public class BriefingAttachPluploadController extends PluploadController {

    @Value(value = "${upload.appid.briefing}")
    private String 						appId;
    

    @Override
    protected String getAppId(HttpServletRequest request) {
        return appId;
    }

}
