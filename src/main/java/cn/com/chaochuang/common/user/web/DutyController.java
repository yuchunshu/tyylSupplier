package cn.com.chaochuang.common.user.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.EasyUIPage;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.user.domain.SysDuty;
import cn.com.chaochuang.common.user.service.SysDutyService;
import cn.com.chaochuang.common.util.SearchListHelper;

/**
 * 
 * @author LJX
 *
 */
@Controller
@RequestMapping("system/duty")
public class DutyController {
	
	@Autowired
    protected ConversionService  conversionService;
	
	@Autowired
	private SysDutyService dutyService;
	
	@Autowired
    private LogService           logService;
	
	@RequestMapping("/list")
    public ModelAndView list(Long depId) {
        ModelAndView mav = new ModelAndView("/system/duty/list");
        return mav;
    }
	
	@RequestMapping("/list.json")
    @ResponseBody
    public EasyUIPage listjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<SysDuty, Long> searchBuilder = new SearchBuilder<SysDuty, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.appendSort(Direction.DESC, "dutyDesc");
        SearchListHelper<SysDuty> listhelper = new SearchListHelper<SysDuty>();
        listhelper.execute(searchBuilder, dutyService.getRepository(), page, rows);
        EasyUIPage p = new EasyUIPage();
        p.setRows(listhelper.getList());
        p.setTotal(listhelper.getCount());
        return p;
    }
	
	@RequestMapping("/edit")
    public ModelAndView edit(Long id) {
        ModelAndView mav = new ModelAndView("/system/duty/edit");
        if(id != null){
        	SysDuty duty = dutyService.findOne(id);
        	mav.addObject("duty", duty);
        }
        return mav;
    }
	
	@RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo update(SysDuty duty, HttpServletRequest request, HttpServletResponse response) {
        try{
        	if(duty.getId() != null){
        		this.logService.saveLog(SjType.普通操作, "修改职务："+duty.getDutyName(),LogStatus.成功, request);
        	}else{
        		this.logService.saveLog(SjType.普通操作, "新增职务："+duty.getDutyName(),LogStatus.成功, request);
        	}
        	return new ReturnInfo(this.dutyService.getRepository().save(duty).getId().toString(), null ,"保存成功。");
        }catch(Exception e){
        	e.printStackTrace();
        	return new ReturnInfo(null, "保存失败！",null);
        }
    }
	
	@RequestMapping("/delete.json")
    @ResponseBody
    public Boolean del(Long[] ids, HttpServletRequest request) {
		if(ids != null && ids.length>0){
			for(Long id : ids){
				SysDuty d = this.dutyService.findOne(id);
				this.dutyService.deleteDuty(id);
				this.logService.saveLog(SjType.普通操作, "删除职务："+d.getDutyName(),LogStatus.成功, request);
			}
			return true;
		}
        return false;
    }
}
