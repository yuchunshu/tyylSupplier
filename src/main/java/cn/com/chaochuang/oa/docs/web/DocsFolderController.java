package cn.com.chaochuang.oa.docs.web;

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
import cn.com.chaochuang.oa.docs.bean.DocsFolderTreeBean;
import cn.com.chaochuang.oa.docs.service.DocsFolderService;

@Controller
@RequestMapping("oa/docs/folder")
public class DocsFolderController {

    @Autowired
    private DocsFolderService folderService;

    @Autowired
    private LogService        logService;

    @Autowired
    private ConversionService conversionService;

    /** 延迟加载树 */
    @RequestMapping("/lazyFolderTree.json")
    @ResponseBody
    public List<DocsFolderTreeBean> lazyFolderTreeJson(Long id) {
        return folderService.lazyFolderTree(id);
    }

    /** 创建节点 */
    @RequestMapping("/createNode.json")
    @ResponseBody
    public DocsFolderTreeBean createNode(Long parentId, HttpServletRequest request) {
        DocsFolderTreeBean bean = folderService.ceateNewNode(parentId);
        if (bean != null) {
            logService.saveDefaultLog("新建文件目录, 流水号：" + bean.getId(), request);
        }
        return bean;
    }

    /** 修改节点 */
    @RequestMapping("/updateNode.json")
    @ResponseBody
    public ReturnInfo updateNode(Long id, Long parentId, String folderName, HttpServletRequest request) {
        try {
            Long folderId = folderService.updateFolder(id, folderName);
            if (folderId != null) {
                logService.saveDefaultLog("修改文件目录, 流水号：" + folderId, request);
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
        String errMsg = folderService.delFolder(id);
        if (StringUtils.isNotBlank(errMsg)) {
            logService.saveDefaultLog("修改文件目录, 流水号：" + id, request);
            return new ReturnInfo(null, errMsg, null);
        } else {
            return new ReturnInfo("1", null, "删除成功！");
        }
    }

    /** 目录树单选dialog */
    @RequestMapping("/folderTree")
    public ModelAndView selectDepTree() {
        ModelAndView mav = new ModelAndView("/oa/docs/select/selectFolder");
        // 取数据的url
        mav.addObject("url", "/oa/docs/folder/lazyFolderTree.json");
        // 标题
        mav.addObject("selectDialogTitle", "选择目录");
        return mav;
    }
}
