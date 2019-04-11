/*
 * FileName:    Snippet.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年2月19日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.excelview;

import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.springframework.web.servlet.view.velocity.VelocityView;

/**
 * @author LJX
 *
 */
public class ExcelXmlView extends VelocityView {

    protected void renderMergedTemplateModel(Map model, HttpServletRequest request, HttpServletResponse response)
                    throws Exception {

        Writer writer = new StringWriter();

        exposeHelpers(model, request);

        Context velocityContext = createVelocityContext(model, request, response);
        exposeHelpers(velocityContext, request, response);
        exposeToolAttributes(velocityContext, request);

        Template t = getTemplate();
        t.merge(velocityContext, writer);

        String fileName = "新建文档";
        String fileType = ".xls";
        
        if (model.containsKey("fileName")) {
            fileName = model.get("fileName").toString();
        }
        if (model.containsKey("fileType")) {
        	fileType = model.get("fileType").toString();
        }
        
        fileName = fileName + fileType;
        
        response.setContentType("application/octet-stream");

        response.setHeader(
                        "Content-Disposition",
                        String.valueOf(new StringBuffer("attachment; filename=\"").append(
                                        new String(fileName.getBytes("gbk"), "ISO-8859-1")).append("\"")));

        OutputStream os = response.getOutputStream();
        os.write(writer.toString().getBytes("utf-8"));
        if (os != null) {
            os.close();
        }

    }
}
