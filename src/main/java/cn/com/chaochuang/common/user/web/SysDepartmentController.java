/*
 * FileName:    SysDepartmentController.java
 * Description:
 * Company:     
 * Copyright:    (c) 2015
 * History:     2015年11月17日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.user.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.chaochuang.common.user.bean.DepTreeBean;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.service.SysDepartmentService;

/**
 * @author LJX
 *
 */
@Controller
@RequestMapping("sysdept")
public class SysDepartmentController {

    @Autowired
    private SysDepartmentService deptService;

    @RequestMapping("/deptree.json")
    @ResponseBody
    public List<DepTreeBean> depTree(Long id) {
        return this.deptService.getDepTree(id);
    }

    @RequestMapping("/deptRuleoutUnit.json")
    @ResponseBody
    public List<SysDepartment> getDeptRuleoutUnit() {
        return this.deptService.getDeptRuleoutUnit();
    }

}
