package cn.com.chaochuang.supplier.web;

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
import cn.com.chaochuang.supplier.bean.SupContractEditBean;
import cn.com.chaochuang.supplier.bean.SupContractShowBean;
import cn.com.chaochuang.supplier.domain.SupContract;
import cn.com.chaochuang.supplier.domain.SupUnit;
import cn.com.chaochuang.supplier.service.SupContractService;
import cn.com.chaochuang.supplier.service.SupUnitService;

@Controller
@RequestMapping("supplier/contract")
public class SupContractController{

    @Autowired
    private SupContractService    	supContractService;

    @Autowired
    private SupUnitService   	  	supUnitService;
    
    @Autowired
    private SysAttachService        attachService;

    @Autowired
    private ConversionService 	  	conversionService;

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/supplier/contract/list");
        return mav;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public Page listjson(Integer page, Integer rows, String unitId, HttpServletRequest request) {
        SearchBuilder<SupContract, Long> searchBuilder = new SearchBuilder<SupContract, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        if (StringUtils.isNotBlank(unitId)) {
            searchBuilder.getFilterBuilder().equal("unitId", unitId);
        }
        searchBuilder.appendSort(Direction.DESC, "createTime");
        searchBuilder.appendSort(Direction.DESC, "id");
        SearchListHelper<SupContract> listhelper = new SearchListHelper<SupContract>();
        listhelper.execute(searchBuilder, supContractService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), SupContractShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    @RequestMapping("/new")
    public ModelAndView newPage(Long unitId) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        ModelAndView mav = new ModelAndView("/supplier/contract/edit");
        mav.addObject("currUser", user);
        mav.addObject("createTime", new Date());
        mav.addObject("unitId", unitId);
        SupUnit supUnit = supUnitService.findOne(unitId);
        if (supUnit != null) {
            mav.addObject("supUnit", supUnit);
        }
        return mav;
    }

    @RequestMapping("/edit")
    public ModelAndView editPage(Long id) {
        ModelAndView mav = new ModelAndView("/supplier/contract/edit");
        SupContract contract = this.supContractService.findOne(id);
        mav.addObject("contract", contract);
        mav.addObject("attachMap", this.attachService.getAttachMap(contract.getId().toString(), SupContract.class.getSimpleName()));

        return mav;
    }
    
    // 保存
    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo save(SupContractEditBean bean, HttpServletRequest request, HttpServletResponse response) {

        try {
            String id = this.supContractService.saveSupContract(bean);
            
            return new ReturnInfo(id, null, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }
    
    
    @RequestMapping("/detail")
    @ResponseBody
    public ModelAndView detail(Long id) {
        ModelAndView mav = new ModelAndView("/supplier/Contract/detail");
        SupContract contract = null;
        if (id != null) {
        	contract = this.supContractService.findOne(id);
            if (contract != null) {
                mav.addObject("contract", contract);
                mav.addObject("attachMap", this.attachService.getAttachMap(contract.getId().toString(), SupContract.class.getSimpleName()));

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
                    this.supContractService.delContract(id);
                }
            }

            return new ReturnInfo("1", null, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }
    
}
