/*
 * FileName:    ReceiveFileController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.archive.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import cn.com.chaochuang.common.serial.service.SerialNumberService;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.AttachUtils;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.archive.bean.DoPigeEditBean;
import cn.com.chaochuang.doc.archive.bean.DocArchivesBorrowShowBean;
import cn.com.chaochuang.doc.archive.bean.DocArchivesEditBean;
import cn.com.chaochuang.doc.archive.bean.DocArchivesShowBean;
import cn.com.chaochuang.doc.archive.domain.DocArchives;
import cn.com.chaochuang.doc.archive.domain.DocArchivesBasic;
import cn.com.chaochuang.doc.archive.domain.DocArchivesBorrow;
import cn.com.chaochuang.doc.archive.reference.ArchStatus;
import cn.com.chaochuang.doc.archive.reference.BasicType;
import cn.com.chaochuang.doc.archive.repository.DocArchivesBasicRepository;
import cn.com.chaochuang.doc.archive.repository.DocArchivesBorrowRepository;
import cn.com.chaochuang.doc.archive.repository.DocArchivesRepository;
import cn.com.chaochuang.doc.archive.service.DocArchivesService;
import cn.com.chaochuang.doc.event.bean.OaDocFileShowBean;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.event.reference.DocSource;
import cn.com.chaochuang.doc.event.reference.FileStatusFlag;
import cn.com.chaochuang.doc.event.repository.OaDocFileRepository;
import cn.com.chaochuang.doc.event.service.OaDocFileService;
import cn.com.chaochuang.doc.letter.service.DocDepLetterService;
import cn.com.chaochuang.doc.readmatter.service.ReadMatterService;
import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.workflow.inst.bean.NodeInstQueryBean;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.inst.service.WfAuditOpinionService;
import cn.com.chaochuang.workflow.inst.service.WfNodeInstService;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfApprResult;
import cn.com.chaochuang.workflow.reference.WfInstStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
/**
 * @author dengl 2017.11.23
 *
 */
@Controller
@RequestMapping("doc/archives")
public class ArchiveController {

	@Value(value = "${upload.appid.docFileAttach}")
    private String                 		appid;
    
    @Autowired
    private SysAttachService       		attachService;
    
	@Autowired
	private DocArchivesService 			docArchivesService;

	@Autowired
	private OaDocFileRepository 		docFileRepository;

	@Autowired
	private OaDocFileService 			docFileService;

	@Autowired
	private ReadMatterService 			readMatterService;

	@Autowired
	private SysAttachService 			docAttachService;

	@Autowired
	private WfAuditOpinionService 		auditOpinionService;

	@Autowired
	private ConversionService 			conversionService;

	@Autowired
	private LogService 					logService;

	@Autowired
	private WfNodeInstService 			nodeInstService;

	@Autowired
	private DocDepLetterService 		depLetterService;

	@Autowired
	private DocArchivesBasicRepository 	docArchivesBasicRepository;

	@Autowired
	private DocArchivesRepository 		docArchivesRepository;

	@Autowired
	private DocArchivesBorrowRepository borrowRepository;

	@Autowired
	private SerialNumberService 		serialNumberService;

	@Value(value = "${upload.rootpath}")
	private String 						rootPath;

	@Value(value = "${qrcode.indexPath}")
	private String 						QRcodeIndex;

	@RequestMapping("/maintain")
	public ModelAndView maintain() {
		ModelAndView mav = new ModelAndView("/doc/archives/maintain");
		return mav;
	}

