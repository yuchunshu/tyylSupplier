package cn.com.chaochuang.mobile.web;

import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.beancopy.BeanCopyUtil;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.repository.SysUserRepository;
import cn.com.chaochuang.mobile.bean.AppNoticeInfo;
import cn.com.chaochuang.mobile.bean.AppNoticeList;
import cn.com.chaochuang.mobile.bean.AppResponseInfo;
import cn.com.chaochuang.mobile.util.AesTool;
import cn.com.chaochuang.oa.notice.domain.OaNotice;
import cn.com.chaochuang.oa.notice.repository.OaNoticeRepository;
import cn.com.chaochuang.oa.notice.service.OaNoticeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 *
 * @author hzr 2017.8.29
 *
 */
@Controller
@RequestMapping(value = "/mobile/notice")
public class MobileNoticeController extends MobileController {

    @Autowired
    private OaNoticeService noticeService;

    @Autowired
    private SysAttachService attachService;

    @Autowired
    private OaNoticeRepository oaNoticeRepository;

    /**
     * 公告列表
     */
    @RequestMapping("/list.mo")
    @ResponseBody
    public AppResponseInfo noticelist(HttpServletRequest request, Boolean aesFlag_) {
        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null,aesFlag_);
        }
        String currentDeptId = user.getDepartment().getId().toString();

        String page = request.getParameter("page");
        String rows = request.getParameter("rows");

        //查询条件（可选）
        String title = request.getParameter("title");
        String deptId = request.getParameter("deptId");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");

        Date dt1 = strToDatetime(fromDate, "00:00");
        Date dt2 = strToDatetime(toDate, "23:59");

        Page p = noticeService.selectAllForDeptShow(title, deptId, dt1, dt2, currentDeptId, new Integer(page), new Integer(rows));

        return AesTool.responseData(p,aesFlag_);

    }


    /**
     * 公告列表(包括本人创建的和接收到的)
     */
    @RequestMapping("/personallist.mo")
    @ResponseBody
    public AppResponseInfo personalNoticeList(Integer page, Integer rows, String title, HttpServletRequest request, Boolean aesFlag_) {
        SysUser user = this.loadCurrentUser(request);
        if (user == null||user.getDepartment()==null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }
        Long currentDeptId = user.getDepartment().getId();
        if(title!=null){
            title = "%"+title+"%";
        }
        //PageRequest 页数从0开始
        org.springframework.data.domain.Page<OaNotice> dataPage= oaNoticeRepository.findPersonalNoticeList(user.getId(),currentDeptId,title,new PageRequest(page-1,rows));

        Page p = new Page();
        p.setTotal(dataPage.getTotalElements());
        p.setRows(BeanCopyBuilder.buildList(dataPage.getContent(),AppNoticeList.class));

        return AesTool.responseData(p,aesFlag_);
    }

    /**
     * 公告详情
     */
    @RequestMapping("/detail.mo")
    @ResponseBody
    public AppResponseInfo detail(HttpServletRequest request, Boolean aesFlag_) {
        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null,aesFlag_);
        }

        String entityId = request.getParameter("id");
        if (StringUtils.isBlank(entityId)) {
            return AesTool.responseData("0", "ID为空，参数错误！", null,aesFlag_);
        }

        OaNotice obj = noticeService.findOne(entityId);
        if (obj == null) {
        	return AesTool.responseData("0", "找不到相应的公告！", null,aesFlag_);
        }
        AppNoticeInfo bean = BeanCopyUtil.copyBean(obj, AppNoticeInfo.class);

        // 加载附件
        bean.setAttachList(attachService.findByOwnerId(obj.getId()));

        return AesTool.responseData(bean,aesFlag_);

    }

}
