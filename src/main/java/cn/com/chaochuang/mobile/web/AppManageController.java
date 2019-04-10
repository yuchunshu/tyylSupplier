/*
 * FileName:    MobileUpdateController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年08月29日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.mobile.web;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.reference.IsValid;
import cn.com.chaochuang.common.upload.support.PluploadController;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.common.util.UserHelper;
import cn.com.chaochuang.mobile.bean.AppDeviceInfo;
import cn.com.chaochuang.mobile.bean.AppResponseInfo;
import cn.com.chaochuang.mobile.domain.SysApkInfo;
import cn.com.chaochuang.mobile.domain.SysMobileDevice;
import cn.com.chaochuang.mobile.reference.AppPlatform;
import cn.com.chaochuang.mobile.reference.RegisterStatus;
import cn.com.chaochuang.mobile.service.MobileRegisterService;
import cn.com.chaochuang.mobile.service.SysApkInfoService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author Shicx
 * 移动端管理。只在电脑端使用
 */
@Controller
@RequestMapping("appmanage")
public class AppManageController extends PluploadController {

    //文件根路径
    @Value(value = "${upload.rootpath}")
    private String           rootPath;
    //apk文件存放目录
    @Value(value = "${upload.appid.apkfile}")
    private String           appId;

    @Autowired
    protected ConversionService conversionService;
    @Autowired
    private SysApkInfoService sysApkInfoService;
    @Autowired
    private MobileRegisterService mobileRegisterService;


    @Override
    protected String getAppId(HttpServletRequest request) {
        return appId;
    }

    /**
     * APK文件列表
     */
    @RequestMapping("/apklist")
    public ModelAndView apkListPage() {
        ModelAndView mav = new ModelAndView("/system/apkmanage/list");
        return mav;
    }

    /**
     * APK文件列表
     * @param page
     * @param rows
     * @param request
     * @return
     */
    @RequestMapping("/listapk.json")
    @ResponseBody
    public Page apkListJson(Integer page, Integer rows, HttpServletRequest request) {
        if(page==null){
            page = 1;
        }
        if(rows == null){
            rows = 15;
        }
        SearchBuilder<SysApkInfo, String> searchBuilder = new SearchBuilder<SysApkInfo, String>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.appendSort(Sort.Direction.DESC, "uploadTime");
        searchBuilder.appendSort(Sort.Direction.DESC, "id");

        SearchListHelper<SysApkInfo> listhelper = new SearchListHelper<SysApkInfo>();
        listhelper.execute(searchBuilder, sysApkInfoService.getRepository(), page, rows);

        Page p = new Page();
        p.setRows(listhelper.getList());
        p.setTotal(listhelper.getCount());
        return p;
    }

    /**
     * 新建一个apk文件上传信息
     * @return
     */
    @RequestMapping("/newapk")
    public ModelAndView addApkInfo() {
        ModelAndView mav = new ModelAndView("/system/apkmanage/new");
        return mav;
    }

    /**
     * 更改apk文件信息
     * @param apkInfo
     * @return
     */
    @RequestMapping("updateapk.json")
    @ResponseBody
    public String updateApkInfo(SysApkInfo apkInfo){
        SysUser currentUser = UserHelper.getCurrentUser();
        if(currentUser!=null) {
            String id = sysApkInfoService.updateApkInfo(apkInfo);
            return JSONObject.toJSONString(new AppResponseInfo(id));
        }
        return JSONObject.toJSONString(new AppResponseInfo("0","无权限",null));
    }

    /**
     *删除apk文件信息
     * @param apkInfo
     * @return
     */
    @RequestMapping("deleteapk.json")
    @ResponseBody
    public String deleteApkInfo(SysApkInfo apkInfo){
        SysUser currentUser = UserHelper.getCurrentUser();
        if(currentUser!=null) {
            boolean result = sysApkInfoService.deleteApkInfo(apkInfo);
            if(result) {
                return JSONObject.toJSONString(new AppResponseInfo(result));
            }
        }
        return JSONObject.toJSONString(new AppResponseInfo("0","删除失败",null));
    }

