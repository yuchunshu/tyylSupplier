/*
 * FileName:    MobileEmailController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年10月12日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.mobile.web;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.mobile.bean.AppEmailShowBean;
import cn.com.chaochuang.mobile.bean.AppResponseInfo;
import cn.com.chaochuang.mobile.util.AesTool;
import cn.com.chaochuang.mobile.util.MobileTool;
import cn.com.chaochuang.oa.mail.bean.ArchiveShowBean;
import cn.com.chaochuang.oa.mail.bean.MailEditBean;
import cn.com.chaochuang.oa.mail.domain.EmDustbin;
import cn.com.chaochuang.oa.mail.domain.EmIncepter;
import cn.com.chaochuang.oa.mail.domain.EmMain;
import cn.com.chaochuang.oa.mail.domain.EmTempStorage;
import cn.com.chaochuang.oa.mail.reference.IncepterStatus;
import cn.com.chaochuang.oa.mail.reference.MailStatus;
import cn.com.chaochuang.oa.mail.repository.EmIncepterRepository;
import cn.com.chaochuang.oa.mail.service.EmDustbinService;
import cn.com.chaochuang.oa.mail.service.EmIncepterService;
import cn.com.chaochuang.oa.mail.service.EmMainService;
import cn.com.chaochuang.oa.mail.service.EmTempStorageService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Shicx
 */
@Controller
@RequestMapping("mobile/email")
public class MobileEmailController extends MobileController{

    @Autowired
    protected ConversionService 	conversionService;
    @Autowired
    private EmIncepterService 		incepterService;
    @Autowired
    private EmIncepterRepository 	emIncepterRepository;
    @Autowired
    private EmMainService 			emMainService;
    @Autowired
    private EmDustbinService 		dustbinService;
    @Autowired
    private SysAttachService 		attachService;
    @Autowired
    private EmTempStorageService 	tempStorageService;

    /**
     * 查询个人收件箱记录
     * @param request
     * @param aesFlag_
     * @return
     */
    @RequestMapping(value = {"/inboxlist.mo"})
    @ResponseBody
    public AppResponseInfo findInboxList(Integer page, Integer rows,HttpServletRequest request, Boolean aesFlag_){
        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }
        SearchBuilder<EmIncepter, String> searchBuilder = new SearchBuilder<EmIncepter, String>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.getFilterBuilder().equal("incepter", user);
        searchBuilder.getFilterBuilder().in("status", new Object[] { IncepterStatus.已收件, IncepterStatus.未处理 });
        searchBuilder.getFilterBuilder().notEqual("mail.status", MailStatus.垃圾);
        searchBuilder.appendSort(Sort.Direction.DESC, "mail.sendDate");
        searchBuilder.appendSort(Sort.Direction.DESC, "id");
        SearchListHelper<EmIncepter> listhelper = new SearchListHelper<EmIncepter>();
        listhelper.execute(searchBuilder, incepterService.getRepository(), page, rows);
        Page p = new Page();

        //获取需要的数据
        List<Map<String,Object>> listData = Lists.newArrayList();
        for(EmIncepter incepter : listhelper.getList()){
            Map<String,Object> dataMap = Maps.newLinkedHashMap();
            dataMap.put("id", incepter.getId());
            dataMap.put("incepterStatus", incepter.getStatus());
            if(incepter.getMail()!=null) {
                dataMap.put("emailId", incepter.getMail().getId());
                dataMap.put("title", incepter.getMail().getTitle());
                String content = MobileTool.replaceHtml(incepter.getMail().getContent());
                dataMap.put("content", MobileTool.interceptStr(content,60));
                dataMap.put("sendDate", incepter.getMail().getSendDate());
                dataMap.put("sendDateShow", Tools.formatMobileDateTime(incepter.getMail().getSendDate()));
                if(incepter.getMail().getSender()!=null) {
                    dataMap.put("senderName", incepter.getMail().getSender().getUserName());
                }
            }
            listData.add(dataMap);
        }
        p.setRows(listData);
        p.setTotal(listhelper.getCount());

