package cn.com.chaochuang.crm.project.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.crm.project.bean.ProjectEditBean;
import cn.com.chaochuang.crm.project.bean.ProjectShowBean;
import cn.com.chaochuang.crm.project.domain.Project;
import cn.com.chaochuang.crm.project.service.ProjectService;

@Controller
@RequestMapping("crm/project")
public class ProjectController{

    @Autowired
    private ProjectService    	projectService;

    @Autowired
    private SysAttachService        attachService;

    @Autowired
    private ConversionService 	  	conversionService;

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/crm/project/list");
        return mav;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public Page listjson(Integer page, Integer rows, String unitId, HttpServletRequest request) {
        SearchBuilder<Project, Long> searchBuilder = new SearchBuilder<Project, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        if (StringUtils.isNotBlank(unitId)) {
            searchBuilder.getFilterBuilder().equal("unitId", unitId);
        }
        searchBuilder.appendSort(Direction.DESC, "createTime");
        searchBuilder.appendSort(Direction.DESC, "id");
        SearchListHelper<Project> listhelper = new SearchListHelper<Project>();
        listhelper.execute(searchBuilder, projectService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), ProjectShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    @RequestMapping("/new")
    public ModelAndView newPage(Long unitId) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        ModelAndView mav = new ModelAndView("/crm/project/edit");
        mav.addObject("currUser", user);
        mav.addObject("createTime", new Date());
        mav.addObject("unitId", unitId);
        return mav;
    }

    @RequestMapping("/edit")
    public ModelAndView editPage(Long id) {
        ModelAndView mav = new ModelAndView("/crm/project/edit");
        Project project = this.projectService.findOne(id);
        mav.addObject("project", project);
        mav.addObject("attachMap", this.attachService.getAttachMap(project.getId().toString(), Project.class.getSimpleName()));

        return mav;
    }
    
    // 保存
    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo save(ProjectEditBean bean, HttpServletRequest request, HttpServletResponse response) {

        try {
            String id = this.projectService.saveProject(bean);
            
            return new ReturnInfo(id, null, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }
    
    
    @RequestMapping("/detail")
    @ResponseBody
    public ModelAndView detail(Long id) {
        ModelAndView mav = new ModelAndView("/crm/project/detail");
        Project project = null;
        if (id != null) {
        	project = this.projectService.findOne(id);
            if (project != null) {
                mav.addObject("project", project);
                mav.addObject("attachMap", this.attachService.getAttachMap(project.getId().toString(), Project.class.getSimpleName()));

            }
        }
        return mav;
    }
    
    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo del(String[] ids, HttpServletRequest request) {
        try {
            if (ids != null && ids.length > 0) {
                for (String id : ids) {
                    this.projectService.delProject(id);
                }
            }

            return new ReturnInfo("1", null, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }
    
}
