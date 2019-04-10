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
@RequestMapping("interequestattach")
public class InterequestAttachPluploadController extends PluploadController {
	
	@Value(value = "${upload.appid.docAsk}")
    private String 			 			appId;

    @Override
    protected String getAppId(HttpServletRequest request) {
        return appId;
    }

}
