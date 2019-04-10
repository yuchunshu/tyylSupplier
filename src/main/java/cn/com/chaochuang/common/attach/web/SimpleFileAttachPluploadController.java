package cn.com.chaochuang.common.attach.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author rongln
 * @date 2017年12月22日 上午9:56:14
 * 
 */
@Controller
@RequestMapping("simpleFileAttach")
public class SimpleFileAttachPluploadController extends SysAttachPluploadController {
	
	@Value(value = "${upload.appid.docMemo}")
    private String 			 			appId;

    @Override
    protected String getAppId(HttpServletRequest request) {
        return appId;
    }

}
