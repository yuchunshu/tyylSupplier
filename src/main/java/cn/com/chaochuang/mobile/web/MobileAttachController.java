package cn.com.chaochuang.mobile.web;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.mobile.bean.AppResponseInfo;
import cn.com.chaochuang.mobile.util.AesTool;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.common.upload.support.PluploadController;

/**
 * @author hzr 2017.8.18
 *
 */
@Controller
@RequestMapping("/mobile/attach")
public class MobileAttachController extends PluploadController {

    @Value(value = "${upload.appid.sysattach}")
    private String           appId;

    @Value(value = "${upload.rootpath}")
    private String           rootPath;

    @Autowired
    private SysAttachService attachService;

    @Override
    protected String getAppId(HttpServletRequest request) {
        return appId;
    }


    /**附件下载*/
    @RequestMapping(value = {"/download.json","/download.mo"})
    @ResponseBody
    public byte[] attachDownloadJson(HttpServletRequest request) {
    	String id = request.getParameter("id");
    	if(StringUtils.isNotBlank(id)){
            SysAttach attach = attachService.findOne(id);
            File f = new File(this.rootPath+File.separator+attach.getSavePath()+File.separator+attach.getSaveName());
            if(f.exists()) {
                try {
                    return FileUtils.readFileToByteArray(f);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    	return null;
    }

    /**
     * 获取文件md5值
     * @param id
     * @return
     */
    @RequestMapping(value = {"/getmdf.mo"})
    @ResponseBody
    public AppResponseInfo getFileMdfCode(String id){
        String mdfCode = "";
        if(StringUtils.isNotBlank(id)){
            SysAttach attach = attachService.findOne(id);
            File f = new File(this.rootPath+File.separator+attach.getSavePath()+File.separator+attach.getSaveName());
            if(f.exists()) {
                try {
                    mdfCode = Tools.getFileMd5Code(f);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return AesTool.responseData(mdfCode, false);
    }

    /**
     * 文件覆盖，新上传的文件覆盖原文件
     * @param attachId
     * @param preAttachId
     * @return
     */
    @RequestMapping(value = {"/fileedit.mo"})
    @ResponseBody
    public AppResponseInfo attachFileEdit(String attachId,String preAttachId){
        boolean resullt = attachService.attachFileEdit(attachId,preAttachId);
        if(resullt){
            return AesTool.responseData("上传成功",false);
        }else{
            return AesTool.responseData("0","上传失败","",false);

        }
    }
}
