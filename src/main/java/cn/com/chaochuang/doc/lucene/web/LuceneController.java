package cn.com.chaochuang.doc.lucene.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.EasyUIPage;
import cn.com.chaochuang.doc.lucene.bean.LuceneInfo;
import cn.com.chaochuang.doc.lucene.service.LuceneService;

/**
 * 
 * @author LJX
 *
 */
@Controller
@RequestMapping("doc/lucene")
public class LuceneController {
	
	@Autowired
	private LuceneService service;

	@RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/doc/lucene/list");
        return mav;
    }
	
	@RequestMapping("/list.json")
	@ResponseBody
    public EasyUIPage listJson(LuceneInfo info, HttpServletRequest request) {
        return this.service.searchLuceneIndex(info,request);
    }	
	
}
