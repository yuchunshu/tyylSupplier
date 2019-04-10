package cn.com.chaochuang.common.attach.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.chaochuang.common.upload.support.PluploadController;


/** 
 * 公务用车
 * @author: yuchunshu
 * @date: 2018年10月29日 下午3:55:38  
 */
@Controller
@RequestMapping("carAttach")
public class CarAttachPluploadController extends PluploadController {
	
	@Value(value = "${upload.appid.car}")
    private String 			 	appId;

    @Override
    protected String getAppId(HttpServletRequest request) {
        return appId;
    }

}