    /**
     * 返回最新的apk文件信息
     * @return
     */
    @RequestMapping("checkupdate.action")
    @ResponseBody
    public String getLastApkInfo(AppPlatform platform){
        if(platform==null){
            //默认查询android端
            platform = AppPlatform.Android;
        }
        SysApkInfo apkInfo = sysApkInfoService.findLastApkInfoByPlatform(platform);
        if(apkInfo!=null){
            return JSONObject.toJSONString(new AppResponseInfo(apkInfo));
        }else{
            return JSONObject.toJSONString(new AppResponseInfo("0","没有APK文件",null));
        }
    }

    /**
     * 下载最新的Android apk
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/apkdownload.action")
    @ResponseBody
    public String download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String errorMessage = null;
        SysApkInfo sysApkInfo = sysApkInfoService.findLastApkInfoByPlatform(AppPlatform.Android);
        if (null != sysApkInfo) {
            String targetFilename = FilenameUtils.normalize(this.rootPath + "/" + sysApkInfo.getFilePath() + "/" + sysApkInfo.getSaveName(), true);
            File targetFile = new File(targetFilename);

            if (targetFile.exists()) {
                String userAgent = request.getHeader("user-agent");
                boolean isMustEncode = false;
                if (StringUtils.isNotBlank(userAgent)) {
                    if (StringUtils.containsIgnoreCase(userAgent, " MSIE ")
                            || StringUtils.containsIgnoreCase(userAgent, "Trident")
                            || StringUtils.containsIgnoreCase(userAgent, "Edge")) {
                        isMustEncode = true;
                    }
                }
                InputStream is = null;
                OutputStream os = null;

                try {
                    String trueName = new String(sysApkInfo.getTrueName().getBytes("UTF-8"), "ISO-8859-1");
                    if (isMustEncode) {
                        trueName = URLEncoder.encode(trueName, "ISO-8859-1");
                    }
                    is = new FileInputStream(targetFile);
                    //返回文件大小(单位为字节)
                    if(sysApkInfo.getFileSize()!=null) {
                        response.setHeader("Content-Length", sysApkInfo.getFileSize()+ "");
                    }
                    response.setContentType("application/octet-stream");
                    os = response.getOutputStream();

                    String preview = request.getParameter("preview");
                    if ("pdf".equals(preview)) {
                        response.setContentType("application/pdf");
                        response.setHeader("Content-Disposition", "inline; filename=\"" + trueName + "\"");
                    } else {
                        response.setHeader("Content-Disposition", "attachment; filename=\"" + trueName + "\"");
                    }
                    IOUtils.copy(is, os);

                } catch (UnsupportedEncodingException e) {
                    errorMessage = "文件名编码错误：" + e.getMessage();
                } catch (FileNotFoundException e) {
                    errorMessage = "下载文件不存在：" + e.getMessage();
                } catch (IOException e) {
                    errorMessage = "数据读取错误：" + e.getMessage();
                } finally {
                    if (is != null) {
                        IOUtils.closeQuietly(is);
                    }
                    if (os != null) {
                        try {
                            os.flush();
                        } catch (IOException ex) {
                            errorMessage = "清空输出缓冲异常：" + ex.getMessage();
                        }
                        IOUtils.closeQuietly(os);
                    }
                }

            }
        } else {
            errorMessage = "无法读取文件";
        }

        if (null != errorMessage) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, errorMessage);
        }
        return errorMessage;
    }


    /**
     * 设备申请列表
     */
    @RequestMapping("/devicelist")
    public ModelAndView deviceListPage() {
        ModelAndView mav = new ModelAndView("/system/mobiledevice/list");
        return mav;
    }

