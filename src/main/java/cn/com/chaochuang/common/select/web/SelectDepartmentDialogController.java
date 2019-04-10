/*
 * FileName:    SelectDepartmentController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年1月22日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.select.web;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.domain.ValidAble;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.select.bean.DepartmentSelectBean;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.repository.SysDepartmentRepository;
import cn.com.chaochuang.common.user.service.SysDepartmentService;
import cn.com.chaochuang.common.util.Tools;

/**
 * @author LaoZhiYong
 *
 */
@Controller
@RequestMapping("/select/Dep")
public class SelectDepartmentDialogController {

    @Autowired
    private SysDepartmentService    	 depService;

    @Autowired
    private SysDepartmentRepository 	 depRepository;
    
    @Autowired
    protected ConversionService     	 conversionService;

    // 部门树单选
    @RequestMapping("/selectDepTree")
    public ModelAndView selectDepTree() {
        ModelAndView mav = new ModelAndView("/selectdialog/selectdeptreedialog");
        // 取数据的url
        mav.addObject("url", "/select/Dep/treeLazyDep.json");
        // 标题
        mav.addObject("selectDialogTitle", "选择部门");
        return mav;
    }

    // 部门树多选有记忆
    @RequestMapping("/selectDepsSelected")
    public ModelAndView selectDeps(String selected, String ignore) {
        ModelAndView mav = new ModelAndView("/selectdialog/selectdep");
        if (StringUtils.isNotBlank(selected)) {
            mav.addObject("selected", selected.split(","));
        }
        mav.addObject("ignore", ignore);
        return mav;
    }

    // 部门树：多选
    @RequestMapping("/selectDeps")
    public ModelAndView selectDepTreePlural() {
        ModelAndView mav = new ModelAndView("/selectdialog/selectdepsdialog");
        // 取数据的url
        mav.addObject("url", "/select/Dep/treeLazyDep.json");
        // 标题
        mav.addObject("selectDialogTitle", "选择部门");
        return mav;
    }

    // 部门树
    @RequestMapping("/selectDep")
    public ModelAndView selectDep(boolean single, String selected, String ignore) {
        ModelAndView mav = new ModelAndView("/selectdialog/selectdep");
        mav.addObject("single", single);
        if (StringUtils.isNotBlank(selected)) {
            mav.addObject("selected", selected.split(","));
        }
        mav.addObject("ignore", ignore);
        return mav;
    }

    @RequestMapping("/selectdep.json")
    @ResponseBody
    public List<DepartmentSelectBean> selectdep(Long id, String ignore) {
        if (id == null) {
            return BeanCopyBuilder.buildList(this.depService.getTreeRootBean(), DepartmentSelectBean.class);
        }
        return BeanCopyBuilder.buildList(this.depService.getChildrenBean(id), DepartmentSelectBean.class);
    }

    /**
     * 部门树延迟加载
     *
     * @param id
     * @return
     */
    @RequestMapping("/treeLazyDep.json")
    @ResponseBody
    public List<DepartmentSelectBean> lazyDepTreeJson(Long id) {
        if (id == null) {
            return BeanCopyBuilder.buildList(this.depService.getTreeRootBean(), DepartmentSelectBean.class);
        }
        return BeanCopyBuilder.buildList(this.depService.getChildrenBean(id), DepartmentSelectBean.class);
    }

    @RequestMapping("/treeLazyDep2.json")
    @ResponseBody
    public List<DepartmentSelectBean> lazyDepTreeJson2(Long id) {
        SysDepartment ud = (SysDepartment) UserTool.getInstance().getCurrentUserDepartment();
        if (id == null) {
            List<DepartmentSelectBean> root = new ArrayList<DepartmentSelectBean>();
            if (ud != null) {
                DepartmentSelectBean bean = new DepartmentSelectBean();
                bean.setId(ud.getId());
                bean.setText(ud.getDeptName());
                List<SysDepartment> sub = this.depRepository.findByDeptParentAndValidOrderByOrderNumAsc(ud.getId(),
                                ValidAble.VALID);
                if (Tools.isNotEmptyList(sub)) {
                    bean.setState("closed");
                }
                root.add(bean);
            }
            return root;
        }
        return BeanCopyBuilder.buildList(this.depService.getChildrenBean(id), DepartmentSelectBean.class);
    }
    
