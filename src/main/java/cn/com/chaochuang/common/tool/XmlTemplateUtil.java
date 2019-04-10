/*
 * FileName:    XmlTemplateUtil.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月13日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.tool;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * @author LJX
 *
 */
public class XmlTemplateUtil {
    private static VelocityTemplateUtil velocityTemplateUtil;

    public static void renderXmlTemplate(String templateName, Map model, Writer writer, String encoding) {
        getVelocityTemplateUtil().renderTemplate(model, templateName, writer, encoding);
    }

    public static String renderXmlTemplate(String templateName, Map model, String encoding) {
        Writer writer = new StringWriter();
        renderXmlTemplate(templateName, model, writer, encoding);
        return writer.toString();
    }

    /**
     * @return 返回 VelocityTemplateUtil。
     */
    public static VelocityTemplateUtil getVelocityTemplateUtil() {
        return velocityTemplateUtil;
    }

    /**
     * @param VelocityTemplateUtil
     *            要设置的 VelocityTemplateUtil。
     */
    public void setVelocityTemplateUtil(VelocityTemplateUtil velocityTemplateUtil) {
        XmlTemplateUtil.velocityTemplateUtil = velocityTemplateUtil;
    }
}
