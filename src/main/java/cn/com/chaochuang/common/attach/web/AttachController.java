/*
 * FileName:    NoticeController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.common.attach.web;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.bean.ReturnBean;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.event.repository.OaDocFileRepository;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.attach.service.SysAttachService;

import java.util.Map;

/**
 * @author HM
 *
 */
@Controller
@RequestMapping("sysattach")
public class AttachController {

    @Autowired
    private SysAttachService    attachService;

    @Autowired
    protected ConversionService conversionService;

    @Autowired
    private OaDocFileRepository docFileRepository;

    @Value(value = "${upload.rootpath}")
    private String           rootPath;

    /**
     * 删除附件记录和文件
     * @param id
     * @return
     */
    @RequestMapping("/delAttach.json")
    @ResponseBody
    public ReturnInfo del(String id) {
        try {
            boolean flag = this.attachService.deleteAttach(id);
            if (flag) {
                return new ReturnInfo(id, null, "删除成功!");
            } else {
                return new ReturnInfo(null, "删除失败!", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
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
            SysAttach attach = this.attachService.findOne(attachId);
            dataMap.put("savePath" , this.rootPath+"/"+attach.getSavePath()+attach.getSaveName());
        }
        return dataMap;
    }

    @RequestMapping("/getpdf.json")
    @ResponseBody
    public ReturnBean convertPdf(String oaDocFileId){
        ReturnBean bean = new ReturnBean();
        bean.setSuccess(false);
        try {
            OaDocFile doc = docFileRepository.findOne(oaDocFileId);
            if(doc!=null&&doc.getDocId()!=null){
                SysAttach attach = attachService.findOne(doc.getDocId());
                if(attach!=null){
                    String subfix =  attach.getSaveName().substring(attach.getSaveName().lastIndexOf("."));
                    if(subfix.contains(".do")||subfix.contains(".xl")||subfix.contains(".pp")){
                        SysAttach pdfAttach = attachService.mainFileToPdf(attach,doc);
                        if(pdfAttach!=null) {
                            bean.setSuccess(true);
                            bean.setMessage("转换成功");
                            bean.setObject(pdfAttach);
                        }
                    }else{
                        bean.setMessage("只能转换Word、Excel和PPT文件");
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return bean;
    }
}
