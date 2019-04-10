/*
 * FileName:    ChartExportController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年6月4日 (HM) 1.0 Create
 */

package cn.com.chaochuang.doc.docstatis.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.transcoder.SVGAbstractTranscoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.chaochuang.common.util.Tools;

/**
 * hichart图片导出功能
 *
 * @author HM
 *
 */
@Controller
@RequestMapping("doc/docstatis/export")
public class ChartExportController {

    /** 附件存放根路径 */
    @Value("${upload.rootpath}")
    private String rootPath;

    @Value("${upload.appid.chart}")
    private String chartPath;

    /**
     * 导出图片调用方法
     *
     * @param svgCode
     *            图片数据
     * @param chartTitle
     *            导出标题（不带格式）
     * @param picWidth
     *            图片分辨率（宽）
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/exportChart")
    @ResponseBody
    public String exportChart(String svgCode, String chartTitle, Float picWidth, HttpServletRequest request,
                    HttpServletResponse response) {

        // 导出名称： 标题_XXXX年XX月XX日.png
        return chartDownload(svgCode, chartTitle + "_" + Tools.DATE_FORMAT3.format(new Date()) + ".png", picWidth,
                        response);

    }

    /**
     * 图片下载
     *
     * @param svg
     * @param chartTitle
     * @param picWidth
     *            图片分辨率（宽）
     * @param response
     * @return
     */
    private String chartDownload(String svg, String chartTitle, Float picWidth, HttpServletResponse response) {
        svg = svg.replaceAll(":rect", "rect");

        InputStream is = null;
        OutputStream os = null;
        String result = null;
        try {
            byte[] bytes = svg.getBytes("UTF-8");
            os = response.getOutputStream();
            is = new ByteArrayInputStream(bytes);

            // 图片处理
            PNGTranscoder t = new PNGTranscoder();
            if (picWidth != null) {
                t.addTranscodingHint(SVGAbstractTranscoder.KEY_WIDTH, picWidth);
            } else {
                t.addTranscodingHint(SVGAbstractTranscoder.KEY_WIDTH, new Float(800));
            }
            TranscoderInput input = new TranscoderInput(is);
            TranscoderOutput output = new TranscoderOutput(os);

            response.setContentType("application/octet-stream");
            String trueName = new String(chartTitle.getBytes("GBK"), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + trueName + "\"");

            t.transcode(input, output);
        } catch (IOException ex) {
            // 下载过程中用户取消或其它IO异常
            result = "用户取消下载或其它IO异常" + ex.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    result = "关闭输入流异常：" + ex.getMessage();
                }
            }
            if (os != null) {
                try {
                    os.flush();
                } catch (IOException ex) {
                    result = "清空输出缓冲异常：" + ex.getMessage();
                }
                try {
                    os.close();
                } catch (IOException ex) {
                    result = "关闭输出流异常：" + ex.getMessage();
                }
            }
        }
        return result;
    }
    
    
}
