package cn.com.chaochuang.doc.archive.web;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.serial.service.SerialNumberService;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.doc.archive.bean.DocPaperArchivesEditBean;
import cn.com.chaochuang.doc.archive.domain.DocArchives;
import cn.com.chaochuang.doc.archive.domain.DocArchivesBasic;
import cn.com.chaochuang.doc.archive.domain.DocPaperArchives;
import cn.com.chaochuang.doc.archive.reference.BasicType;
import cn.com.chaochuang.doc.archive.repository.DocArchivesBasicRepository;
import cn.com.chaochuang.doc.archive.service.DocArchivesService;
import cn.com.chaochuang.doc.archive.service.DocPaperArchivesService;


/**
 * @author dengl 2017.11.24
 *
 */
@Controller
@RequestMapping("doc/archives/paper")
public class PaperArchiveController {
	
	@Autowired
	private DocPaperArchivesService		docPaperArchivesService;
	
    @Autowired
    private LogService             		logService;
	
    @Autowired
    private DocArchivesBasicRepository 	docArchivesBasicRepository;
    
    @Autowired
    private DocArchivesService			docArchivesService;
    
    @Autowired
	private SysAttachService 			attachService;
    
    @Autowired
    private SerialNumberService    		serialNumberService;
	
	@RequestMapping("/list")
    public ModelAndView maintain() {
        ModelAndView mav = new ModelAndView("/doc/archives/paper/list");
        List<DocArchivesBasic> fileCabinetList = this.docArchivesBasicRepository.findByBasicTypeAndDepId(
    			BasicType.档案柜, Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));//获取档案柜list
    	List<DocArchivesBasic> dossierTypeList = this.docArchivesBasicRepository.findByBasicTypeAndDepId(
    			BasicType.案卷类型, Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));//获取案卷类型list
    	List<DocArchivesBasic> mediumTypeList = this.docArchivesBasicRepository.findByBasicTypeAndDepId(
    			BasicType.介质类型, Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));//获取介质类型list
    	
		Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        int[] years={year,year-1,year-2};
        SysDepartment dept = (SysDepartment)UserTool.getInstance().getCurrentUserDepartment();
        mav.addObject("serialNumber", this.serialNumberService.getArchNoSN(DocArchives.class.getSimpleName(),"","000"));
        mav.addObject("years", years);
    	mav.addObject("fileCabinetList",fileCabinetList);
    	mav.addObject("dossierTypeList",dossierTypeList);
    	mav.addObject("mediumTypeList",mediumTypeList);
    	if(StringUtils.isNotEmpty(dept.getDeptCode())){
        	mav.addObject("deptCode", dept.getDeptCode());
        }
        return mav;
    }
	
	// 保存纸质信息
    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo save(DocPaperArchivesEditBean bean, HttpServletRequest request, HttpServletResponse response) {
    	try {
    		String id = this.docPaperArchivesService.saveDocPaperArchives(bean);
    		if(StringUtils.isNotEmpty(id)){
    			logService.saveLog(SjType.普通操作, "新增档案卷，档案名为：" + bean.getArchName() + ",id为：" + id,LogStatus.成功, request);
    			logService.saveLog(SjType.普通操作, "新增纸质信息登记，文件标题为：" + bean.getTitle() + ",id为：" + id,LogStatus.成功, request);
    		}
    		return new ReturnInfo(id, null, "保存成功!");
    	} catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "维护档案卷-新增纸质信息归档登记《" + bean.getArchName() + "》：失败！",LogStatus.失败, request);
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }
    
    //纸质公文详情
    @RequestMapping("/detailPaperFile")
    public ModelAndView detailPaper(@RequestParam("fileId") String fileId, @RequestParam("archId") String archId, HttpServletRequest request) {
        ModelAndView mav = null;
        DocPaperArchives paper = this.docPaperArchivesService.findOne(fileId);
        if (paper == null) {
			mav = new ModelAndView("/doc/docevent/message");
			mav.addObject("hints", "公文信息读取失败，该公文可能已被删除，请刷新或重新进入列表页面。");
			return mav;
		}
        DocArchives	archives = this.docArchivesService.findOne(Long.valueOf(archId));
        mav = new ModelAndView("/doc/archives/paper/detail");
        mav.addObject("obj",paper);
        mav.addObject("archives",archives);
        mav.addObject("attachMap",
				this.attachService.getAttachMap(paper.getId(), DocPaperArchives.class.getSimpleName()));
		return mav;
    }
}
