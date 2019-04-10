/*
 * FileName:    SmsSendController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年12月05日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.sms.web;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnBean;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.select.bean.CommonSelectedBean;
import cn.com.chaochuang.common.select.bean.UserSelectShowBean;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.common.util.UserHelper;
import cn.com.chaochuang.oa.address.domain.OaAddressPersonal;
import cn.com.chaochuang.oa.address.repository.OaAddressPersonalRepository;
import cn.com.chaochuang.sms.bean.SmsEditInfo;
import cn.com.chaochuang.sms.bean.SmsListData;
import cn.com.chaochuang.sms.bean.SmsPhraseInfo;
import cn.com.chaochuang.sms.domain.SmsPhrase;
import cn.com.chaochuang.sms.domain.SmsPhraseType;
import cn.com.chaochuang.sms.domain.SmsSending;
import cn.com.chaochuang.sms.domain.SmsSent;
import cn.com.chaochuang.sms.repository.SmsPhraseRepository;
import cn.com.chaochuang.sms.repository.SmsPhraseTypeRepository;
import cn.com.chaochuang.sms.repository.SmsSendingRepository;
import cn.com.chaochuang.sms.repository.SmsSentRepository;
import cn.com.chaochuang.sms.service.SmsSendService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Shicx
 * <p>
 * 短信保存与查询
 */
@Controller
@RequestMapping("sms")
public class SmsSendController {

    @Autowired
    protected ConversionService conversionService;
    @Autowired
    private SmsSendService smsSendService;
    @Autowired
    private OaAddressPersonalRepository addressPersonalRepository;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SmsSentRepository smsSentRepository;
    @Autowired
    private SmsSendingRepository smsSendingRepository;
    @Autowired
    private SmsPhraseRepository smsPhraseRepository;
    @Autowired
    private SmsPhraseTypeRepository smsPhraseTypeRepository;

    /**
     * 新增短信
     *
     * @return
     */
    @RequestMapping("/newsms")
    public ModelAndView toNewSmsPage() {
        ModelAndView mav = new ModelAndView("/sms/new-sms");

        return mav;
    }

    /**
     * 发送短信
     *
     * @return
     */
    @RequestMapping("/savesms.json")
    @ResponseBody
    public ReturnBean saveSms(SmsEditInfo editInfo) {

        try {
            SysUser currentUser = UserHelper.getCurrentUser();
            editInfo.setCreator(currentUser);
            //调用webservice接口保存
            //return smsSendService.saveSendSmsByWebservice(editInfo);
            //todo 本地测试使用
            return smsSendService.saveSmsLocal(editInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ReturnBean("发送失败", null, false);
    }

    /**
     * 已发短信查询
     *
     * @return
     */
    @RequestMapping("/listsent")
    public ModelAndView toSentList() {
        ModelAndView mav = new ModelAndView("/sms/list-sent");
        return mav;
    }

    /**
     * 已发短信数据
     *
     * @param page
     * @param rows
     * @param request
     * @return
     */
    @RequestMapping("/listsent.json")
    @ResponseBody
    public Page findSentListData(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<SmsSent, Long> searchBuilder = new SearchBuilder<SmsSent, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        searchBuilder.getFilterBuilder().equal("sendManId", user.getId());
        searchBuilder.appendSort(Sort.Direction.DESC, "sendTime");
        searchBuilder.appendSort(Sort.Direction.DESC, "sendId");
        SearchListHelper<SmsSent> listhelper = new SearchListHelper<SmsSent>();
        listhelper.execute(searchBuilder, smsSentRepository, page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), SmsListData.class));
        p.setTotal(listhelper.getCount());

        return p;
    }

    /**
     * 在发短信查询
     *
     * @return
     */
    @RequestMapping("/listsending")
    public ModelAndView toSendingList() {
        ModelAndView mav = new ModelAndView("/sms/list-sending");
        return mav;
    }

