/*
 * FileName:    PersonalController.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.address.web;

import java.util.Date;

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
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.oa.address.bean.AddressPersonalShowBean;
import cn.com.chaochuang.oa.address.domain.OaAddressPersonal;
import cn.com.chaochuang.oa.address.service.OaAddressPersonalService;
import cn.com.chaochuang.oa.datachange.reference.DataChangeTable;
import cn.com.chaochuang.oa.datachange.reference.OperationType;
import cn.com.chaochuang.oa.datachange.service.DataChangeService;

/**
 * @author HM
 *
 */
@Controller
@RequestMapping("oa/address/personal")
public class PersonalController {

    @Autowired
    private OaAddressPersonalService addressPersonalService;

    @Autowired
    protected ConversionService      conversionService;

    @Autowired
    private DataChangeService        dataChangeService;

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/oa/address/personal/list");
        return mav;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public Page listjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<OaAddressPersonal, Long> searchBuilder = new SearchBuilder<OaAddressPersonal, Long>(
                        conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.getFilterBuilder().equal("creatorId", UserTool.getInstance().getCurrentUserId());

        searchBuilder.appendSort(Direction.ASC, "id");
        SearchListHelper<OaAddressPersonal> listhelper = new SearchListHelper<OaAddressPersonal>();
        listhelper.execute(searchBuilder, addressPersonalService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), AddressPersonalShowBean.class));
        p.setTotal(listhelper.getCount());

        return p;
    }

    // 导出EXCEL
    @RequestMapping("/export.json")
    public ModelAndView exportLaw(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("oa/address/personal/excel/personalExport");
        mav.addObject("page", this.listjson(1, 1000000000, request));
        mav.addObject("fileName", "个人通讯录" + Tools.DATE_FORMAT4.format(new Date()));
        return mav;
    }

    @RequestMapping("/new")
    public ModelAndView newPage() {
        ModelAndView mav = new ModelAndView("/oa/address/personal/edit");
        mav.addObject("creatorId", UserTool.getInstance().getCurrentUserId());
        mav.addObject("creatorDepId", UserTool.getInstance().getCurrentUserDepartmentId());
        return mav;
    }

    @RequestMapping("/edit")
    public ModelAndView editPage(Long id) {
        ModelAndView mav = new ModelAndView("/oa/address/personal/edit");
        if (id != null) {
            mav.addObject("obj", this.addressPersonalService.findOne(id));
            // 保存数据变动
            dataChangeService.saveDataChange("addr_id=" + id, DataChangeTable.通讯录, OperationType.修改);
        }
        return mav;
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo save(OaAddressPersonal info, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (info != null) {
                if (info.getId() == null) {
                    info.setCreateDate(new Date());
                }
                this.addressPersonalService.persist(info);
            }
            return new ReturnInfo(info.getId().toString(), null, "发布成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    // 删除
    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo del(String ids) {
        try {
            if (ids != null) {
                String[] idArr = ids.split(",");
                for (String id : idArr) {
                    this.addressPersonalService.delete(Long.valueOf(id));
                    // 保存数据变动
                    dataChangeService.saveDataChange("card_id=" + id, DataChangeTable.通讯录, OperationType.删除);
                }
            }
            return new ReturnInfo("1", null, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    @RequestMapping("/detail")
    @ResponseBody
    public ModelAndView detail(Long id) {
        ModelAndView mav = new ModelAndView("/oa/address/personal/detail");
        if (id != null) {
            mav.addObject("obj", this.addressPersonalService.findOne(id));
        }
        return mav;
    }
}
