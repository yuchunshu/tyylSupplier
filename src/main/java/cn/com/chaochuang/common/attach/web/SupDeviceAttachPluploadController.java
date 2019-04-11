package cn.com.chaochuang.common.attach.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.chaochuang.common.upload.support.PluploadController;

@Controller
@RequestMapping("deviceattach")
public class SupDeviceAttachPluploadController extends PluploadController {
	
	@Value(value = "${upload.appid.device}")
    private String 			 	appId;

    @Override
    protected String getAppId(HttpServletRequest request) {
        return appId;
    }

}
