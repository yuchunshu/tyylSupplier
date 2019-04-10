package cn.com.chaochuang.doc.doccode.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnBean;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.doc.doccode.bean.DocCodeEditBean;
import cn.com.chaochuang.doc.doccode.domain.OaDocCode;
import cn.com.chaochuang.doc.doccode.service.OaDocCodeService;
import cn.com.chaochuang.doc.event.reference.DocCate;
import cn.com.chaochuang.doc.template.domain.DocTemplate;
import cn.com.chaochuang.doc.template.service.DocTemplateService;
import cn.com.chaochuang.workflow.reference.WfBusinessType;

/**
 * @author yuandl 2016-12-04
 *
 */
@Controller
@RequestMapping("/doc/doccode")
public class DocCodeController {

    @Autowired
    private OaDocCodeService     codeService;

    @Autowired
    private ConversionService    conversionService;

    @Autowired
    private LogService           logService;

    @Autowired
    protected DocTemplateService docTemplateService;

    /**
     * 列表页面
     */
    @RequestMapping("/list/{type}")
    public ModelAndView list(@PathVariable String type) {
        ModelAndView mav = new ModelAndView("/doc/doccode/list");
        if ("send".equals(type)) {
            mav.addObject("type", WfBusinessType.发文.getKey());
        } else {
            mav.addObject("type", WfBusinessType.收文.getKey());
        }
        return mav;
    }

    /**
     * 列表数据
     */
    @RequestMapping("/list.json")
    @ResponseBody
    public Page listjson(Integer page, Integer rows, HttpServletRequest request) {
    	Page p = new Page();
    	try {
    		SearchBuilder<OaDocCode, Long> searchBuilder = new SearchBuilder<OaDocCode, Long>(conversionService);
            searchBuilder.clearSearchBuilder().findSearchParam(request);

            searchBuilder.appendSort(Direction.ASC, "codeSort");
            SearchListHelper<OaDocCode> listHelper = new SearchListHelper<OaDocCode>();
            listHelper.execute(searchBuilder, codeService.getRepository(), page, rows);

            p.setRows(listHelper.getList());
            p.setTotal(listHelper.getCount());
		} catch (Exception e) {
			e.printStackTrace();
            logService.saveLog(SjType.普通操作, "公文字号-列表查询：失败！",LogStatus.失败, request);
		}
        
        return p;
    }

    /**
     * 新增、编辑页面
     */
    @RequestMapping("/new")
    public ModelAndView newPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/doc/doccode/edit");
        mav.addObject("type", request.getParameter("type"));
        return mav;
    }

    /**
     * 新增、编辑页面
     */
    @RequestMapping("/edit")
    public ModelAndView editPage(Long id,String codeType, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/doc/doccode/edit");
        if (id != null) {
            OaDocCode code = codeService.findOne(id);
            mav.addObject("obj", code);
            if(WfBusinessType.发文.getKey().equals(codeType) && code.getDocTemplate() != null){
                mav.addObject("templateName", code.getDocTemplate().getTemplateName());
                mav.addObject("templateId", code.getDocTemplate().getId());
            }
        }
        return mav;
    }

    /**
     * 保存
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo save(DocCodeEditBean bean, HttpServletRequest request) {
        try {
            Long id = codeService.saveDocCode(bean);
            if (id != null) {
                if (bean.getId() == null) {
                    logService.saveDefaultLog("新增公文字号：" + bean.getCodeName(), request);
                } else {
                    logService.saveDefaultLog("修改公文字号：" + bean.getCodeName(), request);
                }
                return new ReturnInfo(id.toString(), null, "保存成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (bean.getId() == null) {
                logService.saveLog(SjType.普通操作, "公文字号-新增《" + bean.getCodeName() + "》：失败！",LogStatus.失败, request);
            } else {
                logService.saveLog(SjType.普通操作, "公文字号-修改《" + bean.getCodeName() + "》：失败！",LogStatus.失败, request);
            }
            return new ReturnInfo(e.getMessage(), null);
        }
        return new ReturnInfo("操作失败！", null);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo del(String ids, HttpServletRequest request) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                String[] idArr = ids.split(",");
                for (String id : idArr) {
                    OaDocCode code = codeService.findOne(Long.valueOf(id));
                    if (code != null) {
                        codeService.delDocCode(code.getId());
                        logService.saveDefaultLog("删除公文字号：" + code.getCodeName(), request);
                    }
                }

                return new ReturnInfo(null, "删除成功！");
            }
            return new ReturnInfo("操作失败", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(e.getMessage(), null);
        }
    }

    @RequestMapping("/getDocCodeTempletePath.json")
    @ResponseBody
    public ReturnBean getDocCodeTempletePath(String codeName, String codeType) {
    	if (StringUtils.isBlank(codeName) ) {
    		return new ReturnBean("请选择文号！");
    	}
    	if (StringUtils.isBlank(codeType)) {
    		return new ReturnBean("公文类型为空！");
    	}

    	WfBusinessType bt;
    	if (codeType.equals(WfBusinessType.收文.getKey())) {
    		bt = WfBusinessType.收文;
    	} else if (codeType.equals(WfBusinessType.发文.getKey())) {
    		bt = WfBusinessType.发文;
    	} else {
    		bt = WfBusinessType.便函;
    	}
    	List<OaDocCode> list = this.codeService.findByCodeNameAndCodeType(codeName.trim(), bt );

    	if (list != null && !list.isEmpty()) {
    		DocTemplate tp = list.get(0).getDocTemplate();
    		if (tp != null && StringUtils.isNotBlank(tp.getTemplateFilePath())) {
    			return new ReturnBean("成功找到模板。", tp , true);
    		}
    	}
    	return new ReturnBean("该文号未配置模板！");
    }

    @RequestMapping({"/getCodeNameByDocCate.json"})
    @ResponseBody
    public List<OaDocCode> getCodeNameByDocCate(DocCate docCate) { 
    	return codeService.selectDocCateAndCodeTypeOrderByCodeSortAsc(docCate,WfBusinessType.发文); 
	}
}
