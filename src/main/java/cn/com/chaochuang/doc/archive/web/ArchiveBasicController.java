package cn.com.chaochuang.doc.archive.web;


import java.util.List;

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
import cn.com.chaochuang.doc.archive.bean.DocArchivesBasicEditBean;
import cn.com.chaochuang.doc.archive.bean.DocArchivesBasicShowBean;
import cn.com.chaochuang.doc.archive.domain.DocArchivesBasic;
import cn.com.chaochuang.doc.archive.reference.BasicType;
import cn.com.chaochuang.doc.archive.repository.DocArchivesBasicRepository;
import cn.com.chaochuang.doc.archive.service.DocArchivesBasicService;

/**
 * ！@author shiql 2017.11.22
 *
 */
@Controller
@RequestMapping("doc/archives/basic")
public class ArchiveBasicController {

    @Autowired
    private DocArchivesBasicService     docArchivesBasicService;
    
    @Autowired
    private ConversionService      		conversionService;

    @Autowired
    private LogService             		logService;
    
    @Autowired
    private DocArchivesBasicRepository	docArchivesBasicRepository;

    /** 列表*/
    @RequestMapping("/list")
    public ModelAndView maintain() {
        ModelAndView mav = new ModelAndView("/doc/archives/basic/maintain");
        return mav;
    }
    
    /** 新增*/
    @RequestMapping("/new")
    public ModelAndView newPage() {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        ModelAndView mav = new ModelAndView("/doc/archives/basic/edit");
        mav.addObject("currUser", user);
        return mav;
    }
    /** 修改*/
    @RequestMapping("/edit")
    public ModelAndView editPage(String id) {
        ModelAndView mav = new ModelAndView("/doc/archives/basic/edit");
        DocArchivesBasic basic = this.docArchivesBasicService.findOne(id);
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        mav.addObject("currUser", user);
        mav.addObject("basic", basic);
        return mav;
    }
    //详细页
    @RequestMapping("/detail")
    public ModelAndView detail(String id) {
        ModelAndView mav = new ModelAndView("/doc/archives/basic/detail");
        if (id != null) {
            DocArchivesBasic basic = this.docArchivesBasicService.findOne(id);
            mav.addObject("basic", basic);
        }
        return mav;
    }
    // 保存
    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo save(DocArchivesBasicEditBean bean, HttpServletRequest request, HttpServletResponse response) {
        try {
            DocArchivesBasic docArchivesBisc = this.docArchivesBasicService.saveDocArchives(bean);
            
            if (StringUtils.isNotBlank(bean.getId())) {
                logService.saveLog(SjType.普通操作, "修改档案卷，档案名为：" + bean.getBasicName() + ",id为：" +docArchivesBisc.getId() ,LogStatus.成功, request);
            } else {
                logService.saveLog(SjType.普通操作, "新增档案卷，档案名为：" + bean.getBasicName() + ",id为：" + docArchivesBisc.getId(),LogStatus.成功, request);
            }
            return new ReturnInfo(docArchivesBisc.getId(), null, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            if (StringUtils.isNotBlank(bean.getId())) {
                logService.saveLog(SjType.普通操作, "基础信息维护-修改档案卷《" + bean.getBasicName() + "》：失败！",LogStatus.失败, request);
            } else {
                logService.saveLog(SjType.普通操作, "基础信息维护-新增档案卷《" + bean.getBasicName() + "》：失败！",LogStatus.失败, request);
            }
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }
    
    // 删除
    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo del(String ids, HttpServletRequest request) {
        try {
            boolean b = this.docArchivesBasicService.deleteDocArchivesBasic(ids, request);
            if (b) {

                String[] idArr = ids.split(",");
                for (int i = 0; i < idArr.length; i++) {
                	 this.docArchivesBasicService.findOne(idArr[i]);
                }
                return new ReturnInfo("1", null, "删除成功!");
            } else {
                return new ReturnInfo(null, "服务器连接不上！", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    @RequestMapping("/listMaintain.json")
    @ResponseBody
    // 对页面Pag的操作
    public Page maintainjson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
    	Page p = new Page();
    	try {
    		SearchBuilder<DocArchivesBasic, String> searchBuilder = new SearchBuilder<DocArchivesBasic, String>(conversionService);
            searchBuilder.clearSearchBuilder().findSearchParam(request);
            // 按部门查询
            searchBuilder.getFilterBuilder().equal("depId", UserTool.getInstance().getCurrentUserDepartmentId());
            
            if(StringUtils.isNotBlank(sort)){
            	sort = sort.replace("Show", "");
            	
            	searchBuilder.appendSort("asc".equals(order)? Direction.ASC:Direction.DESC, sort);
            }
            else{
            	searchBuilder.appendSort(Direction.DESC, "createDate");
                searchBuilder.appendSort(Direction.ASC, "basicType");
            }
            
            SearchListHelper<DocArchivesBasic> listhelper = new SearchListHelper<DocArchivesBasic>();
            listhelper.execute(searchBuilder, docArchivesBasicService.getRepository(), page, rows);
            
            p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), DocArchivesBasicShowBean.class));
            p.setTotal(listhelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "基础信息维护-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
        
        return p;
    }
    
    // 检查编码
    @RequestMapping("/checkBasicCode.json")
    @ResponseBody
    public ReturnInfo checkBasicCode(String basicCode,String basicType) {
    	if(StringUtils.isNotEmpty(basicCode) && StringUtils.isNotEmpty(basicType)){
    		List<DocArchivesBasic> list = null;
    		Long depId = Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId());
    		if(BasicType.档案柜.getKey().equals(basicType)){
    			list = this.docArchivesBasicRepository.findByBasicCodeAndBasicTypeAndDepId(basicCode,BasicType.档案柜,depId);
        	}else if(BasicType.案卷类型.getKey().equals(basicType)){
        		list = this.docArchivesBasicRepository.findByBasicCodeAndBasicTypeAndDepId(basicCode,BasicType.案卷类型,depId);
        	}else{
        		list = this.docArchivesBasicRepository.findByBasicCodeAndBasicTypeAndDepId(basicCode,BasicType.介质类型,depId);
        	}
    		if(list.size()==0){
    			return new ReturnInfo(null, "当前编码没有相同，可以使用");
    		}else{
    			if(BasicType.档案柜.getKey().equals(basicType)){
    				return new ReturnInfo("当前柜号有相同，不能使用", null);
    			}else if(BasicType.案卷类型.getKey().equals(basicType)){
    				return new ReturnInfo("当前案卷类型简称有相同，不能使用", null);
    			}else{
    				return new ReturnInfo("当前编号有相同，不能使用", null);
    			}
    		}
    	}else{
    		return new ReturnInfo("编码为空", null);
    	}
    }
    
}
