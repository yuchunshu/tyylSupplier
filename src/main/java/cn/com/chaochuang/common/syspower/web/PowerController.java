/*
 * FileName:    PowerController.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月7日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.syspower.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.syspower.bean.DetailPowerBean;
import cn.com.chaochuang.common.syspower.bean.ModuleShowBean;
import cn.com.chaochuang.common.syspower.bean.PowerInfo;
import cn.com.chaochuang.common.syspower.bean.PowerTreeBean;
import cn.com.chaochuang.common.syspower.bean.PowerTreeGridBean;
import cn.com.chaochuang.common.syspower.domain.SysPower;
import cn.com.chaochuang.common.syspower.service.SysPowerService;
import cn.com.chaochuang.common.syspower.service.SysRoleModuleService;
import cn.com.chaochuang.common.util.ModuleUtils;

/**
 * @author LJX
 *
 */
@Controller
@RequestMapping("system/power")
public class PowerController {

    @Autowired
    private SysPowerService          powerService;

    @Autowired
    private LogService               logService;

    @Autowired
    protected ConversionService      conversionService;

    @Autowired
    private SysRoleModuleService     roleModuleService;

    @RequestMapping("/currentUserPower.json")
    @ResponseBody
    public List<PowerTreeBean> getCurrentPower() {
        return this.powerService.currentPowers();
    }

    @RequestMapping("/allNeedPower.json")
    @ResponseBody
    public List<PowerTreeGridBean> findAll(Long roleId) {
        return this.powerService.loadAllPowerNeed(roleId);
    }

    @RequestMapping("/menulist")
    public ModelAndView powerlist() {
        ModelAndView mav = new ModelAndView("/system/power/treegrid");
        return mav;
    }

    @RequestMapping("/listMenu.json")
    @ResponseBody
    public List<PowerTreeGridBean> listjson() {
        return this.powerService.getAllRoot();
    }

    @RequestMapping("/children.json")
    @ResponseBody
    public List<PowerTreeGridBean> childrenjson(Long parentPowerId) {
        return this.powerService.getChildrenByParentId(parentPowerId);
    }

    @RequestMapping("/new")
    public ModelAndView newpower(Long parentPowerId) {
        ModelAndView mav = new ModelAndView("/system/power/edit");
        mav.addObject("parentPowerId", parentPowerId);
        if(parentPowerId != null){
        	mav.addObject("powerCode", this.powerService.recommendPowerCode(parentPowerId, null));
        }
        return mav;
    }

    @RequestMapping("/edit")
    public ModelAndView editpower(Long id) {
        ModelAndView mav = new ModelAndView("/system/power/edit");
        if (id != null) {
            mav.addObject("obj", this.powerService.findOne(id));
            mav.addObject("powerCode", this.powerService.recommendPowerCode(null, id));
        }
        return mav;
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo savepower(PowerInfo power, HttpServletRequest request, HttpServletResponse response) {
        try {
            String logContent = "";
            if (power.getId() == null) {
                logContent += "新增权限：";
            } else {
                logContent += "修改权限：";
            }
            String powerId = this.powerService.savePower(power);

            logContent += "流水号/" + powerId + "，";
            logContent += "权限名称/" + power.getPowerName();
            logService.saveLog(SjType.角色权限, logContent,LogStatus.成功, request);
            return new ReturnInfo(powerId, null, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo delpower(Long id) {
        try {
            String errMsg = this.powerService.deletePower(id);
            if (errMsg == null) {
                return new ReturnInfo("1", null, "删除成功!");
            } else {
                return new ReturnInfo(null, errMsg, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    /**
     * 更新权限模块
     */
    @RequestMapping("/saveModule.json")
    @ResponseBody
    public ReturnInfo saveModulejson(@RequestBody List<ModuleShowBean> list, HttpServletRequest request, HttpServletResponse response) {
    	try{
    		this.roleModuleService.updateRoleModule(list);
    		this.roleModuleService.refreshCaches(request, response);
    		return new ReturnInfo("1", null, "success");
    	}catch(Exception e){
    		e.printStackTrace();
    		return new ReturnInfo(null, "error", null);
    	}

    }

    /**
     * 详细权限数据
     */
    @RequestMapping("/detailModule.json")
    @ResponseBody
    public Page detailModulejson(Long roleId, DetailPowerBean bean, HttpServletRequest request, Integer page, Integer rows) {

        Page p = new Page();
        p.setRows(this.powerService.selectPowerByRoleId(roleId, bean, rows, page));
        p.setTotal(Long.valueOf(this.powerService.selectPowerByRoleId(roleId, bean, 100000, 1).size()));
        return p;
    }

    /**
     * 保存详细权限
     */
    @RequestMapping("/saveDetailPower.json")
    @ResponseBody
    public ReturnInfo saveDetailPowerjson(DetailPowerBean detailPower, HttpServletRequest request) {
    	try{
    		return new ReturnInfo(this.powerService.checkAndSaveSysPower(detailPower).getId().toString(), null, "success");
    	}catch(Exception e){
    		e.printStackTrace();
    		return new ReturnInfo(null, "连接不上服务器", null);
    	}
    }

    /**
     * 删除详情权限数据
     */
    @RequestMapping("/deleteDetailPower.json")
    @ResponseBody
    public ReturnInfo deleteDetailPowerjson(@RequestBody SysPower power,Long roleId, HttpServletRequest request) {
    	try{
    		return new ReturnInfo(this.powerService.delDetailPower(power, roleId).getId().toString(), null, "success");
    	}catch(Exception e){
    		e.printStackTrace();
    		return new ReturnInfo(null, "连接不上服务器", null);
    	}
    }




}