    /**
     * 设备申请列表
     * @param page
     * @param rows
     * @param request
     * @return
     */
    @RequestMapping("/listdevice.json")
    @ResponseBody
    public Page deviceListJson(Integer page, Integer rows, HttpServletRequest request) {
        if(page==null){
            page = 1;
        }
        if(rows == null){
            rows = 15;
        }
        SearchBuilder<SysMobileDevice, String> searchBuilder = new SearchBuilder<SysMobileDevice, String>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.appendSort(Sort.Direction.DESC, "registerTime");
        searchBuilder.appendSort(Sort.Direction.DESC, "id");

        SearchListHelper<SysMobileDevice> listhelper = new SearchListHelper<SysMobileDevice>();
        listhelper.execute(searchBuilder, mobileRegisterService.getRepository(), page, rows);

        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), AppDeviceInfo.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    /**
     *删除用户设备信息
     * @param deviceId
     * @return
     */
    @RequestMapping("deletedevice.json")
    @ResponseBody
    public String deleteDeviceInfo(String deviceId,HttpServletRequest request){
        SysUser currentUser = UserHelper.getCurrentUser();
        boolean result = mobileRegisterService.deleteMobileDevice(deviceId,currentUser,request);
        if(result) {
            return JSONObject.toJSONString(new AppResponseInfo("1","删除成功",null));
        }
        return JSONObject.toJSONString(new AppResponseInfo("0","删除失败",null));
    }

    /**
     *修改用户设备状态信息
     * @param deviceId
     * @return
     */
    @RequestMapping("updatestatus.json")
    @ResponseBody
    public String changeDeviceStatus(String deviceId, RegisterStatus registerStatus, HttpServletRequest request){
        SysUser currentUser = UserHelper.getCurrentUser();
        boolean result = mobileRegisterService.changeDeviceStatus(deviceId, registerStatus,currentUser,request);
        if(result) {
            return JSONObject.toJSONString(new AppResponseInfo("1","删除成功",null));
        }
        return JSONObject.toJSONString(new AppResponseInfo("0","删除失败",null));
    }

    /**
     *修改用户设备有效状态信息
     * @param deviceId
     * @return
     */
    @RequestMapping("updatevalid.json")
    @ResponseBody
    public String changeDeviceValidable(String deviceId, IsValid isValid, HttpServletRequest request){
        SysUser currentUser = UserHelper.getCurrentUser();
        boolean result = mobileRegisterService.changeDeviceValidable(deviceId, isValid,currentUser,request);
        if(result) {
            return JSONObject.toJSONString(new AppResponseInfo("1","修改成功",null));
        }
        return JSONObject.toJSONString(new AppResponseInfo("0","修改失败",null));
    }

    /**
     * 热更新文件列表
     */
    @RequestMapping("/chcplist")
    public ModelAndView chcpListPage() {
        ModelAndView mav = new ModelAndView("/system/apkmanage/chcplist");

        //获取classes路径
        String classesPath=this.getClass().getClassLoader().getResource("/").getPath();
        String mobileFolderPath = classesPath.replace("WEB-INF/classes/","mobile/");
        File mobileFolder = new File(mobileFolderPath);
        mav.addObject("fileList",mobileFolder.listFiles());
        return mav;
    }

    /**
     * 根据文件名删除mobile文件夹下的文件
     * @param folderName
     * @return
     */
    @RequestMapping("/deletechcp.json")
    @ResponseBody
    public String deleteChcpFolder(String folderName){
        //获取classes路径
        String classesPath=this.getClass().getClassLoader().getResource("/").getPath();
        String mobileFolderPath = classesPath.replace("WEB-INF/classes/","mobile/");
        File chcpFile = new File(mobileFolderPath+folderName);
        boolean result = false;
        if(chcpFile.exists()){
            try {
                FileUtils.deleteDirectory(chcpFile);
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(result){
            return "1";
        }
        return "0";
    }

}

