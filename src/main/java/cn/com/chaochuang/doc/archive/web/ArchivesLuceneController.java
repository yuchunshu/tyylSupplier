package cn.com.chaochuang.doc.archive.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.EasyUIPage;
import cn.com.chaochuang.doc.archive.bean.LuceneEditBean;
import cn.com.chaochuang.doc.archive.service.ArchivesLuceneService;

/**
 * 
 * @author dengl 2017.12.08
 *
 */
@Controller
@RequestMapping("doc/archives/lucene")
public class ArchivesLuceneController {
	
	@Autowired
	private ArchivesLuceneService service;
	
	// 公文档案检索
    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/doc/archives/lucene/list");
        return mav;
    }
    
    @RequestMapping("/list.json")
	@ResponseBody
    public EasyUIPage listJson(LuceneEditBean bean, HttpServletRequest request) {
        return this.service.archiveSearchLuceneIndex(bean,request);
    }	
}
