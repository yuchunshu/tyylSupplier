/*
 * FileName:    BeanCopyCallback.java
 * Description:
 * Company:     
 * Copyright:    (c) 2015
 * History:     2015年1月28日 (guig) 1.0 Create
 */

package cn.com.chaochuang.common.beancopy;

/**
 * @author guig
 *
 */
public interface BeanCopyCallback<ST, DT> {

    public void afterCopy(ST sourObj, DT destObj);

}
