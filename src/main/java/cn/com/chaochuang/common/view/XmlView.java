package cn.com.chaochuang.common.view;

import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.view.ViewToolContext;
import org.apache.velocity.tools.view.ViewToolManager;
import org.springframework.web.servlet.view.velocity.VelocityView;

import cn.com.chaochuang.velocity.toolbox2.VelocityToolbox2Utils;

/**
 * 
 * @author LJX
 *
 */
public class XmlView  extends VelocityView {

    protected void renderMergedTemplateModel(Map model, HttpServletRequest request, HttpServletResponse response)
                    throws Exception {

    	Writer writer = new StringWriter();

        exposeHelpers(model, request);

        Context velocityContext = createVelocityContext(model, request, response);
        exposeHelpers(velocityContext, request, response);
        exposeToolAttributes(velocityContext, request);
        
        Template t = getTemplate();
        t.merge(velocityContext, writer);

        String fileName = "新建Word文档.doc";
        if (model.containsKey("fileName")) {
            fileName = model.get("fileName").toString();
            if (-1 == fileName.toLowerCase().indexOf(".doc")) {
                fileName += ".doc";
            }
        }
        response.setContentType("application/msword");
        response.setHeader("Content-Disposition", String.valueOf(new StringBuffer("attachment; filename=\"")
                        .append(this.encodeFileName(fileName, request)).append("\"")));

        OutputStream os = response.getOutputStream();
        os.write(writer.toString().getBytes("utf-8"));
        if (os != null) {
            os.close();
        }
    }
    
    private String encodeFileName(String fileName, HttpServletRequest request) throws UnsupportedEncodingException {
        String agent = request.getHeader("USER-AGENT");

        if (null != agent && (-1 != agent.indexOf("MSIE") || -1 != agent.indexOf("Trident")
                        || -1 != agent.indexOf("Edge"))) {
            return URLEncoder.encode(fileName, "UTF-8");
        } else if (null != agent && -1 != agent.indexOf("Firefox")) {
            return "=?UTF-8?B?" + (new String(Base64.encodeBase64(fileName.getBytes("UTF-8")))) + "?=";
        } else if (null != agent && -1 != agent.indexOf("Chrome")) {
            return new String(fileName.getBytes("utf-8"), "ISO-8859-1");
        } else {
            return fileName;
        }
    }

    private String          toolboxConfigLocation;
    private boolean         autoConfig      = true;
    private boolean         includeDefaults = true;
    private ViewToolManager viewToolManager;

    public String getToolboxConfigLocation() {
        return toolboxConfigLocation;
    }

    public void setToolboxConfigLocation(String toolboxConfigLocation) {
        this.toolboxConfigLocation = toolboxConfigLocation;
    }

    public ViewToolManager getViewToolManager() {
        return viewToolManager;
    }

    public void setViewToolManager(ViewToolManager viewToolManager) {
        this.viewToolManager = viewToolManager;
    }

    @Override
    protected Context createVelocityContext(Map<String, Object> model, HttpServletRequest request,
                    HttpServletResponse response) throws Exception {

        ToolContext velocityContext = null;

        if (this.viewToolManager != null || getToolboxConfigLocation() != null || autoConfig) {
            ViewToolManager velocityToolManager = null;
            if (this.viewToolManager != null) {
                velocityToolManager = this.viewToolManager;
            } else {
                velocityToolManager = VelocityToolbox2Utils.buildViewToolManager(getServletContext(),
                                toolboxConfigLocation, autoConfig, includeDefaults);
            }

            velocityToolManager.setVelocityEngine(getVelocityEngine());
            velocityContext = velocityToolManager.createContext(request, response);

        } else {
            velocityContext = new ViewToolContext(getVelocityEngine(), request, response, getServletContext());
        }

        if (model != null) {
            for (Map.Entry<String, Object> entry : (model.entrySet())) {
                velocityContext.put(entry.getKey(), entry.getValue());
            }
        }

        return velocityContext;
    }

    @Override
    protected void initTool(Object tool, Context velocityContext) throws Exception {
        // do nothing
    }

    public boolean isAutoConfig() {
        return autoConfig;
    }

    public void setAutoConfig(boolean autoConfig) {
        this.autoConfig = autoConfig;
    }

    public boolean isIncludeDefaults() {
        return includeDefaults;
    }

    public void setIncludeDefaults(boolean includeDefaults) {
        this.includeDefaults = includeDefaults;
    }
}
