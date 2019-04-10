package cn.com.chaochuang.common.view;

import org.apache.velocity.tools.view.ViewToolManager;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.velocity.VelocityView;

import cn.com.chaochuang.velocity.toolbox2.VelocityToolbox2Utils;
/**
 * 
 * @author LJX
 *
 */
public class XmlToolboxViewResolver extends AbstractTemplateViewResolver {
    private String          dateToolAttribute;

    private String          numberToolAttribute;

    private String          toolboxConfigLocation;

    private boolean         autoConfig      = true;
    private boolean         includeDefaults = true;

    private ViewToolManager viewToolManager;

    public XmlToolboxViewResolver() {
        setViewClass(requiredViewClass());
    }

    @Override
    protected Class<?> requiredViewClass() {
        return XmlView.class;
    }

    public String getDateToolAttribute() {
        return dateToolAttribute;
    }

    public void setDateToolAttribute(String dateToolAttribute) {
        this.dateToolAttribute = dateToolAttribute;
    }

    public String getNumberToolAttribute() {
        return numberToolAttribute;
    }

    public void setNumberToolAttribute(String numberToolAttribute) {
        this.numberToolAttribute = numberToolAttribute;
    }

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

    @Override
    protected void initApplicationContext(ApplicationContext context) {
        super.initApplicationContext(context);

        if (this.viewToolManager != null || this.toolboxConfigLocation != null) {
            if (VelocityView.class.equals(getViewClass())) {
                logger.info("Using " + XmlView.class.getName() + " instead of default VelocityView "
                                + "due to specified toolboxConfigLocation");
                setViewClass(XmlView.class);
            } else if (!XmlView.class.isAssignableFrom(getViewClass())) {
                throw new IllegalArgumentException("Given view class [" + getViewClass().getName()
                                + "] is not of type [" + XmlView.class.getName()
                                + "], which it needs to be in case of a specified toolboxConfigLocation");
            }

            if (this.viewToolManager == null && this.toolboxConfigLocation != null) {
                this.viewToolManager = VelocityToolbox2Utils.buildViewToolManager(getServletContext(),
                                toolboxConfigLocation, autoConfig, includeDefaults);
            }
        }
    }

    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        XmlView view = (XmlView) super.buildView(viewName);
        view.setDateToolAttribute(this.dateToolAttribute);
        view.setNumberToolAttribute(this.numberToolAttribute);
        if (this.toolboxConfigLocation != null) {
            view.setToolboxConfigLocation(this.toolboxConfigLocation);
        }
        if (this.viewToolManager != null) {
            view.setViewToolManager(viewToolManager);
        }
        view.setAutoConfig(autoConfig);
        view.setIncludeDefaults(includeDefaults);
        return view;
    }
}
