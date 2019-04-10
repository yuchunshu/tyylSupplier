/*
 * FileName:    RemoteDocSwapAttachController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月22日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.crypto.CipherInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;

import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.upload.support.PluploadController;
import cn.com.chaochuang.common.upload.support.UploadFileItem;
import cn.com.chaochuang.common.util.AttachHelper;
import cn.com.chaochuang.common.util.EncryptUtils;
import cn.com.chaochuang.common.util.ExcelUtil;
import cn.com.chaochuang.common.util.WordUtil;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapAttach;
import cn.com.chaochuang.doc.remotedocswap.service.RemoteDocSwapAttachService;


/** 
 * @ClassName: RemoteDocSwapAttachController 
 * @Description: 公文交换附件控制
 * @author: chunshu
 * @date: 2017年7月5日 下午11:00:23  
 */
@Controller
@RequestMapping("remotedocswap")
public class RemoteDocSwapAttachController extends PluploadController {
    
	@Value(value = "${upload.appid.remote.docswap.attach}")
    private String                     appid;

    @Value(value = "${upload.rootpath}")
    private String           		   wordImageTempPath;
    
    @Autowired
    private RemoteDocSwapAttachService attachService;

    @Override
    protected String getAppId(HttpServletRequest request) {
        return this.appid;
    }

    @RequestMapping("/attach/delete.json")
    @ResponseBody
    public ReturnInfo del(Long[] ids, HttpServletRequest request) {
        try {
            for (Long delId : ids) {
                this.attachService.delete(delId);
            }
            return new ReturnInfo("1", null, "删除成功！");
        } catch (Exception e) {
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }
    
    @RequestMapping(value = "/previewWord", produces = "text/html;charset=GB2312")
    @ResponseBody
    public void wordpreview(Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
    	RemoteDocSwapAttach attach = this.attachService.findOne(id);
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
    public void excelpreview(Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
    	RemoteDocSwapAttach attach = this.attachService.findOne(id);
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


    @RequestMapping("/previewVedio")
    public ModelAndView vedio(String preUrl, String id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/preview/vedio");
        UploadFileItem fileItem = super.getUploadFileSaver().getUploadFileItem(getAppId(request), id);
        String path = getAttachPath(fileItem);
        if (StringUtils.isNotBlank(path)) {
            String fileType = path.substring(path.lastIndexOf(".")).toLowerCase();
            mav.addObject("fileType", fileType);
        }
        mav.addObject("preUrl", preUrl);
        mav.addObject("id", id);
        return mav;
    }

    @RequestMapping("/previewPdf")
    @ResponseBody
    public String preview(String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        UploadFileItem fileItem = super.getUploadFileSaver().getUploadFileItem(getAppId(request), id);
        String errorMessage = null;
        if (null != fileItem) {
            String targetFilename = FilenameUtils.normalize(
                            getUploadFileSaver().getUploadRootPath() + "/" + fileItem.getSavePath() + "/"
                                            + fileItem.getSaveName(), true);
            File targetFile = new File(targetFilename);

            if (targetFile.exists()) {
                String userAgent = request.getHeader("user-agent");
                boolean isMustEncode = false;
                if (StringUtils.isNotBlank(userAgent)) {
                    if (StringUtils.containsIgnoreCase(userAgent, " MSIE ")) {
                        isMustEncode = true;
                    }
                }
                InputStream is = null;
                OutputStream os = null;
                try {
                    String trueName = new String(fileItem.getTrueName().getBytes("UTF-8"), "ISO-8859-1");
                    if (isMustEncode)
                        trueName = URLEncoder.encode(trueName, "ISO-8859-1");
                    is = new FileInputStream(targetFile);
                    if (trueName.endsWith(".pdf"))
                        response.setContentType("application/pdf");
                    os = response.getOutputStream();
                    response.setHeader("Content-Disposition", "inline; filename=\"" + trueName + "\"");
                    IOUtils.copy(is, os);

                } catch (UnsupportedEncodingException e) {
                    errorMessage = e.getMessage();
                } catch (FileNotFoundException e) {
                    errorMessage = e.getMessage();
                } catch (IOException e) {
                    errorMessage = e.getMessage();
                } finally {
                    if (is != null) {
                        IOUtils.closeQuietly(is);
                    }
                    if (os != null) {
                        try {
                            os.flush();
                        } catch (IOException ex) {
                            errorMessage = ex.getMessage();
                        }
                        IOUtils.closeQuietly(os);
                    }
                }

            }
        } else {
            errorMessage = "";
        }

        if (null != errorMessage) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, errorMessage);
        }
        return errorMessage;
    }


    private String getAttachPath(UploadFileItem fileItem) {
        if (fileItem != null) {
            UploadFileItem a = fileItem;
            if (a != null) {
                StringBuffer path = new StringBuffer();
                if (StringUtils.isNotBlank(a.getSavePath())) {
                    if (a.getSavePath().contains(getUploadFileSaver().getUploadRootPath())) {
                        path.append(a.getSavePath()).append("/").append(a.getSaveName());
                    } else {
                        path.append(this.getUploadFileSaver().getUploadRootPath()).append("/").append(a.getSavePath())
                                        .append(a.getSaveName());
                    }
                    return path.toString();
                }
            }
        }
        return null;
    }
    
    /**
     * 获取附信息
     * @param attachId
     * @return
     */
    @RequestMapping("/getInfo.json")
    @ResponseBody
    public Map<String,Object> findAttachInfo(String attachId){
        Map<String,Object> dataMap = Maps.newLinkedHashMap();
        if(attachId!=null){
        	RemoteDocSwapAttach attach = this.attachService.findOne(Long.valueOf(attachId));
            dataMap.put("savePath" , this.wordImageTempPath+"/"+attach.getSavePath()+attach.getSaveName());
        }
        return dataMap;
    }
}
