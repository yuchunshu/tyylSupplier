package cn.com.chaochuang.supplier.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.supplier.bean.SupGroupTreeBean;
import cn.com.chaochuang.supplier.service.SupGroupService;

@Controller
@RequestMapping("supplier/group")
public class SupGroupController {

    @Autowired
    private SupGroupService   folderService;

    @Autowired
    private LogService        logService;

    @Autowired
    private ConversionService conversionService;

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/supplier/group/list");
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        mav.addObject("currUser", user);
        return mav;
    }
    
    /** 延迟加载树 */
    @RequestMapping("/lazyGroupTree.json")
    @ResponseBody
    public List<SupGroupTreeBean> lazyGroupTreeJson(Long id) {
        return folderService.lazyGroupTree(id);
    }

    /** 创建节点 */
    @RequestMapping("/createNode.json")
    @ResponseBody
    public SupGroupTreeBean createNode(Long parentId, HttpServletRequest request) {
        SupGroupTreeBean bean = folderService.ceateNewNode(parentId);
        if (bean != null) {
            logService.saveDefaultLog("新建分组, 流水号：" + bean.getId(), request);
        }
        return bean;
    }

    /** 修改节点 */
    @RequestMapping("/updateNode.json")
    @ResponseBody
    public ReturnInfo updateNode(Long id, Long parentId, String folderName, HttpServletRequest request) {
        try {
            Long folderId = folderService.updateGroup(id, folderName);
            if (folderId != null) {
                logService.saveDefaultLog("修改分组, 流水号：" + folderId, request);
                return new ReturnInfo(folderId.toString(), null, "删除成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
        return new ReturnInfo(null, "服务器连接不上！", null);
    }

    /** 删除节点 */
    @RequestMapping("/delNode.json")
    @ResponseBody
    public ReturnInfo delNode(Long id, HttpServletRequest request) {
        String errMsg = folderService.delGroup(id);
        if (StringUtils.isNotBlank(errMsg)) {
            logService.saveDefaultLog("修改分组, 流水号：" + id, request);
            return new ReturnInfo(null, errMsg, null);
        } else {
            return new ReturnInfo("1", null, "删除成功！");
        }
    }

    /** 分组树单选dialog */
    @RequestMapping("/groupTree")
    public ModelAndView selectDepTree() {
        ModelAndView mav = new ModelAndView("/supplier/group/selectGroup");
        // 取数据的url
        mav.addObject("url", "/supplier/group/lazyGroupTree.json");
        // 标题
        mav.addObject("selectDialogTitle", "选择分组");
        return mav;
    }
}
