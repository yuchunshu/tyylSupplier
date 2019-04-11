/*
 * FileName:    ExcelConfigurer.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年2月3日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.excelview;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.ui.velocity.VelocityEngineFactory;
import org.springframework.web.context.ServletContextAware;

/**
 * @author LJX
 *
 */
public class ExcelConfigurer extends VelocityEngineFactory
		implements ExcelConfig, InitializingBean, ResourceLoaderAware, ServletContextAware {

	/** the name of the resource loader for Spring's bind macros */
	private static final String SPRING_MACRO_RESOURCE_LOADER_NAME = "springMacro";

	/** the key for the class of Spring's bind macro resource loader */
	private static final String SPRING_MACRO_RESOURCE_LOADER_CLASS = "springMacro.resource.loader.class";

	/** the name of Spring's default bind macro library */
	private static final String SPRING_MACRO_LIBRARY = "org/springframework/web/servlet/view/velocity/spring.vm";

	private VelocityEngine velocityEngine;

	private ServletContext servletContext;

	/**
	 * Set a pre-configured VelocityEngine to use for the Velocity web
	 * configuration: e.g. a shared one for web and email usage, set up via
	 * {@link org.springframework.ui.velocity.VelocityEngineFactoryBean}.
	 * <p>
	 * Note that the Spring macros will <i>not</i> be enabled automatically in
	 * case of an external VelocityEngine passed in here. Make sure to include
	 * {@code spring.vm} in your template loader path in such a scenario (if
	 * there is an actual need to use those macros).
	 * <p>
	 * If this is not set, VelocityEngineFactory's properties (inherited by this
	 * class) have to be specified.
	 */
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * Initialize VelocityEngineFactory's VelocityEngine if not overridden by a
	 * pre-configured VelocityEngine.
	 *
	 * @see #createVelocityEngine
	 * @see #setVelocityEngine
	 */
	@Override
	public void afterPropertiesSet() throws IOException, VelocityException {
		if (this.velocityEngine == null) {
			this.velocityEngine = createVelocityEngine();
		}
	}

	public VelocityEngine getVelocityEngine() {
		return this.velocityEngine;
	}

	private ResourceLoader resourceLoader = null;
	private String resourceLoaderPath = null;

	/**
	 * @see org.springframework.ui.velocity.VelocityEngineFactory#setResourceLoaderPath(java.lang.String)
	 */
	public void setResourceLoaderPath(String resourceLoaderPath) {
		this.resourceLoaderPath = resourceLoaderPath;
		super.setResourceLoaderPath(resourceLoaderPath);
	}

	/**
	 * @see org.springframework.ui.velocity.VelocityEngineFactory#setResourceLoader(org.springframework.core.io.ResourceLoader)
	 */
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
		super.setResourceLoader(resourceLoader);
	}

	/**
	 * @see org.springframework.ui.velocity.VelocityEngineFactory#postProcessVelocityEngine(org.apache.velocity.app.VelocityEngine)
	 */
	protected void postProcessVelocityEngine(VelocityEngine velocityEngine) {

		if ((null != this.resourceLoader) && (null != this.resourceLoaderPath)) {
			String[] paths = this.resourceLoaderPath.split(",");
			StringBuffer filePaths = new StringBuffer();
			try {
				for (int i = 0; i < paths.length; i++) {
					if (filePaths.length() > 0) {
						filePaths.append(", ");
					}
					Resource path = this.resourceLoader.getResource(paths[i]);
					File file = path.getFile(); // will fail if not resolvable
												// in the file system

					filePaths.append(file.getAbsolutePath());
				}
				velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
				velocityEngine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, filePaths.toString());
				velocityEngine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, "true");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		velocityEngine.setApplicationAttribute(ServletContext.class.getName(), this.servletContext);
		velocityEngine.setProperty(SPRING_MACRO_RESOURCE_LOADER_CLASS, ClasspathResourceLoader.class.getName());
		velocityEngine.addProperty(VelocityEngine.RESOURCE_LOADER, SPRING_MACRO_RESOURCE_LOADER_NAME);
		velocityEngine.addProperty(VelocityEngine.VM_LIBRARY, SPRING_MACRO_LIBRARY);

		if (logger.isInfoEnabled()) {
			logger.info("ClasspathResourceLoader with name '" + SPRING_MACRO_RESOURCE_LOADER_NAME
					+ "' added to configured VelocityEngine");
		}
	}

	/**
	 * （non Javadoc）
	 *
	 * @see org.springframework.ui.velocity.VelocityEngineFactory#newVelocityEngine()
	 */
	protected VelocityEngine newVelocityEngine() throws IOException, VelocityException {
		return new VelocityEngine();
	}

	/**
	 * （non Javadoc）
	 *
	 * @see com.spower.excel.engine.ExcelConfig#getExcelEngine()
	 */
	public VelocityEngine getExcelEngine() {
		return (VelocityEngine) getVelocityEngine();
	}

}
