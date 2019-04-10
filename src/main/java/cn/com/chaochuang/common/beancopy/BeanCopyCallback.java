/*
 * FileName:    BeanCopyCallback.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
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
