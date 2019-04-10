/*
 * FileName:    NoticeController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.doc.template.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.doccode.domain.OaDocCode;
import cn.com.chaochuang.doc.doccode.repository.OaDocCodeRepository;
import cn.com.chaochuang.doc.template.bean.DocTemplateEditBean;
import cn.com.chaochuang.doc.template.bean.DocTemplateShowBean;
import cn.com.chaochuang.doc.template.domain.DocTemplate;
import cn.com.chaochuang.doc.template.service.DocTemplateService;
import cn.com.chaochuang.common.attach.service.SysAttachService;

/**
 * @author huangwq
 *
 */
@Controller
@RequestMapping("doc/template")
public class DocTemplateController {

    /** 附件所属对象名 */
    private final String         _OWNER_TYPE = "DocTemplate";

    @Autowired
    protected DocTemplateService docTemplateService;

    @Autowired
    protected ConversionService  conversionService;

    @Autowired
    private SysAttachService     attachService;

    @Autowired
    private LogService           logService;
    
    @Autowired
    private OaDocCodeRepository  docCodeRepository;

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/doc/template/list");
        // ModelAndView mav = new ModelAndView("/goldgrid/edit");
        return mav;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public Page listjson(Integer page, Integer rows, HttpServletRequest request) {
    	Page p = new Page();
    	try {
    		SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
            SearchBuilder<DocTemplate, Long> searchBuilder = new SearchBuilder<DocTemplate, Long>(conversionService);
            searchBuilder.clearSearchBuilder().findSearchParam(request);
            searchBuilder.appendSort(Direction.ASC, "id");
            // searchBuilder.getFilterBuilder().equal("deptId", user.getDepartment().getDeptParent());
            // 查询条件改为祖先部门id
            searchBuilder.getFilterBuilder().equal("deptId", user.getDepartment().getUnitDept().getId());
            SearchListHelper<DocTemplate> listhelper = new SearchListHelper<DocTemplate>();
            listhelper.execute(searchBuilder, docTemplateService.getRepository(), page, rows);
            
            p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), DocTemplateShowBean.class));
            p.setTotal(listhelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "发文模板-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
        

        return p;
    }

    @RequestMapping("/new")
    public ModelAndView newPage() {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        ModelAndView mav = new ModelAndView("/doc/template/edit");
        mav.addObject("currUser", user);
        return mav;
    }

    // 保存
    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo save(DocTemplateEditBean bean, HttpServletRequest request, HttpServletResponse response) {
        try {
            Long tempId = this.docTemplateService.saveDocTemplate(bean);
            logService.saveDefaultLog("保存发文模板：" + bean.getTemplateName(), request);
            return new ReturnInfo(tempId.toString(), null, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "发文模板-保存：失败！",LogStatus.失败, request);
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    @RequestMapping("/edit")
    public ModelAndView editPage(Long id) {
        ModelAndView mav = new ModelAndView("/doc/template/edit");
        DocTemplate temp = this.docTemplateService.findOne(id);
        mav.addObject("temp", temp);
        mav.addObject("attachMap", this.attachService.getAttachMap(String.valueOf(temp.getId()), _OWNER_TYPE));
        return mav;
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo del(String ids, HttpServletRequest request) {
        try {
            if (ids != null) {
                String[] idArr = ids.split(",");
                for (String temId : idArr) {
                	List<OaDocCode> docCodeList = docCodeRepository.findByTemplateId(Long.valueOf(temId));
                	if(Tools.isNotEmptyList(docCodeList)){
                		return new ReturnInfo(null, "模板已和发文字号绑定，不能删除！", null);
                	}
                	
                	//删除发文模板的同时，先删除附件记录
                	attachService.deleteOwnerAttach(temId, _OWNER_TYPE);
                    this.docTemplateService.delDocTemplate(Long.valueOf(temId));
                    
                    logService.saveDefaultLog("删除发文模板：" + temId, request);
                }
            }
            return new ReturnInfo("1", null, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

}