        return AesTool.responseData(p,aesFlag_);
    }

    /**
     * 获取个人未处理的收件数量
     * @param request
     * @param aesFlag_
     * @return
     */
    @RequestMapping(value = {"/inboxcount.mo"})
    @ResponseBody
    public AppResponseInfo getInboxCount(HttpServletRequest request, Boolean aesFlag_) {
        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }
        Long count = this.emIncepterRepository.countUnreadInboxNum(user.getId());
        return AesTool.responseData(count, aesFlag_);
    }

    /**
     * 查询个人发件箱记录
     * @param request
     * @param aesFlag_
     * @return
     */
    @RequestMapping(value = {"/outboxlist.mo"})
    @ResponseBody
    public AppResponseInfo findOutboxList(Integer page, Integer rows,HttpServletRequest request, Boolean aesFlag_){
        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }

        Page p = this.findEmailByStatus(request,MailStatus.已发送,page,rows,user);

        return AesTool.responseData(p,aesFlag_);
    }

    /**
     * 查询个人草稿箱记录
     * @param request
     * @param aesFlag_
     * @return
     */
    @RequestMapping(value = {"/draftlist.mo"})
    @ResponseBody
    public AppResponseInfo findDraftList(Integer page, Integer rows,HttpServletRequest request, Boolean aesFlag_){
        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }
        Page p = this.findEmailByStatus(request,MailStatus.草稿,page,rows,user);

        return AesTool.responseData(p,aesFlag_);
    }

    /**
     * 查询个人归档箱记录
     * @param request
     * @param aesFlag_
     * @return
     */
    @RequestMapping(value = {"/archivelist.mo"})
    @ResponseBody
    public AppResponseInfo findArchiveList(Integer page, Integer rows,HttpServletRequest request, Boolean aesFlag_){
        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }
        SearchBuilder<EmTempStorage, String> searchBuilder = new SearchBuilder<EmTempStorage, String>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.getFilterBuilder().equal("incepter", user);
        String senderId = request.getParameter("senderId");
        if (StringUtils.isNotBlank(senderId)) {
            searchBuilder.getFilterBuilder().equal("sender", this.userService.findOne(Long.valueOf(senderId)));
        }

        searchBuilder.appendSort(Sort.Direction.DESC, "pigeDate");
        SearchListHelper<EmTempStorage> listhelper = new SearchListHelper<EmTempStorage>();
        listhelper.execute(searchBuilder, tempStorageService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), ArchiveShowBean.class));
        //获取需要的数据
        List<Map<String,Object>> listData = Lists.newArrayList();
        for(EmTempStorage tempStorage : listhelper.getList()){
            Map<String,Object> dataMap = Maps.newLinkedHashMap();
            dataMap.put("id", tempStorage.getId());
            dataMap.put("title", tempStorage.getTitle());
            if(tempStorage.getSender()!=null) {
                dataMap.put("senderName", tempStorage.getSender().getUserName());
            }
            String content = MobileTool.replaceHtml(tempStorage.getContent());
            dataMap.put("content", MobileTool.interceptStr(content,60));
            dataMap.put("pigeDate", tempStorage.getPigeDate());
            dataMap.put("pigeDateShow", Tools.formatMobileDateTime(tempStorage.getPigeDate()));
            listData.add(dataMap);
        }
        p.setRows(listData);
        p.setTotal(listhelper.getCount());

        return AesTool.responseData(p,aesFlag_);
    }

    /**
     * 查询个人垃圾箱记录
     * @param request
     * @param aesFlag_
     * @return
     */
    @RequestMapping(value = {"/dustbinlist.mo"})
    @ResponseBody
    public AppResponseInfo findDustbinlistList(String uid,Integer page, Integer rows,HttpServletRequest request, Boolean aesFlag_){
        Page p = new Page();
        p.setRows(this.dustbinService.seleceEmDustbinForMobile(uid,page, rows));
        p.setTotal(this.dustbinService.coutEmDustbin(uid));

        return AesTool.responseData(p,aesFlag_);
    }

    /**
     * 根据状态查询邮件
     * @param request
     * @param page
     * @param rows
     * @param user
     * @return
     */
    private Page findEmailByStatus(HttpServletRequest request,MailStatus status,Integer page, Integer rows,SysUser user){
        SearchBuilder<EmMain, String> searchBuilder = new SearchBuilder<EmMain, String>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.getFilterBuilder().equal("sender", user);
        searchBuilder.getFilterBuilder().equal("status", status);

        searchBuilder.appendSort(Sort.Direction.DESC, "saveDate");
        searchBuilder.appendSort(Sort.Direction.DESC, "id");
        SearchListHelper<EmMain> listhelper = new SearchListHelper<EmMain>();
        listhelper.execute(searchBuilder, emMainService.getRepository(), page, rows);
        Page p = new Page();
        //获取需要的数据
        List<Map<String,Object>> listData = Lists.newArrayList();
        for(EmMain emMain : listhelper.getList()){
            Map<String,Object> dataMap = Maps.newLinkedHashMap();
            dataMap.put("id", emMain.getId());
            dataMap.put("title", emMain.getTitle());
            if(emMain.getSender()!=null) {
                dataMap.put("senderName", emMain.getSender().getUserName());
            }
            dataMap.put("address", emMain.getAddress());
            dataMap.put("addressName", emMain.getAddressName());
            dataMap.put("sendDate", emMain.getSendDate());
            dataMap.put("sendDateShow", Tools.formatMobileDateTime(emMain.getSendDate()));
            dataMap.put("saveDate", emMain.getSaveDate());
            dataMap.put("saveDateShow", Tools.formatMobileDateTime(emMain.getSaveDate()));
            dataMap.put("incepterStatus", emMain.getStatus());

            String content = MobileTool.replaceHtml(emMain.getContent());
            dataMap.put("content", MobileTool.interceptStr(content,60));
            listData.add(dataMap);
        }

        p.setRows(listData);
        p.setTotal(listhelper.getCount());

        return p;
    }

    /**
     * 存草稿
     * @param bean
     * @param aesFlag_
     * @return
     */
    @RequestMapping("/outboxsave.mo")
    @ResponseBody
    public AppResponseInfo saveDrafts(Long uid,MailEditBean bean, Boolean aesFlag_) {
        try {
            if (bean != null) {
                bean.setStatus(MailStatus.草稿);
            }
            bean.setSender(uid);
            this.emMainService.saveMail(bean);
            return AesTool.responseData("1","保存成功",null,aesFlag_);
        } catch (Exception e) {
            e.printStackTrace();
            return AesTool.responseData("0","保存失败",null,aesFlag_);
        }
    }

    // 发送
    @RequestMapping("/outboxsend.mo")
    @ResponseBody
    public AppResponseInfo sendEmail(MailEditBean bean, HttpServletRequest request, Boolean aesFlag_) {
        try {
            SysUser user = this.loadCurrentUser(request);
            if (user == null) {
                return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
            }
            if (bean != null) {
                bean.setStatus(MailStatus.已发送);
            }
            bean.setSender(user.getId());
            this.emMainService.saveMail(bean);
            logService.saveMobileLog(user, "发送邮件《" + bean.getTitle() +"》 TO：" + bean.getManAddressNames(), request);
            return AesTool.responseData("1","发送成功",null,aesFlag_);
        } catch (Exception e) {
            e.printStackTrace();
            return AesTool.responseData("0","发送失败",null,aesFlag_);
        }
    }

    /**
     * 邮件详情
     */
    @RequestMapping(value = {"/detail.mo"})
    @ResponseBody
    public AppResponseInfo findEmailDetail(String id, Boolean aesFlag_){
        AppEmailShowBean showBean = null;
        if (StringUtils.isNotBlank(id)) {
            EmMain mail = this.emMainService.findOne(id);
            if(mail!=null){
                showBean = BeanCopyBuilder.buildObject(mail,AppEmailShowBean.class);
                showBean.setManAddressNames(mail.getAddressName());
                showBean.setManAddress(mail.getAddress());
                //附件信息
                showBean.setAttachList(this.attachService.findByOwnerIdAndOwnerType(mail.getId(),EmMain.class.getSimpleName()));
            }
        }
        return AesTool.responseData(showBean,aesFlag_);
    }

    /**
     * 邮件详情
     */
    @RequestMapping(value = {"/dustbindetail.mo"})
    @ResponseBody
    public AppResponseInfo findDustbinDetail(String delId, Boolean aesFlag_){
        AppEmailShowBean showBean = null;
        if (StringUtils.isNotBlank(delId)) {
            EmDustbin dust = this.dustbinService.findOne(delId);
            if(dust!=null){
                EmMain mail = this.emMainService.findOne(dust.getEmailId());
                if(mail!=null) {
                    showBean = BeanCopyBuilder.buildObject(mail, AppEmailShowBean.class);
                    //返回的是 EmDustbin 的ID
                    showBean.setId(dust.getId());
                    //附件信息
                    showBean.setAttachList(this.attachService.findByOwnerIdAndOwnerType(mail.getId(), EmMain.class.getSimpleName()));
                }
            }
        }
        return AesTool.responseData(showBean,aesFlag_);
    }

    /**
     * 邮件详情
     */
    @RequestMapping(value = {"/archivedetail.mo"})
    @ResponseBody
    public AppResponseInfo findArchiveDetail(String tmpId, Boolean aesFlag_){
        AppEmailShowBean showBean = null;
        if (StringUtils.isNotBlank(tmpId)) {
            EmTempStorage ts = this.tempStorageService.findOne(tmpId);
            if(ts!=null){
                EmMain mail = this.emMainService.findOne(ts.getEmailId());
                if(mail!=null) {
                    showBean = BeanCopyBuilder.buildObject(mail, AppEmailShowBean.class);
                    //返回的是 EmDustbin 的ID
                    showBean.setId(ts.getId());
                    //附件信息
                    showBean.setAttachList(this.attachService.findByOwnerIdAndOwnerType(mail.getId(), EmMain.class.getSimpleName()));
                }
            }
        }
        return AesTool.responseData(showBean,aesFlag_);
    }

    /**
     * 邮件移到垃圾箱
     */
    @RequestMapping(value = {"/todustbin.mo"})
    @ResponseBody
    public AppResponseInfo emailToDustbin(String id, Boolean aesFlag_){
        if(dustbinService.draftsTrash(id)){
            return AesTool.responseData("1","操作成功",null,aesFlag_);
        }
        return AesTool.responseData("0","操作失败",null,aesFlag_);
    }

    /**
     * 邮件从垃圾箱恢复
     */
    @RequestMapping(value = {"/recover.mo"})
    @ResponseBody
    public AppResponseInfo recoverFromDustbin(Long uid,String id, Boolean aesFlag_){
        if(this.dustbinService.recoverTrash(id, uid)){
            return AesTool.responseData("1","操作成功",null,aesFlag_);
        }
        return AesTool.responseData("0","操作失败",null,aesFlag_);
    }

    /**
     * 邮件删除
     */
    @RequestMapping(value = {"/delete.mo"})
    @ResponseBody
    public AppResponseInfo deleteEmail(Long uid,String delId, Boolean aesFlag_){
        if(this.dustbinService.deleteTrash(delId, uid)){
            return AesTool.responseData("1","删除成功",null,aesFlag_);
        }
        return AesTool.responseData("0","删除失败",null,aesFlag_);
    }

    /**
     * 删除发件箱的邮件
     */
    @RequestMapping(value = {"/deletesend.mo"})
    @ResponseBody
    public AppResponseInfo deleteSendEmail(String id, Boolean aesFlag_){
        if(this.emMainService.delMail(id)){
            return AesTool.responseData("1","删除成功",null,aesFlag_);
        }
        return AesTool.responseData("0","删除失败",null,aesFlag_);
    }

    /**
     * 撤回
     * @param id
     * @param request
     * @param aesFlag_
     * @return
     */
    @RequestMapping("/getback.mo")
    @ResponseBody
    public AppResponseInfo emailGetBack(String id, HttpServletRequest request, Boolean aesFlag_) {
        try {
            if (this.emMainService.getBack(id)) {
                SysUser user = this.loadCurrentUser(request);
                if (user == null) {
                    return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
                }
                logService.saveMobileLog(user, "撤回邮件，id=" + id, request);
                return AesTool.responseData("1","邮件撤回成功",null,aesFlag_);
            } else {
                return AesTool.responseData("1","收件人已对该邮件进行处理，无法撤回",null,aesFlag_);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AesTool.responseData("0","撤回失败",null,aesFlag_);
        }
    }

    // 阅读
    @RequestMapping("/inboxread.mo")
    @ResponseBody
    public AppResponseInfo readInboxEmail(String id, Long uid, Boolean aesFlag_) {
        try {
            this.emMainService.readMail(id, uid);
            return AesTool.responseData("1","操作成功",null,aesFlag_);
        } catch (Exception e) {
            e.printStackTrace();
            return AesTool.responseData("0","操作失败",null,aesFlag_);
        }
    }

    // 回复

    /**
     * 存档
     * @param incIds
     * @param aesFlag_
     * @return
     */
    @RequestMapping("/archiveemail.mo")
    @ResponseBody
    public AppResponseInfo archiveEmail(String incIds , Boolean aesFlag_) {
        try {
            this.tempStorageService.archiveMails(incIds);
            return AesTool.responseData("1","存档成功",null,aesFlag_);
        } catch (Exception e) {
            e.printStackTrace();
            return AesTool.responseData("0","操作失败",null,aesFlag_);
        }
    }

    /**
     * 从收件箱移动到垃圾箱
     * @param mailId
     * @param uid
     * @param aesFlag_
     * @return
     */
    @RequestMapping("/inboxtodustbin.mo")
    @ResponseBody
    public AppResponseInfo inboxToDustbin(String mailId , Long uid, Boolean aesFlag_) {
        try {
            if (dustbinService.inboxTrash(mailId, uid)) {
                return AesTool.responseData("1","保存成功",null,aesFlag_);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AesTool.responseData("0","操作失败",null,aesFlag_);
    }

    /**
     * 从归档移动到垃圾箱
     * @param tmpIds
     * @param request
     * @param aesFlag_
     * @return
     */
    @RequestMapping("/archivetodustbin.mo")
    @ResponseBody
    public AppResponseInfo archiveToDustbin(String tmpIds, HttpServletRequest request, Boolean aesFlag_) {
        try {
            SysUser user = this.loadCurrentUser(request);
            if (user == null) {
                return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
            }
            if (dustbinService.archiveTrash(tmpIds)) {
                logService.saveMobileLog(user, "移动到垃圾箱，id为'" + tmpIds + "'的记录", request);
                return AesTool.responseData("1","保存成功",null,aesFlag_);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AesTool.responseData("0","操作失败",null,aesFlag_);
    }

    /**
     * 从归档箱删除
     * @param tmpIds
     * @param request
     * @param aesFlag_
     * @return
     */
    @RequestMapping("/deletearchive.mo")
    @ResponseBody
    public AppResponseInfo deleteArchiveEmail(String tmpIds, HttpServletRequest request, Boolean aesFlag_) {
        try {
            SysUser user = this.loadCurrentUser(request);
            if (user == null) {
                return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
            }
            if (this.tempStorageService.deleteArchive(tmpIds)) {
                logService.saveMobileLog(user, "删除邮件，id为'" + tmpIds + "'的记录", request);
                return AesTool.responseData("1","保存成功",null,aesFlag_);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AesTool.responseData("0","操作失败",null,aesFlag_);
    }

    /**
     * 转发
     * @param emailId
     * @param aesFlag_
     * @return
     */
    @RequestMapping("/emailattach.mo")
    @ResponseBody
    public AppResponseInfo emailAttach(String emailId, Boolean aesFlag_) {
        //转存附件
        Map<String, List<SysAttach>> attachMap = this.attachService.copyAttachMap(emailId);
        List<SysAttach> attachList = null;
        if(attachMap!=null) {
            attachList = attachMap.get(emailId);
        }else{
            attachList = Lists.newArrayList();
        }
        return AesTool.responseData(attachList, aesFlag_);
    }
}