    /**
     * 在发短信数据
     *
     * @param page
     * @param rows
     * @param request
     * @return
     */
    @RequestMapping("/listsending.json")
    @ResponseBody
    public Page findSendingListData(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<SmsSending, Long> searchBuilder = new SearchBuilder<SmsSending, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        searchBuilder.getFilterBuilder().equal("sendManId", user.getId());
        searchBuilder.appendSort(Sort.Direction.DESC, "sendTime");
        searchBuilder.appendSort(Sort.Direction.DESC, "sendId");
        SearchListHelper<SmsSending> listhelper = new SearchListHelper<SmsSending>();
        listhelper.execute(searchBuilder, smsSendingRepository, page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), SmsListData.class));
        p.setTotal(listhelper.getCount());

        return p;
    }

    /**
     * 常用短语列表
     *
     * @return
     */
    @RequestMapping("/listphrase")
    public ModelAndView toPhraseList() {
        ModelAndView mav = new ModelAndView("/sms/list-phrase");
        return mav;
    }

    /**
     * 常用短语列表数据
     *
     * @param page
     * @param rows
     * @param request
     * @return
     */
    @RequestMapping("/listphrase.json")
    @ResponseBody
    public Page findPhraseListData(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<SmsPhrase, Long> searchBuilder = new SearchBuilder<SmsPhrase, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        searchBuilder.getFilterBuilder().equal("userId", user.getId());
        searchBuilder.appendSort(Sort.Direction.ASC, "sortFlag");
        searchBuilder.appendSort(Sort.Direction.DESC, "smsPhraseId");
        SearchListHelper<SmsPhrase> listhelper = new SearchListHelper<SmsPhrase>();
        listhelper.execute(searchBuilder, smsPhraseRepository, page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), SmsPhraseInfo.class));
        p.setTotal(listhelper.getCount());

        return p;
    }


    /**
     * 常用短语类型列表
     *
     * @return
     */
    @RequestMapping("/listphrasetype")
    public ModelAndView toPhraseTypeList() {
        ModelAndView mav = new ModelAndView("/sms/list-phrase-type");
        return mav;
    }

    /**
     * 常用短语类型列表数据
     *
     * @param page
     * @param rows
     * @param request
     * @return
     */
    @RequestMapping("/listphrasetype.json")
    @ResponseBody
    public Page findPhraseTypeListData(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<SmsPhraseType, Long> searchBuilder = new SearchBuilder<SmsPhraseType, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        searchBuilder.getFilterBuilder().equal("userId", user.getId());
        searchBuilder.appendSort(Sort.Direction.DESC, "smsPhraseTypeId");
        SearchListHelper<SmsPhraseType> listhelper = new SearchListHelper<SmsPhraseType>();
        listhelper.execute(searchBuilder, smsPhraseTypeRepository, page, rows);
        Page p = new Page();
        p.setRows(listhelper.getList());
        p.setTotal(listhelper.getCount());

        return p;
    }

    /**
     * 短语类型数据
     *
     * @return
     */
    @RequestMapping("/listtype.json")
    @ResponseBody
    public List<Map<String, String>> findPhraseTypeList() {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        List<SmsPhraseType> phraseTypeList = smsPhraseTypeRepository.findByUserId(user.getId());
        List<Map<String, String>> dataList = Lists.newArrayList();
        if (phraseTypeList != null) {
            for (SmsPhraseType phraseType : phraseTypeList) {
                Map<String, String> dataMap = Maps.newLinkedHashMap();
                dataMap.put("value", phraseType.getSmsPhraseTypeId().toString());
                dataMap.put("text", phraseType.getPhraseTypeName());
                dataList.add(dataMap);
            }
        }
        return dataList;
    }

    /**
     * 新增/编辑 短语
     * @param phraseInfo
     * @return
     */
    @RequestMapping("/savephrase.json")
    @ResponseBody
    public ReturnBean savePhrase(SmsPhraseInfo phraseInfo) {
        try {
            SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
            return smsSendService.savePhrase(phraseInfo, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ReturnBean("保存失败", null, false);
    }

    /**
     * 新增/编辑 短语类型
     * @param phraseTypeInfo
     * @return
     */
    @RequestMapping("/savephrasetype.json")
    @ResponseBody
    public ReturnBean savePhraseType(SmsPhraseType phraseTypeInfo) {
        try {
            SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
            return smsSendService.savePhraseType(phraseTypeInfo, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ReturnBean("保存失败", null, false);
    }

    /**
     * 删除短语
     * @param phraseId
     * @return
     */
    @RequestMapping("/deletephrase.json")
    @ResponseBody
    public ReturnBean deletePhrase(Long phraseId){
        try {
            return smsSendService.deletePhrase(phraseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ReturnBean("删除失败", null, false);
    }

    /**
     * 删除短语
     * @param typeId
     * @return
     */
    @RequestMapping("/deletephrasetype.json")
    @ResponseBody
    public ReturnBean deletePhraseType(Long typeId){
        try {
            return smsSendService.deletePhraseType(typeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ReturnBean("删除失败", null, false);
    }

    /**
     * 通讯录选择弹出窗
     *
     * @param dataIds
     * @return
     */
    @RequestMapping("/select/contactdialog")
    public ModelAndView selectContactDialog(String dataIds) {
        ModelAndView mav = new ModelAndView("/sms/select/selectdialogtemplate");
        // 取数据的url
        mav.addObject("url", "/oa/address/personal/list.json");
        if (StringUtils.isNotBlank(dataIds)) {
            mav.addObject("selectedDataUrl", "/sms/select/querycontact.json?dataIds=" + dataIds);
        }
        // 是否单选
        mav.addObject("single", false);
        // 显示名称
        mav.addObject("dataText", "pname");
        // 标题
        mav.addObject("selectDialogTitle", "选择接收人");
        // 表头页面
        mav.addObject("tablehead", "/sms/select/tablehead-contact.vm");
        // 查询条件页面
        mav.addObject("query", "/sms/select/query.vm");
        // 窗口宽度
        mav.addObject("dialogWidth", "800");
        // 窗口高度
        mav.addObject("dialogHeight", "500");
        return mav;
    }

    /**
     * 已选的通讯录人员
     *
     * @param dataIds
     * @return
     */
    @RequestMapping("/select/querycontact.json")
    @ResponseBody
    public Page findSelectedContactData(String dataIds) {
        if (StringUtils.isEmpty(dataIds)) {
            return new Page();
        }
        String[] dataIdArr = dataIds.split(",");
        List<Long> idList = Lists.newArrayList();
        for (int i = 0; i < dataIdArr.length; i++) {
            idList.add(Long.valueOf(dataIdArr[i]));
        }
        List<OaAddressPersonal> personalList = this.addressPersonalRepository.findAll(idList);
        Page p = new Page();
        List<Map<String,String>> beanList = Lists.newArrayList();
        if (personalList != null) {
            for (OaAddressPersonal addressPersonal : personalList) {
                Map<String,String> dataMap = Maps.newLinkedHashMap();
                dataMap.put("id",addressPersonal.getId().toString());
                dataMap.put("dataText",addressPersonal.getPname());
                dataMap.put("mobile",addressPersonal.getMobile());
                beanList.add(dataMap);
            }
        }
        p.setRows(beanList);
        p.setTotal((long) personalList.size());
        return p;
    }

    /**
     * 选择内部人员
     *
     * @param userIds
     * @return
     */
    @RequestMapping("/select/userdialog")
    public ModelAndView selectUserDialog(String userIds) {
        ModelAndView mav = new ModelAndView("/sms/select/selectdialogtemplate");
        // 取数据的url
        mav.addObject("url", "/oa/mail/select/getIncepters.json");
        if (StringUtils.isNotBlank(userIds)) {
            mav.addObject("selectedDataUrl", "/sms/select/queryusers.json?userIds=" + userIds);
        }
        // 是否单选
        mav.addObject("single", false);
        // 显示名称
        mav.addObject("dataText", "userName");
        // 标题
        mav.addObject("selectDialogTitle", "选择接收人");
        // 表头页面
        mav.addObject("tablehead", "/sms/select/tablehead-user.vm");
        // 查询条件页面
        mav.addObject("query", "/sms/select/query.vm");
        // 窗口宽度
        mav.addObject("dialogWidth", "850");
        // 窗口高度
        mav.addObject("dialogHeight", "550");
        // layout左边
        mav.addObject("westLayout", "/oa/mail/select/westLayout.vm");
        // layout左边宽度
        mav.addObject("westWidth", "235");

        return mav;
    }

    /**
     * 已选的内部人员
     *
     * @param userIds
     * @return
     */
    @RequestMapping("/select/queryusers.json")
    @ResponseBody
    public Page findSelectedUsers(String userIds) {
        List<SysUser> uList = sysUserService.getUsersByIdsGroupByDep(userIds);
        Page p = new Page();
        List<Map<String,String>> beanList = Lists.newArrayList();
        if (uList != null) {
            for (SysUser user : uList) {
                Map<String,String> dataMap = Maps.newLinkedHashMap();
                dataMap.put("id",user.getId().toString());
                dataMap.put("dataText",user.getUserName());
                dataMap.put("userName",user.getUserName());
                dataMap.put("mobile",user.getMobile());
                beanList.add(dataMap);
            }
            p.setRows(beanList);
            p.setTotal((long) uList.size());
        }
        return p;
    }


    /**
     * 选择短语
     *
     * @return
     */
    @RequestMapping("/select/phrasedialog")
    public ModelAndView selectPhraseDialog() {
        ModelAndView mav = new ModelAndView("/sms/select/selectdialogtemplate");
        // 取数据的url
        mav.addObject("url", "/sms/listphrase.json");
        // 是否单选
        mav.addObject("single", true);
        // 显示名称
        mav.addObject("dataText", "phraseName");
        // 标题
        mav.addObject("selectDialogTitle", "选择常用短语");
        // 表头页面
        mav.addObject("tablehead", "/sms/select/tablehead-phrase.vm");
        // 查询条件页面
        mav.addObject("query", "/sms/select/query-phrase.vm");
        // 窗口宽度
        mav.addObject("dialogWidth", "650");
        // 窗口高度
        mav.addObject("dialogHeight", "550");
        return mav;
    }
}

