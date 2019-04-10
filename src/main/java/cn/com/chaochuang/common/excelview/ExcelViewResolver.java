/*
 * FileName:    ExcelViewResolver.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年2月3日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.excelview;

import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

/**
 * @author LJX
 *
 */
public class ExcelViewResolver extends VelocityViewResolver {

    private String otherFlagPrefix;

    private String otherPrefix;

    private String otherSuffix;

    /**
     * （non Javadoc）
     *
     * @see org.springframework.web.servlet.view.velocity.VelocityViewResolver#buildView(java.lang.String)
     */
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        AbstractUrlBasedView view = super.buildView(viewName);
        if (viewName != null && otherFlagPrefix != null && otherFlagPrefix.length() > 0
                        && (viewName.startsWith(otherFlagPrefix))) {
            view.setUrl(this.otherPrefix + viewName + this.otherSuffix);
        }
        return view;
    }

    /**
     * @return 返回 otherFlagPrefix。
     */
    public String getOtherFlagPrefix() {
        return otherFlagPrefix;
    }

    /**
     * @param otherFlagPrefix
     *            要设置的 otherFlagPrefix。
     */
    public void setOtherFlagPrefix(String excelPrefix) {
        this.otherFlagPrefix = excelPrefix;
    }

    /**
     * @return 返回 otherSuffix。
     */
    public String getOtherSuffix() {
        return otherSuffix;
    }

    /**
     * @param otherSuffix
     *            要设置的 otherSuffix。
     */
    public void setOtherSuffix(String otherSuffix) {
        this.otherSuffix = otherSuffix;
    }

    /**
     * @return 返回 otherPrefix。
     */
    public String getOtherPrefix() {
        return otherPrefix;
    }

    /**
     * @param otherPrefix
     *            要设置的 otherPrefix。
     */
    public void setOtherPrefix(String otherPrefix) {
        this.otherPrefix = otherPrefix;
    }
}
