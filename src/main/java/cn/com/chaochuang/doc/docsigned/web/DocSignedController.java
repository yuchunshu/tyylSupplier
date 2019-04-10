/*
 * FileName:    ReceiveFileController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.docsigned.web;

import javax.servlet.http.HttpServletRequest;

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
import cn.com.chaochuang.common.upload.support.PluploadController;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.doc.docsigned.bean.DocSignedShowBean;
import cn.com.chaochuang.doc.docsigned.domain.DocSigned;
import cn.com.chaochuang.doc.docsigned.service.DocSignedService;
import cn.com.chaochuang.common.attach.service.SysAttachService;

/**
 * @author HeYunTao
 *
 */
@Controller
@RequestMapping("doc/docsigned")
public class DocSignedController extends PluploadController {

    @Autowired
    protected ConversionService conversionService;

    @Autowired
    private DocSignedService    docSignedService;

    @Autowired
    private SysAttachService    attachService;

    @Autowired
    private LogService          logService;

    @RequestMapping("/signed")
    public ModelAndView signedList() {
        ModelAndView mav = new ModelAndView("/doc/receivefile/signedlist");
        return mav;
    }

    @RequestMapping("/listSigned.json")
    @ResponseBody
    // 对页面Pag的操作
    public Page signedlistjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<DocSigned, Long> searchBuilder = new SearchBuilder<DocSigned, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        // 按单位部门查询
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        searchBuilder.getFilterBuilder().equal("signerUnitId", user.getDepartment().getUnitId());
        searchBuilder.appendSort(Direction.DESC, "signDate");

        SearchListHelper<DocSigned> listhelper = new SearchListHelper<DocSigned>();
        listhelper.execute(searchBuilder, docSignedService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), DocSignedShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    @RequestMapping("/detail")
    public ModelAndView detail(Long id) {
        ModelAndView mav = new ModelAndView("/doc/receivefile/signeddetail");
        DocSigned docSigned = null;
        if (id != null) {
            docSigned = this.docSignedService.findOne(id);

        }
        mav.addObject("docSigned", docSigned);
        mav.addObject("attach", this.attachService.findByOwnerIdAndOwnerType(String.valueOf(id),
                        DocSigned.class.getSimpleName()));
        return mav;
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo del(String ids, HttpServletRequest request) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        try {
            if (ids != null) {
                String[] idArr = ids.split(",");
                for (String signid : idArr) {
                    this.docSignedService.getRepository().delete(Long.valueOf(signid));
                    logService.saveLog(SjType.普通操作, "删除来文签收信息，删除id为'" + signid + "'的记录",LogStatus.成功, request);
                }
            }

            return new ReturnInfo("1", null, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

}
