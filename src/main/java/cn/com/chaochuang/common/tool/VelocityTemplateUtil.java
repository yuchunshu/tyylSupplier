/*
 * FileName:    VelocityTemplateUtil.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月13日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.tool;

import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

/**
 * @author LJX
 *
 */
public class VelocityTemplateUtil {
    private VelocityConfigurer velocityConfig;

    private String             encoding = "GBK";

    protected void exposeHelpers(Map model) {
        if (null != HelperObjectCache.getHelperMap()) {
            for (Iterator it = HelperObjectCache.getHelperMap().entrySet().iterator(); it.hasNext();) {
                Map.Entry me = (Map.Entry) it.next();
                model.put(me.getKey(), me.getValue());
            }
        }
    }

    public void renderTemplate(Map model, String templateName, Writer writer, String code) {
        VelocityEngine ve = velocityConfig.getVelocityEngine();
        if (null != code) {
            encoding = code;
        }
        if (model == null) {
            model = new HashMap();
        }
        exposeHelpers(model);
        Context context = new VelocityContext(model);
        try {
            ve.mergeTemplate(templateName, encoding, context, writer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return 返回 encoding。
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * @param encoding
     *            要设置的 encoding。
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * @return 返回 velocityConfig。
     */
    public VelocityConfigurer getVelocityConfig() {
        return velocityConfig;
    }

    /**
     * @param velocityConfig
     *            要设置的 velocityConfig。
     */
    public void setVelocityConfig(VelocityConfigurer velocityConfig) {
        this.velocityConfig = velocityConfig;
    }

}
