package cn.com.chaochuang.doc.archive.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.doc.archive.bean.DocArchivesBorrowEditBean;
import cn.com.chaochuang.doc.archive.domain.DocArchives;
import cn.com.chaochuang.doc.archive.domain.DocArchivesBorrow;
import cn.com.chaochuang.doc.archive.service.DocArchivesBorrowService;
import cn.com.chaochuang.doc.archive.service.DocArchivesService;

/**
 * @author dengl 2017.11.30
 *
 */
@Controller
@RequestMapping("doc/archives/borrow")
public class ArchiveBorrowController {
	
	@Autowired
	private	DocArchivesBorrowService	docArchivesBorrowService;
	
    @Autowired
    private DocArchivesService     		docArchivesService;
    
    @Autowired
    private LogService             		logService;
	
    // 公文借阅页面
    @RequestMapping("/edit")
    public ModelAndView borrowArch(String ids) {
    	ModelAndView mav = null;
    	if(ids.indexOf(",") > 0){
    		mav = new ModelAndView("/doc/archives/borrow/editmult");
    		mav.addObject("ids", ids);
    		return mav;
    	}else{
    		mav = new ModelAndView("/doc/archives/borrow/edit");
    		DocArchives archivs = this.docArchivesService.findOne(Long.valueOf(ids));
    		mav.addObject("obj", archivs);
    		return mav;
    	}
    }
    
    // 保存案卷借阅
    @RequestMapping("/saveBorrowArch.json")
    @ResponseBody
    public ReturnInfo saveSelectArchJson(DocArchivesBorrowEditBean bean, HttpServletRequest request, HttpServletResponse response) {
        try {
        	String id = this.docArchivesBorrowService.saveBorrowArch(bean);
        	if (StringUtils.isNotEmpty(id)) {
        		DocArchivesBorrow borrow = this.docArchivesBorrowService.findOne(id);
                logService.saveLog(SjType.普通操作, "借阅档案卷，借阅档案卷id为：" + borrow.getArchId() + ",id为：" + id,LogStatus.成功, request);
            }
            return new ReturnInfo(id, null, "保存成功!");
        }catch (Exception e) {
        	e.printStackTrace();
            logService.saveLog(SjType.普通操作, "维护档案卷-借阅档案卷《" + bean.getBorrowUserName() + "》：失败！",LogStatus.失败, request);
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

}
