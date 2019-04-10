package cn.com.chaochuang.common.user.web;

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

import cn.com.chaochuang.common.bean.CommonTreeBean;
import cn.com.chaochuang.common.bean.EasyUIPage;
import cn.com.chaochuang.common.bean.Result;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.domain.ValidAble;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.user.bean.DepartmentEditBean;
import cn.com.chaochuang.common.user.bean.DepartmentShowBean;
import cn.com.chaochuang.common.user.bean.DepartmentTreeShowBean;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.repository.SysDepartmentRepository;
import cn.com.chaochuang.common.user.service.SysDepartmentService;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.SearchListHelper;

@Controller
@RequestMapping("system/dept")
public class DepartmentController {

    @Autowired
    private SysDepartmentService    departmentService;

    @Autowired
    private SysUserService          userService;

    @Autowired
    private SysDepartmentRepository depRepository;

    @Autowired
    private LogService              logService;

    @Autowired
    protected ConversionService     conversionService;

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/system/dept/tree");
        return mav;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public EasyUIPage listjson(Integer page, Integer rows, SysDepartment dept, HttpServletRequest request) {
        SearchBuilder<SysDepartment, Long> searchBuilder = new SearchBuilder<SysDepartment, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.appendSort(Direction.ASC, "orderNum");
        searchBuilder.appendSort(Direction.DESC, "id");
        searchBuilder.getFilterBuilder().equal("valid", ValidAble.VALID); // 过滤无效部门

        SearchListHelper<SysDepartment> listhelper = new SearchListHelper<SysDepartment>();
        listhelper.execute(searchBuilder, departmentService.getRepository(), page, rows);

        EasyUIPage p = new EasyUIPage();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), DepartmentShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    @RequestMapping("/new")
    public ModelAndView newPage(Long id) {
        ModelAndView mav = new ModelAndView("system/dept/edit");
        SysDepartment dept = this.departmentService.findOne(id);
        mav.addObject("parentDept", dept);
        return mav;
    }

    @RequestMapping("/edit")
    public ModelAndView editPage(Long id) {
        ModelAndView mav = new ModelAndView("system/dept/edit");
        mav.addObject("obj", this.departmentService.findOne(id));
        return mav;
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public Result update(DepartmentEditBean dept, HttpServletRequest request, HttpServletResponse response) {
        Long deptId = this.departmentService.saveDept(dept);
        if (deptId != null) {
            // 操作日志
            String logContent = "";
            if (dept.getId() != null) {
                logContent += "修改部门：";
            } else {
                logContent += "新增部门：";
            }
            logContent += "流水号/" + deptId + "，";
            logContent += "部门名称/" + dept.getDeptName();
            logService.saveLog(SjType.部门用户, logContent,LogStatus.成功, request);

            return new Result("success", null);
        } else {
            return new Result("error", "服务器连接不上!");
        }
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public Result delDept(Long id) {

        String errorMsg = this.departmentService.delDept(id);
        if (errorMsg == null) {
            return new Result("success", null);
        } else {
            return new Result("error", errorMsg);
        }
    }

    @RequestMapping("/deptNameCheck.json")
    @ResponseBody
    public boolean nameCheck(Long id, String deptName, Long parentId) {
        // 同一级部门不能重名
        List<SysDepartment> dList = this.depRepository.findByDeptParentAndValidOrderByOrderNumAsc(parentId,
                        ValidAble.VALID);
        if (dList != null && dList.size() > 0) {
            for (SysDepartment d : dList) {
                if (d.getDeptName().equals(deptName)) {
                    if (!d.getId().equals(id)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 递归获取所有树节点
     *
     * @param id
     * @return
     */
    @RequestMapping("/deptree.json")
    @ResponseBody
    public List<DepartmentTreeShowBean> depTree(Long id) {
        return this.departmentService.getDepartmentTree(id);
    }

    /**
     * 延迟加载树
     *
     * @param id
     * @return
     */
    @RequestMapping("/treeLazyDep.json")
    @ResponseBody
    public List<CommonTreeBean> lazyDepTreeJson(Long id) {
        if (id == null) {
            return this.departmentService.getTreeRootBean();
        }
        return this.departmentService.getChildrenBean(id);
    }
}
