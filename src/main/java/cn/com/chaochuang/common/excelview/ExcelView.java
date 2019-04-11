/*
 * FileName:    ExcelView.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年2月3日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.excelview;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.servlet.view.velocity.VelocityView;

import cn.com.chaochuang.common.tool.HelperObjectCache;

/**
 * @author LJX
 *
 */
public class ExcelView extends VelocityView {
    /** 文件名标识 */
    public static final String  FILENAME_IDENTIFIER = "FILENAME";

    /** The content type for an Excel response */
    private static final String CONTENT_TYPE        = "application/vnd.ms-excel";
    // private static final String CONTENT_TYPE = "application/octet-stream";
    // private static final String CONTENT_TYPE = "application/x-msdownload";

    /** The extension to look for existing templates */
    private static final String EXTENSION           = ".xls";

    private Map                 helperMap           = HelperObjectCache.getHelperMap();

    private VelocityEngine      excelEngine;

    protected void exposeHelpers(Map model, HttpServletRequest request) throws Exception {
        if (null != helperMap) {
            for (Iterator it = helperMap.entrySet().iterator(); it.hasNext();) {
                Map.Entry me = (Map.Entry) it.next();
                if (model.containsKey(me.getKey())) {
                    throw new ServletException("Cannot expose helper attribute '" + me.getKey()
                                    + "' because of an existing model object of the same name");
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("Exposing helper attribute '" + me.getKey() + "' with value [" + me.getValue()
                                    + "] to model");
                }
                model.put(me.getKey(), me.getValue());
            }
        }
    }

    protected void doBeforRender(Map model, HttpServletRequest request, HttpServletResponse response)
                    throws UnsupportedEncodingException {

        String fileName = (String) model.get(FILENAME_IDENTIFIER);

        if (fileName == null || fileName.length() == 0) {
            fileName = "新建Excel文档";
        }
        fileName = fileName.trim();
        if (!fileName.toLowerCase().endsWith(EXTENSION)) {
            fileName += EXTENSION;
        }
        response.setHeader(
                        "Content-Disposition",
                        String.valueOf(new StringBuffer("attach; filename=\"").append(
                                        new String(fileName.getBytes("GBK"), "ISO-8859-1")).append("\"")));
    }

    /**
     * （non Javadoc）
     *
     * @see org.springframework.web.servlet.view.AbstractTemplateView#renderMergedTemplateModel(java.util.Map,
     *      javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void renderMergedTemplateModel(Map model, HttpServletRequest request, HttpServletResponse response)
                    throws Exception {
        if (this.getUrl().endsWith(EXTENSION)) { // excel文件
            response.setContentType(CONTENT_TYPE);
            exposeHelpers(model, request);

            doBeforRender(model, request, response);

            Context context = createContext(model, request, response);

            doExcelRender(context, response);
        } else {
            super.renderMergedTemplateModel(model, request, response);
        }
    }

    protected void doExcelRender(Context context, HttpServletResponse response) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("Rendering Velocity template [" + getUrl() + "] in VelocityView '" + getBeanName() + "'");
        }
        mergeTemplate(getExcelTemplate(), context, response);
    }

    /**
     * Merge the template with the context. Can be overridden to customize the behavior.
     *
     * @param template
     *            the template to merge
     * @param context
     *            the Velocity context to use for rendering
     * @param response
     *            servlet response (use this to get the OutputStream or Writer)
     * @throws Exception
     *             if thrown by Velocity
     * @see org.apache.velocity.ExcelTemplate#merge
     */
    protected void mergeTemplate(ExcelTemplate template, Context context, HttpServletResponse response)
                    throws Exception {
        template.merge(context, response.getOutputStream());
    }

    /**
     * Invoked on startup. Looks for a single ExcelConfig bean to find the relevant ExcelEngine for this factory.
     */
    protected void initApplicationContext() throws BeansException {
        super.initApplicationContext();

        if (getExcelEngine() == null) {
            // No explicit ExcelEngine: try to autodetect one.
            setExcelEngine(autodetectExcelEngine());
        }
    }

    /**
     * Autodetect a ExcelEngine via the ApplicationContext. Called if no explicit ExcelEngine has been specified.
     *
     * @return the ExcelEngine to use for VelocityViews
     * @throws BeansException
     *             if no ExcelEngine could be found
     * @see #getApplicationContext
     * @see #setExcelEngine
     */
    protected VelocityEngine autodetectExcelEngine() throws BeansException {
        try {
            ExcelConfig velocityConfig = (ExcelConfig) BeanFactoryUtils.beanOfTypeIncludingAncestors(
                            getApplicationContext(), ExcelConfig.class, true, false);
            return velocityConfig.getExcelEngine();
        } catch (NoSuchBeanDefinitionException ex) {
            throw new ApplicationContextException(
                            "Must define a single ExcelConfig bean in this web application context "
                                            + "(may be inherited): ExcelConfigurer is the usual implementation. "
                                            + "This bean may be given any name.", ex);
        }
    }

    protected ExcelTemplate getExcelTemplate() throws Exception {
        try {
            return getExcelTemplate(getUrl());
        } catch (ResourceNotFoundException ex) {
            throw new Exception("Cannot find Excel template for URL [" + getUrl()
                            + "]: Did you specify the correct resource loader path?", ex);
        } catch (Exception ex) {
            throw new Exception("Could not load Excel template for URL [" + getUrl() + "]", ex);
        }
    }

    protected ExcelTemplate getExcelTemplate(String name) throws Exception {
        return (ExcelTemplate) getExcelEngine().getTemplate(name);
    }

    protected Context createContext(Map model, HttpServletRequest request, HttpServletResponse response)
                    throws Exception {
        return createContext(model);
    }

    protected Context createContext(Map model) throws Exception {
        return new VelocityContext(model);
    }

    /**
     * @return 返回 helperMap。
     */
    public Map getHelperMap() {
        return helperMap;
    }

    /**
     * @param helperMap
     *            要设置的 helperMap。
     */
    public void setHelperMap(Map helperMap) {
        this.helperMap = helperMap;
    }

    /**
     * @return 返回 excelEngine。
     */
    public VelocityEngine getExcelEngine() {
        return excelEngine;
    }

    /**
     * @param excelEngine
     *            要设置的 excelEngine。
     */
    public void setExcelEngine(VelocityEngine excelEngine) {
        this.excelEngine = excelEngine;
    }
}