    /**
     * 部门树多选有记忆
     * 根据部门级别过滤
     * @param id
     * @return
     */
    @RequestMapping("/selectdepbylevel")
    public ModelAndView selectDepsByLevel(String selected, String ignore,String level,String showParent) {
        ModelAndView mav = new ModelAndView("/selectdialog/selectdepbylevel");
        if (StringUtils.isNotBlank(selected)) {
            mav.addObject("selected", selected.split(","));
        }
        mav.addObject("ignore", ignore);
        mav.addObject("level", level);
        //是否在可选列表显示上级
        mav.addObject("showParent", showParent);
        return mav;
    }
    
    /**
     * 部门树延迟加载
     * 根据部门级别加载部门树
     * @param id
     * @return
     */
    @RequestMapping("/selectdepbylevel.json")
    @ResponseBody
    public List<DepartmentSelectBean> selectdepByLevel(Long id,String level) {
        if (id == null) {
            return BeanCopyBuilder.buildList(this.depService.getTreeRootBeanByLevel(level), DepartmentSelectBean.class);
        }
        return BeanCopyBuilder.buildList(this.depService.getChildrenBeanByLevel(id, level), DepartmentSelectBean.class);
    }

    // 部门树多选有记忆(系统外发)
    @RequestMapping("/selectOuterDepsSelected")
    public ModelAndView selectOuterDeps(String selected, String ignore) {
        ModelAndView mav = new ModelAndView("/selectdialog/selectouterdep");
        if (StringUtils.isNotBlank(selected)) {
            mav.addObject("selected", selected.split(","));
        }
        mav.addObject("ignore", ignore);
        return mav;
    }

    /**
     * 根据部门名称查询部门树
     * @return
     */
    @RequestMapping("/searchdep.json")
    @ResponseBody
    public List<DepartmentSelectBean> searchDeptTreeByName(String deptName){
        List<DepartmentSelectBean> selectBeanList = Lists.newArrayList();
        if(StringUtils.isEmpty(deptName)){
            return selectBeanList;
        }
        List<SysDepartment> departmentList = this.depRepository.findByDeptNameLikeAndValid("%"+deptName+"%",ValidAble.VALID);
        if(departmentList!=null){
            for(SysDepartment department : departmentList){
                DepartmentSelectBean selectBean = new DepartmentSelectBean();
                selectBean.setId(department.getId());
                selectBean.setText(department.getDeptName());
                selectBeanList.add(selectBean);
            }
        }
        return selectBeanList;
    }

    @RequestMapping("/selectbytype.json")
    @ResponseBody
    public List<DepartmentSelectBean> selectDeptByType(String deptType){
        List<DepartmentSelectBean> selectBeanList = Lists.newArrayList();
        if(StringUtils.isEmpty(deptType)){
            return selectBeanList;
        }
        List<SysDepartment> departmentList = null;
        // TODO: 2018-1-12 暂时无规律
        if("2".equals(deptType)){
            //查询市局单位
//            departmentList = this.depRepository.findByDeptTypeAndDeptCodeLength(deptType,6,ValidAble.VALID);
        }else if("3".equals(deptType)){
            //查询市局检验所
            departmentList = this.depRepository.findByDeptCodeLikeAndValid("00006000_",ValidAble.VALID);
        }
        if(departmentList!=null){
            for(SysDepartment department : departmentList){
                DepartmentSelectBean selectBean = new DepartmentSelectBean();
                selectBean.setId(department.getId());
                selectBean.setText(department.getDeptName());
                selectBeanList.add(selectBean);
            }
        }
        return selectBeanList;
    }

}
