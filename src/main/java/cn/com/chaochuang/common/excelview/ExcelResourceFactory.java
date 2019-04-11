/*
 * FileName:    ExcelResourceFactory.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年2月3日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.excelview;

import org.apache.velocity.runtime.resource.ContentResource;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.ResourceManager;

/**
 * @author LJX
 *
 */
public class ExcelResourceFactory {
    public static Resource getResource(String resourceName, int resourceType) {
        Resource resource = null;

        switch (resourceType) {
        case ResourceManager.RESOURCE_TEMPLATE:
            resource = new ExcelTemplate();
            break;

        case ResourceManager.RESOURCE_CONTENT:
            resource = new ContentResource();
            break;
        }

        return resource;
    }
}
