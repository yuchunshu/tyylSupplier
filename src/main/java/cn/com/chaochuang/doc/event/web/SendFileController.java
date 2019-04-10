/**
 * 南宁超创信息工程有限公司
 */
package cn.com.chaochuang.doc.event.web;


import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import cn.com.chaochuang.workflow.reference.WfBusinessType;

/**
 * @author hzr 2016年2月17日
 * 发文
 */

@Controller
@RequestMapping("doc/sendfile")
public class SendFileController extends DocFileCommonController {

    @RequestMapping("/new")
    public ModelAndView newPage(Long id, String type, HttpServletRequest request) {
        return super.newFilePage(WfBusinessType.发文);
    }

    @RequestMapping("/edit")
    public ModelAndView editPage(String id,String parentId) {
        return super.editFilePage(id, WfBusinessType.发文,parentId);
    }

    /** 任务办理页面 */
    @RequestMapping("task/deal")
    public ModelAndView dealPage(String returnPageType,String opflag, String id, String fileId, HttpServletRequest request) {
        return super.openDealPage(returnPageType,opflag, id, fileId, WfBusinessType.发文);
    }

}
