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

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.supplier.bean.SupUnitEditBean;
import cn.com.chaochuang.supplier.bean.SupUnitShowBean;
import cn.com.chaochuang.supplier.domain.SupGroup;
import cn.com.chaochuang.supplier.domain.SupUnit;
import cn.com.chaochuang.supplier.service.SupGroupService;
import cn.com.chaochuang.supplier.service.SupUnitService;

@Controller
@RequestMapping("supplier/unit")
public class SupUnitController{

    @Autowired
    private SupUnitService    supUnitService;

    @Autowired
    private SupGroupService   supGroupService;

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

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/supplier/unit/list");
        return mav;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public Page listjson(Integer page, Integer rows, String groupCode, HttpServletRequest request) {
        SearchBuilder<SupUnit, Long> searchBuilder = new SearchBuilder<SupUnit, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        if (StringUtils.isNotBlank(groupCode)) {
            searchBuilder.getFilterBuilder().startWith("group.groupCode", groupCode);
        }
        searchBuilder.appendSort(Direction.ASC, "group.groupCode");
        searchBuilder.appendSort(Direction.DESC, "createTime");
        searchBuilder.appendSort(Direction.DESC, "id");
        SearchListHelper<SupUnit> listhelper = new SearchListHelper<SupUnit>();
        listhelper.execute(searchBuilder, supUnitService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), SupUnitShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    @RequestMapping("/new")
    public ModelAndView newPage(Long groupId) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        ModelAndView mav = new ModelAndView("/supplier/unit/edit");
        mav.addObject("currUser", user);
        mav.addObject("createTime", new Date());
        mav.addObject("groupId", groupId);
        SupGroup supGroup = supGroupService.findOne(groupId);
        if (supGroup != null) {
            mav.addObject("supGroup", supGroup);
        }
        return mav;
    }

    @RequestMapping("/edit")
    public ModelAndView editPage(Long id) {
        ModelAndView mav = new ModelAndView("/supplier/unit/edit");
        SupUnit unit = this.supUnitService.findOne(id);
        mav.addObject("unit", unit);
        return mav;
    }
    
    // 保存
    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo save(SupUnitEditBean bean, HttpServletRequest request, HttpServletResponse response) {

        try {
            String id = this.supUnitService.saveSupUnit(bean);
            
            return new ReturnInfo(id, null, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }
    
    
    @RequestMapping("/detail")
    @ResponseBody
    public ModelAndView detail(Long id) {
        ModelAndView mav = new ModelAndView("/supplier/unit/detail");
        SupUnit unit = null;
        if (id != null) {
        	unit = this.supUnitService.findOne(id);
            if (unit != null) {
                mav.addObject("unit", unit);
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
                    this.supUnitService.delUnit(id);
                }
            }

            return new ReturnInfo("1", null, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }
    
    @RequestMapping("/dealExchange.json")
    @ResponseBody
    public ReturnInfo exchange(Long[] ids, Long groupId, HttpServletRequest request, HttpServletResponse response) {
        try {
        	supUnitService.exchange(ids, groupId);
            return new ReturnInfo("1", null, "迁移成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }

    }
}
