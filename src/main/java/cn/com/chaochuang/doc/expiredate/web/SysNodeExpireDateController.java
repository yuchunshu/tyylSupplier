package cn.com.chaochuang.doc.expiredate.web;



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
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.doc.expiredate.bean.SysNodeExpireDateEditBean;
import cn.com.chaochuang.doc.expiredate.bean.SysNodeExpireDateShowBean;
import cn.com.chaochuang.doc.expiredate.domain.SysNodeExpireDate;
import cn.com.chaochuang.doc.expiredate.service.SysNodeExpireDateService;



/** 
 * @ClassName: SysNodeExpireDateController 
 * @Description: 环节限办时间初始化设置
 * @author: yuchunshu
 * @date: 2017年10月11日 下午5:52:12  
 */
@Controller
@RequestMapping("system/nodeExpireDate")
public class SysNodeExpireDateController {

	@Autowired
    protected ConversionService    	 conversionService;

	@Autowired
    private LogService               logService;
	
	@Autowired
	private SysNodeExpireDateService sysNodeExpireDateService;
	
    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/system/nodeExpireDate/list");
        return mav;
    }
    
    @RequestMapping(value = "list.json")
    @ResponseBody
    public Page listjson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
    	Page p = new Page();
    	String node = request.getParameter("node");
        try {
        	SearchBuilder<SysNodeExpireDate, Long> searchBuilder = new SearchBuilder<SysNodeExpireDate, Long>(conversionService);
            searchBuilder.clearSearchBuilder().findSearchParam(request);
            
            if (StringUtils.isNotBlank(node)) {
                searchBuilder.getFilterBuilder().like("node.nodeName", node.trim() + "%");
            }
            
            searchBuilder.appendSort(Direction.ASC, "documentType");
            searchBuilder.appendSort(Direction.ASC, "deadlineType");
            
            SearchListHelper<SysNodeExpireDate> listhelper = new SearchListHelper<SysNodeExpireDate>();
            listhelper.execute(searchBuilder, sysNodeExpireDateService.getRepository(), page, rows);
            
            p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), SysNodeExpireDateShowBean.class));
            p.setTotal(listhelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "环节限办时间设置-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
        
        return p;
    }
    
    @RequestMapping("/new")
    public ModelAndView newPage() {
        ModelAndView mav = new ModelAndView("/system/nodeExpireDate/edit");
        return mav;
    }

    @RequestMapping("/edit")
    public ModelAndView editPage(String id) {
        ModelAndView mav = new ModelAndView("/system/nodeExpireDate/edit");
        SysNodeExpireDate obj = this.sysNodeExpireDateService.findOne(id);
        mav.addObject("obj", obj);
        return mav;
    }
    
    /**
     * 保存数据
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo save(SysNodeExpireDateEditBean bean, HttpServletRequest request, HttpServletResponse response) {

        try {
            String id = sysNodeExpireDateService.saveSysNodeExpireDate(bean);

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
                    this.sysNodeExpireDateService.delSysNodeExpireDate(id);
                    logService.saveLog(SjType.普通操作, "删除环节限办时间信息：,删除id为'" + id + "'的记录",LogStatus.成功, request);
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
    	
        String documentType = request.getParameter("documentType");
        String deadlineType = request.getParameter("deadlineType");
        String nodeId = request.getParameter("nodeId");
        String id = request.getParameter("id");
        boolean result = false;
        try {
        	result = sysNodeExpireDateService.repeatInfoCheck(documentType,deadlineType, nodeId,id);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }
    
    
    @RequestMapping("/getExpireDay.json")
    @ResponseBody
    public Integer getExpireDay(HttpServletRequest request, HttpServletResponse response) {
    	
        String documentType = request.getParameter("documentType");
        String deadlineType = request.getParameter("deadlineType");
        Integer result = null;
        try {
        	result = sysNodeExpireDateService.getExpireDay(documentType,deadlineType);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }
    
    @RequestMapping("/getRemainingTime.json")
    @ResponseBody
    public Integer getRemainingTime(HttpServletRequest request, HttpServletResponse response) {
    	
        String documentType = request.getParameter("documentType");
        String deadlineType = request.getParameter("deadlineType");
        String id = request.getParameter("id");
        Integer result = null;
        try {
        	result = sysNodeExpireDateService.getRemainingTime(documentType,deadlineType,id);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }
}
