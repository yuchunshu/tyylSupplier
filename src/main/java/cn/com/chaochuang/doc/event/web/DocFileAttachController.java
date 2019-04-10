package cn.com.chaochuang.doc.event.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.upload.support.PluploadController;
import cn.com.chaochuang.common.attach.service.SysAttachService;

/**
 * @author yuandl 2016-11-28
 *
 */
@Controller
@RequestMapping("docfileattach")
public class DocFileAttachController extends PluploadController {
    @Value(value = "${upload.appid.docFileAttach}")
    private String                 appid;

    @Autowired
    private SysAttachService       attachService;

    @Autowired
    protected ConversionService    conversionService;

    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo delete(String id) {
        try {
            boolean flag = this.attachService.deleteAttach(id);
            if (flag) {
                return new ReturnInfo(id.toString(), null, "删除成功!");
            } else {
                return new ReturnInfo(null, "删除失败!", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    @Override
    protected String getAppId(HttpServletRequest request) {
        return this.appid;
    }

}
