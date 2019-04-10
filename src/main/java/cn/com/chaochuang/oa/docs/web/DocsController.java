package cn.com.chaochuang.oa.docs.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.upload.support.PluploadController;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.oa.docs.bean.DocsEditBean;
import cn.com.chaochuang.oa.docs.bean.DocsShowBean;
import cn.com.chaochuang.oa.docs.domain.DocsFolder;
import cn.com.chaochuang.oa.docs.domain.OaDocs;
import cn.com.chaochuang.oa.docs.reference.DocsStatus;
import cn.com.chaochuang.oa.docs.service.DocsFolderService;
import cn.com.chaochuang.oa.docs.service.DocsService;

@Controller
@RequestMapping("oa/docs")
public class DocsController extends PluploadController {

    @Value(value = "${upload.appid.docs}")
    private String            appId;

    @Autowired
    private DocsService       docsService;

    @Autowired
    private LogService        logService;

    @Autowired
    private DocsFolderService folderService;

    @Autowired
    private ConversionService conversionService;

    private String            savePath;

    /**
     * @return the savePath
     */
    public String getSavePath() {
        return savePath;
    }

    /**
     * @param savePath
     *            the savePath to set
     */
    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    @RequestMapping("/dealUploadBatch")
    public ModelAndView uploadView(Long folderId) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        ModelAndView mav = new ModelAndView("/oa/docs/uploadView");
        mav.addObject("createDate", new Date());
        mav.addObject("currUser", user);
        mav.addObject("folderId", folderId);
        DocsFolder folder = folderService.findOne(folderId);
        if (folder != null) {
            mav.addObject("folder", folder);
        }
        return mav;
    }

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/oa/docs/list");
        return mav;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public Page listjson(Integer page, Integer rows, String folderCode, HttpServletRequest request) {
        SearchBuilder<OaDocs, Long> searchBuilder = new SearchBuilder<OaDocs, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.getFilterBuilder().equal("status", DocsStatus.已保存);
        if (StringUtils.isNotBlank(folderCode)) {
            searchBuilder.getFilterBuilder().startWith("folder.folderCode", folderCode);
        }
        searchBuilder.appendSort(Direction.ASC, "folder.folderCode");
        searchBuilder.appendSort(Direction.DESC, "createDate");
        searchBuilder.appendSort(Direction.DESC, "id");
        SearchListHelper<OaDocs> listhelper = new SearchListHelper<OaDocs>();
        listhelper.execute(searchBuilder, docsService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), DocsShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo save(DocsEditBean bean, HttpServletRequest request, HttpServletResponse response) {
        try {
            Long fileId = this.docsService.saveCongressDocs(bean);
            if (bean.getId() == null) {
                logService.saveDefaultLog("新增电子文件：" + bean.getFileName() + ", 文件流水号：" + fileId, request);
            } else {
                logService.saveDefaultLog("修改电子信息：" + bean.getFileName() + ", 文件流水号：" + fileId, request);
            }
            return new ReturnInfo(fileId.toString(), null, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    @RequestMapping("/saveAll.json")
    @ResponseBody
    public ReturnInfo save(String keyword, Long[] docsIds, Long folderId, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (docsIds != null) {
                docsService.saveCongressDocs(keyword, docsIds, folderId);
                logService.saveDefaultLog("新增电子文件, 文件流水号：" + docsIds.toString(), request);
            }
            return new ReturnInfo("1", null, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo delete(Long[] ids, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (null != ids) {
                docsService.deleteCongressDocs(ids, request);
                logService.saveDefaultLog("删除电子文件, 文件流水号：" + ids.toString(), request);
            }
            return new ReturnInfo("1", null, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }

    }

    @RequestMapping("/dealExchange.json")
    @ResponseBody
    public ReturnInfo exchange(Long[] ids, Long folderId, HttpServletRequest request, HttpServletResponse response) {
        try {
            docsService.exchange(ids, folderId);
            logService.saveDefaultLog("迁移电子文件至目录（目录流水号:" + folderId + "）, 文件流水号：" + ids.toString(), request);
            return new ReturnInfo("1", null, "迁移成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }

    }

    @RequestMapping("/new")
    public ModelAndView newPage(Long folderId) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        ModelAndView mav = new ModelAndView("/oa/docs/edit");
        mav.addObject("createDate", new Date());
        mav.addObject("currUser", user);
        mav.addObject("folderId", folderId);
        DocsFolder folder = folderService.findOne(folderId);
        if (folder != null) {
            mav.addObject("folder", folder);
        }
        return mav;
    }

    @RequestMapping("/edit")
    public ModelAndView editPage(Long id) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        ModelAndView mav = new ModelAndView("/oa/docs/edit");
        OaDocs doc = this.docsService.findOne(id);
        mav.addObject("obj", doc);
        mav.addObject("createName", user.getUserName());
        return mav;
    }

    @Override
    protected String getAppId(HttpServletRequest request) {
        return appId;
    }
}
