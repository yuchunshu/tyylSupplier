/*
 * FileName:    RemoteDocSwapUnitController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月24日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.select.bean.DepartmentSelectBean;
import cn.com.chaochuang.common.util.SearchBuilderHelper;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteUnitIdentifier;
import cn.com.chaochuang.doc.remotedocswap.service.RemoteUnitIdentifierService;

/**
 * @author yuandl
 *
 */
@Controller
@RequestMapping("remotedocswap/unit")
public class RemoteDocSwapUnitController {

	@Autowired
    private ConversionService           conversionService;
	
    @Autowired
    private RemoteUnitIdentifierService unitService;

    /**
     * 选择窗口页面
     */
    @RequestMapping("/select")
    public ModelAndView select() {
        ModelAndView mav = new ModelAndView("/doc/remotedocswap/unitSelect");
        // 标题
        mav.addObject("selectDialogTitle", "选择单位");
        return mav;
    }
    
    /**
     * 获取公文交换单位部门列表
     */
    @RequestMapping("/getRemoteUnitIdentifierTree.json")
    @ResponseBody
    public List<DepartmentSelectBean> remoteUnitIdentifierTree() {
        return BeanCopyBuilder.buildList(this.unitService.getChildrenBean(), DepartmentSelectBean.class);
    }
     
    /**
     * 根据公文交换单位部门获取单位名称列表
     */
    @RequestMapping("/selectRemoteUnitByIdentifierName.json")
    @ResponseBody
    public Page selectRemoteUnitByIdentifierName(Integer page, Integer rows, HttpServletRequest request,String identifierName) {
        SearchBuilder<RemoteUnitIdentifier, Long> searchBuilder = SearchBuilderHelper.bindSearchBuilder(conversionService, request);

        SearchListHelper<RemoteUnitIdentifier> listhelper = new SearchListHelper<RemoteUnitIdentifier>();
        Page p = new Page();
        if("常用单位".equals(identifierName)){
        	searchBuilder.appendSort(Direction.DESC, "frequency");
        	listhelper.execute(searchBuilder, unitService.getRepository(), 1, 10);
        	p.setTotal(10L);
        }else{
        	 searchBuilder.clearSearchBuilder().findSearchParam(request);
        	 searchBuilder.appendSort(Direction.ASC, "orderNum");
             if(!Tools.isEmptyString(identifierName)){
             	searchBuilder.getFilterBuilder().equal("identifierName", identifierName);
             }
             
             listhelper.execute(searchBuilder, unitService.getRepository(), page, rows);
             p.setTotal(listhelper.getCount());
        }
        
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), RemoteUnitIdentifier.class));
        
        return p;
    }
}
