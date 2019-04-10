package cn.com.chaochuang.doc.expiredate.web;



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
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.doc.expiredate.bean.SysDeadLineTypeEditBean;
import cn.com.chaochuang.doc.expiredate.domain.SysDeadLineType;
import cn.com.chaochuang.doc.expiredate.service.SysDeadLineTypeService;



/** 
 * @ClassName: SysDeadLineTypeController 
 * @Description: 限时办结要求初始化设置
 * @author: yuchunshu
 * @date: 2017年10月17日 下午4:52:12  
 */
@Controller
@RequestMapping("system/deadLineType")
public class SysDeadLineTypeController {

	@Autowired
    protected ConversionService    	 conversionService;

	@Autowired
    private LogService               logService;
	
	@Autowired
	private SysDeadLineTypeService   sysDeadLineTypeService;
	
    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/system/deadLineType/list");
        return mav;
    }
    
    @RequestMapping(value = "list.json")
    @ResponseBody
    public Page listjson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
    	Page p = new Page();
    	String node = request.getParameter("node");
        try {
        	SearchBuilder<SysDeadLineType, Long> searchBuilder = new SearchBuilder<SysDeadLineType, Long>(conversionService);
            searchBuilder.clearSearchBuilder().findSearchParam(request);
            
            
            searchBuilder.appendSort(Direction.ASC, "documentType");
            searchBuilder.appendSort(Direction.ASC, "deadlineType");
            
            SearchListHelper<SysDeadLineType> listhelper = new SearchListHelper<SysDeadLineType>();
            listhelper.execute(searchBuilder, sysDeadLineTypeService.getRepository(), page, rows);
            
            p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), SysDeadLineType.class));
            p.setTotal(listhelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "限时办结要求初始化设置-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
        
        return p;
    }
    
    @RequestMapping("/new")
    public ModelAndView newPage() {
        ModelAndView mav = new ModelAndView("/system/deadLineType/edit");
        return mav;
    }

    @RequestMapping("/edit")
    public ModelAndView editPage(String id) {
        ModelAndView mav = new ModelAndView("/system/deadLineType/edit");
        SysDeadLineType obj = this.sysDeadLineTypeService.findOne(id);
        mav.addObject("obj", obj);
        return mav;
    }
    
    /**
     * 保存数据
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo save(SysDeadLineTypeEditBean bean, HttpServletRequest request, HttpServletResponse response) {

        try {
            String id = sysDeadLineTypeService.saveSysDeadLineType(bean);

            return new ReturnInfo(id, null, "保存成功！");
        } catch (Exception e) {
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo del(String[] ids, HttpServletRequest request) {
        try {
            if (ids != null && ids.length > 0) {
                for (String id : ids) {
                    this.sysDeadLineTypeService.delSysDeadLineType(id);
                    logService.saveLog(SjType.普通操作, "删除限时办结要求初始化设置信息：,删除id为'" + id + "'的记录",LogStatus.成功, request);
                }
            }

            return new ReturnInfo("1", null, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    /**
     * @Title: repeatInfoCheck
     * @Description: 环节限办时间重复性检查，同一类型、同一环节只能有一个限办时间设置
     * @param request
     * @param response
     * @return
     * @return: String
     */
    @RequestMapping("/checkRepeatInfo.json")
    @ResponseBody
    public boolean checkRepeatInfo(HttpServletRequest request, HttpServletResponse response) {
    	
        String deadlineType = request.getParameter("deadlineType");
        String documentType = request.getParameter("documentType");
        String id = request.getParameter("id");
        boolean result = false;
        try {
        	result = sysDeadLineTypeService.repeatInfoCheck(deadlineType, documentType,id);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }
}
