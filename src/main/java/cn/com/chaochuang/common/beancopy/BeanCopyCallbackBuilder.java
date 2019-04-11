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
public class BeanCopyCallbackBuilder<ST, DT> {

    private final BeanCopyCallback<ST, DT> callback;

    public BeanCopyCallbackBuilder(final BeanCopyCallback<ST, DT> callback) {
        this.callback = callback;
    }

    public void call(Object sourObj, Object destObj) {
        if (null != callback) {
            callback.afterCopy((ST) sourObj, (DT) destObj);
        }
    }

}
