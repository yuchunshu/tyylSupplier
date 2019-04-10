/*
 * FileName:    AppItemAttachPluploadController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年12月2日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.oa.mail.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.CipherInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.chaochuang.common.upload.support.PluploadController;
import cn.com.chaochuang.common.util.AttachHelper;
import cn.com.chaochuang.common.util.EncryptUtils;
import cn.com.chaochuang.common.util.ExcelUtil;
import cn.com.chaochuang.common.util.WordUtil;
import cn.com.chaochuang.oa.mail.domain.EmAttach;
import cn.com.chaochuang.oa.mail.service.EmAttachService;

/**
 * @author LJX
 *
 */
@Controller
@RequestMapping("emAttach")
public class EmAttachPluploadController extends PluploadController {

    @Value(value = "${upload.appid.emattach}")
    private String          appId;

    @Value(value = "${upload.rootpath}")
    private String          wordImageTempPath;
    @Autowired
    private EmAttachService attachService;

    @Override
    protected String getAppId(HttpServletRequest request) {
        return appId;
    }

    @RequestMapping(value = "/previewWord", produces = "text/html;charset=GB2312")
    @ResponseBody
    public void wordpreview(String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        EmAttach attach = this.attachService.findOne(id);
        int index = attach.getSaveName().indexOf(".");
        String attachUrl = AttachHelper.getAttacheFileFullPath(attach.getSavePath(), attach.getSaveName(), "");
        String fileExt = FilenameUtils.getExtension(attach.getSaveName());
        boolean crypted = StringUtils.isNotBlank(fileExt) ? false : true;// 根据保存文件名的后缀判断文件是否被加密：无后缀是加密文件

        InputStream is = null;
        CipherInputStream cis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            if (crypted == true) {// 需要解密
                cis = EncryptUtils.decryptInputStream(EncryptUtils.ALGORITHM_DES, new FileInputStream(attachUrl));
                WordUtil.convert2Html(cis, wordImageTempPath, os);
            } else {
                is = new FileInputStream(attachUrl);
                WordUtil.convert2Html(is, wordImageTempPath, os);
            }

        } catch (Exception e) {
            if (os != null) {
                os.write(new String("<!doctype html><html><head><meta charset='utf-8' />"
                                + "<meta http-equiv='X-UA-Compatible' content='IE=edge' />"
                                + "<meta name='viewport' content='width=device-width, initial-scale=1' /></head>"
                                + "<body>无法预览此文档</body></html>").getBytes("utf-8"));
                os.flush();
            }
            e.printStackTrace();
        } finally {
            if (cis != null) {
                cis.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

    @RequestMapping(value = "/previewExcel", produces = "text/html;charset=GB2312")
    @ResponseBody
    public void excelpreview(String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        EmAttach attach = this.attachService.findOne(id);
        int index = attach.getSaveName().indexOf(".");
        String attachUrl = AttachHelper.getAttacheFileFullPath(attach.getSavePath(), attach.getSaveName(), "");
        String fileExt = FilenameUtils.getExtension(attach.getSaveName());
        boolean crypted = StringUtils.isNotBlank(fileExt) ? false : true;// 根据保存文件名的后缀判断文件是否被加密：无后缀是加密文件

        InputStream is = null;
        CipherInputStream cis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            if (crypted == true) {// 需要解密
                cis = EncryptUtils.decryptInputStream(EncryptUtils.ALGORITHM_DES, new FileInputStream(attachUrl));
                ExcelUtil.convert2Html(cis, wordImageTempPath, os);
            } else {
                is = new FileInputStream(attachUrl);
                ExcelUtil.convert2Html(is, wordImageTempPath, os);
            }
        } catch (Exception e) {
            if (os != null) {
                os.write(new String("<!doctype html><html><head><meta charset='utf-8' />"
                                + "<meta http-equiv='X-UA-Compatible' content='IE=edge' />"
                                + "<meta name='viewport' content='width=device-width, initial-scale=1' /></head>"
                                + "<body>无法预览此文档</body></html>").getBytes("utf-8"));
                os.flush();
            }
            e.printStackTrace();
        } finally {
            if (cis != null) {
                cis.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

}