	@RequestMapping("/listMaintain.json")
	@ResponseBody
	// 对页面Pag的操作
	public Page maintainjson(Integer page, Integer rows, HttpServletRequest request, String sort, String order) {
		Page p = new Page();
		try {
			SearchBuilder<DocArchives, Long> searchBuilder = new SearchBuilder<DocArchives, Long>(conversionService);
			searchBuilder.clearSearchBuilder().findSearchParam(request);
			// 按部门查询
			searchBuilder.getFilterBuilder().equal("depId", UserTool.getInstance().getCurrentUserDepartmentId());

			if (StringUtils.isNotBlank(sort)) {
				sort = sort.replace("Show", "");

				searchBuilder.appendSort("asc".equals(order) ? Direction.ASC : Direction.DESC, sort);
			} else {
				searchBuilder.appendSort(Direction.DESC, "createDate");
				searchBuilder.appendSort(Direction.ASC, "orderNum");
			}

			SearchListHelper<DocArchives> listhelper = new SearchListHelper<DocArchives>();
			listhelper.execute(searchBuilder, docArchivesService.getRepository(), page, rows);

			p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), DocArchivesShowBean.class));
			p.setTotal(listhelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "维护档案卷-列表查询：失败！", LogStatus.失败, request);
			e.printStackTrace();
		}

		return p;
	}

	@RequestMapping("/docCountQuery")
	public ModelAndView docCountQuery() {
		ModelAndView mav = new ModelAndView("/doc/archives/docCountQuery");
		return mav;
	}

	// 保存档案卷
	@RequestMapping("/save.json")
	@ResponseBody
	public ReturnInfo save(DocArchivesEditBean bean, HttpServletRequest request, HttpServletResponse response) {
		try {
			Long id = this.docArchivesService.saveDocArchives(bean);
			if (bean.getId() != null) {
				logService.saveLog(SjType.普通操作, "新增档案卷，档案名为：" + bean.getArchName() + ",id为：" + id, LogStatus.成功,
						request);
			}
			return new ReturnInfo(id.toString(), null, "保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			logService.saveLog(SjType.普通操作, "维护档案卷-新增档案卷《" + bean.getArchName() + "》：失败！", LogStatus.失败, request);
			return new ReturnInfo(null, "服务器连接不上！", null);
		}
	}

	@RequestMapping("/edit")
	public ModelAndView editPage(Long id) {
		ModelAndView mav = new ModelAndView("/doc/archives/edit");
		DocArchives archivs = this.docArchivesService.findOne(id);
		SysDepartment dept = (SysDepartment) UserTool.getInstance().getCurrentUserDepartment();
		mav.addObject("obj", archivs);
		if (StringUtils.isNotEmpty(dept.getDeptCode())) {
			mav.addObject("deptCode", dept.getDeptCode());
		}
		mav.addObject("serialNumber",
				this.serialNumberService.getArchNoSN(DocArchives.class.getSimpleName(), "G", "000"));
		return mav;
	}

	@RequestMapping("/new*")
	public ModelAndView newPage() {
		SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
		ModelAndView mav = new ModelAndView("/doc/archives/edit");
		mav.addObject("currUser", user);
		return mav;
	}

	@RequestMapping("/detail")
	public ModelAndView detail(Long id) {
		ModelAndView mav = new ModelAndView("/doc/archives/detail");
		if (id != null) {
			DocArchives archivs = this.docArchivesService.findOne(id);
			mav.addObject("obj", archivs);
		}
		return mav;
	}

	// 删除案卷
	@RequestMapping("/delete.json")
	@ResponseBody
	public ReturnInfo del(String ids, HttpServletRequest request) {
		try {
			String message = this.docArchivesService.deleteDocArchives(ids, request);
			return new ReturnInfo("1", null, message);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnInfo(null, "服务器连接不上！", null);
		}
	}

	// 待归档公文归档编辑页面
	@RequestMapping("/new")
	public ModelAndView edit(String fileId) {
		ModelAndView mav = new ModelAndView("/doc/archives/file/edit");
		OaDocFile file = null;
		if (StringUtils.isNotEmpty(fileId)) {
			file = this.docFileService.findOne(fileId);
		}
		List<DocArchivesBasic> fileCabinetList = this.docArchivesBasicRepository.findByBasicTypeAndDepId(BasicType.档案柜,
				Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));// 获取档案柜list
		List<DocArchivesBasic> dossierTypeList = this.docArchivesBasicRepository.findByBasicTypeAndDepId(BasicType.案卷类型,
				Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));// 获取案卷类型list
		List<DocArchivesBasic> mediumTypeList = this.docArchivesBasicRepository.findByBasicTypeAndDepId(BasicType.介质类型,
				Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));// 获取介质类型list

		Calendar date = Calendar.getInstance();
		int year = date.get(Calendar.YEAR);
		int[] years = { year, year - 1, year - 2 };
		SysDepartment dept = (SysDepartment) UserTool.getInstance().getCurrentUserDepartment();
		mav.addObject("serialNumber",
				this.serialNumberService.getArchNoSN(DocArchives.class.getSimpleName(), "G", "000"));
		mav.addObject("years", years);
		mav.addObject("fileCabinetList", fileCabinetList);
		mav.addObject("dossierTypeList", dossierTypeList);
		mav.addObject("mediumTypeList", mediumTypeList);
		mav.addObject("obj", file);
		if (StringUtils.isNotEmpty(dept.getDeptCode())) {
			mav.addObject("deptCode", dept.getDeptCode());
		}
		return mav;
	}

	// 案卷迁移页面
	@RequestMapping("/selectArch")
	public ModelAndView selectArch(String ids) {
		ModelAndView mav = null;
		List<DocArchivesBasic> fileCabinetList = this.docArchivesBasicRepository.findByBasicTypeAndDepId(BasicType.档案柜,
				Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));// 获取档案柜list
		List<DocArchivesBasic> dossierTypeList = this.docArchivesBasicRepository.findByBasicTypeAndDepId(BasicType.案卷类型,
				Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));// 获取案卷类型list
		List<DocArchivesBasic> mediumTypeList = this.docArchivesBasicRepository.findByBasicTypeAndDepId(BasicType.介质类型,
				Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));// 获取介质类型list
		Calendar date = Calendar.getInstance();
		int year = date.get(Calendar.YEAR);
		int[] years = { year, year - 1, year - 2 };
		SysDepartment dept = (SysDepartment) UserTool.getInstance().getCurrentUserDepartment();
		if (ids.indexOf(",") > 0) {
			mav = new ModelAndView("/doc/archives/editmult");
			mav.addObject("years", years);
			mav.addObject("fileCabinetList", fileCabinetList);
			mav.addObject("dossierTypeList", dossierTypeList);
			mav.addObject("mediumTypeList", mediumTypeList);
			mav.addObject("ids", ids);
			return mav;
		} else {
			mav = new ModelAndView("/doc/archives/edit");
			DocArchives archivs = this.docArchivesService.findOne(Long.valueOf(ids));
			mav.addObject("serialNumber",
					this.serialNumberService.getArchNoSN(DocArchives.class.getSimpleName(), "G", "000"));
			mav.addObject("years", years);
			mav.addObject("fileCabinetList", fileCabinetList);
			mav.addObject("dossierTypeList", dossierTypeList);
			mav.addObject("mediumTypeList", mediumTypeList);
			mav.addObject("obj", archivs);
			if (StringUtils.isNotEmpty(dept.getDeptCode())) {
				mav.addObject("deptCode", dept.getDeptCode());
			}
			return mav;
		}
	}

	// 保存案卷迁移
	@RequestMapping("/saveSelectArch.json")
	@ResponseBody
	public ReturnInfo saveSelectArchJson(DocArchivesEditBean bean, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Long id = this.docArchivesService.saveSelectArch(bean);
			if (bean.getId() != null) {
				logService.saveLog(SjType.普通操作, "迁移档案卷，档案名为：" + bean.getArchName() + ",id为：" + id, LogStatus.成功,
						request);
			}
			return new ReturnInfo(id.toString(), null, "保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			logService.saveLog(SjType.普通操作, "维护档案卷-迁移档案卷《" + bean.getArchName() + "》：失败！", LogStatus.失败, request);
			return new ReturnInfo(null, "服务器连接不上！", null);
		}
	}

	@RequestMapping("/selectArch.json")
	@ResponseBody
	// 对页面Pag的操作
	public Page selectArchjson(Integer page, Integer rows, HttpServletRequest request, String sort, String order) {
		SearchBuilder<DocArchives, Long> searchBuilder = new SearchBuilder<DocArchives, Long>(conversionService);
		searchBuilder.clearSearchBuilder().findSearchParam(request);
		// 按部门查询
		searchBuilder.getFilterBuilder().equal("depId", UserTool.getInstance().getCurrentUserDepartmentId());

		if (StringUtils.isNotBlank(sort)) {
			sort = sort.replace("Show", "");
			searchBuilder.appendSort("asc".equals(order) ? Direction.ASC : Direction.DESC, sort);
		} else {
			searchBuilder.appendSort(Direction.ASC, "createDate");
			searchBuilder.appendSort(Direction.ASC, "orderNum");
		}

		SearchListHelper<DocArchives> listhelper = new SearchListHelper<DocArchives>();
		listhelper.execute(searchBuilder, docArchivesService.getRepository(), page, rows);
		Page p = new Page();
		p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), DocArchivesShowBean.class));
		p.setTotal(listhelper.getCount());
		return p;
	}

	// 档案归还
	@RequestMapping("/saveReturn.json")
	@ResponseBody
	public ReturnInfo saveReturn(String ids, HttpServletRequest request) {
		try {
			if (StringUtils.isNotEmpty(ids)) {
				String id = this.docArchivesService.saveReturnArch(ids);
				if (StringUtils.isNotEmpty(id)) {
					logService.saveLog(SjType.普通操作, "归还档案卷，档案名为：" + id + ",id为：" + id, LogStatus.成功, request);
				}
				return new ReturnInfo(id, null, "保存成功!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ReturnInfo(null, "服务器连接不上！", null);
	}

	// 借阅详情
	@RequestMapping("/detailBorrow")
	public ModelAndView detailBorrow(@RequestParam("id") String id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/doc/archives/selectarchives");
		mav.addObject("archId", id);
		return mav;
	}

	// 借阅详情查询
	@RequestMapping("/selectBorrowArch.json")
	@ResponseBody
	// 对页面Pag的操作
	public Page selectBorrowArchjson(Integer page, Integer rows, HttpServletRequest request, String sort,
			String order) {
		SearchBuilder<DocArchivesBorrow, String> searchBuilder = new SearchBuilder<DocArchivesBorrow, String>(
				conversionService);
		searchBuilder.clearSearchBuilder().findSearchParam(request);
		String archId = request.getParameter("archId");
		if (StringUtils.isNotEmpty(archId)) {
			searchBuilder.getFilterBuilder().equal("archId", Long.valueOf(archId));
		}
		if (StringUtils.isNotBlank(sort)) {
			sort = sort.replace("Show", "");
			searchBuilder.appendSort("asc".equals(order) ? Direction.ASC : Direction.DESC, sort);
		} else {
			searchBuilder.appendSort(Direction.DESC, "createTime");
		}

		SearchListHelper<DocArchivesBorrow> listhelper = new SearchListHelper<DocArchivesBorrow>();
		listhelper.execute(searchBuilder, this.borrowRepository, page, rows);
		Page p = new Page();
		p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), DocArchivesBorrowShowBean.class));
		p.setTotal(listhelper.getCount());
		return p;
	}

	// 文件归档
	@RequestMapping("/dealDoPige.json")
	@ResponseBody
	public ReturnInfo doPige(DoPigeEditBean bean, HttpServletRequest request, HttpServletResponse response) {
		try {
			String message = this.docArchivesService.saveFileToArch(bean, request);
			return new ReturnInfo("1", null, "归档成功!");
		} catch (Exception e) {
			e.printStackTrace();
			logService.saveLog(SjType.普通操作, "待归档公文-归档：失败！", LogStatus.失败, request);
			return new ReturnInfo(null, "服务器连接不上！", null);
		}
	}

	// 文件迁移
	@RequestMapping("/dealMoveArchive.json")
	@ResponseBody
	public ReturnInfo moveArchive(DoPigeEditBean bean, HttpServletRequest request, HttpServletResponse response) {
		try {
			ReturnInfo returnInfo = this.docArchivesService.saveChangeArch(bean, request);
			return returnInfo;
		} catch (Exception e) {
			e.printStackTrace();
			logService.saveLog(SjType.普通操作, "归档后管理-迁移：失败！", LogStatus.失败, request);
			return new ReturnInfo(null, "服务器连接不上！", null);
		}
	}

	// 文件删除
	@RequestMapping("/deleteFile.json")
	@ResponseBody
	public ReturnInfo deleteFile(DoPigeEditBean bean, HttpServletRequest request, HttpServletResponse response) {
		try {
			String message = this.docArchivesService.deleteFile(bean, request);
			return new ReturnInfo("1", null, message);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnInfo(null, "服务器连接不上！", null);
		}
	}

	// 检查档案编号
	// @RequestMapping("/checkArchNo.json")
	// @ResponseBody
	// public boolean checkArchNo(String archNo, Long archId) {
	// try {
	// boolean bon = this.docArchivesService.checkArchNo(archNo, archId);
	// return bon;
	// } catch (Exception e) {
	// e.printStackTrace();
	// return false;
	// }
	// }

	// 检查档案编号
	@RequestMapping("/checkArchNo.json")
	@ResponseBody
	public ReturnInfo checkArchNo(String archNo, String counterNo, String archType) {
		if (StringUtils.isNotEmpty(archNo) && StringUtils.isNotEmpty(counterNo) && StringUtils.isNotEmpty(archType)) {
			List<DocArchives> list = this.docArchivesRepository.findByArchNoAndCounterNoAndArchType(archNo, counterNo,
					archType);
			if (list.size() == 0) {
				return new ReturnInfo(null, "当前档案案卷号没有相同，可以使用");
			} else {
				return new ReturnInfo("当前档案案卷号有相同，不能使用", null);
			}
		} else {
			return new ReturnInfo("编号为空", null);
		}
	}

	// ----------------------------------------------------------------------

	// // 公文档案检索
	// @RequestMapping("/querylist")
	// public ModelAndView list() {
	// ModelAndView mav = new ModelAndView("/doc/archives/file/querylist");
	// return mav;
	// }

	// 获取公文档案后数据
	@RequestMapping("/listArchived.json")
	@ResponseBody
	// 对页面Pag的操作
	public Page archivedlistjson(Integer page, Integer rows, HttpServletRequest request, String sort, String order) {
		Page p = new Page();
		try {
			SearchBuilder<DocArchives, Long> searchBuilder = new SearchBuilder<DocArchives, Long>(conversionService);
			searchBuilder.clearSearchBuilder().findSearchParam(request);
			/**
			 * // 按部门查询 SysUser user = (SysUser)
			 * UserTool.getInstance().getCurrentUser(); // 设置条件：部门收文、发文只由部门归档 if
			 * (!user.getDepartment().getUnitId().toString().equals("5")) { //
			 * 部门：按部门查文件 searchBuilder.getFilterBuilder().in("sourceType", new
			 * Object[] { DocSource.处室内部收文, DocSource.处室外单位来文, DocSource.处室发文
			 * }); searchBuilder.getFilterBuilder().equal("createrDept.id",
			 * user.getDepartment().getId()); } else { // 政府办：按单位查文件
			 * searchBuilder.getFilterBuilder().in( "sourceType", new Object[] {
			 * DocSource.上级转来信, DocSource.代拟发文, DocSource.外单位来文, DocSource.审批表,
			 * DocSource.区局内部收文, DocSource.区局发文, DocSource.经费请示, DocSource.群众来信
			 * }); }
			 */
			// searchBuilder.getFilterBuilder().equal("status",
			// FileStatusFlag.归档);

			if (StringUtils.isNotBlank(sort)) {
				sort = sort.replace("Show", "");
				if ("archNo".equals(sort)) {
					sort = "archNo";
				} else if ("archName".equals(sort)) {
					sort = "archName";
				}
				searchBuilder.appendSort("asc".equals(order) ? Direction.ASC : Direction.DESC, sort);
			} else {
				searchBuilder.appendSort(Direction.DESC, "createDate");
				searchBuilder.appendSort(Direction.DESC, "orderNum");
			}
			searchBuilder.getFilterBuilder().notEqual("archStatus", ArchStatus.销毁);
			SearchListHelper<DocArchives> listhelper = new SearchListHelper<DocArchives>();
			listhelper.execute(searchBuilder, this.docArchivesRepository, page, rows);

			p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), DocArchivesShowBean.class));
			p.setTotal(listhelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "公文档案检索/归档后管理-列表查询：失败！", LogStatus.失败, request);
			e.printStackTrace();
		}

		return p;
	}

	// 待归档公文列表
	@RequestMapping("/list")
	public ModelAndView archivinglist() {
		ModelAndView mav = new ModelAndView("/doc/archives/file/list");
		return mav;
	}

	// 加载待归档公文数据
	@RequestMapping("/list.json")
	@ResponseBody
	// 对页面Pag的操作
	public Page archivinglistjson(Integer page, Integer rows, HttpServletRequest request, String sort, String order) {
		Page p = new Page();
		try {
			SearchBuilder<OaDocFile, Long> searchBuilder = new SearchBuilder<OaDocFile, Long>(conversionService);
			searchBuilder.clearSearchBuilder().findSearchParam(request);
			// 按部门查询
			SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
			// 设置条件：部门收文、发文只由部门归档
			// if (!user.getDepartment().getUnitId().toString().equals("1")) {
			// 部门：按部门查文件
			// searchBuilder.getFilterBuilder().in("sourceType",
			// new Object[] { DocSource.处室内部收文, DocSource.处室外单位来文,
			// DocSource.处室发文 });
			// searchBuilder.getFilterBuilder().equal("createrDept.id",
			// user.getDepartment().getId());
			// } else {
			// 政府办：按单位查文件
			// searchBuilder.getFilterBuilder().in("sourceType",
			// new Object[] { DocSource.上级转来信, DocSource.代拟发文, DocSource.外单位来文,
			// DocSource.审批表,
			// DocSource.区局内部收文, DocSource.区局发文, DocSource.经费请示, DocSource.群众来信
			// });

			// }

			searchBuilder.getFilterBuilder().equal("status", FileStatusFlag.待归档);

			if (StringUtils.isNotBlank(sort)) {
				sort = sort.replace("Show", "");
				searchBuilder.appendSort("asc".equals(order) ? Direction.ASC : Direction.DESC, sort);
			} else {
				searchBuilder.appendSort(Direction.DESC, "createDate");
				searchBuilder.appendSort(Direction.DESC, "sncode");
			}

			SearchListHelper<OaDocFile> listhelper = new SearchListHelper<OaDocFile>();
			listhelper.execute(searchBuilder, this.docFileRepository, page, rows);

			p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), OaDocFileShowBean.class));
			p.setTotal(listhelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "待归档公文-列表查询：失败！", LogStatus.失败, request);
			e.printStackTrace();
		}

		return p;
	}

	// 公文归档后管理
	@RequestMapping("/archivedlist")
	public ModelAndView archivedlist() {
		ModelAndView mav = new ModelAndView("/doc/archives/file/archivedlist");
		List<DocArchivesBasic> fileCabinetList = this.docArchivesBasicRepository.findByBasicTypeAndDepId(BasicType.档案柜,
				Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));// 获取档案柜list
		List<DocArchivesBasic> dossierTypeList = this.docArchivesBasicRepository.findByBasicTypeAndDepId(BasicType.案卷类型,
				Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));// 获取案卷类型list
		List<DocArchivesBasic> mediumTypeList = this.docArchivesBasicRepository.findByBasicTypeAndDepId(BasicType.介质类型,
				Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));// 获取介质类型list
		mav.addObject("fileCabinetList", fileCabinetList);
		mav.addObject("dossierTypeList", dossierTypeList);
		mav.addObject("mediumTypeList", mediumTypeList);
		// mav.addObject("archType", ArchType.收文);
		return mav;
	}

	// 电子公文详情
	@RequestMapping("/detailFile")
	public ModelAndView detail(@RequestParam("id") String id, HttpServletRequest request) {
		ModelAndView mav = null;

		// 基本信息
		OaDocFile obj = this.docFileService.findOne(id);
		if (obj == null) {
			mav = new ModelAndView("/doc/docevent/message");
			mav.addObject("hints", "公文信息读取失败，该公文可能已被删除，请刷新或重新进入列表页面。");
			return mav;
		}
		if (WfBusinessType.收文.equals(obj.getFileType())) {
			mav = new ModelAndView("/doc/archives/file/receivefile_detail");
		} else if (WfBusinessType.发文.equals(obj.getFileType())) {
			mav = new ModelAndView("/doc/archives/file/sendfile_detail");
		} else if (WfBusinessType.便函.equals(obj.getFileType())) {
			mav = new ModelAndView("/doc/archives/file/simplefile_detail");
		} else if (WfBusinessType.内部请示.equals(obj.getFileType())){
			mav = new ModelAndView("/doc/archives/file/interequest_detail");
		}

		Map<String,Map<String,String>> attachMap = Maps.newLinkedHashMap();
        // 查找正文附件
        String docname = null;
        String docpath = null;
        Map<String,String> docAttach= Maps.newHashMap();
        if (StringUtils.isNotBlank(obj.getDocId())) {
            SysAttach docFileAttach = attachService.findOne(obj.getDocId());
            if (docFileAttach != null) {
                // docname = docFileAttach.getTrueName();
                docname = docFileAttach.getSaveName();
                docpath = this.rootPath + "/" + docFileAttach.getSavePath();

                docAttach.put("id",obj.getDocId());
                docAttach.put("name",docFileAttach.getTrueName());
                docAttach.put("__serverPath",docpath+docname);
                docAttach.put("__fileType","mainFile");
                attachMap.put(obj.getDocId(),docAttach);
            }
        }
        if (StringUtils.isBlank(docname) && StringUtils.isBlank(docpath)) {
            // 附件不存在，或者被删除，创建新的
            docname = AttachUtils.getDocUniqueFileName("MainFile_", "doc");
            docpath = this.rootPath + "/" + this.appid + "/" + Tools.DATE_FORMAT.format(new Date());
            File file = new File(docpath);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        // 读取附件
        Map<String, List<SysAttach>> sysAttachMap = attachService.getAttachMap(id, new String[]{OaDocFile.DOC_ATTACH_OWNER_DOC,OaDocFile.DOC_ATTACH_OWNER_MAIN_PDF});
        mav.addObject("attachMap", sysAttachMap);

        if(sysAttachMap!=null) {
            for (SysAttach attach : sysAttachMap.get(id)) {
                Map<String,String> attachInfo= Maps.newHashMap();
                attachInfo.put("id",attach.getId());
                attachInfo.put("name",attach.getTrueName());
                attachInfo.put("__fileType",attach.getOwnerType());
                attachInfo.put("__serverPath",this.rootPath + "/" +attach.getSavePath()+attach.getSaveName());
                attachMap.put(attach.getId(),attachInfo);
            }
        }


        try {
            //附件数据的json格式
            mav.addObject("attachJson", new ObjectMapper().writeValueAsString(attachMap));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

		// 设置来文字号
		if (StringUtils.isNotBlank(obj.getDocCode())) {
			String docCode = obj.getDocCode().trim();
			int i = docCode.indexOf("[");
			int j = docCode.indexOf("]");
			int k = docCode.indexOf("号");
			try {
				mav.addObject("docCode1", docCode.substring(0, i));
				mav.addObject("docCode2", docCode.substring(i + 1, j));
				mav.addObject("docCode3", docCode.substring(j + 1, docCode.length() - 1));
			} catch (Exception e) {
				// 避免有非法文号，导致文档打不开
			}
		}

		// 读取审批意见
		mav.addObject("opinionList",
				auditOpinionService.findAuditOpinionsByFlowInstIdAndResult(obj.getFlowInstId(), WfApprResult.通过));

		// 读取相关部门函件数量，以显示会办信息
//		mav.addObject("depLettersCount", depLetterService.countLettersByFileId(id));
//		mav.addObject("depLetterReceivers", depLetterService.selectReceiversByFileId(id));

		// 读取核稿秘书环节（用于文笺显示）
		WfNodeInst msNodeInst = null;
		NodeInstQueryBean nodeInstQuery = new NodeInstQueryBean();
		nodeInstQuery.setFlowInstId(obj.getFlowInstId());
		nodeInstQuery.setNodeInstStatus(WfInstStatus.办结);
		if (DocSource.区局发文.equals(obj.getSourceType())) {
			nodeInstQuery.setCurNodeId("N202");
			List<WfNodeInst> msNodeInsts = nodeInstService.selectNodeInsts(nodeInstQuery);
			if (msNodeInsts != null && msNodeInsts.size() > 0) {
				msNodeInst = msNodeInsts.get(msNodeInsts.size() - 1);
			}
		} else if (DocSource.代拟发文.equals(obj.getSourceType())) {
			nodeInstQuery.setCurNodeId("N303");
			List<WfNodeInst> msNodeInsts = nodeInstService.selectNodeInsts(nodeInstQuery);
			if (msNodeInsts != null && msNodeInsts.size() > 0) {
				msNodeInst = msNodeInsts.get(msNodeInsts.size() - 1);
			}
		}
		if (msNodeInst != null) {
			mav.addObject("msNode", msNodeInst);
		}

		SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();

		mav.addObject("readCount", this.readMatterService.selectReadMatterCount(id));

		mav.addObject("obj", obj);
		mav.addObject("docpath", docpath);
		mav.addObject("docname", docname);
		mav.addObject("user", user);
		return mav;
	}

	// 公文编辑详情
	@RequestMapping("/detailDocModify")
	public ModelAndView detailDocModify(@RequestParam("id") String id, HttpServletRequest request) {
		ModelAndView mav = null;

		// 基本信息
		OaDocFile obj = this.docFileService.findOne(id);
		if (obj == null) {
			mav = new ModelAndView("/doc/docevent/message");
			mav.addObject("hints", "公文信息读取失败，该公文可能已被删除，请刷新或重新进入列表页面。");
			return mav;
		}
		if (WfBusinessType.收文.equals(obj.getFileType())) {
			mav = new ModelAndView("/doc/archives/file/receivefile_modify");
		} else if (WfBusinessType.发文.equals(obj.getFileType())) {
			mav = new ModelAndView("/doc/archives/file/sendfile_modify");
		} else if (WfBusinessType.便函.equals(obj.getFileType())) {
			mav = new ModelAndView("/doc/archives/file/simplefile_modify");
		} else if (WfBusinessType.内部请示.equals(obj.getFileType())){
			mav = new ModelAndView("/doc/archives/file/interequest_modify");
		}

		// 读取审批意见
		mav.addObject("opinionList",
				auditOpinionService.findAuditOpinionsByFlowInstIdAndResult(obj.getFlowInstId(), WfApprResult.通过));

		mav.addObject("obj", obj);
		return mav;
	}

	// 二维码页面
	@RequestMapping("/detailQRcode")
    public ModelAndView detailQRcode(String archNo,HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/doc/archives/QRcode/detail");
            try {
            	if (StringUtils.isNotEmpty(archNo)) {
            		String imgPath =request.getSession().getServletContext().getRealPath("/images/QRcode/qrcode.jpg");
            		//BufferedImage bufImg = this.docArchivesService.encode(archNo, BarcodeFormat.QR_CODE, 20, null);  
            		//BufferedImage bufImg = this.docArchivesService.QRCodeCommon(archNo, "JPEG", 7);
            		BufferedImage bufImg = this.docArchivesService.createQrCode(archNo, 300, "JPEG");
            		File imgFile = new File(imgPath);
            		//OutputStream imgFile = new FileOutputStream(imgPath);
            		// 生成二维码QRCode图片  
            		ImageIO.write(bufImg, "JPEG", imgFile);
            		mav.addObject("imgPath",imgFile);
            	}
			} catch (IOException e) {
				e.printStackTrace();
			}
        return mav;
    }
}
