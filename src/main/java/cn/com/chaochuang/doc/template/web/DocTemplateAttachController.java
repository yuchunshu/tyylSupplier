/*
 * FileName:    ProposalAttachController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年4月12日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.template.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.chaochuang.common.upload.support.PluploadController;

/**
 * @author huangwq
 *
 */
@Controller
@RequestMapping("doctemplate/attach")
public class DocTemplateAttachController extends PluploadController {

    @Value(value = "${upload.appid.doc.template.attach}")
    private String appid;

    @Override
    protected String getAppId(HttpServletRequest request) {
        return this.appid;
    }

}
