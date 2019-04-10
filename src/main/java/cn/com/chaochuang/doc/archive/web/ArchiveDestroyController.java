package cn.com.chaochuang.doc.archive.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import cn.com.chaochuang.doc.archive.bean.DocArchivesDestroyEditBean;
import cn.com.chaochuang.doc.archive.bean.DocArchivesDestroyShowBean;
import cn.com.chaochuang.doc.archive.domain.DocArchives;
import cn.com.chaochuang.doc.archive.domain.DocArchivesDestroy;
import cn.com.chaochuang.doc.archive.repository.DocArchivesDestroyRepository;
import cn.com.chaochuang.doc.archive.service.DocArchivesDestroyService;
import cn.com.chaochuang.doc.archive.service.DocArchivesService;

/**
 * @author dengl 2017.11.30
 *
 */
@Controller
@RequestMapping("doc/archives/destroy")
public class ArchiveDestroyController {
	
	@Autowired
	private DocArchivesDestroyService		docArchivesDestroyService;
	
    @Autowired
    private DocArchivesService     			docArchivesService;
    
    @Autowired
    private LogService             			logService;
    
    @Autowired
    private DocArchivesDestroyRepository	repository;
    
    @Autowired
    private ConversionService      			conversionService;
	
	// 公文销毁页面
    @RequestMapping("/edit")
    public ModelAndView destroyArch(String ids) {
    	ModelAndView mav = null;
    	if(ids.indexOf(",") > 0){
    		mav = new ModelAndView("/doc/archives/destroy/editmult");
    		mav.addObject("ids", ids);
    		return mav;
    	}else{
    		mav = new ModelAndView("/doc/archives/destroy/edit");
    		DocArchives archivs = this.docArchivesService.findOne(Long.valueOf(ids));
    		mav.addObject("obj", archivs);
    		return mav;
    	}
    }
    
    // 保存案卷销毁
    @RequestMapping("/saveDestroyArch.json")
    @ResponseBody
    public ReturnInfo saveDestroyArchJson(DocArchivesDestroyEditBean bean, HttpServletRequest request, HttpServletResponse response) {
        try {
        	String id = this.docArchivesDestroyService.saveDestroyArch(bean);
        	if (StringUtils.isNotEmpty(id)) {
        		DocArchivesDestroy destroy = this.docArchivesDestroyService.findOne(id);
                logService.saveLog(SjType.普通操作, "销毁档案卷，销毁档案卷id为：" + destroy.getArchId() + ",id为：" + id,LogStatus.成功, request);
            }
            return new ReturnInfo(id, null, "保存成功!");
        }catch (Exception e) {
        	e.printStackTrace();
            logService.saveLog(SjType.普通操作, "维护档案卷-销毁档案卷《" + bean.getDestroyUserName() + "》：失败！",LogStatus.失败, request);
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }
    
    // 公文销毁列表页面
    @RequestMapping("/list")
    public ModelAndView list() {
    	ModelAndView mav = new ModelAndView("/doc/archives/destroy/list");
		return mav;
    }
    
    // 公文销毁列表页面查询
    @RequestMapping("/list.json")
    @ResponseBody
    // 对页面Pag的操作
    public Page listjson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
        SearchBuilder<DocArchivesDestroy, String> searchBuilder = new SearchBuilder<DocArchivesDestroy, String>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        if(StringUtils.isNotBlank(sort)){
        	sort = sort.replace("Show", "");
        	searchBuilder.appendSort("asc".equals(order)? Direction.ASC:Direction.DESC, sort);
        }
        else{
        	searchBuilder.appendSort(Direction.DESC, "destroyDate");
        }
        
        SearchListHelper<DocArchivesDestroy> listhelper = new SearchListHelper<DocArchivesDestroy>();
        listhelper.execute(searchBuilder, this.repository, page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), DocArchivesDestroyShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }
    
    @RequestMapping("/detail")
    public ModelAndView detail(Long archId) {
        ModelAndView mav = new ModelAndView("/doc/archives/destroy/detail");
        if (archId != null) {
            DocArchives archivs = this.docArchivesService.findOne(archId);
            mav.addObject("obj", archivs);
        }
        return mav;
    }

}
