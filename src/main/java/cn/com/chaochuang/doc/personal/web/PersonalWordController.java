/*
 * FileName:    NoticeController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.doc.personal.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
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
import cn.com.chaochuang.doc.personal.bean.PersonalWordBean;
import cn.com.chaochuang.doc.personal.domain.PersonalWord;
import cn.com.chaochuang.doc.personal.service.PersonalWordService;
import cn.com.chaochuang.oa.datachange.reference.DataChangeTable;
import cn.com.chaochuang.oa.datachange.reference.OperationType;
import cn.com.chaochuang.oa.datachange.service.DataChangeService;

/**
 * @author huangwq
 *
 */
@Controller
@RequestMapping("oa/personal")
public class PersonalWordController {

    @Autowired
    private PersonalWordService personalWordService;

    @Autowired
    protected ConversionService conversionService;

    @Autowired
    private LogService          logService;

    @Autowired
    private DataChangeService   dataChangeService;

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/doc/personal/list");
        return mav;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    // 对页面Pag的操作
    public Page listjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<PersonalWord, Long> searchBuilder = new SearchBuilder<PersonalWord, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.getFilterBuilder().equal("userId", UserTool.getInstance().getCurrentUserId());
        SearchListHelper<PersonalWord> listhelper = new SearchListHelper<PersonalWord>();
        listhelper.execute(searchBuilder, personalWordService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), PersonalWordBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    @RequestMapping("/new")
    public ModelAndView newPage() {
        ModelAndView mav = new ModelAndView("/doc/personal/edit");
        return mav;
    }

    @RequestMapping("/edit")
    public ModelAndView editPage(Long id) {
        ModelAndView mav = new ModelAndView("/doc/personal/edit");
        PersonalWord word = this.personalWordService.findOne(id);
        mav.addObject("obj", word);
        return mav;
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo save(PersonalWordBean bean, HttpServletRequest request, HttpServletResponse response) {

        try {

            SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
            if (user != null) {
                if (bean.getId() == null) {
                    logService.saveLog(SjType.普通操作, "新增词条信息",LogStatus.成功, request);
                } else {
                    // 保存数据变动
                    dataChangeService.saveDataChange("id=" + bean.getId(), DataChangeTable.词条, OperationType.修改);

                    logService.saveLog(SjType.普通操作, "修改词条信息,修改id为'" + bean.getId() + "'的记录",LogStatus.成功, request);
                }
            }
            Long noticeId = this.personalWordService.savePersonalWord(bean);

            return new ReturnInfo(noticeId.toString(), null, "发布成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo del(String ids, HttpServletRequest request) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        try {
            if (ids != null) {
                String[] idArr = ids.split(",");
                for (String wordId : idArr) {
                    this.personalWordService.getRepository().delete(Long.valueOf(wordId));
                    // 保存数据变动
                    dataChangeService.saveDataChange("id=" + wordId, DataChangeTable.词条, OperationType.删除);
                    logService.saveLog(SjType.普通操作, "删除词条信息,删除id为'" + wordId + "'的记录",LogStatus.成功, request);
                }
            }

            return new ReturnInfo("1", null, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上!", null);
        }
    }

    @RequestMapping("/selectword")
    public ModelAndView selectwor(String deputyIds) {
        ModelAndView mav = new ModelAndView("/selectdialog/selectpersonword");
        mav.addObject("selectDialogTitle", "个人词条");
        // 取数据的url
        mav.addObject("dataUrl", "/oa/personal/list.json");
        // mav.addObject("single", true);

        // mav.addObject("groups", deputyGroupService.findAll());
        return mav;
    }

    @RequestMapping("/getWordMenu.json")
    @ResponseBody
    public List wordMenuList(HttpServletRequest request) {
        SearchBuilder<PersonalWord, Long> searchBuilder = new SearchBuilder<PersonalWord, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.getFilterBuilder().equal("userId", UserTool.getInstance().getCurrentUserId());
        SearchListHelper<PersonalWord> listhelper = new SearchListHelper<PersonalWord>();
        listhelper.execute(searchBuilder, personalWordService.getRepository(), 1, 1000);
        return BeanCopyBuilder.buildList(listhelper.getList(), PersonalWordBean.class);
    }

    /**
     * 个人词条选择窗口
     */
    @RequestMapping("/getWordSelect")
    public ModelAndView wordSelectPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/selectdialog/selectPersonalWord");
        mav.addObject("selectDialogTitle", "个人词条");
        return mav;
    }
}
