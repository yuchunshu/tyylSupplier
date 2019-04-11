/*
 * FileName:    ExcelResourceManager.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年2月3日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.excelview;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.ResourceManagerImpl;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

/**
 * @author LJX
 *
 */
public class ExcelResourceManager extends ResourceManagerImpl {

    /**
     * switch to turn off log notice when a resource is found for the first time.
     */
    private boolean logWhenFound = true;

    /**
     * （non Javadoc）
     *
     * @see org.apache.velocity.runtime.resource.ResourceManagerImpl#initialize(org.apache.velocity.runtime.RuntimeServices)
     */
    public void initialize(RuntimeServices rs) {
        super.initialize(rs);

        /*
         * now see if this is overridden by configuration
         */

        logWhenFound = rsvc.getBoolean(RuntimeConstants.RESOURCE_MANAGER_LOGWHENFOUND, true);
    }

    /**
     * （non Javadoc）
     *
     * @see org.apache.velocity.runtime.resource.ResourceManagerImpl#loadResource(java.lang.String, int,
     *      java.lang.String)
     */
    protected Resource loadResource(String resourceName, int resourceType, String encoding)
                    throws ResourceNotFoundException, ParseErrorException {
        Resource resource = ExcelResourceFactory.getResource(resourceName, resourceType);

        resource.setRuntimeServices(rsvc);

        resource.setName(resourceName);
        resource.setEncoding(encoding);

        /*
         * Now we have to try to find the appropriate loader for this resource. We have to cycle through the list of
         * available resource loaders and see which one gives us a stream that we can use to make a resource with.
         */

        long howOldItWas = 0; // Initialize to avoid warnings

        ResourceLoader resourceLoader = null;

        for (int i = 0; i < resourceLoaders.size(); i++) {
            resourceLoader = (ResourceLoader) resourceLoaders.get(i);
            resource.setResourceLoader(resourceLoader);

            /*
             * catch the ResourceNotFound exception as that is ok in our new multi-loader environment
             */

            try {
                if (resource.process()) {
                    /*
                     * FIXME (gmj) moved in here - technically still a problem - but the resource needs to be processed
                     * before the loader can figure it out due to to the new multi-path support - will revisit and fix
                     */

                    if (logWhenFound) {
                        rsvc.info("ResourceManager : found " + resourceName + " with loader "
                                        + resourceLoader.getClassName());
                    }

                    howOldItWas = resourceLoader.getLastModified(resource);
                    break;
                }
            } catch (ResourceNotFoundException rnfe) {
                /*
                 * that's ok - it's possible to fail in multi-loader environment
                 */
            }
        }

        /*
         * Return null if we can't find a resource.
         */
        if (resource.getData() == null) {
            throw new ResourceNotFoundException("Unable to find resource '" + resourceName + "'");
        }

        /*
         * some final cleanup
         */

        resource.setLastModified(howOldItWas);

        resource.setModificationCheckInterval(resourceLoader.getModificationCheckInterval());

        resource.touch();

        return resource;
    }

}