/*
 * FileName:    NoticeController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.doc.log.web;


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
import cn.com.chaochuang.doc.log.bean.DocModifyLogEditBean;
import cn.com.chaochuang.doc.log.bean.DocModifyLogQueryBean;
import cn.com.chaochuang.doc.log.domain.DocModifyLog;
import cn.com.chaochuang.doc.log.service.DocModifyLogService;

/**
 * @author yuchunshu
 *
 */
@Controller
@RequestMapping("doc/modify")
public class DocModifyLogController {

    @Autowired
    private DocModifyLogService DocModifyLogService;

    @Autowired
    protected ConversionService conversionService;

    @Autowired
    private LogService          logService;

    @RequestMapping("/list")
    public ModelAndView list(String fileId, String itemId) {
        ModelAndView mav = new ModelAndView("/doc/docevent/docmodifyhistory");
        mav.addObject("fileId", fileId);
        mav.addObject("itemId", itemId);
        return mav;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    // 对页面Pag的操作
    public Page listjson(Integer page, Integer rows, HttpServletRequest request) {
    	Page p = new Page();
    	try {
    		SearchBuilder<DocModifyLog, Long> searchBuilder = new SearchBuilder<DocModifyLog, Long>(conversionService);
            searchBuilder.clearSearchBuilder().findSearchParam(request);
            searchBuilder.appendSort(Direction.DESC, "modTime");
            SearchListHelper<DocModifyLog> listhelper = new SearchListHelper<DocModifyLog>();
            listhelper.execute(searchBuilder, DocModifyLogService.getRepository(), page, rows);
            
            p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), DocModifyLogQueryBean.class));
            p.setTotal(listhelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "公文编辑-修改历史-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
    	
        return p;
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo save(DocModifyLogEditBean bean, HttpServletRequest request, HttpServletResponse response) {

        try {
            String logId = this.DocModifyLogService.saveDocModifyLog(bean);
            logService.saveLog(SjType.普通操作, "公文编辑，修改id为'" + bean.getId() + "'的记录",LogStatus.成功, request);
            return new ReturnInfo(logId, null, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "公文编辑，修改id为'" + bean.getId() + "'的记录失败！",LogStatus.失败, request);
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

}
